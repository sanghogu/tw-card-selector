package main

import (
	"golan/lib"
	"golan/util"
	"os"
	"path/filepath"
)
import "image/png"

func FindYellow() {

	img := lib.Capture()

	file, err := os.Open(filepath.Join(util.ROOT_PATH, "img", "yellow.png"))
	if err != nil {
		panic(err)
	}

	yellowImage, err := png.Decode(file)
	if err != nil {
		panic(err)
	}

	histResult := lib.HistogramMatchingFromImage(img, yellowImage)

}

func findBlue() {

}

func findRed() {

}
