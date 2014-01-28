package dominant;

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
		
		Double2D v= v8;
//		System.out.println(Math.toDegrees(CALCULATION.getMasonAngle(v)));
//		System.out.println(Math.toDegrees(CALCULATION.getMasonAngle(CALCULATION.vectorRRotate(v, Math.PI/12))));
//		System.out.println(Math.toDegrees(CALCULATION.getMasonAngle(CALCULATION.vectorLRotate(v, Math.PI/12))));
		
//		System.out.println(Math.toDegrees(CALCULATION.getRotateAngle(v6,v4)));
		System.out.println(Math.toDegrees(CALCULATION.getAngle(v6,v5)));
		
	}

}
