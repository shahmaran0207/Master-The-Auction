# 📣 Master-The-Auction 💰

<img src="https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/MTA.png" alt="WIT Banner" width="100%"/>

---

## 1️⃣ Project Overview (프로젝트 개요)

- **📝 프로젝트 이름**: **Master - The - Auction (MTA)**
- **📖 프로젝트 설명**:   실시간 경매 사이트입니다.
  - **판매자가 되어 경매물품 등록 가능**
  - **입찰에 참여하여 물건 낙찰받기 가능**
  - **기존 입찰가보다 높은 입찰가로 입찰 시, 기존 입찰한 사람에게 포인트 돌려주는 기능**
  - **각 판매자 및 게시판에 좋아요, 싫어요 기능**
  - **경매물품 후기를 공유**할 수 있는 커뮤니티 기능 제공

---

## 2️⃣ Team Members (팀원 및 팀 소개)

| 👩‍💻 이름   | 🎯 역할      | 🌍 GitHub 프로필                        |
| ---------- | --------- | -------------------------------------- |
| 박정은     | 기획 및 개발 |  [🔗 GitHub](https://github.com/shahmaran0207) |

---

## 3️⃣ Development Environment (개발 환경)

### 1. Front-End

| HTML | JavaScript | CSS |
|:----:|:----------:|:---:|
| <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/html5/html5-original.svg" alt="HTML5" width="60"/> | <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/javascript/javascript-original.svg" alt="JavaScript" width="60"/> | <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/css3/css3-original.svg" alt="CSS3" width="60"/> |

### 2. Back-End

| Spring Boot | Java | Firebase | ALB |   
|:-----------:|:----:|:--------:|:--------:|
| <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/spring/spring-original.svg" alt="Spring Boot" width="60"/> | <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="Java" width="60"/> | <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/firebase/firebase-plain.svg" alt="Firebase" width="60"/> | <img src="https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/ALB.png" alt="ALB" width="60"/> | 

### 3. Version Control

| GitHub |
|:------:|
| <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/github/github-original.svg" alt="GitHub" width="60"/> |

### 4. Deployment Environment

| RDS | EC2 | S3 | SSL/TLS | Route 53 | 
|:---:|:---:|:--:|:--------:|:--------:|
| <img src="https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/rds.png" alt="RDS" width="60"/> | <img src="https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/ec2.png" alt="EC2" width="60"/>| <img src="https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/s3.png" alt="S3" width="60"/> | <img src="https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/ssl.png" alt="ssl" width="60"/> |  <img src="https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/Route53.jpg" alt="Route53" width="60"/> | 

<br>

## 4️⃣ Special Technology

### 1. Firebase
- 기존보다 보안을 중요시하여 사용하였습니다.
- Firebase의 Authentication를 통해 이메일 / 비밀번호로 회원가입 및 로그인을 하며 DB에 직접 비밀번호를 저장하지는 않습니다.
- 이메일 중복을 위해 가입한 이메일인지 체크하고 Firebase의 비밀번호 자릿수를 체크하는 로직을 회원가입 페이지에 추가하였습니다.
  <br>

### 2. S3
- 사진 업로드 및 출력을 위해 사용하였습니다.
- 다운로드 시 업로드한 주소를 그래도 사용하니 접근이 되지 않아, 강제로 출력할 때 접근 주소를 변환하는 로직을 추가하였습니다.
  <br>

### 3. Redis
- 이메일 인증 등의 데이터를 캐싱하기 위해 Redis를 사용하였습니다.
- RedisTemplate 및 StringRedisTemplate을 활용하여 데이터 저장, 조회, 삭제, 만료 설정을 처리합니다.
- RedisUtil을 통해 키-값 데이터를 쉽게 관리할 수 있도록 구성하였습니다.

### 4.  이메일 전송
- Spring Boot의 JavaMailSender를 사용하여 이메일을 발송할 수 있도록 설정하였습니다.
- SMTP 서버 정보를 application.yml에서 설정하며, TLS 및 타임아웃 옵션을 적용하였습니다.
- 이메일 전송 시 보안성을 위해 SMTP 인증을 적용하였습니다.
  <br>

## 5️⃣Project Structure

```
📦project/
├── 📂 src/
│   └──  📂 main/
│       ├──  📂 java/
│       │   └──  📂 com/
│       │       └──  📂 Master/
│       │           └── 📂 Auction/
│       │               ├── AuctionApplication.java                                         (프로젝트 메인 클래스, 실행 진입점)
│       │               ├──  📂 config/                                                     (설정 파일 모음)
│       │               │   └──  📂  Email/                                                 (이메일 인증 관련 파일 모음)                
│       │               │   │   └── 📜  EmailConfig.java                                    (JavaMailSender를 설정하는 이메일 전송 관련 설정 파일)
│       │               │   │   └── 📜  RedisConfig.java                                    (Redis 연결을 설정하는 설정 파일)
│       │               │   │   └── 📜  RedisUtil.java                                      (Redis를 활용한 데이터 저장 및 조회를 위한 유틸리티 클래스)
│       │               │   │       
│       │               │   └──  📜  AuctionFileConfig.jva                                   (경매물품 이미지 업로드 경로 설정 - 로컬 작업 시 사용)
│       │               │   └──  📜  BoardFileConfig.jva                                     (게시판 이미지 업로드 경로 설정 - 로컬 작업 시 사용)
│       │               │   └──  📜  FirebaseConfig.jva                                      (firebase 초기화 파일)
│       │               │   └──  📜  MemberFileConfig.jva                                    (멤버 프로필 이미지 업로드 경로 설정 - 로컬 작업 시 사용)
│       │               │   └──  📜  QuestionFileConfig.jva                                  (문의사항 이미지 업로드 경로 설정 - 로컬 작업 시 사용)
│       │               │   └──  📜  S3Config.java                                           (AWS S3 관련 설정 - application.yml 에 있는 시크릿 키 밋 엑세스 키 적용)
│       │               │   └──  📜  WebConfig.java                                          (로그인 인터셉터 적용 페이지 설정)
│       │               │
│       │               ├──  📂 Controller/                                                  (컨트롤러: 요청 처리 및 뷰 반환)
│       │               │   └──  📂 Auction/   
│       │               │   │   └──  📜  AuctionController.java                              (경매 컨트롤러 - CRUD 및 페이지 반환)
│       │               │   │   └──  📜  BidController.java                                  (입찰 컨트롤러)
│       │               │   │
│       │               │   └──  📂 Board/      
│       │               │   │   └──  📜  BoardController.java                                (게시판 컨트롤러 - 페이지 반환 및 CRUD)
│       │               │   │   └──  📜  BoardHateController.java                            (게시글 싫어요 컨트롤러)
│       │               │   │   └──  📜  BoardLikeController.java                            (게시글 좋아요 컨트롤러)        
│       │               │   │   └──  📜  CommentController.java                              (게시글 댓글 컨트롤러 - CRUD)
│       │               │   │
│       │               │   └──  📂 Member/      
│       │               │   │   └──  📜  EmailController.java                                (이메일 인증 컨트롤러)
│       │               │   │   └──  📜  MemberCommentController.java                        (멤버 댓글 컨트롤러)
│       │               │   │   └──  📜  MemberController.java                               (멤버 컨트롤러 - 페이지 반환 및 CRUD)  
│       │               │   │   └──  📜  MemberHateController.java                           (멤버 싫어요 컨트롤러)
│       │               │   │   └──  📜  MemberLikeController.java                           (멤버 좋아요 컨트롤러)  
│       │               │   │   
│       │               │   └──  📂  PayMent/         
│       │               │   │   └──  📜  PayMentController.java                              (결제 컨트롤러)  
│       │               │   │
│       │               │   └──   📂 QnA/
│       │               │   │   └── 📂  Answer/       
│       │               │   │   │   └── 📜  AnswerController.java                            (답변 컨트롤러 - CRUD)
│       │               │   │   │        
│       │               │   │   └──  📂 Question/        
│       │               │   │         └── 📜 QuestionController.java                         (질문 컨트롤러 - CRUD) 
│       │               │   │
│       │               │   └──   📂 ControllerAdvice/            
│       │               │   │         └── 📜 GlobalControllerAdvice.java                     (모든 컨트롤러에서 사용되는 메서드 - HTTPOnly 쿠키 관련) 
│       │               │   │
│       │               │   └──  📜 HomeController.java                                      (메인 화면 구성을 위한 컨트롤러)
│       │               │
│       │               ├──  📂 DTO/                                                         (데이터 전송 객체)
│       │               │   └── 📂  Auction/   
│       │               │   │   └──  📜 AuctionDTO.java                                      (경매물품 데이터 모델)
│       │               │   │   └──  📜 BidDTO.java                                          (입찰 데이터 모델)
│       │               │   │   └──  📜 WinningDTO.java                                      (낙찰 데이터 모델)
│       │               │   │
│       │               │   └── 📂  Board/   
│       │               │   │   └──  📜 BoardDTO.java                                        (게시판 데이터 모델)
│       │               │   │   └──  📜 BoardHateDTO.java                                    (게시판 싫어요 데이터 모델)
│       │               │   │   └──  📜 BoardLikeDTO.java                                    (게시판 좋아요 데이터 모델)
│       │               │   │   └──  📜 CommentDTO.java                                      (게시판 댓글 데이터 모델)
│       │               │   │
│       │               │   └── 📂  Member/   
│       │               │   │   └──  📜 EmailDTO.java                                        (이메일 인증 데이터 모델)
│       │               │   │   └──  📜 MemberCommentDTO.java                                (멤버 댓글 모델)
│       │               │   │   └──  📜 MemberDTO.java                                       (멤버 데이터 모델)
│       │               │   │   └──  📜 MemberHateDTO.java                                   (멤버 싫어요 데이터 모델)
│       │               │   │   └──  📜 MemberLikeDTO.java                                   (멤버 좋아요 데이터 모델)
│       │               │   │
│       │               │   └──  📂 QnA/
│       │               │         └──  📂 Answer/       
│       │               │         │    └── 📜  AnswerDTO.java                                (답변 데이터 모델)
│       │               │         │
│       │               │         └──  📂 Question/       
│       │               │               └── 📜  QuestionDTO.java                             (질문 데이터 모델)
│       │               │
│       │               ├──  📂 Entity/                                                      (데이터베이스와 매핑되는 엔티티 클래스)
│       │               │   └──  📂 Auction/   
│       │               │   │   └──  📜 AuctionEntity.java                                   (경매물품 엔티티)
│       │               │   │   └──  📜 AuctionFileEntity.java                               (경매물품 이미지 엔티티)
│       │               │   │   └──  📜 BidEntity.java                                       (입찰 엔티티)
│       │               │   │   └──  📜 WinningBidEntity.java                                (낙찰 엔티티)
│       │               │   │
│       │               │   └──  📂 Board/   
│       │               │   │   └──  📜 BaseEntity.java                                      (게시판 기본 엔티티 - 생성, 수정 시간)
│       │               │   │   └──  📜 BoardEntity.java                                     (게시판 엔티티)
│       │               │   │   └──  📜 BoardFileEntity.java                                 (게시판 이미지 엔티티)
│       │               │   │   └──  📜 BoardHateEntity.java                                 (게시판 싫어요 엔티티)
│       │               │   │   └──  📜 BoardLikeEntity.java                                 (게시판 좋아요 엔티티)
│       │               │   │   └──  📜 CommentEntity.java                                   (게시판 댓글 엔티티)
│       │               │   │
│       │               │   └──  📂 Member/   
│       │               │   │   └──  📜 EmailEntity.java                                     (멤버 이메일 인증 엔티티)
│       │               │   │   └──  📜 MemberCommentEntity.java                             (멤버 댓글 엔티티)
│       │               │   │   └──  📜 MemberEntity.java                                    (멤버 엔티티)
│       │               │   │   └──  📜 MemberHateEntity.java                                (멤버 싫어요 엔티티)
│       │               │   │   └──  📜 MemberLikeEntity.java                                (멤버 좋아요 엔티티)
│       │               │   │   └──  📜 MemberProfileEntity.java                             (멤버 프로필 이미지 엔티티)
│       │               │   │
│       │               │   └── 📂  QnA/
│       │               │   │   └──  📂 Answer/       
│       │               │   │   │    └── 📜  AnswerEntity.java                               (답변 엔티티)
│       │               │   │   │
│       │               │   │   └── 📂  Question/       
│       │               │   │         └── 📜  BaseEntity.java                                (기본 엔티티 - 생성, 수정 시간)
│       │               │   │         └── 📜  QuestionEntity.java                            (질문 엔티티)
│       │               │   │         └── 📜 QuestionFileEntity.java                         (질문 이미지 엔티티)
│       │               │
│       │               ├── 📂  interceptor/
│       │               │   └──  📜 LoginInterceptor.java                                    (로그인 인터셉터)
│       │               │
│       │               ├── 📂  Repository /                                                 (데이터베이스 접근 객체)
│       │               │   └── 📂 Auction/   
│       │               │   │    └──  📜  AuctionFileRepository.java                         (경매 상품 이미지 파일 레퍼지토리)
│       │               │   │    └──  📜  AuctionRepository.java                             (경매 상품 레퍼지토리)
│       │               │   │    └──  📜  BidRepository.java                                 (입찰 레퍼지토리)
│       │               │   │    └──  📜  WinningBidRepository.java                          (낙찰 레퍼지토리)
│       │               │   │
│       │               │   └── 📂 Board/   
│       │               │   │    └──  📜  BoardFileRepository.java                           (게시판 이미지 파일 레퍼지토리)
│       │               │   │    └──  📜  BoardHateRepository.java                           (게시판 싫어요 레퍼지토리)
│       │               │   │    └──  📜  BoardLikeRepository.java                           (게시판 좋아요 레퍼지토리)
│       │               │   │    └──  📜  BoardRepository.java                               (게시판 레퍼지토리)
│       │               │   │    └──  📜  CommentRepository.java                             (게시판 댓글 레퍼지토리)
│       │               │   │
│       │               │   └── 📂 Member/   
│       │               │   │    └──  📜  EmailRepository.java                                (멤버 이메일 레퍼지토리)
│       │               │   │    └──  📜  MemberCommentRepository.java                        (멤버 댓글 레퍼지토리)
│       │               │   │    └──  📜  MemberHateRepository.java                           (멤버 싫어요 레퍼지토리)
│       │               │   │    └──  📜  MemberLikeRepository.java                           (멤버 좋아요 레퍼지토리)
│       │               │   │    └──  📜  MemberProfileRepository.java                        (멤버 프로필 이미지 레퍼지토리)
│       │               │   │    └──  📜  MemberRepository.java                               (멤버 레퍼지토리)
│       │               │   │
│       │               │   └──  📂 QnA/
│       │               │          └── 📂  Answer/       
│       │               │           └──  📜  AnswerRepository.java                             (답변 레퍼지토리)
│       │               │           │
│       │               │           └── 📂  Question/       
│       │               │            └──  📜  QuestionFileRepository.java                      (질문 이미지 파일 레퍼지토리)
│       │               │            └──  📜  QuestionRepository.java                          (질문 레퍼지토리)
│       │               │
│       │               └──  📂 Service/                                                       (비즈니스 로직)
│       │                    └── 📂 Auction/   
│       │                   │    └──  📜  AuctionService.java                                  (경매 상품 서비스)
│       │                   │    └──  📜  AuctionStatusScheduler.java                          (경매 상품 스케줄러 서비스)
│       │                   │    └──  📜  BidService.java                                      (입찰 서비스)
│       │                   │    └──  📜  WinningBidService.java                               (낙찰 서비스)
│       │                   │
│       │                    └── 📂 Board/   
│       │                   │    └──  📜  BoardHateService.java                                 (게시판 싫어요 서비스) 
│       │                   │    └──  📜  BoardLikeService.java                                 (게시판 좋아요 서비스) 
│       │                   │    └──  📜  BoardService.java                                     (게시판 서비스) 
│       │                   │    └──  📜  CommentService.java                                   (게시판 댓글 서비스) 
│       │                   │    
│       │                    └── 📂 Member/   
│       │                   │    └──  📜  EmailService.java                                     (이메일 인증 서비스) 
│       │                   │    └──  📜  MemberCommentService.java                             (멤버 댓글 서비스) 
│       │                   │    └──  📜  MemberHateService.java                                (멤버 싫어요 서비스)
│       │                   │    └──  📜  MemberLikeService.java                                (멤버 좋아요 서비스) 
│       │                   │    └──  📜  MemberService.java                                    (멤버 서비스) 
│       │                   │
│       │                   └── 📂  QnA/
│       │                   │ └── 📂  Answer/       
│       │                   │  │    └──  📜 AnswerService.java                                   (답변서비스)   
│       │                   │  │ 
│       │                   │  └──  📂 Question/       
│       │                   │        └── 📜  QuestionService.java                                (질문서비스)   
│       │                   │
│       │                   └──  📜 ImageService.java                                            (S3 이미지 서비스)   
│       │                   │
│       │                   └──  📜 S3Service.java                                               (S3 서비스)   
│       │               
│       │               
│       └──  📂 resources/
│           ├── 📂  static/
│           │   └──── 📂  assets/
│           │          ├──  📂 css/
│           │          │     └──  📜 style.css                                                    (공통 css 파일)   
│           │          │
│           │          └── 📂  js/      
│           │                └──  📜 membersave.js                                                (멤버 저장 자바스크립트 파일)      
│           │   
│           └──  📂 templates/
│           │   └──  📂 Auction/
│           │   │    └──  📜  AuctionList.html                                                    (본인이 등록한 경매 물품 목록 페이지) 
│           │   │    └──  📜  detail.html                                                         (경매물품 상세 페이지)
│           │   │    └──  📜  list.html                                                           (경매물품 목록 페이지)
│           │   │    └──  📜  save.html                                                           (경매물품 등록 페이지)   
│           │   │
│           │   └──  📂 Board/
│           │   │    └──  📜  detail.html                                                         (게시글 상세 페이지) 
│           │   │    └──  📜  list.html                                                           (게시글 목록 페이지) 
│           │   │    └──  📜  save.html                                                           (게시글 작성 페이지) 
│           │   │    └──  📜  update.html                                                         (게시글 수정 페이지) 
│           │   │    └──  📜  WriteList.html                                                      (본인이 작성한 게시글 목록 페이지) 
│           │   │
│           │   └──  📂 Member/
│           │   │    └──  📜  buyDetail.html                                                      (낙찰받은 경매 물품 상세 페이지)
│           │   │    └──  📜  buyList.html                                                        (낙찰받은 경매물품 목록 페이지)
│           │   │    └──  📜  detail.html                                                         (각 멤버 상세 페이지)
│           │   │    └──  📜  list.html                                                           (전체 멤버 목록 페이지)
│           │   │    └──  📜  login.html                                                          (로그인 페이지)
│           │   │    └──  📜  myPage.html                                                         (마이 페이지)
│           │   │    └──  📜  save.html                                                           (회원가입 페이지)
│           │   │    └──  📜  update.html                                                         (회원정보 수정 페이지) 
│           │   │
│           │   └──  📂 PayMent/
│           │   │    └──  📜  payment.html                                                        (경매 포인트 구매 페이지)
│           │   │
│           │   └──  📂 QnA/
│           │   │    └──  📜  detail.html                                                         (문의사항 상세 페이지)
│           │   │    └──  📜  list.html                                                           (문의사항 목록 페이지)
│           │   │    └──  📜  save.html                                                           (문의사항 답변 페이지)
│           │   │
│           │   └── 📜 home.html                                                                  (메인 페이지) 
│           │   └── 📜 header.html                                                                (헤더 파일) 
│           │   └── 📜 footer.html                                                                (푸터 파일) 
│           │   └── 📜 mail.html                                                                  (회원가입 인증코드 파일) 
│           │   
│           └── 🍃 application.yml                                                           
│           └── 🟣 service.json                                                                   (Firebase SDK 파일 - 현재는 폐쇄시킨 프로젝트로 보안에 문제없이 업로드 함) 
│
└── 🐋 Dockerfile                                                                                 (배포 위한 도커파일) 
└── 🐋 docker-compose.yml                                                                         (redis 컨테이너를 위한 도커파일) 
```

<br>

## 6️⃣ 신경 쓴 부분
### 1. Firebase
- 보안을 위해 Firebase를 사용하였습니다.
- 비밀번호를 직접 DB에 저장하지 않고 로그인시 사용되는 비밀번호만을 저장하여, 로그인 성공 시 Firebase에서 토큰을 받아오면 로그인에 사용한 이메일을 통해 개인정보를 DB에서 불러옵니다.

###  2. HttpOnly Cookie
- Firebase로부터 불러온 토큰과 사이트에 사용되는 이메일을 단순 세션이 아닌 HttpOnly Cookie에 저장함으로써 보안을 강화하였습니다.
-  HttpOnly Cookie를 사용함으로써 XSS 공격에 대한 보안성을 특히 강화하였습니다.
   <br>


###  3. HTTPS 배포
- HttpOnly Cookie 사용을 위해서는 HTTPS를 통한 배포가 필수적이었습니다.
- AWS의 EC2를 사용하여 배포할 경우 기본적으로 HTTP를 통해 배포가 됩니다.
- 이를 해결하기 위해 Route 53과 ALB, SSL/TLS 인증서를 사용 후, 도메인 주소를 구매 이를 통해 HTTPS로 배포하였습니다.
  <br>

### 4. S3
- 사진 업로드 및 출력을 위해 AWS의 S3를 사용하였습니다.
- 출력시 업로드한 경로 그대로 접근하니 접근제한이 되어, 강제로 주소를 변환하여 출력하였습니다.
  <br>

### 5. Interceptor
- 화이트리스트 방식: 모든 페이지를 막아두고 특정 페이지만 로그인 인터셉터에서 제외시키는 화이트리스트 방식을 사용하여 보안을 강화하였습니다.
  <br>

## 7️⃣ 페이지별 기능

### [초기화면]
- 헤더파일에 판매자 목록, 경매물품 목록, 문의사항, 로그인, 회원가입, 게시판을 선택할 수 있습니다.

| 초기화면 |
|----------|
|![first](https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/MTA.png)|
<br>

### [회원가입]
- 이메일 주소와 비밀번호를 입력하면 입력창에서 바로 유효성 검사가 진행됩니다
- 이메일을 다 입력하고 다른 곳을 클릭하면 즉시 DB에서 체크 후, 이미 가입한 이메일인지 확인합니다.
- 비밀번호의 경우 총 7자 이상이어야 가입이 가능하기에 글자수를 체크합니다.
- 비밀번호의 경우 다시한번 입력하도록 하여, 두 비밀호가 일치하는지 다시한번 체크합니다.
- 이메일 인증의 경우, 입력한 이메일로 발송된 인증번호를 인증해야 가입이 가능합니다.
- 이메일, 비밀번호의 유효성 검사 통과, 이메일 인증 및 이름까지 모두 입력해야 회원가입 버튼이 활성화됩니다.

| 회원가입 |
|----------|
|![save](https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/제목 없음.gif)|

<br>

### [로그인]
- 이메일 주소와 비밀번호를 입력 후, 로그인 버튼을 누르면 Firebase를 통해 회원가입한 이메일과 비밀번호와 일치하는지 체크합니다.
- 회원가입한 이메일과 비밀번호와 일치한다면 특정 토큰값을 반환하고 이를 세션에 저장합니다.
- 비밀번호는 따로 DB에 저장하지 않기에 로그인 페이지 내에서 직접 유효성 검사는 불가합니다.
- 로그인 성공 시, 메인 페이지로 이동합니다.

<br>

| 로그인 |
|----------|
|![login](https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/MTA_Login.gif)|

<br>

### [로그아웃]
- 상단 의 헤더에 있는 로그아웃 버튼을 클릭하면 로그아웃이됩니다.
- 로그아웃 시, 세션에 저장한 멤버 아이디, 이메일, 파이어베이스의 토큰 값 등이 전부 삭제됩니다.

<br>

# 상단 배너
- 상단 배너의 MTA 버튼을 클릭하면 언제든 홈 화면으로 돌아옵니다.
- 판매자목록을 누르면 모든 멤버 목록으로 이동합니다.
- 경매상품 / EXPLORE AUCTIONS를 선택하면 경매상품 목록으로 이동합니다.
- 커뮤니티를 누르면 자유게시판으로 이동합니다.
- 고객지원을 누르면 문의사항 목록으로 이동합니다.
- 각 페이지를 로그인하지 않은 상태에서 누르면 로그인 인터셉터에 의해 로그인 페이지로 이동합니다.

<br>

### [마이페이지]
- 개인 아이디, 이메일, 이름, 사진, 포인트 확인 가능하며, 프로필 사진이 없다면 사진 칸 자체가 뜨지 않습니다.

- 정보수정, 내가 작성한 글, 내가 낙찰받은 내역, 내가 등록한 경매 물품 목록 버튼이 있으며, 누르면 각 페이지로 이동합니다.
- 경매에 필요한 물품은 경매 포인트 구매 페이지에서 결제하면 충전 가능합니니다.


<br>

### [경매 상품 목록]
- 헤더에서 경매 상품을 누르면 경매중 / 경매가 끝난 후의 경매 상품들 목록을 확인할 수 있습니다.
- 경매 상태에 필터링을 걸어, 경매 중인 상품, 경매가 끝난 상품을 각각 따로 확인이 가능합니다.
- 글 작성을 누르면 경매 상품을 등록할 수 있습니다.

|경매상품 목록|
|-------------------|
|![auctionlist](https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/MTA_STATUS.gif)|


<br>


### [경매 상품 등록]
- 경매물품 목록에서 글 작성을 누르면 경매상품을 등록할 수 있습니다.
- 제목, 내용, 최초 입찰가, 경매 종료시간, 경매물품 사진을 선택한 후,  글 작성을 누르면 경매 물품이 등록됩니다.

|경매상품 등록|
|-------------------|
|![auctionlist](https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/MTA_REGIST_AUCTION.gif)|


<br>

### [경매 입찰]
- 경매 물품 페이지에서 경매 상품 아이디, 물품 명, 물품 설명, 판매자, 마감 시간, 현재 최고 입찰가를 확인 가능합니다.
- 현재 최고 입찰가 이상, 내가 가진 포인트 이하로 입력해야 입찰이 가능합니다.
- 해당 금액으로 입찰하면 현재 최고 입찰가가 본인이 입찰한 금액으로 변경됩니다.
- 경매 마감 시간 이후로는, 경매 상태가 자동으로 변경되며 더 이상 입찰이 불가합니다.

|경매 입찰|경매 입찰 종료 후|
|-------------------|-------------------|
|![onbid](https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/MTA_ONBID.gif)|![afterbid](https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/MTA_AFTERBID.gif)|


<br>

### [커뮤니티 목록]
- 상단 배너에서 커뮤니티를 클릭하면 자유게시판으로 이동합니다.
- 해당 페이지에서는 사람들이 자유롭게 글을 작성할 수 있으며, 경매 후기, 멤버 후기 등을 공유할 수 있습니다.
- 각 게시글의 아이디, 제목, 작성자, 작성시간, 조회수를 확인가능합니다.

<br>

### [커뮤니티 글 작성]
- 커뮤니티 목록 페이지에서 글 작성 버튼을 누르면 글 작성 페이지로 이동합니다.
- 제목, 암호, 내용, 파일을 선택하면 글을 작성할 수 있습니다.

| 자유게시판 - 글 작성 |
|----------|
|![communitywrite](https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/MTA_BOARD_WRITE.gif)|

<br>

### [커뮤니티 글 상세조회]
- 커뮤니티 목록 페이지에서 제목을 선택하면 게시글 상세조회가 가능합니다.
- 각 게시글의 아이디, 제목, 내용, 작성자, 작성시간, 조회수를 확인 가능합니다.
- 각 게시글의 좋아요, 싫어요, 댓글을 작성 가능합니다.
- 본인이 작성한 게시글의 경우 삭제, 수정버튼이 보이며, 타인이 작성한 글에는 보이지 않습니다.

| 자유게시판 - 글 상세조회|
|----------|
|![communitydetail](https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/MTA_BOARD_DETAIL.gif)|
<br>

### [문의사항 목록]
- 헤더에서 고객지원을 클릭하면 문의사항 목록 페이지로 이동합니다.
- 각 게시글의 아이디, 제목, 작성시간, 조회수, 답변 상태를 확인 가능합니다.
- 답변이 된 글은 초록색으로 '답변 완료', 답변이 되지 않은 글은 붉은 글씨로 '미답변'으로 표시되어 답변 여부를 확인가능합니다.

| 문의사항 목록|
|----------|
|![qnalist](https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/MTA_QNA_LIST.gif)|

<br>

### [문의사항 작성]
- 문의사항 목록에서 글 작성을 클릭하면 문의사항을 작성할 수 있습니다.
- 암호, 제목, 내용, 파일을 선택하면 글을 작성할 수 있습니다.
- 문의사항의 경우 사진이 있으면 더욱 자세히 확인이 가능하여 사진 첨부를 허용하였습니다.

| 문의사항 작성|
|----------|
|![qnawrite](https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/MTA_QNA_WRITE.gif)|

<br>

### [문의사항 싱세조회]
- 문의사항 목록에서 제목을 선택하면 문의사항 상세조회를 가능합니다.
- 각 문의사항 아이디, 제목, 내용,작성자, 작성시간, 조회수, 사진을 확인 가능합니다.
- 답변의 경우 최종 관리자만 확인 가능하며, 본인이 작성한 게시글의 경우 삭제 버튼이 보여 문의사항을 삭제할 수 있습니다.

| 문의사항 싱세조회|
|----------|
|![qnadetail](https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/MTA_QNA_ANSWER.gif)|

<br>

### [경매 포인트 구매]
- 마이페이지에서 경매 포인트 구매 버튼을 클릭하면 포인트 충전 페이지로 이동합니다.
- 구매할 포인트(100 이상)을 입력하고 충전하기 버튼을 누르면 1포인트당 1원으로 구매할 수 있습니다.
- 충전 포인트를 입력하지 않으면 충전 하기를 누를 수 없습니다.
- 성공적으로 충전하면 마이페이지에서 구매한 포인트만큼 본인의 포인트가 증가합니다.

| 경매 포인트 구매|
|----------|
|![payment](https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/MRA_PAYMENT.gif)|
<br>

### [내가 낙찰받은 내역]
- 마이페이지에서 내가 낙찰받은 내역 버튼을 누르면 내가 낙찰받은 내역을 확인할 수 있습니니다.

<br>

### [내가 등록한 경매물품]
- 마이페이지에서 내가 등록한 경매물품 버튼을 누르면 내가 등록한 경매물품 목록을 확인할 수 있습니니다.


<br>

### [내가 작성한 글]
- 마이페이지에서 내가 작성한 글 버튼을 누르면 내가 작성한 글 목록을 확인할 수 있습니니다.


<br>

### [개인정보 수정]
- 마이페이지에서 비밀번호 변경을 누르면 개인정보 수정이 가능합니다.
- 이름, 비밀번호, 프로필 사진을 변경할 수 있습니다.
- 암호는 변경하지 않아도 반듯시 입력해야 하며, 이름 혹은 사진은 변경하고 싶을 때만 변경하면 됩니다.

| 개인정보 수정|
|----------|
|![MTA_CHANGE](https://github.com/shahmaran0207/Master-The-Auction/blob/main/Readme_images/MTA_CHANGE.gif)|

<br>

## 9. 개선 목표
### 1. 웹 소켓을 통한 채팅
- 관리자 혹은 챗봇과의 채팅 기능을 추가하여, 에러 사항을 즉시 반영 혹은 수정할 수 있도록 업데이트할 예정입니다.

### 2. 로그인 방식 개선
- 현재 HttpOnly Cookie를 통해 보안성을 기존보다 강화시킨 상태입니다.
- 여기에 JWT 혹은 OAuth등의 방식을 추가하여, 보안성을 강화할 계획입니다.

**⚠ 중요 사항 ⚠**  
업로드한 **RDS, S3, Firebase, SMTP의 암호**는  
모두 **프로젝트 제거** 혹은 **서비스 종료 후 업로드**하였습니다.
