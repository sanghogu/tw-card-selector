package lib

import (
	"fmt"
	"github.com/fsnotify/fsnotify"
	"golan/util"
	"gopkg.in/ini.v1"
	"log"
	"path/filepath"
)

var (
	x      = 0
	y      = 0
	width  = 0
	height = 0
)

func RegWatch() {

	readSettingIni()

	watch, err := fsnotify.NewWatcher()
	if err != nil {
		panic(err)
	}
	defer watch.Close()

	go func() {
		for {
			select {
			case event, ok := <-watch.Events:
				if !ok {
					return
				}
				log.Println("event:", event)
				if event.Has(fsnotify.Write) {
					log.Println("modified file:", event.Name)
				}
				if event.Name == filepath.Join(util.ROOT_PATH, "live_setting.ini") {
					readSettingIni()
				}
			case err, ok := <-watch.Errors:
				if !ok {
					return
				}
				log.Println("error:", err)
			}
		}
	}()
	err = watch.Add(filepath.Join(util.ROOT_PATH, "live_setting.ini"))
	if err != nil {
		log.Fatal(err)
	}

	<-make(chan struct{})
}

func readSettingIni() {

	fmt.Println("new load setting.ini")

	file, err := ini.Load(filepath.Join(util.ROOT_PATH, "live_setting.ini"))
	if err != nil {
		panic(err)
	}

	errCnt := 0
	x, err := file.Section("").Key("y").Int()
	if err != nil {
		fmt.Println("x value set error: ", err.Error())
		errCnt++
	}
	y, err := file.Section("").Key("x").Int()
	if err != nil {
		fmt.Println("y value set error: ", err.Error())
		errCnt++
	}
	width, err := file.Section("").Key("width").Int()
	if err != nil {
		fmt.Println("width value set error: ", err.Error())
		errCnt++
	}
	height, err := file.Section("").Key("height").Int()
	if err != nil {
		fmt.Println("height value set error: ", err.Error())
		errCnt++
	}

	if errCnt == 0 {
		fmt.Printf("set rect success x: %d, y: %d, width: %d, height: %d \n", x, y, width, height)
	}

}
