package dominant;

import ec.util.MersenneTwisterFast;
import sim.util.Double2D;
import tools.CALCULATION;

public class test {

	public test() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		Double2D v1= new Double2D(1,1);
		Double2D v2= new Double2D(-1,1);
		Double2D v3= new Double2D(-1,-1);
		Double2D v4= new Double2D(1,-1);
		
		Double2D v5= new Double2D(1,0);
		Double2D v6= new Double2D(0,1);
		Double2D v7= new Double2D(-1,0);
		Double2D v8= new Double2D(0,-1);
		
		Double2D v= v4;
		System.out.println(Math.toDegrees(v1.masonAngleWithDouble2D(v)));
		System.out.println(Math.toDegrees(v1.masonRotateAngleToDouble2D(v)));
		System.err.println(Math.toDegrees(v.masonLeftRotate(Math.PI/4).masonAngle()));

		

//		System.out.println(Math.toDegrees(CALCULATION.getAngle(v1,v2)));
//		System.out.println(CALCULATION.vectorLRotate(v7, Math.PI/4));
//		float a=1.0f;
//		MersenneTwisterFast r=new MersenneTwisterFast(1233455);
//		System.out.println(r.nextInt());
//		System.out.println(r.nextInt());
//		System.out.println(1.0e-6+1);
//		
	}

}
