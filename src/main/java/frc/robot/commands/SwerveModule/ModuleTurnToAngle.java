// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.SwerveModule;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.SwerveModule;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ModuleTurnToAngle extends Command {

  private SwerveModule m_swerveModule;
  private double m_targetAngle;
  private CommandXboxController m_controller;
  private double m_error;

  /** Creates a new ModuleTurnToAngle. */
  // public ModuleTurnToAngle(SwerveModule swerveModule, double angle) {
  //   m_swerveModule = swerveModule;
  //   m_targetAngle = angle;

  //   // Use addRequirements() here to declare subsystem dependencies.
  //   addRequirements(m_swerveModule);
  // }

  /** Creates a new ModuleTurnToAngle. */
  public ModuleTurnToAngle(SwerveModule swerveModule, CommandXboxController controller) {
    m_swerveModule = swerveModule;
    m_controller = controller;

    // Use addRequirements() here to declare subsystem dependencies.
    // addRequirements(m_swerveModule);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_targetAngle = Math.toDegrees(Math.atan2(m_controller.getLeftY(), -m_controller.getLeftX()));
    m_error = (m_targetAngle - (m_swerveModule.getCurrentAngle()));
    m_swerveModule.setTurnPower(0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("Error", m_error);
    SmartDashboard.putNumber("Target Angle", m_targetAngle);
    m_targetAngle = Math.toDegrees(Math.atan2(m_controller.getLeftY(), -m_controller.getLeftX()));
    m_error = (m_targetAngle - (m_swerveModule.getCurrentAngle()));
    m_swerveModule.setTurnPower(m_error * -0.0005);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_swerveModule.setTurnPower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false; // Math.abs(m_error) < 0.05
  }
}