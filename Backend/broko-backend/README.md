
# 💸 Broko Backend

Backend del proyecto **Broko**, una billetera virtual basada en **arquitectura de microservicios**, diseñada con enfoque en **escalabilidad, mantenibilidad, seguridad, comunicación asincrónica con Kafka y despliegue independiente por servicio**.

---

## 🧱 Arquitectura del Sistema

```plaintext
broko-backend/
├── config-server/         # Configuración centralizada (Spring Cloud Config)
├── eureka-server/         # Descubrimiento de servicios (Eureka)
├── gateway-server/        # API Gateway con filtros y seguridad
├── identity-service/      # Registro, login, gestión de usuarios
├── finance-service/       # Billeteras, transacciones, conversión de moneda
├── events-service/        # Notificaciones y dashboard
└── fraud-service/         # Detección de fraude
````

---

## 🔧 Tecnologías Principales

* **Java 21 + Spring Boot 3.2**
* **Spring Cloud 2023**
* Eureka Discovery
* Spring Cloud Gateway
* Spring Cloud Config
* Spring Security + JWT
* Apache Kafka
* PostgreSQL *(opcional MySQL)*
* Swagger (SpringDoc OpenAPI)
* Lombok

---

## ⚙️ Requisitos Previos

| Requisito    | Versión                           |
| ------------ | --------------------------------- |
| Java         | 21                                |
| Maven        | 3.9+                              |
| PostgreSQL   | 5432                              |
| Apache Kafka | localhost:9092                    |
| Git          | Opcional para config-repo externo |

---

## 🚀 Cómo iniciar los servicios

Clonar el repositorio:

```bash
git clone https://github.com/tu-usuario/broko-backend.git
cd broko-backend
```

**Orden recomendado de inicio:**

1️⃣ `config-server`
2️⃣ `eureka-server`
3️⃣ `gateway-server`

Luego iniciar los microservicios:

* identity-service
* finance-service
* events-service
* fraud-service

**Verificar endpoints**

| Servicio          | URL                                                                            |
| ----------------- | ------------------------------------------------------------------------------ |
| Eureka Dashboard  | [http://localhost:8761](http://localhost:8761)                                 |
| Swagger (ejemplo) | [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html) |
| API Gateway       | [http://localhost:8080](http://localhost:8080)                                 |

---

## 📁 Estructura por Servicio

Cada microservicio adopta la siguiente convención:

```plaintext
src/main/java/com/broko/{servicio}/
├── config/
├── controller/
├── service/
├── repository/
├── entity/
├── dto/
├── exception/
└── kafka/ (si aplica)
```

---

## 🔐 Seguridad

* JWT generado por **identity-service**
* Validación de tokens en el **gateway-server**
* Roles disponibles:

  * `USER`
  * `ADMIN`

---

## 📬 Kafka Topics

| Topic                  | Emisor           | Consumidor                       |
| ---------------------- | ---------------- | -------------------------------- |
| transactions.completed | finance-service  | events-service, fraud-service    |
| wallet.updated         | finance-service  | events-service                   |
| users.registered       | identity-service | events-service                   |
| fraud.alerts           | fraud-service    | events-service, finance-service* |

* opcional: puede bloquear transacciones en tiempo real.

---

## 🧪 Testing

* Backend → JUnit 5 + Mockito
* API Docs → SpringDoc OpenAPI
* Kafka → Tests con EmbeddedKafka *(opcional)*

---

## 📎 Consideraciones

* Arquitectura **desacoplada y escalable**.
* Cada microservicio puede **desplegarse y escalarse** de manera independiente.
* Recomendaciones para producción:

  * CI/CD: Jenkins + SonarQube
  * Config repo externo
  * Monitoring: Prometheus + Grafana

---

## 🤝 Contribuciones

¿Tenés mejoras, ideas o sugerencias?
👉 **Abrí un Pull Request o crea un Issue.**

---

## 🧠 Autor

**Broko Dev Team – 2025**

