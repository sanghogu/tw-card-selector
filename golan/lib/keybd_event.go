package lib

import (
	"github.com/moutend/go-hook/pkg/types"
	"syscall"
)

var (
	user32     = syscall.NewLazyDLL("user32.dll")
	keybdEvent = user32.NewProc("keybd_event")
)

func Click(code types.VKCode) {
	down(code)
	up(code)
}

func down(code types.VKCode) {
	keybdEvent.Call(uintptr(code), uintptr(0), uintptr(0), uintptr(0))
}

func up(code types.VKCode) {
	keybdEvent.Call(uintptr(code), uintptr(0), uintptr(0x0002), uintptr(0))
}
