// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.MotorIds;

public class Climber extends SubsystemBase {

  // Find the threshold through Tuner X, look at the encoder position when at the
  // correct height
  private final double forwardThreshold = 500;
  private final double reverseThreshold = 0;

  private final TalonFX climberMotor = new TalonFX(MotorIds.climberMotorId);

  /** Creates a new Climber. */
  public Climber() {
    climberMotor.getConfigurator()
        .apply(new SoftwareLimitSwitchConfigs().withForwardSoftLimitEnable(true).withReverseSoftLimitEnable(true)
            .withForwardSoftLimitThreshold(forwardThreshold).withReverseSoftLimitThreshold(reverseThreshold));
  }

  public Command set(double speed) {
    return runEnd(() -> climberMotor.set(speed), () -> climberMotor.set(0));
  }

  public Command extend() {
    return set(0.5).until(() -> climberMotor.getPosition().getValueAsDouble() >= forwardThreshold - 1);
  }

  public Command retract() {
    return set(-0.8).until(() -> climberMotor.getPosition().getValueAsDouble() <= reverseThreshold + 1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
