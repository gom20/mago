# 🏔 Mountain Go
<img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=Spring%20Boot&logoColor=white"/> <img src="https://img.shields.io/badge/mariaDB-003545?style=flat&logo=mariaDB&logoColor=white"/> <img src="https://img.shields.io/badge/redis-DC382D?style=flat&logo=redis&logoColor=white"/>

## 📋 API List

### Auth

|Feature | Method     | URI               | Authorization |
|-------|------------|--------------------|---------------|
|로그인| `POST`     | `api/auth/login`  | |
|로그아웃| `POST` | `api/auth/logout`| |
|토큰 리프레쉬| `POST` | `api/auth/refresh` | |
|본인 인증 메일 발송| `POST`   | `api/auth/sendVerificationEmail` | |
|본인 인증| `GET`      | `api/auth/verifyEmail/{token}`  | |

### Member

|Feature| Method     | URI |             Authorization |
|-----|------------|--------------------|---------------|
| 회원 가입 | `POST`     | `api/members`  ||
| 회원 탈퇴| `DELETE` | `api/members`       | required|
| 회원 비밀번호 변경 | `PUT`      | `api/members/password`| required |
| 임시 비밀번호 메일 발송| `POST` | `api/members/sendTempPassword`  | |

### Record

|Feature         | Method      | URI | Authorization |
|-----|------------|--------------------|------|
| 등산 기록 조회 | `GET`     | `api/records`  | required |
| 등산 기록 등록| `POST` | `api/records`       | required |
| 등산 기록 삭제 | `DELETE`      | `api/records`| required |

### Stamp

|Feature| Method   | URI |Authorization |
|-----|------------|--------------------|------|
| 100대 명산 스탬프 조회 | `GET`  | `api/stamps`  | required |
| 100대 명산 스탬프 변경 | `PUT` | `api/stamps`  | required |


<br>

## 🔍 API Reference

### Auth

<details markdown="1" style="margin-left:14px">
<summary>로그인</summary>

**로그인**
----
엑세스 토큰을 발급합니다.

* **URL**

  api/auth/login

* **Method**

  `POST`

* **Request**

  ***Request Body***
  
  |Name| Type      |Length|Description|Required|
  |-----| --------|-----------|---------|-----------|
  |email| `String` |          | 이메일   | Y         |
  |password| `String`|        | 비밀번호 |  Y        |

  ***Sample Request***   
```
Content-type: application/json;charset=UTF-8
{
    "email": "mago@test.com",
    "password": "gogo2023"
}
```
* **Response**

  ***Response Body***

  |Name| Type     | Description |
  |-----| --------|-----------|
  | accessToken| `String` | 엑세스 토큰 |
  | refreshToken| `String` | 리프레쉬 토큰 |
  | user.email | `String` | 이메일 |
  | user.name | `String` | 이름 |
  
  ***Success Response***  
```
HTTP 200 OK
Content-type: application/json;charset=UTF-8
{
    "code": 0,
    "message": "요청이 성공적으로 처리 되었습니다.",
    "data": {
        "accessToken": "eyJhcGciOiJIUzI1NiJ9.eyFzdWIiOiJyaGFsZHVkODlASZ21hawdY29tIiwiaWF0IjoxNj...",
        "refreshToken": "eyJhcGciOiJIUzI1NiJ9.eyFzdWIiOiJyaGFsZHVkODlASZ21hawdY29tIiwiaWF0IjoxN...",
        "user": {
            "email": "mago@test.com",
            "name": "마고"
        }
    }
}
```
</details>

<details markdown="1" style="margin-left:14px">
<summary>로그아웃</summary>

**로그아웃**
----
엑세스 토큰을 블록 처리합니다.

* **URL**

  api/auth/logout

* **Method**

  `POST`

* **Request**

  ***Request Body***
  
  |Name| Type      |Length|Description|Required|
  |-----| --------|-----------|---------|-----------|
  |accessToken| `String` |          | 엑세스 토큰   |          |

  ***Sample Request*** 
