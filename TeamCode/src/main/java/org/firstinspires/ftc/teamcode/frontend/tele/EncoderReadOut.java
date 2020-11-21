package org.firstinspires.ftc.teamcode.frontend.tele;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.middleend.HardwareMappings.HMap;

@TeleOp(name = "EncoderReadout")
public class EncoderReadOut extends LinearOpMode {
    HMap robot = new HMap();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("TL Encoder: ", robot.TL_.getCurrentPosition());
            telemetry.addData("TR Encoder: ", robot.TR_.getCurrentPosition());
            telemetry.addData("BL Encoder: ", robot.BL_.getCurrentPosition());
            telemetry.addData("BR Encoder: ", robot.BR_.getCurrentPosition());
            telemetry.update();
        }
    }
}
