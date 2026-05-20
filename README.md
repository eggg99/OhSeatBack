# 🎬 OhSeat

영화관 좌석 추천 정보를 공유할 수 있는 사용자 경험 기반 커뮤니티 서비스입니다.
사용자가 직접 관람한 좌석 후기를 공유하고, 지역 커뮤니티 및 이벤트 게시판을 통해 영화 관련 정보를 자유롭게 소통할 수 있도록 기획했습니다.

---

# 🚀 Tech Stack

### Backend

![Java](https://img.shields.io/badge/Java_17-007396?style=for-the-badge\&logo=openjdk\&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge\&logo=springboot\&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge\&logo=springsecurity\&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge\&logo=jsonwebtokens\&logoColor=white)
![MyBatis](https://img.shields.io/badge/MyBatis-BF1E2E?style=for-the-badge)

### Database

![MariaDB](https://img.shields.io/badge/MariaDB_10.11-003545?style=for-the-badge\&logo=mariadb\&logoColor=white)

### Frontend

![React](https://img.shields.io/badge/React-20232A?style=for-the-badge\&logo=react\&logoColor=61DAFB)

### Development Environment

![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000?style=for-the-badge\&logo=intellijidea\&logoColor=white)
![Visual Studio Code](https://img.shields.io/badge/VS_Code-007ACC?style=for-the-badge\&logo=visualstudiocode\&logoColor=white)
![Node.js](https://img.shields.io/badge/Node.js-5FA04E?style=for-the-badge\&logo=nodedotjs\&logoColor=white)

### Tools & Collaboration

![DBeaver](https://img.shields.io/badge/DBeaver-372923?style=for-the-badge)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge\&logo=postman\&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge\&logo=git\&logoColor=white)
![Discord](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge\&logo=discord\&logoColor=white)

---

# ✨ 주요 기능

### 🎯 좌석 추천 게시판

* 영화관 / 상영관 좌석 후기 작성
* 사용자 경험 기반 좌석 정보 공유

### 🏘 씨네광장 게시판

* 지역 기반 자유 커뮤니티 기능 제공
* 지역과 관계없이 전체 게시글 조회 가능

### 🎁 이벤트 게시판

* 시사회 및 예매 이벤트 정보 공유

### 👤 사용자 기능

* JWT 기반 로그인 / 인증
* 게시글 / 댓글 / 좋아요 기능 제공

### 👀 데이터 신뢰성 처리

* 조회수 중복 증가 방지 로직 적용
* 작성자 조회 제외 처리

---

# 🏗 System Architecture

```text
Client (React)
        ↓
Spring Boot API Server
        │
        ├── Spring Security
        │      └── JWT Filter Chain
        │              ├── 사용자 인증 처리
        │              └── 사용자 / 관리자 권한 분리
        │
        ├── Controller Layer
        │
        ├── Service Layer
        │      ├── 인증 / 인가 로직
        │      ├── 게시판 비즈니스 로직
        │      ├── 조회수 중복 방지 처리
        │      └── 파일 업로드 처리
        │
        ├── MyBatis Mapper
        │
        └── MariaDB
               ├── 사용자 도메인
               ├── 영화관 도메인
               │      └── 멀티플렉스 / 지역 / 영화관 / 상영관
               │
               ├── 게시판 도메인
               │      ├── 좌석추천 게시판
               │      ├── 씨네광장 게시판
               │      ├── 이벤트 게시판
               │      ├── 댓글
               │      └── 좋아요
               │
               ├── 조회수 관리
               └── 파일 관리
```

---

# 💡 Trouble Shooting

## 1. 조회수 중복 증가 문제

### 문제

게시글 조회 시마다 조회수가 증가하여
동일 사용자의 반복 요청으로 데이터가 왜곡되는 문제가 발생했습니다.

### 원인

조회 요청 자체를 조회수 증가 기준으로 사용하고 있었고,
사용자 검증 로직이 존재하지 않았습니다.

### 해결

* 작성자 본인 조회 시 조회수 증가 제외
* 동일 사용자 중복 조회 방지 로직 구현
* 프론트 요청 분리 + 백엔드 방어 로직 적용

### 결과

* 조회수 데이터 신뢰성 확보
* 비정상적인 조회수 증가 방지

---

## 2. JWT 인증 구조 개선

### 문제

기존 email 기반 인증 방식으로 인해
인증 과정에서 불필요한 사용자 조회가 발생했습니다.

### 원인

JWT 내부에 email을 저장하고 있어
매 요청마다 email 기반 조회가 필요했습니다.

### 해결

JWT에 userId를 저장하도록 구조를 변경하고,
userId 기반 사용자 조회 방식으로 개선했습니다.

### 결과

* 인증 로직 단순화
* DB 조회 효율 개선

---

## 3. 게시판 도메인 분리 설계

### 문제

좌석추천 / 씨네광장 / 이벤트 게시판을
하나의 테이블로 통합할지 고민했습니다.

### 원인

게시판 성격과 데이터 구조가 서로 달랐으며,
확장 시 조건 분기가 증가할 가능성이 있었습니다.

### 해결

게시판별 테이블을 분리하고,
댓글 및 좋아요 테이블도 각각 독립적으로 설계했습니다.

### 결과

* 도메인 책임 분리
* 유지보수성과 확장성 향상
* 게시판별 기능 확장 대응 가능

---

# 🗄 Database Modeling

* 멀티플렉스 > 지역 > 영화관 > 상영관 구조로 모델링
* 좌석추천 / 씨네광장 / 이벤트 게시판 테이블 분리
* 게시판별 댓글 / 좋아요 테이블 독립 설계
* 파일 첨부를 위한 files 테이블 설계
* 사용자 인증 및 관리를 위한 users 테이블 설계

---

# 👨‍💻 My Role

* 백엔드 API 설계 및 구현
* JWT 인증 / 인가 처리
* 데이터베이스 모델링 및 핵심 비즈니스 로직 구현
* 프론트엔드와 API 명세 기반 협업

---

# ▶️ 실행 방법

```bash
# 프로젝트 클론
git clone [repository-url]

# backend 실행
Spring Boot Application 실행

# frontend 실행
npm install
npm start
```
