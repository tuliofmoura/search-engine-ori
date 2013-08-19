package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import com.google.gson.Gson;

public class Writer
{
	private static Writer mInstance = null;
	
	private Writer() {}
	
	public static void init()
	{
		if (mInstance == null)
			mInstance = new Writer();
	}
	
	public static Writer getInstance()
	{
		return mInstance;
	}
	
	/**
	 * Cria arquivo com a frequencia das palavras
	 * @param map
	 * @throws IOException 
	 */
	public void createFrequencyMapFile(String path, TreeMap<String, Integer> map) throws IOException
	{
		createGenericFile(path, map);
		/*for (Entry<String , Integer> entry : map.entrySet())
		{
			bw.write(entry.getKey());
			bw.write(";");
			bw.write(entry.getValue().toString());
			bw.newLine();
		}*/
	}
	
	public void createInvertedIndexFile(String path, TreeMap<String, ArrayList<Integer>> map) throws IOException
	{
		createGenericFile(path, map);
		/*for (Entry<String, ArrayList<Integer>> entry : map.entrySet())
		{
			bw.write(entry.getKey());
			bw.write(":");
			for (Integer i : entry.getValue())
			{
				bw.write(i.toString());
				bw.write(";");
			}
			bw.newLine();
		}*/
	}
	
	/**
	 * Create a index file with all files in the collection,
	 * only if the file don't exist.
	 * @param files
	 * @throws IOException 
	 */
	public void createDocumentsIndexFile(String docsIndexPath, ArrayList<File> files) throws IOException 
	{
		HashMap<Integer, String> filesIndex = new HashMap<Integer, String>();
		for (int i = 0; i < files.size(); ++i)
			filesIndex.put(i, files.get(i).getAbsolutePath());
		
		Gson gson = new Gson();
		String filesJSON = gson.toJson(filesIndex);
		
		createFile(docsIndexPath, filesJSON);
	}
	
	private void createGenericFile(String path, Object obj) throws IOException 
	{
		Gson gson = new Gson();
		String filesJSON = gson.toJson(obj);
		
		createFile(path, filesJSON);
	}

	private void createFile(String docsIndexPath, String content) throws IOException
	{
		File outFile = new File(docsIndexPath);
		outFile.createNewFile();
		
		FileWriter fw = new FileWriter(outFile.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();
	}
}
