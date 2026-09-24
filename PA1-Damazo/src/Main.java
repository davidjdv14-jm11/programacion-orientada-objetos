import java.util.Locale;
import java.util.Scanner;

/**
 * Clase ejecutora: contiene el método main y el menú por consola.
 * Temas aplicados: do-while (repetición del menú), switch (opciones),
 * while (validación de datos), if-else anidados.
 */
public class Main {

    // Atributos de clase (globales para todos los métodos de Main)
    private static Scanner teclado = new Scanner(System.in).useLocale(Locale.US); // punto decimal
    private static Inventario inventario = new Inventario();

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1: registrarProducto(); break;
                case 2: inventario.listarProductos(); break;
                case 3: buscarProducto(); break;
                case 4: ingresarStock(); break;
                case 5: retirarStock(); break;
                case 6: inventario.mostrarReporte(); break;
                case 0: System.out.println("Gracias por usar el sistema. ¡Hasta luego!"); break;
                default: System.out.println("Opción no válida. Intente nuevamente.");
            }
            System.out.println();
        } while (opcion != 0);
    }

    // ===== Menú =====

    private static void mostrarMenu() {
        System.out.println("========== CONTROL DE PRODUCTOS ==========");
        System.out.println("1. Registrar producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Buscar producto por código");
        System.out.println("4. Ingresar stock (compra)");
        System.out.println("5. Retirar stock (venta)");
        System.out.println("6. Reporte de inventario");
        System.out.println("0. Salir");
    }

    // ===== Opciones del menú =====

    private static void registrarProducto() {
        if (inventario.estaLleno()) {
            System.out.println("Inventario lleno. Capacidad máxima: " + Inventario.CAPACIDAD_MAXIMA);
            return;
        }

        String codigo = leerTexto("Código: ").toUpperCase();
        while (inventario.existeCodigo(codigo)) {
            System.out.println("Ese código ya existe.");
            codigo = leerTexto("Ingrese otro código: ").toUpperCase();
        }

        String nombre = leerTexto("Nombre: ");
        String categoria = leerTexto("Categoría: ");
        double precio = leerDecimalPositivo("Precio (S/): ");
        int stock = leerEnteroNoNegativo("Stock inicial: ");
        int stockMinimo = leerEnteroNoNegativo("Stock mínimo: ");

        Producto nuevo = new Producto(codigo, nombre, categoria, precio, stock, stockMinimo);

        if (inventario.registrarProducto(nuevo)) {
            System.out.println("Producto registrado correctamente.");
            if (nuevo.tieneStockBajo()) {
                System.out.println("Aviso: el producto inicia con stock bajo.");
            }
        } else {
            System.out.println("No se pudo registrar el producto.");
        }
    }

    private static void buscarProducto() {
        Producto p = pedirProductoExistente();
        if (p != null) {
            p.mostrarInformacion();
        }
    }

    private static void ingresarStock() {
        Producto p = pedirProductoExistente();
        if (p == null) {
            return;
        }
        int cantidad = leerEntero("Cantidad a ingresar: ");
        if (p.agregarStock(cantidad)) {
            System.out.println("Stock actualizado. Nuevo stock: " + p.getStock());
        } else {
            System.out.println("La cantidad debe ser mayor a 0.");
        }
    }

    private static void retirarStock() {
        Producto p = pedirProductoExistente();
        if (p == null) {
            return;
        }
        int cantidad = leerEntero("Cantidad a retirar: ");

        // if-else anidado para indicar el motivo exacto del rechazo
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor a 0.");
        } else {
            if (p.retirarStock(cantidad)) {
                System.out.println("Operación realizada. Stock restante: " + p.getStock());
                if (p.tieneStockBajo()) {
                    System.out.println("Alerta: " + p.getNombre() + " quedó en estado " + p.obtenerEstadoStock());
                }
            } else {
                System.out.println("Stock insuficiente. Disponible: " + p.getStock());
            }
        }
    }

    // ===== Métodos auxiliares que devuelven valor =====

    private static Producto pedirProductoExistente() {
        if (inventario.contarProductos() == 0) {
            System.out.println("Primero registre al menos un producto.");
            return null;
        }
        String codigo = leerTexto("Código del producto: ");
        Producto p = inventario.buscarPorCodigo(codigo);
        if (p == null) {
            System.out.println("No existe un producto con el código " + codigo.toUpperCase());
        }
        return p;
    }

    private static String leerTexto(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = teclado.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("El dato no puede estar vacío.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!teclado.hasNextInt()) {
            System.out.print("Debe ingresar un número entero: ");
            teclado.nextLine();
        }
        int valor = teclado.nextInt();
        teclado.nextLine();   // limpia el salto de línea pendiente
        return valor;
    }

    private static int leerEnteroNoNegativo(String mensaje) {
        int valor = leerEntero(mensaje);
        while (valor < 0) {
            System.out.println("El valor no puede ser negativo.");
            valor = leerEntero(mensaje);
        }
        return valor;
    }

    private static double leerDecimalPositivo(String mensaje) {
        System.out.print(mensaje);
        double valor = 0;
        boolean valido = false;
        while (!valido) {
            if (teclado.hasNextDouble()) {
                valor = teclado.nextDouble();
                if (valor > 0) {
                    valido = true;
                } else {
                    System.out.print("El precio debe ser mayor a 0: ");
                }
            } else {
                System.out.print("Debe ingresar un número (ej. 12.50): ");
            }
            teclado.nextLine();   // limpia la línea leída
        }
        return valor;
    }
}
