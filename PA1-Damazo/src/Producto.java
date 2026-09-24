/**
 * Clase de dominio: representa un producto de la microempresa.
 * Temas aplicados: atributos de instancia y de clase, constructores,
 * métodos que devuelven valor y métodos que no devuelven valor (void).
 */
public class Producto {

    // ===== Atributo de clase (static): compartido por TODOS los objetos =====
    private static int totalProductosCreados = 0;

    // ===== Atributos de instancia: cada producto tiene los suyos =====
    private String codigo;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;
    private int stockMinimo;

    // ===== Constructores =====

    // Constructor por defecto (sin parámetros)
    public Producto() {
        this("SIN-COD", "Sin nombre", "General", 0.0, 0, 0);
    }

    // Constructor con parámetros
    public Producto(String codigo, String nombre, String categoria,
                    double precio, int stock, int stockMinimo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        setPrecio(precio);           // se reutiliza la validación del setter
        setStock(stock);
        setStockMinimo(stockMinimo);
        totalProductosCreados++;     // contador de clase
    }

    // ===== Métodos que NO devuelven valor (void) =====

    public void mostrarInformacion() {
        System.out.println("----------------------------------------");
        System.out.println("Código      : " + codigo);
        System.out.println("Nombre      : " + nombre);
        System.out.println("Categoría   : " + categoria);
        System.out.println("Precio      : S/ " + String.format("%.2f", precio));
        System.out.println("Stock       : " + stock + " (mínimo: " + stockMinimo + ")");
        System.out.println("Estado      : " + obtenerEstadoStock());
        System.out.println("Valor total : S/ " + String.format("%.2f", calcularValorTotal()));
    }

    // ===== Métodos que SÍ devuelven valor =====

    // Valor del producto en inventario (precio x stock)
    public double calcularValorTotal() {
        return precio * stock;
    }

    // Estructura selectiva if / else if / else
    public String obtenerEstadoStock() {
        if (stock == 0) {
            return "AGOTADO";
        } else if (stock <= stockMinimo) {
            return "STOCK BAJO";
        } else {
            return "DISPONIBLE";
        }
    }

    public boolean tieneStockBajo() {
        return stock <= stockMinimo;
    }

    public boolean hayStockSuficiente(int cantidad) {
        return cantidad <= stock;
    }

    // Ingreso de mercadería: devuelve true si la operación fue válida
    public boolean agregarStock(int cantidad) {
        if (cantidad <= 0) {
            return false;
        }
        stock = stock + cantidad;    // acumulador
        return true;
    }

    // Salida / venta: valida cantidad y stock disponible
    public boolean retirarStock(int cantidad) {
        if (cantidad <= 0 || !hayStockSuficiente(cantidad)) {
            return false;
        }
        stock = stock - cantidad;
        return true;
    }

    // ===== Getters y setters (con validación básica) =====

    public static int getTotalProductosCreados() { return totalProductosCreados; }

    public String getCodigo()    { return codigo; }
    public String getNombre()    { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecio()    { return precio; }
    public int getStock()        { return stock; }
    public int getStockMinimo()  { return stockMinimo; }

    public void setNombre(String nombre)       { this.nombre = nombre; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public void setPrecio(double precio) {
        if (precio >= 0) {
            this.precio = precio;
        } else {
            this.precio = 0;
        }
    }

    public void setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
        } else {
            this.stock = 0;
        }
    }

    public void setStockMinimo(int stockMinimo) {
        if (stockMinimo >= 0) {
            this.stockMinimo = stockMinimo;
        } else {
            this.stockMinimo = 0;
        }
    }
}
