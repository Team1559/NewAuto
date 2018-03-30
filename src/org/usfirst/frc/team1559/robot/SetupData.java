package org.usfirst.frc.team1559.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetupData {

	private int position;
	private NetworkTableEntry positionEntry;
	
	private String target;
	private NetworkTableEntry targetEntry;
	
	public SetupData() {
//		NetworkTableInstance inst = NetworkTableInstance.getDefault();
//		NetworkTable table = inst.getTable("SmartDashboard/Position");
//		positionEntry = table.getEntry("Position");
//		position = (int) positionEntry.getDouble(0);
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int number) {
//		positionEntry.setNumber(number);
		position = number;
	}
	
	public void setTarget(String n) {
		target = n;
	}
	
	public String getTarget() {
		return target;
	}
	
	public void updateData() {
		int pos = (int) SmartDashboard.getNumber("Position", 0);
		//positionEntry.setDouble(pos);
		position = pos;
		target = SmartDashboard.getString("Target", "switch");
	}
	
}
