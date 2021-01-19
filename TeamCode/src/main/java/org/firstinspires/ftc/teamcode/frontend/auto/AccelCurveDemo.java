package org.firstinspires.ftc.teamcode.frontend.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.backend.control.higher_level.dPIDV2;
import org.firstinspires.ftc.teamcode.middleend.bases.AutoBase;
import org.firstinspires.ftc.teamcode.workinprogress.AccelerationCurve;

@Autonomous(name = "ExampleAuto", group = "example")
public class AccelCurveDemo extends AutoBase {
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
        AccelerationCurve accelCurve = new AccelerationCurve(0.5,1,2);
        while ((dPID_TR.getSuccessCount() < 500 && dPID_TL.getSuccessCount() < 500) && getRuntime() < 3000){
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
            robot.TL.setPower(TLResponse, accelCurve.GetVoltage(getRuntime()/1000));
            robot.TR.setPower(TRResponse, accelCurve.GetVoltage(getRuntime()/1000));
        }
    }


}
