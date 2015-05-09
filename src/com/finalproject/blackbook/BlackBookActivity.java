package com.finalproject.blackbook;

import java.io.IOException;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class BlackBookActivity extends Activity
{
	private ContactDatabase contactDatabase;
	private Cursor contacts;
	private ListView contactListView;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_black_book);
		
		final Button newButton = (Button)findViewById(R.id.new_button);
	    newButton.setOnClickListener(new View.OnClickListener()
	    {
	    	public void onClick(View view)
	        {
	    		Intent newContactIntent = new Intent(view.getContext(), AddContactActivity.class);
	            startActivityForResult(newContactIntent, 0);
	        }
	    });
	    
	    // Add contact names to the list view.
	    contactDatabase = new ContactDatabase(this);
	    try
	    {
	    	contactDatabase.createDatabase();
	    	contactDatabase.openDatabase();
	    }
	    catch(SQLException e)
	    {
	    	
	    }
	    catch(IOException e)
	    {
	    	
	    }
	    	    
	    String[] columnNames = {"Name", BaseColumns._ID};
	    	    
	    // Populate list view with contact names.
	    contactListView = (ListView)findViewById(R.id.contacts_listView);
	    int[] destinationLayoutIDs = {R.id.contact_name_textView};
	    
	    SQLiteDatabase theDatabase = contactDatabase.getWritableDatabase();
	    contacts = theDatabase.query("Contact", columnNames, null, null, null, null, "Name");
	    CursorAdapter dataSource = new SimpleCursorAdapter(this, R.layout.contact_name, contacts, columnNames, destinationLayoutIDs, 0 /*CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER*/);
	    contactListView.setAdapter(dataSource);
	    contactDatabase.close();
	    
	    contactListView.setOnItemClickListener(new ListView.OnItemClickListener()
	    {
	    	public void onItemClick(AdapterView<?> parent, View view, int pos, long id)
	        {
	    		// Get selected item.
	    		Cursor cursor = (Cursor)parent.getAdapter().getItem(pos);
	    		
	    		// Put the contact name data into the shared preference.
	    		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	    	    SharedPreferences.Editor editor = preferences.edit();
	    	    editor.putString("Contact Name", cursor.getString(cursor.getColumnIndex("Name")));
	    	    editor.commit();
	    		
	    		Intent viewContactIntent = new Intent(view.getContext(), ViewContactActivity.class);
	            startActivityForResult(viewContactIntent, 0);
	        }
	    });
	}
	
	@Override
	public void onPause() 
	{
    	super.onPause();
    	//contacts.close();
    	contactDatabase.close();
    }
	
	@Override
	public void onResume()
	{
		super.onResume();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_black_book, menu);
		return true;
	}
}