import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Deque;
import java.util.Collection;
import java.util.PriorityQueue;


public class Graph<V> { 
   
    // Keep an index from node labels to nodes in the map
    protected Map<V, Vertex<V>> vertices; 

    /**
     * Construct an empty Graph.
     */
    public Graph() {
       vertices = new HashMap<V, Vertex<V>>();
    }

    /**
     * Retrieve a collection of vertices. 
     */  
    public Collection<Vertex<V>> getVertices() {
        return vertices.values();
    }

    public void addVertex(V u) {
        addVertex(new Vertex<>(u));
    }
    
    public void addVertex(Vertex<V> v) {
        if (vertices.containsKey(v.name)) 
            throw new IllegalArgumentException("Cannot create new vertex with existing name.");
        vertices.put(v.name, v);
    }

    /**
     * Add a new edge from u to v.
     * Create new nodes if these nodes don't exist yet. 
     * @param u unique name of the first vertex.
     * @param w unique name of the second vertex.
     * @param cost cost of this edge. 
     */
    public void addEdge(V u, V w, Double cost) {
        if (!vertices.containsKey(u))
            addVertex(u);
        if (!vertices.containsKey(w))
            addVertex(w);

        vertices.get(u).addEdge(
            new Edge<>(vertices.get(u), vertices.get(w), cost)
        );
    }

    public void addEdge(V u, V w) {
        addEdge(u, w,1.0);
    }

    public void printAdjacencyList() {
        for (V u : vertices.keySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append(u.toString());
            sb.append(" -> [ ");
            for (Edge e : vertices.get(u).getEdges()) {
                sb.append(e.target.name);
                sb.append(" ");
            }
            sb.append("]");
            System.out.println(sb.toString());
        }
    }    
  
   /**
    * Add a bidirectional edge between u and v. Create new nodes if these nodes don't exist
    * yet. This method permits adding multiple edges between the same nodes.
    *
    * @param u  
    *          the name of the source vertex.
    * @param v 
    *          the name of the target vertex.
    * @param cost
    *          the cost of this edge
    */
    public void addUndirectedEdge(V u, V v, Double cost) {
        addEdge(u, v, cost);
        addEdge(v, u, cost);
    }

    /****************************
     * Your code follows here.  *
     ****************************/

    // Part 1 Additional Functionality
    private void setCurrEdgeDist(Edge<V> edge) {
        Vertex<V> v1 = edge.source;
        Vertex<V> v2 = edge.target;
        edge.distance = Math.hypot(
                v2.posX - v1.posX,
                v2.posY - v1.posY
        );
    }
    
    // Part 1
    public void computeAllEuclideanDistances() {
        for (Vertex<V> currVertex : vertices.values()) {
            for (Edge<V> currEdge : currVertex.getEdges()) {
                setCurrEdgeDist((currEdge));
            }
        }
    }
    
    // Part 2 Additional Functionality
    private void returnVerticesToInitialState() { // resets everything to its initial state
        for (Vertex<V> vertex : vertices.values()) {
            vertex.visited = false;
            vertex.cost = -1; // we use this as a flag to set the cost later when we start traversing
            vertex.backpointer = null;
        }
    }
    
    // Part 2
    public void doDijkstra(V s) {
        returnVerticesToInitialState();

        // If we were to use a Priority Queue
        // PriorityQueue<Edge<V>> heap = new PriorityQueue<Edge<V>>();

        BinaryHeap<Edge<V>> heap = new BinaryHeap<>();

        Vertex<V> source = vertices.get(s);
        source.cost = 0.0;
        heap.insert( // if we used the Priority Queue we'd use "add" here
                new Edge<>(
                        null, source, 0.0
                )
        );

        while (!heap.isEmpty()) {
            Edge<V> minEdge = heap.deleteMin(); // if we used Priority Queue we'd use "remove" here
            Vertex<V> currVertex = minEdge.target;
            double distance = minEdge.distance;

            if (currVertex.visited) {
                continue;
            }
            currVertex.visited = true;

            for (Edge<V> currEdge : currVertex.getEdges()) {
                Vertex<V> currTarget = currEdge.target;
                double distanceToNextTarget = distance + currEdge.distance;
                if (currTarget.cost < 0 || distanceToNextTarget < currTarget.cost) {
                    currTarget.cost = distanceToNextTarget;
                    currTarget.backpointer = currVertex;

                    heap.insert( // if we used the Priority Queue we'd use "add" here
                            new Edge<>(
                                    null, currTarget, distanceToNextTarget
                            )
                    );
                }
            }
        }
    }


    // Part 3
    public List<Edge<V>> getDijkstraPath(V s, V t) {
        doDijkstra(s);

        LinkedList<Edge<V>> DijikstraPath = new LinkedList<>();

        Vertex<V> cur = vertices.get(t);
        while (cur != null) {
            Vertex<V> prev = cur.backpointer;
            if (prev != null) {
                DijikstraPath.addFirst(
                        new Edge<>(
                                prev, cur, cur.cost - prev.cost
                        )
                );
            }
            cur = prev;
        }

        return DijikstraPath;
    }
}
