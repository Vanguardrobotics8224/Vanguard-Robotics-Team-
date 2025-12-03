// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveModule extends SubsystemBase {

  private TalonFX m_driveMotor;
  private TalonFX m_turnMotor;


  /** Creates a new SwerveModule. */
  public SwerveModule(int driverMotorID, int turnMotorID) {
    m_driveMotor = new TalonFX(driverMotorID);
    m_turnMotor = new TalonFX(turnMotorID);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setDrivePower(double speed) {
    m_driveMotor.set(speed);
  }

  public void setTurnPower(double speed) {
    m_turnMotor.set(speed);
  }
}
