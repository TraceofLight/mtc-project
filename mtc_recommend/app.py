from flask import Flask, request, jsonify
import pymysql
import random
import string
import numpy as np
import sys
import datetime

import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from firebase_admin import db
from apscheduler.schedulers.background import BackgroundScheduler
import logging

app = Flask(__name__)


cred = credentials.Certificate('./mtc-project-7f2d7-firebase-adminsdk-yx2tr-3cd691107c.json')
firebase_admin.initialize_app(cred)

db = firestore.client()


"""
인자로 받은 유저 개수, 게시물 개수, 평가 개수만큼 임시 데이터를 만든다.
호출 시 기존 데이터를 모두 지우므로 주의해서 사용해야 한다.

params
user_size : 유저 개수
article_size : 게시물 개수
evaluate_size : 평가 개수
"""

# ================================== 유저 랜덤 생성 시작 ==================================
@app.route("/create", methods=["GET"])
def create_dataset():
    parameter_dict = request.args.to_dict()

    user_size = int(parameter_dict["user_size"])
    article_size = int(parameter_dict["article_size"])
    evaluate_size = int(parameter_dict["evaluate_size"])

    clear_database()
    create_user(user_size)
    create_article(user_size, article_size)
    create_evaluate(user_size, article_size, evaluate_size)
    setting_user(user_size)
    return "SUCCESS"

def setting_user(user_size):
    conn = pymysql.connect(host="i8a710.p.ssafy.io", user="root", password="a710soezmtc", db="mtc", charset="utf8") 
    cursor = conn.cursor()

    sql = "insert into setting (setting_user_index) values (%s)"
    for i in range(1, user_size+1):
        cursor.execute(sql, (i))
    conn.commit()
    conn.close()

def create_user(user_size):
    conn = pymysql.connect(host="i8a710.p.ssafy.io", user="root", password="a710soezmtc", db="mtc", charset="utf8") 
    cursor = conn.cursor()

    sql = "insert into user (user_nickname, user_uid) values (%s, %s)"
    for i in range(user_size):
        random_string = get_random_string()
        cursor.execute(sql, (random_string, random_string))
    conn.commit()
    conn.close()

def create_article(user_size, article_size):
    conn = pymysql.connect(host="i8a710.p.ssafy.io", user="root", password="a710soezmtc", db="mtc", charset="utf8") 
    cursor = conn.cursor()

    sql = "insert into article (article_user_index, article_title, article_regist_time) values (%s, %s, %s)"

    for i in range(article_size):
        random_string = get_random_string()
        cursor.execute(sql, (random.randint(1, user_size), random_string, datetime.datetime.now()))
    conn.commit()
    conn.close()

def create_evaluate(user_size, article_size, evaluate_size):
    conn = pymysql.connect(host="i8a710.p.ssafy.io", user="root", password="a710soezmtc", db="mtc", charset="utf8") 
    cursor = conn.cursor()

    value = ["GOOD", "SOSO", "BAD"]

    rand_list = []
    for i in range(1, user_size):
        for j in range(1, article_size):
            rand_list.append((i, j))

    random.shuffle(rand_list)

    sql = "insert into evaluate (evaluate_article_index, evaluate_user_index, evaluate_value, evaluate_date) values (%s, %s, %s, %s)"

    for i in range(evaluate_size):
        cursor.execute(sql, (rand_list[i][1], rand_list[i][0], value[random.randint(0, 2)], datetime.datetime.now()))
    conn.commit()
    conn.close()

def clear_database():
    conn = pymysql.connect(host="i8a710.p.ssafy.io", user="root", password="a710soezmtc", db="mtc", charset="utf8") 
    cursor = conn.cursor()

    cursor.execute("delete from setting")
    cursor.execute("alter table setting auto_increment=1")

    cursor.execute("delete from evaluate")
    cursor.execute("alter table evaluate auto_increment=1")

    cursor.execute("delete from article")
    cursor.execute("alter table article auto_increment=1")

    cursor.execute("delete from user")
    cursor.execute("alter table user auto_increment=1")

    conn.close()

def get_random_string():
    string_pool = string.ascii_letters + string.digits
    result = ""

    for i in range(random.randint(4, 10)):
        result += random.choice(string_pool)
    return result

# ================================== 유저 랜덤 생성 끝 ==================================

def get_evaluate_array(user, article_list):
    conn = pymysql.connect(host="i8a710.p.ssafy.io", user="root", password="a710soezmtc", db="mtc", charset="utf8") 
    cursor = conn.cursor()

    sql = "select evaluate_value from evaluate where evaluate_user_index = %s and evaluate_article_index = %s"
    len_article_list = len(article_list)
    evaluate_array = np.zeros(len_article_list)
    for i in range(len_article_list):
        cursor.execute(sql, (user, article_list[i]))
        evaluate_value = cursor.fetchone()
        if evaluate_value != None:
            if evaluate_value[0] == 'BAD':
                evaluate_array[i] = -1
            elif evaluate_value[0] == 'GOOD':
                evaluate_array[i] = 1
    conn.commit()
    conn.close()

    return evaluate_array

def get_similarity_list(user, compare_user_list, article_list):
    user_evaluate_array = get_evaluate_array(user, article_list)

    similarity_list = []

    for compare_user in compare_user_list:
        compare_evaluate_array = get_evaluate_array(compare_user, article_list)
        similarity_list.append(cos_similarity(user_evaluate_array, compare_evaluate_array))

    return similarity_list


