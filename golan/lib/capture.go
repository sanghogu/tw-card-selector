package lib

import (
	"bytes"
	"github.com/kbinani/screenshot"
	"golan/util"
	"image"
	"image/png"
	"os"
	"path"
)

func Capture() image.Image {

	img, err := screenshot.Capture(853, 994, 36, 36)
	if err != nil {
		panic(err)
	}

	//디버그용 캡쳐 위치
	go func() {
		buf := new(bytes.Buffer)
		png.Encode(buf, img)
		os.WriteFile(path.Join(util.ROOT_PATH, "img", "test.png"), buf.Bytes(), os.ModePerm)
	}()

	return img
}
