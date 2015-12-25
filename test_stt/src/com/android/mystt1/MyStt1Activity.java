package com.android.mystt1;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class MyStt1Activity extends Activity implements OnClickListener {
    
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    
    private ListView mList;
    
    private  String resultString ="";
    private ArrayList<String> matches =null;
    private int i=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        Button speakButton = (Button) findViewById(R.id.btn_speak);
        
        mList = (ListView) findViewById(R.id.list);

        PackageManager pm = getPackageManager();
        Intent intent = new Intent(RecognizerIntent.ACTION_WEB_SEARCH);//开启网络语音模式
        List<ResolveInfo> activities = pm.queryIntentActivities(//判断哪个Intent可用
//                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        		intent, 0);
        if (activities.size() != 0) {
            speakButton.setOnClickListener(this);
            
        } else {
            speakButton.setEnabled(false);
            speakButton.setText("Recognizer not present");
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_speak) {
            startMysttActivityActivity();
        }
    }

    private void startMysttActivityActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);//开启语音活动
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,//语音识别模式
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);//自由选择一种语言
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition 开始");//提示用户可以开始语音了
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }
  
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
        	matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);//结果
            mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    matches));
        }
        	
        	for(;i< matches.size();i++){
        	 resultString+=matches.get(i);
        	}
        	Toast.makeText(getBaseContext(), resultString, 1000).show();
        
        
        super.onActivityResult(requestCode, resultCode, data);
    }
}
