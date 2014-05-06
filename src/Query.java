import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class Query{
	
	private static String query = "";
	private static HashMap<String,Double> tf = new HashMap<String, Double>();
	private static HashMap<String,Double> norm = new HashMap<String, Double>();
	private static int docID[] ;
	/*
	 * N need to modify later
	 */
	private int  N = 50000;
	//constructor 
	public Query(String s) {
		query = s;
	}
	
	//main processing 
	public int[] getDocFromQuery(){
		
		splitQuery();
		normQuery();
		buildScoreTable();

		return docID;
	}
	
	//split the query
	private void splitQuery(){

		//get the token of the stemming query
		Token t = new Token();
		String[] token = query.split(" ");

		for (int i = 0; i < token.length;i++)
		{
			//find it is a stop word or not
			if (t.IgnoreWord(token[i]))
				continue;
			else {
				
				String terms = new String(Token.HandleTerm(token[i]));
				//increase the tf if key exists
				if (tf.containsKey(terms)){
					tf.put(terms, tf.get(terms)+1);
				}
				// insert a new key 
				else 
					tf.put(terms, 1.0);
			}
		}
	}
	//calculate the query normalization
	private void normQuery(){
		norm = tf;
		double norm_weight = 0.0;
		for (String key:tf.keySet())
		{
			// get IDF from the document based on current key 
			try {
				
				String path ="./index/" + key.trim() +".txt";
				FileInputStream fStream = new FileInputStream(path);
				DataInputStream in = new DataInputStream(fStream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in)); 
				
				String line = (br.readLine());
				int DF = Integer.parseInt(line);
				double IDF = Math.log10(N / DF);
				
				//calculate the weight of the query
				//wf = tf*idf
				double weight = (1 + Math.log10(tf.get(key))) * IDF ;
				norm.put(key, weight);
				norm_weight += Math.pow(weight, 2.0);

			}catch(Exception e){
				System.out.println("Cant read file!");
			}

		}
		//calculate the normalization of the query and
		// put into the normalization table.
		norm_weight = Math.sqrt(norm_weight); 
		for (String key:norm.keySet()){
			norm.put(key, (norm.get(key) / norm_weight));
//			System.out.println(key + ":" + norm.get(key));
		}	
	}
	
	//calculate the score of the query
	//based on - ([index].txt) 
	//read line by line and get the docID and normalization
	//multipy the normalization with HashMap norm then get the final score list 
	private void buildScoreTable(){
		
		double final_score[] = new double[N+1];
		int num = 0;
	
		for (String key:norm.keySet())
		{
			
			try {
				 
				String path ="./index/" + key.trim() +".txt";
				FileInputStream fStream = new FileInputStream(path);
				DataInputStream in = new DataInputStream(fStream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in)); 
				String line = "";
				
				//variable to ignore the first row
				int i = 1;

				while ((line = br.readLine()) != null)
				{
					//ignore the first row
					if (i == 1){
						i++;
						continue;
					}
					//e.g.line 4:999 0.4314243
				    //final_score[999] += 0.4314243
					else{
						
						String[] docID_norm = line.split(",");
						
						//count the number of document need to be consider
						if (final_score[Integer.parseInt(docID_norm[0].trim())] == 0.0)
							num++;
						
						//qi*di
						final_score[Integer.parseInt(docID_norm[0].trim())] += Double.parseDouble(docID_norm[1].trim()) 
								* norm.get(key);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		double final_score_index[][] = new double[num][2];
		int index = 0;

		//Modify the new final score of deleting the zero index
		for (int i = 1;i < N + 1;i++){

			if (final_score[i] > 0){
			 final_score_index[index][0] = i;
			 final_score_index[index++][1] =  final_score[i];
			}		
		}
		//rank the result of high score to low score
		Arrays.sort(final_score_index, new Comparator<double[]>() {
		    public int compare(double[] a, double[] b) {
		        return Double.compare(b[1], a[1]);
		    }
		});
		
		docID = new int[final_score_index.length];
	
		//return the index of ranking the document
		for (int i = 0; i < final_score_index.length;i++)
			docID[i] =  (int) final_score_index[i][0];
		
//		for (int i = 0;i < final_score_index.length;i++){
//			 	System.out.print(final_score_index[i][0]+":");
//				System.out.println(final_score_index[i][1]);		
//		}
		 
	}
}