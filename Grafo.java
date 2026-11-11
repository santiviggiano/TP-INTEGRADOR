
public interface Grafo {

    void agregarVertice(String etiqueta);

    void agregarArista(String v1, String v2);

    boolean existeArista(String v1, String v2);

    ListaEnlazada<String> vecinos(String v);

    int cantidadVertices();

    boolean existeVertice(String etiqueta);

    ListaEnlazada<String> vertices();
}
