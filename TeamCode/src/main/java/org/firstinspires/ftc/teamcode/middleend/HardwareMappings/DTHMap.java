package org.firstinspires.ftc.teamcode.middleend.HardwareMappings;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.hardware_extensions.SeriesX.imuX;
import org.firstinspires.ftc.teamcode.backend.hardware_extensions.SeriesX.mX;
import org.firstinspires.ftc.teamcode.backend.robot_abstractions.DriveTrain;
import org.firstinspires.ftc.teamcode.frontend.CONSTANTS;

public class DTHMap {
    // raw hardware stuff
    private HardwareMap hwmap;
    private DcMotor TL_, TR_, BL_, BR_;
    private BNO055IMU imu_;

    // extended hardware stuff
    public mX TL, TR, BL, BR;
    public imuX imu;

    // surface-level abstraction stuff
    private mX[][] motors;
    public org.firstinspires.ftc.teamcode.backend.robot_abstractions.DriveTrain.DriveTrain dT;

    public void init(HardwareMap aHwmap, Telemetry telem){
        hwmap = aHwmap;

        // declare the raw hardware stuff
        TL_ = hwmap.get(DcMotor.class, "TL");
        TR_ = hwmap.get(DcMotor.class, "TR");
        BL_ = hwmap.get(DcMotor.class, "BL");
        BR_ = hwmap.get(DcMotor.class, "BR");

        TL_.setDirection(DcMotorSimple.Direction.REVERSE);
        BL_.setDirection(DcMotorSimple.Direction.REVERSE);

        resetEncoders();

        // attach raw stuff to their hardware extensions
        TL = new mX(TL_, CONSTANTS.wheel_radius_meters_SMALL_OMNI, CONSTANTS.encoder_count_per_rev_REV_ENCODERS, CONSTANTS.dPid_);
        BL = new mX(BL_, CONSTANTS.wheel_radius_meters_MECANUM, CONSTANTS.encoder_count_per_rev_REV, CONSTANTS.dPid_);
        TR = new mX(TR_, CONSTANTS.wheel_radius_meters_MECANUM, CONSTANTS.encoder_count_per_rev_REV, CONSTANTS.dPid_);
        BR = new mX(BR_, CONSTANTS.wheel_radius_meters_SMALL_OMNI, CONSTANTS.encoder_count_per_rev_REV_ENCODERS, CONSTANTS.dPid_);

        // 2D Configuration of drive train motors
        motors =  new mX[][]{
                {TL, BR}, // BR is placed in front because of some coding that I did (should be fixed later)
                {BL, TR},
        };

        // imu extensions
        imu_ = hwmap.get(BNO055IMU.class, "imu");
        imu = new imuX(imu_);

        // drivetrain construction
        dT = new org.firstinspires.ftc.teamcode.backend.robot_abstractions.DriveTrain.DriveTrain(motors, imu, DriveTrain.DriveTrain.dTLinkType.LEFT_RIGHT_GENERAL);

    }

    public void resetEncoders(){
        TL_.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        TL_.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        TR_.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        TR_.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL_.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL_.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR_.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR_.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
