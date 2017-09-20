package com.kamin.loaderobj;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btStart, btFile, btFile2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btStart = (Button) findViewById(R.id.btStart);
        btFile = (Button) findViewById(R.id.btFile);
        btFile2 = (Button) findViewById(R.id.btFile2);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calc();
            }
        });
        btFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                file();
            }
        });
        btFile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                file2();
            }
        });
    }

    private void file2() {

    }

    private void file() {
        File there = getFilesDir();
        Log.d("File1 ", "path=" + getCacheDir().getAbsolutePath());
        File newF = new File(getFilesDir()+File.separator+"newFile");
        Log.d("File1 ", " newF.exists = " + newF.exists());
        Log.d("File1 ", "scan " + newF.getAbsolutePath());


        File f = new File(getFilesDir()+File.separator+"newFile");
        Log.d("File1 ", "f exist? " + f.exists());
        try {
            f.createNewFile();
            Log.d("File1 ", "f exist? " + f.exists());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String string = "Hello world!";
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput("Hello", Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            RandomAccessFile aFile = new RandomAccessFile(newF, "rw");
            float[] fa = new float[]{1f, 2f, 3f, 4f, 5f};
            ByteBuffer byteBuffer = ByteBuffer.allocate(fa.length*4);
            byteBuffer.asFloatBuffer().put(fa);
            FileChannel channel = aFile.getChannel();
            channel.write(byteBuffer);
            channel.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        float[] fa2 = new float[5];
        try{
            RandomAccessFile rFile = new RandomAccessFile(newF, "rw");
            FileChannel inChannel = rFile.getChannel();
            ByteBuffer buf_in = ByteBuffer.allocate(fa2.length*4);
            inChannel.read(buf_in);
            buf_in.rewind();
            buf_in.asFloatBuffer().get(fa2);
            inChannel.close();
        }
        catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        Log.d("File1 ", "- " + fa2[0]+" "+ fa2[1]+" "+ fa2[2]+" "+ fa2[3]+" "+ fa2[4]);

        for (int i = 0; i < there.listFiles().length; i++) {
            Log.d("File1 ", "- " + there.listFiles()[i].getName()+"  - " + there.listFiles()[i].length());
        }

    }

    private void calc() {
        Log.d("COUNT ", "START");
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
                if (line.startsWith("v ")) {
                    countV++;
                    lineArray = line.substring(2).trim().split(" ");
                    for (String s : lineArray) {
                        vList.add(s);
                        //Log.d("COUNT "+"n ",""+s);
                    }
                }
                if (line.startsWith("vt ")) {
                    countVt++;
                    lineArray = line.substring(3).trim().split(" ");
                    for (String s : lineArray) {
                        vtList.add(s);
                        //Log.d("COUNT "+"vt ",""+s);
                    }
                }
                if (line.startsWith("vn ")) {
                    countVn++;
                    lineArray = line.substring(3).trim().split(" ");
                    for (String s : lineArray) {
                        vnList.add(s);
                        //Log.d("COUNT "+"vn ",""+s);
                    }
                }
            }

            bufferedReader.close();
            inputStream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


        Log.d("COUNT ", "START V");
        FloatBuffer fb = FloatBuffer.allocate(vList.size());
        for (int i = 0; i < vList.size(); i++) {
            fb.put(i, Float.valueOf(vList.get(i)));
        }
        Log.d("COUNT ", "START fb.capacity" + fb.capacity() + "START fb.limit" + fb.limit());

        Log.d("COUNT ", "START VT");
        float[] vt = new float[vtList.size()];
        for (int i = 0; i < vtList.size(); i++) {
            vt[i] = Float.valueOf(vtList.get(i));
        }

        Log.d("COUNT ", "START VN");
        float[] vn = new float[vnList.size()];
        for (int i = 0; i < vnList.size(); i++) {
            vn[i] = Float.valueOf(vnList.get(i));
        }
        File there = new File(getCacheDir().getAbsolutePath());


        Log.d("COUNT " + "FromFile size v ", "" + String.valueOf((System.currentTimeMillis() - start) / 1000) + " sec");
        Log.d("COUNT " + "FromFile size v ", "" + countV + " vList " + "" + vList.size());
        Log.d("COUNT " + "FromFile size vn ", "" + countVt + " vtList " + "" + vtList.size());
        Log.d("COUNT " + "FromFile size vt ", "" + countVn + " vnList " + "" + vnList.size());

    }
}
