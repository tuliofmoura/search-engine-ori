package queryengine;

import java.util.ArrayList;
import java.util.Collections;

public class BooleanQueryProcessor
{
	
	public ArrayList<Integer> processExpression(ArrayList<Integer> previousResult,
			ArrayList<Integer> parcialResult, String operator)
	{
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		if (operator.equals(QueryProcessor.AND_OPERATOR))
			result = processAndExpression(previousResult, parcialResult);

		if (operator.equals(QueryProcessor.OR_OPERATOR))
			result = processOrExpression(previousResult, parcialResult);
		
		if (operator.equals(QueryProcessor.NOT_OPERATOR))
			result = processNotExpression(previousResult, parcialResult);

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
