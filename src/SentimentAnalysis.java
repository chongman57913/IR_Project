import java.io.*;
import java.util.Hashtable;

public class SentimentAnalysis{
	private Hashtable<String,Integer> wordDict;
	private Hashtable<String,int[]> textData;
	
	public SentimentAnalysis(){
		wordDict = GetSentimentWord();
		HandleTextData();
	}
	
	private Hashtable<String,Integer> GetSentimentWord(){
		Hashtable<String,Integer> wordDict = new Hashtable<String,Integer>();
		try{
			
			FileInputStream fstream = new FileInputStream("data/sentiment_data/positive-words.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = "";
			
			while((line = br.readLine()) != null){
				if(!line.startsWith(";")) //not comment
					wordDict.put(line, 1);
			}
			
			br.close();
			in.close();
			fstream.close();
			
			fstream = new FileInputStream("data/sentiment_data/negative-words.txt");
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
			
			while((line = br.readLine()) != null){
				if(!line.startsWith(";"))
					wordDict.put(line, 2);
			}
			
			br.close();
			in.close();
			fstream.close();
			
		}catch(Exception e){ e.printStackTrace(); }
		
		return wordDict;
	}
	
	private void HandleTextData(){
		textData = new Hashtable<String,int[]>();

		//read positive data
		File posPath = new File("data/sentiment_data/pos/");
		File[] posFile = posPath.listFiles();

		for(int i=0;i<posFile.length;i++){
			//System.out.println(posFile[i].getName());
			try{
				FileInputStream fstream = new FileInputStream("data/sentiment_data/pos/" + posFile[i].getName());
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String line = "";
						
				while((line = br.readLine()) != null){
					String[] lineWords = line.split(" ");
					for(int j=0;j<lineWords.length;j++){
						if(wordDict.containsKey(lineWords[j])){
							//count words here
							if(!textData.containsKey(lineWords[j]))
								textData.put(lineWords[j], new int[3]);
							int[] d = textData.get(lineWords[j]);
							d[0]++;
							d[1]++;
							textData.put(lineWords[j], d);
						}
					}
				}
				
				in.close();
				br.close();
				fstream.close();
				//break;
			}catch(Exception e){ e.printStackTrace(); }
		}
		

		//read negative data
		File negPath = new File("data/sentiment_data/neg/");
		File[] negFile = negPath.listFiles();

		for(int i=0;i<negFile.length;i++){
			//System.out.println(posFile[i].getName());
			try{
				FileInputStream fstream = new FileInputStream("data/sentiment_data/neg/" + negFile[i].getName());
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String line = "";
						
				while((line = br.readLine()) != null){
					String[] lineWords = line.split(" ");
					for(int j=0;j<lineWords.length;j++){
						if(wordDict.containsKey(lineWords[j])){
							//count words here
							if(!textData.containsKey(lineWords[j]))
								textData.put(lineWords[j], new int[3]);
							int[] d = textData.get(lineWords[j]);
							d[0]++;
							d[2]++;
							textData.put(lineWords[j], d);
						}
					}
				}
				
				in.close();
				br.close();
				fstream.close();
				//break;
			}catch(Exception e){ e.printStackTrace(); }
		}
	}
	
	public int PredictSentiment(String t){
		double pPos = 1;
		double pNeg = 1;
		
		String[] words = t.split(" ");
		for(int i=0;i<words.length;i++){
			if(wordDict.containsKey(words[i])){ // is sentiment word
				int[] wordData = textData.get(words[i]);
				if(wordData != null){ //test data has this word
					pPos *= Double.valueOf(wordData[1]) / Double.valueOf(wordData[0]);
					pNeg *= Double.valueOf(wordData[2]) / Double.valueOf(wordData[0]);
				}
			}
		}
		System.out.println(pPos + "," + pNeg);
		
		if(pPos > pNeg)
			return 1;
		
		return 0;
	}
}