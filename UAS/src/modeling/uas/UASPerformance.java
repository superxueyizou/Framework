package modeling.uas;

/**
 *
 * @author Robert Lee
 */
public class UASPerformance
{
	//the maximum possible values of the uasBag statistics
	private double maxSpeed;
	private double minSpeed;	
	private double maxClimb;
	private double maxDescent;
	private double maxTurning;
    private double maxAcceleration;
	private double maxDeceleration;
	
	
	//the current limits of speed, etc for the uas.
	private double currentMaxSpeed;
	private double currentMinSpeed;	
	private double currentMaxClimb;
	private double currentMaxDescent;
	private double currentMaxTurning;
	private double currentMaxAcceleration;
	private double currentMaxDeceleration;
	
	
	public UASPerformance(double maxUASSpeed, double minUASSpeed, double maxUASClimb, double maxUASDescent, double maxUASTurning, double maxUASAcceleration, double maxUASDeceleration)
	{
		maxSpeed = maxUASSpeed;
		minSpeed = minUASSpeed;
		maxClimb = maxUASClimb;
		maxDescent = maxUASDescent;
		maxTurning = maxUASTurning;
		maxAcceleration = maxUASAcceleration;
		maxDeceleration = maxUASDeceleration;
		
		
		currentMaxSpeed = maxSpeed;
		currentMinSpeed = minSpeed;
		currentMaxClimb	= maxClimb;
		currentMaxDescent = maxDescent;
		currentMaxTurning = maxTurning;
		currentMaxAcceleration = maxAcceleration;
		currentMaxDeceleration = maxDeceleration;
		
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

	public double getMinSpeed() {
		return minSpeed;
	}

	public void setMinSpeed(double minSpeed) {
		this.minSpeed = minSpeed;
	}

	public double getMaxClimb() {
		return maxClimb;
	}

	public void setMaxClimb(double maxClimb) {
		this.maxClimb = maxClimb;
	}

	public double getMaxDescent() {
		return maxDescent;
	}

	public void setMaxDescent(double maxDescent) {
		this.maxDescent = maxDescent;
	}

	public double getCurrentMinSpeed() {
		return currentMinSpeed;
	}

	public void setCurrentMinSpeed(double currentMinSpeed) {
		this.currentMinSpeed = currentMinSpeed;
	}

	public double getCurrentMaxClimb() {
		return currentMaxClimb;
	}

	public void setCurrentMaxClimb(double currentMaxClimb) {
		this.currentMaxClimb = currentMaxClimb;
	}

	public double getCurrentMaxDescent() {
		return currentMaxDescent;
	}

	public void setCurrentMaxDescent(double currentMaxDescent) {
		this.currentMaxDescent = currentMaxDescent;
	}

	public double getCurrentMaxAcceleration() {
		return currentMaxAcceleration;
	}

	public double getCurrentMaxDeceleration() {
		return currentMaxDeceleration;
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
		currentMinSpeed = minSpeed;
		currentMaxClimb = maxClimb;
		currentMaxDescent = maxDescent;
		currentMaxTurning = maxTurning;
		currentMaxAcceleration = maxAcceleration;
		currentMaxDeceleration = maxDeceleration;
		
	}
	

}
