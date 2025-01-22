package org.firstinspires.ftc.teamcode.Roadrunner.Testers.Pipelines;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Roadrunner.Auto.References.Pipelines.LocateBlue;
import org.firstinspires.ftc.teamcode.Roadrunner.Auto.References.Pipelines.LocateRed;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;



@Autonomous(name="Color Tester")
public class ColorTester extends LinearOpMode{
    boolean isOnRight,isOnMiddle,isOnLeft;
    String result = "";
    final int cameraWidth = 1280;
    final int cameraHeight = 720;

    private enum Color{
        BLUE, RED
    }

    LocateBlue colorB;
    LocateRed colorR;
    Color color = Color.RED;

    public void runOpMode() throws InterruptedException{
        telemetry.addData("Starting Auton now: check camera to init", "Ready");
        telemetry.update();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvWebcam webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        switch(color){
            case RED:
                colorR = new LocateRed();
                webcam.setPipeline(colorR);
            case BLUE:
                colorB = new LocateBlue();
                webcam.setPipeline(colorB);
            default:
                break;
        }

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(cameraWidth, cameraHeight, OpenCvCameraRotation.UPRIGHT);
            }


            @Override
            public void onError(int errorCode) {


            }
        });






        //view the webcam now, you cant check camera stream once the game is initialized.
        waitForStart();


        telemetry.addData("Succesfully init", "Running");
        telemetry.update();

        while(opModeIsActive()){

            switch(color){
                case RED:
                    isOnRight = colorR.getLatestResultR();
                    isOnMiddle = colorR.getLatestResultM();
                    isOnLeft = colorR.getLatestResultL();
                    result = colorR.debugging();
                case BLUE:
                    isOnRight = colorB.getLatestResultR();
                    isOnMiddle = colorB.getLatestResultM();
                    isOnLeft = colorB.getLatestResultL();
                    result = colorB.debugging();
                default:
                    break;
            }

            if(isOnRight){
                telemetry.addData("obj is on right", result);
                telemetry.update();
            }else if(isOnMiddle){
                telemetry.addData("obj is on middle", result);
                telemetry.update();
            }else if(isOnLeft){
                telemetry.addData("obj is on left", result);
                telemetry.update();
            }
        }



        telemetry.addData("Status", "GG");
        telemetry.update();
    }






}
