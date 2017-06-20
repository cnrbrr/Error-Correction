package Codes;

//import java.util.ArrayList;
import java.util.Random;

public class BurstErrors {
	public float pgb = 0;
	public float pbg = 0;
	public float p = 0;
	public int[] burstCode;
	public boolean state = true;
	public int errors = 0;

	public BurstErrors(float p, float pbg, float pgb, int[] burstCode) {
		this.p = p;// probability
		this.pbg = pbg;// bad to good
		this.pgb = pgb;// good to bad
		this.burstCode = burstCode;// initial code value
	}

	public int[] generateBurstCode() {
		for (int i = 0; i < burstCode.length; i++) {
			burstCode[i] = sendingBit(burstCode[i]);// send the individual bits
													// to sending bit and store
													// in order
		}

		return burstCode;
	}

	public int sendingBit(int currentBit) {
		Random r = new Random();
		if (!state) {// if in bad state
			if (r.nextFloat() <= p) {// flips the bit if the state has changed
										// and probability is correct
				errors++;
				if (currentBit == 0) {
					currentBit = 1;
				} else {
					currentBit = 0;
				}
				if (r.nextFloat() <= pbg) {// could return to good state
					state = true;
				}

			}
		} else {
			if (r.nextFloat() <= pgb) {// sets to bad state
				state = false;
			}
		}
		return currentBit;
	}

	public void printCode() {// prints out the current code nicely
		System.out.println("\n----------------------------------------------------------------------");
		System.out.println("\n----------------------------------------------------------------------");
		for (int i = 0; i < burstCode.length; i++) {
			System.out.print(burstCode[i]);
		}
		System.out.println("\n----------------------------------------------------------------------");

	}
}
