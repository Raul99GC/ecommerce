# Documentación de la API: `/api/v1/auth`

## Autenticación
### Método: Cookie con JWT
- **Descripción**: Se utiliza un token JWT para autenticar al usuario. El token se envía como una cookie en las solicitudes.
- **Encabezados necesarios**:
    - Para endpoints protegidos, el cliente debe enviar la cookie `token` obtenida al iniciar sesión.
- **Obtención del token**: El usuario debe iniciar sesión en el endpoint `/login`.

---

## Endpoints

### **1. Registro de Usuario**
- **Ruta**: `/api/v1/auth/register`
- **Método**: `POST`
- **Descripción**: Registra un nuevo usuario en el sistema.
- **Parámetros del cuerpo (Body)**:
  ```json
  {
    "userName": "string (requerido)",
    "email": "string (requerido)",
    "password": "string (requerido)"
  }
  ```
- **Respuesta exitosa (200)**:
  ```json
  {
    "success": true,
    "message": "Registration successful"
  }
  ```
- **Errores comunes**:
    - **409 Conflict**:
      ```json
      {
        "success": false,
        "message": "User Already exists with the same email! Please try again"
      }
      ```
    - **500 Internal Server Error**:
      ```json
      {
        "success": false,
        "message": "Some error occured"
      }
      ```

---

### **2. Inicio de Sesión**
- **Ruta**: `/api/v1/auth/login`
- **Método**: `POST`
- **Descripción**: Permite al usuario iniciar sesión y genera un token JWT.
- **Parámetros del cuerpo (Body)**:
  ```json
  {
    "email": "string (requerido)",
    "password": "string (requerido)"
  }
  ```
- **Respuesta exitosa (200)**:
  ```json
  {
    "success": true,
    "message": "Logged in successfully",
    "user": {
      "email": "string",
      "role": "string",
      "id": "string",
      "userName": "string"
    }
  }
  ```
- **Errores comunes**:
    - **404 Not Found**:
      ```json
      {
        "success": false,
        "message": "User doesn't exists! Please register first"
      }
      ```
    - **401 Unauthorized**:
      ```json
      {
        "success": false,
        "message": "Incorrect password! Please try again"
      }
      ```
    - **500 Internal Server Error**:
      ```json
      {
        "success": false,
        "message": "Some error occured"
      }
      ```

---

### **3. Cierre de Sesión**
- **Ruta**: `/api/v1/auth/logout`
- **Método**: `POST`
- **Descripción**: Cierra la sesión del usuario eliminando el token JWT de la cookie.
- **Respuesta exitosa (200)**:
  ```json
  {
    "success": true,
    "message": "Logged out successfully!"
  }
  ```

---

### **4. Verificación de Autenticación**
- **Ruta**: `/api/v1/auth/check-auth`
- **Método**: `GET`
- **Descripción**: Verifica si el usuario está autenticado.
- **Encabezados necesarios**:
    - Cookie: `token`
- **Respuesta exitosa (200)**:
  ```json
  {
    "success": true,
    "message": "Authenticated user!",
    "user": {
      "id": "string",
      "email": "string",
      "role": "string",
      "userName": "string"
    }
  }
  ```
- **Errores comunes**:
    - **401 Unauthorized**:
      ```json
      {
        "success": false,
        "message": "Unauthorised user!"
      }
      ```


# Diagrama de la Base de Datos MySQL


## Tabla: `users`
| Campo       | Tipo             | Restricciones                  |
|-------------|------------------|--------------------------------|
| id          | INT (PK, AI)     | PRIMARY KEY, AUTO_INCREMENT    |
| user_name   | VARCHAR(255)     | NOT NULL, UNIQUE               |
| email       | VARCHAR(255)     | NOT NULL, UNIQUE               |
| password    | VARCHAR(255)     | NOT NULL                       |
| role_id     | INT (FK)         | FOREIGN KEY -> `roles(id)`     |

### Relaciones:
- Un `user` pertenece a un `role`.
- Un `role` puede tener múltiples `users`.

---

## Tabla: `roles`
| Campo       | Tipo             | Restricciones                  |
|-------------|------------------|--------------------------------|
| id          | INT (PK, AI)     | PRIMARY KEY, AUTO_INCREMENT    |
| role_name   | VARCHAR(255)     | NOT NULL, UNIQUE               |

---

## Tabla: `carts`
| Campo       | Tipo             | Restricciones                  |
|-------------|------------------|--------------------------------|
| id          | INT (PK, AI)     | PRIMARY KEY, AUTO_INCREMENT    |
| user_id     | INT (FK)         | FOREIGN KEY -> `users(id)`     |
| created_at  | TIMESTAMP        | DEFAULT CURRENT_TIMESTAMP      |
| updated_at  | TIMESTAMP        | DEFAULT CURRENT_TIMESTAMP      |

---

