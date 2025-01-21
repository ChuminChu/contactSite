# 개발자 쪽지API spec


## ➡️ **생성**
- **Method**: `POST`
- **Path**: `/alarms`
- **Example Endpoint**: https://localhost:8080/alarms
- **Request Parameters**:
    - **RequestHeader**:
        - (String) `authorization`:  토큰
    - **Body Parameters**:
        - (Long) `기업 ID`: 기업 고유ID(아이디x)
- **Response**:
    - (Long) `쪽지 ID`: 쪽지 고유ID
    - (Long) `기업 ID`: 기업 고유ID

---

## 🔍 **조회(내가 보낸 쪽지)**
- **Method** : `GET`
- **path**: `/alarms/senders`
- **Example Endpoint** : https://localhost:8080/alarms/senders
- **Request Parameters**:
    - **RequestHeader**
        - String `authorization`: 토큰
    - **Response Parameters**
        - List<기업> `companyName`: 기업명들
        - String message : "000기업에서 쪽지를 보냈습니다."

--- 

## 🔍 **조회(내가 받은 쪽지)**
- **Method** : `GET`
- **path**: `/alarms/receptions`
- **Example Endpoint** : https://localhost:8080/alarm/receptions
- **Request Parameters**
    - **RequestHeader**
        - (String) `authorization`: 토큰
- **Response Parameters**
    - List<기업> `companyName`: 기업명들

---

## ❌ **삭제(내가 보낸 쪽지)**
- **Method** : `Delete`
- **Path** : `/alarms/senders/{alarmId}`
- **Example Endpoint** : https://localhost:8080/alarms/senders/1
- **Request Parameters**
    - **Path**:
        - (Long) `alarmId`: 알람 고유ID
    - **RequestHeader**:
        - (String) `authorization`: 토큰
- **RequestBody**:
    - (기업) `companyName`: 기업명

---

## ❌ **삭제(내가 받은 쪽지)**
- **Method** : `Delete`
- **Path** : `/alarms/receptions/{alarmId}`
- **Example Endpoint** : https://localhost:8080/alarms/receptions/1
- **Request Parameters**
    - **Path**:
        - (Long) `alarmId`: 알람 고유ID
    - **RequestHeader**:
        - (String) `authorization`: 토큰
- **RequestBody**:
    - (기업) `companyName`: 기업명

------

# 기업 쪽지API spec
## 개발자랑 똑같음

## ➡️ **생성**
- **Method**: `POST`
- **Path**: `/alarms`
- **Example Endpoint**: https://localhost:8080/alarms
- **Request Parameters**:
    - **RequestHeader**:
        - (String) `authorization`:  토큰
    - **Body Parameters**:
        - (Long) `개발자 ID`: 개발자 고유ID(아이디x)
- **Response**:
    - (Long) `쪽지 ID`: 쪽지 고유ID
    - (Long) `개발자 ID`: 개발자 고유ID

---

## 🔍 **조회(내가 보낸 쪽지)**
- **Method** : `GET`
- **path**: `/alarms/senders`
- **Example Endpoint** : https://localhost:8080/alarms/senders
- **Request Parameters**:
    - **RequestHeader**
        - String `authorization`: 토큰
    - **Response Parameters**
        - List<개발자> `developerName`: 개발자들
        - String message : "000개발자에서 쪽지를 보냈습니다."

--- 

## 🔍 **조회(내가 받은 쪽지)**
- **Method** : `GET`
- **path**: `/alarms/receptions`
- **Example Endpoint** : https://localhost:8080/alarm/receptions
- **Request Parameters**
    - **RequestHeader**
        - (String) `authorization`: 토큰
- **Response Parameters**
    - List<개발자> `developerName`: 개발자들

---

## ❌ **삭제(내가 보낸 쪽지)**
- **Method** : `Delete`
- **Path** : `/alarms/senders/{alarmId}`
- **Example Endpoint** : https://localhost:8080/alarms/senders/1
- **Request Parameters**
    - **Path**:
        - (Long) `alarmId`: 알람 고유ID
    - **RequestHeader**:
        - (String) `authorization`: 토큰
- **RequestBody**:
    - (개발자) `developerName`: 개발자

---

## ❌ **삭제(내가 받은 쪽지)**
- **Method** : `Delete`
- **Path** : `/alarms/receptions/{alarmId}`
- **Example Endpoint** : https://localhost:8080/alarms/receptions/1
- **Request Parameters**
    - **Path**:
        - (Long) `alarmId`: 알람 고유ID
    - **RequestHeader**:
        - (String) `authorization`: 토큰
- **RequestBody**:
    - (개발자) `developerName`: 개발자