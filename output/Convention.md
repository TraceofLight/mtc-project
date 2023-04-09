# Conventions

## ⚙개발 환경

- **Frontend**
    - Java Script
    - React
    - Node.js : 16.18.1
- **Backend**
    - Project : Gradle - Groovy
    - Language : Java 11
    - Spring boot : 2.7.7
    - Packaging : Jar
    - Dependencies
    ➡ Spring Web
    ➡ Lombok
    ➡ MySQL Driver
    ➡ Spring Data JPA
    ➡ Spring Boot DevTools
    
- **DataBase**
    
    
- **Server**

# 깃 컨벤션

### 커밋 메세지의 7가지 규칙

1. **제목과 본문을 빈 행으로 구분**합니다.
2. **제목을 50글자 이내로 제한**합니다.
3. **제목의 첫 글자는 대문자로 작성**합니다.
4. **제목의 끝에는 마침표를 넣지 않습**니다.
5. **제목은 명령문으로! 과거형을 사용하지 않습**니다.
6. **본문의 각 행은 72글자 내로 제한**합니다.
7. **어떻게 보다는 무엇과 왜를 설명**합니다.

## 깃 명령어

git branch <branch name> : <branch name>을 로컬 저장소에 생성

git push origin <branch name> : 해당 <branch name>을 원격 저장소로 push

git branch -r : 원격 저장소 branch 목록

git checkout -b <branch name> : 원격 저장소의 <branch name>을 로컬 저장소로 pull

### branch 전략

![branch](output/imgs/branch.png)

- Master
- Release
- Develop
- Feature
- Hotfix

# 📔 Coding Convention

---

구글 코딩 컨벤션 : [https://google.github.io/styleguide/javaguide.html](https://google.github.io/styleguide/javaguide.html)

네이버 캠퍼스 핵데이 컨벤션 : [캠퍼스 핵데이 Java 코딩 컨벤션](https://naver.github.io/hackday-conventions-java/)

프론트엔드 스타일 가이드(에어비앤비) : [자바스크립트](https://github.com/ParkSB/javascript-style-guide), [리액트](https://github.com/apple77y/javascript/tree/master/react)

# 📌 Frontend

| 이름 | 설명 | 예시 |
| --- | --- | --- |
| 파일명 | Pascal | EduSsafy.jsx |
| 인스턴스 | Camel | eduSsafy |
|  |  |  |
|  |  |  |
|  |  |  |

1. 변수를 할당할 때 var 대신 const, let 사용한다.
2. 컴포넌트명은 파일명과 동일하게 작성한다.

# 📌 Backend

| 이름 | 설명 | 예시 |
| --- | --- | --- |
| 프로젝트명 | 모두 소문자 | mtc |
| 패키지 | 모두 소문자 | com.ssafy.common |
| 파일명 | Pascal | CamelCase |
| 클래스 | Pascal | CamelCase |
| 변수 | Camel | camelCase |
| 메소드 | 동사+목적어+전명구 | findNameById |
|  |  |  |
| CRUD | Repo | Service |
| C | create |  |
| R | read |  |
| U | update |  |
| D | delete |  |
|  |  |  |
|  |  |  |
|  |  |  |
|  |  |  |
|  |  |  |

## 1. 경로는 필드별로 묶는다.

member - controller
service
board - controller  service
service

## 2. 변수는 개행하여 적는다.

int a , b; (x) int a;
int b;

## 3. for문과 if문은 반드시 블록으로 감싼다.

for(int a : number) { System.out.println(a) } (o)

for(int a : number) System.out.println(a) (x)

## 4. lombok사용

## 5. 패키지 명은 com.soez.mtc.* 로 설정한다.

## 6. 각 메소드 사이는 한 줄만 개행한다.

## 7. 주석 : package

                 import

           /* 클래스 설명 */ →ex) 회원관련 클래스다. 로그인 , 회원가입… + 마지막 수정 날짜

     class 클래스명

     /* 메서드 설명*/  → ex) 회원가입 메서드. 아이디와 비밀번호를 입력받아 유효성 검사 후 

                                     회원가입 진행

void 메서드명()
