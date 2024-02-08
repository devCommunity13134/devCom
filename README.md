## DevCom(개발자 커뮤니티 웹 서비스)

### 서비스 설명 및 기획 의도

- 개발자들간 자유롭게 지식을 공유하고 팀을 만들어 함께 프로젝트를 진행할 수 있는 서비스
- 스프링부트 숙련도 향상을 위한 게시판 기반 서비스 제작
- 다양하고 전문화 되어있는 개발분야에 대한 공부도 함께 병행 시도


[[기능정의서](https://docs.google.com/document/d/1bPdPKuAXrLzElYWqFuo3CUAB99n0RePSmSdGM3DF50s/edit)]

---

## 🛠 개발환경

1. 사용언어 : JAVA , MariaDB(MySQL), HTML5 , CSS , JS
2. JDK : Amazon Corretto version 17.0.9
3. 에디터 : IntelliJ IDEA, DBeaver
4. DB종류 : MariaDB(MySQL), Server(local)
5. 프레임워크 : SpringBoot
6. 라이브러리 : JPA, thymeleaf, security, lombok, Java-email, kakao, bootstrap, jquery


---

## ☁️ ERD ([링크](https://www.erdcloud.com/d/moGHLYeCHND3PMxie))


![Untitled](https://github.com/devCommunity13134/devCom/assets/144447216/5c128d0f-f679-463d-91af-058ce7f8c6a4)



---

## 👀 시연영상

[![Video Label]()

제작 후 업로드 예정

---

## 🔥 트러블 슈팅

---

### 🚨 Issue 1
### 🚧 

#### 💭 [이슈 내역]


#### 🛑 [원인]



#### 🚥 [해결]


---

### 🚨 Issue 2
### 🚧 MVC와 Facade Pattern

#### 💭 [이슈 내역]
team, teamMember, teamInvite, project, projectState 엔티티를 관리하는데
서로 모두 연관이 되어 있어, Controller에서 각 Service를 종속성 주입 받게 되면 최악의 경우 아래의 그림과 같이 될 수 있다.
![image](https://github.com/devCommunity13134/devCom/assets/37567402/36d4aebe-55e2-4845-bab4-a7374d20abfa)

심지어 Controller 단에서는 Treansactional 태그도 사용할 수 없어 원자성을 보장 할 수없다.


#### 🚥 해결
MVC에 Facade pattern을 추가하여 TeamAndProject Facade Service를 만들어 구현하였다.
![image](https://github.com/devCommunity13134/devCom/assets/37567402/3d5fa04c-c7c8-4cdc-93e9-96e369cc75fb)

controller와 service 레이어 사이에 해당 파사드 서비스를 추가하여 필요한 서비스들을 종속성 주입을 받고 상위 컨트롤러에서 teamAndProejct Service를 호출하여 사용한다.
서비스이기 때문에 Transactional 태그도 가능하다.

---

## 트러블슈팅 예시

### 🚨 Issue 3
### 🚧 javax.mail.internet.AddressException

#### 💭 [이슈 내역]

프로그램 실행 > ID찾기 > 이메일 발송 실패 알림 생성 후 프로그램 로직 멈추지 않음
![](https://velog.velcdn.com/images/asdf4321/post/40818471-fe05-40da-bcf9-28a3cbd8ab1a/image.png)


#### 🛑 원인
1. SendMail.naverMailSend()의 메서드가 void기 때문에 반환하는 값이 없어 조건에 제약을 걸어 로직을 멈출 수 없다.

#### 🚥 해결
1. SendMail.naverMailSend() 메서드를 String값을 반환하도록 변경하고 이메일 발송에 성공했을 경우, "성공"을 반환시키고, 예외가 발생하여 실패하게 되면 알림을 출력하고 "실패"를 반환하도록 수정함

- 수정전 코드
1. SendMail
```java
public static void naverMailSend(String emailAddress) {
	try {
    	...이메일 발송 로직
    } catch {
    	...이메일 발송 실패 알림 출력
    }
}

```
2. MemberController
```java
        SendMail.naverMailSend(member.getEmail());

        System.out.println("<알림> 등록하신 이메일 주소로 보안코드를 발송하였습니다.");
```

- 수정후 코드
1. SendMail
```java
    public static String naverMailSend(String emailAddress) {
    	try{
        	...이메일 발송 로직
            return "성공";
        } catch {
        	...이메일 발송 실패 알림 출력
            return "실패";
        }
    }
```
2.MemberController
```java
        String sendEmail = SendMail.naverMailSend(member.getEmail());
        if (sendEmail.equals("실패")) {
            return;
        }
```
- 수정 후 프로그램 로직
![](https://velog.velcdn.com/images/asdf4321/post/d64c104c-7678-459d-9bc9-853eb4a0b150/image.png)
진행되던 로직은 중단되고 초기 동작으로 돌아간다.
