package tw.riot.twcardselector;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.robot.Robot;

import java.io.IOException;

public class FindCard extends Thread {

	private static final FindCard findCard = new FindCard();
	private CardType cardType;

	private long findStartTime = 0;

	private enum CardType {
		YELLOW(false, "img/yellow.png"),
		BLUE(true, "img/blue.png"),
		RED(true, "img/red.png");

		boolean keypress;
		String fileNm;
		CardType(boolean keypress, String fileNm) {
			this.keypress = keypress;
			this.fileNm = fileNm;
		}
	}


	private FindCard() {
		this.start();
	}

	public static FindCard getInstance() {
		return findCard;
	}

	public void findYellow(){
		cardType = CardType.YELLOW;
		findStartTime=System.currentTimeMillis();
	}
	public void findBlue(){
		cardType = CardType.BLUE;
		findStartTime=System.currentTimeMillis();
	}
	public void findRed(){
		cardType = CardType.RED;
		findStartTime=System.currentTimeMillis();
	}

	@Override
	public void run() {

		while(true) {
			if(this.cardType != null) {
				if(this.cardType.keypress) {
					wClick();
				}

				if(System.currentTimeMillis()-this.findStartTime>2000) {  //2초동안 못찾으면 스레드 종료함
					wClick();
					restore();
				} else {
					try {
						Histogram.getInstance(null).capture();
					} catch (IOException e) {e.printStackTrace();}
					if(Histogram.getInstance(null).findImage(this.cardType.fileNm)) {
						wClick();
						restore();
					} else {
						delay(100);
					}
				}
			}
		} //while
	} //run

	private void delay(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void restore(){
		delay(100);
		KeyboardHook.check=true;  //다시 키 활성화
		this.cardType = null;       //찾으면 현재 쓰레드 종료함
	}

	private void wClick(){
		System.out.println("wClick");
		Platform.runLater(()->{
			Robot rt = new Robot();
			rt.keyPress(KeyCode.W);
			rt.keyRelease(KeyCode.W);
		});
	}

}
