package org.firstinspires.ftc.teamcode.subsystems;

import android.provider.Telephony;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.Constants;

import java.util.Optional;

public class TurretSubsystem {

    private final CRServoImplEx turretServo;
    private final PIDController pidController;

    // multi-turn tracking
    private double revolutions = 0.0;
    private double lastRawPosition = 0.0;
    private double power = 0.0;

    private final Vision vision;

    public TurretSubsystem(HardwareMap hardwareMap, Vision vision) {
        turretServo = hardwareMap.get(CRServoImplEx.class, "TurretServo");
        this.vision = vision;
        pidController = new PIDController(
                Constants.TurretConstants.kP,
                Constants.TurretConstants.kI,
                Constants.TurretConstants.kD
        );
    }

    public void loop() {
        Optional<Double> angle = vision.getXDegrees();

        if (!angle.isPresent()) {
            setPosition(0);
        }

        if (!angle.isPresent()) return;

        setPosition(angle.get());
    }

    /**
     * Get the raw Axon CR position (0.0 to 1.0 per revolution).
     */
    private double getRawPosition() {
        ServoControllerEx controller = (ServoControllerEx) turretServo.getController();
        return controller.getServoPosition(turretServo.getPortNumber());
    }

    /**
     * Returns continuous position in servo rotations (multi-turn).
     */
    public double getContinuousPosition() {
        double raw = getRawPosition();

        // Detect wraparound
        if (raw - lastRawPosition > 0.5) {
            // jumped backwards across 0
            revolutions -= 1;
        } else if (lastRawPosition - raw > 0.5) {
            // jumped forwards across 1.0
            revolutions += 1;
        }

        lastRawPosition = raw;
        return revolutions + raw;
    }

    /**
     * Returns continuous turret angle in degrees, including gear ratio.
     */
    public double getContinuousAngleDegrees() {
        return getContinuousPosition() * 360.0 * Constants.TurretConstants.GEAR_RATIO;
    }

    /**
     * Run PID to move turret toward a target angle (degrees).
     */
    public void setPosition(double targetAngle) {
        double currentAngle = getContinuousAngleDegrees();
        power = pidController.calculate(currentAngle, targetAngle);
        turretServo.setPower(power);
    }

    /**
     * Stop turret movement.
     */
    public void stop() {
        power = 0.0;
        turretServo.setPower(0.0);
    }

}
