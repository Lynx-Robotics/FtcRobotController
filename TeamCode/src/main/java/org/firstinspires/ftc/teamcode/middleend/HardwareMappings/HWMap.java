package org.firstinspires.ftc.teamcode.middleend.HardwareMappings;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.backend.HWExtension.SeriesZ.mZ;
import org.firstinspires.ftc.teamcode.frontend.CONSTANTS;

public class HWMap {
    private HardwareMap hwmap;

    // primitive motor instances
    private DcMotor TL_, TR_, BL_, BR_;

    // extended motor instances
    public mZ TL, TR, BL, BR;

    public void init(HardwareMap ahwmap){
        this.hwmap = ahwmap;
        TL_ = hwmap.get(DcMotor.class, "TL");
        TR_ = hwmap.get(DcMotor.class, "TR");
        BL_ = hwmap.get(DcMotor.class, "BL");
        BR_ = hwmap.get(DcMotor.class, "BR");

        TL = new mZ(TL_, CONSTANTS.wheel_radius_meters_SMALL_OMNI, CONSTANTS.encoder_count_per_rev_REV_ENCODERS);

        TR = new mZ(TR_, CONSTANTS.wheel_radius_meters_MECANUM, CONSTANTS.encoder_count_per_rev_REV);
        TR.reverseDirection();

        BL = new mZ(BL_, CONSTANTS.wheel_radius_meters_MECANUM, CONSTANTS.encoder_count_per_rev_REV);

        BR = new mZ(BR_, CONSTANTS.wheel_radius_meters_MECANUM, CONSTANTS.encoder_count_per_rev_REV);
        BR.reverseDirection();

        TL.hardReset();
        TR.hardReset();
        BL.hardReset();
        BR.hardReset();
    }
}
