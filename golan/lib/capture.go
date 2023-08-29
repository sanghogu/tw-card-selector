package lib

import (
	"github.com/kbinani/screenshot"
	"image"
)

func Capture() image.Image {

	img, err := screenshot.Capture(853, 994, 36, 36)
	if err != nil {
		panic(err)
	}

	return img

	/*	아래 코드는 파일로 저장하는것 디버그시에만 허용
		buf := new(bytes.Buffer)
			png.Encode(buf, img)
			os.WriteFile(path.Join(util.ROOT_PATH, "img", "test.png"), buf.Bytes(), os.ModePerm)*/
}
