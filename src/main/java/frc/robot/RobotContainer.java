// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ModulesDriveToPower;
import frc.robot.commands.ModuleTurnToAngle;
import frc.robot.commands.TestModule;
import frc.robot.subsystems.SwerveBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  //? The robot's subsystems and commands are defined here...
  private final SwerveBase m_SwerveBase = new SwerveBase();

  //? Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverController1Port);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  private void configureBindings() {
    SmartDashboard.putString("Configuring Bindings", "Begin");
    m_driverController.y().whileTrue(new ModuleTurnToAngle(
        m_SwerveBase.m_frontRight, m_driverController
    ));
    m_driverController.b().whileTrue(new ModuleTurnToAngle(
        m_SwerveBase.m_frontLeft, m_driverController
    ));
    m_driverController.a().whileTrue(new ModuleTurnToAngle(
        m_SwerveBase.m_backLeft, m_driverController
    ));
    m_driverController.x().whileTrue(new ModuleTurnToAngle(
        m_SwerveBase.m_backRight, m_driverController
    ));
    m_driverController.rightBumper().whileTrue(new ModulesDriveToPower(
      m_SwerveBase, m_driverController));
    // m_driverController.povLeft().whileTrue(new ModuleTurnToAngle(
      // m_SwerveBase.m_backRight, 90));
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
