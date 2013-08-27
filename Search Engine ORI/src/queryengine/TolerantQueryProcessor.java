package queryengine;

import java.util.ArrayList;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;

import structure.ApplicationContext;

public class TolerantQueryProcessor extends QueryProcessor
{

	@Override
	public ArrayList<Integer> doQuery() throws UserQuitException, MalformedExpressionException
	{
		ArrayList<Integer> resultList = new ArrayList<Integer>();
		
		String userInput = readUserTerm();
		if (userInput == null)
			return resultList;
		if (!isAValidTerm(userInput))
			throw new MalformedExpressionException();
		
		mStringBuilder.append(userInput.trim());
		processQuery(userInput.trim());
		
		return resultList;
	}
	
	@Override
	protected boolean isAValidTerm(String term)
	{
		boolean isValid = super.isAValidTerm(term);
		if (isValid)
		{
			if (term.trim().contains("*"))
				return true;
		}
		return false;
	}

	private String[] processQuery(String query)
	{
		String[] splitedQuery = query.split("\\*");
		if (query.endsWith("*"))
		{
			String queryNormalized = normalizeToken(splitedQuery[0]);
			tail(queryNormalized);
		}
		else if (query.startsWith("*"))
		{
			//TODO head
		}
		else if (query.contains("*"))
		{
			//TODO center
		}
		return splitedQuery;
	}
	
	private NavigableMap<String, ArrayList<Integer>> tail(String fromKey)
	{
		char lastChar = fromKey.charAt(fromKey.length() - 1);
		char nextChar = (char)(lastChar + 1);
		String toKey = fromKey.substring(0, fromKey.length() - 1);
		toKey = toKey + nextChar;
		
		NavigableMap<String, ArrayList<Integer>> subMap = ApplicationContext.getInvertedIndex()
				.getSubMap(fromKey, true, toKey, true);
		
		return subMap;
	}
	

	protected ArrayList<Integer> asdf(String token)
	{
		SortedMap<String, ArrayList<Integer>> headMapElements = ApplicationContext.getInvertedIndex().getHeadMap(token);
		
		for (Map.Entry entry : headMapElements.entrySet())
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		
		
		return null;
	}

}
