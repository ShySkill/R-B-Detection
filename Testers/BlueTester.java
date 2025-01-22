package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;



@Autonomous(name="Blue Tester")
public class BlueTester extends LinearOpMode{


    public void runOpMode() throws InterruptedException{
        telemetry.addData("Starting Auton now: check camera to init", "Ready");
        telemetry.update();

        GetColorBlue color = new GetColorBlue();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvWebcam webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        webcam.setPipeline(color);


        final int cameraWidth = 1280;
        final int cameraHeight = 720;


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
            boolean isOnRight = color.getLatestResultR();
            boolean isOnMiddle = color.getLatestResultM();
            boolean isOnLeft = color.getLatestResultL();
            String result = color.debugging();
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