```
Content-type: application/json;charset=UTF-8
{
    "accessToken": "eyJhbGciOiJIUsd1NiJ9.eyJzdWIiOiJyaGFsZHVkODOSZ21haWwuY29tIiwiaWF0IjoxNjc0...."
}   
```
* **Response**

  ***Response Body***

  |Name| Type     | Description |
  |-----| --------|-----------|
  | accessToken| `String` | 엑세스 토큰 |
  | refreshToken| `String` | 리프레쉬 토큰 |
  
  ***Success Response***
```
HTTP 200 OK
Content-type: application/json;charset=UTF-8
{
    "code": 0,
    "message": "요청이 성공적으로 처리 되었습니다.",
    "data": null
}
```
</details>


<details markdown="1" style="margin-left:14px">
<summary>토큰 리프레쉬</summary>

**토큰 리프레쉬**
----
액세스 토큰 또는 리프레쉬 토큰을 재발급 합니다.

* **URL**

  api/auth/refresh

* **Method**

  `POST`

* **Request**

  ***Request Body***
  
  |Name| Type      |Length|Description|Required|
  |-----| --------|-----------|---------|-----------|
  |accessToken| `String` |          | 엑세스 토큰   |   Y       |
  |refreshToken| `String` |          | 엑세스 토큰   |     Y     |

  ***Sample Request*** 
```
Content-type: application/json;charset=UTF-8
{
    "accessToken": "eyJdbGciOiIUzD1NiJ9.eyJzdWIiOiJyaGFsZKVkOD2AZ21haWwuY29tIiwiaWF0IjoxNkc0ODg3N...",
    "refreshToken": "eyJdbGciOiJIUzD1NiJ9.eyJzdWIiOiJyaGFsZKVkOD2AZ21haWwuY29tIiwiaWF0IjoxNkc0ODg3..."
}
```
* **Response**

  ***Response Body***

  |Name| Type     | Description |
  |-----| --------|-----------|
  | accessToken| `String` | 엑세스 토큰 |
  | refreshToken| `String` | 리프레쉬 토큰 |
  
  ***Success Response***
```
HTTP 200 OK
Content-type: application/json;charset=UTF-8
{
    "code": 0,
    "message": "요청이 성공적으로 처리 되었습니다.",
    "data": {
        "accessToken": "eyJdbGciOiIUzD1NiJ9.eyJzdWIiOiJyaGFsZKVkOD2AZ21haWwuY29tIiwiaWF0IjoxNkc0ODg3N...",
        "refreshToken": "eyJdbGciOiJIUzD1NiJ9.eyJzdWIiOiJyaGFsZKVkOD2AZ21haWwuY29tIiwiaWF0IjoxNkc0ODg3..."
    }
}
```
</details>


<details markdown="1" style="margin-left:14px">
<summary>본인 인증 메일 발송</summary>

**본인 인증 메일 발송**
----
본인 인증 메일을 발송합니다.

* **URL**

  api/auth/sendVerificationEmail

* **Method**

  `POST`

* **Request**

  ***Request Body***
  
  |Name| Type      |Length|Description|Required|
  |-----| --------|-----------|---------|-----------|
  |email| `String` |          | 이메일   |   Y       |


  ***Sample Request*** 
```
Content-type: application/json;charset=UTF-8
{
    "email": "mago@test.com",
}
```
* **Response**

  ***Response Body***

  |Name| Type     | Description |
  |-----| --------|-----------|
  | accessToken| `String` | 엑세스 토큰 |

  
  ***Success Response***
```
HTTP 200 OK
Content-type: application/json;charset=UTF-8
{
    "code": 0,
    "message": "요청이 성공적으로 처리 되었습니다.",
    "data": "mago@test.com"
}
```
</details>


<details markdown="1" style="margin-left:14px">
<summary>본인 인증</summary>

**본인 인증**
----
본인 인증을 수행합니다.

* **URL**

  api/auth/verifyEmail/{token}

* **Method**

  `GET`

* **Request**

  ***Request Params***
  
  |Name| Type      |Length|Description|Required|
  |-----| --------|-----------|---------|-----------|
  |token| `String` |          | 본인 인증 토큰   |   Y       |
  
  ***Sample Request*** 
