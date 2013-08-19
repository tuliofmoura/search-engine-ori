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
import structure.DocumentIndex;
import structure.InvertedIndex;
import structure.map.FrequencyMap;
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
    	loadInvertedIndex(fileProc);
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

    /**
     * Creates a index file of documents, only if the file don't exist.
     * @throws IOException 
     */
	private void createDocumentsIndexFile(String docsIndexPath) throws IOException
	{
		ArrayList<File> listOfFiles = Reader.getInstance().getListOfDocuments();
    	Writer.getInstance().createDocumentsIndexFile(docsIndexPath, listOfFiles);
	}
	
	/**
	 * Creates a frequency map file with collection's terms, only if the file don't exist.
	 * @throws IOException 
	 */
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
	
	private void loadInvertedIndex(FileProcessor fileProcessor) throws IOException
	{
		StringBuilder sb = new StringBuilder();
    	sb.append(Constants.STRUCTURE_DIRECTORY).append(File.separator)
    		.append(Constants.INVERTED_INDEX);
    	String path = sb.toString();
    	
    	if (!Reader.getInstance().existFile(path))
    		createInvertedIndexFile(path, fileProcessor);
    	
    	TreeMap<String, ArrayList<Integer>> invertedIndex = Reader.getInstance().loadInvertedIndex(path);
    	
    	ApplicationContext.initInvertedIndex(new InvertedIndex(invertedIndex));
	}
	
	/**
	 * Creates a inverted index file with collection's terms, only if the file don't exist.
	 * @param fileProcessor
	 * @throws IOException 
	 */
	private void createInvertedIndexFile(String path, FileProcessor fileProcessor) throws IOException
	{
		TokenMap invertedIndex = (TokenMap) fileProcessor.processFiles(Constants.MAP_BY_FILE);
    	invertedIndex.sort();
    	Writer.getInstance().createInvertedIndexFile(path, invertedIndex.getSortedTokenMap());
	}
}
