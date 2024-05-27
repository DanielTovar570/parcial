import java.util.Objects;

public class Componente {
    private int codigo;
    private String descripcion;
    private String talla;
    private String color;
    private boolean comunitario;
    private double precio;

    public Componente(int codigo, String descripcion, String talla, String color, boolean comunitario, double precio) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.talla = talla;
        this.color = color;
        this.comunitario = comunitario;
        this.precio = precio;
    }

    // Getters y Setters

    @Override
    public String toString() {
        return "Componente{" +
                "codigo=" + codigo +
                ", descripcion='" + descripcion + '\'' +
                ", talla='" + talla + '\'' +
                ", color='" + color + '\'' +
                ", comunitario=" + comunitario +
                ", precio=" + precio +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Componente that = (Componente) o;
        return codigo == that.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
