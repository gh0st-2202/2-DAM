package offline.chat.objetos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "mensajes")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListaMensajes {

    // ===== ATRIBUTOS ===== \\
    @XmlElement(name = "mensaje")
    private List<Mensaje> mensajes = new ArrayList<>();

    // ===== CONSTRUCTORES ===== \\
    public ListaMensajes() { }

    // ===== GETTERS && SETTERS ===== \\
    public List<Mensaje> getMensajes() { return mensajes; }
    public void setMensajes(List<Mensaje> mensajes) { this.mensajes = mensajes; }

    // ===== TO STRING ===== \\
    @Override
    public String toString() {
        return "ListaMensajes{" +
                "mensajes=" + mensajes +
                '}';
    }
}