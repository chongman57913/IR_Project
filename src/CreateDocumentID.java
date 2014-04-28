/*
 * 	CreateDocumentID.java
 * 	Create Document ID from content.txt
 * 	
 */

import java.io.*;

class CreateDocumentID{
	static String SOURCE_PATH = "data/twitter_data/context.txt";
	static String DOC_PATH = "document/";
	
	public static void main(String args[]){
		try{
			int counter = 1;
			
			FileInputStream fStream = new FileInputStream(SOURCE_PATH);
			DataInputStream in = new DataInputStream(fStream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = "";
			
			while((line = br.readLine()) != null){
				//System.out.println(line);
				PrintWriter writer = new PrintWriter(DOC_PATH + counter++ + ".txt");
				writer.write(line);
				writer.close();
			}
			
			br.close();
		}catch(Exception e){ e.printStackTrace(); }
	}
}