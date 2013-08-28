package structure;

import structure.map.DocumentIndex;
import structure.map.InvertedIndex;
import structure.map.ReverseInvertedIndex;


public class ApplicationContext
{
	private static DocumentIndex mDocsIndex;
	private static InvertedIndex mInvertedIndex;
	private static ReverseInvertedIndex mReverseInvertedIndex;
	
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
	
	public static void initReverseInvertedIndex(ReverseInvertedIndex invertedIndex)
	{
		mReverseInvertedIndex = invertedIndex;
	}
	
	public static ReverseInvertedIndex getReverseInvertedIndex()
	{
		return mReverseInvertedIndex;
	}
}
