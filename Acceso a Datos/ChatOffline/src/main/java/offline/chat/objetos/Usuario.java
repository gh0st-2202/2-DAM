package offline.chat.objetos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Usuario {

    // ===== ATRIBUTOS ===== \\
    @XmlAttribute
    private int id;
    private String nombre;

    // ===== CONSTRUCTORES ===== \\
    public Usuario() { }

    public Usuario(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // ===== GETTERS && SETTERS ===== \\
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    // ===== TO STRING ===== \\
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}