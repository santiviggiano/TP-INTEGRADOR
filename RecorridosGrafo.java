
public class RecorridosGrafo {

   
    public static ListaEnlazada<String> bfs(Grafo g, String origen) {
        exigirVertice(g, origen);
        ListaEnlazada<String> orden = new ListaEnlazada<>();
        Diccionario<String, Boolean> visitados = new DiccionarioDinamico<>();
        Cola<String> cola = new ColaVarianteB<>(g.cantidadVertices());

        visitados.definir(origen, true);
        cola.encolar(origen);
        while (!cola.esVacia()) {
            String actual = cola.desencolar();
            orden.agregar(actual);
            for (String vecino : g.vecinos(actual)) {
                if (!visitados.existeClave(vecino)) {
                    visitados.definir(vecino, true);
                    cola.encolar(vecino);
                }
            }
        }
        return orden;
    }

    
    public static ListaEnlazada<String> dfs(Grafo g, String origen) {
        exigirVertice(g, origen);
        ListaEnlazada<String> orden = new ListaEnlazada<>();
        Diccionario<String, Boolean> visitados = new DiccionarioDinamico<>();
        dfsRec(g, origen, visitados, orden);
        return orden;
    }

    private static void dfsRec(Grafo g, String actual,
                               Diccionario<String, Boolean> visitados,
                               ListaEnlazada<String> orden) {
        visitados.definir(actual, true);
        orden.agregar(actual);
        for (String vecino : g.vecinos(actual)) {
            if (!visitados.existeClave(vecino)) {
                dfsRec(g, vecino, visitados, orden);
            }
        }
    }

    
    public static ListaEnlazada<String> dfsIterativo(Grafo g, String origen) {
        return dfsIterativoImpl(g, origen, false);
    }

    
    public static ListaEnlazada<String> dfsIterativoMismoOrden(Grafo g, String origen) {
        return dfsIterativoImpl(g, origen, true);
    }

    private static ListaEnlazada<String> dfsIterativoImpl(Grafo g, String origen,
                                                          boolean vecinosEnOrdenInverso) {
        exigirVertice(g, origen);
        ListaEnlazada<String> orden = new ListaEnlazada<>();
        Diccionario<String, Boolean> visitados = new DiccionarioDinamico<>();

        int v = g.cantidadVertices();
        Pila<String> pila = new PilaVarianteA<>(v * v + 1);

        pila.apilar(origen);
        while (!pila.esVacia()) {
            String actual = pila.desapilar();
            if (visitados.existeClave(actual)) {
                continue; 
            }
            visitados.definir(actual, true);
            orden.agregar(actual);

            ListaEnlazada<String> vecinos = g.vecinos(actual);
            String[] arreglo = new String[vecinos.tamanio()];
            int k = 0;
            for (String vecino : vecinos) {
                arreglo[k] = vecino;
                k++;
            }
            if (vecinosEnOrdenInverso) {
                for (int i = arreglo.length - 1; i >= 0; i--) {
                    if (!visitados.existeClave(arreglo[i])) {
                        pila.apilar(arreglo[i]);
                    }
                }
            } else {
                for (int i = 0; i < arreglo.length; i++) {
                    if (!visitados.existeClave(arreglo[i])) {
                        pila.apilar(arreglo[i]);
                    }
                }
            }
        }
        return orden;
    }

    private static void exigirVertice(Grafo g, String v) {
        if (!g.existeVertice(v)) {
            throw new RuntimeException("El vértice no pertenece al grafo: " + v);
        }
    }
}
