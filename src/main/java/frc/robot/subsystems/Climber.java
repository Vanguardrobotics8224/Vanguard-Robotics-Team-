// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SoftLimitConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.MotorIds;

public class Climber extends SubsystemBase {

  private final SparkMax climberMotor = new SparkMax(MotorIds.climberMotorId, MotorType.kBrushless);
  private final SparkBaseConfig climberMotorConfig = new SparkMaxConfig().apply(new SoftLimitConfig()
      .forwardSoftLimit(0).reverseSoftLimit(0).forwardSoftLimitEnabled(true).reverseSoftLimitEnabled(true));

  /** Creates a new Climber. */
  public Climber() {
    climberMotor.configure(climberMotorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
  }

  public Command set(double speed) {
    return runEnd(() -> climberMotor.set(speed), () -> climberMotor.set(0));
  }

  public Command extend() {
    return set(0.5).until(() -> climberMotor.getEncoder().getPosition() > 500);
  }

  public Command retract() {
    return set(-0.8).until(() -> climberMotor.getEncoder().getPosition() < 10);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
