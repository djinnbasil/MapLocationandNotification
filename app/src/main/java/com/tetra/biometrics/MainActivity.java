package com.tetra.biometrics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

//import android.hardware.biometrics.BiometricPrompt;
import androidx.biometric.BiometricPrompt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Executor newExecutor = Executors.newSingleThreadExecutor();
        FragmentActivity activity = this;

        final BiometricPrompt myBiometricPrompt =
                new BiometricPrompt(activity,newExecutor,new BiometricPrompt.AuthenticationCallback(){

            //onAuthenticationError is called when a fatal error occurs

                    @Override
                    public void onAuthenticationError(int errorcode, @NonNull CharSequence errString){
                        super.onAuthenticationError(errorcode,errString);
                        if(errorcode == BiometricPrompt.ERROR_NEGATIVE_BUTTON){}
                        else{
                            Log.d("Hey","An unreccoverable error has occured");
                        }

                    }

                    //onauthenticationSucceeded

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result){
                        super.onAuthenticationSucceeded(result);
                        Log.d("Hey","Fingerprint recognized");
                        onSuccess();


                    }

                    @Override
                    public  void onAuthenticationFailed(){
                        super.onAuthenticationFailed();
                        Log.d("Hey","Fingerprint not recognized");

                    }

                    //create our biometric prompt instance
                    final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("PlacesNow!!")
                            .setSubtitle("Google Map Places of Interest").setDescription("Keep your places close!!.Press Finger in sensor to authenticate!!").setNegativeButtonText("Cancel").build();

                    //assign an on click listener to the apps authenticate button


        });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("PlacesNow!!")
                .setSubtitle("Google Map Places of Interest").setDescription("Keep your places close!!.Press Finger in sensor to authenticate!!").setNegativeButtonText("Cancel").build();


        findViewById(R.id.launchAuthentication).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        myBiometricPrompt.authenticate(promptInfo);

    }
});

    }

    public void onSuccess(){
        this.startActivity( new Intent(this,AfterBio.class));
    }
}
