package org.firstinspires.ftc.teamcode.TEST;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.middleend.bases.AutoBase3;

@Autonomous(name = "DriveTrainTester")
public class DriveTrainTester extends AutoBase3 {

    @Override
    public void autoCode() {
        robot.dT.drive(1, 5);
        sleep(500);
        robot.dT.drive(-1);
        sleep(500);
    }
}
