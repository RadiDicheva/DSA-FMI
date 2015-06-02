import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public class BookIndexer61666 implements IBookIndexer{

	private HashMap<String, LinkedList<Integer>> wordMap = new HashMap<String, LinkedList<Integer>>();
	
	@Override
	public void buildIndex(String bookFilePath, String[] keywords, String indexFilePath){
		
		Arrays.sort(keywords, String.CASE_INSENSITIVE_ORDER);
		List<String> keywordsSorted = new ArrayList<String>(Arrays.asList(keywords));
		
		try {
			Path p = Paths.get(bookFilePath);
			Scanner scan = new Scanner(p);
			String pattern = "[^=0-9A-Za-z-]";
			scan.useDelimiter(pattern);
			String word, followingWord;
			int page = 0;
			
			while(scan.hasNext()){
				word = scan.next();
				if(word.equals("===")){
					followingWord = scan.next();
					if(!followingWord.equals("Page")){
						wordMapFiller(page, word);
						wordMapFiller(page, followingWord);	
					} else {
						page = Integer.parseInt(scan.next());
						word = scan.next();
					}
				} else {
					wordMapFiller(page, word);
				}
			}
			
			scan.close();
			
		} catch (IOException e) {
			System.out.println("IOException thrown: " + e);
		}
		
		try{
			Writer fw = new FileWriter(indexFilePath);
			Writer bw = new BufferedWriter(fw);
			bw.write("INDEX" + "\r\n");
			
			for(int i = 0; i <= keywordsSorted.size()-1; i++){
				if(wordMap.get(keywordsSorted.get(i).toLowerCase(Locale.getDefault())) != null){
					List<Integer> pageArrayList = new ArrayList<Integer>(wordMap.get(keywordsSorted.get(i).toLowerCase(Locale.getDefault())));
					String paging = " ";		
					int start, end;
					start = 0;
					end = 0;
					
					for(int j = 0; j <= pageArrayList.size()-1; j++){
						if (pageArrayList.get(j) != end) {
							if(start == 0){
								start = pageArrayList.get(j);
								end = pageArrayList.get(j);
							} else {
								if(pageArrayList.get(j) == end + 1){
									end = pageArrayList.get(j);
								} else {
									if(start != end){
										paging = paging + String.valueOf(start) + "-" + String.valueOf(end) + ",";
										start = pageArrayList.get(j);
										end = pageArrayList.get(j);
									} else {
										paging = paging + String.valueOf(start) + ",";
										start = pageArrayList.get(j);
										end = pageArrayList.get(j);
									}
								}
							}
						}
					}
					
					if(start != end){
						paging = paging + String.valueOf(start) + "-" + String.valueOf(end) + "\r\n";
					} else {
						paging = paging + String.valueOf(start) + "\r\n";
					}
					
					bw.write(keywordsSorted.get(i) + "," + paging);
				}
			}
			
			bw.close();
			
		} catch (IOException e){
			System.out.println("IOException thrown: " + e);
		}
	}
	
	private void wordMapFiller(int page, String word){
		if(wordMap.get(word.toLowerCase(Locale.getDefault())) == null){
			LinkedList<Integer> list1 = new LinkedList<Integer>();
			list1.add(page);
			wordMap.put(word.toLowerCase(Locale.getDefault()), list1);
		} else {
			LinkedList<Integer> list2 = wordMap.get(word.toLowerCase(Locale.getDefault()));
			list2.add(page);
			wordMap.put(word.toLowerCase(Locale.getDefault()), list2);
		}
	}
}