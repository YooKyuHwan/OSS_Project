# #Spring(Boot), Thymeleaf, mysql 활용하여 게시판 개발

# #실행  및 개발 환경

## 1. IntelliJ IDE 및 Docker 설치

## 2. 실행 방법
#### 1) repository clone 하기

#### 2) 레포지토리 최상위 디렉토리에서 아래의 명령어 실행
##### docker build -t mydb:0.1 -f DB/Dockerfile .
```shell
  $ docker build -t mydb:0.1 -f DB/Dockerfile .
  
```

#### 3) 도커로 mysql 서버 실행
##### docker run --name mydb -d -p 3306:3306 mydb:0.1
##### (주의!! 서버 빌드 및 실행 전에 mysql 서버 먼저 실행!!)
```shell
  $ docker un --name mydb -d -p 3306:3306 mydb:0.1
  
```
#### 4) IntelliJ 에서 프로젝트 실행(Docker 컨테이너로 실행X)
###### (mysql 연동하면서 따로 도커에서 서버 실행시 오류발생)
###### (Docker 컨테이너로 실행 하려면 아래와 같은 설정 필요)
###### (도커 컨테이너 끼리 네트워크 설정 또는 도커 컴포트 이용 필요) 
###### (또한 application.yml 파일에서 mysql 경로 설정필요)

## 3. mysql 이용 정보
##### Docker 컨테이너로 실행한 mysql 서버 접속하려면,
##### 1. docker exec -it mydb /bin/bash
##### 2. mysql -u SKKU -p
##### 3. SKKU
```shell
  $ docker exec -it mydb /bin/bash
  $ mysql -u SKKU -p
  $ SKKU
```
###### (mysql root의 비번또한 SKKU 이면 현재 SKKU/SKKU로 유저이름/비번 설정되어있음)

# #<기능>
##### (data.dql 파일에서 모든 사용자 아이디/비번 확인 가능하며, 관리자 계정은 skku/skku 입니다.)

#### 1. 로그인 및 회원가입
#### 2. 로그인한 회원만 게시글 작성 
#### 3. 게시판 별로 게시글 보여주기
#### 4. 회원정보 수정 -> 이름, 별명 수정
#### 5. 자신이 작성한 댓글 게시글 모아보기
#### 6. 자신의 게시글, 댓글 삭제
#### 7. 관리자 계정에서만 게시글, 댓글 삭제 기능
#### 8. 게시판에 댓글 작성 기능(로그인한 회원만)
#### 9. 게시글에서 댓글 보여주기
#### 10. 기본적으로 게시글, 댓글 최신순으로
#### 11. 게시글, 댓글 페이지별로 보여주기
#### //////////////////////////////////////////////////////////////////

# #To Do
### 1. 비번변경 기능
### 2. 익명, 실명 댓글 작성 -> 일단 댓글 작성기능부터

##### (위의 기능 말고도 어떠한 추가 기능 구현 가능)















