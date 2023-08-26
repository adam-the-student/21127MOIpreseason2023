package org.firstinspires.ftc.teamcode.Autons;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class ParkOnly extends LinearOpMode {

    private DcMotor motor1, motor2, motor3, motor4;
    private final double INCHTOTICK = 65.8;

    @Override
    public void runOpMode(){
        motor1 = hardwareMap.get(DcMotor.class, "frontLeft");
        motor2 = hardwareMap.get(DcMotor.class, "frontRight");
        motor3 = hardwareMap.get(DcMotor.class, "backLeft");
        motor4 = hardwareMap.get(DcMotor.class, "backRight");

        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        //left wheels+ right wheels-

    }

    public void strafe(double inches){
        motor1.setTargetPosition((int)(inches*INCHTOTICK));
        motor2.setTargetPosition(-(int)(inches*INCHTOTICK));
        motor3.setTargetPosition((int)(inches*INCHTOTICK));
        motor4.setTargetPosition(-(int)(inches*INCHTOTICK));

        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor4.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(motor1.getCurrentPosition()!= motor1.getTargetPosition()){
            motor1.setPower(1-(double)(motor1.getCurrentPosition()/motor1.getTargetPosition()));
            motor2.setPower((double)(motor2.getCurrentPosition()/motor2.getTargetPosition())-1);
            motor3.setPower(1-(double)(motor3.getCurrentPosition()/motor3.getTargetPosition()));
            motor4.setPower((double)(motor4.getCurrentPosition()/motor4.getTargetPosition())-1);
        }
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void forwardAndBack(double inches){
        motor1.setTargetPosition((int)(inches*INCHTOTICK));
        motor2.setTargetPosition((int)(inches*INCHTOTICK));
        motor3.setTargetPosition(-(int)(inches*INCHTOTICK));
        motor4.setTargetPosition(-(int)(inches*INCHTOTICK));

        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor4.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(motor1.getCurrentPosition()!= motor1.getTargetPosition()){
            motor1.setPower(1-(double)(motor1.getCurrentPosition()/motor1.getTargetPosition()));
            motor2.setPower(1-(double)(motor2.getCurrentPosition()/motor2.getTargetPosition()));
            motor3.setPower((double)(motor3.getCurrentPosition()/motor3.getTargetPosition())-1);
            motor4.setPower((double)(motor4.getCurrentPosition()/motor4.getTargetPosition())-1);
        }
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

}