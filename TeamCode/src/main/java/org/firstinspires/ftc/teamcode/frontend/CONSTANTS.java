package org.firstinspires.ftc.teamcode.frontend;

public class CONSTANTS {
    // PID Constants
    public final static double[] dPid_ = new double[]{0, 0, 0}; // distance pid values
    public final static double[] aPid_ = new double[]{0.000000504, 0.00000000002998311, 0.000000000000702983749}; // acceleration pid values
    public final static double[] vPid_ = new double[]{.0000274, 0.0000000004643, 0}; // velocity pid values
    public final static double[] imuPid_ = new double[]{0.00000002546725, 0.000000000007098311, 0.000000045210}; // imu based pid values
    public final static double[] dPidREV_PLANETARY = new double[]{0.761232, 0.00000009, 0};

    public final static double[] fusedDPid_ = new double[]{0, 0, 0};
    public final static double[] NullPid_ = new double[]{0, 0, 0};


    // DeadReckoning Parameter Tuning
    public final static double[] DeadRImuPid_ = new double[]{0.8, 0.0000000000000, 0.000000000000000000}; // imu tuning for dead reckoning alignment code
    public final static double[] DeadRDPid_ = new double[]{0.7, 0.00, 0}; // imu tuning for dead reckoning alignment code

    // PID Drive Parameters
    public final static double target_acceleration = 0.0000124;
    public final static double target_velocity     = .114;

    // Robot Parameters
    public final static double wheel_radius_meters_MECANUM = 0.0508;
    public final static double wheel_radius_meters_SMALL_OMNI = (0.030);
    public final static double x02 = .18;
    public final static double encoder_count_per_rev_NV = 1120;
    public final static double encoder_count_per_rev_REV = 1120;
    public final static double encoder_count_per_rev_REV_ENCODERS = 8192;
}
