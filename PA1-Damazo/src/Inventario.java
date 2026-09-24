/**
 * Administra los productos registrados.
 * Decisión de diseño: como arreglos y listas NO están en el sílabo hasta la
 * semana 4, se usan 3 atributos (espacios) fijos y se accede a ellos con switch.
 * Temas aplicados: atributos de instancia y de clase, switch, for,
 * contadores y acumuladores.
 */
public class Inventario {

    // Atributo de clase constante
    public static final int CAPACIDAD_MAXIMA = 3;

    // Atributos de instancia: los espacios del inventario
    private Producto producto1;
    private Producto producto2;
    private Producto producto3;

    // Constructor: el inventario empieza vacío
    public Inventario() {
        producto1 = null;
        producto2 = null;
        producto3 = null;
    }

    // ===== Acceso a los espacios (switch) =====

    private Producto obtenerProducto(int posicion) {
        switch (posicion) {
            case 1:  return producto1;
            case 2:  return producto2;
            case 3:  return producto3;
            default: return null;
        }
    }

    private void asignarProducto(int posicion, Producto producto) {
        switch (posicion) {
            case 1: producto1 = producto; break;
            case 2: producto2 = producto; break;
            case 3: producto3 = producto; break;
        }
    }

    // ===== Operaciones =====

    // Registra en el primer espacio libre. Devuelve false si está lleno.
    public boolean registrarProducto(Producto nuevo) {
        for (int i = 1; i <= CAPACIDAD_MAXIMA; i++) {
            if (obtenerProducto(i) == null) {
                asignarProducto(i, nuevo);
                return true;
            }
        }
        return false;
    }

    public Producto buscarPorCodigo(String codigo) {
        for (int i = 1; i <= CAPACIDAD_MAXIMA; i++) {
            Producto p = obtenerProducto(i);   // variable local
            if (p != null && p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    public boolean existeCodigo(String codigo) {
        return buscarPorCodigo(codigo) != null;
    }

    public int contarProductos() {
        int contador = 0;                      // contador
        for (int i = 1; i <= CAPACIDAD_MAXIMA; i++) {
            if (obtenerProducto(i) != null) {
                contador++;
            }
        }
        return contador;
    }

    public boolean estaLleno() {
        return contarProductos() == CAPACIDAD_MAXIMA;
    }

    public void listarProductos() {
        if (contarProductos() == 0) {
            System.out.println("No hay productos registrados.");
            return;
        }
        for (int i = 1; i <= CAPACIDAD_MAXIMA; i++) {
            Producto p = obtenerProducto(i);
            if (p != null) {
                p.mostrarInformacion();
            }
        }
    }

    // Reporte general: usa acumuladores y contadores
    public void mostrarReporte() {
        double valorTotal = 0;     // acumulador
        int unidadesTotales = 0;   // acumulador
        int conStockBajo = 0;      // contador
        int agotados = 0;          // contador

        for (int i = 1; i <= CAPACIDAD_MAXIMA; i++) {
            Producto p = obtenerProducto(i);
            if (p != null) {
                valorTotal += p.calcularValorTotal();
                unidadesTotales += p.getStock();
                if (p.getStock() == 0) {
                    agotados++;
                } else if (p.tieneStockBajo()) {
                    conStockBajo++;
                }
            }
        }

        System.out.println("=========== REPORTE DE INVENTARIO ===========");
        System.out.println("Productos registrados : " + contarProductos() + " de " + CAPACIDAD_MAXIMA);
        System.out.println("Unidades en almacén   : " + unidadesTotales);
        System.out.println("Valor del inventario  : S/ " + String.format("%.2f", valorTotal));
        System.out.println("Con stock bajo        : " + conStockBajo);
        System.out.println("Agotados              : " + agotados);
        System.out.println("Objetos Producto creados (atributo de clase): "
                + Producto.getTotalProductosCreados());
    }
}
