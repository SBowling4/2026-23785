package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.util.Alliance;

import java.util.concurrent.atomic.AtomicBoolean;

@TeleOp(name = "Clanker")
public class Robot extends OpMode {

    public static Alliance allliance = Alliance.UNKNOWN;
    public static AtomicBoolean hasMotif = new AtomicBoolean(false);

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }
}
