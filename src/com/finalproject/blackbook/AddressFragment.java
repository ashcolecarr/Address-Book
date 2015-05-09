package com.finalproject.blackbook;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AddressFragment extends Fragment 
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
        return inflater.inflate(R.layout.address, container, false);
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
	    super.onActivityCreated(savedInstanceState);
	    
	    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

	    // Populate text boxes with the relevant information
	    TextView line1 = (TextView)getView().findViewById(R.id.address1_textView);
	    line1.setText(preferences.getString("Line1", ""));
		TextView line2 = (TextView)getView().findViewById(R.id.address2_textView);
		line2.setText(preferences.getString("Line2", ""));
		TextView city = (TextView)getView().findViewById(R.id.state_textView);
		city.setText(preferences.getString("City", ""));
		TextView state = (TextView)getView().findViewById(R.id.city_textView);
		state.setText(preferences.getString("State", ""));
		TextView zip = (TextView)getView().findViewById(R.id.zip_textView);
		zip.setText(preferences.getString("Zip", ""));
			    
	    Button locationButton = (Button)getView().findViewById(R.id.location_button);
        locationButton.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View view) 
            {
            	Intent showLocationIntent = new Intent(view.getContext(), ShowLocationActivity.class);
            	startActivityForResult(showLocationIntent, 0);
            }
        });
	    
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
