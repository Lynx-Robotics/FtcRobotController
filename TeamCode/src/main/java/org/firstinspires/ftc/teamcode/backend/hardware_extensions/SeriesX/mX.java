package org.firstinspires.ftc.teamcode.backend.hardware_extensions.SeriesX;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.control.low_level.PIDV2;

public class mX {
    // Raw Hardware
    private DcMotor motor;

    // Hardware
    private mX linkedMotor;

    // Conversions
    private double e2d_;

    // PID Controller
    private PIDV2 dPid;
    private double[] dPid_;
    private double dPidTolerance = 0.1;

    // State of Motor
    public double cD = 0; // current distance
    public double cE = 0; // current encoders
    public double lD = 0; // last distance
    public double lE = 0; // last encoders
    public double dSuccess = 0;

    // Responses
    private double dResponse = 0;
    private double eResponse = 0;

    public mX(DcMotor motor, double wRadius, double tpr, double[] dPid_){
        // Constructor Param are Init
        this.motor = motor;
        this.e2d_ = (2*3.14159*wRadius)/tpr;
        this.dPid_ = dPid_;

        dPid = new PIDV2(dPid_) {
            @Override
            public void perform(double response) {
                dResponse = response;
            }

            @Override
            public double getInputData() {
                updateDistance();
                return cD;
            }
        };
    }

    // surface level API's
    public double getDistance(){
        updateDistance();
        return cD;
    }

    public void goToPos(double pos, boolean thread_safe){
        // this method will displace the wheel by the amount given in the 'pos' param
        // this is achieved by adding it to our current position.
        double tPos = lD + pos; // tPos -> target position

        if (thread_safe){
            // note this is a thread locking loop
            dSuccess = 0;

            while (dSuccess < 100) {
                dPidIter(tPos); // handles sensor update and power direction to motor

                // perform success check
                if ((cD < (tPos + dPidTolerance)) && (cD > (tPos - dPidTolerance))){
                    dSuccess += 1;
                }
            }
        } else {
            // this is thread safe, but it is a single iteration
            dPidIter(tPos); // handles sensor update and power direction to motor

            // perform success check
            if ((cD < (tPos + dPidTolerance)) && (cD > (tPos - dPidTolerance))){
                dSuccess += 1;
            }
        }

    }

    public void goToPos(double pos, boolean thread_safe, Telemetry telem){
        // this method will displace the wheel by the amount given in the 'pos' param
        // this is achieved by adding it to our current position.
        double tPos = lD + pos; // tPos -> target position

        telem.addData("TPos", tPos);
        telem.update();

        if (thread_safe){
            // note this is a thread locking loop
            dSuccess = 0;

            while (dSuccess < 100) {
                dPidIter(tPos); // handles sensor update and power direction to motor

                // perform success check
                if ((cD < (tPos + dPidTolerance)) && (cD > (tPos - dPidTolerance))){
                    dSuccess += 1;
                }
            }
        } else {
            // this is thread safe, but it is a single iteration
            dPidIter(tPos); // handles sensor update and power direction to motor

            // perform success check
            if ((cD < (tPos + dPidTolerance)) && (cD > (tPos - dPidTolerance))){
                dSuccess += 1;
            }
        }

    }

    public void reset(){
        // makes the motor values relative to zero
        lD = cD;
        lE = cE;
    }

    public void powerWithExternal(){
        setPower(eResponse);
    }

    // standard motor methods
    public void setPower(double power){
        // this is a gateway method for normal setPower function of motor
        motor.setPower(power);
        if (linkedMotor != null) {
            linkedMotor.setPower(power);
        }
    }

    public int getCurrentPosition(){
        // retrieves current encoder value for motor
        return motor.getCurrentPosition();
    }

    public void linkToMotor(mX motor){
        this.linkedMotor = motor;
    }

    public void addResponse(double eResponse){
        this.eResponse += eResponse;
    }

    // under the hood API's
    private void updateDistance(){
        cE = motor.getCurrentPosition();
        cD = e2d_ * cE;
    }

    private void dPidIter(double t){
        // singular iteration of pid loop (will power wheels with response)
        dPid.executePID(t); // the pid will handle updates for cD and cE

        // summing responses
        double sResponses = dResponse + eResponse;

        // power sent to the motor
        setPower(sResponses);

        // resets the external response once they've been used (eResponse must be called repeatedly)
        eResponse = 0;
    }


}
