/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import structure.Constants;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author a98736
 */
public class Reader
{
	private static Reader mInstance = null;
	
    private File docsFolder;
    private File structureFolder;
    
    public static void init() throws Exception
    {
    	if (mInstance == null)
    		mInstance = new Reader();
    }
    
    public static Reader getInstance()
    {
    	return mInstance;
    }
    
    private Reader() throws Exception
    {
        this.docsFolder = new File(Constants.COLLECTION_DIRECTORY);
        if (docsFolder == null || !docsFolder.isDirectory())
            throw new Exception("O diretório informado como contendo a coleção é inválido ou não existe!");
        
        this.structureFolder = new File(Constants.STRUCTURE_DIRECTORY);
        if (structureFolder == null || !structureFolder.isDirectory())
        	throw new Exception("O diretório informado como contendo a estrutura é inválido ou não existe!");
    }

    public ArrayList<File> getListOfDocuments()
    {
    	ArrayList<File> files = new ArrayList<File>();
        File[] list = docsFolder.listFiles();
        for(File file : list)
        {
        	if (file.isFile())
        		files.add(file);
        }
        return files;
    }
    
    public void printDocumentsNames()
    {
    	System.out.printf("Arquivos no diretorio %s \n", docsFolder.getPath());
    	ArrayList<File> documents = new ArrayList<File>();
		for (int i=1; i<= documents.size(); i++)
		{
			File file = documents.get(i-1);
			System.out.printf("[%3d]: %s \n", i, file.getName());
		}
    }
    
    public boolean existFile(String pathName)
    {
    	File file = new File(pathName);
		return file.exists();
    }
    
    public File loadDocument(String path)
    {
    	return new File(path);
    }
    
    public HashMap<Integer, String> loadDocsIndex(String docsIndexPath) throws FileNotFoundException
    {
		Gson gson = new Gson();
		Type collectionType = new TypeToken<HashMap<Integer, String>>(){}.getType();
		FileReader fr = new FileReader(docsIndexPath);
		HashMap<Integer, String> docsIndex = gson.fromJson(fr, collectionType);
		return docsIndex;
    }
    
    public TreeMap<String, ArrayList<Integer>> loadInvertedIndex(String path) throws FileNotFoundException
    {
		Gson gson = new Gson();
		Type collectionType = new TypeToken<TreeMap<String, ArrayList<Integer>>>(){}.getType();
		FileReader fr = new FileReader(path);
		TreeMap<String, ArrayList<Integer>> invertedIndex = gson.fromJson(fr, collectionType);
		return invertedIndex;
    }
}
