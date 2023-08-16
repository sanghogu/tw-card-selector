package tw.riot.twcardselector;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_imgproc.*;
import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgproc.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;

public class Histogram {
	BufferedImage screenShotImage;    //화면 캡처
	java.awt.Robot robot;
	public Histogram() throws Exception{
		robot = new Robot();
		screenShotImage = robot.createScreenCapture(new Rectangle(839, 982,40, 41)); //화면캡쳐
		//인터페이스 26전용
		ImageIO.write(screenShotImage, "png",new File("img/screen.png"));  //캡쳐한 화면내보내기
	}

	public boolean findImage(String filename) {

		String baseFilename = "img/screen.png";

		String contrastFilename = filename;

		IplImage baseImage = cvLoadImage(baseFilename);

		CvHistogram hist= getHueHistogram(baseImage);

		IplImage contrastImage = cvLoadImage(contrastFilename);

		CvHistogram hist1=getHueHistogram(contrastImage);
		double matchValue=cvCompareHist(hist, hist1, CV_COMP_INTERSECT );

		System.out.println(matchValue);
		if(matchValue>0.8)
			return true;
		else {
			return false;
		}
	}

	private static CvHistogram getHueHistogram(IplImage image){

		if(image==null || image.nChannels()<1) new Exception("Error!");



		IplImage greyImage= cvCreateImage(image.cvSize(), image.depth(), 1);

		cvCvtColor(image, greyImage, CV_RGB2GRAY);



//bins and value-range

		int numberOfBins=256;

		float minRange= 0f;

		float maxRange= 255f;

// Allocate histogram object

		int dims = 1;

		int[]sizes = new int[]{numberOfBins};

		int histType = CV_HIST_ARRAY;

		float[] minMax = new  float[]{minRange, maxRange};

		float[][] ranges = new float[][]{minMax};

		int uniform = 1;

		CvHistogram hist = cvCreateHist(dims, sizes, histType, ranges, uniform);

// Compute histogram

		int accumulate = 0;

		IplImage mask = null;

		IplImage[] aux = new IplImage[]{greyImage};



		cvCalcHist(aux,hist, accumulate, null);

		cvNormalizeHist(hist, 1);



		cvGetMinMaxHistValue(hist, minMax, minMax, sizes, sizes);

		System.out.println("Min="+minMax[0]); //Less than 0.01

		System.out.println("Max="+minMax[1]); //255

		return hist;

	}
}
