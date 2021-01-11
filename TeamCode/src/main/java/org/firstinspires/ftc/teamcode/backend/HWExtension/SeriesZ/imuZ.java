package org.firstinspires.ftc.teamcode.backend.HWExtension.SeriesZ;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class imuZ {
    // raw hardware
    BNO055IMU.Parameters parameters;
    BNO055IMU imu;

    // angle variables
    private Orientation lAngle = new Orientation();
    private double gAngle;

    public imuZ(BNO055IMU imu){
        // Basic parameters for the imu
        parameters = new BNO055IMU.Parameters();

        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;

        // imu initialized (assumes imu has already been init from hwMap)
        this.imu = imu;
        imu.initialize(parameters);
    }

    // surface level api's
    public boolean isCalib(){
        return imu.isGyroCalibrated();
    }

    public double updateAngle(){
        gAngle = getAngle(imu);
        return gAngle;
    }

    public void resetAngle(){
        lAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX,
                AngleUnit.DEGREES);
        gAngle = 0;
    }

    // under the hood api's
    private double getAngle(BNO055IMU imu) {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX,
                AngleUnit.DEGREES);
        double deltaAngle = angles.firstAngle - lAngle.firstAngle;
        if (deltaAngle < -180) {
            deltaAngle += 360;
        } else if (deltaAngle > 180) {
            deltaAngle -= 360;
        }
        gAngle += deltaAngle;
        lAngle = angles;
        return gAngle;
    }
}
