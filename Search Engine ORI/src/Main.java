import javax.swing.JOptionPane;

import process.PreProcess;
import queryengine.BooleanQueryProcessor;
import queryengine.QueryProcessor;
import queryengine.TolerantQueryProcessor;
import queryengine.QueryProcessor.MalformedExpressionException;
import queryengine.QueryProcessor.UserQuitException;

public class Main
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
    	try
    	{
    		//realiza pré-processamento (cria índices se eles não existirem e carrega em memória)
    		preProcess();
    		//realiza pesquisa
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
    	QueryProcessor queryProcessor;
    	
    	//consulta booleana
    	/*queryProcessor = new BooleanQueryProcessor();
    	queryProcessor.processsQuery();*/
    	
    	//consulta tolerante
    	queryProcessor = new TolerantQueryProcessor();
    	queryProcessor.processsQuery();
    	
    }
}
