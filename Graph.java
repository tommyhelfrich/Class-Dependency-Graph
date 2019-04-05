/*
 *File Name: Graph.java
 *Author: Thomas Helfrich
 * Date: March 09, 2019
 * Purpose: Create and define the class for building the directed graph
 * and define its methods.
 */

package dependencyproject;

import java.util.*;
import java.util.Map.Entry;

public class Graph<T> {
    private HashMap<Integer, String> vertices;
    private ArrayList<LinkedList<Integer>> adjacencies;
    private int curIdx = 0;
    private HashMap<String, Vertex> vertexMap = new HashMap<>();
    private ArrayList<String> headList;
    private Stack<Vertex> vertStack = new Stack();

    public Graph() {
        vertices = new HashMap<>();
        vertexMap = new HashMap<>();
        adjacencies = new ArrayList<>();
        headList = new ArrayList<>();
    }

    public void initialize(Scanner input) throws CycleException {
        Scanner line;
        String name;
        Vertex<String> curVertex;

        while (input.hasNextLine()) {
            line = new Scanner(input.nextLine());
            name = line.next();
            headList.add(name);

            if (vertexMap.containsKey(name)) {
                curVertex = vertexMap.get(name);
                if (curVertex.isDiscovered()) {
                    throw new CycleException(curVertex.getValue());
                }
                else {
                    curVertex.setDiscovered();
                }
            }
            else {
                vertexMap.put(name, new Vertex<String>(name));
            }

            while(line.hasNext()) {
                String newName = line.next();
                if (vertexMap.containsKey(newName)) {
                    Vertex<String> newVertex = vertexMap.get(newName);
                    vertexMap.get(name).addChild(newVertex);
                    if (newVertex.isDiscovered()) {
                        throw new CycleException(newVertex.getValue());
                    }
                    else {
                        newVertex.setDiscovered();
                    }
                }

                else {
                    vertexMap.put(newName, new Vertex<String>(newName));
                    vertexMap.get(name).addChild(vertexMap.get(newName));
                }
            }
        }

        int vertIdx = 0;
        String newName = "";

        for (String head : headList) {
            curVertex = vertexMap.get(head);
            buildAdjacencyList(curVertex);
        }

    }

    public void buildAdjacencyList(Vertex<String> vertex) {
        String curName = "";
        int vertIdx = 0;
        String newName = "";

        curName = vertex.getValue();
        if (vertices.containsValue(curName)) {
            vertIdx = getKey(curName);
        }
        else {
            vertIdx = curIdx;
            vertices.put(curIdx, curName);
            curIdx++;
            adjacencies.add(new LinkedList<Integer>());
        }
        if (vertex.hasChildren()){
            for (Vertex<String> child : vertex.getChildren()) {
                addEdge(vertex, child);
                buildAdjacencyList(child);
            }
        }
    }

    public void addEdge(Vertex<String> source, Vertex<String> destination) {
        int sourceIdx = getKey(source.getValue());
        int destIdx;
        if (vertices.containsValue(destination.getValue())) {
            destIdx = getKey(destination.getValue());
        }
        else {
            destIdx = curIdx;
            curIdx++;
            vertices.put(destIdx, destination.getValue());
            adjacencies.add(new LinkedList<Integer>());
        }

        adjacencies.get(sourceIdx).add(destIdx);

    }


    public Integer getKey(String value) {
        for(Entry<Integer, String> entry : vertices.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    public String topologicalOrder(String vertName) throws CycleException {
        for (Entry<String, Vertex> entry : vertexMap.entrySet()) {
            entry.getValue().reset();
        }
        String result = "";

        if (vertices.containsValue(vertName)){

            depthFirstSearch(vertexMap.get(vertName));

            while (!vertStack.isEmpty()) {
                result += vertStack.pop().getValue() + " ";
            }

            return result;
        }

        else {
            throw new ClassException(vertName);
        }
    }


    public void depthFirstSearch(Vertex<String> vertex) throws CycleException {
        if (vertex.isDiscovered()) {
            throw new CycleException(vertex.getValue());
        }
        if (vertex.isFinished()) {

            return;
        }

        vertex.setDiscovered();

        if (vertex.hasChildren()) {
            for (Vertex<String> child : vertex.getChildren()) {
                depthFirstSearch(child);
            }

        }

        vertex.setFinished();

        vertStack.push(vertex);

    }

}
