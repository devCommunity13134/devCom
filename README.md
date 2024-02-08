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
### 🚧 template parsing error

#### 💭 [이슈 내역]

타임리프 엔진이 콘트롤러에서 데이터를 읽어오는데 실패했을 때 발생하는 에러로 발생 원인은 크게 3가지로 확인하였음
해당 에러는 뷰에 나타나야 하는 UI가 일부분만 나타나지 않거나, 웹 페이지에 에러메세지가 따로 나타나지 않아 처음에 원일을 찾는데 어려움이 많았음.

#### 🛑 [원인]

1. thymeleaf 문법 오류
   - thymeleaf 문법을 잘못 작성하거나, html 코드를 틀리게 작성 했을 때 발생
  
2. 데이터 오입력
   - 콘트롤러에서 뷰에 전달하는 변수나 메서드명을 잘못입력했을 때도 템플릿 파싱 에러가 발생하였음
  
3. Controller, Service 로직 오류
   - 템플릿에 문제가 없는데, 템플릿에 전달해야할 데이터를 가공하는 과정에서 문제가 있을 때도 해당 에러가 발생함


#### 🚥 [해결]

해당 에러는 정확한 원인을 찾는데 어려움이 많아서 발생원인의 빈도에 따라 차례대로 검증을 함
1. template 문법 확인
   - 템플릿에 thymeleaf 문법에 문제가 있거나 html 태그를 잘못 작성하지는 않았는지 검증함
   - 대부분의 에러는 해당 부분에서 찾아서 해결할 수 있었고, 타이핑 실수롤 인하여 발생하는 오류의 경우도 많았음.
  
2. 변수명, 메서드명 확인
   - 에러가 발생되는 템플릿과 연관된 메서드와 변수명을 찾아봄
   - 변수명이 잘못지정되었거나 메서드 이름이 달라서 발생하는 경우가 두번째로 많았음.
  
3. 로직 재검증
   - 연결되는 해결방법으로 1,2 번의 검증을 거쳐도 에러가 해결되지 않을 경우
   - 연관되어 있는 콘트롤러와 서비스의 로직을 데이터의 흐름순서에 따라 살펴보았음
   - 종종 서비스에 로직이 잘못되어 의도하지 않은 데이터를 꺼내오는 경우가 있었고 해당 서비스를 수정하여 오류를 해결하였음


---

### 🚨 Issue 2
### 🚧 

#### 💭 [이슈 내역]




#### 🛑 원인

#### 🚥 해결


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
