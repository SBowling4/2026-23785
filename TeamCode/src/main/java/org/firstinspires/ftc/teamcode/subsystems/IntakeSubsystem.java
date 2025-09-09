package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class IntakeSubsystem {
    MotorEx intakeMotor;

    HardwareMap hardwareMap;

    Gamepad gamepad2;

    public IntakeSubsystem(HardwareMap hardwareMap, Gamepad gamepad2) {
        this.hardwareMap = hardwareMap;
        this.gamepad2 = gamepad2;
    }

    public void init() {
        intakeMotor = new MotorEx(hardwareMap, "IntakeMotor");
    }

    public void loop() {
        if (gamepad2.a) {
            intakeMotor.set(1);
        } else {
            intakeMotor.stopMotor();
        }
    }
}