```
.../api/auth/verifyEmail/JzdWIiOiJyaGFsZKVkOD2AZ21haWwuY29tIiJzdWIiOiJyaGFsZKVkOD2AZ21haWwuY29tI
```
* **Response**

  ***인증 완료 화면 Redirect***

</details>

<br>

### Member

<details markdown="1" style="margin-left:14px">
<summary>회원 가입</summary>


**회원 가입**
----
회원 정보를 등록합니다.

* **URL**

  api/members

* **Method**

  `POST`

* **Request**

  ***Request Body***
  
  |Name| Type     | Length |Description | Required |
  |-----| --------|-----------|---------|-----------|
  | email| `String` |    ~100     | 이메일 | Y |
  | name | `String` | ~10 | 이름 | Y|
  | password | `String`| 8~20 | 비밀번호 | Y|
  | passwordConfirm | `String` | 8~20|비밀번호 확인 | Y|
  
  ***Sample Request*** 
```
Content-type: application/json;charset=UTF-8
{
    "email": "mago@test.com",
    "name": "마고",
    "password": "gogo2023",
    "passwordConfirm": "gogo2023"
}
```
* **Response**

  ***Response Body***

  |Name| Type     | Description |
  |-----| --------|-----------|
  | email| `String` | 가입 완료 이메일 |

  ***Success Response***
```
HTTP 200 OK
Content-type: application/json;charset=UTF-8
{
    "code": 0,
    "message": "요청이 성공적으로 처리 되었습니다.",
    "data": {
        "email": "mago@test.com"
    }
}
```

</details>

<details markdown="1" style="margin-left:14px">
<summary>회원 탈퇴</summary>

**회원 탈퇴**
----
회원 정보를 삭제 합니다.

* **URL**

  api/members

* **Method**

  `DELETE`

* **Request**

  ***Request Body***
  
  |Name| Type     | Length |Description | Required |
  |-----| --------|-----------|---------|-----------|
  | password| `String` |         | 비밀번호 | Y |

  
  ***Sample Request*** 
```
Authorization: AccessToken
Content-type: application/json;charset=UTF-8
{
    "password": "gogo2023
}
```
* **Response**

  ***Response Body***

  |Name| Type     | Description |
  |-----| --------|-----------|
  | email| `String` | 탈퇴 완료 이메일 |

  ***Success Response***
```
HTTP 200 OK
Content-type: application/json;charset=UTF-8
{
    "code": 0,
    "message": "요청이 성공적으로 처리 되었습니다.",
    "data": {
        "email": "mago@test.com"
    }
}
```

</details>


<details markdown="1" style="margin-left:14px">
<summary>회원 비밀번호 변경</summary>

**회원 비밀번호 변경**
----
회원의 비밀번호를 변경합니다.

* **URL**

  api/members/password

* **Method**

  `PUT`

* **Request**

  ***Request Body***
  
  |Name| Type     | Length |Description | Required |
  |-----| --------|-----------|---------|-----------|
  | password| `String` |         | 기존 비밀번호 | Y |
  | newPassword | `String` | 8~20 | 신규 비밀번호 | Y|
  | newPasswordConfirm | `String`| 8~20 | 신규 비밀번호 확인 | Y|
  
  ***Sample Request*** 
```
Authorization: AccessToken
Content-type: application/json;charset=UTF-8
{
    "password": "gogo2023",
    "newPassword": "2023gogo",
    "newPasswordConfirm": "2023gogo"
}
```
* **Response**

  ***Response Body***

  |Name| Type     | Description |
  |-----| --------|-----------|
  | email| `String` | 이메일 |

  ***Success Response***
```
HTTP 200 OK
Content-type: application/json;charset=UTF-8
{
    "code": 0,
    "message": "요청이 성공적으로 처리 되었습니다.",
    "data": {
        "email": "mago@test.com"
    }
}
```
</details>


<details markdown="1" style="margin-left:14px">
<summary>임시 비밀번호 메일 발송 </summary>

**임시 비밀번호 메일 발송**
----
임시 비밀번호를 발급하여 메일로 전송합니다.

* **URL**

  api/members/sendTempPassword

* **Method**

  `POST`

