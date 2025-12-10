// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveModule;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ModuleTurnToAngle extends Command {

  private SwerveModule m_swerveModule;
  private double m_targetAngle;
  private double m_error;

  /** Creates a new ModuleTurnToAngle. */
  public ModuleTurnToAngle(SwerveModule swerveModule, double angle) {
    m_swerveModule = swerveModule;
    m_targetAngle = angle;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_swerveModule);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_error = (m_targetAngle - (m_swerveModule.getCurrentAngle())) / 360;
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
}