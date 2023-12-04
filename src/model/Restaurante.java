package model;

import java.io.*;
import java.util.*;

public class Restaurante {

    private ArrayList<Ingrediente> ingredientes;
    private ArrayList<Producto> productos;
    private ArrayList<Producto> bebidas;
    private Hashtable<String, Integer> ingresoPrecio;
    private Hashtable<String, Producto> productoNombre;
    private ArrayList<Combo> combos;
    private ArrayList<Pedido> pedidos;
    private ArrayList<Producto> itemsCombo;
    private Pedido pedidoAhora;
    private Hashtable<Integer, Hashtable<String, Integer>> infoFacturas;

    public Restaurante() {
        this.ingredientes = new ArrayList<>();
        this.productos = new ArrayList<>();
        this.combos = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        this.bebidas = new ArrayList<>();
        this.infoFacturas = new Hashtable<>();
    }

    public void iniciarPedido(String nombreCliente, String direccionCliente) {
        this.pedidoAhora = new Pedido(nombreCliente, direccionCliente);
        int idAhora = pedidos.size();
        this.pedidoAhora.setId(idAhora + 1);
        this.infoFacturas.put(pedidoAhora.getIdPedido(), new Hashtable<>());
    }

    public void agregarProducto(Producto producto, boolean combo) {
        try {
            if (combo) {
                itemsCombo = ((Combo) producto).getItems();
                for (Producto i : itemsCombo) {
                    verificarIngredientes(i, producto.getPrecio());
                }
            } else {
                verificarIngredientes(producto, 0);
            }
        } catch (ProductoTotal150 e) {
            System.out.println(e.getMessage());
        }
    }

    public void cerrarYGuardarPedido() {
        File archivoPedido = new File("./Data/" + this.pedidoAhora.getIdPedido() + ".txt");
        pedidoAhora.guardarFactura(archivoPedido);

        pedidos.add(pedidoAhora);
        if (pedidoAhora.getIdPedido() > 1) {
            boolean respuesta = compararPedidos(pedidoAhora.getIdPedido());
            System.out.println(respuesta ? "Ya existía un pedido igual" : "No existía un pedido igual");
        }
    }

    public Hashtable<String, Integer> getIngresoPrecio() {
        return ingresoPrecio;
    }

    public Pedido getPedidoEnCurso() {
        return this.pedidoAhora;
    }

