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
		String queryNormalized;
		if (query.endsWith("*"))
		{
			queryNormalized = normalizeToken(splitedQuery[0]);
			NavigableMap<String, ArrayList<Integer>> result = tail(queryNormalized);
			printTolerantWords(result, false);
		}
		else if (query.startsWith("*"))
		{
			queryNormalized = normalizeToken(splitedQuery[1]);
			NavigableMap<String, ArrayList<Integer>> result = head(queryNormalized);
			printTolerantWords(result, true);
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
				.getSubMap(fromKey, true, toKey, false);
		
		return subMap;
	}
	
	private NavigableMap<String, ArrayList<Integer>> head(String fromKey)
	{
		String reverseKey = new StringBuilder(fromKey).reverse().toString();
		
		char lastChar = reverseKey.charAt(reverseKey.length() - 1);
		char nextChar = (char)(lastChar + 1);
		String toKey = reverseKey.substring(0, reverseKey.length() - 1);
		toKey = toKey + nextChar;
		
		NavigableMap<String, ArrayList<Integer>> subMap = ApplicationContext.getReverseInvertedIndex()
				.getSubMap(reverseKey, true, toKey, false);//fromKey, true, toKey, true);
		
		return subMap;
	}
	
	private void printTolerantWords(NavigableMap<String, ArrayList<Integer>> map, boolean reverse)
	{
		System.out.println("Termos que respondem à busca tolerante:");
		for (String key : map.keySet())
		{
			String word;
			if (reverse)
				word = new StringBuilder(key).reverse().toString();
			else
				word = key;
			System.out.println(word);
		}
	}

}
