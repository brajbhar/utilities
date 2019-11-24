package com.citiustech.rxjava;

import io.reactivex.Flowable;

public class ReactiveJavaDemo {

	public static void main(String[] args) {
		Flowable.just("Hello World").subscribe(System.out::println);
	}

}
