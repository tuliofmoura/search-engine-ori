import javax.swing.JOptionPane;

import process.PreProcess;
import query.QueryProcessor;
import query.QueryProcessor.MalformedExpressionException;
import query.QueryProcessor.UserQuitException;

public class Main
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
    	try
    	{
    		preProcess();
    		query();
    	}
    	catch (UserQuitException ex)
    	{
    		System.out.println(ex.getMessage());
    	}
    	catch (MalformedExpressionException ex)
    	{
    		JOptionPane.showMessageDialog(null, ex.getMessage());
    	}
    	catch (Exception ex)
    	{
    		ex.printStackTrace();
    	}
    }
    
    private static void preProcess() throws Exception
    {
    	new PreProcess().doPreProcess();
		System.out.println("Preprocess OK!");
    }
    
    private static void query() throws Exception
    {
    	System.out.println("Initing query engine...");
    	QueryProcessor queryProcessor = new QueryProcessor();
    	queryProcessor.startQueryEngine();
    }
}
