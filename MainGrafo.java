
public class MainGrafo {

    public static void main(String[] args) {
        Grafo g = new GrafoMatriz(8);
        String[] personas = {"Ana", "Bruno", "Carla", "Diego", "Elena", "Fede", "Gaby", "Hugo"};
        for (String p : personas) {
            g.agregarVertice(p);
        }
        g.agregarArista("Ana", "Bruno");
        g.agregarArista("Ana", "Carla");
        g.agregarArista("Bruno", "Diego");
        g.agregarArista("Carla", "Elena");
        g.agregarArista("Diego", "Elena");
        g.agregarArista("Fede", "Gaby");

        System.out.println("=== Grafo de ejemplo ===");
        System.out.println("Cantidad de vértices: " + g.cantidadVertices());
        for (String v : g.vertices()) {
            System.out.println("  " + v + " -> vecinos: " + texto(g.vecinos(v)));
        }
        System.out.println("existeArista(Ana, Bruno) (true): " + g.existeArista("Ana", "Bruno"));
        System.out.println("existeArista(Bruno, Ana) (true, simétrica): " + g.existeArista("Bruno", "Ana"));
        System.out.println("existeArista(Ana, Diego) (false): " + g.existeArista("Ana", "Diego"));

        System.out.println("\n=== Parte 3: recorridos desde Ana ===");
        System.out.println("BFS:                          " + texto(RecorridosGrafo.bfs(g, "Ana")));
        System.out.println("DFS recursivo:                " + texto(RecorridosGrafo.dfs(g, "Ana")));
        System.out.println("DFS iterativo (Pila):         " + texto(RecorridosGrafo.dfsIterativo(g, "Ana")));
        System.out.println("DFS iterativo (mismo orden):  " + texto(RecorridosGrafo.dfsIterativoMismoOrden(g, "Ana")));
        System.out.println("BFS desde Fede:               " + texto(RecorridosGrafo.bfs(g, "Fede")));
        System.out.println("BFS desde Hugo:               " + texto(RecorridosGrafo.bfs(g, "Hugo")));

        System.out.println("\n=== Parte 4: estanConectados ===");
        mostrarConectados(g, "Ana", "Elena");   
        mostrarConectados(g, "Bruno", "Carla"); 
        mostrarConectados(g, "Fede", "Gaby");   
        mostrarConectados(g, "Ana", "Fede");    
        mostrarConectados(g, "Ana", "Hugo");    
        mostrarConectados(g, "Hugo", "Hugo");   

        System.out.println("\n=== Parte 4: componentesConexas ===");
        System.out.println("componentesConexas(g) (3): " + UtilizacionGrafo.componentesConexas(g));
        System.out.println("componentesConexas(grafo vacío) (0): "
                + UtilizacionGrafo.componentesConexas(new GrafoMatriz(3)));

        System.out.println("\n=== Parte 4: caminoMasCorto ===");
        mostrarCamino(g, "Ana", "Elena");    
        mostrarCamino(g, "Ana", "Diego");    
        mostrarCamino(g, "Bruno", "Carla");  
        mostrarCamino(g, "Ana", "Bruno");    
        mostrarCamino(g, "Fede", "Gaby");    
        mostrarCamino(g, "Ana", "Ana");      
        mostrarCamino(g, "Ana", "Fede");     
        mostrarCamino(g, "Elena", "Hugo");   

        System.out.println("\n=== Precondiciones ===");
        probarError("agregarVertice con etiqueta repetida", () -> g.agregarVertice("Ana"));
        probarError("agregarVertice con el grafo lleno", () -> g.agregarVertice("Ivan"));
        probarError("agregarArista con un vértice inexistente", () -> g.agregarArista("Ana", "Zoe"));
        probarError("agregarArista de un vértice consigo mismo", () -> g.agregarArista("Ana", "Ana"));
        probarError("existeArista con un vértice inexistente", () -> g.existeArista("Zoe", "Ana"));
        probarError("vecinos de un vértice inexistente", () -> g.vecinos("Zoe"));
        probarError("BFS desde un vértice inexistente", () -> RecorridosGrafo.bfs(g, "Zoe"));
        g.agregarArista("Ana", "Bruno"); 
        System.out.println("agregarArista repetida deja los vecinos de Ana igual (Bruno Carla): "
                + texto(g.vecinos("Ana")));
    }

    private static void mostrarConectados(Grafo g, String a, String b) {
        System.out.println("estanConectados(" + a + ", " + b + "): " + UtilizacionGrafo.estanConectados(g, a, b));
    }

    private static void mostrarCamino(Grafo g, String a, String b) {
        int d = UtilizacionGrafo.caminoMasCorto(g, a, b);
        System.out.println("caminoMasCorto(" + a + ", " + b + "): "
                + (d == UtilizacionGrafo.NO_HAY_CAMINO ? "-1 (no hay camino)" : d + " arista(s)"));
    }

    private static void probarError(String descripcion, Runnable accion) {
        try {
            accion.run();
            System.out.println(descripcion + ": NO lanzó error (inesperado)");
        } catch (RuntimeException e) {
            System.out.println(descripcion + " -> error: " + e.getMessage());
        }
    }

    private static String texto(ListaEnlazada<String> lista) {
        StringBuilder sb = new StringBuilder();
        for (String s : lista) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(s);
        }
        return sb.length() == 0 ? "(ninguno)" : sb.toString();
    }
}
