package mwb.upc.edu.myapplication1;

import android.Manifest;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private FragmentTransaction beginTransaction;
    TextView tv;

    private Handler handler = new Handler(){
          public void handleMessage(Message msg){
             String result = (String)msg.getData().get("result");
             //String obj = (String)msg.obj;
             tv.setText(result);
          }
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getResources().getDrawable(R.mipmap.ic_launcher);
        Button btn = (Button) findViewById(R.id.button3);
        tv = (TextView)findViewById(R.id.tv_hello1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.INTERNET}, 1);
                //tv.setText("aaaa");
              new Thread(){
                   public void  run(){
                        getData();
                    }
                }.start();
                //getData();
            }
        });
    }

    public void getData(){
     try{
         String path = "https://www.baidu.com/";
         URL url = new URL(path);
         HttpURLConnection conn = (HttpURLConnection)url.openConnection();
         conn.setRequestMethod("GET");
         conn.setConnectTimeout(5000);
         int responseCode = conn.getResponseCode();
         if(responseCode == 200) {
             InputStream is = conn.getInputStream();
             BufferedReader bis = new BufferedReader(new InputStreamReader(is));
             String line = null;
             while((line=bis.readLine())!=null) {
                 if(line!=null){
                     //System.out.println(line);
                     //tv.setText(line);
                     //JSONObject json = new JSONObject(line);
                     //tv.setText(json.get("status").toString());
                     Message msg = new Message();
                     Bundle  bundle = new Bundle();
                     bundle.putString("result",line);
                     //bundle.putString("status",json.get("status").toString());
                     msg.setData(bundle);
                     handler.sendMessage(msg);
                 }

             }
         }

     }catch (Exception e){
         e.printStackTrace();
     }
        }
    }

