package org.firstinspires.ftc.teamcode.middleend;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.hardware_extensions.SeriesX.imuX;
import org.firstinspires.ftc.teamcode.backend.hardware_extensions.SeriesX.mX;


public class DriveTrain {
    /*
    provide basic drivetrain functionality for autonomous (and possibly teleop). Essentially, it
    acts as the API for the motors on the robot.
     */

    // drivetrain linked type
    public enum dTLinkType{
        LEFT_RIGHT_GENERAL,
        FOUR_WHEEL_DRIVE,
    }

    // hardware
    private mX[][] motors;
    private imuX imu;

    // hardware generalizations
    public dTLinkType linkType;

    // dT status variables
    int distTolerance = 150;

    // Telemetry
    private Telemetry telem;

    public Telemetry getTelem(){
        return telem;
    }

    public DriveTrain(mX[][] motors, imuX imu, dTLinkType dTLinkType){
        // constructor parameters initializations
        this.motors = motors;
        this.imu = imu;
        this.linkType = dTLinkType;

        // handle generalization
        linkMotors(linkType); // will properly link all motors
    }

    // surface level api's
    public void turn(double angle){
        // call imu iter function
        double eResopnse = imu.getImuResponse(angle);
        setPowerLeft(eResopnse);
        setPowerRight(-eResopnse);
    }

    public void drive(double dist){
        drive(dist, 0.000001);
    }

    public void drive(double dist, double angle){
        // drive method with target angle as zero
        int dSuccess = 0;
        for (int i = 0; i < motors.length; i++){
            motors[i][0].reset();
            motors[i][1].reset();
        }
        while (dSuccess < distTolerance){
            double tDSuccess = 0;
            double eResponse = imu.getImuResponse(angle);
            telem.addData("Current Angle", imu.updateAngle());
            tDSuccess += dPidLeft(dist, eResponse);
            tDSuccess += dPidRight(dist, -eResponse);
            dSuccess += tDSuccess/2.0;

            if(telem != null){
                telem.addData("dSuccess", dSuccess);
                telem.addData("Current Angle", imu.updateAngle());
                telem.addData("Distance", dist);
                telem.addData("Angle", angle);
                telem.addData("Angle Response", eResponse);
                telem.update();
            }
        }
    }

    private void setPowerLeft(double power){
        if(linkType == dTLinkType.LEFT_RIGHT_GENERAL){
            motors[0][0].setPower(power);
        } else {
            for (int i = 0; i < motors.length; i++){
                motors[i][0].setPower(power);
            }
        }
    }

    private void setPowerRight(double power){
        if(linkType == dTLinkType.LEFT_RIGHT_GENERAL){
            motors[0][1].setPower(power);
        } else {
            for (int i = 0; i < motors.length; i++){
                motors[i][1].setPower(power);
            }
        }
    }

    public void setPower(double power){
        setPowerLeft(power);
        setPowerRight(power);
    }

    public void addTelem(Telemetry telem){
        this.telem = telem;
    }

    // under the hood api's
    private void linkMotors(dTLinkType dTLinkType){
        if (dTLinkType == DriveTrain.dTLinkType.LEFT_RIGHT_GENERAL){
            // link TL to BL; TR to BR
            // left / right side link
            for (int r = 0; r < motors.length-1; r++){
                motors[r][0].linkToMotor(motors[r+1][0]);
                motors[r][1].linkToMotor(motors[r+1][1]);
            }
        }
    }

    private double dPidLeft(double target_distance, double eResponse){
        if(linkType == dTLinkType.LEFT_RIGHT_GENERAL){
            mX motor = motors[0][0];
            motor.addResponse(eResponse);
            motor.goToPos(target_distance, false);
            return motor.dSuccess;
        } else {
            double dSAvg = 0;
            for (int i = 0; i < motors.length; i++){
                mX motor = motors[i][0];
                motor.addResponse(eResponse);
                motor.goToPos(target_distance, false);
                dSAvg += motor.dSuccess;
            }
            return dSAvg / motors.length;
        }
    }

    private double dPidRight(double target_distance, double eResponse){
        if(linkType == dTLinkType.LEFT_RIGHT_GENERAL){
            mX motor = motors[0][1];
            motor.addResponse(eResponse);
            motor.goToPos(target_distance, false);
            return motor.dSuccess;
        } else {
            double dSAvg = 0;
            for (int i = 0; i < motors.length; i++){
                mX motor = motors[i][1];
                motor.addResponse(eResponse);
                motor.goToPos(target_distance, false);
                motor.dSuccess += dSAvg;
            }
            return dSAvg / motors.length;
        }
    }
}
