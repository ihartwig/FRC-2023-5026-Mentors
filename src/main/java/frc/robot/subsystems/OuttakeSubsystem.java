// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import frc.robot.Constants.Outtake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class OuttakeSubsystem extends SubsystemBase {
  /** The modes of the drivebase subsystem */
  public enum Modes{
    IDLE,
    GRAB,
    HOLD,
    RELEASE
}

private Modes mode = Modes.IDLE;
private final TalonFX outtake;


  public OuttakeSubsystem() {
    this.outtake = new TalonFX(Outtake.Ports.OUTTAKE_MOTOR);
  }

  private boolean modeLocked = false;

private double timeOfModeTransition = Timer.getFPGATimestamp();
  /**
   * gets the current mode of the outtake subsystem state machine
   *
   * @return the current mode
   */
  public Modes getMode() {
    return mode;
  }

  public boolean getModeLocked(){
    return modeLocked;
  }

  public void setIdleMode() {
    mode = Modes.IDLE;
  }

  public void setGrabMode() {
    mode = Modes.GRAB;
  }

  public void setHoldMode() {
    mode = Modes.HOLD;
  }

  public void setReleaseMode() {
    mode = Modes.RELEASE;
  }


  public double timeSinceModeTransition() {
    return Timer.getFPGATimestamp() - timeOfModeTransition;
  }

  public void setModeIfTimeSinceTransitionGreaterThan(double duration, Modes mode) {
    if (timeSinceModeTransition() >= duration) {
      setMode(mode);
    }
  }


  /**
   * Updates the robot pose estimation for newly written module states. Should be called on every
   * periodic
   */
  private void odometryPeriodic() {
    
  }

  public void nextModePeriodic(){
    if(modeLocked) return;
    
    switch(mode){
        case IDLE:
        case HOLD:
          break;
        case GRAB:
          setMode(Modes.HOLD);
          break;
        case RELEASE:
          setMode(Modes.IDLE);
          break;
    }
  }

  public void setMode(Modes mode){
    timeOfModeTransition = Timer.getFPGATimestamp();
    this.mode = mode;
    modeLocked = false;
  }

  public void setLockMode(Modes mode){
    setMode(mode);
    modeLocked = true;
  }

  public void unlockMode(){
    modeLocked = false;
  }

  public void idlePeriodic(){

  }

  public void grabPeriodic(){

  }

  public void holdPeriodic(){

  }

  public void releasePeriodic(){

  }

  @Override
  public void periodic() {
    switch (mode) {
      case IDLE:
        idlePeriodic();
        break;
      case GRAB:
        grabPeriodic();
        break;
      case HOLD:
        holdPeriodic();
        break;
      case RELEASE:
        grabPeriodic();
        break;
    }
  }
}