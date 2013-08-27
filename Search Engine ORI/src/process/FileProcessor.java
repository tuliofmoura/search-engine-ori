package process;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import structure.ApplicationContext;
import structure.Constants;
import structure.map.AbstractMap;
import structure.map.DocumentIndex;
import structure.map.FrequencyMap;
import structure.map.TokenMap;

public class FileProcessor
{
	public AbstractMap processFiles(int mapBy)
	{
		AbstractMap map;
		if (mapBy == Constants.MAP_BY_FREQUENCY)
			map = new FrequencyMap();
		else
			map = new TokenMap();
		
		DocumentIndex docIndex = ApplicationContext.getDocsIndex();
		
		for (int docId : docIndex.getDocumentsKeySet())
		{
			File document = docIndex.getDocument(docId);
			processFile(map, document, docId);
		}
		return map;
	}
	
	private void processFile(AbstractMap map, File file, int index)
	{
		String base_url_relativa = ".";
		try
		{
			Document doc = Jsoup.parse(file, "ISO-8859-1", base_url_relativa);
			String text = doc.body().text();
			processString(map, text, index);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void processString(AbstractMap map, String str, int index)
	{
		String tokenNormalized = Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]|\\p{Punct}", "").toLowerCase();
		StringTokenizer tokenizer = new StringTokenizer(tokenNormalized);
		while(tokenizer.hasMoreTokens())
		{
			String token = tokenizer.nextToken();
			map.mapToken(token, index);
		}
	}
}