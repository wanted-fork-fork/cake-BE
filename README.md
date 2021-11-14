# CAKE - BackEnd

## 시스템구성도
![architecture](./reference/systemArchitecture.jpg)

## ERD
![erd](./reference/1113ERD.png)

## 기술스택
- SpringBoot 2.5.6: 백엔드 서버 프레임워크
- Spring Data JPA: 자바 진영 ORM 기술 표준
- Spring Security: 어플리케이션의 보안을 담당
- Spring Web: REST API 작성을 위한 도구를 제공
- JUnit5: 테스트 코드 작성 도구
- JWT: 인증/인가 도구로 토큰 방식을 사용
- Google SMTP: 회원가입에서 이메일 인증을 위해 Google SMTP를 사용
- ImgScalr: 이미지 업로드, 다운로드 시, 효율성을 위해 이미지 리사이징 과정을 거침
