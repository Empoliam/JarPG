package utilities.noise;
import java.util.Random;

public class SimplexNoise {

	SimplexNoise_octave[] octaves;
	double frequencies[];
	double amplitudes[];

	int largestFeature;
	double persistence;
	int seed;

	public SimplexNoise(int largestFeature, double persistence, int seed)
	{

		this.largestFeature = largestFeature;
		this.persistence = persistence;
		this.seed = seed;

		int numberOfOctaves = (int) Math.ceil(Math.log10(largestFeature)/Math.log10(2));

		octaves = new SimplexNoise_octave[numberOfOctaves];
		frequencies = new double[numberOfOctaves];
		amplitudes = new double[numberOfOctaves];

		Random rand = new Random(seed);

		for (int k = 0; k < numberOfOctaves; k++)
		{

			octaves[k] = new SimplexNoise_octave(rand.nextInt());

			frequencies[k] = Math.pow(2, k);
			amplitudes[k] = Math.pow(persistence, octaves.length-k);

		}

	}

	public double getNoise(int x, int y)
	{

		double result = 0;

		for(int i=0;i<octaves.length;i++){
			double frequency = Math.pow(2,i);
			double amplitude = Math.pow(persistence,octaves.length-i);

			result=result+octaves[i].noise(x / frequency, y / frequency)* amplitude;
		}	

		return result;
		
	}

}