def get_article_score(user_list, article_list, similarity):
    conn = pymysql.connect(host="i8a710.p.ssafy.io", user="root", password="a710soezmtc", db="mtc", charset="utf8") 
    cursor = conn.cursor()

    article_score_list = []
    sql = "select evaluate_value from evaluate where evaluate_user_index = %s and evaluate_article_index = %s"
    for article in article_list:
        score = 0
        for i in range(len(user_list)):
            cursor.execute(sql, (user_list[i], article))
            evaluate_value = 0
            evaluate_result = cursor.fetchone()
            if evaluate_result != None:
                if evaluate_result[0] == 'GOOD':
                    evaluate_value = 1
                elif evaluate_result[0] == 'BAD':
                    evaluate_value = -1
            score += (evaluate_value * similarity[i])
        if user_list:
            score /= len(user_list)
        article_score_list.append((score, article))
    return article_score_list

def get_recommend_list(user):
    conn = pymysql.connect(host="i8a710.p.ssafy.io", user="root", password="a710soezmtc", db="mtc", charset="utf8") 
    cursor = conn.cursor()

    # 유사도를 측정할 유저 목록 가져오기
    sql = "select distinct(evaluate_user_index), evaluate_date from evaluate order by evaluate_date desc limit 20"
    cursor.execute(sql)
    compare_user_list = [compare_user_tuple[0] for compare_user_tuple in cursor.fetchall()]

    # 유사도 측정을 위한 게시물 목록 가져오기
    sql = "select distinct(evaluate_article_index), evaluate_date from evaluate order by evaluate_date desc limit 100"
    cursor.execute(sql)
    temp_article_list = [article_tuple[0] for article_tuple in cursor.fetchall()]

    # 유사도 목록 가져오기
    similarity_list = get_similarity_list(user, compare_user_list, temp_article_list)

    # 추천할 게시물 목록 가져오기   - 최근에 평가된 게시물 500개
    sql = "select distinct(article_index), evaluate_date from article left join evaluate on article_index = evaluate_article_index left join `block` on article_user_index = block_target_index where (evaluate_user_index != %s or evaluate_user_index is null) and block_user_index is null and article_visible = 1 order by evaluate_date desc limit 500"
    cursor.execute(sql, (user))
    recommend_article_list = [article[0] for article in cursor.fetchall()]

    recommend_article_list = list(set(recommend_article_list))

    # 추천할 게시물 목록 가져오기   - 최근에 업로드된 게시물 500개
    sql = "select distinct(article_index), article_regist_time  from article left join `block` on article_user_index = block_target_index where block_user_index is null order by article_regist_time desc limit %s"
    cursor.execute(sql, (1000 - len(recommend_article_list)))
    extend_article_list = [article[0] for article in cursor.fetchall()]
    recommend_article_list.extend(extend_article_list)

    recommend_article_list = list(set(recommend_article_list))

    # 게시물 점수, 게시물 인덱스 얻어오고 점수 순으로 정렬
    article_score_list = get_article_score(compare_user_list, recommend_article_list, similarity_list)
    article_score_list.sort(reverse=True)

    article_list = [article[1] for article in article_score_list]

    conn.commit()
    conn.close()
    return article_list

def cos_similarity(v1, v2):
    dot_product = np.dot(v1, v2)
    l2_norm = (np.sqrt(sum(np.square(v1))) * np.sqrt(sum(np.square(v2))))
    similarity = dot_product / l2_norm
    return similarity

sched = BackgroundScheduler()

@sched.scheduled_job('interval', seconds=300, id='test1')
def set_recommend_list():
    app.logger.debug("run set_recommend_list()")
    conn = pymysql.connect(host="i8a710.p.ssafy.io", user="root", password="a710soezmtc", db="mtc", charset="utf8") 
    cursor = conn.cursor()

    # 유저 추출
    sql = "select recent_view_user_index from recent_view order by recent_view_time desc"
    cursor.execute(sql)
    user_list = list(set([user_tuple[0] for user_tuple in cursor.fetchall()]))

    # 각 유저의 추천 게시물 목록 얻어오기
    for user in user_list:
        recommend_list = get_recommend_list(user)

        recommend_ref = db.collection("recommend").document(str(user))
        recommend_ref.set({
            "list":recommend_list
        })

    conn.commit()
    conn.close()

def get_recent_article(user, page, size):
    conn = pymysql.connect(host="i8a710.p.ssafy.io", user="root", password="a710soezmtc", db="mtc", charset="utf8") 
    cursor = conn.cursor()

    sql = "select distinct(article_index), article_regist_time from article left join `block` on article_user_index = block_target_index where (block_user_index != %s or block_user_index is null) order by article_regist_time desc limit %s, %s"
    cursor.execute(sql, (user, page*size, size))

    article_list = [article[0] for article in cursor.fetchall()]
    conn.commit()
    conn.close()

    return article_list

@app.route("/recommend", methods=['GET'])
def recommend():
    user = request.args.get('user')

    doc_ref = db.collection('recommend').document(user).get().to_dict()

    page = request.args.get('page')
    size = request.args.get('size')

    if page == "null":
        page = 0

    if doc_ref == None:
        page = int(page)
        size = int(50)

        return get_recent_article(user, page, size)
    else:
        recommend_list = doc_ref["list"]
        if size == "null":
            size = len(recommend_list)
        page = int(page)
        size = int(size)

        start = page * size
        end = min((page + 1) * size, len(recommend_list))

        return recommend_list[start:end]

@app.route("/update", methods=['GET'])
def update():
    article = int(request.args.get('article'))
    docs = db.collection('recommend').get()

    for doc in docs:
        recommend_list = doc.to_dict()["list"]
        if article in recommend_list:
            recommend_list.remove(article)
            db.collection("recommend").document(str(doc.id)).set({"list":recommend_list})

    return "LIST UPDATE"

def init_scheduler():
    sched.start()


if __name__ == "__main__":
    init_scheduler()
    app.run(host="0.0.0.0", port=int("5000"), debug=True)