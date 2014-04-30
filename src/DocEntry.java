public class DocEntry{
	private Integer docID;
	private double docWeight;
	
	public DocEntry(int id, double w){
		docID = id;
		docWeight = w;
	}
	
	public Integer GetDocumentID(){
		return docID;
	}
	
	public double GetDocumentWeight(){
		return docWeight;
	}
	
	public void EditDocumentID(Integer newID){
		docID = newID;
	}
	
	public void EditDocumentWeight(double newWeight){
		docWeight = newWeight;
	}
}