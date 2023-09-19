package com.company;

import java.util.Scanner;

public class InputUtil {
	public static Integer inputPosInt(Integer mod) {
		Boolean isCorrect = false;
		Integer input = 0;
		String inputStr = "";
		Scanner scan = new Scanner(System.in);
		
		while(!isCorrect) {
			inputStr = scan.nextLine();
			try {
				input = Integer.parseInt(inputStr);
				if(input >= 0) {
					if(mod == 1 && input == 0) {
						System.out.println("Invalid size. Need more than zero.");
						System.out.print("Try Again: ");
					} else {
						isCorrect = true;
					}
				} else {
					System.out.println("Not a Valid Number, please use Positive Integers.");
					System.out.print("Try Again: ");
				}
			} catch (Exception e) {
				System.out.println("Not an Integer.");
				System.out.print("Try Again: ");
			}
		}
		
		return input;
	}
}