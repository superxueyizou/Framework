package modeling.uas;

/**
 *
 * @author Robert Lee
 */
public class UASPerformance
{
	//the maximum possible values of the uasBag statistics
	private double maxSpeed;
    private double maxAcceleration;
	private double maxDeceleration;
	private double maxTurning;
	
	//the current limits of speed, etc for the uas.
	private double currentMaxSpeed;
	private double currentMaxAcceleration;
	private double currentMaxDeceleration;
	private double currentMaxTurning;
	
	public UASPerformance(double maxUASSpeed, double maxUASAcceleration, double maxUASDeceleration, double maxUASTurning)
	{
		maxSpeed = maxUASSpeed;
		maxAcceleration = maxUASAcceleration;
		maxDeceleration = maxUASDeceleration;
		maxTurning = maxUASTurning;
		
		currentMaxSpeed = maxSpeed;
		currentMaxAcceleration = maxAcceleration;
		currentMaxDeceleration = maxDeceleration;
		currentMaxTurning = maxTurning;
	}
	
	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public double getMaxAcceleration() {
		return maxAcceleration;
	}

	public void setMaxAcceleration(double maxAcceleration) {
		this.maxAcceleration = maxAcceleration;
	}

	public double getMaxDeceleration() {
		return maxDeceleration;
	}

	public void setMaxDeceleration(double maxDeceleration) {
		this.maxDeceleration = maxDeceleration;
	}

	public double getMaxTurning() {
		return maxTurning;
	}

	public void setMaxTurning(double maxTurning) {
		this.maxTurning = maxTurning;
	}
	
	//Methods to set the statistics of the uas
	public void setCurrentMaxSpeed(double speed) {currentMaxSpeed = speed;}
	public void setCurrentMaxAcceleration(double accel) {currentMaxAcceleration = accel;}
	public void setCurrentMaxDeceleration(double decel) {currentMaxDeceleration = decel;}
	public void setCurrentMaxTurning(double turning) {currentMaxTurning = turning;}
	
	//Accessor methods for the statistics of the uas
	public double getCurrentMaxSpeed() {return currentMaxSpeed;}
	public double getCurrentMaxAccel() {return currentMaxAcceleration;}
	public double getCurrentMaxDecel() {return currentMaxDeceleration;}
	public double getCurrentMaxTurning() {return currentMaxTurning;}
	
	/**
	 * A method which sets the current statistics of the uas to be the original
	 * values they were set as.
	 */
	public void reset()
	{
		currentMaxSpeed = maxSpeed;
		currentMaxAcceleration = maxAcceleration;
		currentMaxDeceleration = maxDeceleration;
		currentMaxTurning = maxTurning;
	}
	

}
