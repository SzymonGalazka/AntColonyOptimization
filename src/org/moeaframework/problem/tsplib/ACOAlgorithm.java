package org.moeaframework.problem.tsplib;

import org.moeaframework.core.*;

import java.io.NotSerializableException;
import java.io.Serializable;

public interface ACOAlgorithm extends EvolutionaryAlgorithm {

    //todo
    @Override
    Population getPopulation();

    @Override
    NondominatedPopulation getArchive();

    @Override
    Problem getProblem();

    @Override
    NondominatedPopulation getResult();

    @Override
    void step();

    @Override
    void evaluate(Solution solution);

    @Override
    int getNumberOfEvaluations();

    @Override
    boolean isTerminated();

    @Override
    void terminate();

    @Override
    Serializable getState() throws NotSerializableException;

    @Override
    void setState(Object o) throws NotSerializableException;
}
