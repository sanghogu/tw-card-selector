package main

import (
	"C"
	"fmt"
	"github.com/moutend/go-hook/pkg/keyboard"
	"github.com/moutend/go-hook/pkg/types"
	"golan/lib"
	"os"
	"os/signal"
	"syscall"
)

func main() {

	var isDetectKey bool = true
	var isEnter bool = false

	keyboardChan := make(chan types.KeyboardEvent)

	go func() {

		fmt.Println("install")
		keyboard.Install(nil, keyboardChan)
		for key := range keyboardChan {
			fmt.Println(key.VKCode)
			fmt.Println(key.Time)
			if isDetectKey {
				if key.VKCode == types.VK_RETURN {
					isEnter = !isEnter
				} else if !isEnter {
					if key.VKCode == types.VK_W {
						go func() {
							isDetectKey = false
							lib.FindYellow()
							isDetectKey = true
						}()
					} else if key.VKCode == types.VK_E {
						go func() {
							isDetectKey = false
							lib.FindBlue()
							isDetectKey = true
						}()
					} else if key.VKCode == types.VK_T {
						go func() {
							isDetectKey = false
							lib.FindRed()
							isDetectKey = true
						}()
					}
				}
			}

		}

	}()

	gracefully()
}

func gracefully() {
	exitSignal := make(chan os.Signal, 1)
	signal.Notify(exitSignal, syscall.SIGINT, syscall.SIGTERM, os.Interrupt)
	<-exitSignal

	keyboard.Uninstall()
	fmt.Println("Uninstall")

}
