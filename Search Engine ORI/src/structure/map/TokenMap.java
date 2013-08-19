package structure.map;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TokenMap extends AbstractMap
{
	private HashMap<String, ArrayList<Integer>> tokenMap = new HashMap<String, ArrayList<Integer>>();
	protected TreeMap<String, ArrayList<Integer>> sortedTokenMap;
	
	public HashMap<String, ArrayList<Integer>> getTokenMap() {
		return tokenMap;
	}
	
	public TreeMap<String, ArrayList<Integer>> getSortedTokenMap() {
		return sortedTokenMap;
	}
	
	@Override
	public void mapToken(String token, int fileIndex)
	{
		if (!tokenMap.containsKey(token))
		{
			ArrayList<Integer> array = new ArrayList<Integer>();
			array.add(Integer.valueOf(fileIndex));
			tokenMap.put(token, array);
		}
		else
		{
			ArrayList<Integer> array = tokenMap.get(token);
			if (!array.contains(Integer.valueOf(fileIndex)))
				array.add(Integer.valueOf(fileIndex));
		}
	}
	
	@Override
	public void sort()
	{
		ValueComparator valueComp =  new ValueComparator(tokenMap);
        sortedTokenMap = new TreeMap<String, ArrayList<Integer>>(valueComp);
        sortedTokenMap.putAll(tokenMap);
	}
	
	private class ValueComparator implements Comparator<String>
	{
	    private Map<String, ArrayList<Integer>> base;
	    
	    public ValueComparator(Map<String, ArrayList<Integer>> base)
	    {
	        this.base = base;
	    }

	    public int compare(String a, String b)
	    {
	        if (base.get(a).size() >= base.get(b).size())
	            return -1;
	        else
	            return 1;
	    }
	}
}
