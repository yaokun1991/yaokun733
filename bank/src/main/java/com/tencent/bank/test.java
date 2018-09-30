package com.tencent.bank;

public class test {
	public static void main(String[] args) {
		String s1 = "1923-21";
		String[] split = s1.split("-");

		Integer integer1 = Integer.valueOf(s1);
		System.out.println(integer1);

	}
}