public class Chaqueta extends Componente {
    private int botones;

    public Chaqueta(int codigo, String descripcion, String talla, String color, boolean comunitario, double precio, int botones) {
        super(codigo, descripcion, talla, color, comunitario, precio);
        this.botones = botones;
        setPrecio(getPrecio() + botones * 2);
    }

    // Getters y Setters

    @Override
    public String toString() {
        return "Chaqueta{" +
                "botones=" + botones +
                "} " + super.toString();
    }
}
