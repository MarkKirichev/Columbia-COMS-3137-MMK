# Programming Homework 4 

## Problem 1: Merge Sort in Scala (30 pts)

The Merge Sort implementation discussed in class ultimately rearranges the input array (by copying the merged result back from the temporary array). In a functional programming language you would use immutable data structures, so you cannot modify the input array. Instead, the return value of each recursive call to Merge Sort should be a sorted list. A new list is created every time the the sorted sublists (return values of each recursive call) are merged. 

Implement this version of MergeSort in Scala using immutable lists. You will also have to write your own recursive merge method as well (which is the most challenging part of this problem). A template for your program, which contains a main method to test your code, is provided in `MergeSort.scala`. Make sure that you only use immutable data structures and that your functions do not have any side effects.


## Problem 2: Iterative Merge Sort (30 pts) 
The Merge Sort implementation discussed in class uses recursion. In this problem, you will write a non-recursive (iterative) implementation of Merge Sort. Your code must not use recursion. Your algorithm should start with each pair of elements in the array and then sort larger and larger subarrays bottom-up. For instance, to sort [1,9,4,2,8,7,3,5,0] you would perform the following steps:

    size 2: [1,9] [2,4] [7,8] [3,5] [0]
    size 4: [1,2,4,9] [3,5,7,8] [0]
    size 8: [1,2,3,4,5,7,8,9] [0]
    final: [0,1,2,3,4,5,7,8,9]

Note how these steps are different from the recursive merge sort.

You can use O(N) space (in addition to the input array) and your algorithm should have the same running time as recursive merge sort. Write a class called IterMergeSort.java. Within this class create the method `public static void mergeSort(Integer[] inputArray)` that sorts the array inputArray. You can modify/use the the existing merge method (which is identical to the one discussed in class).  Finally, test your algorithm in the main method on arrays of different length. Make sure your code works on arrays whose length is not a power of two and arrays with odd length. You may also test corner cases such as arrays of length 1 and 2.  

## Problem 3: Dijkstra's Algorithm (40 pts)

In this project you will work with a graph representing train connections between North American cities (the graph represents train routes in the popular board game [Ticket to Ride](https://boardgamegeek.com/boardgame/9209/ticket-ride).
The file `ttrvertices.txt` contains a list of cities and their (X,Y) coordinates on the map. These are the vertices in the graph. The file `ttredges.txt` lists direct connections between pairs of cities. These links are *bidirectional*, if you can travel from NewYork to Boston, you can get from Boston to NewYork. These are the (undirected) edges of the graph. 

In this problem, you will implement Dijkstra's algorithm on the map.  We are providing a representation for the graph, as well as code to visualize the map and the output of your algorithm. Compile all java sources and run the class Display. This should display the map and a user interface. 

Carefully read through the classes `Vertex.java` and `Edge.java`, which represent the vertices and edges of a graph. 

You will only have to modify `Graph.java`, which represents a graph and will contain your code implementing Dijkstra's.  The graph representation is essentially identical to the adjacency lists based implementation discussed in class. You will need to use the instance variable `vertices`, which contains a mapping from city names to `Vertex` instances after the graph is read from the data files.
Note also that `Graph<V>` is generic. V is the data type used to label each vertex. For the ticket-to-ride map, this will be a `Graph<String>`. 
The Graph representation is for a directed graph. When the graph data is read in from the two files, two directed edges are added to the graph for each undirected edge specified in the data. 

### Part 1 (8 pts)
Initially the edge cost (distance) for all edges is set to 1.0. We need to compute the actual distances between the cities. The GUI already contains a button for this purpose, but it does not do anything. In the class `Graph`, implement the method `computeAllEuclideanDistances()` which should compute the euclidean distance between all cities that have a direct link, and set the weights for the corresponding edges in the graph. The euclidean distance between two points (ux, uy) and (vx, vy) is defined as sqrt((ux-vx)^2 + (uy-vy)^2). You might want to use `java.lang.Math` to help with the calculation. After recompiling, clicking the button should now display the correct distances. 

### Part 2 (20 pts)
Implement the method `doDijkstra(String s)` in Graph.java. This method should implement Dijkstra's algorithm starting at the city with name s. Use the distances associated with the edges. The method should update the `distance` and `prev` instance variables of the `Vertex` objects. 
Use a the provided BinaryHeap class in `BinaryHeap.java` as a priority queue, which provides an `insert(x)` and a `deleteMin()` method. 

Important side note: The shortest path cost associated with vertices already on the priority queue can decrease (be relaxed) throughout the algorithm.
When this happens, the cost value for a vertex that is already in the heap will change and a duplicate entry will be added. The vertex that was already in the heap might now be in an incorrect location according to the heap order property (because its cost changed). The best solution to this problem is to use a heap implementation that supports the decreaseKey operation we discussed in class. This has already been implemented in `BinaryHeap.java`. Essentialy, the heap uses a hashmap to keep track of the current index for each entry. On percolate up/down the hashmap is updated accordingly. On insert, the heap first checks if an item already exists. If so, it simply runs percolateUp on the item (which now has a modified priority) to make sure it ends up at the correct index. Take a look at the provided `BinaryHeap.java`. An alternative solution to this problem, using a standard heap, would be allow duplicate vertex *names* on the heap, making sure that each entry maintains a separate cost that never changes. However, in your assignment, please use the modified BinaryHeap.

### Part 3 (12 pts)
Implement the method `public List<Edge<V>> getDijkstraPath(V s, V t)`, which first calls `doDijkstra(s)` and then uses the `distance` and `prev` instance variables of the Vertex objects to find the shortest path between s and t. The result of this method should be a *list of edges* on the path.
If implemented correctly, the "Draw Dijkstra's Path" button in the GUI should display the shortest path between the two selected cities.
