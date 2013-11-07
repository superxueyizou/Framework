/**
 * 
 */
package modeling;

/**
 * @author Xueyi
 *
 */
public class MathVector {

	/**
	 * 
	 * 
	 */
	private double magnitude;
	private double direction;
	
	public MathVector() {
		// TODO Auto-generated constructor stub
		this.magnitude =0;
		this.direction =0;
	}
	
	public MathVector(double m, double d) {
		// TODO Auto-generated constructor stub
		this.magnitude =m;
		this.direction =d;
	}
	
	
	
	/* Copy constructor */
	public MathVector(MathVector mV) 
	{
		this.direction = mV.direction;
		this.magnitude = mV.magnitude;
	}

	/* Accessor methods: Allow the client to access the magnitude and direction of the math vector */
	public double getDirection()
    {
		return direction;
	}
	
	public double getMagnitude()
    {
		return magnitude;
	}



	/* Modifier methods: Allow the client to modify the magnitude and direction of the math vector */
	public void setDirection(double d) {
		this.direction = d;
	}

	public void setMagnitude(double m) {
		this.magnitude = m;
	}

	/*
	Vector-based addition, subtract, and multiplication operator overloading.
	*/

	/* Dot produt. */ 
	public double dotProduct(MathVector mV){
		/* Ax = mag * cos(degree), Ay = mag * sin(degree) */
		double Ax = this.magnitude * Math.cos(Math.toRadians(this.direction));
		double Ay = this.magnitude * Math.sin(Math.toRadians(this.direction));

		/* Bx = mag * cos(degree), By = mag * sin(degree) */
		double Bx = mV.magnitude *  Math.cos(Math.toRadians(mV.direction));
		double By = mV.magnitude *  Math.sin(Math.toRadians(mV.direction));

		return Ax*Bx+Ay*By;

	}

	public MathVector plus(MathVector mV)
    {
		MathVector result= new MathVector(); /* Copy vector */
		
		double Rx = 0.0, Ry = 0.0, R = 0.0, Theta = 0.0;

		/* Ax = mag * cos(degree), Ay = mag * sin(degree) */
		double Ax = this.magnitude * Math.cos(Math.toRadians(this.direction));
		double Ay = this.magnitude * Math.sin(Math.toRadians(this.direction));

		/* Bx = mag * cos(degree), By = mag * sin(degree) */
		double Bx = mV.magnitude *  Math.cos(Math.toRadians(mV.direction));
		double By = mV.magnitude *  Math.sin(Math.toRadians(mV.direction));

		/* Perform addition */
		Rx = Ax + Bx;
		Ry = Ay + By;

		/* R = (Rx^2 + Ry^2)^(1/2) */
		R = Math.sqrt(Rx*Rx + Ry*Ry);

		/* Theta = arctan2(Ry, Rx) */
		Theta = Math.toDegrees(Math.atan2(Ry , Rx));

		/* Assign new values */
		result.setMagnitude(R);
		result.setDirection(Theta);

		/* Return resulting vector */
		return result;

	}



    public MathVector minus(MathVector mV)
    {
    	MathVector result= new MathVector(); 
    	
    	double Rx = 0.0, Ry = 0.0, R = 0.0, Theta = 0.0;
		/* Ax = mag * cos(degree), Ay = mag * sin(degree) */
		double Ax = this.magnitude * Math.cos(Math.toRadians(this.direction));
		double Ay = this.magnitude * Math.sin(Math.toRadians(this.direction));

		/* Bx = mag * cos(degree), By = mag * sin(degree) */
		double Bx = mV.magnitude *  Math.cos(Math.toRadians(mV.direction));
		double By = mV.magnitude *  Math.sin(Math.toRadians(mV.direction));

		/* Perform addition with negative vector */
		Rx = Ax - Bx;
		Ry = Ay - By;

		/* R = (Rx^2 + Ry^2)^(1/2) */
		R = Math.sqrt(Rx*Rx + Ry*Ry);

		/* Theta = arctan2(Ry, Rx) */
		Theta = Math.toDegrees(Math.atan2(Ry , Rx));


		/* Assign new values */
		result.setMagnitude(R);
		result.setDirection(Theta);

		/* Return resulting vector */
		return result;
	}

	/*
	Double-based operator overloading for multiplication and division.
	*/

	public MathVector multiply(double val)
    {
		MathVector result= new MathVector();
		result.setMagnitude(this.getMagnitude()*val);
		result.setDirection(this.getDirection());
		return result;
	}

	
	public MathVector divide(double val) {
		/* Scalar division involves dividing the magnitude by the scale */
		MathVector result= new MathVector();
		result.setMagnitude(this.getMagnitude()/val);
		result.setDirection(this.getDirection());
		return result;
	}


}
