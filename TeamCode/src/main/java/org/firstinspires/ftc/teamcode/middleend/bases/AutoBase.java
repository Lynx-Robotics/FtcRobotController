package org.firstinspires.ftc.teamcode.middleend.bases;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.middleend.HardwareMappings.HWMap;

public abstract class AutoBase extends LinearOpMode {
    public HWMap robot = new HWMap();
    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        codeToInit();
        waitForStart();
        codeToRun();
    }
    // code for initialization (not hardware but simply moving things places, etc).
    public abstract void codeToInit();

    // code that is executed (the actual code, some would say)
    public abstract void codeToRun();

    // code for getting the runtime (time of run?)
    public double getRuntime(){
        return runtime.milliseconds();
    }

    public void resetRuntime(){
        runtime.reset();
    }

    public Telemetry getTelem() {
        return telemetry;
    }



}
