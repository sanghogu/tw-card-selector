package main

import (
	"C"
	"fmt"
	"github.com/moutend/go-hook/pkg/keyboard"
	"github.com/moutend/go-hook/pkg/types"
	"os"
	"os/signal"
	"syscall"
)

func main() {

	var isDetectKey bool = false
	var isEnter bool = false

	keyboardChan := make(chan types.KeyboardEvent)

	go func() {

		fmt.Println("install")
		keyboard.Install(nil, keyboardChan)
		for key := range keyboardChan {
			fmt.Println(key.VKCode)
			fmt.Println(key.Time)
			if key.VKCode == types.VK_RETURN {
				isEnter = !isEnter
			} else if isDetectKey && !isEnter {
				if key.VKCode == types.VK_W {

				} else if key.VKCode == types.VK_E {

				} else if key.VKCode == types.VK_T {

				}
			}
		}

	}()

	gracefully()

}

func gracefully() {
	exitSignal := make(chan os.Signal, 1)
	signal.Notify(exitSignal, syscall.SIGINT, syscall.SIGTERM, os.Interrupt)

	keyboard.Uninstall()
	fmt.Println("Uninstall")

	<-exitSignal
}
