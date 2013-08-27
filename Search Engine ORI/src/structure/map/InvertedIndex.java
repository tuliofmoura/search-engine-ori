package structure.map;

import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class InvertedIndex
{
	private TreeMap<String, ArrayList<Integer>> mInvertedIndex;
	
	public InvertedIndex(TreeMap<String, ArrayList<Integer>> invertedIndex)
	{
		mInvertedIndex = invertedIndex;
	}
	
	public TreeMap<String, ArrayList<Integer>> getInvertedIndex()
	{
		return mInvertedIndex;
	}
	
	public ArrayList<Integer> getListOfDocsByTerm(String term)
	{
		return mInvertedIndex.get(term);
	}
	
	public SortedMap<String, ArrayList<Integer>> getHeadMap(String key)
	{
		return mInvertedIndex.headMap(key);
	}
	
	public NavigableMap<String, ArrayList<Integer>> getTailMap(String fromKey, boolean inclusive)
	{
		return mInvertedIndex.tailMap(fromKey, inclusive);
	}
	
	public NavigableMap<String, ArrayList<Integer>> getSubMap(String fromKey, boolean fromInclusive,
			String toKey, boolean toInclusive)
		{
			return mInvertedIndex.subMap(fromKey, fromInclusive, toKey, toInclusive);
		}
}
