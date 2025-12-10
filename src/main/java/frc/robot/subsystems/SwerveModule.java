// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveModule extends SubsystemBase {

  private TalonFX m_driveMotor;
  private TalonFX m_turnMotor;
  private CANcoder m_encoder;
  private int m_moduleID;

  /** Creates a new SwerveModule. */
  public SwerveModule(int driveMotorID, int turnMotorID, int encoderID, int moduleID) {
    m_driveMotor = new TalonFX(driveMotorID);
    m_turnMotor = new TalonFX(turnMotorID);
    m_encoder = new CANcoder(encoderID);
    m_moduleID = moduleID;

    // TODO: Configure motors and encoder settings
    //// TalonFXConfiguration driveConfig = new TalonFXConfiguration();
    //// TalonFXConfiguration turnConfig = new TalonFXConfiguration();
    //// CANcoderConfiguration encoderConfig = new CANcoderConfiguration();

    // ? Apply configurations
    //// m_driveMotor.getConfigurator().apply(driveConfig);
    //// m_turnMotor.getConfigurator().apply(turnConfig);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Module #" + m_moduleID + " Drive Power", getDrivePower());
    SmartDashboard.putNumber("Module #" + m_moduleID + " Turn Power", getTurnPower());
    SmartDashboard.putNumber("Module #" + m_moduleID + " Encoder Position",
        m_encoder.getAbsolutePosition().getValueAsDouble() * 360);
  }

  public void setDrivePower(double power) {
    m_driveMotor.set(power);
  }

  public void setTurnPower(double power) {
    m_turnMotor.set(power);
  }

  public double getDrivePower() {
    return m_driveMotor.get();
  }

  public double getTurnPower() {
    return m_turnMotor.get();
  }

  public double getCurrentAngle() {
    return m_encoder.getAbsolutePosition().getValueAsDouble() * 360;
  }

  public Command turnTo(double angle) {
    return new Command() {

      private final SwerveModule m_swerveModule = SwerveModule.this;
      private final double m_targetAngle = angle;
      private double m_error;

      // Called when the command is initially scheduled.
      @Override
      public void initialize() {
        m_error = (m_targetAngle - m_swerveModule.getCurrentAngle()) / 360;
        m_swerveModule.setTurnPower(0);
      }

      // Called every time the scheduler runs while the command is scheduled.
      @Override
      public void execute() {
        m_error = (m_targetAngle - (m_swerveModule.getCurrentAngle())) / 360;
        m_swerveModule.setTurnPower(m_error * 0.1);
      }

      // Called once the command ends or is interrupted.
      @Override
      public void end(boolean interrupted) {
        m_swerveModule.setTurnPower(0);
      }

      // Returns true when the command should end.
      @Override
      public boolean isFinished() {
        return Math.abs(m_error) < 0.05;
      }
    };
  }

  public void stopModule() {
    setDrivePower(0);
    setTurnPower(0);
  }
}
