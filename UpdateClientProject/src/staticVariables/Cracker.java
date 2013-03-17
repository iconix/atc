package staticVariables;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();	
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}
	
	private static final int DEFAULT_NUM_WORKERS = 8;
	private	static final int DEFAULT_PASSWORD_LENGTH = 5;
	
	protected static int passwordLength;
	protected static int numWorkers;  //the number of workers
	protected static List<String> validInputStrings;
	protected static CountDownLatch latch;
	
	public static void main(String[] args) {
		if (args == null || args.length == 0) {
			System.err.println("Empty argument");
			System.exit(1);
		}
		
		//generating mode
		if (args.length == 1) {
			try {
				System.out.println(generatingHashValue(args[0]));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return;
		} 
		
		//cracking mode
		if (args.length < 3) {
			System.err.println("Invalid argument input");
			System.exit(1);
		}
		
		//parse the arguments
		String hashValue =  args[0];
		try {
			passwordLength = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			passwordLength = DEFAULT_PASSWORD_LENGTH;
		}
		if (passwordLength <= 0) passwordLength = DEFAULT_PASSWORD_LENGTH;
		
		try {
			numWorkers = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			numWorkers = DEFAULT_NUM_WORKERS;
		}
		if (numWorkers <= 0) numWorkers = DEFAULT_NUM_WORKERS;
		
		//create a latch to know when the program finished
		latch = new CountDownLatch(numWorkers);
		
		//call the cracker workers
		for (int i = 0; i < numWorkers; i++) {
			CrackerWorker worker = new CrackerWorker(numWorkers, i, passwordLength, latch, hashValue);
			new Thread(worker).start();
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("ALL DONE");
	}
	
	/**
	 * This function take a string argument and return its hash value
	 * @param input string
	 * @return its corresponding hash value
	 */
	public static String generatingHashValue(String str) throws NoSuchAlgorithmException{
		MessageDigest digester = MessageDigest.getInstance("SHA");
		digester.reset();
		digester.update(str.getBytes());
		return hexToString(digester.digest());
	}
	
	/**
	 * Cracker worker class. Handle most of the work of cracking the passwords
	 */
	public static class CrackerWorker implements Runnable {
		 
		private final char[] CHARS;
		private final int numWorkers;
		private final int workerNumber;
		private final int maxPasswordLength;
		private final CountDownLatch latch;
		private final String hashValue;
		private MessageDigest digester;
		
		/**
		  * Class constructors
		  * @param number of workers thread
		  * @param worker ID number
		  * @param maximum password length considering for the search
		  * @param count down latch to know when all the workers are finished
		  * @param hash value we wish to obtain.
		  */
		public CrackerWorker(int numWorkers, int workerNumber, int maxPasswordLength, CountDownLatch latch, String hashValue) {
			this.CHARS = Cracker.CHARS;
			this.numWorkers = numWorkers;
			this.workerNumber = workerNumber;
			this.maxPasswordLength = maxPasswordLength;
			this.latch = latch;
			this.hashValue = hashValue;
		}
		
		/**
		 * Get the starting character index to do search on
		 * @return the first char prefix to so search on for this worker 
		 */
		private int getStartingCharIndex() {
			return workerNumber * (CHARS.length / numWorkers);
		}
		
		/**
		 * Get the index of the char in the char array to stop the search.
		 * Note that we don't search on the char whose index is the return value
		 * @return ending char index, excluding
		 */
		private int getEndingCharIndex() {
			if (numWorkers == workerNumber) return CHARS.length;
			return (workerNumber + 1) * (CHARS.length / numWorkers);
		}
		
		/**
		 * find the hash value of all strings with a given character prefix
		 * If the hash value of the string is the same as required has value, then 
		 * print it out.
		 * @param the string to compute the hash value
		 */
		private void findPasswordWithPrefixStr(String str) {
			if (str.length() > maxPasswordLength) return;
			digester.reset();
			digester.update(str.getBytes());
			if (hexToString(digester.digest()).equals(hashValue))
				System.out.println(str);
			for (int index = 0; index < CHARS.length; index++ ) {
				String newStr = str + CHARS[index];
				findPasswordWithPrefixStr(newStr);
			}
		}
		
		/**
		 * Run implementation of the runnable
		 */
		@Override
		public void run() {
			try {
				digester = MessageDigest.getInstance("SHA");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			for (int index = getStartingCharIndex(); index < getEndingCharIndex(); index++) {
				String str = "" + CHARS[index];
				findPasswordWithPrefixStr(str);
			}
			latch.countDown();
		}
		
	}
	
	
	// possible test values:
	// a 86f7e437faa5a7fce15d1ddcb9eaeaea377667b8
	// fm adeb6f2a18fe33af368d91b09587b68e3abcb9a7
	// a! 34800e15707fae815d7c90d49de44aca97e2d759
	// xyz 66b27417d37e024c46526c2f6d358a754fc552f3

}
