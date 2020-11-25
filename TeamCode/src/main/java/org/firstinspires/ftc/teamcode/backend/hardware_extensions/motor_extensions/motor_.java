package org.firstinspires.ftc.teamcode.backend.hardware_extensions.motor_extensions;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.backend.control.low_level.PIDV2;
import org.firstinspires.ftc.teamcode.frontend.CONSTANTS;

public class motor_ {

    public enum motor_usage{
        drive_motor,
        pseudo_motor,
        through_bore_encoder
    }

    public double wheel_rad, tpr;
    public motor_usage cMotorType;

    private motor_ lMotor = null;
    public DcMotor motor = null;

    private int current_encoder_count;
    private double current_distance;

    private double dResponse;

    // Constants
    private double e2dC;
    private double d2eC;

    private double e2eC = 1;

    // PID Stuff
    PIDV2 dPid = new PIDV2(CONSTANTS.dPidREV_PLANETARY) {
        @Override
        public void perform(double response) {
            dResponse = response;
        }

        @Override
        public double getInputData() {
            return getDistance();
        }
    };

    public motor_(DcMotor m, double wheel_rad, double tpr, motor_usage cMotorType){
        this.wheel_rad = wheel_rad;
        this.motor = m;
        this.tpr = tpr;
        this.cMotorType = cMotorType;

        this.e2dC = (2*3.1459*wheel_rad)/tpr;
        this.d2eC = 1/e2dC;
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

    public void NoLoopGoToPos(double dist){
        // This does not operate in a loop! You must run this continually for it to operate
        // as it is designed.

        // the method assumes that the distance is a relative number to our current position (i.e,
        // that if you put 8.0m as your distance, you are expecting it go 8.0m forward regardless
        // of its position on the field).
        dPid.executePID((dist));
    }

    public void resetInfo(){
        this.current_distance = 0;
        this.current_encoder_count = 0;
        this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setPower(double p){
        motor.setPower(p);
    }

    public double getdResponse() {
        return dResponse;
    }
}
