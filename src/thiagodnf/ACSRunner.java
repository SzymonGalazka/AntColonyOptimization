package thiagodnf;

import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import thiagodnf.jacof.aco.AntColonySystem;
import thiagodnf.jacof.problem.Problem;
import thiagodnf.jacof.problem.kp.KnapsackProblem;
import thiagodnf.jacof.util.ExecutionStats;

import java.io.IOException;

public class ACSRunner {

	/** The class logger*/
	static final Logger LOGGER = Logger.getLogger(ACSRunner.class);
	
	public static void main(String[] args) throws ParseException, IOException {

		String instance = "./data/tsp2/oliver30.tsp";

		Problem problem = new KnapsackProblem(instance);

		AntColonySystem aco = new AntColonySystem(problem);

		aco.setNumberOfAnts(50);
		aco.setNumberOfIterations(3000);
		aco.setAlpha(1.0);
		aco.setBeta(2.0);
		aco.setRho(0.1);
		aco.setOmega(0.1);
		aco.setQ0(0.9);

		ExecutionStats es = ExecutionStats.execute(aco, problem);
		es.printStats();
	}

}
