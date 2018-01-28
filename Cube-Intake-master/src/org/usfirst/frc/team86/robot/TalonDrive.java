package org.usfirst.frc.team86.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class TalonDrive {
   
    public static final double SCALE_FACTOR = 1.0 / 600.0; // 1 / 100ms 
    													//relevance?
    private TalonSRX left1;
    private TalonSRX left2;
    private TalonSRX right1;
    private TalonSRX right2;
    
    private double pulsesPerRevolution;//r?
    private double maxMotorRPM;
    private double wheelCircumference;
    //used for
    private Gear gear;//?
    
    public TalonDrive(TalonSRX left1, TalonSRX left2, TalonSRX right1, TalonSRX right2) {
        this.left1 = left1;
        this.left2 = left2;
        this.right1 = right1;
        this.right2 = right2;
        
        left2.set(ControlMode.Follower, left1.getDeviceID());
        right2.set(ControlMode.Follower, right1.getDeviceID());
    
        left1.set(ControlMode.Velocity, 0);
        right1.set(ControlMode.Velocity, 0);
    }
    
    /**															???????????
     * Pass left and right throttle [-1.0, 1.0] will automatically calculate setpoint based on robot configuration.
     * @param left
     * @param right
     */
    public void drive(double left, double right) {
        double maxFloorSpeed = getMaxFloorSpeed();
        left1.set(ControlMode.Velocity, getSetpoint(left * maxFloorSpeed));
        right1.set(ControlMode.Velocity, getSetpoint(right * maxFloorSpeed));
    }
    
    /**
     * 
     * @param floorSpeed ft/s
     * @return units/100ms
     */
    private double getSetpoint(double floorSpeed) {
        double wheelRPM = floorSpeed * 60.0 * 12.0 / wheelCircumference;
        double motorRPM = wheelRPM * gear.getRatio();
        return motorRPM * pulsesPerRevolution * SCALE_FACTOR;
    }
    
    public double getMaxFloorSpeed() {
        return maxMotorRPM / Gear.HIGH.getRatio() * wheelCircumference / 60.0 / 12.0; 
    }
    
    public double getTicksPerRevolution() {
        return pulsesPerRevolution;
    }

    /**
     * CIMCoder 80 pulses
     * @param pulsesPerRevolution
     */
    public void setPulsesPerRevolution(double pulsesPerRevolution) {
        this.pulsesPerRevolution = pulsesPerRevolution;
    }

    public double getMaxMotorRPM() {
        return maxMotorRPM;
    }

    public void setMaxMotorRPM(double maxMotorRPM) {
        this.maxMotorRPM = maxMotorRPM;
    }
    
    public double getLowRatio() {
        return Gear.LOW.getRatio();
    }

    public void setLowRatio(double ratio) {
        Gear.LOW.setRatio(ratio);        
    }

    public double getHighRatio() {
        return Gear.HIGH.getRatio();
    }

    public void setHighRatio(double ratio) {
        Gear.HIGH.setRatio(ratio);
    }

    public double getWheelSize() {
        return wheelCircumference;
    }

    public void setWheelSize(double diameter) {
        this.wheelCircumference =  Math.PI * diameter;
    }
    
    public void setGear(Gear gear) {
        this.gear = gear;
        //TODO: Shift Gears
    }
    
    public Gear getGear() {
        return gear;
    }
    
    public enum Gear {
        LOW(1.0), HIGH(1.0);
        
        private double ratio;
        Gear(double ratio) {
            this.ratio = ratio;
        }
        
        public double getRatio() {
            return ratio;
        }
        
        public void setRatio(double ratio) {
            this.ratio = ratio;
        }
    }
}