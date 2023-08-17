package tw.riot.twcardselector;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import java.awt.*;
import java.util.Map;

public class KeyboardHook{
	public static boolean check=false;   // w,e,t감지가 false은 안켜져있는상태
	public static boolean enterC=false; //false 은 엔터가안눌린상태

	public KeyboardHook() {
		GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // Use false here to switch to hook instead of raw input
		System.out.println("Global keyboard hook successfully started, press [escape] key to shutdown. Connected keyboards:");

		for (Map.Entry <Long, String> keyboard : GlobalKeyboardHook.listKeyboards().entrySet()) {
			System.out.format("%d: %s\n", keyboard.getKey(), keyboard.getValue());
		}
		keyboardHook.addKeyListener(new GlobalKeyAdapter() {

			@Override
			public void keyPressed(GlobalKeyEvent event) {
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

			@Override
			public void keyReleased(GlobalKeyEvent event) {
				System.out.println(event);
				if(check) {
					if(event.getVirtualKeyCode()==87) {    // W   골드
						setCheck(false);
						new FindCard().findYellow();
					}else if(event.getVirtualKeyCode()==69) {    // E   블루
						setCheck(false);
						new FindCard().findBlue();
					}else if(event.getVirtualKeyCode()==84) {    // T   레드
						setCheck(false);
						new FindCard().findRed();
					}
				} //if check

			}
		});

		Runtime.getRuntime().addShutdownHook(new Thread(()->{
			keyboardHook.shutdownHook();
			System.out.println("Shutdownhook");
		}));

	}
	
	public void setCheck(boolean check) {
		KeyboardHook.check =check;
		if(check) {
			enterC=false;        //엔터는 안누른상태로 바꿈
		}
	}



}