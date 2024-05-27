public class Blusa extends Componente {
    private boolean mangaLarga;

    public Blusa(int codigo, String descripcion, String talla, String color, boolean comunitario, double precio, boolean mangaLarga) {
        super(codigo, descripcion, talla, color, comunitario, precio);
        this.mangaLarga = mangaLarga;
    }

    // Getters y Setters

    @Override
    public String toString() {
        return "Blusa{" +
                "mangaLarga=" + mangaLarga +
                "} " + super.toString();
    }
}
