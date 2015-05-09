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

public class WebFragment extends Fragment 
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
        return inflater.inflate(R.layout.website, container, false);
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
	    super.onActivityCreated(savedInstanceState);
	    
	    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

	    // Populate text boxes with the relevant information
	    TextView facebook = (TextView)getView().findViewById(R.id.facebook_textView);
	    facebook.setText(preferences.getString("Facebook", ""));
		TextView twitter = (TextView)getView().findViewById(R.id.twitter_textView);
		twitter.setText(preferences.getString("Twitter", ""));
		TextView website = (TextView)getView().findViewById(R.id.website_textView);
		website.setText(preferences.getString("WebsiteName", ""));
					    
	    /*Button findButton = (Button)getView().findViewById(R.id.go_button);
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
