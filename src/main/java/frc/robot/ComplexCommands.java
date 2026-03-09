package frc.robot;

import static edu.wpi.first.units.Units.Meters;

import java.util.Optional;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.ctre.phoenix6.swerve.SwerveRequest.RobotCentricFacingAngle;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class ComplexCommands {

    private Climber climber;
    private CommandSwerveDrivetrain drivetrain;
    private Intake intake;
    private Shooter shooter;

    private SwerveRequest.RobotCentricFacingAngle aimRequest = new RobotCentricFacingAngle()
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    public ComplexCommands(Climber climber, CommandSwerveDrivetrain drivetrain, Intake intake, Shooter shooter) {
        this.climber = climber;
        this.drivetrain = drivetrain;
        this.intake = intake;
        this.shooter = shooter;
    }

    private Translation2d blueHubTranslation = new Translation2d(Meters.of(4.6), Meters.of(4.03));
    private Translation2d redHubTranslation = new Translation2d(Meters.of(16.5 - 4.6), Meters.of(4.03));

    private Translation2d diffFromHub() {
        Optional<Alliance> alliance = DriverStation.getAlliance();
        if (alliance.get() == Alliance.Blue || alliance.isEmpty()) {
            return blueHubTranslation.minus(drivetrain.getState().Pose.getTranslation());
        } else {
            return redHubTranslation.minus(drivetrain.getState().Pose.getTranslation());
        }
    }

    private void aimRobot() {
        Translation2d diff = diffFromHub();
        Rotation2d targetDirection = diff.getAngle();
        LinearVelocity velocity = TunerConstants.kSpeedAt12Volts.times(0.1 * diff.getNorm());
        drivetrain.applyRequest(
                () -> aimRequest.withVelocityX(velocity).withVelocityY(0)
                        .withTargetDirection(targetDirection));
    }

    public Command aimAndShoot(AngularVelocity speed) {
        return Commands.deadline(
                drivetrain.run(this::aimRobot).until(() -> {
                    Translation2d diff = diffFromHub();
                    return diff.getAngle().getDegrees() < 5 && diff.getNorm() < 0.1;
                }),
                shooter.shootNoIndexer(speed))
                .andThen(shooter.shoot(speed));
    }

}
