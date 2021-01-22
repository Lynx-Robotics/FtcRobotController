package org.firstinspires.ftc.teamcode.frontend.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.backend.control.higher_level.dPID;
import org.firstinspires.ftc.teamcode.backend.control.higher_level.dPIDV2;
import org.firstinspires.ftc.teamcode.frontend.CONSTANTS;
import org.firstinspires.ftc.teamcode.middleend.bases.AutoBase;

@Autonomous(name = "ExampleAuto", group = "example")
public class ExampleAuto extends AutoBase {
    private dPIDV2 dPID_TL, dPID_TR;

    @Override
    public void codeToInit() {
        // motor linkage
        robot.BL.linkToMotor(robot.TL);
        robot.BR.linkToMotor(robot.TR);

        // TODO: 1/14/2021 Tune PID Parameters for motors
        double[] params = {0.1, 0.0, 0.0};

        dPID_TL = new dPIDV2(robot.TL, params);
        dPID_TR = new dPIDV2(robot.TR, params);
    }

    @Override
    public void codeToRun() {
        resetRuntime();
        // while success is less than 'x', execute PID
        while ((dPID_TR.getSuccessCount() < 500 || dPID_TL.getSuccessCount() < 500) && getRuntime() < 10000){
            // give PID target as 1.0 meter
            dPID_TR.execute(1.0);
            dPID_TL.execute(1.0);

            // get the PID response (because this is done after the execute call, we are sure it
            // is an updated response)
            double TLResponse = dPID_TL.getResponse();
            double TRResponse = dPID_TR.getResponse();

            // send data to the phone
            telemetry.addData("TLResponse", TLResponse);
            telemetry.addData("TRResponse", TRResponse);
            telemetry.update();

            // set powers
            robot.TL.setPower(TLResponse, 0.5);
            robot.TR.setPower(TRResponse, 0.5);
        }
    }
}
