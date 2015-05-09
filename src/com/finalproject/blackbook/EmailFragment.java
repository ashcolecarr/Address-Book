package com.finalproject.blackbook;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class EmailFragment extends Fragment 
{
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.email, container, false);
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
	    super.onActivityCreated(savedInstanceState);
	    
	    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

	    // Populate text boxes with the relevant information
	    TextView email1 = (TextView)getView().findViewById(R.id.email1_textView);
	    email1.setText(preferences.getString("Email1", ""));
	    TextView email2 = (TextView)getView().findViewById(R.id.email2_textView);
	    email2.setText(preferences.getString("Email2", ""));
	    			    
	    /*Button findButton = (Button)getView().findViewById(R.id.email_button);
        findButton.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View view) 
            {
            	
            }
        });*/
	    
	}
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) 
    {
    	super.onConfigurationChanged(newConfig);
    }
	
	@Override
	public void onResume()
	{
		super.onResume();
	}
}
