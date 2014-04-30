public class DocEntry{
	Integer docID;
	double docWeight;
	
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
}