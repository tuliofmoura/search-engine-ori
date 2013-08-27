package queryengine;

import java.util.ArrayList;

import structure.ApplicationContext;

public class ResultViewer
{
	public void showQueryResult(String query, ArrayList<Integer> result)
	{
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println("Query: " + query);
		System.out.println("Resultados:");
		ArrayList<String> documentsPath = getDocumentsPaths(result);
		for (String documentPath : documentsPath)
			System.out.println("file://" + documentPath);
	}
	
	private ArrayList<String> getDocumentsPaths(ArrayList<Integer> docIds)
	{
		ArrayList<String> docNames = new ArrayList<String>();
		for (int docId : docIds)
			docNames.add(ApplicationContext.getDocsIndex().getDocumentPath(docId));
		return docNames;
	}
}
