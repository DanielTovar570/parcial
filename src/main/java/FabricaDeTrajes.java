import java.util.*;

public class FabricaDeTrajes implements IFabricaDeTrajes {
    private ArrayList<Componente> componentesEnAlmacen = new ArrayList<>();
    private TreeSet<Traje> trajesEnAlmacen = new TreeSet<>(Comparator.comparing(Traje::getNombre));
    private boolean sonRebajas = false;

    public static void main(String[] args) {
        FabricaDeTrajes fabrica = new FabricaDeTrajes();
        fabrica.escribirMenu();
    }

    @Override
    public void escribirMenu() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("MENU FABRICA TRAJES");
            System.out.println("1.- Añadir Componente a almacén");
            System.out.println("2.- Listar Componentes del almacén");
            System.out.println("3.- Crear traje y añadir a almacén");
            System.out.println("4.- Listar trajes del almacén");
            System.out.println("7.- Activar/Desactivar las rebajas");
            System.out.println("8.- Crear envío");
            System.out.println("9.- Crear componentes de prueba");
            System.out.println("0.- Salir");
            System.out.print("Elija una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // Clear buffer

            switch (opcion) {
                case 1:
                    añadirComponenteAAlmacen(sc);
                    break;
                case 2:
                    listarComponentes();
                    break;
                case 3:
                    añadirTrajeAAlmacen(sc);
                    break;
                case 4:
                    listarTrajes();
                    break;
                case 7:
                    activarDesactivarRebajas();
                    break;
                case 8:
                    crearEnvio(sc);
                    break;
                case 9:
                    crearComponentesDePrueba();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (opcion != 0);
    }

    @Override
    public void añadirComponenteAAlmacen(Scanner sc) {
        try {
            System.out.print("Ingrese el tipo de componente (1. Falda, 2. Chaqueta, 3. Pantalón, 4. Blusa): ");
            int tipo = sc.nextInt();
            sc.nextLine(); // Clear buffer

            System.out.print("Ingrese el ID del componente: ");
            int id = sc.nextInt();
            sc.nextLine(); // Clear buffer

            for (Componente c : componentesEnAlmacen) {
                if (c.getId() == id) {
                    throw new IdException("El ID ya existe.");
                }
            }

            System.out.print("Ingrese el nombre del componente: ");
            String nombre = sc.nextLine();

            System.out.print("Ingrese la talla del componente: ");
            String talla = sc.nextLine();

            System.out.print("Ingrese el color del componente: ");
            String color = sc.nextLine();

            System.out.print("¿Es comunitario? (true/false): ");
            boolean esComunitario = sc.nextBoolean();

            if (!esComunitario) {
                long countExtracomunitario = componentesEnAlmacen.stream().filter(c -> !c.isEsComunitario()).count();
                if (countExtracomunitario >= componentesEnAlmacen.size() / 2) {
                    throw new MuchoExtracomunitarioException("Demasiados componentes extracomunitarios.");
                }
            }

            System.out.print("Ingrese el precio del componente: ");
            double precio = sc.nextDouble();

            Componente componente = null;

            switch (tipo) {
                case 1: // Falda
                    System.out.print("¿Tiene cremallera? (true/false): ");
                    boolean faldaCremallera = sc.nextBoolean();
                    componente = new Falda(id, nombre, talla, color, esComunitario, precio, faldaCremallera);
                    break;
                case 2: // Chaqueta
                    System.out.print("Ingrese el número de botones: ");
                    int numBotones = sc.nextInt();
                    componente = new Chaqueta(id, nombre, talla, color, esComunitario, precio, numBotones);
                    break;
                case 3: // Pantalón
                    System.out.print("¿Tiene cremallera? (true/false): ");
                    boolean pantalonCremallera = sc.nextBoolean();
                    componente = new Pantalon(id, nombre, talla, color, esComunitario, precio, pantalonCremallera);
                    break;
                case 4: // Blusa
                    System.out.print("¿Es de manga larga? (true/false): ");
                    boolean mangaLarga = sc.nextBoolean();
                    componente = new Blusa(id, nombre, talla, color, esComunitario, precio, mangaLarga);

                    boolean existeMangaContraria = componentesEnAlmacen.stream()
                            .anyMatch(c -> c instanceof Blusa && ((Blusa) c).isMangaLarga() != mangaLarga && c.getColor().equals(color));

                    if (!existeMangaContraria) {
                        throw new MangaException("No existe una blusa del color contrario.");
                    }
                    break;
                default:
                    System.out.println("Tipo de componente no válido");
                    return;
            }

            componentesEnAlmacen.add(componente);
            System.out.println("Componente añadido con éxito.");

        } catch (IdException | MuchoExtracomunitarioException | MangaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void listarComponentes() {
        if (componentesEnAlmacen.isEmpty()) {
            System.out.println("No hay componentes en el almacén.");
        } else {
            componentesEnAlmacen.forEach(System.out::println);
        }
    }

    @Override
    public void añadirTrajeAAlmacen(Scanner sc) {
        try {
            if (componentesEnAlmacen.isEmpty()) {
                System.out.println("No hay componentes en el almacén.");
                return;
            }

            System.out.print("Ingrese el nombre del traje: ");
            String nombreTraje = sc.nextLine();

            if (trajesEnAlmacen.stream().anyMatch(t -> t.getNombre().equalsIgnoreCase(nombreTraje))) {
                throw new TrajeYaExisteException("El nombre del traje ya existe.");
            }

            System.out.println("Blusas disponibles:");
            componentesEnAlmacen.stream().filter(c -> c instanceof Blusa).forEach(System.out::println);
            System.out.print("Ingrese el ID de la blusa: ");
            int idBlusa = sc.nextInt();
            sc.nextLine(); // Clear buffer

            System.out.println("Chaquetas disponibles:");
            componentesEnAlmacen.stream().filter(c -> c instanceof Chaqueta).forEach(System.out::println);
            System.out.print("Ingrese el ID de la chaqueta: ");
            int idChaqueta = sc.nextInt();
            sc.nextLine(); // Clear buffer

            System.out.println("Faldas y pantalones disponibles:");
            componentesEnAlmacen.stream().filter(c -> c instanceof Falda || c instanceof Pantalon).forEach(System.out::println);
            System.out.print("Ingrese el ID de la falda o pantalón: ");
            int idFaldaPantalon = sc.nextInt();
            sc.nextLine(); // Clear buffer

            Componente blusa = componentesEnAlmacen.stream().filter(c -> c.getId() == idBlusa).findFirst().orElse(null);
            Componente chaqueta = componentesEnAlmacen.stream().filter(c -> c.getId() == idChaqueta).findFirst().orElse(null);
            Componente faldaPantalon = componentesEnAlmacen.stream().filter(c -> c.getId() == idFaldaPantalon).findFirst().orElse(null);

            if (blusa == null || chaqueta == null || faldaPantalon == null) {
                System.out.println("Alguno de los componentes no existe.");
                return;
            }

            if (!blusa.getColor().substring(0, 1).equalsIgnoreCase(chaqueta.getColor().substring(0, 1)) ||
                !blusa.getColor().substring(0, 1).equalsIgnoreCase(faldaPantalon.getColor().substring(0, 1))) {
                throw new ColoresException("Los colores no son amigos.");
            }

            if (!blusa.getTalla().equals(chaqueta.getTalla()) || (!blusa.getTalla().equals(faldaPantalon.getTalla()) && !(faldaPantalon instanceof Falda))) {
                throw new TallaException("Las tallas no coinciden.");
            }

            Traje traje = new Traje(nombreTraje);
            traje.getPiezas().add(blusa);
            traje.getPiezas().add(chaqueta);
            traje.getPiezas().add(faldaPantalon);

            trajesEnAlmacen.add(traje);
            componentesEnAlmacen.remove(blusa);
            componentesEnAlmacen.remove(chaqueta);
            componentesEnAlmacen.remove(faldaPantalon);

            System.out.println("Traje añadido con éxito.");

        } catch (ColoresException | TallaException | TrajeYaExisteException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void listarTrajes() {
        if (trajesEnAlmacen.isEmpty()) {
            System.out.println("No hay trajes en el almacén.");
        } else {
            trajesEnAlmacen.forEach(System.out::println);
        }
    }

    @Override
    public void activarDesactivarRebajas() {
        sonRebajas = !sonRebajas;
        if (sonRebajas) {
            System.out.println("Rebajas activadas.");
        } else {
            System.out.println("Rebajas desactivadas.");
        }

        // Actualizar precios de los componentes en el almacén
        componentesEnAlmacen.forEach(componente -> {
            if (sonRebajas) {
                componente.setPrecio(componente.getPrecio() * 0.9);
            } else {
                componente.setPrecio(componente.getPrecio() / 0.9);
            }
        });

        // Actualizar precios de los trajes en el almacén
        trajesEnAlmacen.forEach(traje -> {
            traje.getPiezas().forEach(componente -> {
                if (sonRebajas) {
                    componente.setPrecio(componente.getPrecio() * 0.9);
                } else {
                    componente.setPrecio(componente.getPrecio() / 0.9);
                }
            });
        });
    }

    @Override
    public void crearEnvio(Scanner sc) {
        List<Traje> envio = new ArrayList<>();
        String opcion;

        do {
            System.out.println("Trajes disponibles:");
            trajesEnAlmacen.forEach(traje -> System.out.println(traje.getNombre()));
            System.out.print("Ingrese el nombre del traje a añadir al envío (o 'fin' para terminar): ");
            opcion = sc.nextLine();

            if (!opcion.equalsIgnoreCase("fin")) {
                Traje traje = trajesEnAlmacen.stream().filter(t -> t.getNombre().equalsIgnoreCase(opcion)).findFirst().orElse(null);

                if (traje != null) {
                    envio.add(traje);
                    trajesEnAlmacen.remove(traje);
                    System.out.println("Traje añadido al envío.");
                } else {
                    System.out.println("El traje no existe.");
                }
            }

        } while (!opcion.equalsIgnoreCase("fin"));

        if (!envio.isEmpty()) {
            System.out.println("Envío creado con los siguientes trajes:");
            envio.forEach(System.out::println);
        } else {
            System.out.println("No se ha creado ningún envío.");
        }
    }

    @Override
    public void crearComponentesDePrueba() {
        try
