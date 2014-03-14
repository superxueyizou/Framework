package dominant;

import javax.swing.JOptionPane;

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
		try 
		{
			UTILS.CSV2Arff(fileName+"Dataset.csv", fileName+"Dataset.arff");
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		
		GUIChooser gc = new GUIChooser();
		gc.showExplorer(fileName+"Dataset.arff");		
		
	}
	
	public static void main(String[] args) throws Exception
	{
		String fileName;
		String result = JOptionPane.showInputDialog(null, "Please paste the input file name without \"Dataset.csv\":", "File Name",JOptionPane.PLAIN_MESSAGE);

		fileName=result.trim();
		DataMiner dm = new DataMiner();
		dm.execute(fileName);
	}
	
	
	
	

}
