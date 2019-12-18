package thiagodnf.jacof.util.random;

public interface PseudoRandomGenerator {

	int nextInt(int lowerBound, int upperBound);

	double nextDouble(double lowerBound, double upperBound);

	double nextDouble();

	void setSeed(long seed);

	long getSeed();

	String getName();
}
