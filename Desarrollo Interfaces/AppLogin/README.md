# Login Taller Manolo

App Android en **Kotlin** con login, registro y recuperación de contraseña. Los usuarios se guardan en memoria (se pierden al cerrar la app).

## Funcionalidades

- **Login** con validación de campos y credenciales.
- **Registro** de nuevos usuarios (usuario mínimo 3 caracteres, contraseña mínimo 6, sin duplicados).
- **Recuperación de contraseña**: muestra la actual y permite cambiarla.
- **Pantalla de bienvenida** con el nombre del usuario.

## Usuarios de prueba

| Usuario | Contraseña |
|---------|------------|
| Manolo  | 123456     |
| Prueba  | prueba123  |

## Estructura

```
MainActivity.kt          → Login
RegistroActivity.kt      → Alta de usuarios
RecuperarActivity.kt     → Recuperar/cambiar contraseña
BienvenidaActivity.kt    → Pantalla tras el login
RepositorioUsuarios.kt   → Almacén de usuarios en memoria
```

## Limitaciones

- Sin persistencia: los datos se pierden al cerrar la app.
- Contraseñas en texto plano (ejercicio académico).
