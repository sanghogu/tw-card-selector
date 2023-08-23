package lib

import (
	"fmt"
	"gocv.io/x/gocv"
	"golan/util"
	"path"
)

func HistogramMatching() {

	mat := imageLoad(path.Join(util.ROOT_PATH, "img/blue.png"))
	matResult := histogram(mat)

	mat2 := imageLoad(path.Join(util.ROOT_PATH, "img/red.png"))
	mat2Result := histogram(mat2)

	fmt.Println(gocv.CompareHist(matResult, mat2Result, gocv.HistCmpIntersect))
}

func histogram(source gocv.Mat) gocv.Mat {

	channel := []int{1}
	sizes := []int{256}
	ranges := []float64{0, 256}

	resultMat := gocv.NewMat()

	gocv.CalcHist([]gocv.Mat{source}, channel, gocv.NewMat(), &resultMat, sizes, ranges, false)

	re := gocv.NewMat()
	gocv.Normalize(resultMat, &re, 1, 0, gocv.NormL1)
	return re
}

func imageLoad(fullPath string) gocv.Mat {
	return gocv.IMRead(fullPath, 1)
}
