package offline.chat.helper;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import offline.chat.objetos.ListaMensajes;
import offline.chat.objetos.ListaUsuarios;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class GestorXML {

    // ===== ATRIBUTOS ===== \\
    private static final Path ARCHIVO_USUARIOS = Path.of("usuarios.xml");
    private static final Path ARCHIVO_MENSAJES = Path.of("mensajes.xml");

    // ===== METODOS USUARIOS ===== \\
    /**
     * Este metodo es el encargado de guardar la lista de usuarios en usuarios.xml
     * @param listaUsuarios La lista con todos los usuarios a guardar
     * @throws JAXBException Si hay un error al escribir el archivo XML
     */
    public void guardarUsuarios(ListaUsuarios listaUsuarios) throws JAXBException {
        try (OutputStream flujoSalida = Files.newOutputStream(ARCHIVO_USUARIOS)) {
            Marshaller marshaller = JAXBContext.newInstance(ListaUsuarios.class).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(listaUsuarios, flujoSalida);
        } catch (IOException excepcion) {
            throw new JAXBException("Error escribiendo usuarios.xml", excepcion);
        }
    }

    /**
     * Este metodo es el encargado de leer usuarios.xml y devolver la lista de usuarios.
     * Si el archivo no existe, devuelve una lista vacía
     * @return La lista de usuarios leída del XML, o una lista vacía si no existe el archivo
     * @throws JAXBException Si hay un error al leer el archivo XML
     */
    public ListaUsuarios leerUsuarios() throws JAXBException {
        if (!Files.exists(ARCHIVO_USUARIOS)) return new ListaUsuarios();
        try (InputStream flujoEntrada = Files.newInputStream(ARCHIVO_USUARIOS)) {
            Unmarshaller unmarshaller = JAXBContext.newInstance(ListaUsuarios.class).createUnmarshaller();
            return (ListaUsuarios) unmarshaller.unmarshal(flujoEntrada);
        } catch (IOException excepcion) {
            throw new JAXBException("Error leyendo usuarios.xml", excepcion);
        }
    }

    // ===== METODOS MENSAJES ===== \\
    /**
     * Este metodo es el encargado de guardar la lista de mensajes en mensajes.xml
     * @param listaMensajes La lista con todos los mensajes a guardar
     * @throws JAXBException Si hay un error al escribir el archivo XML
     */
    public void guardarMensajes(ListaMensajes listaMensajes) throws JAXBException {
        try (OutputStream flujoSalida = Files.newOutputStream(ARCHIVO_MENSAJES)) {
            Marshaller marshaller = JAXBContext.newInstance(ListaMensajes.class).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(listaMensajes, flujoSalida);
        } catch (IOException excepcion) {
            throw new JAXBException("Error escribiendo mensajes.xml", excepcion);
        }
    }

    /**
     * Este metodo es el encargado de leer mensajes.xml y devolver la lista de mensajes.
     * Si el archivo no existe, devuelve una lista vacía
     * @return La lista de mensajes leída del XML, o una lista vacía si no existe el archivo
     * @throws JAXBException Si hay un error al leer el archivo XML
     */
    public ListaMensajes leerMensajes() throws JAXBException {
        if (!Files.exists(ARCHIVO_MENSAJES)) return new ListaMensajes();
        try (InputStream flujoEntrada = Files.newInputStream(ARCHIVO_MENSAJES)) {
            Unmarshaller unmarshaller = JAXBContext.newInstance(ListaMensajes.class).createUnmarshaller();
            return (ListaMensajes) unmarshaller.unmarshal(flujoEntrada);
        } catch (IOException excepcion) {
            throw new JAXBException("Error leyendo mensajes.xml", excepcion);
        }
    }
}