package org.firstinspires.ftc.teamcode.backend.control.higher_level;

import org.firstinspires.ftc.teamcode.backend.HWExtension.SeriesZ.mZ;
import org.firstinspires.ftc.teamcode.backend.control.low_level.PID.PID;

public class dPIDV2 extends PID {
    // motor to use
    private mZ motor;

    // pid vars
    private PID pid;

    // success parameters (tolerance should be in percent)
    private double successTolerance = 0.005;
    private int successCount = 0;

    // var for accessing the response
    private double response = 0;

    public dPIDV2(mZ motor, double kp, double ki, double kd) {
        super(kp, ki, kd);
        this.motor = motor;
    }

    public dPIDV2(mZ motor, double[] params) {
        super(params);
        this.motor = motor;
    }

    @Override
    public void perform(double response) {
        setResponse(response);
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

    // relevant methods
    private void incrementSuccessCount() {
        successCount += 1;
    }

    public void execute(double target){
        // this is an iterative based function (should be called until the success criteria is met)
        pid.executePID(target);
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

    public void setResponse(double response){
        this.response = response;
    }

    public double getResponse(){
        return this.response;
    }
}
