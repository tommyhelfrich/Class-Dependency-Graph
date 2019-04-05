# Class-Dependency-Graph

This project mimics the Java command line compiler to recompile a class of the user's choosing, and all the other classes that depend on it. This application consists of five Java classes: ProjectGUI.java, Graph.java, Vertex.java, CycleException.java, and ClassException.java

ProjectGUI.java creates and defines the graphical user interface for the project and contains the main method. The user interface is hand coded, with action listeners that will build the directed graph and generate the topological order for the graph.

Graph.java creates and defines the class for building the the directed graph. The public class Graph contains constructor methods, a depth-first-search algorithm method, a method for generating topological order, a method for adding edges to the graph, a method for building an adjacency list, and a method for getting keys.

Vertex.java is a helper class that creates vertex objects used to build the directed graph. It consists of constructor methods, getter and setter methods, and methods for adding children, boolean methods for determing whether a vertex has children, is finished or is discovered, and a reset method.

CycleException.java is a checked exception class for handling cases where a cycle has occurred with the directed graph.

ClassException.java is checked exception class for handling invalid class names.
