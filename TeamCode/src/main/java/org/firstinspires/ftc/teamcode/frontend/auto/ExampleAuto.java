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

        dPID_TL = new dPIDV2(robot.TL, CONSTANTS.dPid_);
        dPID_TR = new dPIDV2(robot.TR, CONSTANTS.dPid_);
    }

    @Override
    public void codeToRun() {
        while (dPID_TR.getSuccessCount() < 500 && dPID_TL.getSuccessCount() < 500){
            dPID_TR.execute(1.0);
            dPID_TL.execute(1.0);

            double TLResponse = dPID_TL.getResponse();
            double TRResponse = dPID_TR.getResponse();

            telemetry.addData("TLResponse", TLResponse);
            telemetry.addData("TRResponse", TRResponse);
            telemetry.update();

            robot.TL.setPower(TLResponse);
            robot.TR.setPower(TRResponse);
        }
    }


}
