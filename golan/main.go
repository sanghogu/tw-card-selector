package main

/*
#include <stdio.h>
void hello() {
	printf("Hello");
}
*/

import (
	"C"
	"golan/lib"
)

func main() {

	C.hello()

	lib.KeyboardInit()

}
