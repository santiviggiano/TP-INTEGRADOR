
public class GrafoMatriz implements Grafo {

    private final boolean[][] matriz;
    private final String[] etiquetas;                  
    private final Diccionario<String, Integer> indices; 
    private int cantidad;

    public GrafoMatriz(int capacidad) {
        if (capacidad < 0) {
            throw new RuntimeException("La capacidad no puede ser negativa");
        }
        matriz = new boolean[capacidad][capacidad];
        etiquetas = new String[capacidad];
        indices = new DiccionarioDinamico<>();
        cantidad = 0;
    }

    @Override
    public void agregarVertice(String etiqueta) {
        if (etiqueta == null) {
            throw new RuntimeException("La etiqueta no puede ser null");
        }
        if (indices.existeClave(etiqueta)) {
            throw new RuntimeException("Ya existe un vértice con la etiqueta: " + etiqueta);
        }
        if (cantidad == etiquetas.length) {
            throw new RuntimeException("El grafo alcanzó su capacidad máxima (" + etiquetas.length + ")");
        }
        indices.definir(etiqueta, cantidad);
        etiquetas[cantidad] = etiqueta;
        cantidad++;
    }

    @Override
    public void agregarArista(String v1, String v2) {
        int i = indiceDe(v1);
        int j = indiceDe(v2);
        if (i == j) {
            throw new RuntimeException("No se admiten aristas de un vértice consigo mismo: " + v1);
        }
        matriz[i][j] = true;
        matriz[j][i] = true; 
    }

    @Override
    public boolean existeArista(String v1, String v2) {
        return matriz[indiceDe(v1)][indiceDe(v2)];
    }

    @Override
    public ListaEnlazada<String> vecinos(String v) {
        int i = indiceDe(v);
        ListaEnlazada<String> resultado = new ListaEnlazada<>();
        for (int j = 0; j < cantidad; j++) {
            if (matriz[i][j]) {
                resultado.agregar(etiquetas[j]);
            }
        }
        return resultado;
    }

    @Override
    public int cantidadVertices() {
        return cantidad;
    }

    @Override
    public boolean existeVertice(String etiqueta) {
        return etiqueta != null && indices.existeClave(etiqueta);
    }

    @Override
    public ListaEnlazada<String> vertices() {
        ListaEnlazada<String> resultado = new ListaEnlazada<>();
        for (int i = 0; i < cantidad; i++) {
            resultado.agregar(etiquetas[i]);
        }
        return resultado;
    }

    
    private int indiceDe(String etiqueta) {
        if (!existeVertice(etiqueta)) {
            throw new RuntimeException("El vértice no pertenece al grafo: " + etiqueta);
        }
        return indices.obtener(etiqueta);
    }
}
