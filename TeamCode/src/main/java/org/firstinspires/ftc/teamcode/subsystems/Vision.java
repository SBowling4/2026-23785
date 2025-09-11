package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.sun.tools.javac.util.List;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.util.Alliance;

import java.util.ArrayList;
import java.util.Optional;

public class Vision {

    HardwareMap hardwareMap;
    private final Limelight3A limelight;
    private LLResult result;

    private final List<Integer> obeliskTags = List.of(21, 22, 23);
    int goodTagId = Robot.allliance == Alliance.BLUE ? 20 : 24;
    LLResultTypes.FiducialResult goodTag;

    public Vision(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");


    }

    public Optional<Double> getXDegrees() {
        if (!result.isValid()) {
            return Optional.empty();
        }

        return Optional.of(goodTag.getTargetXDegrees());
    }

    public void loop() {
        result = limelight.getLatestResult();

        for (LLResultTypes.FiducialResult fidResult : result.getFiducialResults()) {
            if (fidResult.getFiducialId() == goodTagId) {
                goodTag = fidResult;
                break;
            }
        }

    }



}
