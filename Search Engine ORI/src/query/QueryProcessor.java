package query;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import structure.ApplicationContext;

public class QueryProcessor
{
	private final String AND_OPERATOR = "&&";
	private final String OR_OPERATOR = "||";
	private final String NOT_OPERATOR = "!!";
	
	private QueryParser mQueryParser;
	
	public QueryProcessor()
	{
		mQueryParser = new QueryParser();
	}
	
	public void startQueryEngine() throws Exception
	{
		/*String userQuery = getUserQuery();
		mQueryParser.parseRawQuery(userQuery);*/
		getUserQuery();
	}
	
	public ArrayList<Integer> getUserQuery() throws UserQuitException, MalformedExpressionException
	{
		ArrayList<Integer> resultList = new ArrayList<Integer>();
		//ler e processar primeiro termo
		String userInput = readUserTerm();
		if (userInput == null)
			return resultList;
		if (!isAValidTerm(userInput))
			throw new MalformedExpressionException();
		resultList = processToken(userInput);
		do
		{
			//ler e processar operador
			userInput = readUserOperator();
			if (userInput == null)
				return resultList;
			String operator;
			if (isAValidOperator(userInput))
				operator = userInput.trim();
			else
				throw new MalformedExpressionException();
			
			//ler e processar outro termo
			userInput = readUserTerm();
			if (userInput == null)
				return resultList;
			if (!isAValidTerm(userInput))
				throw new MalformedExpressionException();
			ArrayList<Integer> termList = processToken(userInput);
			
			//processa resultado parcial
			resultList = processExpression(resultList, termList, operator);
		}
		while(true);
	}
	
	private String readUserTerm()
	{
		return JOptionPane.showInputDialog("Entre com um termo a ser buscado\n\n"
				+ "Clique em CANCELAR para finalizar consulta.");
	}
	
	private ArrayList<Integer> processToken(String token)
	{
		String term = normalizeExpression(token);
		ArrayList<Integer> termList = getListOfDocsByTerm(term);
		if (termList != null)
			Collections.sort(termList);
		else
			termList = new ArrayList<Integer>();
		return termList;
	}
	
	private String readUserOperator()
	{
		return JOptionPane.showInputDialog("Entre com um operador booleano\n"
				+ "Operadores válidos: OR, AND e NOT\n\n"
				+ "Clique em CANCELAR para finalizar consulta.");
	}
	
	private ArrayList<Integer> processExpression(ArrayList<Integer> previousResult, ArrayList<Integer> termList, String operator)
	{
		ArrayList<Integer> result = new ArrayList<Integer>();
		if (operator.equals(AND_OPERATOR))
			result = processAndExpression(previousResult, termList);
		if (operator.equals(OR_OPERATOR))
		{
			
		}
		if (operator.equals(NOT_OPERATOR))
		{
			
		}
		return result;
	}
	
	private ArrayList<Integer> processAndExpression(ArrayList<Integer> previousResult, ArrayList<Integer> termList)
	{
		ArrayList<Integer> result = new ArrayList<Integer>();
		int i = 0;
		int j = 0;
		while (i < previousResult.size() && j < termList.size())
		{
			if (previousResult.get(i) < termList.get(j))
				++i;
			else if (previousResult.get(i) > termList.get(j))
				++j;
			else
			{
				result.add(previousResult.get(i));
				++i;
				++j;
			}
		}
		return result;
	}
	
	private boolean isAValidTerm(String term)
	{
		String[] terms = term.trim().split(" ");
		if (terms == null || terms.length != 1 || terms[0].equals(AND_OPERATOR) || terms[0].equals(OR_OPERATOR) || terms[0].equals(NOT_OPERATOR))
			return false;
		return true;
	}
	
	private boolean isAValidOperator(String operator)
	{
		String[] operators = operator.trim().split(" ");
		if (operators != null && operators.length == 1 && (operators[0].equals(AND_OPERATOR) || operators[0].equals(OR_OPERATOR) || operators[0].equals(NOT_OPERATOR)))
			return true;
		return false;
	}
	
	private String normalizeExpression(String expression)
	{
		return Normalizer.normalize(expression.trim(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]|\\p{Punct}", "").toLowerCase();
	}
	
	private ArrayList<Integer> getListOfDocsByTerm(String term)
	{
		return ApplicationContext.getInvertedIndex().getListOfDocsByTerm(term);
	}
	
	
	
	
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
