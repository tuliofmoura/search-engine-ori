package structure.map;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class FrequencyMap extends AbstractMap
{
	private HashMap<String, Integer> tokenMap = new HashMap<String, Integer>();
	private TreeMap<String, Integer> sortedTokenMap;
	
	public HashMap<String, Integer> getTokenMap() {
		return tokenMap;
	}
	
	public TreeMap<String, Integer> getSortedTokenMap() {
		return sortedTokenMap;
	}
	
	@Override
	public void mapToken(String token, int fileIndex)
	{
		if (tokenMap.containsKey(token))
			tokenMap.put(token, tokenMap.get(token) + 1);
		else
			tokenMap.put(token, 1);
	}
	
	@Override
	public void sort()
	{
		ValueComparator valueComp =  new ValueComparator(tokenMap);
        sortedTokenMap = new TreeMap<String, Integer>(valueComp);
        sortedTokenMap.putAll(tokenMap);
	}
	
	@Override
	public String toString()
	{
		String s = new String();
		for (Entry<String, Integer> entry : tokenMap.entrySet())
		{
			String key = entry.getKey();
			Integer value = entry.getValue();
			s += key + ": " + value + '\n';
		}
		return s;
	}
	
	
	private class ValueComparator implements Comparator<String>
	{
	    private Map<String, Integer> base;
	    
	    public ValueComparator(Map<String, Integer> base)
	    {
	        this.base = base;
	    }

	    public int compare(String a, String b)
	    {
	        if (base.get(a) >= base.get(b))
	            return -1;
	        else
	            return 1;
	    }
	}
	
}
