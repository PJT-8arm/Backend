# 💪운동(헬스)을 같이 할 친구 찾는 서비스 팔뚝(8Arm)

- 배포 URL : https://app.genj.me
- Test ID : user1
- Test PW : 1234

<br>

## 프로젝트 소개

- 팔뚝은 헬스 운동을 하는 사람들에게 운동 파트너를 찾게 해주는 웹서비스입니다.
- 사용자는 자신의 운동 루틴, 지역 등 내용 모집글에 작성하여 운동을 함께할 파트너를 찾을 수 있습니다.
- 사용자들의 작성자의 글과 프로필을 확인하고 관심있는 사람과 채팅을 통해 운동 약속을 잡을 수 있습니다.


<br>

## 팀원 구성

<div align="center">

| **전희영** | **박태훈** | **서상호** | **임유빈** | **현진영** |
| :------: |  :------: | :------: | :------: | :------: |


</div>

<br>

## 1. 개발 환경
![image](https://github.com/PJT-8arm/Backend/assets/148295292/a3c6ab8a-244a-452d-819f-c59989ae9ed7)
![image](https://github.com/PJT-8arm/Backend/assets/148295292/0312e134-0703-49e3-849a-4c73a567e693)
![image](https://github.com/PJT-8arm/Backend/assets/148295292/5112af56-5e87-40dc-996b-cd1657146dd2)
![image](https://github.com/PJT-8arm/Backend/assets/148295292/b7d93698-b364-49c4-a3d2-5455b495e140)
![image](https://github.com/PJT-8arm/Backend/assets/148295292/1981b49e-647e-4460-aa86-a5dfa1285b1c)

<br>

## 2. 채택한 개발 기술과 브랜치 전략

### 브랜치 전략

- main, develop, Feat 브랜치로 나누는 git flow 전략을 사용하여 개발을 하였습니다.
    - **main** 브랜치는 배포 단계에서만 사용하는 브랜치입니다.
    - **develop** 브랜치는 다음 버전 개발을 위한 코드를 모아두는 브랜치입니다. 개발이 완료되면, Main 브랜치로 머지됩니다.
    - **Feat** 브랜치는 기능 단위로 나누어 개발하기 위해 사용하고 merge하고 난 후에는 브랜치를 삭제해주었습니다.

<br>

## 3. 이벤트 스토밍, ERD
![image](https://github.com/PJT-8arm/Backend/assets/148295292/a654418a-d8e7-4ec8-8276-f4c4df115c1a)

![image](https://github.com/PJT-8arm/Backend/assets/148295292/df677a33-6d4f-46c7-9b7b-f533d083fa2b)

- 사용자 경험 중심 흐름 파악
  - 팔뚝 서비스의 사용자 경험을 깊이 있게 탐구
  - 회원가입, 모집글 작성, 프로필 검색, 채팅 및 약속 잡기 등 주요 사용자 흐름을 면밀히 분석

- 이해관계자들의 생각과 경험 공유
  - 팀원들이 협업하여 이벤트 스토밍 기법을 사용
  - 사용자 관점에서 일어나는 다양한 이벤트들을 정의하고 서비스에 대한 지식과 생각을 공유

- 도메인 모델링 및 ERD 작성
  - 이벤트 스토밍 결과를 바탕으로 팔뚝의 도메인 모델을 정의
  - 개체간 관계와 속성을 면밀히 고려하여 데이터베이스 설계의 기반이 되는 ERD를 작성



## 4. 역할 분담

### 전희영

- 팀장으로 프로젝트 관리를 맡음.
- BE/FE Mypage 구현, 배포 및 파이프라인 구성을 담당

<br>

### 박태훈

- 사용자간 소통을 위한 메시지 기능을 담당하는 팀원
- 웹 소켓을 이용한 통신을 기반으로 웹앱 및 서버의 메시지 기능을 구현
- 웹앱의 UI 구성에 기여

<br>

### 서상호

- 모집글을 담당하는 팀원
- React, Spring Boot와 JPA를 활용하여 서버의 모집글 구현을 담당

<br>

### 임유빈

- 팀의 문서 관리 및 서비스 로직 구현 담당
- Spring boot를 활용하여 모집글 구현에 기여
- 서버와 웹앱 연동하고 컨텍스트를 이용하여 사용자의 권한을 담당

<br>

### 현진영

- UI 디자인 및 서버의 보안 담당
- Figma툴을 이용하여 웹앱의 UI의 프로토타입 디자인을 작성하고 서버의 보안(Spring Security/Oauth 2.0) 설정 및 구현을 담당


<br>

## 5. 개발 기간 및 작업 관리

### 개발 기간

- 전체 개발 기간 : 2024-02-26 ~ 2024-03-29
- 백엔드 구현 : 2024-02-26 ~ 2024-03-15
- 프론트 구현 : 2024-03-15 ~ 2024-03-29

<br>

### 작업 관리

- GitHub Projects와 Issues를 사용하여 진행 상황을 공유했습니다.
  ![image](https://github.com/PJT-8arm/Backend/assets/148295292/b17463a5-1d14-4866-9bce-ac66b43f2732)

- 주간회의를 진행하며 한주간에 있던 이슈들을 정리하고 노션에 정리했습니다.

<br>

## 6. 핵심 기능

### [모집글 작성]
- 사용자는 자신의 운동 스케줄, 능력, 시간 등을 모집글에 작성
- 모집글을 통해 자신이 원하는 운동 파트너를 물색

| 모집글 작성 |
|----------|
|![image](https://github.com/PJT-8arm/Backend/assets/148295292/b1ed782b-c023-4294-9429-6300c2309b81)|

<br>

### [프로필 확인]
- 사용자는 자신의 프로필 내용을 등록
- 등록된 프로필은 모집글에 반영
- 다른 사용자의 프로필을 열람하여 운동 실력 등을 확인
- 자신과 가장 잘 맞는 파트너를 선택

| 프로필 확인 |
|----------|
|![image](https://github.com/PJT-8arm/Backend/assets/148295292/c141595e-7d6a-4530-af7b-eb0941320d3f)|

<br>

### [채팅 기능]
- 모집글을 통해 관심 있는 파트너를 찾은 경우, 사용자들은 채팅을 통해 약속을 조율
- 잘 맞았던 파트너의 채팅 기록을 바탕으로 반복적인 약속이 가능.


| 채팅 기능 |
|----------|
|![image](https://github.com/PJT-8arm/Backend/assets/148295292/828c85a6-36dd-44f9-9d15-13789ce65a2e)|

<br>

### [약속 기능]
- 채팅을 통해 약속이 정해지면 사용자들은 운동 약속 일정 등록 가능
- ‘나의 약속’ 기능을 통해 약속을 간편하게 확인


| 약속 기능 |
|----------|
|![image](https://github.com/PJT-8arm/Backend/assets/148295292/1b8d7a6a-9f22-4d03-88d2-456f1bbafe61)|

<br>

## 8. 트러블 슈팅

![image](https://github.com/PJT-8arm/Backend/assets/148295292/ffa71669-b373-4b93-ad4f-e755a2d68829)

![image](https://github.com/PJT-8arm/Backend/assets/148295292/61b9c02e-fa6d-4513-9bf6-7b70bd23bf8d)

![image](https://github.com/PJT-8arm/Backend/assets/148295292/62ef5f7d-40a2-4898-8c9c-f4757d780cf4)

![image](https://github.com/PJT-8arm/Backend/assets/148295292/85ad6ddf-2b3b-4474-93ee-ee88a287cf2f)

![image](https://github.com/PJT-8arm/Backend/assets/148295292/96be630d-2c95-4a69-a470-a1aa5f57c5f9)

![image](https://github.com/PJT-8arm/Backend/assets/148295292/5b68f499-4609-472a-b796-3022b2fd78b0)


<br>
