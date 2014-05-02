import java.io.*;
import java.util.HashMap;

public class Token{
	private HashMap<String,Integer> stopwordDict;
	
	public Token(){
		GetStopwordDict();
	}
	
	public static String RemoveSymbol(String s){
		//remove all special symbol
		return s.replaceAll("[^\\p{L}\\p{Nd}]", "").toLowerCase().trim();
		
	}
	
	public static String HandleTerm(String s){
		return new Stemmer().stemming(RemoveSymbol(s).toLowerCase().trim(););
	}
	
	public boolean IgnoreWord(String s){
		//check if the word should be ignore
		return IsUserNameOrRT(s) || IsLink(s) || IsStopword(s) || s.trim().equals("");
	}
	
	private boolean IsUserNameOrRT(String s){
		//String start with '@' & RT
		//ignore user name & RT
		return s.startsWith("@") || s.equals("RT");
	}
	
	private boolean IsLink(String s){
		//String start with http://
		//ignore link
		return s.startsWith("http://") || s.startsWith("https://");
	}
	
	private boolean IsStopword(String s){
		//String is a stop word
		//ignore stop word
		if(stopwordDict.containsKey(s))
			return true;
		
		return false;
	}
	
	private void GetStopwordDict(){
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
}