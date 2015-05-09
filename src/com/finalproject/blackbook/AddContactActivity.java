package com.finalproject.blackbook;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContactActivity extends Activity //implements SaveDialogFragment.SaveDialogListener
{
	private ContactDatabase contactDatabase;
	private Cursor contacts;
	
	private EditText name;
	
	private EditText line1;
	private EditText line2;
	private EditText city;
	private EditText state;
	private EditText zip;
	
	private EditText phone1;
	private EditText type1;
	private EditText phone2;
	private EditText type2;
	private EditText phone3;
	private EditText type3;
	
	private EditText email1;
	private EditText email2;
	
	private EditText facebook;
	private EditText twitter;
	private EditText website;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_contact);
		
		name = (EditText)findViewById(R.id.new_name_entry);
		
		line1 = (EditText)findViewById(R.id.new_address_line1_entry);
		line2 = (EditText)findViewById(R.id.new_address_line2_entry);
		city = (EditText)findViewById(R.id.new_city_entry);
		state = (EditText)findViewById(R.id.new_state_entry);
		zip = (EditText)findViewById(R.id.new_zip_entry);
		
		phone1 = (EditText)findViewById(R.id.new_phone1_entry);
		phone1.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		type1 = (EditText)findViewById(R.id.new_phone1_desc_entry);
		phone2 = (EditText)findViewById(R.id.new_phone2_entry);
		phone2.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		type2 = (EditText)findViewById(R.id.new_phone2_desc_entry);
		phone3 = (EditText)findViewById(R.id.new_phone3_entry);
		phone3.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		type3 = (EditText)findViewById(R.id.new_phone3_desc_entry);
				
		email1 = (EditText)findViewById(R.id.new_email1_entry);
		email2 = (EditText)findViewById(R.id.new_email2_entry);
				
		facebook = (EditText)findViewById(R.id.new_facebook_entry);
		twitter = (EditText)findViewById(R.id.new_twitter_entry);
		website = (EditText)findViewById(R.id.new_website_entry);
		
		final Button saveButton = (Button)findViewById(R.id.new_save_button);
	    saveButton.setOnClickListener(new View.OnClickListener()
	    {
	    	public void onClick(View view)
	        {
	    		// Check if name has been entered.
	    		if(name.getText().toString().trim().length() > 0)
	    		{
	    			AlertDialog.Builder saveDialogBuilder = new AlertDialog.Builder(AddContactActivity.this);
	    	 
	    			// saveDialogBuilder.setTitle("Your Title");
	    			saveDialogBuilder.setMessage(R.string.save_new_contact_dialog).setCancelable(false).setPositiveButton(R.string.save_new_dialog_button_confirm, new DialogInterface.OnClickListener() 
	    			{
	    				public void onClick(DialogInterface dialog, int id) 
	    				{
	    					insertContact();
	    	    			
	    	    			Intent blackBookIntent = new Intent(getBaseContext(), BlackBookActivity.class);
	    		            startActivityForResult(blackBookIntent, 0);
	    				}
	    			}).setNegativeButton(R.string.save_new_dialog_button_cancel, new DialogInterface.OnClickListener() 
	    			{
	    				public void onClick(DialogInterface dialog, int id) 
	    				{
	    					dialog.cancel();
	    				}
	    			});
	    	 
	    			AlertDialog saveDialog = saveDialogBuilder.create();
	    	 
	    			saveDialog.show();    			
	    		}
	    		else
	    		{
	    			AlertDialog.Builder namelessDialogBuilder = new AlertDialog.Builder(AddContactActivity.this);
	   	    	 
	    			// saveDialogBuilder.setTitle("Your Title");
	    			namelessDialogBuilder.setMessage(R.string.nameless_contact_dialog).setCancelable(false).setPositiveButton(R.string.nameless_dialog_button_confirm, new DialogInterface.OnClickListener() 
	    			{
	    				public void onClick(DialogInterface dialog, int id) 
	    				{
	    					dialog.dismiss();
	    				}
	    			});
	    	 
	    			AlertDialog namelessDialog = namelessDialogBuilder.create();
	    	 
	    			namelessDialog.show();
	    		}
	        }
	    });
	    
	    final Button cancelButton = (Button)findViewById(R.id.new_cancel_button);
	    cancelButton.setOnClickListener(new View.OnClickListener()
	    {
	    	public void onClick(View view)
	        {
	    		AlertDialog.Builder cancelDialogBuilder = new AlertDialog.Builder(AddContactActivity.this);
		    	 
    			// cancelDialogBuilder.setTitle("Your Title");
    			cancelDialogBuilder.setMessage(R.string.cancel_new_contact_dialog).setCancelable(false).setPositiveButton(R.string.cancel_new_dialog_button_confirm, new DialogInterface.OnClickListener() 
    			{
    				public void onClick(DialogInterface dialog, int id) 
    				{
    					closeSelf();
    				}
    			}).setNegativeButton(R.string.cancel_new_dialog_button_cancel, new DialogInterface.OnClickListener() 
    			{
    				public void onClick(DialogInterface dialog, int id) 
    				{
    					dialog.cancel();
    				}
    			});
    	 
    			AlertDialog cancelDialog = cancelDialogBuilder.create();
    	 
    			cancelDialog.show();
	        }
	    });
	}
	
	public void onPause() 
	{
    	super.onPause();
    	//contactDataBase.close();
    }

	public void closeSelf()
	{
		this.finish();
	}
	
	/*public void showSaveDialog() 
	{
        // Create an instance of the dialog fragment and show it
        DialogFragment saveDialog = new SaveDialogFragment();
        saveDialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) 
    {
        // User touched the dialog's positive button        
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) 
    {
        // User touched the dialog's negative button        
    }*/
	
	public void insertContact()
	{
		contactDatabase = new ContactDatabase(this);
	    try
	    {
	    	contactDatabase.openDatabase();
	    }
	    catch(SQLException e)
	    {
	    	
	    }
	    	    
	    // Insert contact name into the database.
	    SQLiteDatabase theDatabase = contactDatabase.getWritableDatabase();
	    ContentValues contactInsert = new ContentValues();
	    contactInsert.put("Name", name.getText().toString());
	    theDatabase.insert("Contact", null, contactInsert);
	    
	    // Query contact table to get the id of the record just entered.
	    String[] columnNames = {BaseColumns._ID, "Name"};
	    contacts = theDatabase.query("Contact", columnNames, columnNames[1] + " = '" + name.getText().toString() + "'", null, null, null, null);
	    contacts.moveToFirst();
	    int nameRecordID = contacts.getInt(contacts.getColumnIndex(BaseColumns._ID));
	    	    
	    // If all the address fields are empty, don't bother inserting anything into the database.
	    if(line1.getText().toString() == "" && line2.getText().toString() == "" && city.getText().toString() == "" && state.getText().toString() == "" && zip.getText().toString() == "")
	    {
	    	
	    }
	    else
	    {
	    	ContentValues addressInsert = new ContentValues();
	    	
	    	addressInsert.put("Line1", line1.getText().toString());
	    	addressInsert.put("Line2", line2.getText().toString());
	    	addressInsert.put("City", city.getText().toString());
	    	addressInsert.put("State", state.getText().toString());
	    	addressInsert.put("Zip", zip.getText().toString());
	    	addressInsert.put("ContactID", nameRecordID);
	    	
	    	theDatabase.insert("Address", null, addressInsert);
	    }
	    
	    // If all the phone fields are empty, don't bother inserting anything into the database.
	    if(phone1.getText().toString() == "" && phone2.getText().toString() == "" && phone3.getText().toString() == "")
	    {
	    	
	    }
	    else
	    {
	    	ContentValues phoneInsert = new ContentValues();
	    	
	    	phoneInsert.put("Phone1", phone1.getText().toString());
	    	phoneInsert.put("Type1", type1.getText().toString());
	    	phoneInsert.put("Phone2", phone2.getText().toString());
	    	phoneInsert.put("Type2", type2.getText().toString());
	    	phoneInsert.put("Phone3", phone3.getText().toString());
	    	phoneInsert.put("Type3", type3.getText().toString());
	    	phoneInsert.put("ContactID", nameRecordID);
	    	
	    	theDatabase.insert("Phone", null, phoneInsert);
	    }
	    
	    // If all the email fields are empty, don't bother inserting anything into the database.
	    if(email1.getText().toString() == "" && email2.getText().toString() == "")
	    {
	    	
	    }
	    else
	    {
	    	ContentValues emailInsert = new ContentValues();
	    	
	    	emailInsert.put("Email1", email1.getText().toString());
	    	emailInsert.put("Email2", email2.getText().toString());
	    	emailInsert.put("ContactID", nameRecordID);
	    	
	    	theDatabase.insert("Email", null, emailInsert);
	    }
	    
	    // If all the web fields are empty, don't bother inserting anything into the database.
	    if(facebook.getText().toString() == "" && twitter.getText().toString() == "" && website.getText().toString() == "")
	    {
	    	
	    }
	    else
	    {
	    	ContentValues webInsert = new ContentValues();
	    	
	    	webInsert.put("Facebook", facebook.getText().toString());
	    	webInsert.put("Twitter", twitter.getText().toString());
	    	webInsert.put("WebsiteName", website.getText().toString());
	    	webInsert.put("ContactID", nameRecordID);
	    	
	    	theDatabase.insert("Website", null, webInsert);
	    }
	    
	    contactDatabase.close();
	}
}