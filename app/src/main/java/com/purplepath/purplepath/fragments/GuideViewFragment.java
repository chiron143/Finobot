package com.purplepath.purplepath.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.finobot.finobot.activity.LoginandSignUpActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.model.LoginModel;
import com.purplepath.purplepath.model.versionUpdatemodel.VersionModel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;
import java.util.Arrays;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.apputiles.UtileKit.getPersistedPurplePathPref;




/**
 * A simple subclass.
 */
public class GuideViewFragment extends BaseFragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename parameter arguments, choose names that match


    private static final int RC_SIGN_IN = 9001;
    private Button mSignUpBtn, mLoginBtn;
    private AutoScrollViewPager viewPager;
    private PageIndicator mIndicator = null;
//    private GoogleApiClient mGoogleApiClient;
//    private GoogleSignInOptions gso;
    private Context mContext;
    private ProgressDialog mProgressDialog;

    private Button signInButto1;
    int resVector=0;
    private ArrayList<String> menuList;
    AlertDialog alertDialog;
    LayoutInflater inflater;
    View dialogView;

    VersionModel versionModel;

    public GuideViewFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mContext = getContext();
        menuList=new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.menuitem)));
        UtileKit.cretePrefAtHome(getContext());
//
//        try {
//            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                    .requestProfile()
//                    .requestEmail()
//                    .requestId()
//                    .build();
//            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
//                    .enableAutoManage(getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
//                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                    .build();
//        } catch (IllegalStateException e) {
//
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getContext();
        View guideView = inflater.inflate(R.layout.fragment_guide_view, container, false);



        return guideView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dailyupdateRisk();
        viewPager.setAdapter(new ScreenSlidePagerAdapter(getFragmentManager()));
        viewPager.setInterval(2000);
        viewPager.setOffscreenPageLimit(1);
        viewPager.startAutoScroll();
        mIndicator.setViewPager(viewPager);
    }

    private void dailyupdateRisk() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        String mVersionName = null;
        try {
            mVersionName = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Call<VersionModel> call = webServiceObj.callVersionService(mVersionName);
        call.enqueue(new Callback<VersionModel>() {
            @Override
            public void onResponse(Call<VersionModel> call, Response<VersionModel> response) {
                UtileKit.dismisssSpinnerDialog();
                VersionModel  versionModel = response.body();

               checkYourverson(versionModel);


            }

            @Override
            public void onFailure(Call<VersionModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }
    private void checkYourverson(VersionModel versionModel) {
        if (versionModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
            if (versionModel.getData().getIs_version_update().equalsIgnoreCase(UtileKit.is_version_update)) {

              passalertButtonDialogYesNo("New update available, Install the lastest version for new features", mContext);

            }
        }
    }

    private void passalertButtonDialogYesNo(String message, final Context context) {
        inflater = LayoutInflater.from(context);
        dialogView = inflater.inflate(R.layout.alert_message_layout, null);
        alertDialog = new android.support.v7.app.AlertDialog.Builder(context).create();
        alertDialog.setView(dialogView);
       // dialogView.findViewById(R.id.no).setVisibility(View.GONE);
        TextView stringErrorMessage = dialogView.findViewById(R.id.textViewDilog);
        stringErrorMessage.setText(message);
        alertDialog.setCancelable(false);
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity  object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                }
//                clearPreferences();
//                LogotTheApp();
                alertDialog.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityCompat.finishAffinity(getActivity());
                } else
                    getActivity().finish();
            }
        });
        /*dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });*/
        alertDialog.show();
    }
    @Override
    public void onViewCreated(View guideView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(guideView, savedInstanceState);

        mSignUpBtn = guideView.findViewById(R.id.sign_in_buttonId);
        mLoginBtn = guideView.findViewById(R.id.loginBtnId);

        mSignUpBtn.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);

        signInButto1 = guideView.findViewById(R.id.sign_in_button);

        UtileKit.setSvgButtonDrawableLeft(signInButto1, mContext,R.drawable.ic_g_icon);

        try{
            String mLoginBtnChanged = getPersistedPurplePathPref("firsttimeLogin");
            Log.i("GuideViewFragment","mLoginBtnChanged " + mLoginBtnChanged);
            if(mLoginBtnChanged!= null){
                if(mLoginBtnChanged.equalsIgnoreCase("isSuccess")){
                    mLoginBtn.setText(R.string.track_your_finaces);
                }else{
                    mLoginBtn.setText(R.string.plan_your_finaces);
                }

            }else{
                mLoginBtn.setText(R.string.plan_your_finaces);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        viewPager = guideView.findViewById(R.id.home_viewpager);
        mIndicator = guideView.findViewById(R.id.indicator);
//        SignInButton signInButton = (SignInButton) guideView.findViewById(R.id.sign_in_g);
//        signInButton.setSize(SignInButton.SIZE_STANDARD);
//        setGooglePlusButtonText(signInButton, "Sign Up");
//        signInButton.setScopes(gso.getScopeArray());
//        signInButto1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signIn();
//            }
//        });
    }

//    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
//        // Find the TextView that is inside of the SignInButton and set its text
//        for (int i = 0; i < signInButton.getChildCount(); i++) {
//            View v = signInButton.getChildAt(i);
//
//            if (v instanceof TextView) {
//                TextView tv = (TextView) v;
//                tv.setText(buttonText);
//                return;
//            }
//        }
//    }



    @Override
    public void onClick(View v) {

        if (v == mSignUpBtn) {

        } else if (v == mLoginBtn) {
            Intent mainActivity =new Intent( mContext,LoginandSignUpActivity.class);
//            mainActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(mainActivity);
//             addFragmenttoStack(new EmailLoginFragment());
        }
    }

   /* private void callSignUpSocialMediaService(String mMediaId, String mMailID, String mPassword,
                                              String mFullName, String mFlag) {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        String mobilenumberischeck = getPersistedPurplePathPref("mobilenumbergot");
        String countrycode = getPersistedPurplePathPref("countrycode");
        Call<SignUpModel> call = webServiceObj.callRegisterSocialMediaService(mMediaId, mFullName, mMailID,
                mPassword, mFlag,mobilenumberischeck,countrycode);
        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                //Log.e("CallBack", " response is " + call.toString());
                UtileKit.dismisssSpinnerDialog();
                SignUpModel signupModel = response.body();
                if (signupModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    //taking paid type
                    UtileKit.persistingPurplePathPref("paid_type",signupModel.getData().getUser_details().getUser_paid_type());

                    UtileKit.createSharedPreference(getActivity(), signupModel.getData().getUser_id());
//                   UtileKit.C signupModel.getData().getUser_id();
                    Bundle args = new Bundle();
                    args.putString("userid", signupModel.getData().getUser_id());
                    args.putBoolean("IsSignUp", true);
                    InstructionScreenOne fragment = new InstructionScreenOne();
                    fragment.setArguments(args);
                    addFragmenttoStack(fragment);
                }
            }

            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
    }
/*

*//*

    */
/**
     * enable menu in list
     *//*

    private void setRestricted_menus(ArrayList<Restricted_menus> restricted_menus) {
        try {
            for (String menu : menuList){
                persistingPurplePathPref(menu, true);
            }
            for (String menu : menuList) {
                for(Restricted_menus obj : restricted_menus){
                    if (obj.getMenu_name().equalsIgnoreCase(menu)) {
                        persistingPurplePathPref(menu, false);
                    }
                }


            }
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        catch (Exception e1){
            e1.printStackTrace();
        }

    }
*/

    private void callLoginServiceSocialMedia(final String social_media_id, final String mMailID, final String name) {
        UtileKit.showSpinnerDialog(mContext, false);
        final String mPassword = "12345";
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        Call<LoginModel> call = webServiceObj.CallLoginSocialService(social_media_id, mMailID, mPassword, "social", "1");
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call1, Response<LoginModel> response) {
                //Log.e("CallBack", " response is " + call1.toString());
                UtileKit.dismisssSpinnerDialog();
                LoginModel loginModel = response.body();
                if (loginModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    //taking paid type
                    UtileKit.persistingPurplePathPref("paid_type",loginModel.getData().getUser_details().getUser_paid_type());

                    UtileKit.createSharedPreference(mContext, loginModel.getData().getUser_details().getUser_id());
                    Intent homePage = new Intent(getActivity(), HomePageActivity.class);
                    startActivity(homePage);
//                    getActivity().finish();
                } else {
//                    UtileKit.alertDialog(
//                            loginModel.getData().getMessage(),
//                            mContext);
                    //callSignUpSocialMediaService(social_media_id, mMailID, mPassword, name, "1");
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
    }

//    private void signIn() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }

    /*private void handleSignInResult(GoogleSignInResult result) {
        Log.d("TAG", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.

            GoogleSignInAccount acct = result.getSignInAccount();
//            //Log.e("Google Sucess....", "NAme" + acct.getDisplayName());
//            //Log.e("Google Sucess....", "NAme" + acct.getEmail() + acct.getPhotoUrl());
            String url = "";
            if (acct.getPhotoUrl() != null)
                url = acct.getPhotoUrl().toString();
            getUserSocialMediaAccoutnt(acct.getDisplayName(), url, acct.getEmail(), UtileKit.encryptPwd("12345"), acct.getId());

//            loginForgetPwdView.setText("Singned in as" + acct.getDisplayName());
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }*/

    private void getUserSocialMediaAccoutnt(String displayName, String url, String email, String x, String id) {

        callLoginServiceSocialMedia(id, email, displayName);
    }

    // [START signOut]
//    private void signOut() {
//        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
//                new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(Status status) {
//                        // [START_EXCLUDE]
//                        updateUI(false);
//                        // [END_EXCLUDE]
//                    }
//                });
//    }
    // [END handleSignInResult]

    // [START signIn]

    // [END signIn]

    // [START revokeAccess]
//    private void revokeAccess() {
//        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
//                new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(Status status) {
//                        // [START_EXCLUDE]
//                        updateUI(false);
//                        // [END_EXCLUDE]
//                    }
//                });
//    }
    // [END signOut]

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("TAG", "onConnectionFailed:" + connectionResult);
    }
    // [END revokeAccess]

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
//                findViewById(R.id.sign_in_button).setVisibility(View.GONE);
//                findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
//                findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
//                mStatusTextView.setText(R.string.signed_out);
//                findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

//        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
//        if (opr.isDone()) {
//            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
//            // and the GoogleSignInResult will be available instantly.
//            Log.d("TAG", "Got cached sign-in");
//            revokeAccess();
//            GoogleSignInResult result = opr.get();
//            handleSignInResult(result);
//        } else {
//            // If the user has not previously signed in on this device or the sign-in has expired,
//            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
//            // single sign-on will occur in this branch.
//            showProgressDialog();
////            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
////                @Override
////                public void onResult(GoogleSignInResult googleSignInResult) {
////                    hideProgressDialog();
////                    handleSignInResult(googleSignInResult);
////                }
////            });
//        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return (ScreenSlidePageFragment.newInstance(position));
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
