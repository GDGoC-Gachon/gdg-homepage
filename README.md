# 🌐 GDG Homepage Server

> GDGoC GACHON의 공식 홈페이지 백엔드 서버입니다.

<img width="1130" alt="스크린샷 2025-05-24 12 46 45" src="https://github.com/user-attachments/assets/2f42e86b-821f-4a07-91fd-bdf996298304" />

---

## 📦 프로젝트 소개

`gdg-homepage`는 GDGoC GACHON 커뮤니티를 위한 홈페이지의 **백엔드 시스템**입니다.
회원가입, 인증, 게시판, 페이지 뷰 등 주요 기능을 API로 제공합니다.

---

## ⚙️ 기술 스택

- **Java 17**
- **Spring Boot 3**
- **Spring Security (JWT 인증)**
- **Spring Data JPA**
- **MySQL**
- **Redis**
- **JavaMailSender (Thymeleaf 템플릿)**
- **Swagger (API 문서화)**

---

## 📧 이메일 템플릿
이메일은 Thymeleaf 템플릿을 사용하여 HTML 형식으로 발송됩니다.
<img width="895" height="504" alt="스크린샷 2025-12-07 14 02 43" src="https://github.com/user-attachments/assets/9704e18a-7ae9-455d-9e6b-1dd08f715cdc" />

---

## ✨ 주요 기능

### 1. 🔐 인증 시스템
- JWT 기반 Access Token (1일) / Refresh Token (7일)
- HTTP-Only Cookie를 통한 보안 토큰 관리
- Redis를 활용한 Refresh Token 저장 및 관리

### 2. 📧 이메일 시스템
- **이메일 인증 코드 발송** (회원가입 시)
- **비밀번호 재설정 링크 발송**
- Thymeleaf 템플릿 기반 HTML 이메일
- 인증 코드 30분 유효기간 (자동 만료 처리)
- 매일 정오 만료된 인증 코드 자동 삭제 (Scheduled)

### 3. 👥 회원 관리
- 회원가입 / 로그인 / 로그아웃
- 비밀번호 변경 및 재설정
- 회원 탈퇴 (Soft Delete 방식)
- 마이페이지 조회

### 4. 🛡️ 관리자 페이지

#### 4.1 회원 관리
- **회원 목록 조회** (페이징 지원)
- **회원 상세 정보 조회**
- **가입 신청 목록 조회** (미승인 회원)
- **회원 승인/거절 처리**
- **권한 변경** (MEMBER → TEAM_MEMBER → ORGANIZER)

#### 4.2 가입 기간 관리
- **가입 기간 생성** (제목, 시작일, 종료일, 최대 인원)
- **가입 기간 수정**
- **가입 기간 조회** (전체 목록)
- **가입 기간 종료** (상태 변경)

#### 4.3 통계 대시보드
- **총 멤버 수** (신규 가입자 증가 포함)
- **가입 신청 수** (신청 증가 포함)
- **탈퇴 회원 수** (탈퇴 증가 포함)
- **페이지 뷰 수** (페이지 뷰 증가 포함)
- **인기 역할(Stack) 조회**

#### 4.4 페이지 뷰 추적
- 홈페이지 방문 시 페이지 뷰 카운트 증가

---

## 📄 라이선스

이 프로젝트는 GDGoC GACHON의 소유입니다.
