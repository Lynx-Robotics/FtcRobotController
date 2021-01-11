package org.firstinspires.ftc.teamcode.backend.control.higher_level;

import org.firstinspires.ftc.teamcode.backend.HWExtension.SeriesZ.mZ;
import org.firstinspires.ftc.teamcode.backend.control.low_level.PID;

class dPID {
    // motor to use
    private mZ motor;

    // pid vars
    private PID pid;

    // success parameters (tolerance should be in percent)
    private double successTolerance = 0.005;
    private int successCount = 0;
    private final int STND_SUCCESS_CAP = 500;

    public dPID(mZ motor, double[] pidParams){
        setMotor(motor);
        pid = new PID(pidParams) {
            @Override
            public void perform(double response) {
                getMotor().setPower(response);
            }

            @Override
            public double getInputData() {
                double dist = getMotor().getCD();

                // success evaluation
                if(isWithin(dist, successTolerance)){
                    incrementSuccessCount();
                } else {
                    setSuccessCount(0);
                }
                return dist;
            }
        };
    }

    public dPID(mZ motor, double[] pidParams, double successTolerance){
        setSuccessTolerance(successTolerance);
        setMotor(motor);
        pid = new PID(pidParams) {
            @Override
            public void perform(double response) {
                getMotor().setPower(response);
            }

            @Override
            public double getInputData() {
                double dist = getMotor().getCD();

                // success evaluation
                if(isWithin(dist, getSuccessTolerance())){
                    incrementSuccessCount();
                } else {
                    setSuccessCount(0);
                }
                return dist;
            }
        };
    }

    // relevant methods
    private void incrementSuccessCount() {
        successCount += 1;
    }



    public void goToPos(double target, int successCap){
        // this is an iterative based function (should be called until the success criteria is met)
        successCount = 0;
        while(successCount < successCap){
            pid.executePID(target);
        }
        getMotor().setPower(0);
    }

    public void goToPos(double target){
        // this is an iterative based function (should be called until the success criteria is met)
        successCount = 0;
        while(successCount < STND_SUCCESS_CAP){
            pid.executePID(target);
        }
        getMotor().setPower(0);
    }

    // misc methods
    public static boolean isWithin(double num, double min, double max){
        return (num >= min) && (num <= max);
    }

    public static boolean isWithin(double num, double percent){
        double tolerance = num * percent;
        return isWithin(num, (num-tolerance), (num+tolerance));
    }

    // getters and setters
    public void setMotor(mZ motor) {
        this.motor = motor;
    }

    public mZ getMotor() {
        return motor;
    }

    public void setSuccessTolerance(double successTolerance) {
        this.successTolerance = successTolerance;
    }

    public double getSuccessTolerance() {
        return successTolerance;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }
}
