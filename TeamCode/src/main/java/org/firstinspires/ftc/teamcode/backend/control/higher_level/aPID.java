package org.firstinspires.ftc.teamcode.backend.control.higher_level;

import org.firstinspires.ftc.teamcode.backend.HWExtension.SeriesZ.imuZ;
import org.firstinspires.ftc.teamcode.backend.control.low_level.PID;

class aPID {
    // hardware stuff
    private imuZ imu;

    public aPID(imuZ imu) {
        this.imu = imu;
    }
}
