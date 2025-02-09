# Back-End Documentation

## Overview
This documentation covers the back-end API for the eCommerce project. It provides detailed information on each endpoint, including required parameters, request examples, and responses. A dedicated section for the database design is also provided, including table definitions and an ER diagram.

## 🔓 Public Endpoints

### 🛂 Authentication (`AuthController`)

#### ✅ Check Authentication Status
**GET** `/api/v1/auth/check-auth`
- **Description:** Verifies if the user is authenticated.
- **Headers:**
    - `Authorization: Bearer {JWT_TOKEN}` *(optional, can be sent via cookies instead)*

#### 🔑 Login
**POST** `/api/v1/auth/login`
- **Body:**
  ```json
  {
    "email": "",
    "password": ""
  }
  ```
- **Description:** Authenticates a user and returns a JWT token.

#### 🚪 Logout
**POST** `/api/v1/auth/logout`
- **Description:** Logs out the user by clearing authentication cookies.

#### 📝 Register
**POST** `/api/v1/auth/register`
- **Body:**
  ```json
  {
    "userName": "",
    "email": "",
    "password": ""
  }
  ```
- **Description:** Creates a new user account.

## 🔐 Protected Endpoints

### 🛍️ Product Management (`ProductsController`)

#### 📦 Get Products
**GET** `/api/v1/shop/products/get?category=array&brand=array&sortBy=string`
- **Query Parameters:**
    - `category`: Filter by category.
    - `brand`: Filter by brand.
    - `sortBy`: Sorting method, allowed values:
        - `price-lowtohigh`
        - `price-hightolow`
        - `title-atoz`
        - `title-ztoa`
- **Description:** Retrieves a list of products based on filters.

#### 🔍 Get Product by ID
**GET** `/api/v1/shop/products/get/{productId}`
- **Description:** Retrieves product details by ID.

#### ✍️ Add Review
**POST** `/api/v1/shop/review/add`
- **Body:**
  ```json
  {
    "reviewMessage": "",
    "reviewValue": 0,
    "userName": "",
    "productId": "",
    "userId": ""
  }
  ```
- **Description:** Adds a review for a product.

## 🔐🔑 Admin-Only Endpoints

### 🏪 Product Management

#### ➕ Add Product
**POST** `/api/v1/admin/products/add`
- **Body:**
  ```json
  {
    "averageReview": 0,
    "brand": "",
    "category": "",
    "description": "",
    "image": "",
    "salePrice": 0,
    "price": 0,
    "title": "",
    "totalStock": 0
  }
  ```
- **Description:** Adds a new product to the inventory.

#### ✏️ Edit Product
**POST** `/api/v1/admin/products/edit/{productId}`
- **Body:**
  ```json
  {
    "averageReview": 0,
    "brand": "",
    "category": "",
    "description": "",
    "image": "",
    "salePrice": 0,
    "price": 0,
    "title": "",
    "totalStock": 0
  }
  ```
- **Description:** Edits an existing product.

#### ❌ Delete Product
**DELETE** `/api/v1/admin/products/delete/{productId}`
- **Description:** Deletes a product by ID.

#### 📷 Upload Product Image
**POST** `/api/v1/admin/products/upload-image?my_file=string`
- **Description:** Uploads an image for a product.

### 📦 Order Management (`OrderAdminController`)

#### 📜 Get Order Details
**GET** `/api/v1/admin/orders/details/{orderId}`
- **Description:** Retrieves order details.

#### 📋 Get All Orders
**GET** `/api/v1/admin/orders/get`
- **Description:** Fetches all orders.

#### 🔄 Update Order Status
**PUT** `/api/v1/admin/orders/update-status/{orderId}?orderStatus=string`
- **Description:** Updates the status of an order.

### 📦 Order Handling (`OrderShopController`)

#### 💰 Capture Payment
**POST** `/api/v1/shop/order/capture`
- **Body:**
  ```json
  {
    "orderId": "",
    "token": "",
    "payerId": ""
  }
  ```
- **Description:** Captures payment for an order.

#### 🛍️ Create Order
**POST** `/api/v1/shop/order/create`
- **Body:**
  ```json
  {
    "userId": "",
    "cartId": "",
    "addressInfo": {},
    "orderStatus": "",
    "paymentMethod": "",
    "paymentStatus": "",
    "totalAmount": 0,
    "token": "",
    "payerId": "",
    "orderDate": "",
    "orderUpdateDate": ""
  }
  ```
- **Description:** Creates a new order.

### 🛒 Cart Management (`CartController`)

#### 🛍️ Add to Cart
**POST** `/api/v1/shop/cart/add`
- **Body:**
  ```json
  {
    "userId": "",
    "productId": "",
    "quantity": 0
  }
  ```
- **Description:** Adds a product to the cart.

#### 🛍️ Get Cart
**GET** `/api/v1/shop/cart/get/{userId}`
- **Description:** Retrieves the cart contents.

#### ✏️ Update Cart
**PUT** `/api/v1/shop/cart/update-cart`
- **Body:**
  ```json
  {
    "userId": "",
    "productId": "",
    "quantity": 0
  }
  ```
- **Description:** Updates the quantity of an item in the cart.

#### ❌ Remove from Cart
**DELETE** `/api/v1/shop/cart/{userId}/{productId}`
- **Description:** Removes an item from the cart.