* **Request**

  ***Request Body***
  
  |Name| Type     | Length |Description | Required |
  |-----| --------|-----------|---------|-----------|
  | email | `String` |         | 이메일| Y |
  | name | `String` |    | 이름 | Y|

  
  ***Sample Request*** 
```
Content-type: application/json;charset=UTF-8
{
    "email": "mago@test.com",
    "name": "마고"
}
```

* **Response**

  ***Response Body***

  |Name| Type     | Description |
  |-----| --------|-----------|
  | email| `String` | 이메일 |
  | name| `String` | 이름 |
  
  ***Success Response:***
```
HTTP 200 OK
Content-type: application/json;charset=UTF-8
{
    "code": 0,
    "message": "요청이 성공적으로 처리 되었습니다.",
    "data": {
        "email": "mago@test.com",
        "name": "마고"
    }
}
```
</details>


<br>

### Record

<details markdown="1" style="margin-left:14px">
<summary>등산 기록 조회</summary>

**등산 기록 조회**
----
등산 기록 목록을 조회합니다.

* **URL**

  api/records

* **Method**

  `GET`

* **Request**

  ***Request Params***
  
  |Name| Type     | Length |Description | Required |
  |-----| --------|-----------|---------|-----------|
  | page | `Integer` |         | 페이지 번호|  |
  | size | `Integer` |    | 사이즈 | |
  
  ***Sample Request*** 
```
Authorization: AccessToken
.../api/records?page=3
```

* **Response**

  ***Response Body***

  |Name| Type     | Description |
  |-----| --------|-----------|
  | uid| `Long` | 레코드 아이디 |
  | email| `String` | 이메일 |
  | yymmdd| `String` | 등산 일자 |
  | mountain| `String` | 산 이름 |
  | startDatetime| `String` | 시작 시각 |
  | endDatetime| `String` | 종료 시각 |
  | distance| `Float` | 거리 |
  | minAltitude| `Float` | 최소 고도 |
  | maxAltitude| `Float` | 최대 고도 |
  | totalTime| `Integer` | 전체 시간 |
  | hikingTime| `Integer` | 등산 시간 |
  | breakTime| `Integer` | 휴식 시간 |
  | avgSpeed| `Float` | 평균 속도 |
  | imgPath| `String` | 스냅샷 경로 |

  
  ***Success Response:***
```
HTTP 200 OK
Content-type: application/json;charset=UTF-8
{
    "code": 0,
    "message": "요청이 성공적으로 처리 되었습니다.",
    "data": {
        "content": [
            {
                "uid": 213,
                "email": "mago@test.com",
                "yymmdd": "20230128",
                "mountain": "가덕산",
                "startDatetime": "2023-01-28T08:16:59",
                "endDatetime": "2023-01-28T12:17:08",
                "distance": 5.0268,
                "minAltitude": 72,
                "maxAltitude": 305.7,
                "totalTime": 14402,
                "hikingTime": 10802,
                "breakTime": 3600,
                "avgSpeed": 1.50729,
                "imgPath": "file:///data/user/0/com.gom.mago/cache/AirMapSnapshot8994814948568585903.png"
            },
            {
              ... 
            }, ...
     
        ],
        "pageable": {
            "sort": {
                "sorted": true,
                "unsorted": false,
                "empty": false
            },
            "offset": 0,
            "pageNumber": 0,
            "pageSize": 10,
            "paged": true,
            "unpaged": false
        },
        "totalPages": 1,
        "totalElements": 3,
        "last": true,
        "size": 10,
        "number": 0,
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "numberOfElements": 1,
        "first": true,
        "empty": false
    }
}
```
</details>

<details markdown="1" style="margin-left:14px">
<summary>등산 기록 등록</summary>

**등산 기록 등록**
----
등산 기록을 등록합니다.

* **URL**

  api/records

* **Method**

  `POST`

* **Request**

  ***Request Body***
  
  |Name| Type     | Length |Description | Required |
  |-----| --------|-----------|---------|-----------|
  | mountain| `String` | |산 이름 | Y|
  | startDatetime| `String`| | 시작 시각 |Y|
  | endDatetime| `String`| | 종료 시각 |Y|
  | distance| `Float`| | 거리 |Y|
  | minAltitude| `Float`| | 최소 고도 |Y|
  | maxAltitude| `Float`| | 최대 고도 |Y|
  | breakTime| `Integer`| | 휴식 시간 |Y|
  | imgPath| `String`| | 스냅샷 경로 | |

    ***Sample Request*** 
