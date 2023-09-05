package test

import (
	"fmt"
	"github.com/lxn/win"
	"syscall"
	"testing"
	"unsafe"
)

var (
	user32         = syscall.NewLazyDLL("user32.dll")
	mapVirtualKeyA = user32.NewProc("MapVirtualKeyA")
)

func TestConvertVkCodeToScanCodeRetDEC(t *testing.T) {
	ret, _, _ := mapVirtualKeyA.Call(uintptr(0x57), uintptr(0))
	fmt.Println(uintptr(ret))
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
