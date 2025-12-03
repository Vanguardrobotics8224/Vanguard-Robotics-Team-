// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SwerveBase extends SubsystemBase {

  public SwerveModule m_frontRight;
  public SwerveModule m_frontLeft;
  public SwerveModule m_backLeft;
  public SwerveModule m_backRight;

  /** Creates a new SwerveBase. */
  public SwerveBase() {
    m_frontRight = new SwerveModule(
      Constants.CANConstants.kFrontRightDriveMotorID,
      Constants.CANConstants.kFrontRightTurnMotorID
    );

    m_frontLeft = new SwerveModule(
      Constants.CANConstants.kFrontLeftDriveMotorID,
      Constants.CANConstants.kFrontLeftTurnMotorID
    );

    m_backLeft = new SwerveModule(
      Constants.CANConstants.kBackLeftDriveMotorID,
      Constants.CANConstants.kBackLeftTurnMotorID
    );

    m_backRight = new SwerveModule(
      Constants.CANConstants.kBackRightDriveMotorID,
      Constants.CANConstants.kBackRightTurnMotorID
    );
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public Command frontRightCommand(double driveSpeed, double turnSpeed) {
    // Inline construction of command goes here.
    // Subsystem::run implicitly requires `this` subsystem.
    return run(
        () -> {
          /* one-time action goes here */
          m_frontRight.setDrivePower(driveSpeed);
          m_frontRight.setTurnPower(turnSpeed);
        });
  }

  public Command frontLeftCommand(double driveSpeed, double turnSpeed) {
    // Inline construction of command goes here.
    // Subsystem::run implicitly requires `this` subsystem.
    return run(
        () -> {
          /* one-time action goes here */
          m_frontLeft.setDrivePower(driveSpeed);
          m_frontLeft.setTurnPower(turnSpeed);
        });
  }

  public Command backLeftCommand(double driveSpeed, double turnSpeed) {
    // Inline construction of command goes here.
    // Subsystem::run implicitly requires `this` subsystem.
    return run(
        () -> {
          /* one-time action goes here */
          m_backLeft.setDrivePower(driveSpeed);
          m_backLeft.setTurnPower(turnSpeed);
        });
  }

  public Command backRightCommand(double driveSpeed, double turnSpeed) {
    // Inline construction of command goes here.
    // Subsystem::run implicitly requires `this` subsystem.
    return run(
        () -> {
          /* one-time action goes here */
          m_backRight.setDrivePower(driveSpeed);
          m_backRight.setTurnPower(turnSpeed);
        });
  }

  public Command stopCommand() {
    // Inline construction of command goes here.
    // Subsystem::run implicitly requires `this` subsystem.
    return run(
        () -> {
          /* one-time action goes here */
          m_frontRight.setDrivePower(0);
          m_frontRight.setTurnPower(0);
          m_frontLeft.setDrivePower(0);
          m_frontLeft.setTurnPower(0);
          m_backLeft.setDrivePower(0);
          m_backLeft.setTurnPower(0);
          m_backRight.setDrivePower(0);
          m_backRight.setTurnPower(0);
        });
  }
}
