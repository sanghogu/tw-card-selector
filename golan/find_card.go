package main

import (
	"github.com/moutend/go-hook/pkg/types"
	"golan/lib"
	"golan/util"
	"os"
	"path/filepath"
	"time"
)
import "image/png"

func findYellow() bool {
	return find("yellow.png")
}

func findBlue() bool {
	lib.Click(types.VK_W)
	return find("blue.png")
}

func findRed() bool {
	lib.Click(types.VK_W)
	return find("red.png")
}

func find(srcFileNm string) bool {
	img := lib.Capture()
	histResult := false
	startTime := time.Now().UnixMilli()

	for !histResult {
		file, err := os.Open(filepath.Join(util.ROOT_PATH, "img", srcFileNm))
		if err != nil {
			panic(err)
		}

		srcImage, err := png.Decode(file)
		if err != nil {
			panic(err)
		}
		histResult = lib.HistogramMatchingFromImage(img, srcImage) > 0.6
		if time.Now().UnixMilli()-startTime > 2000 {
			lib.Click(types.VK_W)
			return false
		}
		//카드변경타임동안 스톱 딜레이
		if !histResult {
			time.Sleep(time.Millisecond * 100)
		}
	}
	lib.Click(types.VK_W)
	return true
}
