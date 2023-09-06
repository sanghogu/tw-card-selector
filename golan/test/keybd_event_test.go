package test

import (
	"fmt"
	"github.com/lxn/win"
	"github.com/moutend/go-hook/pkg/types"
	"golan/lib"
	"testing"
	"unsafe"
)

func TestConvertVkCodeToScanCodeRetDEC(t *testing.T) {

	lib.Click(types.VK_W)
}

func TestKeyPress(t *testing.T) {

	keyboard := win.KEYBDINPUT{
		WScan:   17,
		DwFlags: win.KEYEVENTF_SCANCODE,
		Time:    0,
	}
	var i [1]win.KEYBD_INPUT = [1]win.KEYBD_INPUT{{Type: win.INPUT_KEYBOARD, Ki: keyboard}}
	ret := win.SendInput(1, unsafe.Pointer(&i), int32(unsafe.Sizeof(i[0])))

	keyboard = win.KEYBDINPUT{
		WScan:   17,
		DwFlags: win.KEYEVENTF_SCANCODE | win.KEYEVENTF_KEYUP,
		Time:    0,
	}
	i[0].Ki = keyboard
	ret = win.SendInput(1, unsafe.Pointer(&i), int32(unsafe.Sizeof(i[0])))

	fmt.Println(ret)

}
