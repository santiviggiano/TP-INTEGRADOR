
public class UtilizacionGrafo {

  
    public static final int NO_HAY_CAMINO = -1;

   
    public static boolean estanConectados(Grafo g, String v1, String v2) {
        exigirVertice(g, v1);
        exigirVertice(g, v2);
        for (String alcanzable : RecorridosGrafo.bfs(g, v1)) {
            if (alcanzable.equals(v2)) {
                return true;
            }
        }
        return false;
    }

   
    public static int componentesConexas(Grafo g) {
        Diccionario<String, Boolean> visitados = new DiccionarioDinamico<>();
        int componentes = 0;
        for (String v : g.vertices()) {
            if (!visitados.existeClave(v)) {
                componentes++;
                for (String miembro : RecorridosGrafo.bfs(g, v)) {
                    visitados.definir(miembro, true);
                }
            }
        }
        return componentes;
    }

   
  
    public static int caminoMasCorto(Grafo g, String origen, String destino) {
        exigirVertice(g, origen);
        exigirVertice(g, destino);
        if (origen.equals(destino)) {
            return 0;
        }
        Diccionario<String, Integer> distancia = new DiccionarioDinamico<>();
        Cola<String> cola = new ColaVarianteB<>(g.cantidadVertices());

        distancia.definir(origen, 0);
        cola.encolar(origen);
        while (!cola.esVacia()) {
            String actual = cola.desencolar();
            int d = distancia.obtener(actual);
            for (String vecino : g.vecinos(actual)) {
                if (!distancia.existeClave(vecino)) {
                    if (vecino.equals(destino)) {
                        return d + 1; 
                    }
                    distancia.definir(vecino, d + 1);
                    cola.encolar(vecino);
                }
            }
        }
        return NO_HAY_CAMINO;
    }

    private static void exigirVertice(Grafo g, String v) {
        if (!g.existeVertice(v)) {
            throw new RuntimeException("El vértice no pertenece al grafo: " + v);
        }
    }
}
