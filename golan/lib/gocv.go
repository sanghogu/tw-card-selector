package lib

import (
	"fmt"
	"gocv.io/x/gocv"
	"golan/util"
	"image"
	"path"
)

// return 1에 가까울수록 유사 1 = 완전일치
func HistogramMatchingFromFile(img1Name string, img2Name string) float32 {

	mat := gocv.IMRead(path.Join(util.ROOT_PATH, "img", img1Name), 1)
	mat2 := gocv.IMRead(path.Join(util.ROOT_PATH, "img", img2Name), 1)

	return histogramMatching(mat, mat2)
}
func HistogramMatchingFromImage(img1 image.Image, img2 image.Image) float32 {

	mat, err := gocv.ImageToMatRGB(img1)
	if err != nil {
		panic(err)
	}
	mat2, err := gocv.ImageToMatRGB(img2)
	if err != nil {
		panic(err)
	}

	result := histogramMatching(mat, mat2)

	fmt.Println("Histogram Result Value: ", result)

	return result
}

func histogramMatching(img1 gocv.Mat, img2 gocv.Mat) float32 {

	matResult := calcHistogram(img1)

	mat2Result := calcHistogram(img2)

	return gocv.CompareHist(matResult, mat2Result, gocv.HistCmpCorrel)
}

func calcHistogram(source gocv.Mat) gocv.Mat {

	channel := []int{1}
	sizes := []int{256}
	ranges := []float64{0, 256}

	resultMat := gocv.NewMat()

	gocv.CalcHist([]gocv.Mat{source}, channel, gocv.NewMat(), &resultMat, sizes, ranges, false)

	return resultMat
}