    public void getPedidoId(int id) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("./Data/" + id + ".txt"));
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("El id de ese pedido no existe");
        }
    }

    public ArrayList<Producto> getMenuBase() {
        return this.productos;
    }

    public ArrayList<Producto> getBebidas() {
        return this.bebidas;
    }

    public ArrayList<Ingrediente> getIngredientes() {
        return this.ingredientes;
    }

    public ArrayList<Combo> getCombos() {
        return this.combos;
    }

    private boolean compararPedidos(int pedidoId) {
        Hashtable<String, Integer> pedidoActual = infoFacturas.get(pedidoId);
        for (int i = 1; i < pedidoId; i++) {
            Hashtable<String, Integer> pedidoAnterior = infoFacturas.get(i);
            if (pedidoActual.equals(pedidoAnterior)) {
                return true;
            }
        }
        return false;
    }

    public void verificarIngredientes(Producto producto, float descuento) throws ProductoTotal150 {
        ArrayList<Ingrediente> listaIngredientes = getIngredientes();
        int k = 0;
        for (Ingrediente i : listaIngredientes) {
            System.out.println((k + 1) + ". " + i.getNombre() + ": " + i.getCostoAdicional());
            k++;
        }
        System.out.println((k + 1) + ": Ninguno");
        float nuevoPrecio;
        String nuevoNombre;
        if (descuento != 0) {
            nuevoPrecio = producto.getPrecio() - (producto.getPrecio() * descuento);
            nuevoNombre = "Combo " + producto.getNombre();
        } else {
            nuevoPrecio = producto.getPrecio();
            nuevoNombre = producto.getNombre();
        }

        while (true) {
            int ingredienteEscogido = Integer.parseInt(input("\n¿Qué ingrediente desea eliminar o agregar (1 - " + (k + 1)
                    + ") para el producto " + nuevoNombre));
            if (0 <= ingredienteEscogido && ingredienteEscogido <= k) {
                int adicionarEliminar = Integer.parseInt(input("\n¿Desea \n1) Adicionar\n2) Eliminar\nElija"));
                if (adicionarEliminar == 1) {
                    nuevoNombre = nuevoNombre + "con" + listaIngredientes.get(ingredienteEscogido - 1).getNombre();
                    nuevoPrecio = nuevoPrecio + ingresoPrecio.get(listaIngredientes.get(k - 1).getNombre());
                } else if (adicionarEliminar == 2) {
                    nuevoNombre = nuevoNombre + "sin" + listaIngredientes.get(ingredienteEscogido - 1).getNombre();
                } else {
                    System.out.println("Opción Incorrecta");
                }
            } else if (ingredienteEscogido == k + 1) {
                if (!nuevoNombre.equals(producto.getNombre())) {
                    ProductoAjustado productoNuevo = new ProductoAjustado(nuevoNombre, nuevoPrecio, producto.getCalorias());
                    pedidoAhora.agregarProducto(productoNuevo);
                } else {
                    pedidoAhora.agregarProducto(producto);
                }
                break;
            } else {
                System.out.println("Opción incorrecta");
            }
        }

        String key = nuevoNombre;
        if (infoFacturas.get(pedidoAhora.getIdPedido()).containsKey(key)) {
            int cantidad = infoFacturas.get(pedidoAhora.getIdPedido()).get(key);
            infoFacturas.get(pedidoAhora.getIdPedido()).replace(key, cantidad + 1);
        } else {
            infoFacturas.get(pedidoAhora.getIdPedido()).put(key, 1);
        }
    }

    public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos,
            File archivoBebidas) {
        try {
            cargarIngredientes(archivoIngredientes);
        } catch (IngredienteRepetidoException e) {
            System.out.println(e.getMessage());
        }
        try {
            cargarMenu(archivoMenu);
        } catch (ProductoRepetidoException e) {
            System.out.println(e.getMessage());
        }
        cargarBebidas(archivoBebidas);
        cargarCombos(archivoCombos);
    }

    private void cargarIngredientes(File archivoIngredientes) throws IngredienteRepetidoException {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes))) {
            String linea;
            ingresoPrecio = new Hashtable<>();
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                int valor = Integer.parseInt(partes[1]);

                boolean repetido = ingredientes.stream().anyMatch(i -> i.getNombre().equals(partes[0]));
                if (repetido) {
                    throw new IngredienteRepetidoException("El ingrediente " + partes[0] + " está repetido");
                } else {
                    ingredientes.add(new Ingrediente(partes[0], valor));
                    ingresoPrecio.put(partes[0], Integer.parseInt(partes[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los ingredientes");
        }
    }

    private void cargarMenu(File archivoMenu) throws ProductoRepetidoException {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoMenu))) {
            String linea;
            productoNombre = new Hashtable<>();
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                int valor = Integer.parseInt(partes[1]);
                int cal = Integer.parseInt(partes[2]);

                try {
                    boolean repetido = productos.stream().anyMatch(p -> p.getNombre().equals(partes[0]));
                    if (repetido) {
                        throw new ProductoRepetidoException("El producto " + partes[0] + " está repetido");
                    }
                    ProductoMenu productoMenu = new ProductoMenu(partes[0], valor, cal);
                    productos.add(productoMenu);
                    productoNombre.put(productoMenu.getNombre(), productoMenu);
                    System.out.println(partes[0] + " : $" + partes[1]);
                } catch (ProductoRepetidoException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el menú");
        }
    }

    private void cargarBebidas(File archivoMenu) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoMenu))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                int valor = Integer.parseInt(partes[1]);
                int cal = Integer.parseInt(partes[2]);
                ProductoBebida productoBebida = new ProductoBebida(partes[0], valor, cal);
                bebidas.add(productoBebida);
                productoNombre.put(productoBebida.getNombre(), productoBebida);
                System.out.println(partes[0] + " : $" + partes[1]);
            }
            System.out.println("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarCombos(File archivoCombos) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCombos))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                float descuento = Float.parseFloat(partes[1].substring(0, partes[1].length() - 1));

                Combo combo = new Combo(partes[0], descuento / 100);

                for (int i = 2; i < partes.length; i++) {
                    String nombre = partes[i];
                    combo.agregarItemACombo(productoNombre.get(nombre));
                }

                combos.add(combo);
                System.out.println(partes[0]);
            }
            System.out.println("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String input(String mensaje) {
        try {
            System.out.print(mensaje + ": ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }
}
