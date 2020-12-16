package org.firstinspires.ftc.teamcode.frontend.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.middleend.bases.AutoBase3;


@Autonomous(name = "ExampleDrivetrainAuto", group = "example")
public class ExampleDrivetrainAuto extends AutoBase3 {

    @Override
    public void autoCode() {
        addTelemetryData("STATUS", "Starting...");
        sleep(1000);
        // setting time paramters
        double time = et.seconds();
        double time_stop = time + 2;
        addTelemetryData("STATUS", "S1 (0.5 power forward)");
        while (time <= time_stop){
            // set power to all wheels for x seconds (should go relatively straight)
            robot.dT.setPower(0.5);
            time = et.seconds();
        }
        robot.dT.setPower(0.0);
        // setting time parameters
        time = et.seconds();
        time_stop = time + 2;
        addTelemetryData("STATUS", "S2 (-0.5 power backward)");
        while (time <= time_stop){
            // set power to all wheels for x seconds (should go relatively straight)
            robot.dT.setPower(-0.5);
            time = et.seconds();
        }
        robot.dT.setPower(0.0);
        sleep(1000);
//        // Checking turning mechanism
//        addTelemetryData("STATUS", "S3 (Turning Test)");
//        sleep(2000);
//        robot.dT.turn(0);
//        sleep(1000);
//        robot.dT.turn(90);
//        sleep(1000);
//        robot.dT.turn(90);
//        sleep(1000);
//        robot.dT.turn(90);
//        sleep(1000);
//        robot.dT.turn(90);
//        sleep(1000);
//        robot.dT.turn(-90);
//        sleep(1000);
//        robot.dT.turn(-90);
//        sleep(1000);
//        robot.dT.turn(-90);
//        sleep(1000);
//        robot.dT.turn(-90);
//        sleep(1000);

        // Checking distance drive mechanism
        addTelemetryData("STATUS", "S4 (Drive Method Testing)");
        robot.dT.drive(0.45);
        sleep(1000);

        // Checking distance drive backwards
        addTelemetryData("STATUS", "S5 (Backwards Drive Method Testing)");
        robot.dT.drive(-0.45);
        sleep(1000);

        // Checking distance drive with angle mechanism
        addTelemetryData("STATUS", "S6 (Distance Angle Forward Testing)");
        robot.dT.drive(0.6, 45);
        sleep(1000);

        // Checking distance drive with same angle backwards distance
        addTelemetryData("STATUS", "S7 (Distance Angle Backward Testing)");
        robot.dT.drive(-0.6, 45);
        sleep(1000);

        // Checking random angle random distance
        addTelemetryData("STATUS", "S8 (Distance Angle Random Testing)");
        robot.dT.drive(0.3, -31);
        sleep(1000);

        sleep(6000);
    }
}
