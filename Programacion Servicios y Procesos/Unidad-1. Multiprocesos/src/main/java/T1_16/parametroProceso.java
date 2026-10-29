package T1_16;

import java.time.LocalTime;

public class parametroProceso {
    public static void main(String[] args) {
        // Tomamos los minutos actuales, y si es par devolvemos "Console"
        if (LocalTime.now().getMinute() % 2 == 0) System.out.println("Console");

        // En caso contrario, devolvemos "Services"
        else System.out.println("Service");
    }
}
