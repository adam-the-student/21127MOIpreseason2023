package org.firstinspires.ftc.teamcode.Teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Moi preseasonTeleOP")
public class zekeWithoutArm extends LinearOpMode {
    private DcMotor motor1;
    private DcMotor motor2;
    private DcMotor motor3;
    private DcMotor motor4;
    private Servo rightIntake, leftIntake, flippy;
    private CRServo intakeClaw;
    private DcMotor intakemotor, outtakemotor, outtakemotor2, v4bmotor;
    private DcMotor intakeEncoder, outtakeEncoder;

    @Override
    public void runOpMode() {
        motor1 = hardwareMap.dcMotor.get("frontLeft");
        motor2 = hardwareMap.dcMotor.get("frontRight");
        motor3 = hardwareMap.dcMotor.get("backLeft");
        motor4 = hardwareMap.dcMotor.get("backRight");
        leftIntake = hardwareMap.servo.get("leftv4b");
        rightIntake = hardwareMap.servo.get("rightv4b");
        intakeClaw = hardwareMap.crservo.get("claw");
        intakemotor = hardwareMap.dcMotor.get("inTake");
        outtakemotor = hardwareMap.dcMotor.get("outTake");
        outtakemotor2 = hardwareMap.dcMotor.get("outTake1");
        flippy = hardwareMap.servo.get("flippy");

//        leftIntake.scaleRange((double)11/54,(double)34/54);
//        rightIntake.scaleRange(43/(double)54,10/(double)27);
        leftIntake.scaleRange(.499,.501);
        rightIntake.scaleRange(.499,.501);
        waitForStart();
        if (opModeIsActive()) {/* Put run blocks here. */
            while (opModeIsActive()) {/* Put loop blocks here. */
                double y = gamepad1.left_stick_y;
                double x = -gamepad1.left_stick_x; /*Counteract imperfect strafing*/
                double rx = -gamepad1.right_stick_x; /*Denominator is the largest motor power (absolute value) or 1 This ensures all the powers maintain the same ratio, but only when at least one is out of the range [-1, 1] */
                double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                double motor1Power = (y + x + rx) / denominator; /*motor1 is top left corner*/
                double motor2Power = (y - x - rx) / denominator; /*motor2 is top right corner*/
                double motor3Power = (y - x + rx) / denominator; /*motor3 is bottom left corner*/
                double motor4Power = (y + x - rx) / denominator; /*motor4 is bottom right corner*/
                if (gamepad1.left_trigger >= 0.1) {
                    motor1Power = motor1Power / 4;
                    motor2Power = motor2Power / 4;
                    motor3Power = motor3Power / 4;
                    motor4Power = motor4Power / 4;
                } else if (gamepad1.right_trigger >= 0.1) {
                    motor1Power = motor1Power * 2;
                    motor2Power = motor2Power * 2;
                    motor3Power = motor3Power * 2;
                    motor4Power = motor4Power * 2;
                }
                motor1.setPower(-motor1Power * .5); /* motor1 is top left*/
                motor2.setPower(motor2Power * .5); /* motor2 is top right*/
                motor3.setPower(-motor3Power * .5); /* motor3 is bottom left*/
                motor4.setPower(motor4Power * .5);

                intakemotor.setPower(-gamepad2.left_stick_y);
                outtakemotor.setPower(gamepad2.right_stick_y);

                if (gamepad2.dpad_up){
                    leftIntake.scaleRange((double)11/54,11.001/54);
                    rightIntake.scaleRange((double)43/54,43.001/54);
                } else if (gamepad2.dpad_down) {
                    leftIntake.scaleRange(34/(double)54, 34.0001/(double)54);
                    rightIntake.scaleRange(20/(double)54, 20.0001/(double)54);
                } else if (gamepad2.dpad_right || gamepad2.dpad_left) {
                    leftIntake.scaleRange(.499,.501);
                    rightIntake.scaleRange(.499,.501);
                }
                leftIntake.setPosition(.5);
                rightIntake.setPosition(.5);

                if (gamepad2.triangle){
                    flippy.setPosition(1);
                }
                else if (gamepad2.x){
                    flippy.setPosition(0);
                }
                if (gamepad2.right_trigger >= 0.1){
                intakeClaw.setPower(.3);
                }
                else if (gamepad2.left_trigger >= 0.1) {
                intakeClaw.setPower(-.3);
                }
                else {
                    intakeClaw.setPower(0);
                }


                telemetry.update();
            }
        }
    }
}