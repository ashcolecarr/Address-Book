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

public class PhoneFragment extends Fragment 
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
        return inflater.inflate(R.layout.phone, container, false);
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
	    super.onActivityCreated(savedInstanceState);
	    
	    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

	    // Populate text boxes with the relevant information
	    TextView phone1 = (TextView)getView().findViewById(R.id.phone1_textView);
	    phone1.setText(preferences.getString("Phone1", ""));
	    TextView type1 = (TextView)getView().findViewById(R.id.type1_textView);
	    type1.setText(preferences.getString("Type1", ""));
	    TextView phone2 = (TextView)getView().findViewById(R.id.phone2_textView);
	    phone2.setText(preferences.getString("Phone2", ""));
	    TextView type2 = (TextView)getView().findViewById(R.id.type2_textView);
	    type2.setText(preferences.getString("Type2", ""));
	    TextView phone3 = (TextView)getView().findViewById(R.id.phone3_textView);
	    phone3.setText(preferences.getString("Phone3", ""));
	    TextView type3 = (TextView)getView().findViewById(R.id.type3_textView);
	    type3.setText(preferences.getString("Type3", ""));
	    			    
	    /*Button findButton = (Button)getView().findViewById(R.id.call_button);
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
