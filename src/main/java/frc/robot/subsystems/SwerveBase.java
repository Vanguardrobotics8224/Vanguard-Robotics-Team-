// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

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
        Constants.CANConstants.kFrontRightTurnMotorID,
        Constants.CANConstants.kFrontRightEncoderID,
        0 // TODO: Determine correct angle offset
    );
    
    m_frontLeft = new SwerveModule(
        Constants.CANConstants.kFrontLeftDriveMotorID,
        Constants.CANConstants.kFrontLeftDriveTurnID,
        Constants.CANConstants.kFrontLeftEncoderID,
        90 // TODO: Determine correct angle offset
    );
    
    m_backLeft = new SwerveModule(
        Constants.CANConstants.kBackLeftDriveMotorID,
        Constants.CANConstants.kBackLeftTurnMotorID,
        Constants.CANConstants.kBackLeftEncoderID,
        180 // TODO: Determine correct angle offset
    );
    
    m_backRight = new SwerveModule(
        Constants.CANConstants.kBackRightDriveMotorID,
        Constants.CANConstants.kBackRightTurnMotorID,
        Constants.CANConstants.kBackRightEncoderID,
        270 // TODO: Determine correct angle offset
    );
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public static void drive(double x, double y, double rot, boolean isFieldOriented) {
    if (isFieldOriented) {
      // TODO: Implement F-O Swerve Drive
    } else {
      // TODO: Implement R-O Swerve Drive
    }
  }
}
