package test

import (
	"golan/lib"
	"testing"
)

func TestGoCv(t *testing.T) {

	print(lib.HistogramMatchingFromFile("blue.png", "red.png"))
}
