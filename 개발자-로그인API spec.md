# 구인구직 API Specification

---

# 👤 개발자

## ➡️ **생성**
- **Method**: `POST`
- **Path**: `/programmers`
- **Example Endpoint**: `https://localhost:8080/programmers`
- **Request Parameters**:
    - **Body Parameters**:
        - `userId` (String): 회원 아이디
        - `password` (String): 비밀번호
        - `name` (String): 이름
        - `age` (Number): 나이
        - `email` (String): 이메일
        - `personalHistory` (Number): 경력
        - **field** (Array):
            - `fieldName` (String): 분야 이름
        - `selfIntroduction` (String): 자기 소개
        - `certificate` (String): 자격증
---

## 🔍 **간단한 조회**
- **Method**: `GET`
- **Path**: `/programmers`
- **Example Endpoint**: `https://localhost:8080/programmers?order=like`
- **Request Parameters**:
    - **Query String Parameter**:
        - `order` (String): 정렬 방법
        - `field` (Array): 분야
        - `personalHistory` (Number): 경력
- **Response Message**:
    - `message` (String)
    - `data`:
        - `id` (Number): 아이디 (회원 아이디 X)
        - `name` (String): 이름
        - `age` (Number): 나이
        - `personalHistory` (Number): 경력
        - **field** (Array):
            - `fieldName` (String): 분야 이름

---

## 🔍 **상세 조회 (더보기 용)**
- **Method**: `GET`
- **Path**: `/programmers/{id}`
- **Example Endpoint**: `https://localhost:8080/programmers/1`
- **Request Parameters**:
    - **Path Segment Parameter**:
        - `id` (Number): 아이디 (회원 아이디 X)
- **Response Message**:
    - `message` (String)
    - `data`:
        - `name` (String): 이름
        - `age` (Number): 나이
        - `email` (String): 이메일
        - `personalHistory` (Number): 경력
        - **field** (Array):
            - `fieldName` (String): 분야 이름
        - `selfIntroduction` (String): 자기 소개
        - `certificate` (String): 자격증

---

## 🔍 **상세 조회 (내정보 용)**
- **Method**: `GET`
- **Path**: `/programmers/my`
- **Example Endpoint**: `https://localhost:8080/programmers/my`
- **Request Parameters**:
    - **Headers**:
      - `Authorization` (String, Required): Bearer 토큰 형태의 인증 토큰
- **Response Message**
    - `message` (String)
    - `data`:
        - `userId` (String): 회원 아이디
        - `name` (String): 이름
        - `age` (Number): 나이
        - `email` (String): 이메일
        - `personalHistory` (Number): 경력
        - **field** (Array):
            - `fieldName` (String): 분야 이름
        - `selfIntroduction` (String): 자기 소개
        - `certificate` (String): 자격증

---

## ✏️ **수정**
- **Method**: `PUT`
- **Path**: `/programmers/my`
- **Example Endpoint**: `https://localhost:8080/programmers/my`
- **Request Parameters**:
    - **Headers**:
        - `Authorization` (String, Required): Bearer 토큰 형태의 인증 토큰
    - **Body Parameters**:
        - `name` (String): 이름
        - `age` (Number): 나이
        - `email` (String): 이메일
        - `personalHistory` (Number): 경력
        - **field** (Array):
            - `fieldName` (String): 분야 이름
        - `selfIntroduction` (String): 자기 소개
        - `certificate` (String): 자격증

---

## ✏️ **수정 (비밀번호)**
- **Method**: `PATCH`
- **Path**: `/programmers/my`
- **Example Endpoint**: `https://localhost:8080/programmers/my`
- **Request Parameters**:
    - **Headers**:
        - `Authorization` (String, Required): Bearer 토큰 형태의 인증 토큰
    - **Body Parameters**:
        - `password` (String): 비밀번호

---

## ❌ **삭제**
- **Method**: `DELETE`
- **Path**: `/programmers/my`
- **Example Endpoint**: `https://localhost:8080/programmers/my`
- **Request Parameters**:
    - **Headers**:
        - `Authorization` (String, Required): Bearer 토큰 형태의 인증 토큰
- **Response Message**:
    - `message` (String)

---

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