package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Wiring;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;

public class Intake {

	private Solenoid solenoid;
	private Spark sparkLeft;
	private Spark sparkRight;
	private Spark sparkRotate;

	private boolean isGoingDown;
	private boolean activeRotate;

	public Intake() {
		solenoid = new Solenoid(Wiring.NTK_SOLENOID);
		sparkLeft = new Spark(Wiring.NTK_SPARK_LEFT);
		sparkRight = new Spark(Wiring.NTK_SPARK_RIGHT);
		sparkRotate = new Spark(Wiring.NTK_SPARK_ROTATE);
		isGoingDown = false;
		activeRotate = false;
	}

	public void open() {
		solenoid.set(true); //true
	}

	public void close() {
		solenoid.set(false); //false
	}

	public void toggle() {
		solenoid.set(!solenoid.get());
	}
	
	public void toggleRotate() {
		isGoingDown = !isGoingDown;
	}
	
	public void out(double speed) {
		sparkLeft.set(-speed);
		sparkRight.set(speed);
	}

	public void out() { 
		sparkLeft.set(-1.0);
		sparkRight.set(1.0);
	}

	public void in(double speed) {
		sparkLeft.set(speed);
		sparkRight.set(-speed);
	}
	
	public void in() {
		sparkLeft.set(1.0);
		sparkRight.set(-1.0);
	}

	public void rotateIntake() {
		sparkLeft.set(1.0);
		sparkRight.set(1.0);
	}

	public void stopIntake() {
		sparkLeft.set(0.0);
		sparkRight.set(0.0);
	}

	public void rotateDown() {
		isGoingDown = true;
	}

	public void rotateUp() {
		isGoingDown = false;
	}

	public void setActiveRotate(boolean b) {
		activeRotate = b;
	}

	public void updateRotate() {
		//FOR ROBOT 1//	
		//TODO possibly change this for robot 2, unsure of the wiring currently
		if (!isGoingDown) {
			if (activeRotate) {
				sparkRotate.set(1);
			} else {
				sparkRotate.set(0.6);
			}
		} else {
			if (activeRotate) {
				sparkRotate.set(-0.06); 
			} else {
				sparkRotate.set(-0.7); 
			}
		}
		
//		//ROBOT 2
//		if (!isGoingDown) {
//			if (activeRotate) {
//				sparkRotate.set(-0.8);//.55
//			} else {
//				sparkRotate.set(0);//.45 //-0.5
//			}
//		} else {
//			if (activeRotate) {
//				sparkRotate.set(0.5);
//			} else {
//				sparkRotate.set(0); //0.35
//				//sparkRotate.stopMotor(); // TODO: bottom limit switch is broken, replace when it's not
//			}
//		}
		

	}

	public void switchState() {
		isGoingDown = !isGoingDown;
	}

	public void rotateStop() {
		sparkRotate.set(0);
	}
	
	public boolean isGoingDown() {
		return isGoingDown;
	}
}
