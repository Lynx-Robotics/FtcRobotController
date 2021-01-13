package org.firstinspires.ftc.teamcode.backend.control.low_level.AccDecc;

public interface VelProfile {
    // this is the method that should be used to parse a given profile
    void parseString(String profile);

    // this method should return the power being sent to the motor (this is called from the motor)
    double queryModel();
}


