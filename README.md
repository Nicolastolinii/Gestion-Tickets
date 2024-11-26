# Proyecto de Gestión de Eventos y Tickets - API REST

## Descripción

Este proyecto es una API REST desarrollada con Java y Spring Boot que permite gestionar eventos y tickets. Los usuarios pueden realizar acciones como crear, actualizar, eliminar y consultar eventos, así como administrar tickets para los eventos. Incluye validación de tickets y manejo de excepciones personalizadas.

## Tecnologías Utilizadas
#### Backend
- **Java**: Lenguaje de programación principal.
- **Spring Boot**: Framework para el desarrollo de aplicaciones web modernas.
- **Spring Security**: Para la autenticación y autorización de usuarios mediante JWT.
- **Spring Data JPA**: Acceso a datos con soporte para ORM.
- **Hibernate**: Implementación de JPA para la persistencia de datos.

#### Base de Datos
- **MySQL**: Base de datos relacional utilizada para almacenar información de usuarios, eventos y tickets.

#### Herramientas Adicionales
- **Lombok**: Simplificación del código Java.
- **MapStruct**: Para el mapeo entre entidades y DTOs.
- **Maven**: Gestión de dependencias y construcción del proyecto.
---
## ⚙️ **Funcionalidades**



### ✨ **Autenticación y Registro de Usuarios**
- 🔑 **Registro de Usuarios**:  
  Permite registrar nuevos usuarios en la plataforma con validación de datos para garantizar la integridad de la información.  
  - Se verifica que no existan correos duplicados.
  - Contraseñas encriptadas para mayor seguridad.  

- 🔒 **Autenticación con JWT**:  
  - Se generan tokens JWT (JSON Web Tokens) para manejar sesiones de manera segura.  
  - Los tokens se utilizan para proteger endpoints y garantizar el acceso únicamente a usuarios autenticados.


### 🎟️ **Gestión de Tickets**
- 🛒 **Compra de Tickets**:  
  Los usuarios pueden adquirir tickets para eventos disponibles.  
  - Se valida automáticamente la cantidad disponible antes de procesar la compra.

- ✅ **Verificación de Disponibilidad**:  
  - Control automático para evitar que se exceda la capacidad permitida de un evento.

- 🚫 **Evitar la Sobreventa**:  
  - Implementación de lógica que establece el estado de agotamiento (**soldOut**) cuando no hay más tickets disponibles.

- 🗑️ **Eliminación de Tickets**:  
  Los usuarios pueden cancelar tickets previamente adquiridos.  



### 🗓️ **Gestión de Eventos**
- ✍️ **Creación de Eventos**:  
  - Los administradores pueden definir nuevos eventos con detalles como:  
    - Nombre del evento.  
    - Descripción.  
    - Fecha y hora.  
    - Capacidad máxima.  

- 🔄 **Actualización de Eventos**:  
  - Modificación de los detalles del evento ya creado.  
  - Permite ajustar capacidad, fechas o cualquier información importante.

- 🚦 **Control de Estado (soldOut)**:  
  - Automatización del estado de agotamiento cuando un evento ya no tiene tickets disponibles.  

- 🗑️ **Eliminación de Eventos**:  
  - Los administradores pueden eliminar eventos que ya no estén disponibles o sean cancelados.  



### 🔒 **Gestión de Roles y Seguridad**
- 👥 **Protección de Endpoints**:  
  - Endpoints configurados según los roles de usuario:  
    - Usuarios autenticados.  
    - Administradores con permisos elevados.  

- 🛡️ **Autorización Granular**:  
  - Permisos específicos según el nivel de acceso para realizar ciertas acciones como:  
    - Crear, editar o eliminar eventos.  
    - Comprar o cancelar tickets.  

## 📚 **Endpoints de la API**


### 🗓️ **Eventos**

#### 1️⃣ **Obtener Evento por ID**
- **Endpoint**: `/api/eventos/{eventoId}`  
- **Método**: `GET`  
- **Descripción**: Obtiene la información de un evento específico por su ID.  

#### Respuesta Exitosa:
```json
{
    "id": 1,
    "nombre": "Evento 1",
    "fecha": "2024-12-01",
    "capacidad": 100
}
```
```bash
curl -X GET http://localhost:8080/api/eventos/1
```

#### 2️⃣ Actualizar Evento
- **Endpoint**: `/api/eventos/update/{eventId}`
- **Método**: `PUT`
- **Descripción**: Actualiza los detalles de un evento existente.

