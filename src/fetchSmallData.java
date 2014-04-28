import java.io.*;

class fetchSmallData{
	public static void main(String args[]){
		try{
			FileInputStream fstream = new FileInputStream("data/twitter_data/context.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			PrintWriter writer = new PrintWriter("data/twitter_data/small_context.txt");
			
			for(int i=0;i<50000;i++){
				writer.write(br.readLine() + "\n");
			}
			
			writer.close();
			br.close();
			
		}catch(Exception e){ e.printStackTrace(); }
	}
}