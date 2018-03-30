package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Diagnostics {

	public SmartDashboard x;
	
	public Diagnostics() //the constructor -- Do not pass in the SmartDashboard from other classes
	{
		x = new SmartDashboard();
	}
//------------------------------------------------------------------------------------------------------------------------------------
//       --Call these methods to test various things--
	
	//Encoders
	public void Encoder(Encoder e, String name) //call this to output encoder data (distance)
	{
		String key = "Encoder Distance: ";
		SmartDashboard.putString(key, (e.getDistance() + ""));				
	}
	//-----------------------------------------------------------------------------------------
	//Servos
	public void Servo(Servo s, String name) //call to output servo data (angle)
	{
		String key = "Servo Angle: ";
		SmartDashboard.putString(key, (s.getAngle() + ""));				
	}
	//-----------------------------------------------------------------------------------------
	//Gyros
	public void Gyro(Gyro g, String name) //outputs gyro data (angle)
	{
		String key = "Gyro Angle: ";
		SmartDashboard.putString(key, (g.getAngle() + ""));				
	}
	//-----------------------------------------------------------------------------------------
	//Solenoids
	public void Solenoid(Solenoid s, String name) //outputs solenoid data (on or off)
	{
		String key = name + " Status: ";
		SmartDashboard.putString(key, (s.get() + ""));				
	}	
	//-----------------------------------------------------------------------------------------
	//Digital inputs
	public void DigitalInput(DigitalInput d, String name)  //outputs the value from the digital input channel (true or false)
	{
		String key = "DigitalInput Status: ";
		SmartDashboard.putString(key, (d.get() + ""));				
	}
	
}