Cuerpo de la Solicitud:
```json
{
    "nombre": "Nuevo Evento",
    "fecha": "2024-12-15",
    "capacidad": 200
}
```
Respuesta Exitosa:
```json
{
    "message": "Evento actualizado exitosamente"
}
```
#### 3️⃣ Eliminar Evento
- **Endpoint**: `/api/eventos/delete/{eventId}`
- **Método**: `DELETE`
- **Descripción**: Elimina un evento existente por su ID.

Respuesta Exitosa:
```json
204 No Content
```

#### 4️⃣ Obtener Todos los Eventos
- **Endpoint**: `/api/eventos/all`
- **Método**: `GET`
- **Descripción**: Obtiene una lista de todos los eventos.
Respuesta Exitosa:
```json
[
    {
        "id": 1,
        "nombre": "Evento 1",
        "fecha": "2024-12-01",
        "capacidad": 100
    },
    {
        "id": 2,
        "nombre": "Evento 2",
        "fecha": "2024-12-15",
        "capacidad": 200
    }
]
```
#### 5️⃣ Crear Evento
- **Endpoint**: `/api/eventos/create`
- **Método**: `POST`
- **Descripción**: Crea un nuevo evento.

Cuerpo de la Solicitud:
```json
{
    "nombre": "Nuevo Evento",
    "fecha": "2024-12-15",
    "capacidad": 300
}
```
Respuesta Exitosa:
```json
{
    "message": "creado con exito"
}
```
### 🎟️ Tickets
#### 1️⃣ Obtener Tickets por Usuario
- **Endpoint**: `/api/eventos/user/{userId}`
- **Método**: `GET`
- **Descripción**: Obtiene una lista de tickets adquiridos por un usuario.

Respuesta Exitosa:
```json
[
    {
        "ticketId": 1,
        "evento": "Evento 1",
        "codigo": "ABC123"
    },
    {
        "ticketId": 2,
        "evento": "Evento 2",
        "codigo": "DEF456"
    }
]
```
#### 2️⃣ Verificar Ticket
- **Endpoint**: /api/eventos/verify
- **Método**: GET
- **Descripción**: Verifica si un ticket es válido para un evento específico.

Parámetros de Consulta:
- **code**: Código del ticket.
- **eventoId**: ID del evento.

Respuesta Exitosa:
```json
{
    "message": "ticket valido."
}
```

3️⃣ Crear Ticket
-**Endpoint**: `/api/eventos/tickets`
-**Método**: `POST`
-**Descripción**: Genera un nuevo ticket para un usuario en un evento.

Cuerpo de la Solicitud:
```json
{
    "userId": 1,
    "eventoId": 1,
    "cantidad": 2
}
```
Respuesta Exitosa:
```json
{
    "ticketId": 1,
    "codigo": "ABC123",
    "cantidad": 2
}
```

4️⃣ Eliminar Ticket
-**Endpoint**: `/api/eventos/delete/{ticketId}`
-**Método**: `DELETE`
-**Descripción**: Elimina un ticket existente.

Respuesta Exitosa:
```json
204 No Content
```

### 👤 Usuarios
#### 1️⃣ Login
- **Endpoint**: `/api/login`
- **Método**: `POST`
- **Descripción**: Permite a un usuario autenticarse y recibir un token JWT.

Cuerpo de la Solicitud:
```json
{
    "email": "usuario@example.com",
    "password": "contraseña"
}
```
Respuesta Exitosa:
```json
{
    "token": "jwt_token"
}
```

#### 2️⃣ Registrar Usuario
- **Endpoint**: `/api/register`
- **Método**: `POST`
- **Descripción**: Registra un nuevo usuario en el sistema.

Cuerpo de la Solicitud:
```json
{
    "email": "usuario@example.com",
    "password": "contraseña",
    "nombre": "Nuevo Usuario"
}
```
Respuesta Exitosa:
```json
{
    "message": "Usuario registrado con éxito"
}
```
#### 3️⃣ Obtener Usuario por ID
- **Endpoint**: `/api/v1/user/{userId}`
- **Método**: `GET`
- **Descripción**: Obtiene la información de un usuario específico por su ID.

Cuerpo de la Solicitud:
```json
{
    "userId": 1,
    "nombre": "Usuario 1",
    "email": "usuario1@example.com"
}
```
Respuesta de Error (Usuario no encontrado):
```json
{
    404 Not Found
}
```

--- 
## Instalación

 **Clonar el repositorio**:
   ```bash
   git clone https://github.com/Nicolastolinii/Gestion-Tickets.git
   ```

#### Configurar la base de datos:

Crea una base de datos y configura las credenciales en ```application.properties```.

# Licencia
Este proyecto está licenciado bajo la Licencia MIT.