```
Authrozation: AccessToken
Content-type: application/json;charset=UTF-8
{
    "mountain": "수락산",
    "startDatetime": "2022-12-05T08:00:00",
    "endDatetime": "2022-12-05T13:00:00",
    "distance": "8.53",
    "minAltitude": "10.22",
    "maxAltitude": "536.32",
    "breakTime": "5260",
    "imgPath": ""
}
```

* **Response**

  ***Response Body***

  |Name| Type     | Description |
  |-----| --------|-----------|
  | uid| `Long` | 레코드 아이디 |
  | email| `String` | 이메일 |
  | yymmdd| `String` | 등산 일자 |
  | mountain| `String` | 산 이름 |
  | startDatetime| `String` | 시작 시각 |
  | endDatetime| `String` | 종료 시각 |
  | distance| `Float` | 거리 |
  | minAltitude| `Float` | 최소 고도 |
  | maxAltitude| `Float` | 최대 고도 |
  | totalTime| `Integer` | 전체 시간 |
  | hikingTime| `Integer` | 등산 시간 |
  | breakTime| `Integer` | 휴식 시간 |
  | avgSpeed| `Float` | 평균 속도 |
  | imgPath| `String` | 스냅샷 경로 |

  
  ***Success Response:***
```
HTTP 200 OK
Content-type: application/json;charset=UTF-8
{
    "code": 0,
    "message": "요청이 성공적으로 처리 되었습니다.",
    "data": {
        "uid": 214,
        "email": "mago@test.com",
        "yymmdd": "20221205",
        "mountain": "수락산",
        "startDatetime": "2022-12-05T08:00:00",
        "endDatetime": "2022-12-05T13:00:00",
        "distance": 8.53,
        "minAltitude": 10.22,
        "maxAltitude": 536.32,
        "totalTime": 18000,
        "hikingTime": 12740,
        "breakTime": 5260,
        "avgSpeed": 2.41037,
        "imgPath": ""
    }
}
```
</details>

<details markdown="1" style="margin-left:14px">
<summary>등산 기록 삭제</summary>

**등산 기록 삭제**
----
등산 기록을 삭제합니다.

* **URL**

  api/records

* **Method**

  `DELETE`

* **Request**

  ***Request Body***
  
  |Name| Type     | Length |Description | Required |
  |-----| --------|-----------|---------|-----------|
  | ids| `Array<String>` | |기록 아이디 리스트 | Y|


    ***Sample Request*** 
```
Authrozation: AccessToken
Content-type: application/json;charset=UTF-8
{  "ids" : [ "213", "214"]}
```

* **Response**

  ***Response Body***

  |Name| Type     | Description |
  |-----| --------|-----------|
  | ids| `Array<String>` | 삭제된 기록 아이디 리스트 |


  
  ***Success Response:***
```
HTTP 200 OK
Content-type: application/json;charset=UTF-8
{
    "code": 0,
    "message": "요청이 성공적으로 처리 되었습니다.",
    "data": {
        "ids": [
            213,
            214
        ]
    }
}
```
</details>

<br>

### Stamp

<details markdown="1" style="margin-left:14px">
<summary>100대 명산 스탬프 조회</summary>

**100대 명산 스탬프 조회**
----
100대 명산 등정 여부 플래그를 조회합니다.

* **URL**

  api/stamps

* **Method**

  `GET`
  
  ***Sample Request*** 
```
Authrozation: AccessToken
.../api/stamps
```

* **Response**

  ***Response Body***

  |Name| Type     | Description |
  |-----| --------|-----------|
  | mountainId| `Long` | 산 아이디 |
  | mountainName| `String` | 산 이름 |
  | regionType| `String` | 지역 타입 |
  | regionName| `String` | 지역 이름 |
  | positionX| `Integer` | 지도 내 position:left |
  | positionY| `Integer` | 지도 내 position:top |
  | flag| `Boolean` | 삭제된 기록 아이디 리스트 |

  
  ***Success Response:***
