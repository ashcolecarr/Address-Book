package com.finalproject.blackbook;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
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
import android.widget.TextView;

public class ViewContactActivity extends Activity implements ActionBar.TabListener 
{
	private ContactDatabase contactDatabase;
	private Cursor contacts;
	private Cursor contactsAddress;
	private Cursor contactsPhone;
	private Cursor contactsEmail;
	private Cursor contactsWeb;
	
	private SharedPreferences preferences;
	
	private AddressFragment addressFragment = new AddressFragment();
	private PhoneFragment phoneFragment = new PhoneFragment();
	private EmailFragment emailFragment = new EmailFragment();
	private WebFragment webFragment = new WebFragment();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
	    	    
		// Populate textview with the chosen name.
		TextView nameTextView = (TextView)findViewById(R.id.name_textView);
		nameTextView.setText(preferences.getString("Contact Name", ""));
		
		final Button editButton = (Button)findViewById(R.id.edit_button);
	    editButton.setOnClickListener(new View.OnClickListener()
	    {
	    	public void onClick(View view)
	        {
	    		Intent editContactIntent = new Intent(view.getContext(), EditContactActivity.class);
	            startActivityForResult(editContactIntent, 0);
	        }
	    });
	    
	    final Button deleteButton = (Button)findViewById(R.id.delete_button);
	    deleteButton.setOnClickListener(new View.OnClickListener()
	    {
	    	public void onClick(View view)
	        {
	    		AlertDialog.Builder deleteDialogBuilder = new AlertDialog.Builder(ViewContactActivity.this);
		    	 
    			// deleteDialogBuilder.setTitle("Your Title");
    			deleteDialogBuilder.setMessage(R.string.delete_contact_dialog).setCancelable(false).setPositiveButton(R.string.delete_dialog_button_confirm, new DialogInterface.OnClickListener() 
    			{
    				public void onClick(DialogInterface dialog, int id) 
    				{
    					contactDatabase = new ContactDatabase(getBaseContext());
    		    	    try
    		    	    {
    		    	    	contactDatabase.openDatabase();
    		    	    }
    		    	    catch(SQLException e)
    		    	    {
    		    	    	
    		    	    }
    		    	    
    		    	    // Delete the contact and all related data.
    		    	    SQLiteDatabase theDatabase = contactDatabase.getWritableDatabase();
    		    	    theDatabase.delete("Website", "ContactID = " + preferences.getInt("ContactID", 0), null);
    		    	    theDatabase.delete("Email", "ContactID = " + preferences.getInt("ContactID", 0), null);
    		    	    theDatabase.delete("Phone", "ContactID = " + preferences.getInt("ContactID", 0), null);
    		    	    theDatabase.delete("Contact", BaseColumns._ID + " = " + preferences.getInt("ContactID", 0), null);
    		    	    
    		    	    contactDatabase.close();
    		    	    
    		    	    Intent blackBookIntent = new Intent(getBaseContext(), BlackBookActivity.class);
    		            startActivityForResult(blackBookIntent, 0);
    				}
    			}).setNegativeButton(R.string.delete_dialog_button_cancel, new DialogInterface.OnClickListener() 
    			{
    				public void onClick(DialogInterface dialog, int id) 
    				{
    					dialog.cancel();
    				}
    			});
    	 
    			AlertDialog deleteDialog = deleteDialogBuilder.create();
    	 
    			deleteDialog.show();
	        }
	    });
	    
	    contactDatabase = new ContactDatabase(this);
	    try
	    {
	    	contactDatabase.openDatabase();
	    }
	    catch(SQLException e)
	    {
	    	
	    }
	    	    	    
	    String[] contactColumnNames = {BaseColumns._ID, "Name"};
	    String[] addressColumnNames = {"Line1", "Line2", "City", "State", "Zip", BaseColumns._ID};
	    String[] phoneColumnNames = {"Phone1", "Type1", "Phone2", "Type2", "Phone3", "Type3", BaseColumns._ID};
	    String[] emailColumnNames = {"Email1", "Email2", BaseColumns._ID};
	    String[] webColumnNames = {"Facebook", "Twitter", "WebsiteName", BaseColumns._ID};
	    	    
	    // Get the record id of the chosen contact name.
	    SQLiteDatabase theDatabase = contactDatabase.getWritableDatabase();
	    contacts = theDatabase.query("Contact", contactColumnNames, "Name = '" + preferences.getString("Contact Name", "") + "'", null, null, null, null);
	    
