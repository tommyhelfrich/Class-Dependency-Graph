/*
 *File Name: ProjectGUI.java
 *Author: Thomas Helfrich
 * Date: March 09, 2019
 * Purpose: Create and define the project graphical user interface
 * and provide the main method for the project.
 */

package dependencyproject;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.util.*;


public class ProjectGUI extends JFrame {
    private static final int TEXT_FIELD_WIDTH = 7;
    Graph<String> graph;
    boolean initialized = false;

    public ProjectGUI(){
        super("Class Dependency Graph");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 10));

        JPanel inputFrame = new JPanel(new BorderLayout(25,0));
        inputFrame.setBorder(new EmptyBorder(10, 20, 0, 20));
        JPanel outputFrame = new JPanel(new BorderLayout());

        JPanel inputLabels = new JPanel(new BorderLayout());
        JPanel inputFields = new JPanel(new BorderLayout());
        JPanel inputButtons = new JPanel(new BorderLayout(10,5));

        JLabel fileLabel = new JLabel("Input file name: ");
        JLabel classLabel = new JLabel("Class to recompile: ");

        final JTextField fileField = new JTextField(TEXT_FIELD_WIDTH);
        final JTextField classField = new JTextField(TEXT_FIELD_WIDTH);

        JButton buildButton = new JButton("Build Directed Graph");
        JButton orderButton = new JButton("Topological Order");

        JLabel recompileLabel = new JLabel("Recompilation Order: ");

        final JTextArea outputArea = new JTextArea(11, 50);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        outputArea.setEditable(false);

        buildButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                Scanner fileIn = null;
                String fileName = fileField.getText();


                try {
                    fileIn = new Scanner(new BufferedReader(
                            new FileReader(fileName)));
                    graph = new Graph();
                    graph.initialize(fileIn);
                    initialized = true;
                    JOptionPane.showMessageDialog(null,
                            "Graph built successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                }
                catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "File Did Not Open.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                } catch (CycleException e) {
                    JOptionPane.showMessageDialog(null, "A cycle has occurred. Please enter new valid input.");
                }
                finally {
                    if (fileIn != null) {
                        fileIn.close();
                    }
                }

            }
        });

        orderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (!initialized) {
                    JOptionPane.showMessageDialog(null, "No graph has been " +
                                    "initialized. Please initialize a graph.",
                            "Invalid Graph", JOptionPane.ERROR_MESSAGE);
                }

                else {
                    try {
                        outputArea.setText(graph.topologicalOrder(
                                classField.getText()));
                    }
                    catch(CycleException e) {
                        JOptionPane.showMessageDialog(null, e.getCycle() +
                                ". A cycle has occurred. Enter new input.");
                    }
                    catch(ClassException exc) {
                        JOptionPane.showMessageDialog(null, exc.getClassName() +
                                        " is not a valid class name. Please enter a valid"
                                        + " class name.", "Invalid Class Name",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        inputLabels.add(fileLabel, BorderLayout.NORTH);
        inputLabels.add(classLabel, BorderLayout.SOUTH);

        inputFields.add(fileField, BorderLayout.NORTH);
        inputFields.add(classField, BorderLayout.SOUTH);

        inputButtons.add(buildButton, BorderLayout.NORTH);
        inputButtons.add(orderButton, BorderLayout.SOUTH);

        inputFrame.add(inputLabels, BorderLayout.WEST);
        inputFrame.add(inputFields, BorderLayout.CENTER);
        inputFrame.add(inputButtons, BorderLayout.EAST);

        outputFrame.add(recompileLabel, BorderLayout.NORTH);
        outputFrame.add(outputArea, BorderLayout.SOUTH);

        add(inputFrame, BorderLayout.NORTH);
        add(outputFrame, BorderLayout.SOUTH);
    }



    public static void main(String[] args) {
        ProjectGUI gui = new ProjectGUI();
        gui.pack();
        gui.setVisible(true);
    }

}



