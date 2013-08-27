package queryengine;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import structure.ApplicationContext;

public abstract class QueryProcessor
{
	protected final String AND_OPERATOR = "&&";
	protected final String OR_OPERATOR = "||";
	protected final String NOT_OPERATOR = "!!";
	
	protected StringBuilder mStringBuilder;
	protected ResultViewer mResultViewer;
	
	public QueryProcessor()
	{
		mStringBuilder = new StringBuilder();
		mResultViewer = new ResultViewer();
	}
	
	public void processsQuery() throws UserQuitException, MalformedExpressionException
	{
		ArrayList<Integer> result = doQuery();
		mResultViewer.showQueryResult(mStringBuilder.toString(), result);
	}
	
	public abstract ArrayList<Integer> doQuery() throws UserQuitException, MalformedExpressionException;
	
	protected String readUserTerm()
	{
		return JOptionPane.showInputDialog("Entre com um termo a ser buscado\n\n"
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
	
	protected ArrayList<Integer> getListOfDocsInInvertedListByToken(String token)
	{
		String term = normalizeToken(token);
		ArrayList<Integer> docsInInvertedList = getListOfDocsByTerm(term);
		if (docsInInvertedList != null)
			Collections.sort(docsInInvertedList);
		else
			docsInInvertedList = new ArrayList<Integer>();
		return docsInInvertedList;
	}
	
	protected String normalizeToken(String expression)
	{
		return Normalizer.normalize(expression.trim(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]|\\p{Punct}", "").toLowerCase();
	}
	
	protected ArrayList<Integer> getListOfDocsByTerm(String term)
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
