```markdown
# Chat Offline

Chat de consola con persistencia en XML usando JAXB.

## Requisitos

- Java 17+
- Maven

## Ejecución

```bash
mvn clean package
java -jar target/chat-offline-1.0.0.jar
```

## Uso

1. Introduce tu usuario (si no existe, te ofrece registrarlo).
2. Introduce el destinatario.
3. Escribe mensajes. `/exit` para salir.

## Comandos

| Comando | Descripción |
|---|---|
| `/help` | Ayuda |
| `/history` | Ver conversación |
| `/stats` | Estadísticas |
| `/export` | Exportar a .txt |
| `/users` | Otros usuarios |
| `/exit` | Salir del chat |
| `/quit` | Cerrar app |

## Adjuntos

Escribe `{ruta/al/archivo}` dentro de un mensaje.

- Máx. 10 MB
- Extensiones: `png`, `jpg`, `jpeg`, `gif`, `pdf`, `txt`
- Se copian a `media/`

## Archivos generados

- `usuarios.xml`
- `mensajes.xml`
- `media/`
```
