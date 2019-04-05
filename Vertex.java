/*
 *File Name: Vertex.java
 *Author: Thomas Helfrich
 * Date: March 09, 2019
 * Purpose: Create and define the helper class that creates
 * the vertices used to build the directed graph.
 */

package dependencyproject;

import java.util.*;

public class Vertex<T> {
    private ArrayList<Vertex<T>> children;
    private T value;
    private boolean discovered = false;
    private boolean finished = false;

    public Vertex(T value) {
        this.value = value;
        children = new ArrayList<>();
    }

    public Vertex(T value, ArrayList<Vertex<T>> children) {
        this.value = value;
        this.children = children;
    }

    public void addChild(Vertex child) {
        children.add(child);
    }

    public ArrayList<Vertex<T>> getChildren() {
        return children;
    }

    public T getValue() {
        return value;
    }

    public boolean hasChildren() {
        return (children.size() > 0);
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setDiscovered() {
        discovered = true;
    }

    public void setFinished() {
        finished = true;
    }

    public void reset() {
        discovered = false;
        finished = false;
    }
}
