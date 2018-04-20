package com.vinod.broadcastreceiverapp;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE =200 ;
    DynamicBroadcastReceiver mDynamicBroadcastReceiver =null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M) {
            if (!checkIfAlreadyhavePermission()) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, REQ_CODE);

            }
        }

    }
    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS);
        return result == PackageManager.PERMISSION_GRANTED ? true: false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //below lines for dynamic broadcast receiver
        mDynamicBroadcastReceiver = new DynamicBroadcastReceiver();
        IntentFilter filter = new IntentFilter("com.vinod.broadcastreceiverapp.DYNAMIC_RECEIVER");
        registerReceiver(mDynamicBroadcastReceiver,filter);
        //-------------------------------------------------


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode ==REQ_CODE)
        {
            if(grantResults!=null && grantResults.length> 0)
            {
                toast("granted");
            }
            else
                toast("Not granted");


        }
        else
            toast("Not granted");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    void toast(String str)
    {
        Toast.makeText(this, str+"", Toast.LENGTH_SHORT).show();

    }

    public void onClkSendBroadcast(View view)
    {
        Intent intent = new Intent("com.vinod.broadcastreceiverapp.CUSTOM_RECEIVER");
        intent.putExtra("MSG","From Main Activity data is sent");
        sendBroadcast(intent);
    }

    public void onClkDynamicBroadcast(View view)
    {
        Intent intent = new Intent("com.vinod.broadcastreceiverapp.DYNAMIC_RECEIVER");
        intent.putExtra("MSG","From Main Activity data is sent");
        sendBroadcast(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mDynamicBroadcastReceiver);
    }
}
