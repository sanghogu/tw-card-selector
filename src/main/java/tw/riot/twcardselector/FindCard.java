package tw.riot.twcardselector;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.robot.Robot;

import java.io.IOException;

public class FindCard extends Thread {

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


	public FindCard() {
	}

	public void findYellow(){
		find(CardType.YELLOW);
	}
	public void findBlue(){
		find(CardType.BLUE);
	}
	public void findRed(){
		find(CardType.RED);
	}

	private void find(CardType cardType) {
		System.out.println("FIND"+ cardType);
		this.cardType = cardType;
		this.start();
	}

	@Override
	public void run() {

		findStartTime=System.currentTimeMillis();
		if(this.cardType.keypress) {
			wClick();
		}

		while(true) {
			System.out.println(Thread.currentThread().getName());
			long currTimeMillis = System.currentTimeMillis();
			System.out.println(currTimeMillis - findStartTime);
			if(currTimeMillis-findStartTime>3000) {  //3초동안 못찾으면 스레드 종료함
				wClick();
				break;
			} else {
				try {
					Histogram.getInstance(null).capture();
				} catch (IOException e) {e.printStackTrace();}
				if(Histogram.getInstance(null).findImage(this.cardType.fileNm)) {
					wClick();
					break;
				} else {
					delay(100);
				}
			}

		} //while

		delay(100);
		KeyboardHook.check=true;  //다시 키 활성화

	} //run

	private void delay(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
