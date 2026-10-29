package offline.chat;

import offline.chat.helper.GestorChat;
import offline.chat.objetos.Mensaje;
import offline.chat.objetos.Usuario;

import jakarta.xml.bind.JAXBException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    // ===== ATRIBUTOS ===== \\
    private static final Scanner TECLADO = new Scanner(System.in);
    private static final Pattern PATRON_ADJUNTO = Pattern.compile("\\{([^}]+)\\}");

    // ===== MAIN ===== \\
    /**
     * Este metodo es el encargado de arrancar la aplicación, pedir el usuario,
     * el destinatario y lanzar el bucle de chat
     * @param args Argumentos de la línea de comandos (no se usan)
     * @throws JAXBException Si hay un error al leer o escribir los archivos XML
     */
    public static void main(String[] args) throws JAXBException {
        GestorChat gestorChat = new GestorChat();

        System.out.println("Chat Offline - Proyecto UD1");
        System.out.println();

        while (true) {
            String usuario = pedirUsuario(gestorChat);
            if (usuario == null) break;

            String destinatario = pedirDestinatario(gestorChat, usuario);
            if (destinatario == null) continue;

            mostrarHistorial(gestorChat, usuario, destinatario);
            bucleChat(gestorChat, usuario, destinatario);
        }

        System.out.println("Hasta luego.");
        TECLADO.close();
    }

    // ===== LOGIN ===== \\
    /**
     * Este metodo es el encargado de gestionar el login del usuario
     * @param gestorChat Es la instancia del gestor de chats que estamos usando
     * @return Nombre de usuario si es correcto, null si es para salir
     */
    private static String pedirUsuario(GestorChat gestorChat) {
        while (true) {
            System.out.print("Usuario (vacío o 'salir' para cerrar): ");
            String nombre = TECLADO.nextLine().trim();

            if (nombre.isEmpty() || nombre.equalsIgnoreCase("salir")) {
                return null;
            }

            try {
                Usuario usuario = gestorChat.buscarPorNombre(nombre);
                if (usuario != null) {
                    return usuario.getNombre();
                }

                System.out.print("No existe '" + nombre + "'. ¿Registrarlo? (s/n): ");
                String respuesta = TECLADO.nextLine().trim();
                if (respuesta.equalsIgnoreCase("s") || respuesta.equalsIgnoreCase("si")) {
                    gestorChat.registrarUsuario(nombre);
                    System.out.println("Usuario '" + nombre + "' registrado.");
                    return nombre;
                }
            } catch (Exception excepcion) {
                System.out.println("Error: " + excepcion.getMessage());
            }
        }
    }

    /**
     * Este metodo es el encargado de solicitar el destinatario para el cual se enviaran los mensajes
     * @param gestorChat La instancia del gestor que estamos usando
     * @param usuarioEmisor El usuario registrado que envia el mensaje
     * @return el nombre del destinatario, null si esta vacio
     */
    private static String pedirDestinatario(GestorChat gestorChat, String usuarioEmisor) {
        while (true) {
            System.out.print("Destinatario (vacío para cambiar de usuario): ");
            String nombre = TECLADO.nextLine().trim();

            if (nombre.isEmpty()) {
                return null;
            }

            if (nombre.equalsIgnoreCase(usuarioEmisor)) {
                System.out.println("No puedes chatear contigo mismo.");
                continue;
            }

            try {
                Usuario destinatario = gestorChat.buscarPorNombre(nombre);
                if (destinatario != null) {
                    return destinatario.getNombre();
                }
                System.out.println("No existe el usuario '" + nombre + "'. Usuarios registrados:");
                for (Usuario usuario : gestorChat.listarUsuarios()) {
                    if (!usuario.getNombre().equals(usuarioEmisor)) {
                        System.out.println("  - " + usuario.getNombre());
                    }
                }
            } catch (Exception excepcion) {
                System.out.println("Error: " + excepcion.getMessage());
            }
        }
    }

    // ===== BUCLE DE CHAT ===== \\
    /**
     * Este metodo es el encargado de crear el bucle de chat y gestionarlo
     * @param gestorChat La instancia del gestor de chat que estamos utilizando
     * @param usuario El usuario registrado
     * @param destinatario El usuario al cual se le enviará el mensaje
     */
    private static void bucleChat(GestorChat gestorChat, String usuario, String destinatario) {
        System.out.println();
        System.out.println("=== Chat con " + destinatario + " ===");
        System.out.println("Comandos: /help /exit /quit /history /stats /export /users");
        System.out.println("Adjuntos: escribe {ruta/al/archivo} dentro del mensaje");
        System.out.println();

        while (true) {
            System.out.print(usuario + "> ");
            String linea = TECLADO.nextLine();

            if (linea.isBlank()) continue;
            String lineaRecortada = linea.trim();

            try {
                if (lineaRecortada.startsWith("/")) {
                    if (!procesarComando(gestorChat, lineaRecortada, usuario, destinatario)) {
                        return; // /exit o /quit
                    }
                    continue;
                }

                Matcher matcherAdjunto = PATRON_ADJUNTO.matcher(lineaRecortada);
                if (matcherAdjunto.find()) {
                    enviarConAdjunto(gestorChat, lineaRecortada, matcherAdjunto, usuario, destinatario);
                } else {
                    gestorChat.enviarMensaje(usuario, destinatario, lineaRecortada);
                }
            } catch (IllegalArgumentException excepcionArgumento) {
                System.out.println("Aviso: " + excepcionArgumento.getMessage());
            } catch (Exception excepcion) {
                System.out.println("Error: " + excepcion.getMessage());
            }
        }
    }

    // ===== ADJUNTOS ===== \\
    /**
     * Este metodo es el encargado de enviar un mensaje con un archivo adjunto,
     * extrayendo la ruta del adjunto del texto del mensaje y guardándolo en media/
     * @param gestorChat La instancia del gestor de chat que estamos utilizando
     * @param linea El mensaje completo escrito por el usuario, con la ruta entre llaves
     * @param matcher El matcher que contiene la ruta del archivo adjunto capturada
     * @param usuario El usuario que envía el mensaje
     * @param destinatario El usuario al que va dirigido el mensaje
     * @throws Exception Si hay algún error al adjuntar el archivo
     */
    private static void enviarConAdjunto(GestorChat gestorChat, String linea, Matcher matcher,
                                         String usuario, String destinatario) throws Exception {
        String rutaTexto = matcher.group(1).trim();
        Path rutaAdjunto = Path.of(rutaTexto);

        String textoMensaje = (linea.substring(0, matcher.start())
                + linea.substring(matcher.end())).trim();
        if (textoMensaje.isEmpty()) {
            textoMensaje = "(adjunto) " + rutaAdjunto.getFileName();
        }

        String fechaActual = LocalDateTime.now().withNano(0).toString();
        Mensaje mensaje = new Mensaje(usuario, destinatario, textoMensaje, fechaActual);

        gestorChat.adjuntarArchivo(mensaje, rutaAdjunto);
        System.out.println("Adjunto enviado: " + rutaAdjunto.getFileName());
    }

    // ===== COMANDOS ===== \\
    /**
     * Este metodo es el encargado de procesar los comandos que empiezan por "/",
     * como /exit, /help, /history, /stats, /export, /users o /quit
     * @param gestorChat La instancia del gestor de chat que estamos utilizando
     * @param linea El comando escrito por el usuario
     * @param usuario El usuario que está en sesión
     * @param destinatario El usuario con el que se está chateando
     * @return true si el chat debe continuar, false si el usuario quiere salir del chat
     * @throws Exception Si hay algún error al ejecutar el comando
     */
    private static boolean procesarComando(GestorChat gestorChat, String linea,
                                           String usuario, String destinatario) throws Exception {

        String comando = linea.toLowerCase().split("\\s+")[0];

        switch (comando) {
            case "/exit":
                System.out.println("Saliendo del chat con " + destinatario + ".");
                return false;

            case "/quit":
                System.out.println("Cerrando aplicación.");
                System.exit(0);
                return false;

            case "/help":
                mostrarAyuda();
                return true;

            case "/history":
                mostrarHistorial(gestorChat, usuario, destinatario);
                return true;

            case "/stats":
                mostrarEstadisticas(gestorChat, usuario, destinatario);
                return true;

            case "/export":
                exportarConversacion(gestorChat, usuario, destinatario);
                return true;

            case "/users":
                mostrarOtrosUsuarios(gestorChat, usuario);
                return true;

            default:
                System.err.println("Comando desconocido: " + comando + ". Usa /help.");
                return true;
        }
    }

    /**
     * Este metodo es el encargado de mostrar por pantalla los comandos disponibles
     * y la ayuda sobre cómo usar los adjuntos
     */
    private static void mostrarAyuda() {
        System.out.println();
        System.out.println("--- Comandos disponibles ---");
        System.out.println("  /help      muestra esta ayuda");
        System.out.println("  /history   ver conversación actual");
        System.out.println("  /stats     estadísticas de la conversación");
        System.out.println("  /export    exportar conversación a .txt");
        System.out.println("  /users     listar otros usuarios registrados");
        System.out.println("  /exit      salir del chat (volver a login)");
        System.out.println("  /quit      cerrar la aplicación");
        System.out.println();
        System.out.println("Adjuntos:");
        System.out.println("  Escribe {ruta/al/archivo} dentro del mensaje.");
        System.out.println("  Ejemplo:  mira esto {/home/foto.png}");
        System.out.println("  Ejemplo:  {/home/documento.pdf}");
        System.out.println();
    }

    // ===== HISTORIAL Y ESTADISTICAS ===== \\
    /**
     * Este metodo es el encargado de mostrar la conversación entre dos usuarios,
     * incluyendo la marca de adjunto si el mensaje tiene uno
     * @param gestorChat La instancia del gestor de chat que estamos utilizando
     * @param usuario El usuario que está en sesión
     * @param destinatario El usuario con el que se está chateando
     * @throws JAXBException Si hay un error al leer el archivo XML de mensajes
     */
    private static void mostrarHistorial(GestorChat gestorChat, String usuario, String destinatario)
            throws JAXBException {

        List<Mensaje> mensajes = gestorChat.consultarConversacion(usuario, destinatario);

        if (mensajes.isEmpty()) {
            System.out.println("No hay mensajes con " + destinatario + " todavía.");
            System.out.println();
            return;
        }

        System.out.println("--- Historial con " + destinatario + " ---");
        for (Mensaje mensaje : mensajes) {
            String sufijoAdjunto = "";
            if (mensaje.getAdjuntoRuta() != null) {
                boolean adjuntoDisponible = Files.exists(Path.of(mensaje.getAdjuntoRuta()));
                sufijoAdjunto = " [adjunto: " + mensaje.getAdjuntoNombre()
                        + (adjuntoDisponible ? "" : " (no disponible)") + "]";
            }
            String hora = extraerHora(mensaje.getFecha());
            System.out.println("  [" + hora + "] " + mensaje.getEmisor()
                    + ": " + mensaje.getContenido() + sufijoAdjunto);
        }
        System.out.println("--- Fin del historial ---");
        System.out.println();
    }

    /**
     * Este metodo es el encargado de extraer la hora de una fecha en formato ISO-8601
     * para mostrarla de forma más legible en el historial
     * @param fechaIso La fecha completa en formato ISO-8601 (ejemplo: 2026-10-29T14:32:00)
     * @return La hora en formato HH:MM, o la fecha original si no se puede procesar
     */
    private static String extraerHora(String fechaIso) {
        if (fechaIso == null || fechaIso.length() < 16 || !fechaIso.contains("T")) {
            return fechaIso;
        }
        return fechaIso.substring(11, 16);
    }

    /**
     * Este metodo es el encargado de mostrar las estadísticas de la conversación,
     * indicando el total de mensajes y cuántos ha enviado cada usuario
     * @param gestorChat La instancia del gestor de chat que estamos utilizando
     * @param usuario El usuario que está en sesión
     * @param destinatario El usuario con el que se está chateando
     * @throws JAXBException Si hay un error al leer el archivo XML de mensajes
     */
    private static void mostrarEstadisticas(GestorChat gestorChat, String usuario, String destinatario)
            throws JAXBException {

        Map<String, Long> conteoPorEmisor = gestorChat.estadisticas(usuario, destinatario);
        if (conteoPorEmisor.isEmpty()) {
            System.out.println("No hay mensajes.");
            return;
        }

        long totalMensajes = conteoPorEmisor.values().stream().mapToLong(Long::longValue).sum();

        System.out.println();
        System.out.println("--- Estadísticas " + usuario + " <-> " + destinatario + " ---");
        System.out.println("Total de mensajes: " + totalMensajes);
        conteoPorEmisor.forEach((nombreEmisor, cantidadMensajes) ->
                System.out.println("  " + nombreEmisor + ": " + cantidadMensajes));
        System.out.println();
    }

    // ===== EXPORTAR ===== \\
    /**
     * Este metodo es el encargado de exportar la conversación entre dos usuarios
     * a un archivo de texto plano
     * @param gestorChat La instancia del gestor de chat que estamos utilizando
     * @param usuario El usuario que está en sesión
     * @param destinatario El usuario con el que se está chateando
     * @throws Exception Si hay algún error al exportar la conversación
     */
    private static void exportarConversacion(GestorChat gestorChat, String usuario, String destinatario)
            throws Exception {

        Path rutaDestino = Path.of("conv_" + usuario + "_" + destinatario + ".txt");
        gestorChat.exportarConversacion(usuario, destinatario, rutaDestino);
        System.out.println("Exportado a " + rutaDestino.toAbsolutePath());
    }

    // ===== USUARIOS ===== \\
    /**
     * Este metodo es el encargado de mostrar por pantalla los usuarios registrados,
     * excluyendo al usuario que está en sesión
     * @param gestorChat La instancia del gestor de chat que estamos utilizando
     * @param usuario El usuario que está en sesión
     * @throws JAXBException Si hay un error al leer el archivo XML de usuarios
     */
    private static void mostrarOtrosUsuarios(GestorChat gestorChat, String usuario)
            throws JAXBException {

        System.out.println("Otros usuarios registrados:");
        boolean alguno = false;
        for (Usuario otroUsuario : gestorChat.listarUsuarios()) {
            if (!otroUsuario.getNombre().equals(usuario)) {
                System.out.println("  - " + otroUsuario.getNombre());
                alguno = true;
            }
        }
        if (!alguno) {
            System.out.println("  (no hay otros usuarios todavía)");
        }
    }
}