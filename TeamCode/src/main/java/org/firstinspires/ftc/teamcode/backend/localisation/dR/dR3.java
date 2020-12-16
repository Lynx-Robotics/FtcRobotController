package org.firstinspires.ftc.teamcode.backend.localisation.dR;

import org.firstinspires.ftc.teamcode.backend.hardware_extensions.SeriesX.imuX;
import org.firstinspires.ftc.teamcode.backend.hardware_extensions.SeriesX.mX;
import org.firstinspires.ftc.teamcode.middleend.DriveTrain;

public class dR3 {
    // Raw mX implementation
    private mX[][] motors;
    private mX[] leftMotors, rightMotors;

    private imuX imu;

    // DriveTrain Implementation
    private DriveTrain dt;

    private double current_total_dist = 0;
    private double current_angle = 0;

    private double x = 0, y = 0;

    public dR3(mX[][] motors, imuX imu){
        // motors is set
        this.motors = motors;

        // motors are parsed into corresponding arrays
        parseMotors();

        // imu is set
        this.imu = imu;
    }

    public dR3(DriveTrain dt){
        this.dt = dt;
    }

    public void move(double tx, double ty){
        double target_angle = calcTargAngle(tx, ty);
        double target_dist = calcTargDist(tx, ty);

        dt.getTelem().addData("moving", Double.toString(target_angle) + ", "+ Double.toString(target_dist));
        dt.getTelem().update();

        dt.drive(target_dist, target_angle);

        this.x = tx;
        this.y = ty;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    private double calcTargAngle(double tX, double tY){
        double ratio;
        if (tY != 0){
            ratio = (tY - y)/(tX - x);
        } else {
            ratio = 0.00000000000000000001;
        }

        double angle = Math.atan(ratio);
        return Math.toDegrees(angle);
    }

    private double calcTargDist(double tX, double tY){
        double sqrdSum = ((tX - x)*(tX - x)) + ((tY - y)*(tY - y));
        return Math.sqrt(sqrdSum);
    }

    private double updateAngle(){
        current_angle = imu.updateAngle();
        return current_angle;
    }

    private double updateDist(){
        double lD = getDist(leftMotors);
        double rD = getDist(rightMotors);

        current_total_dist = ((lD + rD)/2.0);
        return current_total_dist;
    }

    private double getDist(mX[] mArray){
        double d = 0;
        for (mX m : mArray){
            d += m.getDistance();
        }
        return (d/mArray.length);
    }

    public void parseMotors(){
        mX[] tLeft = new mX[motors.length];
        mX[] tRight = new mX[motors.length];

        for (int i = 0; i<motors.length; i++){
            tLeft[i] = motors[i][0];
            tRight[i] = motors[i][1];
        }

        leftMotors = tLeft;
        rightMotors = tRight;
    }

}
