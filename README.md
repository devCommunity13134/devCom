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

## ☁️ ERD ([링크](https://dbdiagram.io/d/TeamA-devCom_project-65c49ed0ac844320aeb7d9fa))


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
템플릿에 문제가 있는 경우에는 편집기 콘솔에 어떤 템플릿에 문제가 있는지 명시를 해줬음

1. template 문법 확인
   - 템플릿에 thymeleaf 문법에 문제가 있거나 html 태그를 잘못 작성하지는 않았는지 검증함
   - 대부분의 에러는 해당 부분에서 찾아서 해결할 수 있었고, 타이핑 실수롤 인하여 발생하는 오류의 경우도 많았음.
   - 대부분의 에러는 해당 부분에서 찾아서 해결할 수 있었고, 타이핑 실수로 인하여 발생하는 오류의 경우도 많았음.

2. 변수명, 메서드명 확인
   - 에러가 발생되는 템플릿과 연관된 메서드와 변수명을 찾아봄
   - 변수명이 잘못지정되었거나 메서드 이름이 달라서 발생하는 경우가 두번째로 많았음.
  
3. 로직 재검증
   - 연결되는 해결방법으로 1,2 번의 검증을 거쳐도 에러가 해결되지 않을 경우
   - 연관되어 있는 콘트롤러와 서비스의 로직을 데이터의 흐름순서에 따라 살펴보았음
   - 종종 서비스에 로직이 잘못되어 의도하지 않은 데이터를 꺼내오는 경우가 있었고 해당 서비스를 수정하여 오류를 해결하였음

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

### 🚨 Issue 3
### 🚧 정렬 버튼의 기능 구현

A. 이슈 내역
<br>
#### 문제
- 게시글 정렬을 위해 get 요청으로 파라미터로 좋아요(like)와 조회수(popular)를 보내고자 하였다.
- 점프 투 스프링 부트의 자바스크립트 예시를 따라하고자 했지만 원리를 이해하지 못해 시도에 난항을 겪었다.
![정렬 트러블 슈팅 예시](https://velog.velcdn.com/images/twinogre/post/b251c23c-11df-4582-9dc5-23b1d95c71ea/image.png)


<br>
문제점 설명
1. addEventListner 자바스크립트를 이해하지 못하고 사용하여 자바스크립트가 원활히 실행되지 않았다.

<br>

## 🛑 원인


<br>
1. 예외처리를 하지 않음
<br>

## 🚥 해결
- 점프 투 스프링부트의 검색을 위한 자바스크립트를 해체분석하여 addEventListner를 이해한 뒤에 사용하였다.
참고한 [점프 투 스프링부트 3-13_2.4](https://wikidocs.net/162799#_9)
#### addEventListner 소개
사용한 자바스크립트
- 게시글 목록의 정렬버튼(좋아요, 조회수 순)의 기능 구현에 사용
```javascript
        const btn_sort = document.getElementById("btn_sort");
        btn_sort.addEventListener('click', function() {
            document.getElementById('sortBy').value = document.getElementById('sort_param').value;
            document.getElementById('page').value = 0;
            document.getElementById('searchForm').submit();
        });

        const btn_sort2 = document.getElementById("btn_sort2");
        btn_sort2.addEventListener('click', function() {
            document.getElementById('sortBy').value = document.getElementById('sort_param2').value;
            document.getElementById('page').value = 0;
            document.getElementById('searchForm').submit();
        });
```


사용한 html
- input과 button의 서로 다른 id, 그리고  input의 th:value에 주목

```html
                <input type="hidden" id="sort_param" class=" form-control" th:value="popular">
                <button  class="btn fw-bold" role="button" id="btn_sort">
                    <span class="mx-1" >💥</span>Popular</button>

                <input type="hidden" id="sort_param2" class=" form-control" th:value="like">
                <button  class=" btn fw-bold" role="button" id="btn_sort2">
                    <span class="mx-1">👍</span>Like</button>
```
- 전송을 위해 숨겨진 폼태그
- 3번째 `input`태그의 name과 th:value에 주목.
```html 
        <form th:action="@{|/article/${categoryName}|}" method="get" id="searchForm">
            <input type="hidden" id="keyword" name="keyword" th:value="${keyword}">
            <input type="hidden" id="page" name="page" th:value="${paging.number}">
            <input type="hidden" id="sortBy" name="sortBy" th:value="${sortBy}">
        </form>
```

이를 종합하여 보면

```javascript
											//1
       const btn_sort = document.getElementById("btn_sort");
									// 사용자가 클릭할 때 함수 실행
        btn_sort.addEventListener('click', function() {
          								//2
            document.getElementById('sortBy').value = document.getElementById('sort_param').value;
          							 //3
            document.getElementById('page').value = 0;
          							//4
            document.getElementById('searchForm').submit();
        });
```

1. 버튼의 id는 변수에 담는다.
2. 폼태그에 전송할 `sortBy` 의 값을 사용자가 정렬버튼(`btn_sort`) 클릭 시에 매칭되는 input 값의 th:value 값으로 할당한다.
3. 정렬 시 0페이지로 이동하기 위해 `page` 값을 0으로 초기화
4. `searchForm`  폼 태그를 컨트롤러에 전송한다.


기본 문법은 다음과 같다.
```javascript
const btn = document.getElementById("btnId"); 
 
btn.addEventListener("이벤트종류", 함수이름)
```

이벤트의 종류:
> - **mouseover**	해당 객체의 영역 위에 마우스 커서가 진입하는 순간
- **mouseout**	해당 객체의 영역 위에 마우스 커서가 빠져나가는 순간
- **mousedown**	해당 객체의 영역 위에서 마우스 버튼을 누르는 순간
- **mouseup**	해당 객체의 영역 위에서 마우스 버튼을 떼는 순간
- **mousemove**	해당 객체의 영역 위에서 마우스 커서가 움직이는 순간
- **keydown**	키를 눌렀을 때 발생
- **keyup**	키를 뗐을 때 발생
- **keypress**	키를 눌렀을 때 발생 (잘 안 쓰임)
- ✅ **click**	마우스 버튼을 클릭하고 버튼에서 손가락을 떼면 발생 (**이번에 사용한 이벤트**) ✅
- **resize** 	브라우저 창의 크기를 조절할때 발생한다.
- **scroll** 	스크롤바를 드래그하거나 키보드(up, down)를 사용하거나 마우스 휠을 사용해서 웹페이지를 스크롤 할 때 발생
- **change**	변동이 있을 때 발생
- **focus**	포커스를 얻었을 때 발생
- **load**	로드가 완료 되었을 때 발생
- **select**	option 태그 등에서 선택을 했을 때 발생
- **submit**	submit 실행 시 발생

참고 링크
- [자바스크립트 이벤트 등록](https://lakelouise.tistory.com/35)
