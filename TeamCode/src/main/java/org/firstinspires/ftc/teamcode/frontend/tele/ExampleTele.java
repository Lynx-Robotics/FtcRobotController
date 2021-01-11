package org.firstinspires.ftc.teamcode.frontend.tele;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.middleend.bases.TeleBase;

@TeleOp(name = "ExampleTeleOp", group = "example")
public class ExampleTele extends TeleBase {

    @Override
    public void codeToInit() {
        // motor linkage
        robot.BL.linkToMotor(robot.TL);
        robot.BR.linkToMotor(robot.TR);
    }

    @Override
    public void codeToRun() {
        while(opModeIsActive() && !Thread.interrupted()){
            // power only needs to be supplied to the TL and TR since the other are linked
            robot.TL.setPower(gamepad1.left_stick_y);
            robot.TR.setPower(gamepad1.right_stick_y);
        }
    }
}
