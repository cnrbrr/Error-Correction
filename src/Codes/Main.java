package Codes;

import java.util.Random;
import java.util.Scanner;

public class Main {

	private static Scanner scr;

	public static void main(String[] args) {
		//
		// scr = new Scanner(System.in);
		// System.out.println("Please enter the code to be hammed");//code which
		// originally allowed me to enter the code which was to be hammed
		// String code = scr.nextLine();
		// String[] splitCode = code.split("");
		// int m = splitCode.length;
		// try{
		// HammingCode hc = new HammingCode(code, getM(m));
		// hc.createTheHammingCode();
		// System.out.println("\n----------------------------------------------------------------------");
		// hc.calculateTheParity();
		// int[] burstCode = new int[hc.code.length];
		// for(int g = 0; g < hc.code.length; g++){
		// System.out.print(hc.code[g]);
		// burstCode[g] = hc.code[g];
		// }
		//
		// BurstErrors be = new BurstErrors(0.3f, 0.3f,0.1f, burstCode);
		//
		//
		// System.out.println("\n----------------------------------------------------------------------");
		//
		// for(int g = 0; g < hc.code.length; g++){
		// System.out.print(hc.code[g]);
		// }
		// System.out.println("\n----------------------------------------------------------------------");
		// System.out.println("\n----------------------------------------------------------------------");
		// int[] tempCode = be.generateBurstCode();
		// for(int g = 0; g < tempCode.length; g++){
		// System.out.print(tempCode[g]);
		// }
		// hc.checkIfCodeCorrect(tempCode);
		// }catch(NumberFormatException e){
		// System.out.println("That is not a valid number");
		// }
		//
		Random rand = new Random();
		int size = 10;// how many rows
		InterLeaver interleaver = new InterLeaver(size, 7);
		interleaver.setTheSize();
		HammingCode hc = null;
		for (int i = 0; i < size; i++) {// random numbers
			int one = rand.nextInt(2);
			int two = rand.nextInt(2);
			int three = rand.nextInt(2);
			int four = rand.nextInt(2);
			String code = String.valueOf(one) + String.valueOf(two) + String.valueOf(three) + String.valueOf(four);// code

			hc = new HammingCode(code, getM(code.length()));// sends the code to
															// hc
			hc.createTheHammingCode();
			hc.calculateTheParity();
			interleaver.setTheData(hc.code, i);// sets the values in data and
												// check data
		}

		int[] burstCode = new int[size];

		BurstErrors be = new BurstErrors(0.3f, 0.3f, 0.3f, burstCode);
		for (int g = 0; g < hc.code.length; g++) {
			for (int h = 0; h < size; h++) {
				interleaver.data[h][g] = be.sendingBit(interleaver.data[h][g]);// changes in the columns
			}
		}

		int totalEnteredErrors = 0;
		int totalErrorsLeft = 0;
		int totalErrorsCorrected = 0;
		for (int k = 0; k < 100; k++) {
			for (int i = 0; i < size; i++) {
				int[] tempCode = new int[hc.code.length];// so that the code could be checked against it
				int[] checkCode = new int[hc.code.length];// taken from the Original correct row
				for (int j = 0; j < interleaver.count; j++) {
					tempCode[j] = interleaver.data[i][j];
					checkCode[j] = interleaver.checkData[i][j];
				}
				hc.code = checkCode;
				interleaver.updateTheData(hc.checkIfCodeCorrect(tempCode), i);// changes if it can
			}

			totalEnteredErrors += be.errors;// calculates
			totalErrorsLeft += interleaver.checkErrors();
			totalErrorsCorrected += (be.errors - interleaver.checkErrors());
		}

		System.out.println("\n----------------------------------------------------------------------");// results

		System.out.println("TOTAL NUMBER OF ERRORS WHICH OCCURED IN THE BURSTS: " + totalEnteredErrors);
		System.out.println("TOTAL NUMBER OF ERRORS LEFT IN THE INTERLEAVER: " + totalErrorsLeft);
		System.out.println("TOTAL NUMBER OF ERRORS CORRECTED: " + totalErrorsCorrected);
		System.out.println("\n----------------------------------------------------------------------");
	}

	public static int getM(int m) {// gets number of parity bits needed
		if (m > 11) {
			return 5;
		} else if (m > 4) {
			return 4;
		} else if (m > 1) {
			return 3;
		} else {
			return 2;
		}
	}

}
