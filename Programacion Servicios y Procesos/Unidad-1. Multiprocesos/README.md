# T1_16 — Multiprocesos en Java

Ejercicio de multiprocesos que simula `TASKLIST | FIND` usando `ProcessBuilder` y tuberías.

## Clases

- **`listaProcesos`** → Ejecuta `ps -eo pid,tty,comm` y muestra los procesos, añadiendo `Service` o `Console` según tengan terminal o no.
- **`parametroProceso`** → Devuelve `Console` o `Service` según si el minuto actual es par o impar.
- **`filtraProcesos`** → Ejecuta `parametroProceso`, usa su salida como filtro y muestra solo las líneas de la entrada que lo contienen.

## Ejecución

```bash
javac T1_16/*.java
java T1_16.listaProcesos | java T1_16.filtraProcesos
```

## Conceptos

- `ProcessBuilder` y `Process`
- Tuberías (`|`) y redirección de entrada/salida
- `BufferedReader` para leer línea a línea
