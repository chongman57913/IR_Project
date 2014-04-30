import java.io.*;
import java.util.*;

/*
 * Building inverted index 
 * 
 */

public class InvertedIndex{
	private static HashMap<String,Integer> stopwordDict;
	
	public static void main(String args[]){
		GetStopwordDict();
		CreateInvertedIndex();
	}
	
	private static void CreateInvertedIndex(){
		HashMap<String,ArrayList<DocEntry>> termIndex = new HashMap<String,ArrayList<DocEntry>>();
		
		HashMap<String,Integer> CollectionDF = new HashMap<String, Integer>(); //store DF
		HashMap<Integer,HashMap<String,Integer>> CollectionTF = new HashMap<Integer,HashMap<String,Integer>>();
		//store TF
		
		try{
			//get all the document
			File docPath = new File("document/");
			File[] allDoc = docPath.listFiles();
			for(int i=0;i<allDoc.length;i++){
				if(allDoc[i].getName().startsWith(".")) //avoid .DS_STORE
					continue;
				
				FileInputStream fStream = new FileInputStream("document/" + allDoc[i].getName());
				DataInputStream in = new DataInputStream(fStream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				
				Stemmer s = new Stemmer();
				
				Tweet t = new Tweet(br.readLine()); //get tweet
				br.close();
				
				String[] tokens = t.GetContent().split(" ");
				
				for(int j=0;j<tokens.length;j++){
					if(!IgnoreWord(tokens[j]) && !IgnoreWord(RemoveSymbol(tokens[j])) && !RemoveSymbol(tokens[j]).equals("")){
						//allow indexed word, calc TF
						Integer docID = Integer.valueOf(allDoc[i].getName().split(".txt")[0]);
						tokens[j] = s.stemming(RemoveSymbol(tokens[j])).trim(); //handle token
						
						HashMap<String,Integer> oldTFList = CollectionTF.get(docID);
						if(oldTFList == null)
							oldTFList = new HashMap<String,Integer>();
						
						int tokenCount = 0;
						if(oldTFList.get(tokens[j]) != null)
							tokenCount = oldTFList.get(tokens[j]);
					
						
						oldTFList.put(tokens[j], tokenCount + 1); //increase TF
						CollectionTF.put(docID, oldTFList); //update collection TF
						
						//End calc TF
						
						//Calc DF
						int isRepeated = 0;
						for(int k=0;k<j;k++){ //check if word appeared before
							String compare = s.stemming(RemoveSymbol(tokens[k])).trim();
							if(compare.equals(tokens[j])){
								isRepeated = 1;
								break;
							}
						}
						
						if(isRepeated == 0){ //not repeated word, increase DF
							int oldVal = 0;
							if(CollectionDF.get(tokens[j]) != null)
								oldVal = CollectionDF.get(tokens[j]);
							CollectionDF.put(tokens[j], oldVal + 1);
						}
						//end calc DF
					}
				}
			}
		
		}catch(Exception e){ e.printStackTrace(); }
		
	}
	
	private static void GetStopwordDict(){
		//init stop word dictionary
		stopwordDict = new HashMap<String, Integer>();
		try{
			FileInputStream fStream = new FileInputStream("data/stopwords.txt");
			DataInputStream in = new DataInputStream(fStream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = "";
			
			while((line = br.readLine()) != null)
				stopwordDict.put(line,1);
			
			br.close();
		}catch(Exception e){ e.printStackTrace(); }
	}
	
	private static String RemoveSymbol(String s){
		//remove all special symbol
		return s.replaceAll("[^\\p{L}\\p{Nd}]", "").toLowerCase();
		
	}
	
	private static boolean IgnoreWord(String s){
		//check if the word should be ignore
		return IsUserNameOrRT(s) || IsLink(s) || IsStopword(s) || s.trim().equals("");
	}
	
	private static boolean IsUserNameOrRT(String s){
		//String start with '@' & RT
		//ignore user name & RT
		return s.startsWith("@") || s.equals("RT");
	}
	
	private static boolean IsLink(String s){
		//String start with http://
		//ignore link
		return s.startsWith("http://") || s.startsWith("https://");
	}
	
	private static boolean IsStopword(String s){
		//String is a stop word
		//ignore stop word
		if(stopwordDict.containsKey(s))
			return true;
		
		return false;
	}
}