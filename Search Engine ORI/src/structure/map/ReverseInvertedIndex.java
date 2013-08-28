package structure.map;

import java.util.ArrayList;
import java.util.TreeMap;

public class ReverseInvertedIndex  extends AbstractInvertedIndex
{
	public ReverseInvertedIndex(TreeMap<String, ArrayList<Integer>> reverseIndex)
	{
		super(reverseIndex);
	}
}