package structure;

import io.Reader;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

public class DocumentIndex
{
	private HashMap<Integer, String> mDocsIndex;
	
	public DocumentIndex(HashMap<Integer, String> docsIndex)
	{
		mDocsIndex = docsIndex;
	}
	
	public Set<Integer> getDocumentsKeySet()
	{
		return mDocsIndex.keySet();
	}
	
	public String getDocumentPath(int index)
	{
		return mDocsIndex.get(index);
	}
	
	public File getDocument(int index)
	{
		String path = getDocumentPath(index);
		return Reader.getInstance().loadDocument(path);
	}
	
	public String getDocumentName(int index)
	{
		File document = getDocument(index);
		return document.getName();
	}
}
