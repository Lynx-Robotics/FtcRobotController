package org.firstinspires.ftc.teamcode.backend.localisation.dR;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.backend.control.low_level.PIDV2;
import org.firstinspires.ftc.teamcode.backend.hardware_extensions.motor_extensions.motor_;
import org.firstinspires.ftc.teamcode.frontend.CONSTANTS;

import java.util.Locale;

public class DeadReckoning2 {
    // Related to position
    private double px = 0, py = 0;
    private double distance_travelled = 0;
    private int success = 0;
    private int success_max = 150;

    // Related to motor logic
    private motor_[][] motorS;

    // Related to angle (IMU)
    private BNO055IMU[] imu_list;
    private double imu_global_angle;
    private Orientation lastAngles = new Orientation();

    // Related to overall response
    private double imu_response;
    private double distance_response;

    // Additional stuff
    private Telemetry telem;

    PIDV2 imu_pid = new PIDV2(CONSTANTS.DeadRImuPid_) {
        @Override
        public void perform(double response) {
            imu_response = response;
        }

        @Override
        public double getInputData() {
            return imu_global_angle;
        }
    };

    PIDV2 dist_pid = new PIDV2(CONSTANTS.DeadRDPid_) {
        @Override
        public void perform(double response) {
            distance_response = response;
        }

        @Override
        public double getInputData() {
            return distance_travelled;
        }
    };

    // Constructor Code
    public DeadReckoning2(motor_[][] motorS, BNO055IMU[] imus, Telemetry telem){
        this.motorS = motorS;
        this.imu_list = imus;
        this.telem = telem;
    }

    // Overall Position Methods
    public void move(double tx, double ty){
        double dY = ty - py;
        double dX = tx - px;

        double target_angle = Math.toDegrees(Math.atan((dY/dX)));
        double target_distance = Math.sqrt(((dX*dX) + (dY*dY)));

        // some logic to handle direction
        if(py > ty){
            target_distance = -target_distance;
        } else if (px > tx){
            target_distance = -target_distance;
        }

        int dist_success = 0, imu_success = 0;

        if(!Double.isNaN(distance_travelled)){
            while ((dist_success < success_max) ){
                // Update information
                updateIMU();
                updateDistance();

                // execute PID controller
                imu_pid.executePID(target_angle);
                dist_pid.executePID(target_distance);

                // Use response data from the PID
                powerLeft(distance_response - imu_response);
                powerRight(distance_response + imu_response);

                // Telemetry Update
                telem.addData("Target Angle : ", target_angle);
                telem.addData("Current Angle: ", imu_global_angle);
                telem.addData("Angle Counter: ", imu_success);
                telem.addData("---","--");
                telem.addData("Target Distance : ", target_distance);
                telem.addData("Current Distance: ", distance_travelled);
                telem.addData("Distance Counter: ", dist_success);
                telem.addData("---","--");
                telem.addData("current (x, y): ", String.format(Locale.ENGLISH,"%f, %f", px, py));
                telem.update();

                if (isWithin(target_angle-0.05, target_angle+0.05, imu_global_angle)){
                    dist_success += 1;
                }

                if (isWithin(target_distance-0.05, target_distance+0.05, distance_travelled)){
                    dist_success += 1;
                }
            }
        }

        // Update Position Info
        px = tx;
        py = ty;

//        resetImu();
        resetMotorInformation();
    }

    private void updateSuccess(double objToCheck){
        if (isWithin(0.0, 0.005, objToCheck, 9)){
            success += 1;
        } else {
            success = 0;
        }
    }

    private boolean isWithin(double min, double max, double val){
        return (val < max) && (val > min);
    }

    private boolean isWithin(double targ, double tolerance, double val, int meaningless){
        return isWithin((targ-tolerance), (targ+tolerance), val, 0);
    }

    // Encoder Position Related Methods
    private double leftDistance(){
        return getDistanceFromOneSideOfRobot(0);
    }

    private double rightDistance(){
        return getDistanceFromOneSideOfRobot(1);
    }

    private void updateDistance(){
        double dist = leftDistance() + rightDistance()/2.0;
        distance_travelled = dist;
    }

    private double getDistanceFromOneSideOfRobot(int col){
        double reported_dist = 0;
        for (int i = 0; i<motorS.length; i++){
            // Iterates through the rows of the 2D array of motors
            // The first row is the left motors
            motor_ cm = motorS[i][col];
            reported_dist += cm.getDistance();
        }

        return reported_dist/motorS.length;
    }

    // IMU Related Methods
    public void updateIMU(){
        for (BNO055IMU imu : imu_list){
            imu_global_angle = updateImuGlobalAngle(imu);
        }
    }


    private double updateImuGlobalAngle(BNO055IMU imu){
        return getAngle(imu);
    }

    private double getAngle(BNO055IMU imu) {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX,
                AngleUnit.DEGREES);
        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;
        if (deltaAngle < -180) {
            deltaAngle += 360;
        } else if (deltaAngle > 180) {
            deltaAngle -= 360;
        }
        imu_global_angle += deltaAngle;
        lastAngles = angles;
        return imu_global_angle;
    }

    public void resetImu(){
        resetImuAngle(imu_list[0]);
    }

    private void resetImuAngle(BNO055IMU imu){
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX,
                AngleUnit.DEGREES);
        imu_global_angle = 0;
    }

    // Additional Methods
    private double convert_to_radians(double degree){
        return degree*0.0174533;
    }

    public boolean stopCondition(){
        return false;
//        return success > success_max;
    }

    private void powerLeft(double p){
        for (int i = 0; i < motorS.length; i++){
            motor_ cm = motorS[i][0];
            cm.setPower(p);
        }
    }

    private void powerRight(double p){
        for (int i = 0; i < motorS.length; i++){
            motor_ cm = motorS[i][1];
            cm.setPower(p);
        }
    }

    private void resetDistance(){
        this.distance_travelled = 0;
    }

    private void resetMotorInformation(){
        for (motor_[] mS : motorS){
            for (motor_ m : mS){
                m.resetInfo();
            }
        }
    }

}
