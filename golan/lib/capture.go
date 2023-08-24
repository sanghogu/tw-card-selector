package lib

import (
	"bytes"
	"fmt"
	"github.com/kbinani/screenshot"
	"golan/util"
	"os"
	"path"
)
import "image/png"

func Capture() {

	img, err := screenshot.Capture(853, 994, 36, 36)
	if err != nil {
		panic(err)
	}

	buf := new(bytes.Buffer)
	png.Encode(buf, img)

	fmt.Println(buf.Bytes())

	os.WriteFile(path.Join(util.ROOT_PATH, "img", "test.png"), buf.Bytes(), os.ModePerm)

}