## Tabla: `cart_items`
| Campo       | Tipo             | Restricciones                  |
|-------------|------------------|--------------------------------|
| id          | INT (PK, AI)     | PRIMARY KEY, AUTO_INCREMENT    |
| cart_id     | INT (FK)         | FOREIGN KEY -> `carts(id)`     |
| product_id  | INT (FK)         | FOREIGN KEY -> `products(id)`  |
| quantity    | INT              | NOT NULL, CHECK (quantity >= 1)|

---

## Tabla: `addresses`
| Campo       | Tipo             | Restricciones                  |
|-------------|------------------|--------------------------------|
| id          | INT (PK, AI)     | PRIMARY KEY, AUTO_INCREMENT    |
| user_id     | INT (FK)         | FOREIGN KEY -> `users(id)`     |
| address     | VARCHAR(255)     | NOT NULL                       |
| city        | VARCHAR(255)     | NOT NULL                       |
| pincode     | VARCHAR(20)      | NOT NULL                       |
| phone       | VARCHAR(20)      | NOT NULL                       |
| notes       | TEXT             | NULL                           |
| created_at  | TIMESTAMP        | DEFAULT CURRENT_TIMESTAMP      |
| updated_at  | TIMESTAMP        | DEFAULT CURRENT_TIMESTAMP      |

---

## Tabla: `features`
| Campo       | Tipo             | Restricciones                  |
|-------------|------------------|--------------------------------|
| id          | INT (PK, AI)     | PRIMARY KEY, AUTO_INCREMENT    |
| image       | VARCHAR(255)     | NULL                           |
| created_at  | TIMESTAMP        | DEFAULT CURRENT_TIMESTAMP      |
| updated_at  | TIMESTAMP        | DEFAULT CURRENT_TIMESTAMP      |

---

## Tabla: `orders`
| Campo          | Tipo             | Restricciones                  |
|----------------|------------------|--------------------------------|
| id             | INT (PK, AI)     | PRIMARY KEY, AUTO_INCREMENT    |
| user_id        | INT (FK)         | FOREIGN KEY -> `users(id)`     |
| address_id     | INT (FK)         | FOREIGN KEY -> `addresses(id)` |
| order_status   | VARCHAR(50)      | NULL                           |
| payment_method | VARCHAR(50)      | NULL                           |
| payment_status | VARCHAR(50)      | NULL                           |
| total_amount   | DECIMAL(10, 2)   | NOT NULL                       |
| order_date     | DATETIME         | NOT NULL                       |
| order_update_date | DATETIME      | NULL                           |
| payment_id     | VARCHAR(255)     | NULL                           |
| payer_id       | VARCHAR(255)     | NULL                           |

---

## Tabla: `order_cart_items`
| Campo       | Tipo             | Restricciones                  |
|-------------|------------------|--------------------------------|
| id          | INT (PK, AI)     | PRIMARY KEY, AUTO_INCREMENT    |
| order_id    | INT (FK)         | FOREIGN KEY -> `orders(id)`    |
| product_id  | INT (FK)         | FOREIGN KEY -> `products(id)`  |
| title       | VARCHAR(255)     | NOT NULL                       |
| image       | VARCHAR(255)     | NULL                           |
| price       | DECIMAL(10, 2)   | NOT NULL                       |
| quantity    | INT              | NOT NULL                       |

---

## Tabla: `products`
| Campo          | Tipo             | Restricciones                  |
|----------------|------------------|--------------------------------|
| id             | INT (PK, AI)     | PRIMARY KEY, AUTO_INCREMENT    |
| image          | VARCHAR(255)     | NULL                           |
| title          | VARCHAR(255)     | NOT NULL                       |
| description    | TEXT             | NULL                           |
| category       | VARCHAR(255)     | NULL                           |
| brand          | VARCHAR(255)     | NULL                           |
| price          | DECIMAL(10, 2)   | NOT NULL                       |
| sale_price     | DECIMAL(10, 2)   | NULL                           |
| total_stock    | INT              | NOT NULL                       |
| average_review | DECIMAL(3, 2)    | NULL                           |
| created_at     | TIMESTAMP        | DEFAULT CURRENT_TIMESTAMP      |
| updated_at     | TIMESTAMP        | DEFAULT CURRENT_TIMESTAMP      |

---

## Tabla: `product_reviews`
| Campo          | Tipo             | Restricciones                  |
|----------------|------------------|--------------------------------|
| id             | INT (PK, AI)     | PRIMARY KEY, AUTO_INCREMENT    |
| product_id     | INT (FK)         | FOREIGN KEY -> `products(id)`  |
| user_id        | INT (FK)         | FOREIGN KEY -> `users(id)`     |
| user_name      | VARCHAR(255)     | NOT NULL                       |
| review_message | TEXT             | NULL                           |
| review_value   | INT              | NOT NULL, CHECK (value >= 1 AND value <= 5) |
| created_at     | TIMESTAMP        | DEFAULT CURRENT_TIMESTAMP      |
| updated_at     | TIMESTAMP        | DEFAULT CURRENT_TIMESTAMP      |

---