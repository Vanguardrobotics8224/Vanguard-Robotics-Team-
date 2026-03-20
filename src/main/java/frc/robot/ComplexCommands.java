package frc.robot;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Seconds;

import java.util.Optional;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.ctre.phoenix6.swerve.SwerveRequest.RobotCentricFacingAngle;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class ComplexCommands {

    private Climber climber;
    private CommandSwerveDrivetrain drivetrain;
    private Intake intake;
    private Shooter shooter;

    private final Distance aimDistError = Meters.of(0.1); // Good enough distance error
    private final Angle aimAngleError = Degrees.of(3); // Good enough rotation error

    private SwerveRequest.RobotCentricFacingAngle aimRequest = new RobotCentricFacingAngle()
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    public ComplexCommands(Climber climber, CommandSwerveDrivetrain drivetrain, Intake intake, Shooter shooter) {
        this.climber = climber;
        this.drivetrain = drivetrain;
        this.intake = intake;
        this.shooter = shooter;
    }

    private Translation2d blueHubTranslation = new Translation2d(Meters.of(4.6), Meters.of(4.03)); // Position of blue
                                                                                                   // hub
    private Translation2d redHubTranslation = new Translation2d(Meters.of(16.5 - 4.6), Meters.of(4.03)); // Position of
                                                                                                         // red hub

    /**
     * Get the current difference between robot position and hub position.
     * Uses the alliance color in driver station, blue is default.
     * 
     * @return Translation of difference between position of robot and hub
     */
    private Translation2d diffFromHub() {
        Optional<Alliance> alliance = DriverStation.getAlliance();
        if (alliance.get() == Alliance.Blue || alliance.isEmpty()) {
            return blueHubTranslation.minus(drivetrain.getState().Pose.getTranslation());
        } else {
            return redHubTranslation.minus(drivetrain.getState().Pose.getTranslation());
        }
    }

    /**
     * Aim the robot towards the hub at a specific distance.
     * Turns the robot to set angle and goes thowards the hub.
     * 
     * @param aimDistance Distance from the hub
     * @param kP          Gain to reach the set distance based on distance error
     */
    private void aimRobot(Distance aimDistance, double kP) {
        Translation2d diff = diffFromHub();
        Rotation2d targetDirection = diff.getAngle();
        LinearVelocity velocity = Meters.of(diff.getNorm()).minus(aimDistance).times(kP).per(Seconds);
        drivetrain.applyRequest(
                () -> aimRequest.withVelocityX(velocity).withVelocityY(0)
                        .withTargetDirection(targetDirection));
    }

    /**
     * Command to aim the robot at the alliance hub and start shooting when close
     * enough.
     * 
     * @param speed       Speed for the shooter wheel
     * @param aimDistance Distance from the hub to shoot from
     * @return Command that performs the task
     */
    public Command aimAndShoot(AngularVelocity speed, Distance aimDistance) {
        return Commands.deadline(
                drivetrain.run(() -> aimRobot(aimDistance, 2)).until(() -> {
                    Translation2d diff = diffFromHub();
                    Distance currDistance = Meters.of(diff.getNorm());
                    return diff.getAngle().getMeasure().lt(aimAngleError)
                            && currDistance.minus(aimDistance).lt(aimDistError);
                }),
                shooter.shootNoIndexer(speed))
                .andThen(shooter.shoot(speed));
    }

    /**
     * Spins up the shooter first, then starts feeding with the intake once the
     * shooter is at speed.
     *
     * @param speed Speed for the shooter wheel
     * @return Command that performs the task while held
     */
    public Command shootWithIntake(AngularVelocity speed) {
        return Commands.deadline(
                shooter.shoot(speed),
                Commands.waitUntil(() -> shooter.getVelocity().gte(speed.times(0.95)))
                        .andThen(intake.runIntake()));
    }

}
