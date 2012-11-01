package com.darkbrain.testfourth;


import org.apache.cordova.DroidGap;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends DroidGap
{
	

	//private API_vcluster mc; 
	private API_android Nmc;
	
	//
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        super.init();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        //mc = new MyClass(this, appView);
       // mc = new API_vcluster(this, appView);
        //mc = new API_vcluster(this, appView);
        //appView.addJavascriptInterface(mc, "APIVcluster");
        
        Nmc = new API_android(this, appView);
        appView.addJavascriptInterface(Nmc, "NAPIVcluster");
        
        
        //API_vcluster cafHelper = new API_vcluster();
        
        //appView.addJavascriptInterface(mc, "APIVcluster"); 
        
        //String wnStatus = cafHelper.hostPowerOn("test");
        //super.appView.addJavascriptInterface(new Toaster(), "toaster");
        super.loadUrl("file:///android_asset/www/index.html");
        super.appView.setOnLongClickListener(new View.OnLongClickListener() {

            public boolean onLongClick(View v) {
                return true;
            }
        });
    }   
    
}


