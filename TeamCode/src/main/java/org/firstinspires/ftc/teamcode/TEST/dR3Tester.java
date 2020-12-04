package org.firstinspires.ftc.teamcode.TEST;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.backend.localisation.dR.dR3;
import org.firstinspires.ftc.teamcode.middleend.bases.AutoBase3;

@Autonomous(name = "d3Tester")
public class dR3Tester extends AutoBase3 {

    @Override
    public void autoCode() {
        locate.move(0.8, 0.8);
        locate.move(0.8, 0.8);
        robot.dT.setPower(0);
        sleep(5000);
    }
}
