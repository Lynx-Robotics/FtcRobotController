package org.firstinspires.ftc.teamcode.backend.control.low_level;

public abstract class PIDBase {
    // pid vars
    private PID pid;
    private double response = 0.0;

    // success parameters (tolerance should be in percent)
    private double successTolerance = 0.005;
    private int successCount = 0;
    private final int STND_SUCCESS_CAP = 500;

    public PIDBase(PID pid, double successTolerance) {
        this.successTolerance = successTolerance;
        setPid(pid);
    }

    public PIDBase(PID pid) {
        this(pid, 0.005);
    }

    // relevant methods
    public void incrementSuccessCount() {
        successCount += 1;
    }

    public void updateResponse(double response) {
        this.response = response;
    }

    public double getResponse(){
        return this.response;
    }


    public PID getPid() {
        return pid;
    }

    public int getSTND_SUCCESS_CAP() {
        return STND_SUCCESS_CAP;
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

    public void setPid(PID pid) {
        this.pid = pid;
    }
}
