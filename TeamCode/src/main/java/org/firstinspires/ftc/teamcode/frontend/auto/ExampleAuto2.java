package org.firstinspires.ftc.teamcode.frontend.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.middleend.bases.AutoBase2;

@Autonomous(name="UpdatedCoordinateTester")
public class ExampleAuto2 extends AutoBase2 {
    @Override
    public void autoCode() {
//        drive(new double[]{0.4, 0.4});
//        drive(new double[]{0.8, 0.0});
//        drive(new double[]{0.0, 0.0});
        drive(new double[]{1.0, 0.0});
        drive(new double[]{0.0, 0.0});
    }
}
