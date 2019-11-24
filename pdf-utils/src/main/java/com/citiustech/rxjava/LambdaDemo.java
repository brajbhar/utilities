package com.citiustech.rxjava;

import java.util.Arrays;
import java.util.List;

public class LambdaDemo {

	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
		System.out.println("Iterating Array");
		numbers.forEach((v)->System.out.println(v));
	}

}
