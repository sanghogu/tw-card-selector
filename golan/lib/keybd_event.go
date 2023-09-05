package lib

import (
	"github.com/lxn/win"
	"github.com/moutend/go-hook/pkg/types"
	"syscall"
	"unsafe"
)

var (
	user32         = syscall.NewLazyDLL("user32.dll")
	mapVirtualKeyA = user32.NewProc("MapVirtualKeyA")
)

func convertVkCodeToScanCodeRetDEC(code types.VKCode) int {
	r1, _, err := mapVirtualKeyA.Call(uintptr(code), uintptr(0))
	if err != nil {
		panic(err)
	}

	return int(r1)
}

func Click(code types.VKCode) {
	scancode := convertVkCodeToScanCodeRetDEC(code)
	down(scancode)
	up(scancode)
}

func down(scancode int) {
	keyboard := win.KEYBDINPUT{
		WScan:   0x11,
		DwFlags: win.KEYEVENTF_SCANCODE,
		Time:    0,
	}
	var i [1]win.KEYBD_INPUT = [1]win.KEYBD_INPUT{{Type: win.INPUT_KEYBOARD, Ki: keyboard}}
	win.SendInput(1, unsafe.Pointer(&i), int32(unsafe.Sizeof(i[0])))
}

func up(scancode int) {
	keyboard := win.KEYBDINPUT{
		WScan:   0x11,
		DwFlags: win.KEYEVENTF_SCANCODE | win.KEYEVENTF_KEYUP,
		Time:    0,
	}
	var i [1]win.KEYBD_INPUT = [1]win.KEYBD_INPUT{{Type: win.INPUT_KEYBOARD, Ki: keyboard}}
	win.SendInput(1, unsafe.Pointer(&i), int32(unsafe.Sizeof(i[0])))
}
