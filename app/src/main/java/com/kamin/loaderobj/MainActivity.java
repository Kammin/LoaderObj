package com.kamin.loaderobj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
    FloatBuffer fb;

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
                file(fb, "VertexBuffer");
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

    private void file(FloatBuffer floatBuffer, String fileName) {
        File newF = new File(getFilesDir()+File.separator+fileName);
        Log.d("File1 ", " newF.exists = " + newF.exists());
        try {
            RandomAccessFile aFile = new RandomAccessFile(newF, "rw");
            ByteBuffer byteBuffer = ByteBuffer.allocate(floatBuffer.capacity()*4);
            byteBuffer.asFloatBuffer().put(floatBuffer);
            FileChannel channel = aFile.getChannel();
            channel.write(byteBuffer);
            channel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < getFilesDir().listFiles().length; i++) {
            Log.d("File1 ", "- " + getFilesDir().listFiles()[i].getAbsolutePath()+"  - " + getFilesDir().listFiles()[i].length());
        }
        Log.d("File1 ", "capacity " + LoadBuff(newF).capacity());
    }

    private FloatBuffer LoadBuff(File file) {
        FloatBuffer result= FloatBuffer.allocate(0);

        try{
            RandomAccessFile rFile = new RandomAccessFile(file, "rw");
            Log.d("File1 ", "path " + file.getAbsolutePath());
            if(file.exists()){
                FileChannel inChannel = rFile.getChannel();
                Log.d("File1 ", "file.length() " + file.length());
                ByteBuffer buf_in = ByteBuffer.allocate((int)file.length());
                inChannel.read(buf_in);
                buf_in.rewind();
                result = buf_in.asFloatBuffer();
                inChannel.close();
            }
            else
                Log.d("File1 ", "not exist " + file.getAbsolutePath());
        }
        catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
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
        fb = FloatBuffer.allocate(vList.size());
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
