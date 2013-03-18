package com.sap.clientproject;

import android.app.ListActivity;
import android.os.Bundle;

public class AdListActivity extends ListActivity {
    static final String[] AD_LIST = new String[] {"Baume", "Cool Cafe", "Ike's Place", "Coupa Cafe", "Evvia Estiatorio", "Garden Fresh", "Tamarine"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setListAdapter(new AdListArrayAdapter(this, AD_LIST));
    }

}
