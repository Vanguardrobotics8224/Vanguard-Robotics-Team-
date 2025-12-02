// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.SwerveBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

@SuppressWarnings("unused")
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
    m_driverController.a().onTrue(m_SwerveBase.run(() -> {
      m_SwerveBase.m_frontRight.setDrivePower(0.5);
    }));
  }

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }
}
