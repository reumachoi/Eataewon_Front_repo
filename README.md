# EAT태원 클라쓰
(* APP: main 브랜치 / WEB: main2 브랜치)

## 프로젝트 진행 기간
`2022.03.14 ~ 04.18`

멀티캠퍼스 시큐어코딩을 적용한 앱 개발자 양성과정 파이널 프로젝트 (220314~220418)

## 팀원 
- 김나현, 김민기, 안도현, 윤동호, 최아름, 한정혁
<img width="1532" alt="스크린샷 2022-08-01 오전 12 19 21" src="https://user-images.githubusercontent.com/85995802/182033267-482bc9c6-02eb-49db-bccf-b8512d16eba0.png">

## 사용 기술

- HTML, CSS, JavaScript, Kolin
- Spring Boot, Oracle, MyBatis
- Naver API, Kakao API, Google API

## 서비스 개요
소비자의 경험을 통해 글을 작성하여 경험 공유 및 홍보로 소비 유발로 사업 가치화 <br/>
소비자들에게 소규모 요식업자들의 매장을 홍보할 수 있는 매개체 

## 서비스 기능 (앱 기준 설명)

A. 유저

- 회원가입 
    - 아이디, 비밀번호, 이름, 닉네임, 소개, 프로필사진을 입력 및 등록하고 회원가입을 진행합니다.
    - 아이디가 기존 회원과 중복되는 경우 다른 아이디로 가입을 해야합니다.
- 로그인 (로그인, 카카오로그인, 구글로그인)
    - 로그인시 액세스 토큰과 리프레쉬 토큰을 발급받습니다.
- 아이디, 비밀번호 찾기 
    - 아이디찾기: 입력한 아이디의 계정을 찾은경우 비밀번호 재설정이 가능합니다.
    - 이메일찾기: 입력한 이메일의 계정을 찾은경우 아이디를 알려주고 비밀번호 재설정이 가능합니다.
- 회원탈퇴
    - 아이디, 비밀번호 입력시 탈퇴가 가능합니다. 재가입은 불가능합니다.

B. 글
- 작성
    - 작성하려는 매장에 대해 검색하고 선택할 수 있습니다.
    - 사진을 업로드 할 수 있습니다.
    - 제목, 소개글, 해시태그를 작성하고 글 작성을 완료합니다.
    - 글작성 완료시 호감도가 50 상승합니다.
- 상세보기
    - 선택한 글을 보여줍니다.
    - 해당 글을 좋아요하거나 북마크하거나 카카오톡 공유가 가능합니다.
    - 더보기를 클릭시 매장의 가게 정보(텍스트)와 위치(지도)를 보여줍니다.
    - 좋아요 시 글 작성자의 호감도가 1 상승합니다. (이미 좋아요를 한경우 다시 좋아요를 하면 호감도 1감소)
    - 북마크 시 글 작성자의 호감도가 5 상승합니다.
- 수정
    - 글 작성자의 경우 수정이 가능합니다.
- 삭제
    - 글 작성자의 경우 삭제가 가능합니다.
- 검색
    - 입력한 키워드가 포함된 글을 검색해서 보여줍니다.
    - 지도보기 클릭 시 지도로 매장의 위치를 표시해서 보여줍니다.

C. 화면
- 홈
    - (가로) 조회수가 높은순으로 글들을 정렬해서 보여줍니다. 최신순으로 작성된 글들을 확인할 수 있습니다.
    - (세로) 최신 작성순으로 작성된 글들을 확인할 수 있습니다.
- 북마크
    - 사용자가 북마크했던 글들을 확인할 수 있습니다.
    - 북마크를 취소할 수 있습니다.
- 마이페이지
    - 사용자가 쓴 글들을 확인할 수 있습니다.
    - 사용자 정보(프로필사진, 이메일, 닉네임, 소개)를 수정할 수 있습니다.
    - 사용자의 정보를 확인할 수 있습니다.
- 하단 네비게이션바
    - 네비게이션 바를 통해 각 메뉴로 이동할 수 있습니다.
    
    
## ERD
<img width="675" alt="스크린샷 2022-08-01 오전 12 00 08" src="https://user-images.githubusercontent.com/85995802/182032445-8b4500f3-8517-404a-945e-366290b69184.png">

## 스토리보드
<img width="1124" alt="스크린샷 2022-08-01 오전 12 02 08" src="https://user-images.githubusercontent.com/85995802/182032506-2c2a7f5e-57cf-466c-9a6e-9cd92fa235d0.png">
<img width="1096" alt="스크린샷 2022-08-01 오전 12 02 16" src="https://user-images.githubusercontent.com/85995802/182032511-1509fdbd-4f9e-45b5-b7e8-a374e2622689.png">
