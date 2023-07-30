package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class BaseRobotMethods {

    //VARIABLES ------------------------------------------------------------------------------------
    private ElapsedTime runtime = new ElapsedTime();

    // ticks to a degree
    final double tickToDegree = 1.295;

    public DcMotorEx arm;
    public DcMotorEx frontleft;
    public DcMotorEx frontright;
    public DcMotorEx backleft;
    public DcMotorEx backright;


    public CRServo wrist;
    public CRServo claw;
    public Servo rotatingClaw;

    public ColorSensor color;
    public DistanceSensor distance;

    public LinearOpMode parent;
    public Telemetry telemetry;

    private int timeout = 7; //seconds until command abort

    //HARDWARE SETUP -------------------------------------------------------------------------------
    public BaseRobotMethods(HardwareMap hardwareMap) { //init all hardware here
        frontleft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontright = hardwareMap.get(DcMotorEx.class, "frontRight");
        backleft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backright = hardwareMap.get(DcMotorEx.class, "backRight");
        arm = hardwareMap.get(DcMotorEx.class, "arm");

        arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontright.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backleft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backright.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        wrist = hardwareMap.get(CRServo.class,"wrist");
        claw = hardwareMap.get(CRServo.class,"claw");
        rotatingClaw = hardwareMap.servo.get("ServoRotate");

        color = hardwareMap.get(ColorSensor.class, "color");
        distance = hardwareMap.get(DistanceSensor.class, "distance");
    }

    //MOTOR COMMANDS   -----------------------------------------------------------------------------
    public void setMotorMode(DcMotorEx.RunMode mode){
        frontleft.setMode(mode);
        frontright.setMode(mode);
        backleft.setMode(mode);
        backright.setMode(mode);
    }

    public void setMotorPosition(int pos1, int pos2, int pos3, int pos4){
        frontleft.setTargetPosition(pos1); //set encoder ticks target
        frontright.setTargetPosition(pos2);
        backleft.setTargetPosition(pos3);
        backright.setTargetPosition(pos4);
    }
    public void turn(double degree, double power){

        frontleft.setTargetPosition((int)(degree*tickToDegree));
        frontright.setTargetPosition((int)(degree*tickToDegree));
        backleft.setTargetPosition((int)(-degree*tickToDegree));
        backright.setTargetPosition((int)(degree*tickToDegree));

        frontleft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        frontright.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        frontleft.setPower(power);
        frontright.setPower(power);
        backleft.setPower(-power);
        backright.setPower(power);

        while (frontleft.isBusy() || frontright.isBusy() || backleft.isBusy() || backright.isBusy()) {}

        stopMovement();

    }

    public void setMotorPower(double speed1, double speed2, double speed3, double speed4){
        frontleft.setPower(speed1); //set motor power target
        frontright.setPower(speed2);
        backleft.setPower(speed3);
        backright.setPower(speed4);

        //run motors until one of them stops
        while(parent.opModeIsActive() &&  (runtime.seconds() < timeout) && (frontleft.isBusy()
                && frontright.isBusy() && backleft.isBusy() && backright.isBusy())){

            telemetry.addData("encoder-fwd-left", frontleft.getCurrentPosition() + "busy=" + frontleft.isBusy());
            telemetry.addData("encoder-fwd-right", frontright.getCurrentPosition() + "busy=" + frontright.isBusy());
            telemetry.addData("encoder-bkw-left", backleft.getCurrentPosition() + "busy=" + backleft.isBusy());
            telemetry.addData("encoder-bkw-right", backright.getCurrentPosition() + "busy=" + backright.isBusy());
            telemetry.update();
        }

        stopMovement();
        runtime.reset();
    }

    public void stopMovement(){
        frontleft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontright.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        backleft.setPower(0);
        frontleft.setPower(0);
        frontright.setPower(0);
        backright.setPower(0);
    }

    //ARM COMMANDS ---------------------------------------------------------------------------------
    public void liftArm(int distance, double power){
        arm.setTargetPosition(distance);
        arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        arm.setPower(power);
        while (arm.isBusy()){}
        stopArm();
        arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void liftArmWithFlip(int distance, double power){
        arm.setTargetPosition(distance);
        arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        arm.setPower(power);
        flipClaw();
        while (arm.isBusy()){}
        stopArm();
    }

    public void stopArm(){
        arm.setPower(0);
        arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void controlClaw(double power){
        claw.setPower(power);
    }

    public void controlWrist(double power){wrist.setPower(power);}

    public void flipClaw(){
        if (rotatingClaw.getPosition() == -1){
            rotatingClaw.setPosition(1);
        } else {
            rotatingClaw.setPosition(-1);
        }
    }

    public void initRotate(){
        rotatingClaw.setPosition(-1);
    }

    public double colorDetection(String rgb){

     if (rgb.contains("ed")){
         return color.red();
     } else if(rgb.contains("lue")){
         return color.blue();
     }
     return color.green();
    }

}
