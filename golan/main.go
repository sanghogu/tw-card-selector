package main

import (
	"C"
	"github.com/rthornton128/goncurses"
	"log"
)

func main() {

	src, err := goncurses.Init()
	if err != nil {
		log.Fatal("init:", err)
	}
	src.Println("TEST")
	defer goncurses.End()

}
