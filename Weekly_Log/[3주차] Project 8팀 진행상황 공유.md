# 3주차

### 파일 명: [3주차] Project_8팀 진행상황 공유

<aside>
✔️ 하단의 내용을 복사하여 .md 파일을 제작해주시길 바랍니다.

</aside>

## 팀 구성원, 개인 별 역할

---

프로젝트 팀 구성원을 기재해 주시고, 그 주의 팀원이 어떤 역할을 맡아서 개발을 진행했는지 구체적으로 작성해 주세요. 🙂 

- 전희영 : 팀장, 마이페이지, 배포 파이프라인 구축
- 박태훈 : 메시지 기능
- 서상호 : 모집글 CRUD
- 임유빈 : 모집글 CRUD, 위클리 회의록 작성 및 제출
- 현진영 : 회원기능

## 팀 내부 회의 진행 회차 및 일자

---

- 일주일 간 진행한 내부 회의 횟수와 일자, 진행 방법, 불참 인원을 위와 같이 작성해 주세요.
- 1회차(2024.3.12) : 디스코드 진행 , 박태훈님 불참
- 2회차(2024.3.13) : 디스코드 진행 , 전원 참석
- 3회차(2024.3.14) : 디스코드 진행 , 임유빈님 조퇴
- 4회차(2024.3.15) : 디스코드 진행 , 전원 참석

## 현재까지 개발 과정 요약 (최소 500자 이상)

---

<aside>
💡 현재까지 진행하고 있는 개발 현황을 기능별 목표, 목표달성률, 성과자체평가(상세히) 작성해주세요.

- 성과자체평가는 ‘기술적으로 새로 알게된 점, 어려웠던 점, 아쉬운 점, 시도해볼 점' 등을 작성해 주시면 됩니다 🙂
- 팀원 각자 현재 구현하고 있는 것을 적어주세요. :)
- ex) 기능별 목표: 암기장 삭제하기 기능 추가 / 목표달성률: 50% - 마켓에서 등록해제는 가능, 내 암기장 삭제는 아직 미구현 / 지속적으로 수정사항이 발생하여 완성도를 올리는 중, 현재 관련 문서 참고 중
</aside>

- 전희영 :
    - 목표 : 마이페이지 구현
    - 목표 달성률 : 80%
    - 세부 내용 :
        - 마이페이지 중 약속 관련 기능은 아직 구현이 안되어 메소드 작성을 하지 못함.
        - 토큰으로 인증하는 방법을 사용하게 되면서 인증 정보를 어떻게 활용하는 방법을 알게됨. 또한, 토큰인증이 어려운 부분이기에 해결을 위해 같이 노력하는 과정에서 토큰 인증과 스프링 시큐리티에 대해서 보다 자세하게 알게 되었음.
- 박태훈 :
    - 목표 : 프론트 메세지 기능 구현, ui 적용
    - 목표 달성률 : 30%
    - 세부 내용 : 리액트 라이브러리의 사용법을 이해하는데에 시간이 많이 걸렸고, tanstack query를 사용하기 위해 프로젝트를 재생성 해보고, 시간이 남는다면 리코일을 공부할 예정이다.
- 서상호 :
    - 목표 : 프론트 화면 구현 , 리엑트 동작 이해
    - 세부 내용 :  리엑트를 처음 사용해보기 때문에 일단 화면을 먼저 무작정 만들어본다음 이해를 하는것이 가장 빠른 방법이라고 생각했다. 하다보니 멘토님의 조언을 듣고 swagger api, orval, tanstack query를 사용하면 빠르고 쉽게 구현을 할 수 있다고 하셔서 주말동안 더 공부를 하기로 했다.
    
- 임유빈 :
    - 목표 :  프론트 메인페이지, 회원가입페이지, 로그인페이지 구현 / 목표 달성률 : 80%
    - 세부 내용 : 메인페이지 쪽은 백엔드 쪽 일부 코드 추가가 필요해서 회원가입, 로그인 페이지 구현을 목표로 했는데 꾸미는 것 빼고 기능 구현은 완료했다. 같이 프론트 작업을 시작했던 상호님, 태훈님과 같이 피드백 해주고 도움을 주고 받으며 원활한 작업이 이루어졌다.
- 현진영 :
    - 목표 :  회원기능 마무리하기 , 리액트 공부하고 프론트엔드 개발 시작하기
    - 세부 내용 :  현재 쿠키에 엑세스 토큰만 담겨져 있어서 리프레시 토큰도 함께 넣을 수 있도록 코드를 수정했고, 토큰을 헤더가 아닌 쿠키에서 가져올 수 있도록 하였습니다. 그리고 jwt토큰을 이용한 로그아웃 방법 중 쿠키를 삭제해서 로그아웃 하는 방법으로 구현하였습니다. 프론트엔드 작업 할 때 사용자 정보를 가져올 수 있도록 컨트롤러에 memberinfo를 추가하였습니다.

## 개발 과정에서 나왔던 질문 (최소 200자 이상)

---

<aside>
💡 개발을 진행하며 나왔던 질문 중 핵심적인 것을 요약하여 작성해 주세요 🙂

- 질의응답 과정 중 해결되지 않은 질문을 정리하여도 좋습니다.
</aside>

- 로그아웃은 어떤 방식으로 하는 게 좋을까요?
    - 쿠키를 삭제하는 방식으로 진행하기로 했습니다.
- 프론트 로그인 쪽 구현을 해야 다른 쪽에서 테스트가 가능할 것 같은데 어떻게 하는 게 좋을까요?
    - 태훈님, 상호님 두분이 개발해보고 더 좋은 결과물을 사용하기로 했습니다.
- 로그인 정보를 계속 가져오는 것보다 어디 한곳에 저장해놓는게 좋은 방법이 아닐까요?
    - 멘토님의 조언을 듣고 context api를 사용하여 관리하기로 했습니다.

## 개발 결과물 공유

---

Github Repository URL: https://github.com/PJT-8arm

- 필수) 팀원들과 함께 찍은 인증샷(온라인 만남시 스크린 캡쳐)도 함께 업로드 해주세요 🙂

![스크린샷 2024-03-15 131055](https://github.com/PJT-8arm/Backend/assets/148295292/cc86089f-9e5d-4f2c-87f5-271b797fd487)

