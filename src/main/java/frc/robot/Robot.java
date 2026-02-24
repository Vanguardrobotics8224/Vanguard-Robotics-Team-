package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.DutyCycleOut;

public class Robot extends TimedRobot {

    // Kraken X60 motors (TalonFX) with CAN IDs
    private TalonFX kraken1 = new TalonFX(0);   // Motor 1
    private TalonFX kraken2 = new TalonFX(1);   // Motor 2
    private TalonFX kraken3 = new TalonFX(2);   // Motor 3

    private XboxController controller = new XboxController(0);

    @Override
    public void teleopPeriodic() {

        // A button → run motor 1 and motor 2
        if (controller.getAButton()) {
            kraken1.setControl(new DutyCycleOut(0.5));   // 50% power
            kraken2.setControl(new DutyCycleOut(-0.5));
        } else {
            kraken1.setControl(new DutyCycleOut(0.0));   // stop
            kraken2.setControl(new DutyCycleOut(0.0));
        }

        // B button → run motor 3
        if (controller.getBButton()) {
            kraken3.setControl(new DutyCycleOut(-0.7));   // 50% power
        } else {
            kraken3.setControl(new DutyCycleOut(0.0));   // stop
        }
    }
}
