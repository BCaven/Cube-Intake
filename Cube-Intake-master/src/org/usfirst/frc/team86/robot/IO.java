package org.usfirst.frc.team86.robot;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

public class IO {
// all need to be changed to channels #
public static Victor mot1 = new Victor(1);
public static Victor mot2 = new Victor(2);

public static DigitalInput banSensLEFT = new DigitalInput(1);
public static DigitalInput banSensRIGHT = new DigitalInput(0);	

public static Joystick leftStick = new Joystick(0);
public static Joystick rightStick = new Joystick(1);

public static Solenoid grab1 = new Solenoid(1,1);
public static Solenoid grab2 = new Solenoid(1,2);	//change channel #

public static Compressor compressor = new Compressor(1);
public static Relay compressorRelay = new Relay(0);



}
