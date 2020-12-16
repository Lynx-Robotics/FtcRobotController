package org.firstinspires.ftc.teamcode.TEST;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.backend.localisation.dR.dR3;
import org.firstinspires.ftc.teamcode.middleend.bases.AutoBase3;

@Autonomous(name = "d3Tester")
public class dR3Tester extends AutoBase3 {

    @Override
    public void autoCode() {
        sleep(500);
        locate.move(0.0, 0.0);
        robot.dT.setPower(0);

        telemetry.addData("X-> ", locate.getX());
        telemetry.addData("Y-> ", locate.getY());
        telemetry.update();
        sleep(15000);

    }
}
