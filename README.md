# 📦 Sistema de Gestión de Pedidos – Spring Boot & Kafka

Este proyecto consiste en desarrollar un sistema de gestión de pedidos utilizando un stack tecnológico moderno basado en Spring Boot, JPA, Kafka y PostgreSQL. Se implementa autenticación con JWT mediante Spring Security y una arquitectura MVC para mantener la claridad del código.

---

## 🎯 Objetivos del Proyecto

- Desarrollar un sistema de gestión de pedidos eficiente y seguro.
- Utilizar arquitectura MVC (Controlador - Servicio - Repositorio).
- Aplicar autenticación con JWT y control de acceso por roles.
- Comunicar eventos asincrónicos mediante Apache Kafka.

---

## 🔧 Tecnologías Utilizadas

- Java 21
- Spring Boot
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- PostgreSQL
- Apache Kafka
- Postman (pruebas de endpoints)
- Springdoc OpenAPI (Swagger)

---

## 🧱 Arquitectura del Proyecto

- **Controladores:** Exponen los endpoints REST.
- **Servicios:** Contienen la lógica de negocio.
- **Repositorios:** Acceden a la base de datos.


---

## ⚙️ Instalación y Configuración del Proyecto

### 1. Clonar el repositorio

```bash
git clone https://github.com/order-management-team/orderManagementAPI.git
cd group05
```

### 2. Crear la base de datos en PostgreSQL

Abrí tu herramienta de gestión (como pgAdmin o consola) y ejecutá:

```sql
CREATE DATABASE order_mgmt;
```

> Asegurate de tener PostgreSQL en ejecución y recordar tus credenciales.

### 3. Configurar `application.yml`

El archivo se encuentra en:  
`src/main/resources/application.yml`

Ejemplo de configuración:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/order_mgmt
    username: tu_usuario
    password: tu_contraseña
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

server:
  port: 8080

jwt:
  private:
    key: tu_clave_secreta
  user:
    generator: your_group
```

> ⚠️ Nunca subas tu clave secreta JWT o credenciales a un repositorio público.

---

### 4. Ejecutar la aplicación

Desde la raíz del proyecto, usá:

```bash
./mvnw spring-boot:run
```

La app se iniciará en:  
[http://localhost:8080](http://localhost:8080)

---

## 🔐 Endpoints y Códigos de Estado

| Método | Endpoint           | Descripción              | Código HTTP | Respuesta                          |
|--------|--------------------|--------------------------|-------------|------------------------------------|
| POST   | `/auth/register`   | Registro de usuario      | 201         | Usuario registrado exitosamente   |
| POST   | `/auth/login`      | Inicio de sesión         | 200         | Login exitoso, JWT generado        |
| GET    | `/productos`       | Obtener productos        | 200         | Lista de productos                 |
| POST   | `/pedidos`         | Crear nuevo pedido       | 201         | Pedido creado exitosamente        |
| PUT    | `/pedidos/{id}`    | Actualizar pedido        | 200         | Pedido actualizado exitosamente   |
| DELETE | `/pedidos/{id}`    | Eliminar pedido          | 204         | Pedido eliminado                   |

---

## ❌ Códigos de Error

| Código | Descripción                  |
|--------|------------------------------|
| 200    | OK                           |
| 201    | Recurso creado               |
| 204    | Eliminado sin contenido      |
| 401    | No autenticado               |
| 403    | Acceso prohibido             |
| 404    | No encontrado                 |

---

## Documentación con Swagger
La API aun NO está documentada con **Springdoc OpenAPI** y se puede acceder en:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Entrega Final
- Repositorio Git con el código fuente.
- Archivo `README.md` con instrucciones de instalación y uso.
- Documentación generada con Swagger.


## ✅ Consideraciones Finales

Este proyecto busca ser una base sólida y escalable para sistemas de pedidos. Se recomienda mantener buenas prácticas de desarrollo, usar control de versiones, realizar pruebas automatizadas y documentar adecuadamente los cambios futuros.

