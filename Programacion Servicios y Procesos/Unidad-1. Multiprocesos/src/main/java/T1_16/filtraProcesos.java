package T1_16;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class filtraProcesos {
    public static void main(String[] args) {
        try {
            String lineaSalida = "";

            // Creamos un nuevo proceso para llamar a "parametroProceso"
            ProcessBuilder processBuilder = new ProcessBuilder("java", "T1_16.parametroProceso");

            // Ejecutamos el proceso
            Process proceso = processBuilder.start();

            // Le tomamos prestada la salida del proceso al sistema operativo
            BufferedReader salidaParametroProceso = new BufferedReader(new InputStreamReader(proceso.getInputStream()));

            // Guardamos la salida de "parametroProceso" en la variable "filtro"
            String filtro = salidaParametroProceso.readLine();

            // Preparamos al programa para que reciba los parametros desde la tubería usando "|"
            BufferedReader entradaTuberia = new BufferedReader(new InputStreamReader(System.in));

            // Leemos todas las líneas que recibe el programa
            while ((lineaSalida = entradaTuberia.readLine()) != null) {

                // Y solo la mostramos por pantalla cuando cumpla con el filtro
                if (lineaSalida.contains(filtro)) {
                    System.out.println(lineaSalida);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

}