package org.firstinspires.ftc.teamcode.backend.HWExtension.SeriesZ;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import java.util.ArrayList;

public class mZ {
    // "Primitive" Hardware Object
    private DcMotor motor;

    // encoder to distance conversion constant
    private double e2d = 0;

    // State of Motor
    private double cD = 0; // current distance
    private double cE = 0; // current encoder count
    private double lD = 0; // last distance
    private double lE = 0; // last encoder count

    // additional hardware stuff
    private ArrayList<mZ> linkedMotors = new ArrayList<>();

    public mZ(DcMotor motor){
        // Default Constructor
        setMotor(motor);
    }

    public mZ(DcMotor motor, double wheelRadius, double tpr){
        // Constructor used for when the wheel radius is known
        this(motor);
        setEncoderToDistance(wheelRadius, tpr);
    }

    public double setEncoderToDistance(double wheelRad, double tpr){
        // returns the conversion factor and sets it to the variable as well
        /*
        wheelRad -> wheel radius
        tpr -> ticks per rotation
         */

        double tempE2D = (2 * 3.14159 * wheelRad)/(tpr);
        this.e2d = tempE2D;
        return tempE2D;
    }

    // returns the raw instance of the motor
    public DcMotor getMotor() {
        return motor;
    }

    // method used for linking to a motor
    public void linkToMotor(mZ motorToLinkTo){
        motorToLinkTo.addLinkedMotor(this);
    }

    // method used for adding a linked motor
    private void addLinkedMotor(mZ motor) {
        this.linkedMotors.add(motor);
    }

    // lower level set power method with power filtration
    public void setPower(double power, double min, double max){
        double filteredPower = Range.clip(power, min, max);
        this.motor.setPower(filteredPower);
        for(mZ m : linkedMotors){
            m.setPower(power);
        }
    }

    // setting the power with the same lower and upper bound
    public void setPower(double power, double cap){
        // (bound should be positive)
        setPower(power, (cap * -1), cap);
    }

    // standard power setting
    public void setPower(double power){
        setPower(power, -1, 1);
    }

    // sets the motor according to presets
    private void setMotor(DcMotor motor){
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.motor = motor;
    }

    // primitive motor methods
    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior){
        this.motor.setZeroPowerBehavior(zeroPowerBehavior);
    }

    public void setMode(DcMotor.RunMode mode){
        this.motor.setMode(mode);
    }

    public void setDirection(DcMotorSimple.Direction direction){
        this.motor.setDirection(direction);
    }

    // method used for getting change in distance (relative to our last distance)
    public double getCD(boolean relative) {
        if(relative){
            // relative refers to the distance relative to our
            // last recorded distance
            return getCD() - getLD();
        }

        // if not relative, return the raw value of our distance
        return cD;
    }

    // method used for getting change in encoder (relative)
    public double getCE(boolean relative) {
        cE = getMotor().getCurrentPosition();
        if(relative){
            // if relative return difference (i.e., change in encoders)
            return cE - lE;
        }
        return cE;
    }

    // internal method used for updating our current distance
    private void updateDistance(){
        // holds the values as temporary variables
        double encoderCountTemp = getCE();

        // actual distance calculation
        double tempDist = (getE2D() * encoderCountTemp);

        // sets the values
        setCD(tempDist);
    }

    // hard reset of primitive motor
    public void hardReset(){
        // resets the motor information (regarding distance) in both the mZ
        // class and the primitive motor
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setCE(0);
        setCD(0);
        softReset();
    }

    // soft reset of superficial motor
    public void softReset(){
        // soft reset means making the distance calculations relative to
        // our current one (making it zero, but artificially)
        setLD(getCD());
        setLE(getCE());
    }

    // returns the encoder to distance ratio
    public double getE2D() {
        return e2d;
    }

    // returns the current distance (raw)
    public double getCD() {
        return getCD(false);
    }

    // returns the current encoder count
    public double getCE() {
        return getCE(false);
    }

    // sets the current distance
    private void setCD(double cD) {
        this.cD = cD;
    }

    // sets the current encoder count
    private void setCE(double cE) {
        this.cE = cE;
    }

    // returns the last distance
    public double getLD() {
        return lD;
    }

    // used for setting the last distance
    private void setLD(double lD) {
        this.lD = lD;
    }

    // used for setting the last encoder
    public double getLE() {
        return lE;
    }

    // used for setting the last encoder
    private void setLE(double lE) {
        this.lE = lE;
    }

    // reverse direction
    public void reverseDirection(){
        DcMotorSimple.Direction current_dir = this.motor.getDirection();
        if(current_dir == DcMotorSimple.Direction.FORWARD){
            getMotor().setDirection(DcMotorSimple.Direction.REVERSE);
        } else if (current_dir == DcMotorSimple.Direction.REVERSE){
            getMotor().setDirection(DcMotorSimple.Direction.FORWARD);
        }
    }
}
