package Codes;

import java.util.ArrayList;

public class HammingCode {
	public int[] code = null;
	public int actualCode;
	public double m = 0;
	public double k = 0;
	public double n = 0;
	public String enteredCode;
	ArrayList<Integer> parityLocations = new ArrayList<Integer>();// stores location of the parity bits

	public HammingCode(String enteredCode, int m) {
		this.enteredCode = enteredCode;
		this.m = m;
	}

	public void createTheHammingCode() {
		calculateN();
		calculateK();
		code = new int[(int) n];
		String[] splitter = enteredCode.split("");//splits the code up

		int power = 0;
		int splitterLocation = 0;
		for (int i = 0; i < code.length; i++) {//loops throught
			if (i + 1 == (int) Math.pow(2, power)) {//if parity location, add
				parityLocations.add(i + 1);
				power++;
			} else {
				if (splitterLocation >= splitter.length) {//if going over
					code[i] = 0;
				} else {
					code[i] = Integer.parseInt(splitter[splitterLocation]);//set to value
					splitterLocation++;
				}

			}

		}
	}

	public void calculateTheParity() {
		int power = 0;
		for (int i = 0; i < parityLocations.size(); i++) {
			int mover = parityLocations.get(i) + 1;//how many bits to move over
			int taker = parityLocations.get(i);//how many to calculate in one sweep
			int j = parityLocations.get(i) - 1;//where in the array
			int total = 0;//value from calculation of parity bit
			while (j < code.length) {
				if (j + 1 == (int) Math.pow(2, power)) {//if parity bit skip
					if (taker > 1) {//unless taker needs to calculate
						for (int q = j; q < j + taker - 1; q++) {
							total += code[q + 1];//skip parity bit and calculate
						}
						j += taker - 1;
						j += mover;//move once checked
					} else {
						j += mover;
						
					}
				} else {
					if (taker > 1) {//if not a skip the do same without skip
						for (int q = j; q < j + taker; q++) {
							if ((q) < code.length) {
								total += code[q];
							} else {
								break;
							}

						}
						j += taker;
						j += mover;
					} else {
						total += code[j];//or if only one take that value and move on
						j += mover;
						
					}
				}

			}
			power++;
			code[parityLocations.get(i) - 1] = (total % 2);

		}
	}

	public int[] calculateTheParityDouble(int[] checkCode) {//same as method above but returns
		int power = 0;
		for (int i = 0; i < parityLocations.size(); i++) {
			int mover = parityLocations.get(i) + 1;
			int taker = parityLocations.get(i);
			int j = parityLocations.get(i) - 1;
			int total = 0;
			while (j < checkCode.length) {
				if (j + 1 == (int) Math.pow(2, power)) {
					if (taker > 1) {
						for (int q = j; q < j + taker - 1; q++) {
							total += checkCode[q + 1];

						}
						j += taker - 1;
						j += mover;
					} else {
						j += mover;
						j += taker;
					}
				} else {
					if (taker > 1) {
						for (int q = j; q < j + taker; q++) {
							if ((q) < checkCode.length) {
								total += checkCode[q];
							} else {
								break;
							}

						}
						j += taker;
						j += mover;
					} else {
						total += checkCode[j];
						j += mover;
						j += taker;
					}
				}

			}
			power++;
			checkCode[parityLocations.get(i) - 1] = (total % 2);

		}

		return checkCode;
	}

	public void calculateN() {
		double n = Math.pow(2, m) - 1;
		this.n = n;
	}

	public void calculateK() {
		double k = Math.pow(2, m) - m - 1;
		this.k = k;
	}

	public int[] checkIfCodeCorrect(int[] checkCode) {
		int numberOfErrors = 0;

		for (int i = 0; i < checkCode.length; i++) {
			if (checkCode[i] == code[i]) {//if not the same
//didn't work for some reason so this way fixes it
			} else {
				numberOfErrors++;
			}
		}
		if (numberOfErrors == 1) {//if only one error go to next method
			return fixTheCode(checkCode, numberOfErrors);
		}
		return checkCode;
	}

	public int[] fixTheCode(int[] checkCode, int errors) {

		ArrayList<Integer> parity = new ArrayList<Integer>();//stores parity values

		checkCode = updateTheParity(checkCode);//update parity bits

		for (int i = 0; i < parityLocations.size(); i++) {
			parity.add(checkCode[parityLocations.get(i) - 1]);//add values
		}
		int power = 0;
		int value = 0;
		for (int i = 0; i < parity.size(); i++) {
			value += ((int) Math.pow(2, power) * (parity.get(i)));//calculate value
			power++;
		}
		if (value > 0) {//cos array starts at 0 not 1
			value--;
		}

		if (checkCode[value] == 0) {//flip bit at incorrect location
			checkCode[value] = 1;
		} else {
			checkCode[value] = 0;
		}

		return calculateTheParityDouble(checkCode);//return with redone parity bits
	}

	public int[] updateTheParity(int[] checkCode) {//similar to other parity checkers
		for (int i = 0; i < parityLocations.size(); i++) {
			int mover = parityLocations.get(i);
			int taker = parityLocations.get(i);
			int j = parityLocations.get(i) - 1;
			int total = 0;
			while (j < checkCode.length) {
				if (taker > 1) {
					for (int q = j; q < j + taker; q++) {
						if ((q) < checkCode.length) {
							total += checkCode[q];
						} else {
							break;
						}

					}
					j += taker;
					j += mover;
				} else {
					total += checkCode[j];
					j += mover;
					j += taker;
				}
			}
			checkCode[parityLocations.get(i) - 1] = (total % 2);
		}

		return checkCode;
	}
}
