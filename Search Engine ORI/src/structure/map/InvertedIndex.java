package structure.map;

import java.util.ArrayList;
import java.util.TreeMap;

public class InvertedIndex extends AbstractInvertedIndex
{
	public InvertedIndex(TreeMap<String, ArrayList<Integer>> invertedIndex)
	{
		super(invertedIndex);
	}
}
