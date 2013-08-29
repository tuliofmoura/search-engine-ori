import javax.swing.JOptionPane;

import process.PreProcess;
import queryengine.QueryProcessor;
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
    	System.out.println("Iniciando pré-processamento...");
    	long start = System.currentTimeMillis();
    	new PreProcess().doPreProcess();
    	long delta = System.currentTimeMillis() - start;
		System.out.println("OK! Tempo gasto " + delta + " milisegundos.\n");
    }
    
    private static void query() throws Exception
    {
    	QueryProcessor queryProcessor = new QueryProcessor();
    	System.out.println("QueryProcessor Iniciado.\n");
    	queryProcessor.initQueryProcessor();
    	
    }
}
