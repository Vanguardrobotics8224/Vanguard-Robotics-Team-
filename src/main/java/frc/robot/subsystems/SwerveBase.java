// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SwerveBase extends SubsystemBase {

  public Pigeon2 m_gyro;
  public SwerveModule m_frontRight;
  public SwerveModule m_frontLeft;
  public SwerveModule m_backLeft;
  public SwerveModule m_backRight;

  /** Creates a new SwerveBase. */
  public SwerveBase() {
    m_gyro = new Pigeon2(Constants.CANConstants.kPigeonID);

    m_frontRight = new SwerveModule(
        Constants.CANConstants.kFrontRightDriveMotorID,
        Constants.CANConstants.kFrontRightTurnMotorID,
        Constants.CANConstants.kFrontRightEncoderID,
        0
    );
    
    m_frontLeft = new SwerveModule(
        Constants.CANConstants.kFrontLeftDriveMotorID,
        Constants.CANConstants.kFrontLeftDriveTurnID,
        Constants.CANConstants.kFrontLeftEncoderID,
        1
    );
    
    m_backLeft = new SwerveModule(
        Constants.CANConstants.kBackLeftDriveMotorID,
        Constants.CANConstants.kBackLeftTurnMotorID,
        Constants.CANConstants.kBackLeftEncoderID,
        2
    );
    
    m_backRight = new SwerveModule(
        Constants.CANConstants.kBackRightDriveMotorID,
        Constants.CANConstants.kBackRightTurnMotorID,
        Constants.CANConstants.kBackRightEncoderID,
        3
    );
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void drive(double x, double y, double rot, boolean isFieldOriented) {
    if (isFieldOriented) {
      // TODO: Implement F-O Swerve Drive
    } else {
      // TODO: Implement R-O Swerve Drive
    }
  }

  public void drive(double x, double y) {

  }

  public void drive(double rot) {
    
  }

  public void setTranslationPower(double power) {
    m_frontRight.setDrivePower(power);
    m_frontLeft.setDrivePower(power);
    m_backLeft.setDrivePower(power);
    m_backRight.setDrivePower(power);
  }

  public void stopAllModules() {
    m_frontRight.stopModule();
    m_frontLeft.stopModule();
    m_backLeft.stopModule();
    m_backRight.stopModule();
  }
}
