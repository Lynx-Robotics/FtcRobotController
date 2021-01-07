package org.firstinspires.ftc.teamcode.frontend.tele;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.backend.hardware_extensions.SeriesX.mX;
import org.firstinspires.ftc.teamcode.frontend.CONSTANTS;
import org.firstinspires.ftc.teamcode.middleend.HardwareMappings.DTHMap;
import org.firstinspires.ftc.teamcode.middleend.HardwareMappings.HMap;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOP")
public class TeleOp extends LinearOpMode {

    public DTHMap robot = new DTHMap();

    private DcMotor intakeMotor_;
    private mX intakeMotor;

    private CRServo intakeServo;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, telemetry);

        intakeMotor_ = hardwareMap.get(DcMotor.class, "IntakeMotor");
        intakeMotor = new mX(intakeMotor_, CONSTANTS.wheel_radius_meters_SMALL_OMNI, 1220, CONSTANTS.NullPid_);

        intakeServo = hardwareMap.get(CRServo.class, "IntakeServo");
        waitForStart();
        while(opModeIsActive() && !isStopRequested()){
            robot.TL.setPower(gamepad1.left_stick_y);
            robot.BL.setPower(gamepad1.left_stick_y);
            robot.BR.setPower(gamepad1.right_stick_y);
            robot.TR.setPower(gamepad1.right_stick_y);

            if(gamepad1.left_trigger > 0){
                intakeMotor.setPower(gamepad1.left_trigger*0.5);
            } else if (gamepad1.right_trigger > 0){
                intakeMotor.setPower(gamepad1.right_trigger*0.5);
            } else {
                intakeMotor.setPower(0.0);
            }

            if(gamepad1.a){
                intakeServo.setDirection(DcMotorSimple.Direction.FORWARD);
                intakeServo.setPower(0.5);
            } else if (gamepad1.y){
                intakeServo.setDirection(DcMotorSimple.Direction.REVERSE);
                intakeServo.setPower(0.5);
            } else {
                intakeServo.setPower(0.0);
            }

        }
    }
}
