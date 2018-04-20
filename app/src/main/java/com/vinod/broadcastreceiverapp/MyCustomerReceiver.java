package com.vinod.broadcastreceiverapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyCustomerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent!=null && intent.getExtras()!=null)
        {
            String str =(String) intent.getExtras().get("MSG");
            if(str!=null)
            {
                Log.e("MyCustomerReceiver :" ,str+"");
            }
        }

    }
}
