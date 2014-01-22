/**
 * 
 */
package tools;

/**
 * @author Xueyi
 *
 */
public class CONFIGURATION {

	/**
	 * 
	 */
	public CONFIGURATION() 
	{
		// TODO Auto-generated constructor stub
	}
	
	
	public static boolean collisionAvoidanceEnabler=true;
	public static boolean selfSeparationEnabler=true;
	
	public static double selfDestDist = 50;
	public static double selfDestAngle = Math.toRadians(30);	
	
	public static double selfMaxSpeed =2.235;
	public static double selfMaxAcceleration = 1.0;
	public static double selfMaxDeceleration = 1.0;
	public static double selfMaxTurning = Math.toRadians(5.0);
	public static double selfSpeed = 1.5;
	public static double selfViewingRange =38.89;
	public static double selfViewingAngle = Math.toRadians(60);
	public static double selfSensitivityForCollisions = 0.5;
	public static double selfSafetyRadius=1.667;
	public static double selfAlpha=1; //RVO[0,1.0], HRVO(1.0,2.0], ORCA(2.0,3.0]
	public static String selfCollisionAvoidanceAlgorithmSelection = "AVOAvoidanceAlgorithm"; //"AVOAvoidanceAlgorithm","HRVOAvoidanceAlgorithm","RVOAvoidanceAlgorithm","ORCAAvoidanceAlgorithm","TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 
	public static String selfSelfSeparationAlgorithmSelection = "SVOAvoidanceAlgorithm"; //"AVOAvoidanceAlgorithm","HRVOAvoidanceAlgorithm","RVOAvoidanceAlgorithm","ORCAAvoidanceAlgorithm","TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 

	
	
	public static boolean headOnSelected = true;
	public static boolean headOnIsRightSide = false ;
	public static double headOnOffset= 0;
	public static int headOnTimes=1;
	public static double headOnMaxSpeed =2.235;
	public static double headOnMaxAcceleration = 1.0;
	public static double headOnMaxDeceleration = 1.0;
	public static double headOnMaxTurning = Math.toRadians(5.0);
	public static double headOnSpeed = 1.5;
	public static double headOnViewingRange =38.89;
	public static double headOnViewingAngle =  Math.toRadians(60);
	public static double headOnSensitivityForCollisions = 0.5;
	public static double headOnSafetyRadius=1.667;
	public static double headOnAlpha=1;
	public static String headOnCollisionAvoidanceAlgorithmSelection = "AVOAvoidanceAlgorithm";//"AVOAvoidanceAlgorithm","HRVOAvoidanceAlgorithm","RVOAvoidanceAlgorithm","ORCAAvoidanceAlgorithm","TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 
	public static String headOnSelfSeparationAlgorithmSelection = "SVOAvoidanceAlgorithm";//"AVOAvoidanceAlgorithm","HRVOAvoidanceAlgorithm","RVOAvoidanceAlgorithm","ORCAAvoidanceAlgorithm","TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 

	
	public static boolean crossingSelected = false;
	public static double crossingEncounterAngle= Math.toRadians(30);
	public static boolean crossingIsRightSide=true;
	public static int crossingTimes=1;
	public static double crossingMaxSpeed =2.235;
	public static double crossingMaxAcceleration = 1.0;
	public static double crossingMaxDeceleration = 1.0;
	public static double crossingMaxTurning = Math.toRadians(5.0);
	public static double crossingSpeed = 1.5;
	public static double crossingViewingRange =38.89;
	public static double crossingViewingAngle =  Math.toRadians(60);
	public static double crossingSensitivityForCollisions = 0.5;
	public static double crossingSafetyRadius=1.667;
	public static double crossingAlpha=1;
	public static String crossingCollisionAvoidanceAlgorithmSelection = "AVOAvoidanceAlgorithm";//"AVOAvoidanceAlgorithm","HRVOAvoidanceAlgorithm","RVOAvoidanceAlgorithm","ORCAAvoidanceAlgorithm","TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 
	public static String crossingSelfSeparationAlgorithmSelection = "SVOAvoidanceAlgorithm";//"AVOAvoidanceAlgorithm","HRVOAvoidanceAlgorithm","RVOAvoidanceAlgorithm","ORCAAvoidanceAlgorithm","TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 

	
	public static boolean tailApproachSelected = false;
	public static double tailApproachOffset = 2;
    public static boolean tailApproachIsRightSide =true;
	public static int tailApproachTimes=1;
	public static double tailApproachMaxSpeed =2.235;
	public static double tailApproachMaxAcceleration = 1.0;
	public static double tailApproachMaxDeceleration = 1.0;
	public static double tailApproachMaxTurning = Math.toRadians(5.0);
	public static double tailApproachSpeed = 1.0;
	public static double tailApproachViewingRange =38.89;
	public static double tailApproachViewingAngle =  Math.toRadians(60);
	public static double tailApproachSensitivityForCollisions = 0.5;
	public static double tailApproachSafetyRadius=1.667;
	public static double tailApproachAlpha=1;
	public static String tailApproachCollisionAvoidanceAlgorithmSelection = "AVOAvoidanceAlgorithm";//"AVOAvoidanceAlgorithm","HRVOAvoidanceAlgorithm","RVOAvoidanceAlgorithm","ORCAAvoidanceAlgorithm","TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 
	public static String tailApproachSelfSeparationAlgorithmSelection = "SVOAvoidanceAlgorithm";//"AVOAvoidanceAlgorithm","HRVOAvoidanceAlgorithm","RVOAvoidanceAlgorithm","ORCAAvoidanceAlgorithm","TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 

	
	
}
