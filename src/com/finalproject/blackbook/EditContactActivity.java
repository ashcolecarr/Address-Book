package com.finalproject.blackbook;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditContactActivity extends Activity 
{
	private ContactDatabase contactDatabase;
	private SharedPreferences preferences;
	
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
		setContentView(R.layout.edit_contact);
		
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		name = (EditText)findViewById(R.id.edit_name_entry);
		
		line1 = (EditText)findViewById(R.id.edit_address_line1_entry);
		line2 = (EditText)findViewById(R.id.edit_address_line2_entry);
		city = (EditText)findViewById(R.id.edit_city_entry);
		state = (EditText)findViewById(R.id.edit_state_entry);
		zip = (EditText)findViewById(R.id.edit_zip_entry);
		
		phone1 = (EditText)findViewById(R.id.edit_phone1_entry);
		phone1.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		type1 = (EditText)findViewById(R.id.edit_phone1_desc_entry);
		phone2 = (EditText)findViewById(R.id.edit_phone2_entry);
		phone2.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		type2 = (EditText)findViewById(R.id.edit_phone2_desc_entry);
		phone3 = (EditText)findViewById(R.id.edit_phone3_entry);
		phone3.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		type3 = (EditText)findViewById(R.id.edit_phone3_desc_entry);
				
		email1 = (EditText)findViewById(R.id.edit_email1_entry);
		email2 = (EditText)findViewById(R.id.edit_email2_entry);
				
		facebook = (EditText)findViewById(R.id.edit_facebook_entry);
		twitter = (EditText)findViewById(R.id.edit_twitter_entry);
		website = (EditText)findViewById(R.id.edit_website_entry);
		
		// Populate all the text fields with existing contact data.
		name.setText(preferences.getString("Contact Name", ""));
		
		line1.setText(preferences.getString("Line1", ""));
		line2.setText(preferences.getString("Line2", ""));
		city.setText(preferences.getString("City", ""));
		state.setText(preferences.getString("State", ""));
		zip.setText(preferences.getString("Zip", ""));
		
		phone1.setText(preferences.getString("Phone1", ""));
		phone1.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		type1.setText(preferences.getString("Type1", ""));
		phone2.setText(preferences.getString("Phone2", ""));
		phone2.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		type2.setText(preferences.getString("Type2", ""));
		phone3.setText(preferences.getString("Phone3", ""));
		phone3.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		type3.setText(preferences.getString("Type3", ""));
				
		email1.setText(preferences.getString("Email1", ""));
		email2.setText(preferences.getString("Email2", ""));
				
		facebook.setText(preferences.getString("Facebook", ""));
		twitter.setText(preferences.getString("Twitter", ""));
		website.setText(preferences.getString("WebsiteName", ""));
		
		final Button saveButton = (Button)findViewById(R.id.edit_save_button);
	    saveButton.setOnClickListener(new View.OnClickListener()
	    {
	    	public void onClick(View view)
	        {
	    		// Check if name has been entered.
	    		if(name.getText().toString().trim().length() > 0)
	    		{
	    			AlertDialog.Builder saveDialogBuilder = new AlertDialog.Builder(EditContactActivity.this);
	   	    	 
	    			// saveDialogBuilder.setTitle("Your Title");
	    			saveDialogBuilder.setMessage(R.string.save_edit_contact_dialog).setCancelable(false).setPositiveButton(R.string.save_edit_dialog_button_confirm, new DialogInterface.OnClickListener() 
	    			{
	    				public void onClick(DialogInterface dialog, int id) 
	    				{
	    					updateContact();
	    	    			
	    	    			Intent blackBookIntent = new Intent(getBaseContext(), BlackBookActivity.class);
	    		            startActivityForResult(blackBookIntent, 0);
	    				}
	    			}).setNegativeButton(R.string.save_edit_dialog_button_cancel, new DialogInterface.OnClickListener() 
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
	    			AlertDialog.Builder namelessDialogBuilder = new AlertDialog.Builder(EditContactActivity.this);
		   	    	 
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
	    
	    final Button cancelButton = (Button)findViewById(R.id.edit_cancel_button);
	    cancelButton.setOnClickListener(new View.OnClickListener()
	    {
	    	public void onClick(View view)
	        {
	    		AlertDialog.Builder cancelDialogBuilder = new AlertDialog.Builder(EditContactActivity.this);
		    	 
    			// cancelDialogBuilder.setTitle("Your Title");
    			cancelDialogBuilder.setMessage(R.string.cancel_edit_contact_dialog).setCancelable(false).setPositiveButton(R.string.cancel_edit_dialog_button_confirm, new DialogInterface.OnClickListener() 
    			{
    				public void onClick(DialogInterface dialog, int id) 
    				{
    					closeSelf();
    				}
    			}).setNegativeButton(R.string.cancel_edit_dialog_button_cancel, new DialogInterface.OnClickListener() 
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
	
	@Override
	public void onPause() 
	{
    	super.onPause();
    	//contactDatabase.close();
    }

	public void closeSelf()
	{
		this.finish();
	}
	
	public void updateContact()
	{
		contactDatabase = new ContactDatabase(this);
	    try
	    {
	    	contactDatabase.openDatabase();
	    }
	    catch(SQLException e)
	    {
	    	
	    }
	    	    
	    int nameRecordID = preferences.getInt("ContactID", 0);
	    
	    // Update contact information in the database.
	    SQLiteDatabase theDatabase = contactDatabase.getWritableDatabase();
	    ContentValues contactUpdate = new ContentValues();
	    contactUpdate.put("Name", name.getText().toString());
	    theDatabase.update("Contact", contactUpdate, BaseColumns._ID + " = " + nameRecordID, null);
	    	    
	    // If all the address fields are empty, then don't update, but rather delete the row.
	    if(line1.getText().toString() == "" && line2.getText().toString() == "" && city.getText().toString() == "" && state.getText().toString() == "" && zip.getText().toString() == "")
	    {
	    	theDatabase.delete("Address", "ContactID = " + nameRecordID, null);
	    }
	    else
	    {
	    	ContentValues addressUpsert = new ContentValues();
	    	
	    	addressUpsert.put("Line1", line1.getText().toString());
	    	addressUpsert.put("Line2", line2.getText().toString());
	    	addressUpsert.put("City", city.getText().toString());
	    	addressUpsert.put("State", state.getText().toString());
	    	addressUpsert.put("Zip", zip.getText().toString());
	    	addressUpsert.put("ContactID", nameRecordID);
	    	
	    	// Check if an address exists for the contact: insert if not; update if so.
	    	String[] addressColumns = {BaseColumns._ID};
	    	Cursor checkCursor = theDatabase.query("Address", addressColumns, "ContactID = " + nameRecordID, null, null, null, null);
	    	
	    	if(checkCursor != null)
	    	{
	    		theDatabase.update("Address", addressUpsert, "ContactID = " + nameRecordID, null);
	    	}
	    	else
	    	{
	    		theDatabase.insert("Address", null, addressUpsert);
	    	}
	    }
	    
	    // If all the phone fields are empty, then don't update, but rather delete the row.
	    if(phone1.getText().toString() == "" && phone2.getText().toString() == "" && phone3.getText().toString() == "")
	    {
	    	theDatabase.delete("Phone", "ContactID = " + nameRecordID, null);
	    }
	    else
	    {
	    	ContentValues phoneUpsert = new ContentValues();
	    	
	    	phoneUpsert.put("Phone1", phone1.getText().toString());
	    	phoneUpsert.put("Type1", type1.getText().toString());
	    	phoneUpsert.put("Phone2", phone2.getText().toString());
	    	phoneUpsert.put("Type2", type2.getText().toString());
	    	phoneUpsert.put("Phone3", phone3.getText().toString());
	    	phoneUpsert.put("Type3", type3.getText().toString());
	    	phoneUpsert.put("ContactID", nameRecordID);
	    	
	    	// Check if a phone record exists for the contact: insert if not; update if so.
	    	String[] phoneColumns = {BaseColumns._ID};
	    	Cursor checkCursor = theDatabase.query("Phone", phoneColumns, "ContactID = " + nameRecordID, null, null, null, null);
	    	
	    	if(checkCursor != null)
	    	{
	    		theDatabase.update("Phone", phoneUpsert, "ContactID = " + nameRecordID, null);
	    	}
	    	else
	    	{
	    		theDatabase.insert("Phone", null, phoneUpsert);
	    	}
	    }
	    
	    // If all the email fields are empty, then don't update, but rather delete the row.
	    if(email1.getText().toString() == "" && email2.getText().toString() == "")
	    {
	    	theDatabase.delete("Email", "ContactID = " + nameRecordID, null);
	    }
	    else
	    {
	    	ContentValues emailUpsert = new ContentValues();
	    	
	    	emailUpsert.put("Email1", email1.getText().toString());
	    	emailUpsert.put("Email2", email2.getText().toString());
	    	emailUpsert.put("ContactID", nameRecordID);
	    	
	    	// Check if an email record exists for the contact: insert if not; update if so.
	    	String[] emailColumns = {BaseColumns._ID};
	    	Cursor checkCursor = theDatabase.query("Phone", emailColumns, "ContactID = " + nameRecordID, null, null, null, null);
	    	
	    	if(checkCursor != null)
	    	{
	    		theDatabase.update("Email", emailUpsert, "ContactID = " + nameRecordID, null);
	    	}
	    	else
	    	{
	    		theDatabase.insert("Email", null, emailUpsert);
	    	}
	    }
	    
	    // If all the web fields are empty, then don't update, but rather delete the row.
	    if(facebook.getText().toString() == "" && twitter.getText().toString() == "" && website.getText().toString() == "")
	    {
	    	theDatabase.delete("Website", "ContactID = " + nameRecordID, null);
	    }
	    else
	    {
	    	ContentValues webUpsert = new ContentValues();
	    	
	    	webUpsert.put("Facebook", facebook.getText().toString());
	    	webUpsert.put("Twitter", twitter.getText().toString());
	    	webUpsert.put("WebsiteName", website.getText().toString());
	    	webUpsert.put("ContactID", nameRecordID);
	    	
	    	// Check if a web record exists for the contact: insert if not; update if so.
	    	String[] webColumns = {BaseColumns._ID};
	    	Cursor checkCursor = theDatabase.query("Website", webColumns, "ContactID = " + nameRecordID, null, null, null, null);
	    	
	    	if(checkCursor != null)
	    	{
	    		theDatabase.update("Website", webUpsert, "ContactID = " + nameRecordID, null);
	    	}
	    	else
	    	{
	    		theDatabase.insert("Website", null, webUpsert);
	    	}
	    }
	    
	    contactDatabase.close();
	}
}