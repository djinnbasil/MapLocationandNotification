package com.tetra.biometrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.android.volley.RequestQueue;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class AfterBio extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button getplacesofinterest;

    private static final String PRIMARY_CHANNEL_ID = "primary_notifaction_channel";
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID=0;

    String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=new+york+city+point+of+interest&language=en&key=AIzaSyDYWB9hpF_-53-IYlfSVHsM1rXAkVa35aY";

    private Button locationstart;
    private BottomSheetBehavior bottomSheet;
    RequestQueue queue;
    public ArrayList<InterestPlace> places_of_interest=new ArrayList<InterestPlace>();;
    public String place_name = "Barrie";


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        locationstart = findViewById(R.id.EnterLocation);
        if (resultCode == RESULT_OK) {
            final Place place = Autocomplete.getPlaceFromIntent(data);
            if (requestCode == 2) {


                LatLng latlng = place.getLatLng();
                place_name = place.getName();

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15), 2000, null);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_bio);
        locationstart = findViewById(R.id.EnterLocation);
        getplacesofinterest = findViewById(R.id.getplacesofInterest);
        Places.initialize(getApplicationContext(), "AIzaSyDYWB9hpF_-53-IYlfSVHsM1rXAkVa35aY");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        final RequestQueue queue = Volley.newRequestQueue(this);


        View nestedScrollView = findViewById(R.id.bottom_sheet);
        bottomSheet = BottomSheetBehavior.from(nestedScrollView);
        final BottomSheetBehavior behavior = bottomSheet;
        bottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
        createNotificationChannel();
       // setNotificationButtonState(true,true,true);

        getplacesofinterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("Hey Placesss",place_name);
                url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+place_name+"+point+of+interest&language=en&key=AIzaSyDYWB9hpF_-53-IYlfSVHsM1rXAkVa35aY";
                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                   // Log.d("Api Result1",jsonObject.toString());
                                    JSONArray array1 = jsonObject.getJSONArray("results");
                                   // Log.d("Api Result2",array1.toString());
                                    for(int i =0; i< array1.length();i++){
                                        InterestPlace temppoi = new InterestPlace();
                                        JSONObject jsonObject1 = new JSONObject(array1.get(i).toString());
                                        temppoi.address = jsonObject1.getString("formatted_address");
                                        temppoi.icon = jsonObject1.getString("icon");
                                        temppoi.name =  jsonObject1.getString("name");
                                        temppoi.rating = jsonObject1.getString("rating");
                                        temppoi.user_rating = jsonObject1.getString("user_ratings_total");


                                        places_of_interest.add(temppoi);




                                    }
                                    Log.d("Placessss_Length",places_of_interest.toString());
                                    JSONObject jsonObject1 = new JSONObject(array1.get(0).toString());
                                    Log.d("Api Result3",jsonObject1.toString());
                                    String placeicon = jsonObject1.getString("formatted_address");
                                    Log.d("Api Result4",placeicon);

                                    float high_rate = 0;
                                    String temp_place = "";
                                    for(int j = 0 ;j<places_of_interest.size();j++) {
                                        InterestPlace temppoi = new InterestPlace();
                                        temppoi = places_of_interest.get(j);
                                        temp_place = temppoi.name;
                                        Log.d("Rating",temppoi.rating);
                                        if (temppoi.rating != "") {
                                            float temp_rating = Float.parseFloat(temppoi.rating);


                                            if (temp_rating > high_rate) {

                                                high_rate = temp_rating;
                                                temp_place = temppoi.name;
                                            }
                                        }
                                    }

                                    send_notification(place_name,temp_place,high_rate);


                                    //JSONObject jsonObject2 = new JSONObject(array2.get(0).toString());
                                    // JSONObject obj1 = jsonObject2.getJSONObject("distance");
                                    //  JSONObject obj2 = jsonObject2.getJSONObject("duration");




                                } catch (JSONException err) {
                                    Log.d("Error", err.toString());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {



                    }
                });
                queue.add(stringRequest2);

              //Intent passlist = new Intent(AfterBio.this , RecActivity.class);
               //Bundle bundle = new Bundle();
              // bundle.putSerializable("Donations",places_of_interest);
               //bundle.putParcelableArrayList("Donations",places_of_interest);
               //passlist.putExtras(bundle);




               // passlist.putExtra("mylist", places_of_interest);
              //startActivity(passlist);

               // send_notification();
              //  setNotificationButtonState(false,true,true);

            }
        });


        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.e("onStateChanged", "onStateChanged:" + newState);
                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING: {
                        break;
                    }
                    case BottomSheetBehavior.STATE_SETTLING: {
                        break;
                    }
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        break;
                    }
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        break;
                    }
                    case BottomSheetBehavior.STATE_HIDDEN: {
                        break;
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        behavior.setPeekHeight(500);



        locationstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
// Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(AfterBio.this);
                startActivityForResult(intent, 2);


            }


        });

    }

    public void send_notification(String place_name,String temp_place,float high_rate){


        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.setContentTitle("Best Place in "+place_name+" is "+temp_place + " Rating : " +Float.toString(high_rate));
        mNotificationManager.notify(NOTIFICATION_ID,notifyBuilder.build());


    }

    public void createNotificationChannel(){
        mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,"Test Notification Channel",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Test Notiication Channel");
            mNotificationManager.createNotificationChannel(notificationChannel);

        }
    }

    public void updateNotification(){
        Bitmap androidImage = BitmapFactory.decodeResource(getResources(),R.drawable.fingerprint_img);
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(androidImage).setBigContentTitle("Notification Updated!!"));
        mNotificationManager.notify(NOTIFICATION_ID,notifyBuilder.build());


    }
    public void cancelNotification(){
        mNotificationManager.cancel(NOTIFICATION_ID);
    }

    private NotificationCompat.Builder getNotificationBuilder(){

        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,NOTIFICATION_ID,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this,PRIMARY_CHANNEL_ID).setContentIntent(notificationPendingIntent).setAutoCancel(true).setSmallIcon(R.drawable.ic_stat_name);
        return notifyBuilder;

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);




        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                //Execute Directions API request


                CameraUpdate center = CameraUpdateFactory.newLatLng(latLng);
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);

                mMap.moveCamera(center);


            }
        });


    }
}
