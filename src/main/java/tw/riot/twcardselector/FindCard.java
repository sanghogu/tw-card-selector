package tw.riot.twcardselector;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.robot.Robot;

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
				try {
					if(System.currentTimeMillis()-this.findStartTime>2000) {  //2초동안 못찾으면 스레드 종료함
						wClick();
						try {
							Thread.sleep(100);	//키가눌린후 약간의 딜레이후에 후킹 w,e,t 감지 true
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						KeyboardHook.check=true;  //다시 키 활성화
						this.cardType = null;
						continue;
					}
					Histogram.getInstance(null).capture();
				}catch(Exception e) { e.printStackTrace(); continue;}
				if(Histogram.getInstance(null).findImage(this.cardType.fileNm)) {
					wClick();
					try {
						Thread.sleep(100);	//키가눌린후 약간의 딜레이후에 후킹 w,e,t 감지 true
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					KeyboardHook.check=true;  //다시 키 활성화
					this.cardType = null;       //찾으면 현재 쓰레드 종료함
					continue;
				} else {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
						KeyboardHook.check=true;  //다시 키 활성화
						this.cardType = null;       //찾으면 현재 쓰레드 종료함
						continue;
					}
				}
			}
		} //while
	} //run

	private void wClick(){
		Platform.runLater(()->{
			Robot rt = new Robot();
			rt.keyPress(KeyCode.W);
			rt.keyRelease(KeyCode.W);
		});
	}

}
