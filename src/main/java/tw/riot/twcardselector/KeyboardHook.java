package tw.riot.twcardselector;

import de.ksquared.system.keyboard.GlobalKeyListener;
import de.ksquared.system.keyboard.KeyAdapter;
import de.ksquared.system.keyboard.KeyEvent;
import javafx.application.Platform;

import java.awt.*;

public class KeyboardHook{
	public static boolean check=false;   // w,e,t감지가 false은 안켜져있는상태
	public static boolean enterC=false; //false 은 엔터가안눌린상태
	
	private GlobalKeyListener gk;
	private KeyAdapter ka;
	public KeyboardHook() {
		gk=new GlobalKeyListener();
		ka=new KeyAdapter() {
			@Override public void keyPressed(KeyEvent event) { 
				System.out.println(event+"키눌림");
				if(event.getVirtualKeyCode()==13&&check) {
					if(!enterC) {  //엔터가 최초로 눌려서 채팅창이 열리는시점
						setCheck(false); // w,e,t 키 감지안함 
						enterC=true;
					}
				}else if(event.getVirtualKeyCode()==13) {
					if(enterC) {  //엔터가 눌려서 채팅창이 닫히는시점
						setCheck(true);	// w,e,t 키 감지시작
						enterC=false;   //채팅창이 닫혀서 false 로 설정함
					}
				}
			}
			@Override public void keyReleased(KeyEvent event) {
				System.out.println(event);
				try {
					if(check) {
						if(event.getVirtualKeyCode()==87) {    // W   골드
							setCheck(false);
							FindCard.getInstance().findYellow();
						}else if(event.getVirtualKeyCode()==69) {    // E   블루
							setCheck(false);
							FindCard.getInstance().findBlue();
						}else if(event.getVirtualKeyCode()==84) {    // T   레드
							setCheck(false);
							FindCard.getInstance().findRed();
						}
					} //if check
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		};
		gk.addKeyListener(ka);

	}
	
	public void setCheck(boolean check) {
		KeyboardHook.check =check;
		if(check) {
			enterC=false;        //엔터는 안누른상태로 바꿈
		}
	}



}