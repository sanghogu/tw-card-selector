package tw.riot.twcardselector;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.*;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.scene.image.Image;
import javafx.scene.robot.Robot;
import org.bytedeco.javacpp.tools.Logger;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_imgproc.*;
import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgproc.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;

public class Histogram {

	Map<String, CvHistogram> cardCache = new HashMap <>();

	static Logger logger = Logger.create(Histogram.class);
	private int x, y, width, height;

	private static Histogram histogram;

	private ImageView captureView;

	private Histogram(ImageView captureView) {
		this.captureView = captureView;
	}

	public static Histogram getInstance(ImageView captureView){
		synchronized (Histogram.class) {
			if(histogram == null) histogram = new Histogram(captureView);
		}
		return histogram;
	}

	public void adjustPosition(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void capture() throws IOException, AWTException {

		File captureFile = new File("capture/screen.png");

		java.awt.Robot robot = new java.awt.Robot();
		BufferedImage screenShotImage = robot.createScreenCapture(new Rectangle(x, y,width, height)); //화면캡쳐
		ImageIO.write(screenShotImage, "png", captureFile);

		Platform.runLater(()->{
			captureView.setImage(SwingFXUtils.toFXImage(screenShotImage, null));
		});
	}

	public boolean findImage(String filename) {

		try {
			String captureFileName = "capture/screen.png";
			CvHistogram hist1 = null;
			if(!cardCache.containsKey(filename)) {
				hist1 = getHueHistogram(cvLoadImage(filename));
			} else hist1 = cardCache.get(filename);


			IplImage captureImage = cvLoadImage(captureFileName);
			CvHistogram hist = getHueHistogram(captureImage);

			//CV_COMP_INTERSECT == 일치할수록 갚이 높음, 컴퓨터마다 색상값이 다르므로 완벽일치 불일치는 보지않으며 수치로 지정함
			double matchValue=cvCompareHist(hist, hist1, CV_COMP_INTERSECT );

			logger.info(String.valueOf(matchValue));
			return matchValue > 0.8;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static CvHistogram getHueHistogram(IplImage image){

		if(image==null || image.nChannels()<1) new Exception("Error!");

		IplImage greyImage = cvCreateImage(image.cvSize(), image.depth(), 1);

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

		logger.info("Min="+minMax[0]);
		logger.info("Max="+minMax[1]);

		return hist;

	}
}
