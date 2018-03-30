package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface {

	private Joystick driverStick, copilotStick, cocopilotStick;
	private DTButton[] driverButtons, copilotButtons, cocopilotButtons;

	public OperatorInterface() {
		driverStick = new Joystick(Wiring.JOY_DRIVER);
		copilotStick = new Joystick(Wiring.JOY_COPILOT);
		cocopilotStick = new Joystick(Wiring.EXTRA_BUTTONS);
		driverButtons = new DTButton[14]; //increased from size 10 to 14 because of PS4 controller
		copilotButtons = new DTButton[14];
		cocopilotButtons = new DTButton[2];
		for (int i = 0; i < driverButtons.length; i++) {
			driverButtons[i] = new DTButton(driverStick, i + 1);
		}
		for (int i = 0; i < copilotButtons.length; i++) {
			copilotButtons[i] = new DTButton(copilotStick, i + 1);
		}
		
		for (int i = 0; i < cocopilotButtons.length; i++) {
			cocopilotButtons[i] = new DTButton(cocopilotStick, i + 1);
		}
	}

	public DTButton getDriverButton(int i) {
		return driverButtons[i];
	}

	public DTButton getCopilotButton(int i) {
		return copilotButtons[i];
	}
	
	public DTButton getCocopilotButton(int i) {
		return cocopilotButtons[i];
	}

	public double getDriverX() {
		return driverStick.getRawAxis(0);
	}

	public double getDriverY() {
		return driverStick.getRawAxis(1);
	}

	//XBOX//
	public double getDriverZ() {
		return driverStick.getRawAxis(4);
	}
	
	public double getPS4Z() {
		return driverStick.getRawAxis(2);
	}

	public double getCopilotX() {
		return copilotStick.getRawAxis(0);
	}

	public double getCopilotY() {
		return copilotStick.getRawAxis(1);
	}

	public double getCopilotZ() {
		return copilotStick.getRawAxis(4);
	}
	
	public double getDriverAxis(int x) {
		return driverStick.getRawAxis(x);
	}
	
	public double getCopilotAxis(int x) {
		return copilotStick.getRawAxis(x);
	}
	
	public int getDriverPOV() {
		return driverStick.getPOV();
	}
	
	public int getCopilotPOV() {
		return copilotStick.getPOV();
	}

	/**
	 * Calls the update methods for all of the driver and co-pilot buttons
	 * ({@link #driverButtons} and {@link #copilotButtons})
	 */
	public void update() {
		for (DTButton button : driverButtons) {
			button.update();
		}
		for (DTButton button : copilotButtons) {
			button.update();
		}
		for (DTButton button : cocopilotButtons) {
			button.update();
		}
	}
}
