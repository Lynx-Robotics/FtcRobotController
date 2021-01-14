package org.firstinspires.ftc.teamcode.frontend.tele;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "teleop1")
@Disabled
public class Teleop1 extends LinearOpMode {
    DcMotor tl, tr, bl, br;
    ElapsedTime runtime = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        tl = hardwareMap.get(DcMotor.class,"TL");
        tr = hardwareMap.get(DcMotor.class,"TR");
        bl = hardwareMap.get(DcMotor.class,"BL");
        br = hardwareMap.get(DcMotor.class,"BR");
        runtime.reset();
        tr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        runtime.reset();
        while(runtime.seconds()<2)
        {
            tl.setPower(0.5);
            tr.setPower(0.5);
            bl.setPower(0.5);
            br.setPower(0.5);
        }
    }
}
