package structure.map;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

public class TokenMap extends AbstractMap
{
	private HashMap<String, ArrayList<Integer>> tokenMap = new HashMap<String, ArrayList<Integer>>();
	protected TreeMap<String, ArrayList<Integer>> sortedTokenMap;
	protected TreeMap<String, ArrayList<Integer>> reverseTokenMap;
	
	public HashMap<String, ArrayList<Integer>> getTokenMap() {
		return tokenMap;
	}
	
	public TreeMap<String, ArrayList<Integer>> getSortedTokenMap() {
		return sortedTokenMap;
	}
	
	public TreeMap<String, ArrayList<Integer>> getReverseTokenMap() {
		return reverseTokenMap;
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
	
	private HashMap<String, ArrayList<Integer>> getReverseMap()
	{
		HashMap<String, ArrayList<Integer>> reverseMap = new HashMap<String, ArrayList<Integer>>();
		for (Entry<String, ArrayList<Integer>> entry : tokenMap.entrySet())
		{
			String key = entry.getKey();
			ArrayList<Integer> value = entry.getValue();
			String reverse = new StringBuilder(key).reverse().toString();
			reverseMap.put(reverse, value);
		}
		return reverseMap;
	}
	
	private void sortReverse()
	{
		HashMap<String, ArrayList<Integer>> reverseMap = getReverseMap();
		StringComparator valueComp = new StringComparator();
		reverseTokenMap = new TreeMap<String, ArrayList<Integer>>(valueComp);
		reverseTokenMap.putAll(reverseMap);
	}
	
	@Override
	public void sort()
	{
		StringComparator valueComp =  new StringComparator();
        sortedTokenMap = new TreeMap<String, ArrayList<Integer>>(valueComp);
        sortedTokenMap.putAll(tokenMap);
        
        sortReverse();
	}
	
	private class StringComparator implements Comparator<String>
	{

	    public int compare(String a, String b)
	    {
	        return a.compareTo(b);
	    }
	}
}
