package tw.riot.twcardselector;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.robot.Robot;

public class FindCard extends Thread{
	private Robot rt;

	private static FindCard findCard = new FindCard();
	private CardType cardType;

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
	}

	public static FindCard getInstance() {
		return findCard;
	}

	public void findYellow(){
		cardType = CardType.YELLOW;
		Platform.runLater(this);
	}
	public void findBlue(){
		cardType = CardType.BLUE;
		Platform.runLater(this);
	}
	public void findRed(){
		cardType = CardType.RED;
		Platform.runLater(this);
	}

	@Override
	public void run() {
		//fx robot 생성은 platform runlater 내에서만 가능
		if(rt == null) rt = new Robot();

		if(this.cardType.keypress) {
			wClick();
		}

		long startTime=System.currentTimeMillis();
		while(true) {
			try {
				if(System.currentTimeMillis()-startTime>2000) {  //2초동안 못찾으면 스레드 종료함
					wClick();
					try {
						Thread.sleep(100);	//키가눌린후 약간의 딜레이후에 후킹 w,e,t 감지 true
					} catch (InterruptedException e) {
						e.printStackTrace();
						break;       //
					}
					
					KeyboardHook.check=true;  //다시 키 활성화
					break;       //
				}
				Histogram.getInstance().capture();
			}catch(Exception e) { e.printStackTrace(); continue;}
			if(Histogram.getInstance().findImage(this.cardType.fileNm)) {
				wClick();
				try {
					Thread.sleep(100);	//키가눌린후 약간의 딜레이후에 후킹 w,e,t 감지 true
				} catch (InterruptedException e) {
					e.printStackTrace();
					break;
				}
				
				KeyboardHook.check=true;  //다시 키 활성화
				break;       //찾으면 현재 쓰레드 종료함
			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
					break;
				}
			}
		} //while
	} //run

	private void wClick(){
		rt.keyPress(KeyCode.W);
		rt.keyRelease(KeyCode.W);
	}

}