	    // Use the id to get the related information in the other tables.
	    contacts.moveToFirst();
	    int nameRecordID = contacts.getInt(contacts.getColumnIndex(BaseColumns._ID));
	    contactsAddress = theDatabase.query("Address", addressColumnNames, "ContactID = " + nameRecordID, null, null, null, null);
	    contactsPhone = theDatabase.query("Phone", phoneColumnNames, "ContactID = " + nameRecordID, null, null, null, null);
	    contactsEmail = theDatabase.query("Email", emailColumnNames, "ContactID = " + nameRecordID, null, null, null, null);
	    contactsWeb = theDatabase.query("Website", webColumnNames, "ContactID = " + nameRecordID, null, null, null, null);
	    
	    // Place the data in the appropriate shared preferences.
	    SharedPreferences.Editor editor = preferences.edit();
	    
	    editor.putInt("ContactID", nameRecordID);
	    
	    if(contactsAddress != null && contactsAddress.moveToFirst())
	    {
	    	editor.putString("Line1", contactsAddress.getString(contactsAddress.getColumnIndex("Line1")));
			editor.putString("Line2", contactsAddress.getString(contactsAddress.getColumnIndex("Line2")));
			editor.putString("City", contactsAddress.getString(contactsAddress.getColumnIndex("City")));
			editor.putString("State", contactsAddress.getString(contactsAddress.getColumnIndex("State")));
			editor.putString("Zip", contactsAddress.getString(contactsAddress.getColumnIndex("Zip")));
	    }
	    	    	    
	    if(contactsPhone != null && contactsPhone.moveToFirst())
	    {
	    	editor.putString("Phone1", contactsPhone.getString(contactsPhone.getColumnIndex("Phone1")));
		    editor.putString("Type1", contactsPhone.getString(contactsPhone.getColumnIndex("Type1")));
		    editor.putString("Phone2", contactsPhone.getString(contactsPhone.getColumnIndex("Phone2")));
		    editor.putString("Type2", contactsPhone.getString(contactsPhone.getColumnIndex("Type2")));
		    editor.putString("Phone3", contactsPhone.getString(contactsPhone.getColumnIndex("Phone3")));
		    editor.putString("Type3", contactsPhone.getString(contactsPhone.getColumnIndex("Type3")));
	    }
	    	    
	    if(contactsEmail != null && contactsEmail.moveToFirst())
	    {
	    	editor.putString("Email1", contactsEmail.getString(contactsEmail.getColumnIndex("Email1")));
		    editor.putString("Email2", contactsEmail.getString(contactsEmail.getColumnIndex("Email2")));
	    }
	    	    
	    if(contactsWeb != null && contactsWeb.moveToFirst())
	    {
	    	editor.putString("Facebook", contactsWeb.getString(contactsWeb.getColumnIndex("Facebook")));
		    editor.putString("Twitter", contactsWeb.getString(contactsWeb.getColumnIndex("Twitter")));
		    editor.putString("WebsiteName", contactsWeb.getString(contactsWeb.getColumnIndex("WebsiteName")));
	    }
	    
	    editor.commit();
	    
	    contactDatabase.close();
	    
	    // Create the action bar
	    ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.setDisplayShowTitleEnabled(false);

	    Tab tab = actionBar.newTab().setText(R.string.contact_address);
	    tab.setTabListener(this);
	    actionBar.addTab(tab);

	    tab = actionBar.newTab().setText(R.string.contact_phone);
	    tab.setTabListener(this);
	    actionBar.addTab(tab);
	    
	    tab = actionBar.newTab().setText(R.string.contact_email);
	    tab.setTabListener(this);
	    actionBar.addTab(tab);

	    tab = actionBar.newTab().setText(R.string.contact_website);
	    tab.setTabListener(this);
	    actionBar.addTab(tab);
	}
	
	public void onPause() 
	{
    	super.onPause();
    	contactDatabase.close();
    }

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction fragTrans) 
	{
		
	}
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction fragTrans) 
	{
		FragmentManager fragmentManager = getFragmentManager();
	    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	    
	    // Choose the fragment to attach based on the selected tab.
	    switch(tab.getPosition())
		{
			case 0:
				fragmentTransaction.replace(R.id.fragment_container, addressFragment);
			    //fragmentTransaction.addToBackStack(null);
			    fragmentTransaction.commit();
			    break;
			case 1:
				fragmentTransaction.replace(R.id.fragment_container, phoneFragment);
			    //fragmentTransaction.addToBackStack(null);
			    fragmentTransaction.commit();
			    break;
			case 2:
				fragmentTransaction.replace(R.id.fragment_container, emailFragment);
			    //fragmentTransaction.addToBackStack(null);
			    fragmentTransaction.commit();
			    break;
			case 3:
				fragmentTransaction.replace(R.id.fragment_container, webFragment);
			    //fragmentTransaction.addToBackStack(null);
			    fragmentTransaction.commit();
			    break;
			default:
				break;
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction fragTrans) 
	{
		
	}
}