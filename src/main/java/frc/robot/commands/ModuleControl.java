// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.SwerveModule;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ModuleControl extends Command {

  private SwerveModule m_SwerveModule;
  private CommandXboxController m_XboxController;

  /** Creates a new FrontRightModule. */
  public ModuleControl(SwerveModule swerveModule, CommandXboxController controller) {
    m_SwerveModule = swerveModule;
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_SwerveModule);

    m_XboxController = controller;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_SwerveModule.setDrivePower(m_XboxController.getLeftY() * 0.5);
    m_SwerveModule.setTurnPower(m_XboxController.getLeftX() * 0.1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_SwerveModule.setDrivePower(0);
    m_SwerveModule.setTurnPower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
