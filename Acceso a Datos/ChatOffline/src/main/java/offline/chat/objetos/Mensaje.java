package offline.chat.objetos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Mensaje {

    // ===== ATRIBUTOS ===== \\
    private String emisor;
    private String destinatario;
    private String contenido;
    private String fecha;
    private String adjuntoRuta;
    private String adjuntoNombre;
    private long   adjuntoTamano;
    private String adjuntoTipo;

    // ===== CONSTRUCTORES ===== \\
    public Mensaje() { }

    public Mensaje(String emisor, String destinatario, String contenido, String fecha) {
        this.emisor = emisor;
        this.destinatario = destinatario;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    // ===== GETTERS && SETTERS ===== \\
    public String getEmisor() { return emisor; }
    public void setEmisor(String emisor) { this.emisor = emisor; }

    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getAdjuntoRuta() { return adjuntoRuta; }
    public void setAdjuntoRuta(String adjuntoRuta) { this.adjuntoRuta = adjuntoRuta; }

    public String getAdjuntoNombre() { return adjuntoNombre; }
    public void setAdjuntoNombre(String adjuntoNombre) { this.adjuntoNombre = adjuntoNombre; }

    public long getAdjuntoTamano() { return adjuntoTamano; }
    public void setAdjuntoTamano(long adjuntoTamano) { this.adjuntoTamano = adjuntoTamano; }

    public String getAdjuntoTipo() { return adjuntoTipo; }
    public void setAdjuntoTipo(String adjuntoTipo) { this.adjuntoTipo = adjuntoTipo; }

    // ===== TO STRING ===== \\
    @Override
    public String toString() {
        return "Mensaje{" +
                "emisor='" + emisor + '\'' +
                ", destinatario='" + destinatario + '\'' +
                ", contenido='" + contenido + '\'' +
                ", fecha='" + fecha + '\'' +
                ", adjuntoRuta='" + adjuntoRuta + '\'' +
                ", adjuntoNombre='" + adjuntoNombre + '\'' +
                ", adjuntoTamano=" + adjuntoTamano +
                ", adjuntoTipo='" + adjuntoTipo + '\'' +
                '}';
    }
}