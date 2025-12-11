# Vanguard Robotics 2025-2026 Season

> **IMPORTANT**: All the relevant code relating to swerve can be found under *src/main/java/frc/robot*. For the sake of simplicity, we will refer to that file path as [**/robot**](https://github.com/Vanguardrobotics8224/Vanguard-Robotics-Team-/tree/nour/src/main/java/frc/robot) for short.

## File structure

```markdown
/robot
├── commands/
│   └── All command files will be placed here
├── subsystems/
│   └── All subsystem files will be placed here
├── Constants.java
├── Main.java
├── RobotContainer.java
└── Robot.java
```

## Explanation of the files and directories contents

1. [commands/](https://github.com/Vanguardrobotics8224/Vanguard-Robotics-Team-/tree/nour/src/main/java/frc/robot/commands)

    - This directory will be used to store all classes extending from the **Command** superclass.

    - Every command should, by default, include the following methods:
        - *initialize()* -> The first instruction called by the scheduler
        - *execute()* -> An instruction loop that is called periodically by the scheduler
        - *end(boolean)* -> A final instruction called by the scheduler when the command is interrupted or ends
        - *isFinished()* -> A check that returns if the command has accomplished it's goal

    > **NOTE**: Upon returning *true* for the *isFinished()* method, the scheduler will automatically execute the *end(boolean)* method, signaling the end of the command until it is rescheduled once more.

2. [subsystems/](https://github.com/Vanguardrobotics8224/Vanguard-Robotics-Team-/tree/nour/src/main/java/frc/robot/subsystems)

    - This directory will be used to store all classes extending from the **Subsystem** superclass.

    - A subsystem describes any part of the robot that is unique in functionality. For example:
        - DriveBase.java
        - PivotArm.java
        - EndEffector.java

    - Every subsystem class should only include **getters**, such as *getEncoderValue()*, **setters**, such as *setMotorPower(double power)*, and *periodic()*, which executes instructions eery 20ms or so (perfect for telemetry via *SmartDashboard()*).

    - By convention, any attributes of a subsystem will both be **private** and contain the prefix "**m_**", declaring it as a member of the subsystem.

    > **NOTE**: Any arguments passed inside of the constructor, or a getter/setter method, may NOT have the "**m_**" prefix.

3. [Constants.java](https://github.com/Vanguardrobotics8224/Vanguard-Robotics-Team-/blob/nour/src/main/java/frc/robot/Constants.java)

    - Any constants, such as CAN IDs or Controller Ports, can be put here to be used globally (by import).

4. [Main.java](https://github.com/Vanguardrobotics8224/Vanguard-Robotics-Team-/blob/nour/src/main/java/frc/robot/Main.java)

    - The entry point of the whole program. **DO NOT, UNDER ANY CIRCUMSTANCE, INSERT INITIALIZATION CODE HERE**.

5. [Robot.java](https://github.com/Vanguardrobotics8224/Vanguard-Robotics-Team-/blob/nour/src/main/java/frc/robot/Robot.java)

    - Serves as the main control file of the FRC robot, with core methods that dictate the robot's operation. Tampering with this file is not recommended unless necessary.

6. [RobotContainer.java](https://github.com/Vanguardrobotics8224/Vanguard-Robotics-Team-/blob/nour/src/main/java/frc/robot/RobotContainer.java)

    - Serves as the configurator of the robot, with all the necessary subsystems, commands and bindings.

## TL;DR

- A **Subsystem** is a part of the robot, with no real logic embedded in it.

- A **Command** contains all the logic in order to activate the functionalities of a subsystem.

> Please refer to the full explanation in order to fully comprehend the usages of these core components, or refer to [FRC Zero](https://www.frczero.org/programming/command-based-programming/) for more information.

## List of items to implement/fix

- [ ] Tune the turn motor to be able to be within a 1 degree margin of error.

- [ ] Optimize the wheel rotation in order to turn 90 degrees at most.
