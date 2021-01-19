package org.firstinspires.ftc.teamcode.workinprogress;

public class AccelerationCurve //curve accelerates to a max value, holds it for a duration, then smoothly drops back down
{
    public double targetVoltage;    //max output of curve
    public double accelDuration;    //duration of smooth acceleration curves at beginning and end of curve
    public double topSpeedDuration; //duration for max output to be held for

    public AccelerationCurve(double maxVoltage, double accelDuration, double topSpeedDuration)  //just a constructor
    {
        this.targetVoltage = targetVoltage;
        this.accelDuration = accelDuration;
        this.topSpeedDuration = topSpeedDuration;
    }

    public double GetVoltage(double elapsedTime)    //fancy function for getting current voltage of curve from elapsed time of curve
    {
        if (elapsedTime < accelDuration)
        {
            return Sigmoid(elapsedTime/accelDuration);  //if accelerating, give voltage using acceleration s curve
        }
        else if (elapsedTime < accelDuration+topSpeedDuration)
        {
            return targetVoltage;  //if at top speed, return max voltage
        }
        else if (elapsedTime < 2*accelDuration+topSpeedDuration)
        {
            return -Sigmoid((elapsedTime-topSpeedDuration-accelDuration)/accelDuration)+0.5;    //if decelerating, give voltage using deceleration s curve
        }
        return 0;   //dumb return statement cuz it made me put one
    }

    private double Sigmoid(double x)    //function for returning s curve value given x input 0 to 1
    {
        return targetVoltage*(0.5*Math.tanh((6*x)-3)+0.5);
    }
}