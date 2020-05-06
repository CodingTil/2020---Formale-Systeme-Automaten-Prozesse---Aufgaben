package main

import (
	"fmt"
	"time"
	"regexp"
)

var results [20]bool
var t [20]time.Duration

func main() {
	pattern := "(.*?,){13}z"
	r,_ := regexp.Compile(pattern)
	for i := 1; i <= 20; i++ {
		go func(i int) {
			w := "1,2,3,4,5,6,7,8,9,10,11,12"
			for j:= 0; j < i; j++ {
				w += ",1"
			}
			start := time.Now()
			result := r.MatchString(w)
			tm := time.Now()
			elapsed := tm.Sub(start)
			results[i-1] = result
			t[i-1] = elapsed
		}(i)
	}

	var avg int64
	avg = 0;
	fmt.Println("Index | Result | Duration")
	for i:= 1; i <= 20; i++ {
		fmt.Printf("%v    ",i)
		if i < 10 {
			fmt.Printf(" ")
		}
		fmt.Printf("| %v  ", results[i-1])
		if results[i-1] == true {
			fmt.Printf(" ")
		}
		fmt.Printf("| %v\n", t[i-1])
		avg += t[i-1].Microseconds()
	}

	avg /= 20

	fmt.Printf("Average Time (ms): %v\n", avg)
}