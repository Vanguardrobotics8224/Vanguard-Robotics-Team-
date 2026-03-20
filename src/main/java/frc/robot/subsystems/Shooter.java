// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Pounds;
import static edu.wpi.first.units.Units.RPM;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.MotorIds;
import yams.mechanisms.config.FlyWheelConfig;
import yams.mechanisms.velocity.FlyWheel;
import yams.motorcontrollers.SmartMotorController;
import yams.motorcontrollers.SmartMotorControllerConfig;
import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;
import yams.motorcontrollers.remote.TalonFXWrapper;

public class Shooter extends SubsystemBase {

  public class FlyWheelSubsystem extends SubsystemBase {
  }

  private final TelemetryVerbosity telemetryVerbosity = TelemetryVerbosity.HIGH;

  private final TalonFX leftShooterMotor = new TalonFX(MotorIds.leftShooterMotorId);
  private final TalonFX rightShooterMotor = new TalonFX(MotorIds.rightShooterMotorId);

  private final SparkMax indexerMotor = new SparkMax(MotorIds.indexerMotorId, MotorType.kBrushless);
  private final SparkMax beltMotor = new SparkMax(MotorIds.shooterBeltMotorId, MotorType.kBrushless);

  private final FlyWheelSubsystem flyWheelSubsystem = new FlyWheelSubsystem();

  private final SmartMotorControllerConfig shooterControllerConfig = new SmartMotorControllerConfig(flyWheelSubsystem)
      .withFollowers(Pair.of(leftShooterMotor, true))
      .withControlMode(ControlMode.CLOSED_LOOP)
      .withClosedLoopController(50, 0, 0)
      .withFeedforward(new SimpleMotorFeedforward(0, 0, 0))
      .withTelemetry("ShooterMotor", telemetryVerbosity)
      .withGearing(1)
      .withMotorInverted(false)
      .withIdleMode(MotorMode.COAST)
      .withStatorCurrentLimit(Amps.of(40));

  private final SmartMotorController shooterController = new TalonFXWrapper(rightShooterMotor, DCMotor.getKrakenX60(2),
      shooterControllerConfig);

  private final FlyWheelConfig shooterConfig = new FlyWheelConfig(shooterController)
      .withDiameter(Inches.of(4))
      .withMass(Pounds.of(3))
      .withUpperSoftLimit(RPM.of(6000))
      .withTelemetry("ShooterMech", telemetryVerbosity);

  private final FlyWheel shooter = new FlyWheel(shooterConfig);

  /**
   * Gets the current velocity of the shooter.
   *
   * @return Shooter velocity.
   */
  public AngularVelocity getVelocity() {
    return shooter.getSpeed();
  }

  /**
   * Speed up the shooter and activate the indexer when ready, then turn off the
   * shooter and indexer when done
   * 
   * @param speed angular velocity of the shooter
   * @return Command to schedule
   */
  public Command shoot(AngularVelocity speed) {
    return shooter.runTo(speed, speed.times(0.05))
        .andThen(run(() -> {
          shooter.setSpeed(speed);
          indexerMotor.set(-0.5);
          beltMotor.set(-0.5);
        }))
        .finallyDo(() -> {
          shooter.setDutyCycleSetpoint(0);
          indexerMotor.set(0);
          beltMotor.set(0);
        });
  }

  public Command shootNoIndexer(AngularVelocity speed) {
    return shooter.run(speed);
  }

  public Command stop() {
    return shooter.set(0);
  }

  public Command reverseIndexer() {
    return startEnd(
        () -> {
          indexerMotor.set(-0.5);
          beltMotor.set(-0.5);
        }, () -> {
          indexerMotor.set(0);
          beltMotor.set(0);
        });
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    shooter.updateTelemetry();
  }

  @Override
  public void simulationPeriodic() {
    shooter.simIterate();
  }
}
