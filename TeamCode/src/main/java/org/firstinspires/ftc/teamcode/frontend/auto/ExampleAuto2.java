package org.firstinspires.ftc.teamcode.frontend.auto;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.middleend.bases.AutoBase2;

@TeleOp(name="UpdatedCoordinateTester")
public class ExampleAuto2 extends AutoBase2 {
    @Override
    public void autoCode() {
        drive(new double[]{0.4, 0.4});
    }
}
