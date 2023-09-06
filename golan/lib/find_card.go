package lib

import (
	"github.com/moutend/go-hook/pkg/types"
	"golan/util"
	"os"
	"path/filepath"
	"time"
)
import "image/png"

func FindYellow() bool {
	return find("yellow.png")
}

func FindBlue() bool {
	Click(types.VK_W)
	return find("blue.png")
}

func FindRed() bool {
	Click(types.VK_W)
	return find("red.png")
}

func find(srcFileNm string) bool {
	histResult := false
	startTime := time.Now().UnixMilli()

	for !histResult {
		img := Capture()
		file, err := os.Open(filepath.Join(util.ROOT_PATH, "img", srcFileNm))
		if err != nil {
			panic(err)
		}

		srcImage, err := png.Decode(file)
		if err != nil {
			panic(err)
		}
		histResult = HistogramMatchingFromImage(img, srcImage) > 0.8
		if time.Now().UnixMilli()-startTime > 2000 {
			Click(types.VK_W)
			return false
		}
		//카드변경타임동안 스톱 딜레이
		if !histResult {
			time.Sleep(time.Millisecond * 100)
		}
	}
	Click(types.VK_W)
	return true
}
