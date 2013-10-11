package fr.sdesby.fileIO;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class FileManager 
{
	public FileManager()
	{
		
	}
	
	public boolean writeLine(Context context, String filename, String toWrite)
	{
		String appPath = context.getApplicationContext().getFilesDir().getAbsolutePath();

		File file = new File(appPath + "/" + filename);
		OutputStream outputStream = null;

		try
		{
			if(!file.exists())
				file.createNewFile();

			outputStream = new BufferedOutputStream(new FileOutputStream(file, true));
			outputStream.write(toWrite.getBytes());
			outputStream.close();

			return true;
		}
		catch(FileNotFoundException fnf)
		{
			fnf.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	private boolean deleteFile(Context context, String filename)
	{
		String appPath = context.getApplicationContext().getFilesDir().getAbsolutePath();
		File file = new File(appPath + "/" + filename);
		
		try
		{
			file.delete();
			return true;
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean readFile(Context context, String filename, List<String> readContainer)
	{
		FileInputStream inputStream;

		try
		{
			inputStream = context.openFileInput(filename);
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) 
			{
				readContainer.add(line);
			}
			inputStream.close();
			return true;
		} 
		catch(OutOfMemoryError om)
		{
			om.printStackTrace();
		} 
		catch(FileNotFoundException fnf)
		{
			fnf.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteLine(Context context, String filename, int position)
	{
		FileInputStream inputStream;
		FileOutputStream outputStream;
		int counter = 0;
		
		try
		{
			List<String> listToKeep = new ArrayList<String>();
			inputStream = context.openFileInput(filename);
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String line = null;
			//On lit le fichier mais on saute la ligne qui correspond ˆ la position de la ligne ˆ supprimer
			while ((line = reader.readLine()) != null) 
			{
				if(counter != position)
				{
					listToKeep.add(line + '\n');
				}
				counter++;
			}
			inputStream.close();
			
			//On supprime le fichier
			deleteFile(context, filename);
			
			//On rŽŽcrit le fichier sans la ligne ˆ supprimer
			outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
			for(int i = 0; i < listToKeep.size(); i++)
			{
				outputStream.write(listToKeep.get(i).getBytes());
			}
			outputStream.close();
			return true;
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}

}
