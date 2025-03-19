# Living Shop API Documentation

## **1. 프로젝트 개요**

Living Shop은 리빙 스타일 제품을 판매하는 쇼핑몰입니다. 이 프로젝트는 Spring Boot 기반의 RESTful API로 백엔드를 구성하였으며, MyBatis를 활용하여 데이터베이스와 연동하였습니다.

---

## **2. 프로젝트 폴더 구조**

```
📂 living-shop
├── 📂 src
│   ├── 📂 main
│   │   ├── 📂 java
│   │   │   ├── 📂 com.shop.living
│   │   │   │   ├── 📂 controller  # API 컨트롤러
│   │   │   │   │   ├── CartController.java
│   │   │   │   │   ├── MemberController.java
│   │   │   │   │   ├── OrderController.java
│   │   │   │   │   ├── ProductController.java
│   │   │   │   ├── 📂 dao  # 데이터 접근 계층
│   │   │   │   │   ├── CartDao.java
│   │   │   │   │   ├── MemberDao.java
│   │   │   │   │   ├── OrderDao.java
│   │   │   │   │   ├── ProductDao.java
│   │   │   │   ├── 📂 dto  # 데이터 모델
│   │   │   │   │   ├── Cart.java
│   │   │   │   │   ├── Member.java
│   │   │   │   │   ├── Order.java
│   │   │   │   │   ├── OrderItem.java
│   │   │   │   │   ├── Product.java
│   │   │   │   ├── 📂 service  # 서비스 계층
│   │   │   │   │   ├── CartService.java
│   │   │   │   │   ├── MemberService.java
│   │   │   │   │   ├── OrderService.java
│   │   │   │   │   ├── ProductService.java
│   │   │   │   ├── 📂 util  # 유틸리티 클래스
│   │   │   │   │   ├── PasswordUtil.java
│   │   │   ├── 📂 resources
│   │   │   │   ├── application.properties
│   │   │   │   ├── config/secu.properties
│   │   │   │   ├── 📂 mapper  # MyBatis XML 매퍼 파일
│   │   │   │   │   ├── CartMapper.xml
│   │   │   │   │   ├── OrderMapper.xml
│   ├── LivingShopApplication.java  # 메인 클래스
```

---

## **3. API 목록 및 사용법**

### **1. 회원 관련 API**

#### **1.1 회원가입**

- **URL**: `POST http://localhost:8080/member/signup`
- **Request Body**:
  ```json
  {
    "email": "test@example.com",
    "pwd": "password123",
    "nickname": "Tester"
  }
  ```
- **Response**:
  ```json
  "Tester님 가입을 환영합니다!"
  ```

#### **1.2 이메일 중복 확인**

- **URL**: `GET http://localhost:8080/member/check-email`
- **Request Body**:
  ```json
  {
    "email": "test@example.com"
  }
  ```
- **Response** (사용 가능):
  ```json
  {
    "available": true,
    "message": "사용 가능한 이메일입니다."
  }
  ```
- **Response** (중복된 이메일):
  ```json
  {
    "available": false,
    "message": "이미 사용 중인 이메일입니다."
  }
  ```

#### **1.3 로그인**

- **URL**: `POST http://localhost:8080/member/login`
- **Request Body**:
  ```json
  {
    "email": "test@example.com",
    "pwd": "password123"
  }
  ```
- **Response**:
  ```json
  {
    "nickname": "Tester",
    "message": "로그인 성공"
  }
  ```

#### **1.4 로그아웃**

- **URL**: `POST http://localhost:8080/member/logout`
- **Response**:
  ```json
  "로그아웃 성공"
  ```

---

#### **1.5 로그인 상태 확인**

- **URL**: `GET http://localhost:8080/member/status`
- **설명**: 현재 세션의 로그인 상태를 확인합니다.
- **Response** (로그인 상태 유지 중):
  ```json
  {
    "nickname": "Tester",
    "message": "로그인 상태 유지 중"
  }
  ```
- **Response** (로그인되지 않은 상태):
  ```json
  {
    "message": "로그인되지 않은 상태입니다."
  }
  ```

---

#### **1.6 세션 유지 시간 확인**

- **URL**: `GET http://localhost:8080/member/session-time`
- **설명**: 현재 세션의 만료 시간(유지 시간)을 조회합니다.
- **Response** (세션 유지 중):
  ```json
  {
    "sessionTimeout": "1800초",
    "message": "세션이 유지 중입니다."
  }
  ```
- **Response** (세션이 존재하지 않는 경우):
  ```json
  {
    "message": "세션이 존재하지 않습니다."
  }
  ```

### **2. 상품 관련 API**

#### **2.1 모든 상품 조회**

