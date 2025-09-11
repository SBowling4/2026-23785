package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.Artifact;
import org.firstinspires.ftc.teamcode.util.IndexerSlot;

public class IndexerSubsystem {
    HardwareMap hardwareMap;

    MotorEx indexerMotor;

    private PIDController pidController;
    private double position;
    IndexerSlot slot1 = new IndexerSlot(Artifact.NONE);
    IndexerSlot slot2 = new IndexerSlot(Artifact.NONE);
    IndexerSlot slot3 = new IndexerSlot(Artifact.NONE);
    IndexerSlot slot4 = new IndexerSlot(Artifact.NONE);

    public IndexerSubsystem(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }
}