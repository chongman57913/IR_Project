public class Tweet{
	private String content = "";;
	private String date;
	private String time;
	private String user;
	
	public Tweet(String t){
		String[] words = t.split(";");
		if(words.length < 3) //error format
			return;
		date = words[0].split(" ")[0].trim();
		time = words[0].split(" ")[1].trim();
		user = words[1].trim();
		content += words[2].trim();
		
		for(int i=3;i<words.length;i++)  //avoid content contains ';'
			content += words[i];
	}
	
	public void PrintTweet(){
		System.out.println(date);
		System.out.println(time);
		System.out.println(user);
		System.out.println(content);
	}
	
	public String GetDate(){
		return date;
	}
	
	public String GetTime(){
		return time;
	}
	
	public String GetUser(){
		return user;
	}
	
	public String GetContent(){
		return content;
	}
}