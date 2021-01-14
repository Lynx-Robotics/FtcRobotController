package org.firstinspires.ftc.teamcode.middleend.HardwareMappings;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.backend.HWExtension.SeriesZ.mZ;
import org.firstinspires.ftc.teamcode.frontend.CONSTANTS;

public class HWMap {
    private HardwareMap hwmap;

    // primitive motor instances
    private DcMotor TL_, TR_, BL_, BR_, intakeMotor_;
    public CRServo crServo;

    // extended motor instances
    public mZ TL, TR, BL, BR, intakeMotor;

    public void init(HardwareMap ahwmap){
        this.hwmap = ahwmap;

        // Drive Motor Instantiation
        TL_ = hwmap.get(DcMotor.class, "TL");
        TR_ = hwmap.get(DcMotor.class, "TR");
        BL_ = hwmap.get(DcMotor.class, "BL");
        BR_ = hwmap.get(DcMotor.class, "BR");
        intakeMotor_ = hwmap.get(DcMotor.class, "intakeMotor");

        TL = new mZ(TL_, CONSTANTS.wheel_radius_meters_SMALL_OMNI, CONSTANTS.encoder_count_per_rev_REV_ENCODERS);
        TL.reverseDirection();

        BL = new mZ(BL_, CONSTANTS.wheel_radius_meters_MECANUM, CONSTANTS.encoder_count_per_rev_REV);
        BL.reverseDirection();

        BR = new mZ(BR_, CONSTANTS.wheel_radius_meters_MECANUM, CONSTANTS.encoder_count_per_rev_REV);
        TR = new mZ(TR_, CONSTANTS.wheel_radius_meters_MECANUM, CONSTANTS.encoder_count_per_rev_REV);

        intakeMotor = new mZ(intakeMotor_);

        TL.hardReset();
        TR.hardReset();
        BL.hardReset();
        BR.hardReset();

        crServo = hwmap.get(CRServo.class, "intakeServo");
        crServo.setDirection(DcMotorSimple.Direction.FORWARD);
    }
}
