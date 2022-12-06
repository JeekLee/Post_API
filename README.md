# Post_API
## Spring 입문 1주차

<aside>
🖇️ https://github.com/JeekLee/Post_API

</aside><br>

<aside>
💡 1. 과제 요구사항

</aside><br>

<aside>
⚠️ **아래의 요구사항에 맞는 API 명세서를 작성해 보고 프로젝트를 생성해 직접 구현해 보세요!**

</aside><br>

<aside>
✅ 서비스 완성 요구사항

</aside><br>

1. 아래의 요구사항을 기반으로 Use Case 그려보기 ✔
    - 손으로 그려도 됩니다.
    - cf. [https://narup.tistory.com/70](https://narup.tistory.com/70)
2. 전체 게시글 목록 조회 API
    - 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기 ✔
    - 작성 날짜 기 내림차순으로 정렬하기 `List<Forum> findAllByOrderByModifiedAtDesc();`
3. 게시글 작성 API ✔
    - 제목, 작성자명, 비밀번호, 작성 내용을 저장하고 *(추가구현 : SHA256을 통한 단방향 암호화)*
    - 저장된 게시글을 Client 로 반환하기
4. 선택한 게시글 조회 API ✔
    - 선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기 
    (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
5. 선택한 게시글 수정 API ✔
    - 수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
    - 제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
6. 선택한 게시글 삭제 API ✔
    - 삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
    - 선택한 게시글을 삭제하고 Client 로 성공했다는 표시 반환하기

<aside>
💡 2. Use Case

</aside><br>

![KakaoTalk_20221130_152602331](https://user-images.githubusercontent.com/72681875/204935243-d1b50ba1-ba5c-4ec6-b6cb-9565a6f40f83.jpg)

<aside>
💡 3. API 설계


</aside><br>

[API 구조](https://www.notion.so/3af19518c52a4fc9877e74a08b5558d5)

<aside>
💡 4. 추가 답변

</aside><br>

<aside>
❓ **Why: 과제 제출시에는 아래 질문을 고민해보고 답변을 함께 제출해주세요.**

</aside><br>

1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)
    1. 수정 : URL Parameter
    2. 삭제 : URL Parameter
2. 어떤 상황에 어떤 방식의 request를 써야하나요?
    1. 조회 : @GettMapping
    2. 삭제 : @DeleteMapping
    3. 수정 : @PutMapping
3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?
    
    > Rest란
    1. HTTP URI(Uniform Resource Identifier)를 통해 자원(Resource)을 명시하고,
    2. HTTP Method(POST, GET, PUT, DELETE, PATCH 등)를 통해
    3. 해당 자원(URI)에 대한 CRUD Operation을 적용하는 것을 의미한다.
 
    > REST API 설계 규칙
    1. URI는 동사보다는 명사를, 대문자보다는 소문자를 사용하여야 한다.
    2. 마지막에 슬래시 (/)를 포함하지 않는다.
    3. 언더바 대신 하이폰을 사용한다.
    4. 파일확장자는 URI에 포함하지 않는다.
    5. 행위를 포함하지 않는다. 🤔
    > 
    
    Http Method의 사용의 측면에서는, Rest의 조건을 어느정도 충족했다고 이야기 할 수 있을 것 같다.
    
    하지만 API 설계시 url에 delete, new 등의 단어로 행위(기능)을 명시했기 때문에, 아쉬움이 있다.
    
4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)
관심사 분리는 다음과 같은 형태로 진행했다.
    1. Controller : PostAPIController
    이번 과제에서는 Postmand을 사용했기 때문에. Model과 연관되는 점은 없었지만 API의 설계 내용(method, url)을 빠짐없이 반영했다.
    2. Repository : ForumRepository
    JPA를 extend 하는 방식으로 사용했다.
    3. Service : ForumService, SHA256
    Forum에 관한 Request와 Response를 제어하기 위해 사용했다. SHA256의 경우 static으로 선언하여 비밀번호를 단방향으로 암호화 하는데 사용했다.
    4. Dto : ForumRequestDto, ForumResponseDto
    DB와 통신에 있어, 중간 단계에서 틀을 잡아주는 역할로 사용했다. Response의 경우 ResponseEntity로 감싸 header, body, httpStatus를 좀 더 세밀하게 다루었다.
    5. Entity : TimeStamp, Forum
    게시판 기능에 사용되는 모든 field를 담은 Forum을 사용했으며, TimeStamp를 abstract Class로 선언해 수정 시간과 작성 시간을 반영할 수 있도록 했다.

## Spring 입문 2주차

### 🔥 API 명세서
> https://documenter.getpostman.com/view/24666737/2s8YzP24wf