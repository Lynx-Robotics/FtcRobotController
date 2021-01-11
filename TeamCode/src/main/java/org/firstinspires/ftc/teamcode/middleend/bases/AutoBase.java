package org.firstinspires.ftc.teamcode.middleend.bases;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.middleend.HardwareMappings.HWMap;

public abstract class AutoBase extends LinearOpMode {
    public HWMap robot;

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

}
