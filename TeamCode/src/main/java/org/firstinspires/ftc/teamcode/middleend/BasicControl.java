package org.firstinspires.ftc.teamcode.middleend;

import org.firstinspires.ftc.teamcode.backend.control.low_level.PIDV2;
import org.firstinspires.ftc.teamcode.frontend.CONSTANTS;
import org.firstinspires.ftc.teamcode.middleend.bases.AutoBase3;

public class BasicControl extends AutoBase3 {
    private double external_response = 0;

    private PIDV2 distancePID = new PIDV2(CONSTANTS.dPid_) {
        @Override
        public void perform(double response) {
            robot.TR.setPower(response);
            robot.TR.addResponse(external_response);

            robot.BR.setPower(response);
            robot.BR.addResponse(external_response);

            robot.BL.setPower(response);
            robot.BL.addResponse(external_response);

            robot.TL.setPower(response);
            robot.TL.addResponse(external_response);
        }

        @Override
        public double getInputData() {
            return getEncoderAvg();
        }
    };


    public BasicControl() {

    }

    @Override
    public void autoCode() {
        driveToPos(1.3, 0);
    }

    private void driveToPos(double distance, double angle){
        // convert distance to encoder
        int encoder_count = (int)Math.floor(distance*(1.0/robot.BL.getE2d_()));

        // calculate error tolerance
        double tolerance = encoder_count*0.05;

        // get the target distance (distance we want to go  + current distance)
        int current_encoder_count = getEncoderAvg();
        int target = encoder_count + current_encoder_count;
        // execute the pid loop for our given distance

        while(!(getEncoderAvg() > (distance-tolerance) && getEncoderAvg() < (distance + tolerance))){
            setExternalResponse(robot.imu.getImuResponse(angle));
            distancePID.executePID(target);
        }
        setExternalResponse(0);
    }

    // averages the encoder counts for all the motors (should only be used for driving straight)
    private int getEncoderAvg(){
        return (int)Math.floor((robot.TR.getCurrentPosition() + robot.BR.getCurrentPosition()
                + robot.TL.getCurrentPosition() + robot.BL.getCurrentPosition())/4.0);
    }

    // external response setting and getting
    public void setExternalResponse(double external_response) {
        this.external_response = external_response;
    }

    public double getExternalResponse() {
        return external_response;
    }
}
