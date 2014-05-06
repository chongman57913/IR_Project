import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

class main_test{
	
	public static void main(String args[]){
		
		Query query  = new Query(new String("nba football"));
		
		int []table = query.getDocFromQuery();

		for (int i = 0 ;i<table.length;i++)
		{
//			System.out.println(table[i]);
			try{
				FileInputStream fStream = new FileInputStream("document/" + table[i]+".txt");
				DataInputStream in = new DataInputStream(fStream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				System.out.println(br.readLine());
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
//		String line = "";
//		Tweet t = new Tweet(line);
	}
}