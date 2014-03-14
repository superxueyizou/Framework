package modeling.saa.sense;

import tools.CONFIGURATION;

public class PerfectSensor extends Sensor 
{	
	public double range;
	public double falsePositive;
	public double falseNegative;	 

	public PerfectSensor()
	{
		range = 9260*CONFIGURATION.lengthScale;
		falsePositive=0;
		falseNegative=0;
	}

}
