package queryengine;

import java.text.Normalizer;
import java.util.ArrayList;

import queryengine.QueryProcessor.MalformedExpressionException;
import queryengine.QueryProcessor.UserQuitException;

public abstract class AbstractQuery
{
	public abstract ArrayList<Integer> doQuery(String query) throws UserQuitException, MalformedExpressionException;

	
	protected String normalizeToken(String expression)
	{
		return Normalizer.normalize(expression.trim(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]|\\p{Punct}", "").toLowerCase();
	}
	
}
