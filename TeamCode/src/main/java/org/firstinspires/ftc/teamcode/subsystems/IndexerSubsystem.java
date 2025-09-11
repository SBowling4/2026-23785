package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.util.Artifact;
import org.firstinspires.ftc.teamcode.util.IndexerSlot;

import java.util.HashMap;
import java.util.Map;

public class IndexerSubsystem {
    HardwareMap hardwareMap;

    MotorEx indexerMotor;

    private final PIDController pidController;
    private double position;

    private ColorSensor colorSensor;

    double color;

    IndexerSlot slot1 = new IndexerSlot(Artifact.NONE);
    IndexerSlot slot2 = new IndexerSlot(Artifact.NONE);
    IndexerSlot slot3 = new IndexerSlot(Artifact.NONE);
    IndexerSlot slot4 = new IndexerSlot(Artifact.NONE);

    Map<IndexerSlot.IndexerPositons, IndexerSlot> currentIndexerPositions = new HashMap<>();
    Map<IndexerSlot.IndexerPositons, IndexerSlot> lastIndexerPositions = new HashMap<>();

    public IndexerSubsystem(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        pidController = new PIDController(Constants.IndexerConstants.kP, Constants.IndexerConstants.kI, Constants.IndexerConstants.kD);

        setIndexerPositions();
    }

    public void loop() {
        position = indexerMotor.getCurrentPosition();
    }

    public void rotatePos90() {
        double power = pidController.calculate(position, position + 90);

        indexerMotor.set(power);

        for (IndexerSlot.IndexerPositons pos : currentIndexerPositions.keySet()) {
            lastIndexerPositions.put(pos, currentIndexerPositions.get(pos));
        }

        currentIndexerPositions.put(IndexerSlot.IndexerPositons.INTAKE, lastIndexerPositions.get(IndexerSlot.IndexerPositons.LEFT));
        currentIndexerPositions.put(IndexerSlot.IndexerPositons.RIGHT, lastIndexerPositions.get(IndexerSlot.IndexerPositons.INTAKE));
        currentIndexerPositions.put(IndexerSlot.IndexerPositons.SHOOTER, lastIndexerPositions.get(IndexerSlot.IndexerPositons.RIGHT));
        currentIndexerPositions.put(IndexerSlot.IndexerPositons.LEFT, lastIndexerPositions.get(IndexerSlot.IndexerPositons.SHOOTER));
    }

    public void rotateNeg90() {
        double power = pidController.calculate(position, position - 90);

        indexerMotor.set(power);

        for (IndexerSlot.IndexerPositons pos : currentIndexerPositions.keySet()) {
            lastIndexerPositions.put(pos, currentIndexerPositions.get(pos));
        }

        currentIndexerPositions.put(IndexerSlot.IndexerPositons.INTAKE, lastIndexerPositions.get(IndexerSlot.IndexerPositons.RIGHT));
        currentIndexerPositions.put(IndexerSlot.IndexerPositons.RIGHT, lastIndexerPositions.get(IndexerSlot.IndexerPositons.SHOOTER));
        currentIndexerPositions.put(IndexerSlot.IndexerPositons.SHOOTER, lastIndexerPositions.get(IndexerSlot.IndexerPositons.LEFT));
        currentIndexerPositions.put(IndexerSlot.IndexerPositons.LEFT, lastIndexerPositions.get(IndexerSlot.IndexerPositons.INTAKE));
    }

    public void removeShooter() {
        currentIndexerPositions.get(IndexerSlot.IndexerPositons.SHOOTER).setArtifact(Artifact.NONE);
    }

    public void intake() {
        color = colorSensor.blue();

        //color logic

        currentIndexerPositions.get(IndexerSlot.IndexerPositons.INTAKE).setArtifact(Artifact.GREEN); //or blue
    }


    public void setIndexerPositions() {
        currentIndexerPositions.put(IndexerSlot.IndexerPositons.INTAKE, slot1);
        currentIndexerPositions.put(IndexerSlot.IndexerPositons.LEFT, slot2);
        currentIndexerPositions.put(IndexerSlot.IndexerPositons.SHOOTER, slot3);
        currentIndexerPositions.put(IndexerSlot.IndexerPositons.RIGHT, slot4);

        lastIndexerPositions.put(IndexerSlot.IndexerPositons.INTAKE, slot1);
        lastIndexerPositions.put(IndexerSlot.IndexerPositons.SHOOTER, slot3);
        lastIndexerPositions.put(IndexerSlot.IndexerPositons.RIGHT, slot4);
        lastIndexerPositions.put(IndexerSlot.IndexerPositons.LEFT, slot2);
    }

}