- **URL**: `GET http://localhost:8080/products`
- **Response**:
  ```json
  [
    {
      "prodcode": 1,
      "prodname": "우드 테이블",
      "company": "리빙우드",
      "pimg": "table1.jpg",
      "description": "우드 소재의 테이블",
      "price": 120000
    }
  ]
  ```

#### **2.2 특정 상품 상세 조회**

- **URL**: `GET http://localhost:8080/products/{prodcode}`
- **Response**:
  ```json
  {
    "prodcode": 1,
    "prodname": "우드 테이블",
    "company": "리빙우드",
    "pimg": "table1.jpg",
    "description": "우드 소재의 테이블",
    "price": 120000
  }
  ```

---

---

### **3. 장바구니 관련 API**

#### **3.1 장바구니에 상품 추가**

- **URL:** `POST http://localhost:8080/cart/add`
- **설명:** 특정 상품을 장바구니에 추가
- **요청 예시:**

```json
{
  "prodcode": 1,
  "quantity": 2
}
```

- **응답 예시:**

```json
"장바구니에 추가되었습니다."
```

#### **3.2 장바구니 목록 조회**

- **URL:** `GET http://localhost:8080/cart`
- **설명:** 현재 장바구니에 있는 상품 목록을 조회
- **응답 예시:**

```json
[
  {
    "cartId": 1,
    "email": "test@example.com",
    "prodcode": 1,
    "quantity": 2
  },
  {
    "cartId": 2,
    "email": "test@example.com",
    "prodcode": 2,
    "quantity": 1
  }
]
```

#### **3.3 장바구니에서 특정 상품 삭제**

- **URL:** `DELETE http://localhost:8080/cart/{cartId}`
- **설명:** 특정 상품을 장바구니에서 삭제
- **응답 예시:**

```json
"장바구니에서 삭제되었습니다."
```

#### **3.4 장바구니에서 선택한 상품 주문**

- **URL:** `POST http://localhost:8080/cart/checkout`
- **설명:** 선택한 상품을 주문 후 장바구니에서 제거
- **요청 예시:**

```json
{
  "prodcodeList": [1, 2]
}
```

- **응답 예시:**

```json
"선택한 상품이 구매 목록에 추가되었습니다."
```

---

### **4. 주문 관련 API**

#### **4.1 개별 상품 주문**

- **URL:** `POST http://localhost:8080/order`
- **설명:** 개별 상품을 바로 구매
- **요청 예시:**

```json
{
  "orderItems": [
    {
      "prodcode": 3,
      "quantity": 1
    }
  ]
}
```

- **응답 예시:**

```json
"구매가 완료되었습니다."
```

#### **4.2 주문 목록 조회**

- **URL:** `GET http://localhost:8080/order`
- **설명:** 현재 사용자의 주문 목록을 조회
- **응답 예시:**

```json
[
  {
    "orderId": 1,
    "email": "test@example.com",
    "orderDate": "2025-03-18T11:12:39.000+00:00",
    "orderItems": [
      {
        "itemId": 1,
        "orderId": 1,
        "prodcode": 2,
        "quantity": 1
      }
    ]
  }
]
```

#### **4.3 주문 취소**

- **URL:** `DELETE http://localhost:8080/order/{orderId}`
- **설명:** 특정 주문을 취소
- **응답 예시:**

```json
"구매가 취소되었습니다."
```

---

## 🔐 인증 및 세션

## 1. 로그인 및 세션 관리 개요

Living Shop API는 **세션 기반 인증 방식**을 사용하여 사용자의 로그인 상태를 유지합니다. 사용자가 로그인하면 **서버에서 세션을 생성하고, 세션 ID를 클라이언트 측 쿠키에 저장**하여 이후 요청에서도 사용자 인증을 처리합니다. 로그아웃 시에는 해당 세션을 무효화하여 인증을 해제합니다.

---

## 2. 세션이 어떻게 다뤄지는가?

### 1. 로그인 요청 시

- 사용자가 이메일과 비밀번호를 입력하고 로그인 요청을 보냅니다.
- 서버에서 입력된 비밀번호를 해싱하여 저장된 비밀번호와 비교합니다.
- 인증이 성공하면 새로운 **세션 객체를 생성**하고, 해당 세션에 사용자 정보를 저장합니다.
- **세션 ID는 서버가 자동으로 생성하며, 클라이언트의 쿠키에 저장**됩니다.

### 2. 로그인 후 API 요청 시

