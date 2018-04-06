/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

import org.usfirst.frc.team1559.robot.subsystems.Climber;
import org.usfirst.frc.team1559.robot.subsystems.Intake;
import org.usfirst.frc.team1559.robot.subsystems.Lifter;
import org.usfirst.frc.team1559.util.BNO055;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Robot extends IterativeRobot {

	public static PowerDistributionPanel pdp;

	public static OperatorInterface oi;
	//public static DriveTrain driveTrain;
	public static BNO055 imu;
	private static final int TIMEOUT = 0;
	private String gameData;
	private CommandGroup routine;
	public static UDPClient udp;
	public WPI_TalonSRX frontLeft, frontRight, rearLeft, rearRight;
	private double prev, FL_dist, FR_dist, RR_dist, RL_dist;
	boolean countNow;
	private double  lastDistanceFR, lastDistanceFL, lastDistanceRR, lastDistanceRL, lastAngle;
	//public static VisionData visionData;
	//public static Lifter lifter;
	//public static Climber climber;
	//public static Intake intake;
	private static int autoIndex;
	private static double num, desiredHeading;
	private double zRotation;
	private static MecanumDrive drive;
	private SetupData setupData;
	private Diagnostics da;
	private BufferedReader buffRead;
	private double p, d, i, f;
	public boolean xbox = false;
	public boolean scott = false; //for axis 4 manual toggle
	private int ccCount, lastTime;
	private ArrayList<Double> right_velocity, left_velocity, heading;
	private String csvFile;
	public final static boolean robotOne = false;

	@Override
	public void robotInit() {
		right_velocity = new ArrayList<Double>();
		left_velocity = new ArrayList<Double>();
		heading = new ArrayList<Double>();
		
		csvFile = "";
		
		buffRead = null;
		ccCount = 0;
		lastTime = 0;
		lastDistanceFR = 0.0;
		lastDistanceFL = 0.0;
		lastDistanceRR = 0.0;
		lastDistanceRL = 0.0;
		lastAngle = 0.0;
		countNow = false;
		FL_dist = 0;
		FR_dist = 0;
		RL_dist = 0;
		RR_dist = 0;
		prev = 0;
		da = new Diagnostics();
		autoIndex = 0;
		zRotation = 0;
		num = 0; //TODO: find val
		desiredHeading = 0; //TODO: find val
		
		// input
		oi = new OperatorInterface();
		imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS, BNO055.vector_type_t.VECTOR_EULER);
		udp = new UDPClient(); // for jetson communications
		//visionData = new VisionData();
		frontLeft = new WPI_TalonSRX(Wiring.DRV_FL_SRX);
		frontRight = new WPI_TalonSRX(Wiring.DRV_FR_SRX);
		rearLeft = new WPI_TalonSRX(Wiring.DRV_RL_SRX);
		rearRight = new WPI_TalonSRX(Wiring.DRV_RR_SRX);
		
		frontLeft.setNeutralMode(NeutralMode.Brake);
		frontRight.setNeutralMode(NeutralMode.Brake);
		rearLeft.setNeutralMode(NeutralMode.Brake);
		rearRight.setNeutralMode(NeutralMode.Brake);
		
		frontLeft.set(ControlMode.Velocity, 0);
		frontRight.set(ControlMode.Velocity, 0);
		rearLeft.set(ControlMode.Velocity, 0);
		rearRight.set(ControlMode.Velocity, 0);
		p = 2.66;
		i = 0;
		d = 12.5;
		f = 0.4;
		frontLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
		rearLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
		frontRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
		rearRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
		// subsystems
		//driveTrain = new DriveTrain(false);
		drive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);
		
		//lifter = new Lifter();
		//climber = new Climber();
		//intake = new Intake();

		// autonomous
		routine = new CommandGroup();
		//AutoPicker.init();
		setupData = new SetupData();

	}

	@Override
	public void robotPeriodic() {
		setupData.updateData();
		
		//TODO remove this
		//udp.send("c");

		//SmartDashboard.putNumber("Motor 0 Current", driveTrain.motors[0].getOutputCurrent());
		
		//TODO use these to test
		//SmartDashboard.putNumber("Lifter Pot", lifter.getPot());
		//SmartDashboard.putNumber("Lifter Current", lifter.getMotor().getOutputCurrent());
		//SmartDashboard.putNumber("Lifter Percent", lifter.getMotor().getMotorOutputPercent());
		//SmartDashboard.putNumber("Climber Pot", climber.getPot());

		SmartDashboard.putNumber("IMU", imu.getHeadingRelative());

//		SmartDashboard.putNumber("Motor 0 CL Error", driveTrain.getMotors()[0].getClosedLoopError(0));
//		SmartDashboard.putNumber("Motor 1 CL Error", driveTrain.getMotors()[1].getClosedLoopError(0));
//		SmartDashboard.putNumber("Motor 2 CL Error", driveTrain.getMotors()[2].getClosedLoopError(0));
//		SmartDashboard.putNumber("Motor 3 CL Error", driveTrain.getMotors()[3].getClosedLoopError(0));
//		SmartDashboard.putNumber("Motor 0 CL Target", driveTrain.getMotors()[0].getClosedLoopTarget(0));
//		SmartDashboard.putNumber("Motor 1 CL Target", driveTrain.getMotors()[1].getClosedLoopTarget(0));
//		List<Integer> errors = MathUtils.map((x) -> Math.abs(((WPI_TalonSRX) x).getClosedLoopError(0)),
//				Robot.driveTrain.motors);
//		SmartDashboard.putNumber("Average Motor CL Error", MathUtils.average(errors));
	}

	@Override
	public void autonomousInit() {
		// 
		setAutoArrays(heading, "/media/sda1/testNewAuto/csvTest.csv");
		setAutoArrays(left_velocity, "/media/sda1/testNewAuto/csvTest1.csv");
		setAutoArrays(right_velocity, "/media/sda1/testNewAuto/csvTest2.csv");

		imu.zeroHeading();
		autoIndex = 0;
		ccCount = 0;
		subRoutine(frontLeft);
		subRoutine(frontRight);
		subRoutine(rearLeft);
		subRoutine(rearRight);
		FL_dist = 0;
		FR_dist = 0;
		RL_dist = 0;
		RR_dist = 0;
		lastTime = 0;
		lastDistanceFR = 0.0;
		lastDistanceRR = 0.0;
		lastDistanceFL = 0.0;
		lastDistanceRL = 0.0;
		lastAngle = 0.0;
		countNow = false;
		/*
		imu.zeroHeading();
		setupData.updateData();
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		System.out.println("POSITION IS " + setupData.getPosition());

		// TODO Fix target
		SmartDashboard.putString("target returned is:", setupData.getTarget());
		routine = AutoPicker.pick(gameData, (int) setupData.getPosition(), "both");
		
		//routine = new CommandGroup();
		
		routine.start();
		*/
		
	}

	@Override
	public void autonomousPeriodic() {
		
		//driveTrain.setPIDF(0, 0, 0, 0.4269);
		//Scheduler.getInstance().run();
		//intake.updateRotate();
		//lifter.update();
		
		//udp.send("c");

		//SmartDashboard.putNumber("rpm", driveTrain.getAverageRPM());
		
		//SmartDashboard.putNumber("Motor 0 error: ",driveTrain.motors[0].getClosedLoopError(0));
		// SmartDashboard.putNumber("Motor 1 error: ",
		// driveTrain.motors[1].getClosedLoopError(0));
		// SmartDashboard.putNumber("Motor 2 error: ",
		// driveTrain.motors[2].getClosedLoopError(0));
		// SmartDashboard.putNumber("Motor 3 error: ",
		// driveTrain.motors[3].getClosedLoopError(0));
		// SmartDashboard.putNumber("Motor 0 value: ",
		// driveTrain.motors[0].getMotorOutputVoltage());
		/*
			double y = (AutoConstants.left_velocity[autoIndex] + AutoConstants.right_velocity[autoIndex])/6.5;
			double r = AutoConstants.global_heading[autoIndex] - imu.getHeadingRelative();
			driveTrain.drive(0, y, r);
		if(autoIndex < 412) {
			autoIndex++;
		}
		
		*/
		//FL_dist = Constants.INCH_PER_TICK*frontLeft.getSelectedSensorPosition(0);
		//FR_dist = Constants.INCH_PER_TICK*frontRight.getSelectedSensorPosition(0);
		//RL_dist = Constants.INCH_PER_TICK*rearLeft.getSelectedSensorPosition(0);
		//RR_dist = Constants.INCH_PER_TICK*rearRight.getSelectedSensorPosition(0);
		desiredHeading = heading.get(autoIndex);
		//desiredHeading = 0.0;
		//double conversionFactor = 156.0494058201553;
		zRotation = 0.02*(desiredHeading*(180.0/Math.PI) - imu.getHeadingRelative()); //TODO: Find desiredHeading and num.
		//zRotation =0;
		//SmartDashboard.putNumber("Velocity:", frontRight.getSelectedSensorVelocity(0));
		//SmartDashboard.putNumber("IMU", imu.getHeading());
		//SmartDashboard.putNumber("Heading Error", (desiredHeading*(180.0/Math.PI) - imu.getHeading()));
		//System.out.println(imu.getHeadingRelative());
		//SmartDashboard.putNumber("zRotation", zRotation);
		//System.out.println(lastAngle + "      " + imu.getHeading() + "       "  + lastTime);
		System.out.println("autoIndex: " + autoIndex);
		if(autoIndex < heading.size()) {
			drive.driveCartesian(0, ((left_velocity.get(autoIndex) + right_velocity.get(autoIndex))/15), zRotation);
			
			//Just go in some circles.
			//drive.driveCartesian(0, 0, zRotation);
		}
		/* FOR TUNING THE AUTO SEQUENCE
		 * drive.driveCartesian(0, 0, 1);
		 */
		
		/*
		 * SmartDashboard.putNumber("Difference", imu.getHeading() - prev);
		SmartDashboard.putNumber("FrontLeft Velocity", FL_dist);
		SmartDashboard.putNumber("FrontRight Velocity", FR_dist);
		SmartDashboard.putNumber("rearLeft Velocity", RL_dist);
		SmartDashboard.putNumber("rearRight Velocity", RR_dist);
		 */
		
		prev = imu.getHeading();
		/*
		if((1 < imu.getHeading() % 360) && (imu.getHeading() % 360 < 10) && (countNow)) {
			System.out.println(autoIndex);
			autoIndex++;
			countNow = false;
		}
		if(imu.getHeading() > 180) {
			countNow = true;
		}
		*/
		/*
		if(imu.getHeading() / 360.0 >= 10 && !countNow) {
			lastDistanceRR = RR_dist;
			lastDistanceFL = FL_dist;
			lastDistanceFR = FR_dist;
			lastDistanceRL = RL_dist;
			
			SmartDashboard.putNumber("FrontLeft DIST", lastDistanceFL);
			SmartDashboard.putNumber("FrontRight DIST", lastDistanceFR);
			SmartDashboard.putNumber("rearLeft DIST", lastDistanceRR);
			SmartDashboard.putNumber("rearRight DIST", lastDistanceRR);
			//System.out.println(lastDistanceFR + "		" + lastDistanceFL + "		" + lastDistanceRR + "		" + lastDistanceRL);
			lastTime = ccCount;
			lastAngle = imu.getHeading();
			//drive.driveCartesian(0, 0, 0);
			countNow = true;
		}
		ccCount++;
		*/
		autoIndex++;
	}

	@Override
	public void disabledInit() {
	}

	@Override
	public void teleopInit() {
		//driveTrain.shift(true);
		//lifter.holdPosition();
	}

	@Override
	public void teleopPeriodic() {
		SmartDashboard.putNumber("FrontLeft DIST", lastDistanceFL);
		SmartDashboard.putNumber("FrontRight DIST", lastDistanceFR);
		SmartDashboard.putNumber("rearLeft DIST", lastDistanceRL);
		SmartDashboard.putNumber("rearRight DIST", lastDistanceRR);
		//SmartDashboard.putNumber("Encoder FrontLeft", driveTrain.getMotors()[DriveTrain.FL].getSelectedSensorPosition(0));
		//SmartDashboard.putNumber("Encoder FrontRight", driveTrain.getMotors()[DriveTrain.FR].getSelectedSensorPosition(0));
		//SmartDashboard.putNumber("Encoder RearLeft", driveTrain.getMotors()[DriveTrain.RL].getSelectedSensorPosition(0));
		//SmartDashboard.putNumber("Encoder RearRight", driveTrain.getMotors()[DriveTrain.RR].getSelectedSensorPosition(0));
		//driveTrain.drive(oi.getDriverX(), oi.getDriverY(), oi.getDriverZ());
		SmartDashboard.putNumber("IMU", imu.getHeading());
		drive.driveCartesian((-oi.getDriverX()*Math.abs(-oi.getDriverX())), (-oi.getDriverY()*Math.abs(-oi.getDriverY())), (Math.abs(oi.getDriverZ())*oi.getDriverZ()));
	}

	@Override
	public void disabledPeriodic() {

	}

	@Override
	public void testInit() {

	}

	@Override
	public void testPeriodic() {
		
	}
	
	public void subRoutine(WPI_TalonSRX talon) {
		talon.config_kP(0, p, TIMEOUT);
		talon.config_kI(0, i, TIMEOUT);
		talon.config_kD(0, d, TIMEOUT);
		talon.config_kF(0, f, TIMEOUT);
	}
	public void setAutoArrays(ArrayList<Double> a, String location) {
		a.clear();
		csvFile = location;
		String line = "";
			try (BufferedReader buffRead = new BufferedReader(new FileReader(csvFile))) {
				while((line = buffRead.readLine()) != null) {
					//System.out.println(line);
					a.add(Double.parseDouble(line));
					line = "";
				}
			} catch(IOException e) {
				e.printStackTrace();
		}	
	}
}
