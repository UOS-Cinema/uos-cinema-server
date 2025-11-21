## 목차

- [협업 환경](#협업-환경)
- [개발 환경](#개발-환경)
  - [프론트엔드](#프론트엔드)
  - [백엔드](#백엔드)
- [테스트 환경](#테스트-환경)
  - [Acceptance Test](#acceptance-test)
  - [Integration Test](#integration-test)
  - [Unit Test](#unit-test)

## 협업 환경

- Notion

## 개발 환경

### 프론트엔드

- Language: JavaScript
- Framework: React

### 백엔드

- Language: Java
- DB: Oracle 21c
- Framework: Spring Boot
- ORM: JPA

## 테스트 환경

### Acceptance Test

- Test Container를 사용하여 실제 서비스 환경 (Oracle 21c)과 동일한 테스트 환경 구축
- Acceptance Test: 사용자 시나리오 기반 End-to-End 테스트 진행

### Integration Test

- `WebEnvironment.RANDOM` 설정을 통해 실제 서버 통신 수행
- Service ~ Repository의 상호작용 검증을 위한 통합 테스트 진행

### Unit Test

- Service 로직의 유효성을 검증하기 위한 단위 테스트 진행