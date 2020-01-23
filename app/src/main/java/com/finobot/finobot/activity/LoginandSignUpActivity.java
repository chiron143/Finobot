package com.finobot.finobot.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.AppManagement.Survey.SurveyFragmentView;
import com.purplepath.purplepath.AppManagement.Survey.model.Surveymodel;
import com.purplepath.purplepath.apputiles.CrashExceptionHandler;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.chatprompt.PromptChatFragment1;
import com.purplepath.purplepath.fragments.EmailLoginFragment;
import com.purplepath.purplepath.fragments.PersonalDetailsFragment;
import com.purplepath.purplepath.fragments.SignUpFragment;
import com.purplepath.purplepath.model.CardResponse;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginandSignUpActivity extends AppCompatActivity implements OnActivityBackPressedListener {
    private static final int PERIOD = 2000;
    public static int checkoff = 0;
    Toolbar mToolbar;
    TextView mTitle;
    CoordinatorLayout mCoordinatorLayout;
    LayoutInflater inflater;
    View dialogView;
    AlertDialog alertDialog;
    Context mContext;
    private long lastPressedTime;
    private int SPLASH_TIME_OUT = 5000;

    ImageView splash_image;

    Boolean isLogOut=false;

    AppBarLayout appbarTitleBarLay;

    String taxfiling_flags;
    private CardResponse cardPermission;
    public static String plan, file;

    private FragmentManager fragmentManager;



    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

         if (intent.hasExtra("flag")) {
             taxfiling_flags = intent.getStringExtra("flag");
            Log.d("flagss", "flagss"+taxfiling_flags);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread.setDefaultUncaughtExceptionHandler(new CrashExceptionHandler(this, LoginandSignUpActivity.class));
        //callSurveyCheck();
//        setContentView(R.layout.splash_screen);
        cardViewPermission();
//        getAppKeyHash();
        if(getIntent().hasExtra("Logout"))
            isLogOut=getIntent().getBooleanExtra("Logout",false);

        Intent intent = getIntent();
        printHashKey(this);
        if (intent.hasExtra("flag")) {
            taxfiling_flags = intent.getStringExtra("flag");
            Log.d("flagss", "flagss"+taxfiling_flags);

        }
        Intent s = getIntent();
        if (getIntent()!=null) {
            String dataTransmited = intent.getStringExtra("success");
//            Log.d("kjdsfghjf", dataTransmited);
        }else {

        }

    }

    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("viswa_key", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("viswa_keys", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("viswa_keyvxdmng", "printHashKey()", e);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mContext = this;
//        splash_image = (ImageView) findViewById(R.id.splash_image);
//        splash_image.setVisibility(View.VISIBLE);
//        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.loginCoordinatorLayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        appbarTitleBarLay=(AppBarLayout)findViewById(R.id.appbarId);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
        mTitle = mToolbar.findViewById(R.id.toolbar_title);
        setActionBarTitle(getString(R.string.app_name));
//


        UtileKit.cretePrefAtHome(getBaseContext());
        UtileKit.cretefinobotPrefForFirsttime(getBaseContext());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        GuideViewFragment fragment =new GuideViewFragment();
        EmailLoginFragment fragment =(EmailLoginFragment.newInstance(taxfiling_flags));
        fragmentTransaction.add(R.id.fragment_container, fragment);
        if (!isFinishing())
            fragmentTransaction.commitAllowingStateLoss();

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {

            @Override
            public void onBackStackChanged() {
//                Log.i("LoginandSignUpActivity","LoginandSignUpActivity getBackStackEntryCount" +getSupportFragmentManager().getBackStackEntryCount());
                Fragment instanceFragment =
                        getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);

                }
                if (instanceFragment instanceof PersonalDetailsFragment) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);

                }
                if(instanceFragment instanceof PromptChatFragment1){
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);

                }
            }
        });
//        if(!isLogOut) {
//
//            try {
//                new Handler().postDelayed(new Runnable() {
//
//			/*
//             * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
//                    @Override
//                    public void run() {
//
//                        try {
//
////                            splash_image.setVisibility(View.GONE);
//                            FragmentManager fragmentManager = getSupportFragmentManager();
//                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                            GuideViewFragment fragment = new GuideViewFragment();
//                            fragmentTransaction.add(R.id.fragment_container, fragment);
//                            if (!isFinishing())
//                                fragmentTransaction.commitAllowingStateLoss();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }, SPLASH_TIME_OUT);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }else {

