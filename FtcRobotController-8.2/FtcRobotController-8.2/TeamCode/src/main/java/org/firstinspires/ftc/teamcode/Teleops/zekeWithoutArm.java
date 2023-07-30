package org.firstinspires.ftc.teamcode.Teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "MOIPreSeasonTeleop", group = "teleop")

public class zekeWithoutArm extends LinearOpMode {
    private DcMotor motor1;
    private DcMotor motor2;
    private DcMotor motor3;
    private DcMotor motor4;
    private Servo rightIntake;
    private Servo leftIntake;
    private CRServo intakeClaw;
    private DcMotor outtakemotor1;
    private DcMotor outtakemotor2;

    @Override
    public void runOpMode() {
        motor1 = hardwareMap.dcMotor.get("frontLeft");
        motor2 = hardwareMap.dcMotor.get("frontRight");
        motor3 = hardwareMap.dcMotor.get("backLeft");
        motor4 = hardwareMap.dcMotor.get("backRight");
        leftIntake = hardwareMap.servo.get("leftIntake");
        rightIntake = hardwareMap.servo.get("rightIntake");
        intakeClaw = hardwareMap.crservo.get("claw");
        outtakemotor1 = hardwareMap.dcMotor.get("outtakeMotor1");
        outtakemotor2 = hardwareMap.dcMotor.get("outtakeMotor2");

        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.

            while (opModeIsActive()) {
                // Put loop blocks here.

                double y = gamepad1.left_stick_y;
                double x = -gamepad1.left_stick_x; // Counteract imperfect strafing
                double rx = -gamepad1.right_stick_x;

          /*Denominator is the largest motor power (absolute value) or 1
             This ensures all the powers maintain the same ratio, but only when
             at least one is out of the range [-1, 1]                   */
                double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                double motor1Power = (y + x + rx) / denominator;  //motor1 is top left corner
                double motor2Power = (y - x - rx) / denominator;  //motor2 is top right corner
                double motor3Power = (y - x + rx) / denominator;  //motor3 is bottom left corner
                double motor4Power = (y + x - rx) / denominator;  //motor4 is bottom right corner

                if(gamepad1.left_trigger >= 0.1){
                    motor1Power = motor1Power/4;
                    motor2Power = motor2Power/4;
                    motor3Power = motor3Power/4;
                    motor4Power = motor4Power/4;
                }
                else if(gamepad1.right_trigger >= 0.1){
                    motor1Power = motor1Power*2;
                    motor2Power = motor2Power*2;
                    motor3Power = motor3Power*2;
                    motor4Power = motor4Power*2;
                }

                motor1.setPower(-motor1Power * .5);  // motor1 is top left
                motor2.setPower(motor2Power * .5);  // motor2 is top right
                motor3.setPower(-motor3Power * .5);  // motor3 is bottom left
                motor4.setPower(motor4Power * .5);  // motor4 is bottom right

                // gamepad 2 starts
                //Intake
                //V4B
                //scaling
                if (gamepad2.x)
                {
                    leftIntake.scaleRange(0.2,0.5);
                    rightIntake.scaleRange(0.2,0.5);
                }

                else if (gamepad2.square)
                {
                    leftIntake.scaleRange(0.5,0.9);
                    rightIntake.scaleRange(0.5,0.9);
                }

                if (gamepad2.dpad_left)
                {
                    leftIntake.setPosition(1.0);
                    rightIntake.setPosition(1.0);
                }

                else if (gamepad2.dpad_up)
                {
                 leftIntake.setPosition(0.5);
                 rightIntake.setPosition(0.5);
                }

                else if (gamepad1.dpad_right)
                {
                    leftIntake.setPosition(0.0);
                    rightIntake.setPosition(0.0);
                }
                //intakeClaw
                if (gamepad2.right_trigger >= 0.1)
                {
                intakeClaw.setPower(gamepad2.right_trigger);
                }
                else if (gamepad2.left_trigger >= 0.1)
                {
                intakeClaw.setPower(-gamepad2.left_trigger);
                }
                //outTake
                if (gamepad2.right_stick_y >= 0.1)
                    outtakemotor1.setPower(gamepad2.right_stick_y);
                    outtakemotor2.setPower(gamepad2.right_stick_y);


                telemetry.update();

            }
        }
    }
}

