package structure.map;

import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class AbstractInvertedIndex
{
	private TreeMap<String, ArrayList<Integer>> mMap;
	
	public AbstractInvertedIndex(TreeMap<String, ArrayList<Integer>> map)
	{
		mMap = map;
	}
	
	public TreeMap<String, ArrayList<Integer>> getInvertedIndex()
	{
		return mMap;
	}
	
	public ArrayList<Integer> getListOfDocsByTerm(String term)
	{
		return mMap.get(term);
	}
	
	public SortedMap<String, ArrayList<Integer>> getHeadMap(String key)
	{
		return mMap.headMap(key);
	}
	
	public NavigableMap<String, ArrayList<Integer>> getTailMap(String fromKey, boolean inclusive)
	{
		return mMap.tailMap(fromKey, inclusive);
	}
	
	public NavigableMap<String, ArrayList<Integer>> getSubMap(String fromKey, boolean fromInclusive,
			String toKey, boolean toInclusive)
	{
		return mMap.subMap(fromKey, fromInclusive, toKey, toInclusive);
	}
	
	public NavigableMap<String, ArrayList<Integer>> getDescendingMap()
	{
		return mMap.descendingMap();
	}
}
