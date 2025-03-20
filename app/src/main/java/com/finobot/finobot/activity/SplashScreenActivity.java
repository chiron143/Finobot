package com.finobot.finobot.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.CrashExceptionHandler;
import com.purplepath.purplepath.fragments.GuideViewFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by dinesh on 10/05/16.
 */
public class SplashScreenActivity extends AppCompatActivity {
    private int SPLASH_TIME_OUT = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Thread.setDefaultUncaughtExceptionHandler(new CrashExceptionHandler(this,SplashScreenActivity.class));
        getAppKeyHash();

    }
    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;

                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                System.out.println("HASH  " + something);
             //  showSignedHashKey(something);

            }
        } catch (PackageManager.NameNotFoundException e1) {
            // TODO Auto-generated catch block
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {

            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ImageView splash_image = (ImageView) findViewById(R.id.splash_image);
        splash_image.setVisibility(View.VISIBLE);
        try {
            new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

                @Override
                public void run() {
//           Intent mainActivity =new Intent( SplashScreenActivity.this,LoginandSignUpActivity.class);
//           //Intent mainActivity =new Intent( SplashScreenActivity.this,HomePageActivity.class);
//           mainActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//           startActivity(mainActivity);
//           finish();
                    try {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        GuideViewFragment fragment = new GuideViewFragment();
                        fragmentTransaction.add(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        if(!isFinishing())
                            fragmentTransaction.commitAllowingStateLoss();
                        fragmentTransaction.addToBackStack(null);
                    }catch (Exception e){
                        e.printStackTrace();
                    }



                }
            }, SPLASH_TIME_OUT);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
