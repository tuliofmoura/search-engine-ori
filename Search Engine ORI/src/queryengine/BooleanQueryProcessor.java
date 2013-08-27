package queryengine;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

public class BooleanQueryProcessor extends QueryProcessor
{
	@Override
	public ArrayList<Integer> doQuery() throws UserQuitException, MalformedExpressionException
	{
		ArrayList<Integer> resultList = new ArrayList<Integer>();
		//ler e processar primeiro termo
		String userInput = readUserTerm();
		if (userInput == null)
			return resultList;
		if (!isAValidTerm(userInput))
			throw new MalformedExpressionException();
		
		mStringBuilder.append(userInput.trim());
		resultList = getListOfDocsInInvertedListByToken(userInput);
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
			mStringBuilder.append(" ").append(userInput.trim());
			
			//ler e processar outro termo
			userInput = readUserTerm();
			if (userInput == null)
				return resultList;
			if (!isAValidTerm(userInput))
				throw new MalformedExpressionException();
			mStringBuilder.append(userInput.trim());
			
			ArrayList<Integer> docsInInvertedList = getListOfDocsInInvertedListByToken(userInput);
			
			//processa resultado parcial
			resultList = processExpression(resultList, docsInInvertedList, operator);
		}
		while(true);
	}
	
	private String readUserOperator()
	{
		return JOptionPane.showInputDialog("Entre com um operador booleano\n"
				+ "Operadores válidos: || (OR), && (AND) e !! (NOT)\n\n"
				+ "Clique em CANCELAR para finalizar consulta.");
	}
	
	private ArrayList<Integer> processExpression(ArrayList<Integer> previousResult,
			ArrayList<Integer> docsInInvertedList, String operator)
	{
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		if (operator.equals(AND_OPERATOR))
			result = processAndExpression(previousResult, docsInInvertedList);

		if (operator.equals(OR_OPERATOR))
			result = processOrExpression(previousResult, docsInInvertedList);
		
		if (operator.equals(NOT_OPERATOR))
			result = processNotExpression(previousResult, docsInInvertedList);

		return result;
	}
	
	private ArrayList<Integer> processAndExpression(ArrayList<Integer> previousResult,
			ArrayList<Integer> docsInInvertedList)
	{
		ArrayList<Integer> result = new ArrayList<Integer>();
		int i = 0;
		int j = 0;
		while (i < previousResult.size() && j < docsInInvertedList.size())
		{
			if (previousResult.get(i) < docsInInvertedList.get(j))
				++i;
			else if (previousResult.get(i) > docsInInvertedList.get(j))
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
	
	private ArrayList<Integer> processOrExpression(ArrayList<Integer> previousResult,
			ArrayList<Integer> docsInInvertedList)
	{
		ArrayList<Integer> result = new ArrayList<Integer>();
		boolean changed = docsInInvertedList.addAll(previousResult);
		if (changed)
		{
			Collections.sort(docsInInvertedList);
			for (int i = 0; i < docsInInvertedList.size() - 1; ++i)
			{
				if (docsInInvertedList.get(i) == docsInInvertedList.get(i + 1))
					continue;
				result.add(docsInInvertedList.get(i));
			}
		}
		else
			result = docsInInvertedList;

		return result;
	}
	
	private ArrayList<Integer> processNotExpression(ArrayList<Integer> previousResult,
			ArrayList<Integer> docsInInvertedList)
	{
		previousResult.removeAll(docsInInvertedList);
		ArrayList<Integer> result = previousResult;
		return result;
	}
	
	
}
