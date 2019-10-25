package com.tetra.biometrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RecActivity extends AppCompatActivity {

    private TextView infotext;
    private TextView infotext1;
    private TextView infotext2;
    private ImageView imageView;
    private ImageView imageView1;
    private ImageView imageView2;
    private RecyclerView.LayoutManager mlayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec);


        Bundle passlist = getIntent().getExtras();
       // ArrayList<InterestPlace> dons = (ArrayList<InterestPlace>) passlist.getSerializable("Donations");
        ArrayList<InterestPlace> dons = passlist.getParcelableArrayList("Donations");

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rview1);

        mlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter=new ExampleAdapter(dons);
        recyclerView.setAdapter(mAdapter);





        //ArrayList<InterestPlace> myList = (ArrayList<InterestPlace>) getIntent().getSerializableExtra("mylist");
        //imageView = findViewById(R.id.imageView);
        //imageView1=findViewById(R.id.imageView1);
        //imageView2= findViewById(R.id.imageView2);
        //infotext = findViewById(R.id.info_text);
        //infotext1 = findViewById(R.id.info_text1);
        //infotext2=findViewById(R.id.info_text2);
        Log.d("Hey", (dons.toString()));
       // infotext.setText(dons.get(0).name);
        //infotext1.setText(dons.get(1).name);
        //infotext2.setText(dons.get(2).name);
        //String imageurl = dons.get(0).icon;
        //String imageurl1 = dons.get(1).icon;
        //String imageurl2 = dons.get(2).icon;

        //imageload(imageurl,imageView);
        //imageload(imageurl1,imageView1);
        //imageload(imageurl2,imageView2);

    }

    void imageload(String imageurl11,ImageView imageViewtemp){
        URL url = null;
        try {
            url = new URL(imageurl11);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imageViewtemp.setImageBitmap(bmp);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
