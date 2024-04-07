package org.firstinspires.ftc.teamcode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class GetColorBlue extends OpenCvPipeline {

    final int cameraWidth = 1184;
    final int cameraHeight = 656;
    static boolean isOnRight = false;
    static boolean isOnLeft = false;
    static boolean isOnMiddle = false;

    Mat YCbCr = new Mat();
    Mat leftSide = new Mat();
    Mat rightSide = new Mat();
    Mat middleSide = new Mat();
    Mat output = new Mat();
    Scalar rectColor = new Scalar(255.0, 0.0, 0.0);

    static double leftavgfin;
    static double rightavgfin;
    static double middleavgfin;
    Rect leftRect = new Rect(1, cameraHeight / 3, (cameraWidth / 3) - 1, (int) (cameraHeight) / 5);
    Rect middleRect = new Rect(cameraWidth / 3, cameraHeight / 3, (cameraWidth / 3) - 1, (int) (cameraHeight) / 5);
    Rect rightRect = new Rect((cameraWidth / 3 * 2) + 1, cameraHeight / 3, (cameraWidth / 3) - 1, (int) (cameraHeight) / 5);

    @Override
    public Mat processFrame(Mat input) {
        // Clear Mats
        leftSide.release();
        rightSide.release();
        middleSide.release();
        output.release();


        Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2YCrCb);


        input.copyTo(output);
        Imgproc.rectangle(output, leftRect, rectColor, 2);
        Imgproc.rectangle(output, rightRect, rectColor, 2);
        Imgproc.rectangle(output, middleRect, rectColor, 2);


        leftSide = YCbCr.submat(leftRect);
        rightSide = YCbCr.submat(rightRect);
        middleSide = YCbCr.submat(middleRect);

        //extracts the channel, since its YCRCB
        //channel 0 is Y (luma) channel 1 is CR (red) channel 2 is CB (blue)

        Core.extractChannel(leftSide, leftSide, 2);
        Core.extractChannel(rightSide, rightSide, 2);
        Core.extractChannel(middleSide, middleSide, 2);


        Scalar leftAvg = Core.mean(leftSide);
        Scalar rightAvg = Core.mean(rightSide);
        Scalar middleAvg = Core.mean(middleSide);


        leftavgfin = leftAvg.val[0];
        rightavgfin = rightAvg.val[0];
        middleavgfin = middleAvg.val[0];
        System.out.println("Mid Value of left: " + leftavgfin);
        System.out.println("Mid Value of right: " + rightavgfin);
        System.out.println("Mid Value of middle: " + middleavgfin);


        if (middleavgfin > leftavgfin && middleavgfin > rightavgfin) {
            isOnMiddle = true;
            isOnRight = false;
            isOnLeft = false;
        } else if (leftavgfin > rightavgfin && leftavgfin > middleavgfin) {
            isOnLeft = true;
            isOnRight = false;
            isOnMiddle = false;
        } else {
            isOnRight = true;
            isOnLeft = false;
            isOnMiddle = false;
        }

        return output;
    }

    public boolean getLatestResultR() {
        return isOnRight;
    }

    public boolean getLatestResultL() {
        return isOnLeft;
    }

    public String debugging() {
        return "\n left:" + leftavgfin + "\n right: " + rightavgfin + "\n middle: " + middleavgfin;
    }

    public boolean getLatestResultM() {
        return isOnMiddle;
    }
}