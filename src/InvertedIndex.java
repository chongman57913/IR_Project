import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/*
 * Building inverted index 
 * 
 */

@SuppressWarnings("static-access")
public class InvertedIndex{
	private static Token token;
	
	public static void main(String args[]){
		token = new Token();
		
		CreateInvertedIndex();
	}
	

	private static void CreateInvertedIndex(){
		HashMap<String,ArrayList<DocEntry>> termIndex = new HashMap<String,ArrayList<DocEntry>>(); //store index data
		
		HashMap<String,Integer> CollectionDF = new HashMap<String, Integer>(); //store DF
		HashMap<Integer,HashMap<String,Integer>> CollectionTF = new HashMap<Integer,HashMap<String,Integer>>();
		//store TF
		
		try{
			//get all the document
			File docPath = new File("document/");
			File[] allDoc = docPath.listFiles();
			int docCounter = 0;
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
					if(!token.IgnoreWord(tokens[j]) && !token.IgnoreWord(token.RemoveSymbol(tokens[j])) && !token.RemoveSymbol(tokens[j]).equals("")){
						//token allow to index, calc TF
						Integer docID = Integer.valueOf(allDoc[i].getName().split(".txt")[0]);
						tokens[j] = s.stemming(token.RemoveSymbol(tokens[j])); //handle token
						
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
							String compare = s.stemming(token.RemoveSymbol(tokens[k]));
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
						} //end calc DF
					}
				}
				
				docCounter++; //count document number
			}
			
			//calc tf-idf weight
			for(int i=1;i<=docCounter;i++){ //calc all document
				HashMap<String,Integer> docTF = CollectionTF.get(i);
				
				if(docTF == null)
					continue;
				
				//calc TF-IDF
				double vectorLength = 0;
				HashMap<String,Double> docWeight = new HashMap<String,Double>(); //store the term weight of document
				
				for(Entry<String,Integer> entry : docTF.entrySet()){
					String term = entry.getKey();
					Integer TF = entry.getValue();
					
					if(CollectionDF.get(term) == null)
						continue;
					
					double weight = (1 + Math.log10(TF)) * (Math.log10(docCounter / CollectionDF.get(term)));
					vectorLength += weight * weight;
					
					docWeight.put(term, weight);
				}
				vectorLength = Math.sqrt(vectorLength);
				
				//normalization, v = v / |v|
				for(Entry<String,Double> entry : docWeight.entrySet()){
					String term = entry.getKey();
					Double weight = entry.getValue() / vectorLength;
					
					ArrayList<DocEntry> oldList = termIndex.get(term);
					if(oldList == null)
						oldList = new ArrayList<DocEntry>();
					
					oldList.add(new DocEntry(i,weight));
					termIndex.put(term, oldList);	
				}
				//break; //testing
			}
			
			//create index
			//first line is DF
			//others are docID,TF-IDF
			for(Entry<String,ArrayList<DocEntry>> entry : termIndex.entrySet()){
				String term = entry.getKey();
				ArrayList<DocEntry> docList = entry.getValue();
				
				PrintWriter writer = new PrintWriter("index/" + term +  ".txt");
				//write DF
				writer.write(CollectionDF.get(term) + "\n");
				
				//write doc ID & weight
				for(int i=0;i<docList.size();i++) //loop list
					writer.write(docList.get(i).GetDocumentID() + "," + docList.get(i).GetDocumentWeight() + "\n");
				
				writer.close();
			}
		}catch(Exception e){ e.printStackTrace(); }
	}
}