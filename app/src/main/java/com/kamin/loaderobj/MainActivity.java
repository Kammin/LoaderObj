package com.kamin.loaderobj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btStart = (Button) findViewById(R.id.btStart);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calc();
            }
        });
    }

    private void calc() {
        Log.d("COUNT ","START");
        List<String> vList = new ArrayList<>();
        List<String> vtList = new ArrayList<>();
        List<String> vnList = new ArrayList<>();

        int countV = 0, countVt = 0, countVn = 0;
        String[] lineArray;
        InputStream inputStream = getResources().openRawResource(R.raw.si);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        long start = System.currentTimeMillis();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if(line.startsWith("v ")){
                    countV ++;
                    lineArray = line.substring(2).trim().split(" ");
                    for(String s : lineArray){
                        vList.add(s);
                        //Log.d("COUNT "+"n ",""+s);
                    }
                }
                if(line.startsWith("vt ")){
                    countVt ++;
                    lineArray = line.substring(3).trim().split(" ");
                    for(String s : lineArray){
                        vtList.add(s);
                        //Log.d("COUNT "+"vt ",""+s);
                    }
                }
                if(line.startsWith("vn ")){
                    countVn ++;
                    lineArray = line.substring(3).trim().split(" ");
                    for(String s : lineArray){
                        vnList.add(s);
                        //Log.d("COUNT "+"vn ",""+s);
                    }
                }
            }

            bufferedReader.close();
            Log.d("COUNT ","START 2");
            float[] v = new float[vList.size()];
            for(int i=0;i<vList.size();i++){
                v[i]= Float.valueOf(vList.get(i));
            }
            inputStream.close();
        } catch (IOException e1) {
            e1.printStackTrace();}
        Log.d("COUNT "+"FromFile size v ",""+String.valueOf((System.currentTimeMillis()-start)/1000)+" sec");
        Log.d("COUNT "+"FromFile size v ",""+countV+" vList "+""+vList.size());
        Log.d("COUNT "+"FromFile size vn ",""+countVt+" vtList "+""+vtList.size());
        Log.d("COUNT "+"FromFile size vt ",""+countVn+" vnList "+""+vnList.size());

    }
}
