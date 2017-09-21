package com.kamin.loaderobj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
    FloatBuffer floatBuffer;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        file = new File(getFilesDir() + File.separator + "si.vtx");
        file.delete();
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
                long start = System.currentTimeMillis();
                file = new File(getFilesDir() + File.separator + "si.v");
                LoadBuff(file);
                file = new File(getFilesDir() + File.separator + "si.vt");
                LoadBuff(file);
                file = new File(getFilesDir() + File.separator + "si.vn");
                LoadBuff(file);
                Log.d("File1 ", "- " + (System.currentTimeMillis() - start));
                for (int i = 0; i < getFilesDir().listFiles().length; i++) {
                    Log.d("File1 ", "- " + getFilesDir().listFiles()[i].getAbsolutePath()+"  - " + getFilesDir().listFiles()[i].length());
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

    private void writeToFile(FloatBuffer floatBuffer, File file) {
        file.delete();
        try {
            RandomAccessFile aFile = new RandomAccessFile(file, "rw");
            ByteBuffer byteBuffer = ByteBuffer.allocate(floatBuffer.capacity() * 4);
            byteBuffer.asFloatBuffer().put(floatBuffer);
            FileChannel channel = aFile.getChannel();
            channel.write(byteBuffer);
            channel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FloatBuffer LoadBuff(File file) {
        FloatBuffer result = FloatBuffer.allocate(0);

        try {
            RandomAccessFile rFile = new RandomAccessFile(file, "rw");
            Log.d("File1 ", "path " + file.getAbsolutePath());
            if (file.exists()) {
                FileChannel inChannel = rFile.getChannel();
                Log.d("File1 ", "file.length() " + file.length());
                ByteBuffer buf_in = ByteBuffer.allocate((int) file.length());
                inChannel.read(buf_in);
                buf_in.rewind();
                result = buf_in.asFloatBuffer();
                inChannel.close();
            } else
                Log.d("File1 ", "not exist " + file.getAbsolutePath());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        Log.d("File1 ", "LoadBuff " + file.getName() + " " + result);
        return result;
    }

    private void calc() {
        Log.d("COUNT ", "START");
        List<String> vList = new ArrayList<>();
        List<String> vtList = new ArrayList<>();
        List<String> vnList = new ArrayList<>();

        String[] lineArray;
        InputStream inputStream = getResources().openRawResource(R.raw.si);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        long start = System.currentTimeMillis();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("v ")) {
                    lineArray = line.substring(2).trim().split(" ");
                    for (String s : lineArray) {
                        vList.add(s);
                    }
                }
                if (line.startsWith("vt ")) {
                    lineArray = line.substring(3).trim().split(" ");
                    for (String s : lineArray) {
                        vtList.add(s);
                    }
                }
                if (line.startsWith("vn ")) {
                    lineArray = line.substring(3).trim().split(" ");
                    for (String s : lineArray) {
                        vnList.add(s);
                    }
                }
            }

            bufferedReader.close();
            inputStream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Log.d("COUNT ", "START V" + vList.size());
        Log.d("COUNT ", "START Vt" + vtList.size());
        Log.d("COUNT ", "START Vn" + vnList.size());


        Log.d("COUNT ", "START V");
        floatBuffer = FloatBuffer.allocate(vList.size());
        for (int i = 0; i < vList.size(); i++) {
            floatBuffer.put(i, Float.valueOf(vList.get(i)));
        }
        file = new File(getFilesDir() + File.separator + "si.v");
        writeToFile(floatBuffer, file);
        Log.d("COUNT ", "capacity "+floatBuffer.capacity());


        Log.d("COUNT ", "START VN");
        floatBuffer = FloatBuffer.allocate(vnList.size());
        float[] vn = new float[vnList.size()];
        for (int i = 0; i < vnList.size(); i++) {
            vn[i] = Float.valueOf(vnList.get(i));
        }
        file = new File(getFilesDir() + File.separator + "si.vn");
        writeToFile(floatBuffer, file);
        Log.d("COUNT ", "capacity "+floatBuffer.capacity());


        Log.d("COUNT ", "START VT");
        floatBuffer = FloatBuffer.allocate(vtList.size());
        float[] vt = new float[vtList.size()];
        for (int i = 0; i < vtList.size(); i++) {
            floatBuffer.put(i, Float.valueOf(vtList.get(i)));
        }
        file = new File(getFilesDir() + File.separator + "si.vt");
        writeToFile(floatBuffer, file);
        Log.d("COUNT ", "capacity "+floatBuffer.capacity());



        Log.d("COUNT ", "END");

    }
}
