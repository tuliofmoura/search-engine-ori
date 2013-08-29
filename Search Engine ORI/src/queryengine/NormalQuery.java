package queryengine;

import java.util.ArrayList;
import java.util.Collections;

import queryengine.QueryProcessor.MalformedExpressionException;
import queryengine.QueryProcessor.UserQuitException;
import structure.ApplicationContext;

public class NormalQuery extends AbstractQuery
{

	@Override
	public ArrayList<Integer> doQuery(String query) throws UserQuitException,
			MalformedExpressionException
	{
		String term = normalizeToken(query);
		ArrayList<Integer> docsInInvertedList = getListOfDocsByTerm(term);
		if (docsInInvertedList != null)
			Collections.sort(docsInInvertedList);
		else
			docsInInvertedList = new ArrayList<Integer>();
		
		printDocsList(query, docsInInvertedList);
		
		return docsInInvertedList;
	}

	private ArrayList<Integer> getListOfDocsByTerm(String term)
	{
		return ApplicationContext.getInvertedIndex().getListOfDocsByTerm(term);
	}
	
	private void printDocsList(String query, ArrayList<Integer> docsList)
	{
		System.out.println("\nLista de documentos que respondem à busca:");
		System.out.println(query + " = " + docsList.toString());
	}
}