```
HTTP 200 OK
Content-type: application/json;charset=UTF-8
{
    "code": 0,
    "message": "요청이 성공적으로 처리 되었습니다.",
    "data": [
        {
            "mountainId": 1,
            "mountainName": "가리산",
            "regionType": "GW",
            "regionName": "강원도",
            "positionX": 149,
            "positionY": 163,
            "flag": false
        },
        
        ...
        
        {
            "mountainId": 100,
            "mountainName": "희양산",
            "regionType": "GB",
            "regionName": "경상북도",
            "positionX": 52,
            "positionY": 117,
            "flag": true
        }
    ]
}
```
</details>

<details markdown="1" style="margin-left:14px">
<summary>100대 명산 스탬프 변경</summary>

**100대 명산 스탬프 변경**
----
100대 명산 등정 여부 플래그를 변경합니다.

* **URL**

  api/stamps

* **Method**

  `PUT`
  
  ***Sample Request*** 
```
Authrozation: AccessToken
Content-type: application/json;charset=UTF-8
{
    "mountainId": "93",
    "flag": "true"
}
```

* **Response**

  ***Response Body***

  |Name| Type     | Description |
  |-----| --------|-----------|
  | uid| `Long` | 스탬프 아이디 |
  | email| `String` | 이메일 |
  | mountainId| `Long` | 산 아이디 |
  | flag| `Boolean` | 등정 여부 |

  
  ***Success Response:***
```
HTTP 200 OK
Content-type: application/json;charset=UTF-8
{
    "code": 0,
    "message": "요청이 성공적으로 처리 되었습니다.",
    "data": {
        "uid": 247,
        "email": "mago@test.com",
        "mountainId": 93,
        "flag": true
    }
}
```
</details>

<br>

### Response Code
<details markdown="1">
<summary>성공 코드</summary>

**성공 코드**
----

* **코드 정의**
  |Code| Http Status |Message |
  |-----|--------|-----------|
  |`0`| `200` | 요청이 성공적으로 처리 되었습니다.|

* **Response**

  |Name| Type     | Description |
  |-----| --------|-----------|
  | code| `Integer` | 코드 |
  | message| `String` | 메세지|
  | data| `Object` | 데이터 |

* **Success Response:**
```
HTTP 200 OK
Content-type: application/json;charset=UTF-8
{
    "code": 0,
    "message": "요청이 성공적으로 처리 되었습니다.",
    "data": {
        ... 
    }
}
```
</details>


<details markdown="1">
<summary>에러 코드</summary>

**에러 코드**
----

* **코드 정의**
  |Code| Http Status |Message |
  |-----|--------|-----------|
  |`100`| `500` | 서버에서 에러가 발생하였습니다.|
  |`101`| `400` | 잘못된 요청입니다.|  
  |`200`| `401` | 권한이 없습니다.|
  |`201`| `401` | 이메일 본인 인증이 완료되지 않았습니다.|  
  |`202`| `401` | 토큰이 만료되었습니다.|
  |`203`| `401` | 리프레쉬 토큰이 유효하지 않습니다.|    
   |`204`| `401` | 엑세스 토큰이 유효하지 않습니다.|
  |`205`| `404` | 인증에 실패했습니다. 입력한 사용자 이름 또는 비밀번호가 잘못 되었습니다. |  
  |`206`| `400` | 비밀 번호와 확인용 비밀번호가 일치하지 않습니다.|
  |`300`| `400` | 이미 가입된 이메일입니다.|  
  |`301`| `404` | 일치하는 사용자가 없습니다. 입력한 정보를 다시 확인해주세요.|
  |`401`| `500` | 이메일 전송에 실패하였습니다.|    

  
* **Response**

  |Name| Type     | Description |
  |-----| --------|-----------|
  | code| `Integer` | 코드 |
  | message| `String` | 메세지|


* **Error Response:**
```
HTTP/1.1 404 Not Found
{
    "code": 205,
    "message": "인증에 실패했습니다. 입력한 사용자 이름 또는 비밀번호가 잘못 되었습니다. "
}
```
</details>
