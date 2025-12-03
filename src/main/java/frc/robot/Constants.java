// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class CANConstants {
    public static final int kPidgeonID = 0;
    public static final int kFrontRightDriveMotorID = 1;
    public static final int kFrontRightTurnMotorID = 2;
    public static final int kFrontRightEncoderID = 0;
    public static final int kFrontLeftDriveMotorID = 4;
    public static final int kFrontLeftTurnMotorID = 5;
    public static final int kFrontLeftEncoderID = 0;
    public static final int kBackLeftDriveMotorID = 7;
    public static final int kBackLeftTurnMotorID = 8;
    public static final int kBackLeftEncoderID = 0;
    public static final int kBackRightDriveMotorID = 10;
    public static final int kBackRightTurnMotorID = 11;
    public static final int kBackRightEncoderID = 0;
  }
}
