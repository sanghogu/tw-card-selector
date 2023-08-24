package test

import (
	"golan/lib"
	"testing"
)

func TestGoCv(t *testing.T) {

	print(lib.HistogramMatching("blue.png", "red.png"))
}
