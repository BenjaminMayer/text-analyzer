import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TextAnalyzer {
	private String filePath;
	private List<WordCounter> wordCounter;
	
	public static void main(String[] args) {
		
		TextAnalyzer textAnalyser = new TextAnalyzer("src/test.txt");
		
		for(WordCounter wordcounter : textAnalyser.wordCounter) {
			System.out.println("Mot : " + wordcounter.word + " Occurence : " + wordcounter.counter);
			
		}
		WordCounter[] wc = textAnalyser.topWord(10);
		System.out.println("MOT RANG " + wc[12].word + " COUNTER :" + wc[12].counter + "SIZE :" + wc.length);
		
	}

	public TextAnalyzer(String filePath) {
		super();
		this.filePath = filePath;
		String text = "";
		this.wordCounter = new ArrayList();
		
		try{
			InputStream flux=new FileInputStream(filePath); 
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);
			String ligne;
			
			while ((ligne=buff.readLine())!=null){
				
				ligne = ligne.replace(",", "").replace(";", "").replace(".", "").replace("!","").replace("?","").replace(":", "").replace("(","").replace(")", "").replace("«", "").replace("»", "").replace("[", "").replace("]", "").toLowerCase();
				
				System.out.println(ligne);
				text += " " + ligne.toLowerCase();
			}
			buff.close();
			
			}		
			catch (Exception e){
			System.out.println(e.toString());
			}
		System.out.println(text);
		
		String[] words = text.split(" ");
		for(String word : words) {
			int i = 0;
			boolean isRepeat = false;
			for(WordCounter wordCounterUnity : this.wordCounter) {
				
				
				if (word.equals(wordCounterUnity.word)){
					
					this.wordCounter.get(i).counter = this.wordCounter.get(i).counter+1;
					
					isRepeat = true;
					break;
				}else {
					i++;
				}
				
			}
			if(isRepeat == false) {
				this.wordCounter.add(new WordCounter(word,1));
			}
		}
		this.wordCounter.remove(0);
		
	}
	
	public class WordCounter{
		private String word;
		private int counter;
		
		
		
		public WordCounter(String word, int counter){
			super();
			this.word = word;
			this.counter = counter;
		}
		public String getWord() {
			return word;
		}
		public void setWord(String word) {
			this.word = word;
		}
		public int getCounter() {
			return counter;
		}
		public void setCounter(int counter) {
			this.counter = counter;
		}
	
	
		
	}
	
	public class CounterComparator implements Comparator<WordCounter>{

		@Override
		public int compare(WordCounter o1, WordCounter o2) {
			Integer p1 = ((WordCounter) o1).getCounter();
		    Integer p2 = ((WordCounter) o2).getCounter();
		    
		    if (p1 > p2) {
		           return -1;
		       } else if (p1 < p2){
		           return +1;
		       } else {
		           return 0;
		       }
		    
			
		}
		
	}
	
	private WordCounter[] topWord(int n) {
		List<Integer> counters = new ArrayList();;
		List tmp = new ArrayList();
		int max = 0;
		//tmp = this.wordCounter;
		for(WordCounter wordCounterUnity : this.wordCounter) {
			if (!counters.contains(wordCounterUnity.counter)) {
			counters.add(wordCounterUnity.counter);
			}
		}
		max =(int) Collections.max(counters);
		System.out.println(max);
		for(Integer c : counters) {
			System.out.println(c);
		}
		for(int i =1;i<=n;i++) {
			max =(int) Collections.max(counters);
			int j = 0;
			int j2=0;
		
			
			for(Integer c : counters) {
				if(c==max) {
					j=j2;
				}
				
				j2++;
			}
			counters.remove(j);
			System.out.println(counters);
			
			
		}
		System.out.println("MAX : " + max);
		Collections.sort(this.wordCounter, new CounterComparator());
		
		for(WordCounter wordCounterUnity : this.wordCounter) {
		if(wordCounterUnity.counter >= max) {
			tmp.add(wordCounterUnity);
		}
		}
		n = tmp.size();
		WordCounter[] wC = new WordCounter[n];
		for(int i=0;i<n;i++) {
			wC[i]=(WordCounter) tmp.get(i);
		}
		return wC;
		
	}
	

}
