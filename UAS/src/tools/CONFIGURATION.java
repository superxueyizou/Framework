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
	public CONFIGURATION() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean avoidanceAlgorithmEnabler = true;
	public static boolean staticAvoidance = false;
	public static boolean dynamicAvoidance= !staticAvoidance;
	public static String encounterSelection = "HeadOnEncounter";// "HeadOnEncounter", "CrossingEncounter","TailApproachEncounter";
	public static String avoidanceAlgorithmSelection = "TurnRightAvoidanceAlgorithm"; //"TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 

	public static int sUASNo = 8;
	public static int sObstacleNo = 20;
	
	public static double selfMaxSpeed =2.235;
	public static double selfMaxAcceleration = 0.05;
	public static double selfMaxDeceleration = 0.05;
	public static double selfMaxTurning = 5;
	public static double selfSpeed = 1.5;
	public static double selfViewingRange =38.89;
	public static double selfViewingAngle = 60;
	public static double selfSensitivityForCollisions = 0.5;
	public static double selfSafetyRadius=1.667;
	
	
	public static boolean headOnSelected = true;
	public static boolean headOnIsRightSide = true;
	public static double headOnOffset=0;
	public static int headOnTimes=1;
	public static double headOnMaxSpeed =2.235;
	public static double headOnMaxAcceleration = 0.05;
	public static double headOnMaxDeceleration = 0.05;
	public static double headOnMaxTurning = 5;
	public static double headOnSpeed = 1.5;
	public static double headOnViewingRange =38.89;
	public static double headOnViewingAngle = 60;
	public static double headOnSensitivityForCollisions = 0.5;
	public static double headOnSafetyRadius=1.667;
	public static String headOnAvoidanceAlgorithmSelection = "None";//"TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 
	
	public static boolean crossingSelected = false;
	public static double crossingDistance=20;
	public static double crossingEncounterAngle=150;
	public static int crossingTimes=1;
	public static double crossingMaxSpeed =2.235;
	public static double crossingMaxAcceleration = 0.05;
	public static double crossingMaxDeceleration = 0.05;
	public static double crossingMaxTurning = 5;
	public static double crossingSpeed = 1.5;
	public static double crossingViewingRange =38.89;
	public static double crossingViewingAngle = 60;
	public static double crossingSensitivityForCollisions = 0.5;
	public static double crossingSafetyRadius=1.667;
	public static String crossingAvoidanceAlgorithmSelection = "None";//"TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 
	
	public static boolean tailApproachSelected = false;
	public static double tailApproachOffset = 2;
    public static boolean tailApproachIsRightSide =true;
	public static int tailApproachTimes=1;
	public static double tailApproachMaxSpeed =2.235;
	public static double tailApproachMaxAcceleration = 0.05;
	public static double tailApproachMaxDeceleration = 0.05;
	public static double tailApproachMaxTurning = 5;
	public static double tailApproachSpeed = 1.5;
	public static double tailApproachViewingRange =38.89;
	public static double tailApproachViewingAngle = 60;
	public static double tailApproachSensitivityForCollisions = 0.5;
	public static double tailApproachSafetyRadius=1.667;
	public static String tailApproachAvoidanceAlgorithmSelection = "None";//"TurnRightAvoidanceAlgorithm", "SmartTurnAvoidanceAlgorithm", "None", "RIPNAvoidanceAlgorithm" 
	
	
	
}
