## 프로젝트
Board 프로젝트

## 개발 기간
- 1.0.0 : 2024.09 ~ 2024.12
- 2.0.0(코드 전체 리펙토링) : 2025.09 ~

## 개발 환경
- Java 17
- Spring 3
- JPA
- Vue 3
- Composition API
- MariaDB 10.11
- IDE : IntelliJ, VS Code, DataGrip

## 주요 기능
- 게시판 회원 가입 및 로그인
- 게시글 등록, 수정, 삭제
- 자유게시판, 개발 / 스택 게시판 댓글 등록, 수정, 삭제
- 회원 등록 및 회원 정보 수정

## 리펙토링 이유
- JPA 의 연관관계를 사용해보기 위함
- Frontend 코드의 공통 컴포넌트화를 위함
- Backend 코드의 디자인을 맞추기 위함
- Entity 구조 변경하기 위함

## ERD
- 1.0.0 : common/erd/board-1.0.0.vuerd.json
- 2.0.0 : common/erd/board-2.0.0.vuerd.json

## Module
- 1.0.0 : api, ui, front 뒤에 1.0.0 이 붙음
- 2.0.0 : api, ui, front 뒤에 버전 정보가 없음
