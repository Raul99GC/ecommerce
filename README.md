# 📘 API Documentation

## 📝 Project Description
This project is a complete eCommerce backend solution built with **Spring Boot** and **MySQL**. It includes features such as user and admin dashboards, product management, shopping cart functionality, payment processing via **PayPal (Sandbox)**, a review system, order tracking, and secure authentication using **JWT**. The application integrates with **Cloudinary** for image storage and retrieval, ensuring efficient handling of multimedia content.

This project is inspired by [**mern-ecommerce-2024**](https://github.com/sangammukherjee/mern-ecommerce-2024) by [**sangammukherjee**](https://github.com/sangammukherjee), utilizing its API structure and UI template. However, while the original backend is built with **Node.js, Express, and MongoDB**, this version has been developed **entirely from scratch** using **Spring Boot and MySQL**.

## 🎨 Front-End Overview
The front-end of this project provides a user-friendly interface for browsing products, managing shopping carts, and completing purchases. For more detailed information, including screenshots of the user interface and instructions on how to initialize the front-end, please refer to the [Front-End Documentation](https://github.com/Raul99GC/ecommerce/tree/main/client-ecommerce).

### 🛠 Technologies Used
- **Java**
- **Spring Boot**
- **MySQL**
- **JWT Authentication**
- **PayPal SDK**
- **Cloudinary SDK**

### 🔐 JWT Token Transmission
The JWT token can be provided in two ways:
- **Header:** Include the token in the request header using the parameter `token`.
- **Cookie:** The backend sets the token as a cookie upon successful login, allowing it to be sent automatically with subsequent requests.

Below are the categorized endpoints based on their security levels.

## 🔓 Public Endpoints (No Authentication Required)
These endpoints are accessible to any user without authentication.

### 🛂 Authentication (`AuthController`)
- `POST /api/v1/auth/login` - Log in
- `POST /api/v1/auth/logout` - Log out
- `POST /api/v1/auth/register` - Register user
- `GET /api/v1/auth/check-auth` - Check authentication status

## 🔐 Protected Endpoints (Authentication Required)
These endpoints require the user to be authenticated.

### 🛒 Shop (`ProductsController`)
- `GET /api/v1/shop/products/get` - Get products
- `GET /api/v1/shop/products/get/{productId}` - Get specific product

### 📝 Reviews (`ProductReviewController`)
- `POST /api/v1/shop/review/add` - Add a review
- `GET /api/v1/shop/review/{productId}` - Get product reviews

### 📦 Orders (`OrderShopController`)
- `POST /api/v1/shop/order/capture` - Capture order
- `POST /api/v1/shop/order/create` - Create order
- `GET /api/v1/shop/order/details/{orderId}` - Get order details
- `GET /api/v1/shop/order/list/{userId}` - List user orders

### 🔎 Search (`SearchController`)
- `GET /api/v1/shop/search/{keywords}` - Search products by keywords

### 📍 Addresses (`AddressController`)
- `POST /api/v1/shop/address/add` - Add address
- `DELETE /api/v1/shop/address/delete/{userId}/{addressId}` - Delete address
- `GET /api/v1/shop/address/get/{userId}` - Get user addresses
- `PUT /api/v1/shop/address/update/{userId}/{addressId}` - Update address

### 🛒 Cart (`CartController`)
- `POST /api/v1/shop/cart/add` - Add product to cart
- `GET /api/v1/shop/cart/get/{userId}` - Get user cart
- `PUT /api/v1/shop/cart/update-cart` - Update cart
- `DELETE /api/v1/shop/cart/{userId}/{productId}` - Remove product from cart

## 🔐🔑 Admin-Only Endpoints
These endpoints require the user to have the `ADMIN` role.

### 📦 Product Management (`ProductsController`)
- `POST /api/v1/admin/products/add` - Add a product
- `DELETE /api/v1/admin/products/delete/{productId}` - Delete a product
- `POST /api/v1/admin/products/edit/{productId}` - Edit a product
- `GET /api/v1/admin/products/get` - Get all products
- `POST /api/v1/admin/products/upload-image` - Upload product image

### 📦 Order Management (`OrderAdminController`)
- `GET /api/v1/admin/orders/details/{orderId}` - Get order details
- `GET /api/v1/admin/orders/get` - Get order list
- `PUT /api/v1/admin/orders/update-status/{orderId}` - Update order status

### ⚙️ Common Features (`FeatureController`)
- `POST /api/v1/common/feature/add` - Add a common feature (Admin only)
- `GET /api/v1/common/feature/get` - Get common features

---

## 🔒 Security
- **Public:** `/api/v1/auth/**`
- **Authenticated:** `/api/v1/**`
- **Admin-Only:** `/api/v1/admin/**`, `/api/v1/common/feature/add`

This document facilitates the use and control of the API according to access levels. 🚀
