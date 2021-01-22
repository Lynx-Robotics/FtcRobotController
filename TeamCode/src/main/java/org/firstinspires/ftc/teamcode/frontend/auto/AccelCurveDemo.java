package org.firstinspires.ftc.teamcode.frontend.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.control.higher_level.dPIDV2;
import org.firstinspires.ftc.teamcode.middleend.bases.AutoBase;
import org.firstinspires.ftc.teamcode.workinprogress.AccelerationCurve;

@Autonomous(name = "AccelerationTester", group = "example")
public class AccelCurveDemo extends AutoBase {
    private dPIDV2 dPID_TL, dPID_TR;

    @Override
    public void codeToInit() {
        // motor linkage
//        robot.BL.linkToMotor(robot.TL);
//        robot.BR.linkToMotor(robot.TR);

        // TODO: 1/14/2021 Tune PID Parameters for motors
        double[] params = {0.4, 0.0, 0.0};

        dPID_TL = new dPIDV2(robot.TL, params);
        dPID_TR = new dPIDV2(robot.TR, params);
    }

    @Override
    public void codeToRun() {
        resetRuntime();
        // while success is less than 'x', execute PID
        AccelerationCurve accelCurve = new AccelerationCurve(0.5,3,2);
        double lastTime = getRuntime()-15;
        double accConst =  accelCurve.GetVoltage(getRuntime()/1000);
        while (getRuntime() < 10000){

            if(getRuntime() - lastTime > 250){
                accConst = accelCurve.GetVoltage(getRuntime()/1000);
                lastTime = getRuntime();
            }

            getTelem().addData("Voltage", robot.TL.getMotor().getPower());
            getTelem().addData("Velocity", accConst);
            getTelem().update();

            // set powers
            robot.TL.setPower(0.5, accConst);
            robot.TR.setPower(0.5, accConst);
            robot.BL.setPower(0.5, accConst);
            robot.BR.setPower(0.5, accConst);

        }
    }


}
