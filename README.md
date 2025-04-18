# P5
Aplicación web que usa Spring JPA para persistir los datos de un API REST de gestión de usuarios.
El API permite el registro de nuevos usuarios y su identificación mediante email y password.
Una vez identificados, se emplea una cookie de sesión para autenticar las peticiones que permiten 
a los usuarios leer, modificar y borrar sus datos. También existe un endpoint para cerrar la sesión.  

## Endpoints

// TODO#1: rellena la tabla siguiente analizando el código del proyecto

| Método | Ruta                  | Descripción                 | Respuestas                                                |
|--------|-----------------------|-----------------------------|-----------------------------------------------------------|
| POST   | /api/users            | Registra un nuevo usuario   | 201 Created con perfil si OK, 409 Conflict si ya existe   |
| POST   | /api/users/me/session | Inicia sesión (Login)       | 201 Created + cookie de sesión, 401 Unauthorized si falla |
| DELETE | /api/users/me/session | Cierra sesión (Logout)      | 204 No Content, borra cookie de sesión                    |
| DELETE | /api/users/me         | Elimina usuario autenticado | 200 OK con perfil, 401 Unauthorized si sesión inválida    |
| GET    | /api/users/me         | Obtiene datos del usuario   | 200 OK con perfil actualizado, 401 Unauthorized           |
| PUT    | /api/users/me         | Modifica datos del usuario  | 204 No Content, 401 Unauthorized                          |


## Comandos 

- Construcción: 
  ```sh
  ./mvnw clean package
  ```

- Ejecución: 
  ```sh
  ./mvnw spring-boot:run
  ```

- Tests:
  ```sh
  ./mvnw test
  ```
