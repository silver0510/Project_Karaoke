package com.example.sinki.project_karaoke;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.sinki.adapter.MusicAdapter;
import com.example.sinki.model.BaiHat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String DATABASE_NAME="Arirang.sqlite";
    private static final String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database=null;

    ListView lvBaiHatGoc;
    ArrayList<String>dsBaiHatGoc;
    ArrayList<String>dsBaiHatHienThi;
    //ArrayAdapter<String>adapterBaiHatGoc;

    //ArrayList<BaiHat>dsBaiHatGoc;
    MusicAdapter adapterBaiHatGoc;

    ListView lvBaiHatYeuThich;
    //ArrayList<BaiHat>dsBaiHatYeuThich;
    //MusicAdapter adapterBaiHatYeuThich;

    EditText txtSearch;

    TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xuLySaoChepCSDLTuAssetsVaoHeThongMobile();

        addControls();
        addEvents();
    }

    private void addControls() {
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("",getResources().getDrawable(R.drawable.music));
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("",getResources().getDrawable(R.drawable.favoritemusic));
        tabHost.addTab(tab2);

        txtSearch = (EditText) findViewById(R.id.txtSearch);

        lvBaiHatGoc = (ListView) findViewById(R.id.lvBaiHatGoc);
        dsBaiHatGoc = new ArrayList<>();
        dsBaiHatHienThi = new ArrayList<>();
       //adapterBaiHatGoc = new MusicAdapter(MainActivity.this,R.layout.item,dsBaiHatGoc);
        adapterBaiHatGoc = new MusicAdapter(MainActivity.this,R.layout.itemtext,dsBaiHatHienThi);
        lvBaiHatGoc.setAdapter(adapterBaiHatGoc);
        lvBaiHatGoc.setTextFilterEnabled(true);
//        lvBaiHatYeuThich = (ListView) findViewById(R.id.lvBaiHatYeuThich);
//        dsBaiHatYeuThich = new ArrayList<>();
//        adapterBaiHatYeuThich = new MusicAdapter(MainActivity.this,R.layout.item,dsBaiHatYeuThich);
//        lvBaiHatYeuThich.setAdapter(adapterBaiHatYeuThich);
        xuLyHienThiBaiHatGoc();
    }

    private void xuLyHienThiBaiHatGoc() {
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = database.query("ArirangSongList", null, null, null, null, null, null);
        dsBaiHatGoc.clear();
        while (cursor.moveToNext())
        {
//            String mabh=cursor.getString(0);
            String ten = cursor.getString(1);
//            String caSi = cursor.getString(3);
//            int yeuThich = cursor.getInt(5);
//            BaiHat music = new BaiHat();
//            music.setMa(mabh);
//            music.setTen(ten);
//            music.setCaSi(caSi);
//            music.setLike(yeuThich==1);
//            dsBaiHatGoc.add(music);
            dsBaiHatGoc.add(ten);
            dsBaiHatHienThi.add(ten);
        }
        cursor.close();
        adapterBaiHatGoc.notifyDataSetChanged();
    }

    private void addEvents() {
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                xuLyTimKiem(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void xuLyTimKiem(CharSequence charSequence) {
        charSequence = charSequence.toString().toLowerCase();
        ArrayList<String>searchList = new ArrayList<String>();
        dsBaiHatHienThi.clear();
        if(charSequence!=null&&charSequence.toString().length()>0)
            {
                for(String s:dsBaiHatGoc)
                {
                    if(s.toLowerCase().startsWith(charSequence.toString()))
                    {
                        searchList.add(s);
                    }
                }
                for(String s : searchList)
                    dsBaiHatHienThi.add(s);
            }
            else
            {
                for(String s : dsBaiHatGoc)
                dsBaiHatHienThi.add(s);
            }
            adapterBaiHatGoc.notifyDataSetChanged();
    }

    private void xuLySaoChepCSDLTuAssetsVaoHeThongMobile() {
        //private app
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())    {
            try
            {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            }
            catch (Exception e)
            {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void CopyDataBaseFromAsset() {
        try
        {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);

            // Path to the just created empty db
            String outFileName = layDuongDanLuuTru();

            // if the path doesn't exist first, create it
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
            {
                f.mkdir();
            }
            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch (Exception ex)
        {
            Log.e("Loi sao chep",ex.toString());
        }
    }

    private String layDuongDanLuuTru()
    {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }
}
