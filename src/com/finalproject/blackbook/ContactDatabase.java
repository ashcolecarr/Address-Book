package com.finalproject.blackbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactDatabase extends SQLiteOpenHelper
{
    //The Android's default system path of your application database.
	private static String DB_PATH = "/data/data/com.finalproject.blackbook/databases/";
	private static String DB_NAME = "contactsdatabase";
	private SQLiteDatabase contactsDatabase;
	private final Context theContext;
 
	public ContactDatabase(Context context) 
	{
		super(context, DB_NAME, null, 1);
		this.theContext = context;
	}	
 
	public void createDatabase() throws IOException
	{
		// Check if database exists; if not, then create a new, empty database
		// and overwrite it with the stored database.
		boolean databaseExists = checkDatabase();
 
		if(databaseExists)
		{
			//this.getWritableDatabase();
		}
		else
		{
			this.getReadableDatabase();
			
			try 
			{
				copyDatabase();
			}
			catch(IOException e)
			{
				throw new IOException("Error copying database");
			}
		}
 	}

 	private boolean checkDatabase()
 	{
 		SQLiteDatabase checkExistence = null;
 		
 		// Check to see if the database exists at the specified location.
 		try
 		{
 			String path = DB_PATH + DB_NAME;
 			checkExistence = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
 		}
 		catch(SQLiteException e)
 		{
 			
 		}
 
 		// It exists, so close it up again.
 		if(checkExistence != null)
 		{
 			checkExistence.close();
 		}
 
 		return checkExistence != null;
 	}
 
 	private void copyDatabase() throws IOException
 	{
 		// Copies the database file from assets to the data folder.
 		InputStream theInput = theContext.getAssets().open(DB_NAME);
 
 		// Path to the just created empty database.
 		String outFileName = DB_PATH + DB_NAME;
 
 		// Open the empty database as the output stream.
 		OutputStream theOutput = new FileOutputStream(outFileName);
 
 		// Transfer file bytes from the input file to the output file.
 		byte[] buffer = new byte[1024];
 		int length;
 		while((length = theInput.read(buffer))>0)
 		{
 			theOutput.write(buffer, 0, length);
 		}
 
 		//Close the streams
 		theOutput.flush();
 		theOutput.close();
 		theInput.close();
 	}
 	
 	public void openDatabase() throws SQLException
 	{
 		String path = DB_PATH + DB_NAME;
 		contactsDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
 	}
 
 	@Override
 	public synchronized void close() 
 	{
 		if(contactsDatabase != null)
 		{
 			contactsDatabase.close();
 		}
 
 		super.close();
 	}
 
 	@Override
 	public void onCreate(SQLiteDatabase database) 
 	{
 		
 	}
 	
 	@Override
 	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) 
 	{
 
 	}
}