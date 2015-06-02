import java.util.LinkedList;
import java.io.*;

/**
 * 
 * @author Radka Dicheva
 *
 */

public class FileEncoder61666 implements FileEncoderFN {
	
	public static boolean isPrimeNumber(int number) {
		if (number == 2 || number == 3) {
			return true;
		}
		if (number % 2 == 0) {
			return false;
		}
		int sqrt = (int) Math.sqrt(number) + 1;
		for (int i = 3; i < sqrt; i += 2) {
			if (number % i == 0) {
				return false;
			}
		}
		return true;
	}

	public void encode(String sourceFile, String destinationFile,
			LinkedList<Character> key) {

		BufferedInputStream inputFile;
		BufferedOutputStream outputFile;
		
		try {
			inputFile = new BufferedInputStream(new FileInputStream(sourceFile));
			outputFile = new BufferedOutputStream(new FileOutputStream(destinationFile));

			int counter = 0;
			int byt;
			while ((byt = inputFile.read()) != -1) {
				if (!isPrimeNumber(counter)) {
					outputFile.write(key.get(byt));		
				} else {
					outputFile.write(byt);
				}
				counter++;
			}
			
			inputFile.close();
			outputFile.close();
			
		} catch (Exception e) {
			System.out.println("Exception thrown :" + e);
		}
	}

	public void decode(String encodedFile, String destinationFile,
			LinkedList<Character> key) {

		BufferedInputStream inputFile;
		BufferedOutputStream outputFile;
		
		try {
			inputFile = new BufferedInputStream(new FileInputStream(encodedFile));
			outputFile = new BufferedOutputStream(new FileOutputStream(destinationFile));

			int counter = 0;
			int byt;
			while ((byt = inputFile.read()) != -1) {
				if (!isPrimeNumber(counter)) {
					outputFile.write(key.indexOf((char) byt));	
				} else {
					outputFile.write(byt);
				}
				counter++;
			}
			
			inputFile.close();
			outputFile.close();
			
		} catch (Exception e) {
			System.out.println("Exception thrown :" + e);
		}
	}
}