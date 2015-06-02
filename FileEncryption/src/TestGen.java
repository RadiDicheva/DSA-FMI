import java.io.FileOutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Georgi Gaydarov
 *
 */
public abstract class TestGen {
	private static void dumpKey(String where, List<Character> key)
			throws Exception {
		FileOutputStream keyOut = new FileOutputStream(where);
		for (char c : key) {
			keyOut.write(c);
		}
		keyOut.close();
	}

	public static void main(String[] args) throws Exception {
		FileEncoder61666 coder = new FileEncoder61666();
		LinkedList<Character> key = new LinkedList<>();
		for (int i = 0; i < 256; i++) {
			key.add((char) i);
		}
		long time1 = System.nanoTime();
		for (int i = 0; i < 10; i++) {

			Collections.shuffle(key); 
			dumpKey("key1.txt", key);
			coder.encode("in1.jpg", "out1.enc", key);
			
			Collections.shuffle(key); 
			dumpKey("key2.txt", key);
			coder.encode("in2.rar", "out2.enc", key);
			
			Collections.shuffle(key); 
			dumpKey("key3.txt", key);
			coder.encode("in3.rar", "out3.enc", key);
			
			Collections.shuffle(key); 
			dumpKey("key4.txt", key);
			coder.encode("in4.pdf", "out4.enc", key); 
		}
		long elapsedTime1 = (System.nanoTime() - time1);
		System.out.println(elapsedTime1/10);
	}
}
