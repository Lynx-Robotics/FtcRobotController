package org.firstinspires.ftc.teamcode.backend.hardware_extensions.motor_extensions;

import com.qualcomm.robotcore.hardware.DcMotor;

public class motor_ {

    public enum motor_usage{
        drive_motor,
        pseudo_motor,
        through_bore_encoder
    }

    public enum distance_units{
        M,
        CM,
        IN,
        MM,
        FT
    }

    public double wheel_rad, tpr;
    public motor_usage cMotorType;
    private distance_units chosen_unit;

    private motor_ lMotor = null;
    public DcMotor motor = null;

    private int current_encoder_count;
    private double current_distance;

    // Constants
    private double e2dC = (2*3.1459*wheel_rad)/tpr;
    private double d2eC = 1/e2dC;

    private double e2eC = 1;

    public motor_(DcMotor m, double wheel_rad, double tpr, motor_usage cMotorType, distance_units chosen_unit){
        this.wheel_rad = wheel_rad;
        this.motor = m;
        this.tpr = tpr;
        this.cMotorType = cMotorType;
        this.chosen_unit = chosen_unit;
    }

    public void linkToMotor(motor_ lMotor){
        this.lMotor = lMotor;
        e2eC = (lMotor.wheel_rad*this.tpr)/
                (this.wheel_rad*lMotor.tpr);

    }

    public int updateEncoderCount(){
        // Updates the current known encoder count of the motor
        current_encoder_count = motor.getCurrentPosition();
        return current_encoder_count;
    }

    public double getDistance(){
        // Returns distance traversed in chosen unit
        updateEncoderCount();
        current_distance = encoder_to_dist(current_encoder_count);
        return current_distance;
    }

    public double encoder_to_dist(int e){
        // Returns the encoder ticks in distance (same distance as wheels)
        return e2dC * e;
    }

    public int distance_to_enc(double dist){
        // returns the encoder ticks for a given distance
        return (int)(d2eC * dist);
    }

    public void setPower(double p){
        motor.setPower(p);
    }

}
