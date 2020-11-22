package org.firstinspires.ftc.teamcode.middleend.bases;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.backend.hardware_extensions.motor_extensions.motor_;
import org.firstinspires.ftc.teamcode.backend.localisation.dR.DeadReckoning;
import org.firstinspires.ftc.teamcode.backend.localisation.dR.DeadReckoning2;
import org.firstinspires.ftc.teamcode.middleend.HardwareMappings.HMap;
import org.firstinspires.ftc.teamcode.middleend.HardwareMappings.dRRobot;

public abstract class AutoBase2 extends LinearOpMode {
    dRRobot robot = new dRRobot();
    public DeadReckoning2 dR;

    motor_[][] ms;

    BNO055IMU[] imus;

    public abstract void autoCode();

    @Override
    public void runOpMode() throws InterruptedException {

        // Initializing the hardware map
        robot.init(hardwareMap);

        ms = new motor_[][]{
                {robot.TL, robot.TR},
                {robot.BL, robot.BR}
        };

        imus = new BNO055IMU[]{robot.imu_};

        dR = new DeadReckoning2(
                ms, imus, telemetry
        );

        // Calibrating Sensors
        addTelemetryData("IMU Calibration: ",
                Boolean.toString(robot.imu.isGyroCalibrated()));
        calibrateIMU();
        addTelemetryData("IMU Calibration: ",
                Boolean.toString(robot.imu.isGyroCalibrated()));

        // Pre-Auto Calibration
        robot.imu.resetAngle();

        // End of Initialization
        waitForStart();

        autoCode();
    }

    private void addTelemetryData(String caption, String msg){
        telemetry.addData(caption, msg);
        telemetry.update();
    }

    private void calibrateIMU(){
        // Code for calibrating the imu
        while (!isStopRequested() && !robot.imu.isGyroCalibrated())
        {
            sleep(50);
            idle();
        }
    }

    public void drive(double[] coordinate){
        dR.move(coordinate[0], coordinate[1]);
    }
}
