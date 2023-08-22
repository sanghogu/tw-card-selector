package lib

import (
	"fmt"
	"gocv.io/x/gocv"
	"golan/util"
	"image"
	"image/png"
	"os"
	"path"
)

func Histogram() {
	fmt.Println(os.Getwd())

	img := imageLoad(path.Join(util.ROOT_PATH, "img/blue.png"))

	mat, err := gocv.ImageToMatRGB(img)
	if err != nil {
		panic(err)
	}

	img2 := imageLoad(path.Join(util.ROOT_PATH, "img/red.png"))

	mat2, err := gocv.ImageToMatRGB(img2)
	if err != nil {
		panic(err)
	}

	fmt.Println(gocv.CompareHist(mat, mat2, 5))

}

func imageLoad(fullPath string) image.Image {
	file, err := os.Open(fullPath)

	if err != nil {
		panic(err)
	}
	defer file.Close()

	resultImg, err := png.Decode(file)
	if err != nil {
		panic(err)
	}
	fmt.Println("Size")
	fmt.Println(resultImg.Bounds().Size())

	return resultImg
}
