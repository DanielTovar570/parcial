import java.util.ArrayList;
import java.util.List;

public class Traje {
    private List<Componente> partes;
    private String nombre;

    public Traje(String nombre) {
        this.nombre = nombre;
        this.partes = new ArrayList<>();
    }

    // Getters y Setters

    @Override
    public String toString() {
        return "Traje{" +
                "nombre='" + nombre + '\'' +
                ", partes=" + partes +
                '}';
    }
}
