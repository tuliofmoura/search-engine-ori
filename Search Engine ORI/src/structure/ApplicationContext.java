package structure;

import structure.map.DocumentIndex;
import structure.map.InvertedIndex;


public class ApplicationContext
{
	private static DocumentIndex mDocsIndex;
	private static InvertedIndex mInvertedIndex;
	
	public static void initDocsIndex(DocumentIndex docsIndex)
	{
		mDocsIndex = docsIndex;
	}
	
	public static DocumentIndex getDocsIndex()
	{
		return mDocsIndex;
	}
	
	public static void initInvertedIndex(InvertedIndex invertedIndex)
	{
		mInvertedIndex = invertedIndex;
	}
	
	public static InvertedIndex getInvertedIndex()
	{
		return mInvertedIndex;
	}
}
