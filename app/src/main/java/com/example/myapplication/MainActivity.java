package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.VpnService;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String TAG = "CCLOVECC";
    final String[] perms = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    String path = null;
    File recordFile;

    Button mBtnRecord = null;
    TextView mTxtState = null;
    Button mBtnVpnCon = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //控件
        mBtnRecord = (Button) findViewById(R.id.button_rcd);
        mTxtState = (TextView) findViewById(R.id.editText_state);
        mBtnVpnCon = (Button) findViewById(R.id.button_con);

        //初始化控件
        mBtnRecord.setOnTouchListener(new listen());

        //parse way
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        recordFile = new File(externalStorageDirectory.toString() + "/sRcd");
        boolean b = recordFile.mkdirs();

        ActivityCompat.requestPermissions(MainActivity.this, perms, 1);

        mBtnVpnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = VpnService.prepare(MainActivity.this);
                if (intent != null) {
                    startActivityForResult(intent, 0);
                } else {
                    onActivityResult(0, RESULT_OK, null);
                }
            }
        });


        path= externalStorageDirectory.toString();
        Log.d(TAG, "onCreate: "+path.toString());

    }

    class listen implements View.OnTouchListener{
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startRecord();
                    break;
                case MotionEvent.ACTION_UP:
                    stopRecord();
                    break;
            }
            return false;
        }
    }

    private void stopRecord() {
        mTxtState.setText("OFF");
    }

    private void startRecord() {
        mTxtState.setText("ON");

        long time = System.currentTimeMillis();
        String state = android.os.Environment.getExternalStorageState();
        Log.i(TAG, "startRecord: state==  "+state);



//        File f = new File(recordFile, String.valueOf(time) + ".txt");
//        Log.d(TAG, "startRecord: " + f.toString());
//        try {
//            boolean isSuc = f.createNewFile();
//            Log.d(TAG, "startRecord: " + String.valueOf(isSuc));
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e(TAG, "startRecord: ", e);
//        }
//        try (FileOutputStream fos = new FileOutputStream(f.toString(),false)) {
//            fos.write(6);
//            fos.flush();
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            Log.e(TAG, "startRecord: ", e);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
