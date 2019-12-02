package com.sg;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;
import org.moeaframework.problem.tsplib.TSP2OptHeuristic;
import org.moeaframework.problem.tsplib.TSPInstance;
import org.moeaframework.problem.tsplib.Tour;

import java.awt.*;

public class Helpers {

    static final Color lightGray = new Color(128, 128, 128, 64);

    /**
     * Converts a MOEA Framework solution to a {@link Tour}.
     *
     * @param solution the MOEA Framework solution
     * @return the tour defined by the solution
     */
    static Tour toTour(Solution solution) {
        int[] permutation = EncodingUtils.getPermutation(
                solution.getVariable(0));

        // increment values since TSP nodes start at 1
        for (int i = 0; i < permutation.length; i++) {
            permutation[i]++;
        }

        return Tour.createTour(permutation);
    }

    /**
     * Saves a {@link Tour} into a MOEA Framework solution.
     *
     * @param solution the MOEA Framework solution
     * @param tour the tour
     */
    static void fromTour(Solution solution, Tour tour) {
        int[] permutation = tour.toArray();

        // decrement values to get permutation
        for (int i = 0; i < permutation.length; i++) {
            permutation[i]--;
        }

        EncodingUtils.setPermutation(solution.getVariable(0), permutation);
    }

    public static class TSPProblem extends AbstractProblem {

        /**
         * The TSP problem instance.
         */
        private final TSPInstance instance;

        /**
         * The TSP heuristic for aiding the optimization process.
         */
        private final TSP2OptHeuristic heuristic;

        /**
         * Constructs a new optimization problem for the given TSP problem
         * instance.
         *
         * @param instance the TSP problem instance
         */
        TSPProblem(TSPInstance instance) {
            super(1, 1);
            this.instance = instance;

            heuristic = new TSP2OptHeuristic(instance);
        }

        @Override
        public void evaluate(Solution solution) {
            Tour tour = toTour(solution);

            // apply the heuristic and save the modified tour
            heuristic.apply(tour);
            fromTour(solution, tour);

            solution.setObjective(0, tour.distance(instance));
        }

        @Override
        public Solution newSolution() {
            Solution solution = new Solution(1, 1);

            solution.setVariable(0, EncodingUtils.newPermutation(
                    instance.getDimension()));

            return solution;
        }

    }
}
