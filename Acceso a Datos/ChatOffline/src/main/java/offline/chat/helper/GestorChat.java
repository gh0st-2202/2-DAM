package offline.chat.helper;

import jakarta.xml.bind.JAXBException;
import offline.chat.objetos.ListaMensajes;
import offline.chat.objetos.ListaUsuarios;
import offline.chat.objetos.Mensaje;
import offline.chat.objetos.Usuario;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class GestorChat {

    // ===== ATRIBUTOS ===== \\
    private static final long TAMANO_MAXIMO = 10L * 1024 * 1024; // 10 MB
    private static final Set<String> EXTENSIONES_PERMITIDAS =
            Set.of("png", "jpg", "jpeg", "gif", "pdf", "txt");
    private static final Path CARPETA_MEDIA = Path.of("media");

    private final GestorXML gestorXML = new GestorXML();

    // ===== METODOS USUARIOS ===== \\
    /**
     * Este metodo es el encargado de registrar un nuevo usuario comprobando
     * que no exista previamente y asignándole el siguiente id disponible
     * @param nombre Nombre del usuario a registrar
     * @throws JAXBException Si hay un error al leer o escribir usuarios.xml
     */
    public void registrarUsuario(String nombre) throws JAXBException {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre no puede estar vacío");

        ListaUsuarios listaUsuarios = gestorXML.leerUsuarios();
        boolean yaExiste = listaUsuarios.getUsuarios().stream()
                .anyMatch(usuario -> usuario.getNombre().equalsIgnoreCase(nombre));
        if (yaExiste)
            throw new IllegalArgumentException("Ya existe el usuario: " + nombre);

        int nuevoId = listaUsuarios.getUsuarios().stream()
                .mapToInt(Usuario::getId)
                .max()
                .orElse(0) + 1;

        listaUsuarios.getUsuarios().add(new Usuario(nuevoId, nombre));
        gestorXML.guardarUsuarios(listaUsuarios);
    }

    /**
     * Este metodo es el encargado de buscar un usuario por su nombre exacto
     * @param nombre Nombre del usuario a buscar
     * @return El usuario encontrado, o null si no existe
     * @throws JAXBException Si hay un error al leer usuarios.xml
     */
    public Usuario buscarPorNombre(String nombre) throws JAXBException {
        return gestorXML.leerUsuarios().getUsuarios().stream()
                .filter(usuario -> usuario.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }

    /**
     * Este metodo es el encargado de devolver todos los usuarios registrados
     * @return Lista con todos los usuarios del sistema
     * @throws JAXBException Si hay un error al leer usuarios.xml
     */
    public List<Usuario> listarUsuarios() throws JAXBException {
        return gestorXML.leerUsuarios().getUsuarios();
    }

    // ===== METODOS MENSAJES ===== \\
    /**
     * Este metodo es el encargado de enviar un mensaje de solo texto entre dos usuarios,
     * validando que ambos existan y que el contenido no esté vacío
     * @param nombreEmisor Nombre del usuario que envía el mensaje
     * @param nombreDestinatario Nombre del usuario que recibe el mensaje
     * @param contenido Texto del mensaje
     * @throws JAXBException Si hay un error al leer o escribir mensajes.xml
     */
    public void enviarMensaje(String nombreEmisor, String nombreDestinatario, String contenido)
            throws JAXBException {

        validarUsuarioRegistrado(nombreEmisor);
        validarUsuarioRegistrado(nombreDestinatario);

        if (nombreEmisor.equals(nombreDestinatario))
            throw new IllegalArgumentException("Emisor y destinatario no pueden coincidir");
        if (contenido == null || contenido.isBlank())
            throw new IllegalArgumentException("El contenido no puede estar vacío");

        String fechaActual = LocalDateTime.now().withNano(0).toString();
        Mensaje nuevoMensaje = new Mensaje(nombreEmisor, nombreDestinatario, contenido, fechaActual);

        ListaMensajes listaMensajes = gestorXML.leerMensajes();
        listaMensajes.getMensajes().add(nuevoMensaje);
        gestorXML.guardarMensajes(listaMensajes);
    }

    /**
     * Este metodo es el encargado de recuperar todos los mensajes intercambiados
     * entre dos usuarios, en ambos sentidos, ordenados por fecha
     * @param usuarioA Primer usuario de la conversación
     * @param usuarioB Segundo usuario de la conversación
     * @return Lista de mensajes ordenados por fecha
     * @throws JAXBException Si hay un error al leer mensajes.xml
     */
    public List<Mensaje> consultarConversacion(String usuarioA, String usuarioB)
            throws JAXBException {

        return gestorXML.leerMensajes().getMensajes().stream()
                .filter(mensaje ->
                        (mensaje.getEmisor().equals(usuarioA) && mensaje.getDestinatario().equals(usuarioB))
                                || (mensaje.getEmisor().equals(usuarioB) && mensaje.getDestinatario().equals(usuarioA)))
                .sorted(Comparator.comparing(Mensaje::getFecha))
                .toList();
    }

    // ===== METODOS STREAMS ===== \\
    /**
     * Este metodo es el encargado de calcular cuántos mensajes ha enviado cada usuario
     * en una conversación, usando Stream API
     * @param usuarioA Primer usuario de la conversación
     * @param usuarioB Segundo usuario de la conversación
     * @return Mapa con el nombre del emisor como clave y el número de mensajes como valor
     * @throws JAXBException Si hay un error al leer mensajes.xml
     */
    public Map<String, Long> estadisticas(String usuarioA, String usuarioB)
            throws JAXBException {

        return consultarConversacion(usuarioA, usuarioB).stream()
                .collect(Collectors.groupingBy(Mensaje::getEmisor, Collectors.counting()));
    }

    /**
     * Este metodo es el encargado de exportar una conversación a un archivo de texto plano,
     * usando Streams para recorrer los mensajes ordenados por fecha
     * @param usuarioA Primer usuario de la conversación
     * @param usuarioB Segundo usuario de la conversación
     * @param rutaDestino Ruta del archivo de texto que se va a generar
     * @throws JAXBException Si hay un error al leer mensajes.xml
     * @throws IOException Si hay un error al escribir el archivo de texto
     */
    public void exportarConversacion(String usuarioA, String usuarioB, Path rutaDestino)
            throws JAXBException, IOException {

        List<Mensaje> mensajes = consultarConversacion(usuarioA, usuarioB);

        try (BufferedWriter escritor = Files.newBufferedWriter(rutaDestino)) {
            mensajes.forEach(mensaje -> {
                try {
                    escritor.write(mensaje.getFecha() + " | " + mensaje.getEmisor()
                            + ": " + mensaje.getContenido());
                    if (mensaje.getAdjuntoRuta() != null) {
                        escritor.write(" [adjunto: " + mensaje.getAdjuntoNombre() + "]");
                    }
                    escritor.newLine();
                } catch (IOException excepcion) {
                    throw new UncheckedIOException(excepcion);
                }
            });
        }
    }

    // ===== METODOS ADJUNTOS ===== \\
    /**
     * Este metodo es el encargado de adjuntar un archivo a un mensaje,
     * validando el tamaño y la extensión, copiándolo a media/ con
     * InputStream/OutputStream y guardando sus metadatos en el XML
     * @param mensaje El mensaje al que se le va a adjuntar el archivo
     * @param rutaArchivoOrigen Ruta del archivo original que se quiere adjuntar
     * @throws IOException Si hay un error al copiar el archivo
     * @throws JAXBException Si hay un error al leer o escribir mensajes.xml
     */
    public void adjuntarArchivo(Mensaje mensaje, Path rutaArchivoOrigen)
            throws IOException, JAXBException {

        if (rutaArchivoOrigen == null || !Files.exists(rutaArchivoOrigen))
            throw new IllegalArgumentException("No existe el archivo: " + rutaArchivoOrigen);

        long tamanoArchivo = Files.size(rutaArchivoOrigen);
        if (tamanoArchivo > TAMANO_MAXIMO)
            throw new IllegalArgumentException("Archivo demasiado grande (máx 10 MB)");

        String nombreOriginal = rutaArchivoOrigen.getFileName().toString();
        int posicionPunto = nombreOriginal.lastIndexOf('.');
        String extension = (posicionPunto < 0) ? "" : nombreOriginal.substring(posicionPunto + 1).toLowerCase();
        if (!EXTENSIONES_PERMITIDAS.contains(extension))
            throw new IllegalArgumentException("Extensión no permitida: " + extension);

        validarUsuarioRegistrado(mensaje.getEmisor());
        validarUsuarioRegistrado(mensaje.getDestinatario());

        if (mensaje.getEmisor().equals(mensaje.getDestinatario()))
            throw new IllegalArgumentException("Emisor y destinatario no pueden coincidir");
        if (mensaje.getContenido() == null || mensaje.getContenido().isBlank())
            throw new IllegalArgumentException("El mensaje debe tener texto acompañando al adjunto");

        Files.createDirectories(CARPETA_MEDIA);
        String nombreGuardado = UUID.randomUUID() + "_" + nombreOriginal;
        Path rutaDestinoFisico = CARPETA_MEDIA.resolve(nombreGuardado);

        try (InputStream flujoEntrada = Files.newInputStream(rutaArchivoOrigen);
             OutputStream flujoSalida = Files.newOutputStream(rutaDestinoFisico)) {
            flujoEntrada.transferTo(flujoSalida);
        }

        mensaje.setAdjuntoRuta(rutaDestinoFisico.toString());
        mensaje.setAdjuntoNombre(nombreOriginal);
        mensaje.setAdjuntoTamano(tamanoArchivo);
        mensaje.setAdjuntoTipo(Files.probeContentType(rutaDestinoFisico));

        ListaMensajes listaMensajes = gestorXML.leerMensajes();
        listaMensajes.getMensajes().add(mensaje);
        gestorXML.guardarMensajes(listaMensajes);
    }

    // ===== HELPERS PRIVADOS ===== \\
    /**
     * Este metodo es el encargado de validar que un usuario exista en el sistema
     * @param nombre Nombre del usuario a validar
     * @throws JAXBException Si hay un error al leer usuarios.xml
     */
    private void validarUsuarioRegistrado(String nombre) throws JAXBException {
        boolean existe = gestorXML.leerUsuarios().getUsuarios().stream()
                .anyMatch(usuario -> usuario.getNombre().equals(nombre));
        if (!existe)
            throw new IllegalArgumentException("Usuario no registrado: " + nombre);
    }
}