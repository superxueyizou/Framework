package dominant;

import tools.UTILS;
import weka.gui.GUIChooser;




public class DataMiner 
{

	public DataMiner() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public void execute(String fileName)
	{
		try {
			UTILS.CSV2Arff(fileName+"Dataset.csv", fileName+"Dataset.arff");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GUIChooser gc = new GUIChooser();
		gc.showExplorer(fileName+"Dataset.arff");
		
		
	}
	
	

}
