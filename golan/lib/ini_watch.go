package lib

import (
	"fmt"
	"github.com/fsnotify/fsnotify"
	"golan/util"
	"gopkg.in/ini.v1"
	"path"
)

var (
	x      = 0
	y      = 0
	width  = 0
	height = 0
)

func RegWatch() {

	fmt.Println(x, y, width, height)
	watch, err := fsnotify.NewWatcher()
	if err != nil {
		panic(err)
	}
	defer watch.Close()

	watch.Add(path.Join(util.ROOT_PATH, "live_setting.ini"))

	go func() {
		for {
			select {
			case event, ok := <-watch.Events:
				if !ok {
					return
				}
				if event.Name == path.Join(util.ROOT_PATH, "live_setting.ini") {
					readSettingIni()
				}
			}
		}
	}()
}

func readSettingIni() {

	file, err := ini.Load(path.Join(util.ROOT_PATH, "live_setting.ini"))
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
