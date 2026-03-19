// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Pounds;
import static edu.wpi.first.units.Units.Seconds;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.MotorIds;
import yams.gearing.GearBox;
import yams.gearing.MechanismGearing;
import yams.mechanisms.config.ArmConfig;
import yams.mechanisms.positional.Arm;
import yams.motorcontrollers.SmartMotorController;
import yams.motorcontrollers.SmartMotorControllerConfig;
import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;
import yams.motorcontrollers.remote.TalonFXWrapper;

public class Intake extends SubsystemBase {

  public class PivotSubsystem extends SubsystemBase {
  }

  private final TelemetryVerbosity telemetryVerbosity = TelemetryVerbosity.HIGH;

  private final TalonFX leftPivotMotor = new TalonFX(MotorIds.leftIntakePivotMotorId);
  private final TalonFX rightPivotMotor = new TalonFX(MotorIds.rightIntakePivotMotorId);

  private final SparkMax inatakeMotor = new SparkMax(MotorIds.intakeMotorId, MotorType.kBrushless);

  private final Angle pivotUpSetpoint = Degrees.of(85);
  private final Angle pivotDownSetpoint = Degrees.of(5);

  private final PivotSubsystem pivotSubsystem = new PivotSubsystem();

  private final SmartMotorControllerConfig pivotMotorConfig = new SmartMotorControllerConfig(pivotSubsystem)
      .withFollowers(Pair.of(leftPivotMotor, true))
      .withControlMode(ControlMode.CLOSED_LOOP)
      .withClosedLoopController(50, 0, 0)
      .withFeedforward(new ArmFeedforward(0, 0, 0))
      .withTelemetry("PivotMotor", telemetryVerbosity)
      .withGearing(new MechanismGearing(GearBox.fromReductionStages(4, 5)))
      .withMotorInverted(false)
      .withIdleMode(MotorMode.BRAKE)
      .withStatorCurrentLimit(Amps.of(40))
      .withClosedLoopRampRate(Seconds.of(0.25))
      .withOpenLoopRampRate(Seconds.of(0.25));

  private final SmartMotorController pivotMotorController = new TalonFXWrapper(rightPivotMotor, DCMotor.getNEO(2),
      pivotMotorConfig);

  private final ArmConfig pivotConfig = new ArmConfig(pivotMotorController)
      .withSoftLimits(Degrees.of(0), Degrees.of(90))
      .withHardLimit(Degrees.of(-5), Degrees.of(95))
      .withStartingPosition(Degrees.of(90))
      .withLength(Inches.of(18))
      .withMass(Pounds.of(10))
      .withTelemetry("Pivot", telemetryVerbosity);

  private final Arm pivot = new Arm(pivotConfig);

  /** Creates a new Intake. */
  public Intake() {

  }

  public Command runIntake() {
    return pivot.run(pivotDownSetpoint).alongWith(runEnd(() -> inatakeMotor.set(1), () -> inatakeMotor.set(0)));
  }

  public Command lift() {
    return runOnce(() -> pivot.setMechanismPositionSetpoint(pivotUpSetpoint));
  }

  public Command reverse() {
    return pivot.run(pivotDownSetpoint).alongWith(runEnd(() -> inatakeMotor.set(-1), () -> inatakeMotor.set(0)));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    pivot.updateTelemetry();
  }

  @Override
  public void simulationPeriodic() {
    pivot.simIterate();
  }
}
