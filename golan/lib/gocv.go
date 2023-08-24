package lib

import (
	"gocv.io/x/gocv"
	"golan/util"
	"path"
)

// return 1에 가까울수록 유사 1 = 완전일치
func HistogramMatching(img1Name string, img2Name string) float32 {

	mat := imageLoad(path.Join(util.ROOT_PATH, "img", img1Name))
	matResult := calcHistogram(mat)

	mat2 := imageLoad(path.Join(util.ROOT_PATH, "img", img2Name))
	mat2Result := calcHistogram(mat2)

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

func imageLoad(fullPath string) gocv.Mat {
	return gocv.IMRead(fullPath, 1)
}
