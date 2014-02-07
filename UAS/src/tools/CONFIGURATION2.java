/**
 * 
 */
package tools;

/**
 * @author Xueyi
 *
 */
public class CONFIGURATION2 {

	/**
	 * 
	 */
	public CONFIGURATION2() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public static double worldX = 1500; // unit m
	public static double worldY = 1050; // unit m
	
	public static double lengthScale = 0.1; // 1m for ? grid
	//public static double timeScale = 1; // 1s for ? step
	
	public static double fieldXVal = worldX*lengthScale; //do not change
	public static double fieldYVal = worldY*lengthScale; //do not change	
	
	public static boolean collisionAvoidanceEnabler=true;
	public static boolean selfSeparationEnabler=true;
	public static boolean accidentDetectorEnabler=true;
	
	
	public static double selfDestDist = 1200*lengthScale;
	public static double selfDestAngle = Math.toRadians(0);	
	
	public static double selfMaxSpeed =2.235;
	public static double selfMinSpeed =1.0;
	public static double selfMaxAcceleration = 0.1;
	public static double selfMaxDeceleration = 0.1;
	public static double selfMaxClimb = 17.78*lengthScale;
	public static double selfMaxDescent = 20.23*lengthScale;
	public static double selfMaxTurning = Math.toRadians(5);
	public static double selfSpeed = 1.5;
	public static double selfViewingRange =38.8;
	public static double selfViewingAngle = Math.toRadians(60);
	public static double selfSensitivityForCollisions = 0.5;
	public static double selfSafetyRadius=1.667;
	public static double selfAlpha=1.5; //RVO[0,1.0], HRVO(1.0,2.0], ORCA(2.0,3.0]
	public static String selfCollisionAvoidanceAlgorithmSelection = "AVOAvoidanceAlgorithm"; //"AVOAvoidanceAlgorithm","HRVOAvoidanceAlgorithm","RVOAvoidanceAlgorithm","ORCAAvoidanceAlgorithm","TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 
	public static String selfSelfSeparationAlgorithmSelection = "SVOAvoidanceAlgorithm"; //"AVOAvoidanceAlgorithm","HRVOAvoidanceAlgorithm","RVOAvoidanceAlgorithm","ORCAAvoidanceAlgorithm","TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 

	
	
	public static boolean headOnSelected = true;
	public static boolean headOnIsRightSide = false ;
	public static double headOnOffset= 0*lengthScale;
	public static int headOnTimes=1;
	public static double headOnMaxSpeed =2.235;
	public static double headOnMinSpeed =1.0;
	public static double headOnMaxAcceleration = 0.1;
	public static double headOnMaxDeceleration = 0.1;
	public static double headOnMaxClimb = 17.78*lengthScale;
	public static double headOnMaxDescent = 20.23*lengthScale;
	public static double headOnMaxTurning = Math.toRadians(5);
	public static double headOnSpeed = 1.5;
	public static double headOnViewingRange =38.85;
	public static double headOnViewingAngle =  Math.toRadians(60);
	public static double headOnSensitivityForCollisions = 0.5;
	public static double headOnSafetyRadius=1.667;
	public static double headOnAlpha=1.5;
	public static String headOnCollisionAvoidanceAlgorithmSelection = "AVOAvoidanceAlgorithm";//"AVOAvoidanceAlgorithm","HRVOAvoidanceAlgorithm","RVOAvoidanceAlgorithm","ORCAAvoidanceAlgorithm","TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 
	public static String headOnSelfSeparationAlgorithmSelection = "SVOAvoidanceAlgorithm";//"AVOAvoidanceAlgorithm","HRVOAvoidanceAlgorithm","RVOAvoidanceAlgorithm","ORCAAvoidanceAlgorithm","TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 

	
	public static boolean crossingSelected = false;
	public static double crossingEncounterAngle= Math.toRadians(30);
	public static boolean crossingIsRightSide=true;
	public static int crossingTimes=1;
	public static double crossingMaxSpeed =92.6*lengthScale;
	public static double crossingMinSpeed =51.4*lengthScale;
	public static double crossingMaxAcceleration = 10*lengthScale;
	public static double crossingMaxDeceleration = 10*lengthScale;
	public static double crossingMaxClimb = 17.78*lengthScale;
	public static double crossingMaxDescent = 20.23*lengthScale;
	public static double crossingMaxTurning = Math.toRadians(2.50);
	public static double crossingSpeed = 77.2*lengthScale;
	public static double crossingViewingRange =7000*lengthScale;
	public static double crossingViewingAngle =  Math.toRadians(60);
	public static double crossingSensitivityForCollisions = 5*lengthScale;
	public static double crossingSafetyRadius=20*lengthScale;
	public static double crossingAlpha=1;
	public static String crossingCollisionAvoidanceAlgorithmSelection = "AVOAvoidanceAlgorithm";//"AVOAvoidanceAlgorithm","HRVOAvoidanceAlgorithm","RVOAvoidanceAlgorithm","ORCAAvoidanceAlgorithm","TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 
	public static String crossingSelfSeparationAlgorithmSelection = "SVOAvoidanceAlgorithm";//"AVOAvoidanceAlgorithm","HRVOAvoidanceAlgorithm","RVOAvoidanceAlgorithm","ORCAAvoidanceAlgorithm","TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 

	
	public static boolean tailApproachSelected = false;
	public static double tailApproachOffset = 20*lengthScale;
    public static boolean tailApproachIsRightSide =true;
	public static int tailApproachTimes=1;
	public static double tailApproachMaxSpeed =92.6*lengthScale;
	public static double tailApproachMinSpeed =51.4*lengthScale;
	public static double tailApproachMaxAcceleration = 10*lengthScale;
	public static double tailApproachMaxDeceleration = 10*lengthScale;
	public static double tailApproachMaxClimb = 17.78*lengthScale;
	public static double tailApproachMaxDescent = 20.23*lengthScale;
	public static double tailApproachMaxTurning = Math.toRadians(2.50);
	public static double tailApproachSpeed = 92.6*lengthScale;
	public static double tailApproachViewingRange =7000*lengthScale;
	public static double tailApproachViewingAngle =  Math.toRadians(60);
	public static double tailApproachSensitivityForCollisions = 5*lengthScale;
	public static double tailApproachSafetyRadius=20*lengthScale;
	public static double tailApproachAlpha=1;
	public static String tailApproachCollisionAvoidanceAlgorithmSelection = "AVOAvoidanceAlgorithm";//"AVOAvoidanceAlgorithm","HRVOAvoidanceAlgorithm","RVOAvoidanceAlgorithm","ORCAAvoidanceAlgorithm","TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 
	public static String tailApproachSelfSeparationAlgorithmSelection = "SVOAvoidanceAlgorithm";//"AVOAvoidanceAlgorithm","HRVOAvoidanceAlgorithm","RVOAvoidanceAlgorithm","ORCAAvoidanceAlgorithm","TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 

	
	
}
