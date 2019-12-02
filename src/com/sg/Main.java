package com.sg;

import org.moeaframework.core.Algorithm;
import org.moeaframework.core.EvolutionaryAlgorithm;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.spi.AlgorithmFactory;
import org.moeaframework.problem.tsplib.*;
import org.moeaframework.problem.tsplib.Tour;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;


public class Main {


    public static void main(String[] args) throws IOException {
        // create the TSP problem instance and display panel

        //easy
        TSPInstance instance = new TSPInstance(new File("./data/tsp/pr76.tsp"));
        //mid
//        TSPInstance instance = new TSPInstance(new File("./data/tsp/d198.tsp"));
        //hard
//        TSPInstance instance = new TSPInstance(new File("./data/tsp/pr439.tsp"));

        TSPPanel panel = new TSPPanel(instance);
        panel.setAutoRepaint(false);

        // create other components on the display
        StringBuilder progress = new StringBuilder();
        JTextArea progressText = new JTextArea();

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(panel);
        splitPane.setBottomComponent(new JScrollPane(progressText));
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(1.0);

        // display the panel on a window
        JFrame frame = new JFrame(instance.getName());
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(splitPane, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // create the optimization problem and evolutionary algorithm
        Problem problem = new Helpers.TSPProblem(instance);

        Properties properties = new Properties();
        properties.setProperty("swap.rate", "0.7");
        properties.setProperty("insertion.rate", "0.9");
        properties.setProperty("pmx.rate", "0.4");

        Algorithm algorithm = AlgorithmFactory.getInstance().getAlgorithm(
                "NSGAII", properties, problem);

        int iteration = 0;

        // now run the evolutionary algorithm
        while (frame.isVisible()) {
            algorithm.step();
            iteration++;

            // clear existing tours in display
            panel.clearTours();

            // display population with light gray lines
            if (algorithm instanceof EvolutionaryAlgorithm) {
                EvolutionaryAlgorithm ea = (EvolutionaryAlgorithm)algorithm;

                for (Solution solution : ea.getPopulation()) {
                    panel.displayTour(Helpers.toTour(solution), Helpers.lightGray);
                }
            }

            // display current optimal solutions with red line
            Tour best = Helpers.toTour(algorithm.getResult().get(0));
            panel.displayTour(best, Color.RED, new BasicStroke(2.0f));
            progress.insert(0, "Iteration " + iteration + ": " +
                    best.distance(instance) + "\n");
            progressText.setText(progress.toString());

            //write to console
            if (iteration % 5 == 0) {
                System.out.println(iteration + ". ITERATION: " + best.distance(instance));
            }

            // repaint the TSP display
            panel.repaint();
        }
    }

}