//            try {
//
////                splash_image.setVisibility(View.GONE);
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                EmailLoginFragment fragment = new EmailLoginFragment();
//                fragmentTransaction.add(R.id.fragment_container, fragment);
//                if (!isFinishing())
//                    fragmentTransaction.commitAllowingStateLoss();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
//            if (event.getDownTime() - lastPressedTime < PERIOD) {
//                finish();
//                return true;
//            } else {
//                Toast.makeText(getApplicationContext(), "Press again to exit.",
//                        Toast.LENGTH_SHORT).show();
//                lastPressedTime = event.getEventTime();
//                Snackbar.make(mCoordinatorLayout,"press one moret time to Exit",Snackbar.LENGTH_SHORT);
//                return false;
//            }
            intitializeAlertDialog();
            try {
                alertDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                hideKeyboard(mContext);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cardViewPermission() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls callObj= ServiceGenerator.createService(WebServiceCalls.class);
        Call<CardResponse> call =callObj.getCardPermission("6");
        call.enqueue(new Callback<CardResponse>() {
            @Override
            public void onResponse(Call<CardResponse> call, Response<CardResponse> response) {
                UtileKit.dismisssSpinnerDialog();

                cardPermission=response.body();
                Log.d("tax_plan_display", cardPermission.getData().getResult().get(0).getTaxPlanDisplay());
                Log.d("tax_file_display", cardPermission.getData().getResult().get(0).getTaxFileDisplay());
                plan = cardPermission.getData().getResult().get(0).getTaxPlanDisplay();
                file = cardPermission.getData().getResult().get(0).getTaxFileDisplay();
                Log.d("Planing&&Filing_Login", plan +"::"+file);

            }

            @Override
            public void onFailure(Call<CardResponse> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(LoginandSignUpActivity.this,t);
            }
        });
    }

    private void intitializeAlertDialog() {
        inflater = LayoutInflater.from(this);
        dialogView = inflater.inflate(R.layout.yes_or_no_system_back, null);
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setView(dialogView);
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();
                ActivityCompat.finishAffinity(LoginandSignUpActivity.this);

            }
        });
        dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityBackPressed() {
        hideKeyboard(mContext);
        onBackPressed();
    }

    @Override
    public void setActionBarTitle(String mTitleName) {

        if (mToolbar != null) {
            mToolbar.setTitle("");
            mToolbar.getMenu().clear();
        }
        if (mTitle != null)
            mTitle.setText(mTitleName);

    }

    @Override
    public void setActionBarExpTitle(String mTitleName) {
        if (mToolbar != null) {
            mToolbar.setTitle("");
        }
        if (mTitle != null)
            mTitle.setText(mTitleName);
    }

    @Override
    public void closeDropDownTab() {

    }

    private void callSurveyCheck() {

        Log.i("go", "now");
        WebServiceCalls webServiceObj;

        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<Surveymodel> call = webServiceObj.getSurveyService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<Surveymodel>() {
            @Override
            public void onResponse(Call<Surveymodel> call, Response<Surveymodel> response) {

                Surveymodel quizmodels = response.body();

                try {
                    if (quizmodels.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {


                        checkoff = 1;
                        SharedPreferences prefsonoff = getSharedPreferences("my_prefscheck", MODE_PRIVATE);
                        SharedPreferences.Editor editonoff = prefsonoff.edit();
                        editonoff.putString("MIDonoff", "checkit");
                        editonoff.commit();

                        Log.i("go2", "now");
                    } else {

                        SurveyFragmentView.silakidum = 0;
                        checkoff = 0;
                        SharedPreferences prefsonoff = getSharedPreferences("my_prefscheck", MODE_PRIVATE);
                        SharedPreferences.Editor editonoff = prefsonoff.edit();
                        editonoff.putString("MIDonoff", "dontcheck");
                        editonoff.commit();

                        Log.i("go3", "now");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Surveymodel> call, Throwable t) {


                UtileKit.alertRetrofitExceptionDialog(mContext, t);

            }
        });
    }


}