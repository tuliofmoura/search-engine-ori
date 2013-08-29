package queryengine;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class QueryProcessor
{
	public static final String AND_OPERATOR = "&&";
	public static final String OR_OPERATOR = "||";
	public static final String NOT_OPERATOR = "!!";
	
	protected StringBuilder mStringBuilder;
	protected ResultViewer mResultViewer;
	private AbstractQuery mQuery;
	
	public QueryProcessor()
	{
		mStringBuilder = new StringBuilder();
		mResultViewer = new ResultViewer();
	}
	
	public void initQueryProcessor() throws UserQuitException, MalformedExpressionException
	{
		ArrayList<Integer> result = doQuery();
		mResultViewer.showQueryResult(mStringBuilder.toString(), result);
	}
	
	public ArrayList<Integer> doQuery() throws UserQuitException, MalformedExpressionException
	{
		ArrayList<Integer> resultList = new ArrayList<Integer>();
		ArrayList<Integer> parcialResult;
		String userInput, input, operator;
		
		//ler primeiro termo
		userInput = readUserTerm();
		if (userInput == null)
			return resultList;
		if (!isAValidTerm(userInput))
			throw new MalformedExpressionException();
		
		input = userInput.trim();
		mStringBuilder.append(input);
		
		//processar o termo
		resultList = processTerm(input);
		
		//loop -> ler operador, ler e processar termo, juntar resultados
		do
		{
			//ler operador
			userInput = readUserOperator();
			if (userInput == null)
				return resultList;
			if (!isAValidOperator(userInput))
				throw new MalformedExpressionException();
			
			operator = userInput.trim();
			mStringBuilder.append(" ").append(operator);
			
			//ler e processar outro termo
			userInput = readUserTerm();
			if (userInput == null)
				return resultList;
			if (!isAValidTerm(userInput))
				throw new MalformedExpressionException();
			
			input = userInput.trim();
			mStringBuilder.append(" ").append(input);
			
			parcialResult = processTerm(input);
			//processa resultado final
			resultList = processExpression(resultList, parcialResult, operator);
		}
		while(true);
	}
	
	private ArrayList<Integer> processTerm(String userInput) throws UserQuitException, MalformedExpressionException
	{
		if (userInput.contains("*"))
			mQuery = new TolerantQuery();
		else
			mQuery = new NormalQuery();
		
		return mQuery.doQuery(userInput);
	}
	
	private ArrayList<Integer> processExpression(ArrayList<Integer> previousResult,
			ArrayList<Integer> parcialResult, String operator)
	{
		BooleanQueryProcessor bqp = new BooleanQueryProcessor();
		return bqp.processExpression(previousResult, parcialResult, operator);
	}
	
	protected String readUserTerm()
	{
		return JOptionPane.showInputDialog("Entre com um termo a ser buscado\n\n"
				+ "Termos válidos:\nUma única palavra\n"
				+ "Um único caractere coringa (Exemplo: asdf* ou *asdf ou as*df\n\n"
				+ "Clique em CANCELAR para finalizar consulta.");
	}
	
	private String readUserOperator()
	{
		return JOptionPane.showInputDialog("Entre com um operador booleano\n"
				+ "Operadores válidos:\n|| (OR)\n&& (AND)\n!! (NOT)\n\n"
				+ "Clique em CANCELAR para finalizar consulta.");
	}
	
	protected boolean isAValidTerm(String term)
	{
		String[] terms = term.trim().split(" ");
		if (terms == null || terms.length != 1 || terms[0].equals(AND_OPERATOR) || terms[0].equals(OR_OPERATOR) || terms[0].equals(NOT_OPERATOR))
			return false;
		return true;
	}
	
	protected boolean isAValidOperator(String operator)
	{
		String[] operators = operator.trim().split(" ");
		if (operators != null && operators.length == 1 && (operators[0].equals(AND_OPERATOR) || operators[0].equals(OR_OPERATOR) || operators[0].equals(NOT_OPERATOR)))
			return true;
		return false;
	}
	
	
	/*
	 * Exceptions 
	 */
	
	public class UserQuitException extends Exception
	{
		private static final long serialVersionUID = 1L;
		@Override
		public String getMessage()
		{
			return "The application was finished by the user";
		}
		
	}
	
	public class MalformedExpressionException extends Exception
	{
		private static final long serialVersionUID = 1L;
		@Override
		public String getMessage()
		{
			return "Malformed expression";
		}
	}
}
