// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.SwerveBase;
import frc.robot.subsystems.SwerveModule;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ModulesDriveToPower extends Command {

  private SwerveBase m_swerveBase;
  private CommandXboxController m_controller;
  private double m_power;

  /** Creates a new ModuleDriveToPower. */
  // public ModuleDriveToPower(SwerveModule swerveModule, double angle) {
  //   m_swerveModule = swerveModule;
  //   m_targetAngle = angle;

  //   // Use addRequirements() here to declare subsystem dependencies.
  //   addRequirements(m_swerveModule);
  // }

  /** Creates a new ModuleTurnToAngle. */
  public ModulesDriveToPower(SwerveBase swerveBase, CommandXboxController controller) {
    m_swerveBase = swerveBase;
    m_controller = controller;

    // Use addRequirements() here to declare subsystem dependencies.
    // addRequirements(m_swerveBase);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_power = 0;
    m_swerveBase.setTranslationPower(m_power);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_power = Math.pow(Math.sqrt((m_controller.getLeftX() * m_controller.getLeftX()) + (m_controller.getLeftY() * m_controller.getLeftY())), 2);
    m_swerveBase.setTranslationPower(m_power * 0.1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_swerveBase.stopAllModules();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}