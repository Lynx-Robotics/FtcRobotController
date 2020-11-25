package org.firstinspires.ftc.teamcode.middleend.bases;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.backend.hardware_extensions.motor_extensions.motor_;
import org.firstinspires.ftc.teamcode.backend.localisation.dR.DeadReckoning2;
import org.firstinspires.ftc.teamcode.middleend.HardwareMappings.DTHMap;

public abstract class AutoBase3 extends LinearOpMode {
    public DTHMap robot = new DTHMap();

    public abstract void autoCode();

    @Override
    public void runOpMode() throws InterruptedException {

        // Initializing the hardware map
        robot.init(hardwareMap, telemetry);
        addTelemetryData("Calibrating...", "");

        while (!robot.imu.isCalib()){
            // while not calibrated -> calibrate
            sleep(50);
            idle();
        }

        addTelemetryData("Done Calibrating...","");

        // End of Initialization
        waitForStart();

        // Starts autonomous
        autoCode();
    }

    private void addTelemetryData(String caption, String msg){
        telemetry.addData(caption, msg);
        telemetry.update();
    }
}
