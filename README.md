# 영화예매 프로젝트

생성일: 2024년 11월 20일 오후 11:21

# 빌드방법
<pre>
    1. develop 브랜치 git pull
    2. mysql 설치 진행
    3. application.properties mysql 정보 수정
    4. ./gradlew clean bootJar
    5. java -jar MovieProject.jar (실행)
</pre>

# 인프라 구성
![image](https://github.com/user-attachments/assets/5e1c7ab8-d641-4111-ae8b-ef09b6724ed3)



# 개발 환경
![image 1](https://github.com/user-attachments/assets/d6d30b44-88f1-491b-bcb1-295b710b6e12)


# Git

- Organization 생성
    - 팀원 초대
    - repository 생성
    - 초기 springboot push ( dependency + [application.properties](http://application.properties) )
- GitFlow
    - Main - develop - feature(local PC)
        - Main : 대규모 기능별 머지
        - Develop : 소규모 기능별 머지
        - Feature : 로컬 PC 기능 개발

<img width="705" alt="GitFlow%E1%84%80%E1%85%AE%E1%84%89%E1%85%A5%E1%86%BC" src="https://github.com/user-attachments/assets/6fd2c5d8-0a11-4305-8cc5-43c87c05682c">

            
- CommitMessage
    
<img width="469" alt="Commit_Message_%E1%84%8C%E1%85%A1%E1%86%A8%E1%84%89%E1%85%A5%E1%86%BC%E1%84%87%E1%85%A1%E1%86%BC%E1%84%87%E1%85%A5%E1%86%B8" src="https://github.com/user-attachments/assets/833e5a9a-cd7e-4293-a181-2d90398d8c32">

    
 # API Doc
    
| **기능**                                   | **HTTP 메서드** | **API Path**                                 | **Request**                                                | **Response**                                               |
|--------------------------------------------|----------------|----------------------------------------------|------------------------------------------------------------|------------------------------------------------------------|
| 네이버 로그인                              | GET            | /naver/callback                             |                                                            |                                                            |
| 로그인                                     | POST           | /login                                      |                                                            |                                                            |
| 로그아웃                                   | POST           | /logout                                     |                                                            |                                                            |
| 회원가입                                   | POST           | /api/members/join                           | 📜 [링크1](https://www.notion.so/153cc60c603b8147861fcd4f8ab7074a?pvs=21) | 📜 [링크2](https://www.notion.so/153cc60c603b81109a17f409ed87d497?pvs=21) |
| 로그인한 회원정보 가져오기                | GET            | /api/me                                     |                                                            | 📜 [링크](https://www.notion.so/153cc60c603b81109a17f409ed87d497?pvs=21) |
| 나의 회원 정보 수정                        | PUT            | /api/me/update                              | 📜 [링크](https://www.notion.so/153cc60c603b81d1b72ad5e62c905736?pvs=21) | 📜 [링크](https://www.notion.so/153cc60c603b81109a17f409ed87d497?pvs=21) |
| 비밀번호 변경                              | PUT            | /api/me/change-password                     | 📜 [링크](https://www.notion.so/153cc60c603b817a9e05f63029d4a89e?pvs=21) |                                                            |
| 로그인한 계정 탈퇴                         | DELETE         | /api/me/delete                              | String password                                            |                                                            |
| 영화 목록 조회                             | GET            | /api/movies/status/{status}                 | URL {status} 부분에 조회 컬럼 입력                        | 📜 [링크](https://www.notion.so/153cc60c603b81f4b291c0d5c16cc131?pvs=21) |
| 영화 목록 조회 (예매 시 사용)              | GET            | /api/movies/available                       | 📜 [링크](https://www.notion.so/153cc60c603b8190b725f71e8f4ec765?pvs=21) | 📜 [링크](https://www.notion.so/153cc60c603b81afa6c6f974454f4a02?pvs=21) |
| 영화 상세 조회                             | GET            | /api/movies/{id}                            | URL {id} 부분에 영화 id 입력                              | 📜 [링크](https://www.notion.so/153cc60c603b81f4b291c0d5c16cc131?pvs=21) |
| 영화 시간표 정보 조회                     | GET            | /api/schedules                              | 📜 [링크](https://www.notion.so/153cc60c603b818d96aac1a30ac50e38?pvs=21) | 📜 [링크](https://www.notion.so/153cc60c603b81d48109d0969e08968a?pvs=21) |
| 영화관 목록 가져오기                      | GET            | /api/theaters                               |                                                            | 📜 [링크](https://www.notion.so/153cc60c603b81f4b291c0d5c16cc131?pvs=21) |
| 좌석 목록 가져오기                        | GET            | /api/seats/available                        | 📜 [링크](https://www.notion.so/153cc60c603b81d18206cef9bb59044c?pvs=21) | 📜 [링크](https://www.notion.so/153cc60c603b8170b9e6ead8307ea19c?pvs=21) |
| 영화 예매하기                              | POST           | /api/reservations/create                    | 📜 [링크](https://www.notion.so/153cc60c603b8161a2b9c27446fad50c?pvs=21) | 📜 [링크](https://www.notion.so/153cc60c603b814489e2da9b084ea097?pvs=21) |
| 영화 좋아요 누르기                         | POST           | /api/likes/doLike/{movieId}                 | 📜 [링크](https://www.notion.so/153cc60c603b8138a3b7f9150d297308?pvs=21) | 📜 [링크](https://www.notion.so/153cc60c603b8173881dc4ee9fe14022?pvs=21) |
| 좋아요 취소                               | POST           | /api/likes/doUnlike/{movieId}               | 📜 [링크](https://www.notion.so/153cc60c603b8138a3b7f9150d297308?pvs=21) | 📜 [링크](https://www.notion.so/153cc60c603b8173881dc4ee9fe14022?pvs=21) |
| 좋아요한 목록                 | GET            | `/api/likes/myLikes`                              |                                                | 📜 [Notion 링크](https://www.notion.so/153cc60c603b8173881dc4ee9fe14022?pvs=21) |
| 선택한 영화 좋아요 개수       | GET            | `/api/likes/count/{movieId}`                      |                                                | 📜 [Notion 링크](https://www.notion.so/153cc60c603b8173881dc4ee9fe14022?pvs=21) |
| 좋아요 여부 확인              | GET            | `/api/likes/check/{movieId}` 
| 1:1 문의 작성           | POST           | `/api/board/write`                                       | 📜 [Notion Link](https://www.notion.so/153cc60c603b818f9ab2cc294b1eb359?pvs=21)          | 📜 [Notion Link](https://www.notion.so/153cc60c603b81e9871bfb29390381cd?pvs=21)          |
| 1:1 문의 작성 첨부파일 포함 | POST           | `http://localhost:8080/swagger-ui/index.html#/board-controller/writeListFileapi/board/writeFile` | 📜 [Notion Link](https://www.notion.so/153cc60c603b818f9ab2cc294b1eb359?pvs=21)          | 📜 [Notion Link](https://www.notion.so/153cc60c603b81e9871bfb29390381cd?pvs=21)          |
| 작성한 문의 조회        | GET            | `/api/board/lists/myList`                                |                                                   | 📜 [Notion Link](https://www.notion.so/153cc60c603b81e9871bfb29390381cd?pvs=21)          |
| 선택한 게시물 조회      | GET            | `/api/board/lists/{id}`                                  |                                                   | 📜 [Notion Link](https://www.notion.so/153cc60c603b81e9871bfb29390381cd?pvs=21)          |
| 첨부파일 다운로드       | GET            | `/api/board/download/{id}`                               |                                                   | 📜 [Notion Link](https://www.notion.so/153cc60c603b81e9871bfb29390381cd?pvs=21)          |
| 리뷰 작성              | POST           | `/api/review/write`                                      | 📜 [Notion Link](https://www.notion.so/153cc60c603b81659d57c67d2129da2b?pvs=21)          | 📜 [Notion Link](https://www.notion.so/153cc60c603b81999be3ec04ba29250b?pvs=21)          |
| 리뷰 수정              | POST           | `/api/review/update/{id}`                                | 📜 [Notion Link](https://www.notion.so/153cc60c603b81659d57c67d2129da2b?pvs=21)          | 📜 [Notion Link](https://www.notion.so/153cc60c603b81999be3ec04ba29250b?pvs=21)          |
| 현재 영화의 리뷰 조회    | GET            | `http://localhost:8080/swagger-ui/index.html#/review-controller/getLists` |                                                   | 📜 [Notion Link](https://www.notion.so/153cc60c603b81999be3ec04ba29250b?pvs=21)          |
| 본인이 작성한 리뷰       | GET            | `/api/review/lists/myList`                              |                                                   | 📜 [Notion Link](https://www.notion.so/153cc60c603b81999be3ec04ba29250b?pvs=21)          |
| 리뷰 삭제              | POST           | `/api/review/delete/{id}`                                |                                                   | 📜 [Notion Link](https://www.notion.so/153cc60c603b81999be3ec04ba29250b?pvs=21)          |
| 상품 조회              | GET            | `/api/items`                                             |                                                   | 📜 [Notion Link](https://www.notion.so/153cc60c603b813bbeb0e1dd76b36b28?pvs=21)          |
| 상품 개별 조회          | GET            | `/api/items/{id}`                                        |                                                   | 📜 [Notion Link](https://www.notion.so/153cc60c603b813bbeb0e1dd76b36b28?pvs=21)          |
| 장바구니 조회           | GET            | `/api/cart`                                              |                                                   | 📜 [Notion Link](https://www.notion.so/153cc60c603b818f94f7f08e259446d1?pvs=21)          |
| 장바구니 추가           | POST           | `/api/cart`                                              | 📜 [Notion Link](https://www.notion.so/153cc60c603b81c984efcbc21f5d010b?pvs=21)          | 📜 [Notion Link](https://www.notion.so/153cc60c603b81c984efcbc21f5d010b?pvs=21)          |
| 장바구니 업데이트        | PUT            | `/api/cart`                                              | 📜 [Notion Link](https://www.notion.so/153cc60c603b81c984efcbc21f5d010b?pvs=21)          | 📜 [Notion Link](https://www.notion.so/153cc60c603b818f94f7f08e259446d1?pvs=21) / [Notion Link](https://www.notion.so/153cc60c603b81c6a423dda30cf129f2?pvs=21) |
| 장바구니 구매           | POST           | `/api/pay/cart/purchase/create`                        | 📜 [Notion Link](https://www.notion.so/153cc60c603b813e84c7e225de002af0?pvs=21)          | 📜 [Notion Link](https://www.notion.so/153cc60c603b8163844bfac9859dc63f?pvs=21)          |
| 상품 직접 구매          | POST           | `/api/pay/direct/purchase/create`                      | 📜 [Notion Link](https://www.notion.so/153cc60c603b81839b07c4d86d2028ce?pvs=21)          | 📜 [Notion Link](https://www.notion.so/153cc60c603b8163844bfac9859dc63f?pvs=21)          |
| 결제 성공              | POST           | `/api/pay/payment/complete`                            | 📜 [Notion Link](https://www.notion.so/153cc60c603b81668db4e5e195154e44?pvs=21)          | 📜 [Notion Link](https://www.notion.so/153cc60c603b8103b4a6f19f1beb0954?pvs=21)          |
| 결제 항목 개별 조회      | GET            | `/api/pay/{payCode}`                                    |                                                   | 📜 [Notion Link](https://www.notion.so/153cc60c603b81ed8564d690348be9b4?pvs=21)          |
| 결제 항목 전체 조회      | GET            | `/api/pay`                                              |                                                   | 📜 [Notion Link](https://www.notion.so/153cc60c603b81ed8564d690348be9b4?pvs=21)          |
| 결제 취소              | PUT            | `/api/pay/cancel/{payCode}`                            |                                                   | 📜 [Notion Link](https://www.notion.so/153cc60c603b81ed8564d690348be9b4?pvs=21)          |
| 쿠폰 전체 조회          | GET            | `/api/coupon`                                           |                                                   | 📜 [Notion Link](https://www.notion.so/153cc60c603b819c854ef22bd103fd45?pvs=21)          |
| 쿠폰 개별 조회          | GET            | `/api/coupon/{couponId}`                                |                                                   | 📜 [Notion Link](https://www.notion.so/153cc60c603b819c854ef22bd103fd45?pvs=21)          |
| 쿠폰 사용              | PUT            | `/api/coupon/{couponId}/use`                           |                                                   | 📜 [Notion Link](https://www.notion.so/153cc60c603b819c854ef22bd103fd45?pvs=21)          |
| 쿠폰 삭제              | DELETE         | `/api/coupon/{couponId}`                                |                                                   |                                                                                               |


# 기능 개발
    
 - 로그인기능 (spring security + Oauth2.0 네이버)
 - 회원관리 (회원가입, 회원정보 수정)
 - 영화예매 기능 ( 영화관조회, 영화 조회, 영화 예매, 결제 )
 - 스토어 기능 ( 쿠폰 및 팝콘 구매, 장바구니, 결제 )
 - 게시판 기능(고객센터 1:1, 파일업로드)

# 컨벤션
<pre>

1. Indent 규칙
  * 공백 (space bar) 4칸
  * 중괄호는 같은 줄에 열고, 코드 블록이 끝나는 부분에 닫기

EX)
  if ( [조건] ) {
  (4칸)// body
  }

2. 클래스 규칙
  * 파스칼 케이스 적용

  파스칼 케이스 (Pascal Case)
  - 클래스명은 대문자로 시작하고 각 단어의 첫글자도 대문자로 표기
  - 인터페이스 이름은 형용사 형태로 짓기 가능

  Ex) 
    UserAccount

3. 네이밍 규칙
  * 카멜케이스 적용 (변수이름, 메서드 ... )
  
  카멜 케이스 (Camel Case)
  - 맨 앞 단어의 첫 철자를 소문자로 시작하되, 그 다음 이어지는 단어의 첫 철자를 대문자로 표기하는 방식

  Ex) 
    autoHandle

4. 상수 규칙
  * 상수의 경우 모두 대문자로 설정
  * 매직넘버 사용 금지 ( 숫자나, 문자열과 같은 리터럴 값을 바로 사용하는 것 피하기, 의미있는 이름을 가진 상수로 대체 )
  
  EX)
    public static final double PI = 3.14;

5. 문장 길이  
  * 한 줄의 최대 길이는 100자 이내로 작성 (넘을경우, 적절한 위치에서 줄바꿈)
  EX)
    @GetMapping("/test")
    public String func1 ( 
          @RequestParam("test1") String test,
          @RequestParam("test2") String test2,
          @RequestParam("test3") String test3) {

         //body
         return "[테스트페이지]";
    }

</pre>