- 이후 클라이언트가 로그인된 상태에서 다른 API를 호출하면 **요청 헤더에 포함된 세션 쿠키를 기반으로 인증을 수행**합니다.
- 서버는 세션 쿠키를 확인하고 **해당 세션이 유효하면 인증된 사용자로 요청을 처리**합니다.
- 세션이 만료되었거나 유효하지 않다면, **로그인 상태가 해제되며 API 요청이 차단**됩니다.

### 3. 로그아웃 요청 시

- 사용자가 로그아웃을 요청하면 서버에서 세션을 무효화(`invalidate`)하고 삭제합니다.
- 클라이언트의 **세션 쿠키는 자동으로 삭제**되며, 이후 요청에서는 인증되지 않은 상태가 됩니다.

### 4. 세션 유지 시간 (Timeout)

- 기본적으로 **세션 유지 시간은 1800초(30분)이며, 이를 초과하면 세션이 자동 만료**됩니다.
- 유지 시간을 변경하고 싶다면 `application.properties`에서 `server.servlet.session.timeout` 값을 조정하면 됩니다.

```properties
server.servlet.session.timeout=900s # 15분 유지
```

---

## 3. API 목록 및 설명

### **1. 로그인 (세션 생성)**

- **URL:** `POST http://localhost:8080/member/login`
- **설명:** 사용자의 이메일과 비밀번호를 검증한 후, **세션을 생성**하고 로그인 상태를 유지합니다.
- **요청 예시:**

```json
{
  "email": "test@example.com",
  "pwd": "password123"
}
```

- **응답 예시:**

```json
{
  "nickname": "Tester",
  "message": "로그인 성공"
}
```

- **로그인 성공 후 클라이언트 측 처리**
  - 서버가 세션을 생성하면 **세션 ID가 클라이언트의 쿠키에 자동 저장**됩니다.
  - 이후 요청에서 해당 쿠키를 사용하여 인증됩니다.

---

### **2. 로그인 상태 확인 (세션 유효성 체크)**

- **URL:** `GET http://localhost:8080/member/status`
- **설명:** 현재 세션이 유효한지 확인합니다.
- **응답 예시 (로그인 상태 유지 중):**

```json
{
  "nickname": "Tester",
  "message": "로그인 상태 유지 중"
}
```

- **응답 예시 (세션 만료 또는 로그아웃됨):**

```json
{
  "message": "로그인되지 않은 상태입니다."
}
```

---

### **3. 로그아웃 (세션 삭제)**

- **URL:** `POST http://localhost:8080/member/logout`
- **설명:** 현재 사용자의 세션을 무효화하여 로그아웃합니다.
- **응답 예시:**

```json
"로그아웃 성공"
```

- **로그아웃 후 클라이언트 처리**
  - 로그아웃이 성공하면 **클라이언트는 세션 쿠키를 삭제**해야 합니다.
  - 이후 요청에서는 **인증되지 않은 상태가 되므로 로그인해야 합니다**.

---

### **4. 세션 유지 시간 확인**

- **URL:** `GET http://localhost:8080/member/session-time`
- **설명:** 현재 사용자의 세션 유지 시간을 확인합니다.
- **응답 예시:**

```json
{
  "sessionTimeout": "1800초",
  "message": "세션이 유지 중입니다."
}
```

- **세션이 없는 경우:**

```json
{
  "message": "세션이 존재하지 않습니다."
}
```

---

## 4. 세션 ID가 API 통신에서 어떻게 활용되는가?

1. **로그인 후 클라이언트는 세션 ID를 쿠키에 저장**합니다.
2. **이후 API 요청마다 해당 쿠키가 자동으로 포함**되어 인증됩니다.
3. **서버에서는 요청에 포함된 세션 ID를 통해 사용자 정보를 식별**합니다.
4. **세션이 유지되는 동안 클라이언트는 별도의 인증 없이 API를 이용할 수 있음**.
5. **세션이 만료되면 클라이언트는 다시 로그인해야 API를 사용할 수 있음**.

---

## 5. 세션을 활용한 보안 고려 사항

✅ **세션 고정 공격(Session Fixation) 방지**

- 로그인 시 새로운 세션을 생성하여 기존 세션을 제거해야 합니다.

✅ **세션 하이재킹 방지**

- HTTPS를 사용하여 **세션 쿠키가 탈취되지 않도록 암호화**해야 합니다.
- `SameSite=Strict` 및 `HttpOnly` 설정을 적용하여 쿠키 보안을 강화합니다.

✅ **세션 만료 후 자동 로그아웃**

- 일정 시간 동안 요청이 없으면 **자동 로그아웃**되도록 구현해야 합니다.

✅ **CSRF 방어**

- CSRF 공격을 방지하기 위해 **POST 요청에 CSRF 토큰을 포함**하는 방식이 필요할 수 있습니다.

---
