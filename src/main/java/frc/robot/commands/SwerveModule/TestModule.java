// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.SwerveModule;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.SwerveModule;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class TestModule extends Command {

  private SwerveModule m_swerveModule;
  private CommandXboxController m_controller;

  /** Creates a new TestModule. */
  public TestModule(SwerveModule swerveModule, CommandXboxController controller) {
    m_swerveModule = swerveModule;
    m_controller = controller;

    // Use addRequirements() here to declare subsystem dependencies.
    // addRequirements(m_swerveModule);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double drivePower = m_controller.getLeftY();
    double turnPower = m_controller.getRightX();

    m_swerveModule.setDrivePower(drivePower);
    m_swerveModule.setTurnPower(turnPower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_swerveModule.setDrivePower(0);
    m_swerveModule.setTurnPower(0);  

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (m_swerveModule.getDrivePower() == 0 && m_swerveModule.getTurnPower() == 0);
  }
}
