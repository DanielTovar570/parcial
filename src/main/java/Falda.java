public class Falda extends Componente {
    private boolean cremallera;

    public Falda(int codigo, String descripcion, String talla, String color, boolean comunitario, double precio, boolean cremallera) {
        super(codigo, descripcion, talla, color, comunitario, precio);
        this.cremallera = cremallera;
        if (cremallera) {
            setPrecio(getPrecio() + 1);
        }
    }

    // Getters y Setters

    @Override
    public String toString() {
        return "Falda{" +
                "cremallera=" + cremallera +
                "} " + super.toString();
    }
}
