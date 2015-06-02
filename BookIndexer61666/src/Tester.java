public class Tester {
	public static void main(String[] args) {
		BookIndexer61666 bookInd = new BookIndexer61666();
		
		String[] keywords = new String[] { "Devon", "believe", "kid", "did",
				"war", "the", "be", "to", "of", "and", "death", "miracle" };
		
		long start = System.nanoTime();
		bookInd.buildIndex("separatePeace.txt", keywords, "newfile.txt");
		long end = System.nanoTime();
		
		System.out.println("raboti za :" + (end - start) / 1000000);		
	}
}
