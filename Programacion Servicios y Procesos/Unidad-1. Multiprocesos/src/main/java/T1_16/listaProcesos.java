package T1_16;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class listaProcesos {
    public static void main(String[] args) {
        try {
            String lineaSalida = "";

            // Creamos un nuevo proceso (como estoy en Linux, el equivalente a TaskList es "ps -eo pid,comm")
            ProcessBuilder processBuilder = new ProcessBuilder("ps", "-eo", "pid,tty,comm");

            // Ejecutamos el proceso
            Process proceso = processBuilder.start();

            // Le tomamos prestada la salida del proceso al sistema operativo
            BufferedReader salidaSistema = new BufferedReader(new InputStreamReader(proceso.getInputStream()));

            // Esperamos a que termine
            proceso.waitFor();

            // Leemos línea a línea la salida del proceso
            while ((lineaSalida = salidaSistema.readLine()) != null) {

                // En Linux, el equivalente a TASKLIST no devuelve Service o Console...
                // Para poder hacer la tarea, lo simularemos agregando Service cuando el proceso no tenga un tty asignado
                if (lineaSalida.contains("?")) lineaSalida += " Service";

                // O agregando Console cuando tenga un tty asignado
                else lineaSalida += " Console";

                System.out.println(lineaSalida);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
