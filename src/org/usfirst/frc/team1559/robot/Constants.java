package org.usfirst.frc.team1559.robot;

public interface Constants {

	public final double WHEEL_RADIUS_INCHES_MECANUM = 3;
	public final double WHEEL_RADIUS_INCHES_TRACTION = 3.125;
	public final double DT_SPROCKET_RATIO = 32.0 / 22.0;
	public final double WHEEL_FUDGE_MECANUM = 0.723;
	public final double WHEEL_FUDGE_TRACTION = 1;//0.6
	
	public final double CLM_WINCH_SPEED = 0.75; //TODO: Find actual value.
	public final int CLM_UPPER_BOUND = 0; 
	public final int CLM_LOWER_BOUND = 0;
	public final int TICKS_PER_REV = 4096;
	public final double INCH_PER_TICK = (2.0*Math.PI)/TICKS_PER_REV;
}
