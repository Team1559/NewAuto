package org.usfirst.frc.team1559.robot;

public interface Wiring {

	// Drive Train
	public static final int DRV_FR_SRX = 11;
	public static final int DRV_RR_SRX = 10; // actual robot = 10, last year = 13
	public static final int DRV_RL_SRX = 12;
	public static final int DRV_FL_SRX = 13; // actual robot = 13, last year = 10

	// Lifter
	public static final int LIFT_POT = 0;
	public static final int LIFT_TALON = 1; 

	// Intake
	public static final int NTK_SPARK_LEFT = 1;
	public static final int NTK_SPARK_RIGHT = 0;
	public static final int NTK_SPARK_ROTATE = 2;
	public static final int NTK_SOLENOID = 7;

	// Climber
	public static final int CLM_BELT = 2; // Using TalonSRX speed controller.
	public static final int CLM_WINCH = 3; // Using TalonSR speed controller.

	// Controllers
	public static final int JOY_DRIVER = 0;
	public static final int JOY_COPILOT = 1;
	public static final int EXTRA_BUTTONS = 2;

	// Buttons
	public static final int BTN_CLIMB = 0;
	public static final int BTN_LIFT_SWITCH = 0;
	public static final int BTN_LIFT_GROUND = 0;
	public static final int BTN_LIFT_SCALE_POS_ONE = 0;
	public static final int BTN_LIFT_SCALE_POS_TWO = 0;
	public static final int BTN_LIFT_SCALE_POS_THREE = 0;
	public static final int BTN_GRAB = 0;

}
