package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.Wiring;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber {
	private static final int TIMEOUT = 0;
	private Talon winch;
	private WPI_TalonSRX belt;
	 // TODO figure that out for robot 1
	public int upperBound = 269; //359 for robot 2//pot value decrease as it moves up
	public int lowerBound = 815; //905 for robot 2
	
	private double kP = 10;
	private double kI = 0;
	private double kD = 0;
	private double kF = 0;

	public Climber() {
		winch = new Talon(Wiring.CLM_WINCH);
		belt = new WPI_TalonSRX(Wiring.CLM_BELT);
		belt.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);
		belt.enableCurrentLimit(false);

		belt.configNominalOutputForward(+0, TIMEOUT);
		belt.configNominalOutputReverse(-0, TIMEOUT);
		belt.configPeakOutputForward(+1, TIMEOUT);
		belt.configPeakOutputReverse(-1, TIMEOUT);

		belt.configReverseSoftLimitThreshold(lowerBound, TIMEOUT);
		belt.configForwardSoftLimitThreshold(upperBound, TIMEOUT);
		belt.configForwardSoftLimitEnable(false, TIMEOUT);
		belt.configReverseSoftLimitEnable(false, TIMEOUT);

		belt.config_kP(0, kP, TIMEOUT);
		belt.config_kI(0, kI, TIMEOUT);
		belt.config_kD(0, kD, TIMEOUT);
		belt.config_kF(0, kF, TIMEOUT);

		belt.setSensorPhase(true);
		belt.setInverted(true);
		belt.setNeutralMode(NeutralMode.Brake);

		belt.enableVoltageCompensation(false);
		
		//for robot 2
		if(!Robot.robotOne) {
			upperBound = 359;
			lowerBound = 905;
		}
	}

	public double getPot() {
		return belt.getSelectedSensorPosition(0);
	}

	public void driveManual(double val) {
		belt.set(ControlMode.PercentOutput, val);
	}

	public void stageOne(double manual) {
		if (Math.abs(manual) > 0.1) {
			if (manual > 0 && getPot() > upperBound) { // going up
				belt.set(ControlMode.PercentOutput, manual * 1.33);
			} else { // going down
				belt.set(ControlMode.PercentOutput, manual/1.5);
			}
		}
		else { //holding voltage
			belt.set(ControlMode.PercentOutput, -0.1);
		}
	}

	public void stageTwo(boolean b) {
		winch.set(b ? 1 : 0);
		//belt.set(ControlMode.Current, -40);
	}
	
	public void winchDown(boolean b) {
		winch.set(b ? -0.5 : 0);
	}
	
	public void stopClimbing() {
		winch.stopMotor();
	}
	
	public void stopBelting() {
		belt.stopMotor();
	}

	public void disable() {
		winch.stopMotor();
		belt.stopMotor();
	}

	public void setBeltMotor(double val) {
		belt.set(ControlMode.PercentOutput, val);
	}

	public void setWinchMotor(double val) {
		winch.set(val);
	}
}
