package org.firstinspires.ftc.teamcode.customLibraries;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Localizer3Wheel {

    private DcMotorEx rightOdo, leftOdo, sideStrafe;
    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private static double overallHeading = 0;


    public Localizer3Wheel(HardwareMap hardwareMap) {
        //Odo wheels are mapped to the encoder ports of the motors.
        rightOdo = hardwareMap.get(DcMotorEx.class,"frontRight");
        leftOdo = hardwareMap.get(DcMotorEx.class,"frontLeft");
        sideStrafe = hardwareMap.get(DcMotorEx.class,"backRight");
        //Motors are mapped accordingly.
        frontLeft = hardwareMap.get(DcMotor.class,"frontLeft");
        frontRight = hardwareMap.get(DcMotor.class,"frontRight");
        backLeft = hardwareMap.get(DcMotor.class,"backLeft");
        backRight = hardwareMap.get(DcMotor.class,"backLeft");

    }

}

class XYPosWithHeading {

    private double x, y, theta;

    public XYPosWithHeading(double x, double y, double theta) {
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    public double getXCord() {
        return x;
    }

    public double getYCord() {
        return y;
    }

    public double getAngleTheta() {
        return theta;
    }
}

class VectorGeneratorWithTrig {
    private static XYPosWithHeading lastPos;

    public VectorGeneratorWithTrig(double startX, double startY, double heading) {
        lastPos = new XYPosWithHeading(startX,startY,heading);
    }

    public VectorGeneratorWithTrig(XYPosWithHeading currentPos) {
        double vectorAngle;
        double distance = Math.sqrt(Math.pow(currentPos.getXCord()- lastPos.getXCord(),2) + Math.pow(currentPos.getXCord()- lastPos.getXCord(),2));
        if (currentPos.getXCord()<=lastPos.getXCord()){
            vectorAngle = Math.atan(Math.abs(currentPos.getYCord()-lastPos.getYCord())/(currentPos.getXCord()-lastPos.getXCord()))+90;
            if (currentPos.getYCord()<=lastPos.getYCord()){
                vectorAngle+=90;
            }
        } else {
            vectorAngle = Math.atan(Math.abs(currentPos.getYCord()-lastPos.getYCord())/(currentPos.getXCord()-lastPos.getXCord()));
            if (currentPos.getYCord()<lastPos.getYCord()){
                vectorAngle+=270;
            }
        }

        double vectorMagnitude = Math.sqrt(Math.abs(currentPos.getYCord()-lastPos.getYCord())*(currentPos.getXCord()-lastPos.getXCord()));


    }
}
