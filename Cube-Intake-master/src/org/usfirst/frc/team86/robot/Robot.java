package org.usfirst.frc.team86.robot;

import org.usfirst.frc.team86.robot.TalonDrive.Gear;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

    private TalonSRX left1;
    private TalonSRX left2;
    private TalonSRX right1;
    private TalonSRX right2;
    private CubeGrabber CubeGrabber;
    
    private TalonDrive driveTrain;
    
    private Joystick left;
    private Joystick right;
    
    private PowerDistributionPanel pdp = new PowerDistributionPanel(0);
    
    @Override
    public void robotInit() {
    	
    	CubeGrabber = new CubeGrabber(IO.mot1,IO.mot2,IO.grab1,IO.grab2,IO.banSens);
    	
        left1 = new TalonSRX(57);
        left1.setInverted(true);
        left1.setSensorPhase(false); //?
        left2 = new TalonSRX(59);
        left2.setInverted(true);
        left2.setSensorPhase(false);
        right1 = new TalonSRX(58);
        right1.setSensorPhase(false);
        right2 = new TalonSRX(56);
        right2.setSensorPhase(false);
        
        left1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0); //2nd 0, pidIdx - 0 for Primary closed-loop. 1 for cascaded closed-loop. ?
        left2.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        right1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);  
        right2.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        
        driveTrain = new TalonDrive(left1, left2, right1, right2);
        driveTrain.setMaxMotorRPM(5100);
        driveTrain.setWheelSize(4.0);
        driveTrain.setLowRatio(8.45);  //?? point of gear and 2 speeds? number relevance
        driveTrain.setHighRatio(8.45);
        driveTrain.setGear(Gear.LOW); //???
        driveTrain.setPulsesPerRevolution(80); // what do pulses do
        
        left = new Joystick(0);
        right = new Joystick(1);
    }
    
	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {
		}
	
	@Override
    public void teleopPeriodic() {
		IO.compressorRelay.set(IO.compressor.enabled() ? Relay.Value.kForward : Relay.Value.kOff);  //??
        driveTrain.drive(left.getY(), right.getY());
        
        SmartDashboard.putNumber("Left", left.getY() * driveTrain.getMaxFloorSpeed()); //math for getMaxFloorSpeed
        SmartDashboard.putNumber("Right", right.getY() * driveTrain.getMaxFloorSpeed());
        
        SmartDashboard.putNumber("left1", pdp.getCurrent(1));
        SmartDashboard.putNumber("left2", pdp.getCurrent(2));
        SmartDashboard.putNumber("right1", pdp.getCurrent(12));
        SmartDashboard.putNumber("right2", pdp.getCurrent(13));
        
        SmartDashboard.putNumber("left1 velocity", left1.getSelectedSensorVelocity(0) / TalonDrive.SCALE_FACTOR);  //scale factor meaning
        SmartDashboard.putNumber("left2 velocity", left2.getSelectedSensorVelocity(0) / TalonDrive.SCALE_FACTOR);
        SmartDashboard.putNumber("right1 velocity", right1.getSelectedSensorVelocity(0) / TalonDrive.SCALE_FACTOR);
        SmartDashboard.putNumber("right2 velocity", right2.getSelectedSensorVelocity(0) / TalonDrive.SCALE_FACTOR);
    }
}