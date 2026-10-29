package offline.chat.objetos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "usuarios")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListaUsuarios {

    // ===== ATRIBUTOS ===== \\
    @XmlElement(name = "usuario")
    private List<Usuario> usuarios = new ArrayList<>();

    // ===== CONSTRUCTORES ===== \\
    public ListaUsuarios() { }

    // ===== GETTERS && SETTERS ===== \\
    public List<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }

    // ===== TO STRING ===== \\
    @Override
    public String toString() {
        return "ListaUsuarios{" +
                "usuarios=" + usuarios +
                '}';
    }
}