---

## Database

### 🗺️ ER Diagram
![alt text](https://raw.githubusercontent.com/Raul99GC/ecommerce/refs/heads/main/images/ecommerce-db.png "Database ER Diagram")

### 📊 Tables
## 🛍️ Tables

### **1. `user`**
| Column      | Type           | Constraints                     |
|------------|--------------|--------------------------------|
| `id`       | `binary(16)` | `PK, NOT NULL`                 |
| `created_at` | `datetime(6)` |                                |
| `email`    | `varchar(255)` | `NOT NULL`                     |
| `password` | `varchar(120)` | `NOT NULL`                     |
| `updated_at` | `datetime(6)` |                                |
| `user_name` | `varchar(255)` | `NOT NULL`                     |
| `cart_id`  | `binary(16)` | `FK → cart.id`                  |
| `role_id`  | `binary(16)` | `FK → role.id`                  |

---

### **2. `role`**
| Column  | Type         | Constraints    |
|---------|------------|---------------|
| `id`    | `binary(16)` | `PK, NOT NULL` |
| `role`  | `tinyint`    |               |

---

### **3. `cart`**
| Column       | Type         | Constraints    |
|-------------|------------|---------------|
| `id`        | `binary(16)` | `PK, NOT NULL` |
| `created_at` | `datetime(6)` |               |
| `updated_at` | `datetime(6)` |               |

---

### **4. `cart_item`**
| Column      | Type         | Constraints              |
|------------|------------|-------------------------|
| `id`       | `binary(16)` | `PK, NOT NULL`         |
| `quantity` | `int`        | `NOT NULL`             |
| `cart_id`  | `binary(16)` | `FK → cart.id`         |
| `product_id` | `binary(16)` | `FK → product.id`     |

---

### **5. `product`**
| Column         | Type          | Constraints      |
|--------------|--------------|-----------------|
| `id`        | `binary(16)`  | `PK, NOT NULL`  |
| `average_review` | `int`   | `NOT NULL`      |
| `brand`     | `varchar(255)` |                 |
| `category`  | `varchar(255)` |                 |
| `created_at` | `datetime(6)` |                 |
| `description` | `varchar(255)` |                |
| `image`     | `varchar(255)` |                 |
| `title`     | `varchar(255)` |                 |
| `total_stock` | `double`   | `NOT NULL`      |
| `updated_at` | `datetime(6)` |                 |

---

### **6. `product_review`**
| Column         | Type          | Constraints            |
|--------------|--------------|-----------------------|
| `id`        | `binary(16)`  | `PK, NOT NULL`       |
| `review_message` | `varchar(255)` |                   |
| `review_value` | `int`       | `NOT NULL`          |
| `user_name` | `varchar(255)` | `NOT NULL`          |
| `product_id` | `binary(16)`  | `FK → product.id`   |
| `user_id`  | `binary(16)`  | `FK → user.id`      |

---

### **7. `orders`**
| Column         | Type          | Constraints             |
|--------------|--------------|------------------------|
| `id`        | `binary(16)`  | `PK, NOT NULL`        |
| `address_id` | `binary(16)`  | `FK → address.id`     |
| `notes`     | `varchar(255)` |                        |
| `order_date` | `datetime(6)` |                        |
| `order_update_date` | `datetime(6)` |                |
| `order_status` | `tinyint`   |                        |
| `payer_id`  | `varchar(255)` |                        |
| `payment_method` | `varchar(255)` |                  |
| `payment_status` | `tinyint` |                        |
| `token`     | `varchar(255)` |                        |
| `total_amount` | `bigint`   |                        |
| `user_id`   | `binary(16)`  | `FK → user.id`        |

---

### **8. `order_item`**
| Column      | Type          | Constraints             |
|------------|--------------|------------------------|
| `id`       | `binary(16)`  | `PK, NOT NULL`        |
| `image`    | `varchar(255)` |                        |
| `price`    | `double`      |                        |
| `product_id` | `binary(16)`  | `FK → product.id`     |
| `quantity` | `int`        | `NOT NULL`            |
| `title`    | `varchar(255)` |                        |
| `order_id` | `binary(16)`  | `FK → orders.id`      |
| `order_items_id` | `binary(16)` |                    |

---

### **9. `address`**
| Column      | Type         | Constraints            |
|------------|------------|-----------------------|
| `id`       | `binary(16)` | `PK, NOT NULL`       |
| `address`  | `varchar(255)` |                   |
| `notes`    | `varchar(255)` |                   |
| `phone`    | `varchar(255)` |                   |
| `pincode`  | `varchar(255)` |                   |
| `updated_at` | `datetime(6)` |                   |

---

### **10. `feature`**
| Column      | Type          | Constraints          |
|------------|--------------|---------------------|
| `id`       | `binary(16)`  | `PK, NOT NULL`     |
| `created_at` | `datetime(6)` |                     |
| `image`    | `varchar(255)` |                     |
| `updated_at` | `datetime(6)` |                     |

---


## 🔒 Security
- **Public:** `/api/v1/auth/**`
- **Authenticated:** `/api/v1/**`
- **Admin-Only:** `/api/v1/admin/**`

This document serves as a guide to effectively utilize the API endpoints. 🚀
