package com.kamin.loaderobj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.nio.FloatBuffer;

public class MainActivity extends AppCompatActivity {
    Button btStart, btFile, btFile2;
    FloatBuffer floatBuffer;
    File file;
    Loader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        file = new File(getFilesDir() + File.separator + "si.vtx");
        file.delete();
        btStart = (Button) findViewById(R.id.btStart);
        btFile = (Button) findViewById(R.id.btFile);
        btFile2 = (Button) findViewById(R.id.btFile2);
        loader = new Loader(this);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loader.parse(R.raw.cube);
            }
        });
        btFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long start = System.currentTimeMillis();
                Log.d("File1 ", "count " + getFilesDir().listFiles().length);
                for (int i = 0; i < getFilesDir().listFiles().length; i++) {
                    Log.d("File1 ", "- " + getFilesDir().listFiles()[i].getAbsolutePath()+"  - " + getFilesDir().listFiles()[i].length());
                    file = new File(getFilesDir().listFiles()[i].getAbsolutePath());
                    loader.LoadBuff(file);
                }
                Toast.makeText(getApplicationContext(), "" + (System.currentTimeMillis() - start), Toast.LENGTH_SHORT).show();
            }
        });
        btFile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void file2() {

    }



}
