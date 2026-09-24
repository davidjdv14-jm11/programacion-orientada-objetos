# PA1 – Sistema básico de registro y control de productos

Programa en Java por consola que permite a una microempresa registrar productos, mostrar su información, validar stock y realizar operaciones básicas de control (ingresos, salidas y reporte), usando solo los temas de las semanas 1 a 4.

## Integrantes

| Integrante | Responsabilidad |
|---|---|
| David Jared Damazo Valdeos | Análisis, desarrollo, pruebas, README y Video |


## Video de exposición

[Ver video en YouTube](https://youtu.be/ENLACE-AQUI)

## Instrucciones de ejecución

Requisito: JDK 17 o superior.

```bash
cd src
javac *.java
java Main
```

En NetBeans/IntelliJ: abrir el proyecto y ejecutar la clase `Main`.

## Funcionalidades

1. Registrar producto (valida código repetido, precio > 0 y stock no negativo)
2. Listar productos
3. Buscar producto por código
4. Ingresar stock (compra)
5. Retirar stock (venta), valida stock suficiente y alerta stock bajo
6. Reporte: productos registrados, unidades, valor total, stock bajo y agotados

## Análisis: clases, atributos y métodos

**Producto** (clase de dominio)
- Atributos de instancia: `codigo`, `nombre`, `categoria`, `precio`, `stock`, `stockMinimo`
- Atributo de clase: `totalProductosCreados`
- Constructores: por defecto y con parámetros
- Métodos void: `mostrarInformacion()`, setters
- Métodos con retorno: `calcularValorTotal()`, `obtenerEstadoStock()`, `tieneStockBajo()`, `hayStockSuficiente()`, `agregarStock()`, `retirarStock()`

**Inventario**
- Atributos: `producto1`, `producto2`, `producto3`; constante de clase `CAPACIDAD_MAXIMA`
- Métodos: `registrarProducto()`, `buscarPorCodigo()`, `contarProductos()`, `listarProductos()`, `mostrarReporte()`

**Main** (clase ejecutora)
- Método `main` con menú `do-while` + `switch`
- Métodos de lectura con validación (`while`)

## Decisiones de diseño

- **Separé responsabilidades en 3 clases:** `Producto` guarda los datos y reglas de un producto, `Inventario` administra el conjunto y `Main` solo se encarga de la interacción con el usuario.
- **Capacidad fija de 3 productos:** arreglos y listas no forman parte del sílabo hasta la semana 4, por eso usé 3 atributos y un `switch` para acceder a ellos. Es una limitación consciente que se resolvería con arreglos más adelante.
- **Validaciones dentro de la clase:** los setters y `retirarStock()` impiden valores inválidos (precio o stock negativos, retirar más de lo disponible), así el objeto nunca queda en un estado incorrecto.
- **Métodos que devuelven `boolean`:** las operaciones informan si se realizaron, y `Main` decide qué mensaje mostrar.
- **Contadores y acumuladores** en el reporte para calcular totales, productos con stock bajo y agotados.

## Evidencias

Capturas de las pruebas en la carpeta [evidencias](evidencias).
