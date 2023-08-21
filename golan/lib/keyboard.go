package lib

import (
	"fmt"
	"github.com/moutend/go-hook/pkg/keyboard"
	"github.com/moutend/go-hook/pkg/types"
	"os"
	"os/signal"
	"syscall"
)

func KeyboardInit() {

	fmt.Println("Start")

	keyboardChan := make(chan types.KeyboardEvent)
	go func() {
		fmt.Println("install")
		keyboard.Install(nil, keyboardChan)
		for key := range keyboardChan {
			fmt.Println(key.VKCode)
			fmt.Println(key.Time)
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
