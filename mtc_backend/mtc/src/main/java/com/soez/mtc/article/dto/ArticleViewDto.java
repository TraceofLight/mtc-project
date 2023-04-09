package com.soez.mtc.article.dto;

import com.soez.mtc.alarm.entity.AlarmEntity;
import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.evaluate.entity.EvaluateValue;
import com.soez.mtc.hashtag.entity.HashtagEntity;
import com.soez.mtc.hashtagging.entity.HashtaggingEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleViewDto {

    private Long userIndex; // 게시물 작성자 인덱스
    private String userNickname;    // 게시물 작성자 닉네임
    private String userPictureSource;   // 게시물 작성자 프로필 사진
    private Long articleIndex;  // 게시물 인덱스
    private String articleTitle;    // 게시물 제목
    private String articlePictureSource;    // 게시물 사진
    private int articleHit; // 게시물 조회수
    private List<String> hashtagList;   // 게시물에 연결된 해시태그 목록
    private int goodCount; // ㅅㅌㅊ 수
    private int sosoCount; // ㅍㅌㅊ 수
    private int badCount;  // ㅎㅌㅊ 수
    private EvaluateValue evaluateValue;    // 게시물을 보는 사용자의 게시물 평가 [GOOD, SOSO, BAD, NONE(평가 안함)]

    @Builder
    public ArticleViewDto(Long userIndex, String userNickname, String userPictureSource, Long articleIndex, String articleTitle, String articlePictureSource, int articleHit, List<String> hashtagList, int goodCount, int sosoCount, int badCount, EvaluateValue evaluateValue) {
        this.userIndex = userIndex;
        this.userNickname = userNickname;
        this.userPictureSource = userPictureSource;
        this.articleIndex = articleIndex;
        this.articleTitle = articleTitle;
        this.articlePictureSource = articlePictureSource;
        this.articleHit = articleHit;
        this.hashtagList = hashtagList;
        this.goodCount = goodCount;
        this.sosoCount = sosoCount;
        this.badCount = badCount;
        this.evaluateValue = evaluateValue;
    }

}
