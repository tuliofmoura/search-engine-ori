package process;

import io.Reader;
import io.Writer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import structure.ApplicationContext;
import structure.Constants;
import structure.map.DocumentIndex;
import structure.map.FrequencyMap;
import structure.map.InvertedIndex;
import structure.map.ReverseInvertedIndex;
import structure.map.TokenMap;

public class PreProcess
{
	public void doPreProcess() throws Exception
	{
    	Reader.init();
    	Writer.init();
    	preProcess();
	}
	
	public void preProcess() throws Exception
    {
    	FileProcessor fileProc = new FileProcessor();
    	loadDocsIndex();
    	createFrequencyMapFile(fileProc);
    	loadInvertedIndexes(fileProc);
    }
	
	private void loadDocsIndex() throws IOException
	{
		StringBuilder sb = new StringBuilder();
    	sb.append(Constants.STRUCTURE_DIRECTORY).append(File.separator)
    		.append(Constants.FILES_INDEX);
    	String docsIndexPath = sb.toString();
    	
		if (!Reader.getInstance().existFile(docsIndexPath))
			createDocumentsIndexFile(docsIndexPath);
		
		HashMap<Integer, String> docsIndex = Reader.getInstance().loadDocsIndex(docsIndexPath);

		ApplicationContext.initDocsIndex(new DocumentIndex(docsIndex));
	}

	private void createDocumentsIndexFile(String docsIndexPath) throws IOException
	{
		ArrayList<File> listOfFiles = Reader.getInstance().getListOfDocuments();
    	Writer.getInstance().createDocumentsIndexFile(docsIndexPath, listOfFiles);
	}
	
	private void createFrequencyMapFile(FileProcessor fileProcessor) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.STRUCTURE_DIRECTORY).append(File.separator)
			.append(Constants.LIST_OF_TERMS);
		String path = sb.toString();
		
		if (Reader.getInstance().existFile(path))
			return;
		
		FrequencyMap frequencyMap = (FrequencyMap)fileProcessor.processFiles(Constants.MAP_BY_FREQUENCY);
		frequencyMap.sort();
		Writer.getInstance().createFrequencyMapFile(path, frequencyMap.getSortedTokenMap());
	}
	
	private void loadInvertedIndexes(FileProcessor fileProcessor) throws IOException
	{
		StringBuilder sb = new StringBuilder();
    	sb.append(Constants.STRUCTURE_DIRECTORY).append(File.separator)
    		.append(Constants.INVERTED_INDEX);
    	String path = sb.toString();
    	
    	sb = new StringBuilder();
    	sb.append(Constants.STRUCTURE_DIRECTORY).append(File.separator)
			.append(Constants.REVERSE_INVERTED_INDEX);
    	String reverseMapPath = sb.toString();
    	
    	if (!Reader.getInstance().existFile(path))
    		createInvertedIndexFiles(path, reverseMapPath, fileProcessor);
    	
    	TreeMap<String, ArrayList<Integer>> invertedIndex = Reader.getInstance().loadInvertedIndex(path);
    	ApplicationContext.initInvertedIndex(new InvertedIndex(invertedIndex));
    	
    	TreeMap<String, ArrayList<Integer>> reverseInvertedIndex = Reader.getInstance().loadInvertedIndex(reverseMapPath);
    	ApplicationContext.initReverseInvertedIndex(new ReverseInvertedIndex(reverseInvertedIndex));
	}
	
	private void createInvertedIndexFiles(String sortedMapPath, String reverseMapPath, FileProcessor fileProcessor) throws IOException
	{
		TokenMap invertedIndex = (TokenMap) fileProcessor.processFiles(Constants.MAP_BY_FILE);
    	invertedIndex.sort();
    	Writer.getInstance().createInvertedIndexFiles(sortedMapPath, reverseMapPath, invertedIndex);
	}
}
