package org.firstinspires.ftc.teamcode.backend.HWExtension.SeriesX;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.backend.control.low_level.PID.PID;
import org.firstinspires.ftc.teamcode.frontend.CONSTANTS;

public class imuX {
    // raw hardware
    BNO055IMU.Parameters parameters;
    BNO055IMU imu;

    // angle variables
    private Orientation lAngle = new Orientation();
    private double gAngle;

    // angle pid
    private PID imuPid;
    private double imuResponse;

    // debug
    private Telemetry telem;


    public imuX(BNO055IMU imu){
        // Basic parameters for the imu
        parameters = new BNO055IMU.Parameters();

        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;

        // imu initialized (assumes imu has already been init from hwMap)
        this.imu = imu;
        imu.initialize(parameters);

        // initialize pid parameters
        imuPid = new PID(CONSTANTS.imuPid_) {
            @Override
            public void perform(double response) {
                imuResponse = response;
            }

            @Override
            public double getInputData() {
                return updateAngle();
            }
        };
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

    public void setTelem(Telemetry telem){
        this.telem = telem;
    }

    public Telemetry getTelem(){
        return this.telem;
    }

    public double getImuResponse(double target_angle) {
        double angle;
        if(target_angle == 0){
            angle = 0.000000000000000001;
        } else {
            angle = target_angle;
        }
        imu_iter(angle);
        return imuResponse;
    }

    public double getImuResponse(){
        return imuResponse;
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

    private void imu_iter(double t){
        imuPid.executePID(t); // t -> target angle (updates are handled in getInput() func)
    }



}
