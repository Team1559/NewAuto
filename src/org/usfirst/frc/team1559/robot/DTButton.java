package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Joystick;

public class DTButton {

	private boolean old, current;
	private Joystick stick;
	private int button;

	public DTButton(Joystick stick, int button) {
		this.stick = stick;
		this.button = button;
	}

	public void update() {
		update(stick.getRawButton(button));
	}

	/**
	 * Sets {@link #old} to {@link #current}, and then sets {@link #current} to the
	 * given value
	 * 
	 * @param b
	 *            The value to set {@link #current} to
	 */
	public void update(boolean b) {
		old = current;
		current = b;
	}

	/**
	 * @return Rising edge of a button press.
	 */
	public boolean isPressed() {
		return current && !old;
	}

	/**
	 * @return If button is being held down.
	 */
	public boolean isDown() {
		return current;
	}

	/**
	 * @return Falling edge of button press.
	 */
	public boolean isReleased() {
		return old && !current;
	}
}
