# 기업

## 생성
**Method**: `POST`
- **Path**: `/companys`
- **Example Endpoint**: `https://localhost:8080/companys`
- **Request Parameters**:
    - **Body Parameters**:
        - `userId` (String): 회원 아이디
        - `password` (String): 비밀번호
        - 
        - `name` (String): 이름
        - `homepage` (String): 홈페이지
        - `adress` (String): 주소
        - `field` (String): 분야
        - `businesstype` (String): 업종
        - `career` (int): 업력
        - `employees` (int): 사원수 
        - `Introduction` (String) : 기업 소개
       
---
## 🔍 **간단한 조회**
- **Method**: `GET`
- **Path**: `/companys`
- **Example Endpoint**: `https://localhost:8080/companys?order=like`
- **Request Parameters**:
    - **Query String Parameter**:
        - `order` (String): 정렬 방법
        - `field` (Array): 분야
        - `area` (String): 지역
- **Response Message**: //기업은 목록조회에서 어디까지 보이게 할건지
    - `message` (String)
    - `data`:
        - `id` (Number): 아이디 (회원 아이디 X)
        - `name` (String): 이름
        - `field` (Number): 분야

---

## 🔍 **상세 조회 (내정보 용)**
- **Method**: `GET`
- **Path**: `/companys/my`
- **Example Endpoint**: `https://localhost:8080/companys/my`
- **Request Parameters**:
    - **Headers**:
        - `Authorization` (String, Required): Bearer 토큰 형태의 인증 토큰
- **Response Message**
    - `userId` (String): 회원 아이디
    - `password` (String): 비밀번호
    - `name` (String): 이름
    - `homepage` (String): 홈페이지
    - `adress` (String): 주소
    - `field` (String): 분야
    - `businesstype` (String): 업종
    - `career` (int): 업력
    - `employees` (int): 사원수
    - `Introduction` (String) : 기업 소개

---

## ✏️ **수정**
- **Method**: `PUT`
- **Path**: `/companys/my`
- **Example Endpoint**: `https://localhost:8080/programmers/my`
- **Request Parameters**:
    - **Headers**:
        - `Authorization` (String, Required): Bearer 토큰 형태의 인증 토큰
    - **Body Parameters**:
      - `name` (String): 이름
      - `homepage` (String): 홈페이지
      - `adress` (String): 주소
      - `field` (String): 분야
      - `businesstype` (String): 업종
      - `career` (int): 업력
      - `employees` (int): 사원수
      - `Introduction` (String) : 기업 소개

---

## ✏️ **수정 (비밀번호)**
- **Method**: `PATCH`
- **Path**: `/companys/my`
- **Example Endpoint**: `https://localhost:8080/companys/my`
- **Request Parameters**:
    - **Headers**:
        - `Authorization` (String, Required): Bearer 토큰 형태의 인증 토큰
    - **Body Parameters**:
        - `password` (String): 비밀번호


 ---

## ❌ **삭제**
- **Method**: `DELETE`
- **Path**: `/companys/my`
- **Example Endpoint**: `https://localhost:8080/companys/my`
- **Request Parameters**:
    - **Headers**:
        - `Authorization` (String, Required): Bearer 토큰 형태의 인증 토큰
- **Response Message**:
    - `message` (String)
- ---

# 🔒 로그인

## ➡️ **로그인 요청**
- **Method**: `POST`
- **Path**: `/auth/login`
- **Example Endpoint**: `https://localhost:8080/auth/login`
- **Request Parameters**:
    - **Body Parameters**:
        - `userId` (String): 회원 아이디
        - `password` (String): 비밀번호
- **Response Message**:
    - `data`:
        - `Authorization` (String, Required): Bearer 토큰 형태의 인증 토큰

---

#좋아요 기능
- 기업, 개발자 둘 다 사용 
- n:m
- 이넘(기업,개발자)

## 생성
**Method**: `POST`
- **Path**: `/likes`
- **Example Endpoint**: `https://localhost:8080/likes`
- **Request Parameters**:
    - **Body Parameters**:
        - `clickeduserId` (int): 좋아요 누른 주체id
        - `targetId` (int): 좋아요 누른 주체id
        - `userType` (String) : (enum) 주체 타입 ( 기업 또는 개발자)

## 삭제
**Method**: `DELETE`
- **Path**: `/likes`
- **Example Endpoint**: `https://localhost:8080/likes`
- **Request Parameters**:
    - **Body Parameters**:
        - `clickeduserId` (int): 좋아요 누른 주체id
        - `targetId` (int): 좋아요 누른 주체id
        - `userType` (String) : (enum) 주체 타입 ( 기업 또는 개발자)


## 좋아요 목록 조회
**Method**: `GET`
- **Path**: `/likes`
- **Example Endpoint**: `https://localhost:8080/likes?userId=1&userType=`
- **Request Parameters**:     //주체 기준으로 목록 조회
    - **Body Parameters**:
        - `clickeduserId` (int): 좋아요 누른 주체id
        - `userType` (String) : (enum) 주체 타입 ( 기업 또는 개발자)
-**Response Parameters**:  //좋아요 받은 대상 반환(간단히 이름만)
        -   `targetid`(int) :
        -   `targetType`(String): 어떤 기업/개발자 가 좋아요를 받았는지
           

    
        
       