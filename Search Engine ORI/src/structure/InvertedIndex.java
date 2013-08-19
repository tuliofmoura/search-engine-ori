package structure;

import java.util.ArrayList;
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
}
