package com.purplepath.purplepath.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.AppManagement.Survey.SurveyFragmentView;
import com.purplepath.purplepath.apputiles.AppConstants;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.model.LoginModel;
import com.purplepath.purplepath.model.Restricted_menus;
import com.purplepath.purplepath.model.SignUpModel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.apputiles.UtileKit.getTextFromObjects;
import static com.purplepath.purplepath.apputiles.UtileKit.persistingPurplePathPref;


/**
 * Created by dinesh on 10/3/2016.
 */
public class EmailLoginFragment extends BaseFragment implements View.OnClickListener, AppConstants, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 9001;
    public static String surch = "";
    int keyDel = 0;
    String signUpEdtPhoneNumStr;
    CallbackManager callbackManager;
    private EditText loginEdtEmailView, loginEdtPwdView;
    private TextView loginForgetPwdView;
    private Button loginMailButtonView, sign_in_googleplus, sign_in_facebook;
    // private LinearLayout mRelativeLayout;
    private Context mContext;
    private TextView mLoginTxtView, loginCreateAnAccount;
    private RelativeLayout mLoginView;
    private ProgressDialog mProgressDialog;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    private LoginButton login_button;
    //    private HashMap<String,Boolean> lockFeatureArray=new HashMap<>();
    private ArrayList<String> menuList;
    public static String oAuth_key;

    private String taxfiling_flags;


    public static EmailLoginFragment newInstance(String taxfiling_flags) {
        EmailLoginFragment emaillogin = new EmailLoginFragment();
        Bundle args = new Bundle();

        if (taxfiling_flags != null) {
            args.putSerializable("taxfiling_flags", taxfiling_flags);
        }
        emaillogin.setArguments(args);
        return emaillogin;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
            if (opr.isDone()) {
                // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
                // and the GoogleSignInResult will be available instantly.
                Log.d("TAG", "Got cached sign-in");
//            GoogleSignInResult result = opr.get();
//            handleSignInResult(result);
            } else {
                // If the user has not previously signed in on this device or the sign-in has expired,
                // this asynchronous branch will attempt to sign in the user silently.  Cross-device
                // single sign-on will occur in this branch.
//            showProgressDialog();
                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(GoogleSignInResult googleSignInResult) {
//                    hideProgressDialog();
//                    handleSignInResult(googleSignInResult);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        if (getArguments().containsKey("taxfiling_flags")) {
            taxfiling_flags = getArguments().getString("taxfiling_flags");
        }

        MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        menuList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.menuitem)));
        UtileKit.cretePrefAtHome(getContext());
//        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
//        UtileKit.cretefinobotPrefForFirsttime(getContext());
        UtileKit.createKeyHash(getActivity());
        try {
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .enableAutoManage(getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
//            FacebookSdk.sdkInitialize(mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }

//
//        try {
//            PackageInfo info = mContext.getPackageManager().getPackageInfo(
//                    "com.finobot.finobot",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View emailLoginView;
        emailLoginView = inflater.inflate(R.layout.fragment_email_login, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Login");

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        return initCurrentScreenView(emailLoginView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MyApplication.mFirebaseAnalytics.setCurrentScreen(getActivity(), getString(R.string.analtics_login), null /* class override */);
        try {
            callbackManager = CallbackManager.Factory.create();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View initCurrentScreenView(View emailLoginView) {
        // TODO Auto-generated method stub
        //  mRelativeLayout = (LinearLayout) emailLoginView.findViewById(R.id.emailloginrelativelayout);
        loginEdtEmailView = emailLoginView.findViewById(R.id.loginEmailID);
        UtileKit.setSvgEdittextDrawableLeft(loginEdtEmailView, mContext, R.drawable.ic_email_icon);

        String sugestionEmail = UtileKit.getPersistedPurplePathPref("UserEmailPref", null);
        if (sugestionEmail != null) {
            loginEdtEmailView.setText(sugestionEmail);
        }
        loginEdtPwdView = emailLoginView.findViewById(R.id.loginPwdID);
        // UtileKit.setSvgEdittextDrawableLeft(loginEdtPwdView,mContext,R.drawable.ic_password_icon);

        Log.d("hi", "password" + loginEdtPwdView);

        loginEdtEmailView.setSelection(loginEdtEmailView.getText().length());

        loginEdtPwdView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                    if (i == KeyEvent.KEYCODE_ENTER) {
                        //do what you want
                        String _loginEmail = getTextFromObjects(loginEdtEmailView).trim();
                        String _loginPwd = getTextFromObjects(loginEdtPwdView).trim();
                        validateUserCredentials(_loginEmail, _loginPwd);
                    }
                return false;
            }
        });
//       signUpEdtEmailView = (EditText) emailLoginView
//             .findViewById(R.id.signUpEmailID);
//       signUpEdtPhoneNumView = (EditText) emailLoginView
//             .findViewById(R.id.signUpPhoneNumID);
//            phoneNumberViewSep = emailLoginView.findViewById(R.id.viewSep2);
//            try {
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    signUpEdtPhoneNumView.addTextChangedListener(new PhoneNumberFormattingTextWatcher("US"));
//                }
//
//            }catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//       signUpEdtPwdView = (EditText) emailLoginView
//             .findViewById(R.id.signUpPwdID);

        loginCreateAnAccount = emailLoginView.findViewById(R.id.loginCreateAnAccount);
        loginCreateAnAccount.setOnClickListener(this);
        loginForgetPwdView = emailLoginView
                .findViewById(R.id.loginForgetPwdID);
        loginForgetPwdView.setOnClickListener(this);
        sign_in_googleplus = emailLoginView.findViewById(R.id.sign_in_googleplus);
        UtileKit.setSvgButtonDrawableLeft(sign_in_googleplus, mContext, R.drawable.ic_googleplusicon);
        sign_in_googleplus.setOnClickListener(this);
        sign_in_facebook = emailLoginView.findViewById(R.id.sign_in_facebook);

        UtileKit.setSvgButtonDrawableLeft(sign_in_facebook, mContext, R.drawable.ic_facebookicon);

        sign_in_facebook.setOnClickListener(this);
        // Init Buttons
        loginMailButtonView = emailLoginView
                .findViewById(R.id.loginMailButtonId);
        loginMailButtonView.setOnClickListener(this);
//       signUpButtonView = (Button) emailLoginView
//             .findViewById(R.id.signUpButtonId);
//       signUpButtonView.setOnClickListener(this);

//       mSignUpTxtView=(TextView)emailLoginView.findViewById(R.id.SignUpTxtViewId);

//            mSignUpView=(RelativeLayout)emailLoginView.findViewById(R.id.signUpRelativeViewId);


//            SignInButton signInButton = (SignInButton) emailLoginView.findViewById(R.id.sign_in_button);
//            signInButton.setSize(SignInButton.SIZE_STANDARD);
//
//            signInButton.setScopes(gso.getScopeArray());
//            signInButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    signIn();
//                }
//            });


//        login_button = (LoginButton) emailLoginView.findViewById(R.id.login_button);
//
////        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
//        login_button.setReadPermissions(Arrays.asList(
//                "public_profile", "email", "user_birthday", "user_friends"));
        // If using in a fragment
//        login_button.setFragment(this);
        // Other app specific specialization

        // Callback registration
//        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.i("EmailLoginFragment","EmailLoginFragment on create view"+ loginResult.toString());
//
//                GraphRequest request = GraphRequest.newMeRequest(
//                        loginResult.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(JSONObject object, GraphResponse response) {
//                                Log.v("LoginActivity", response.toString());
//
//                                // Application code
//                                try {
//                                    String email = object.getString("email");
//                                    String facebook_name = object.getString("name");
//                                    String facebook_id = object.getString("id");
//                                    Log.i("EmailLoginFragment","EmailLoginFragment on create view email "+ email + " object "+ object.toString());
//                                    callLoginServiceSocialMedia(facebook_id, email, facebook_name);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id,name,email,gender,birthday");
//                request.setParameters(parameters);
//                request.executeAsync();
//            }
//
//            @Override
//            public void onCancel() {
//                Log.i("EmailLoginFragment","EmailLoginFragment on cancel view");
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                Log.i("EmailLoginFragment","EmailLoginFragment on Error view");
//            }
//        });
        return emailLoginView;
    }


    public void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.

    }


    private void signIn() {
        try {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.v("LoginActivity", response.toString());

                                        // Application code
                                        try {
                                            String email = "";
                                            if (object.has("email")) {
                                                email = object.getString("email");
                                            }

                                            String facebook_name = object.getString("name");
                                            String facebook_id = object.getString("id");
                                            UtileKit.persistingPurplePathPref("name_services", facebook_name);
                                            UtileKit.persistingPurplePathPref("email_service", email);
                                            Log.i("EmailLoginFragment", "EmailLoginFragment on create view email " + email + " object " + object.toString());

                                            callLoginServiceSocialMedia(facebook_id, email, facebook_name);
                                            Bundle bundle = new Bundle();
                                            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
                                            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Facebook Login/SignUp");
                                            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Login/SignUp");
                                            MyApplication.mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields",
                                "id,name,email,gender, birthday,link");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.i("Cancel......", "check cancel");
                        LoginManager.getInstance().logOut();
//                        loginPermission();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.i("onError......", "onError cancel" + exception);
                    }
                });
        LoginManager.getInstance().logOut();
    }

    private void loginPermission() {
        // TODO Auto-generated method stub
        try {
            LoginManager.getInstance().logInWithReadPermissions(
                    this,
                    Arrays.asList("public_profile", "email",
                            "user_birthday"));
        } catch (Exception e) {
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Fragment fragment = null;
        if (v == loginMailButtonView) {
            if (!UtileKit.isNetworkAvailable(MyApplication.getInstance())) {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            } else {
                String _loginEmail = getTextFromObjects(loginEdtEmailView).trim();
                String _loginPwd = getTextFromObjects(loginEdtPwdView).trim();
                try {
                    UtileKit.persistingPurplePathPref("firsttimeLogin", "isSuccess");
                    String mLoginBtnChanged = UtileKit.getPersistedPurplePathPref("firsttimeLogin");
                    Log.i("GuideViewFragment", "mLoginBtnChanged " + mLoginBtnChanged);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                validateUserCredentials(_loginEmail, _loginPwd);
            }
        } else if (v == loginForgetPwdView) {
            if (!UtileKit.isNetworkAvailable(MyApplication.getInstance())) {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            } else {
                loginForgetPwdView.setEnabled(false);

                addFragmenttoStack(new ForgotPwdFragment());

            }

        } else if (v == sign_in_googleplus) {
            signIn();
        } else if (v == sign_in_facebook) {
            loginPermission();
        } else if (v == loginCreateAnAccount) {

            // register status
           /* if (UtileKit.getPersistedPurplePathPref(("is_avail") + "").equalsIgnoreCase("")) {
                UtileKit.persistingPurplePathPref("is_avail", "Y");

            } else if (UtileKit.getPersistedPurplePathPref(("is_avail") + "").equalsIgnoreCase("N")) {
              // UtileKit.persistingPurplePathPref("is_avail", "Y");

            }*/
            addFragmenttoStack(new SignUpFragment());
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "2");
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Email Sign Up");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Sign Up");
            MyApplication.mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
        }
    }

    protected void validateUserCredentials(String _loginEmail,
                                           String _loginPwd) {
        // TODO Auto-generated method stub
        if (UtileKit.validateObjectValues(_loginEmail)) {
            if (UtileKit.validateEmail(_loginEmail)) {

                if (UtileKit.validateObjectValues(_loginPwd)) {
                    String mPassword = UtileKit.encryptPwd(_loginPwd);
                    callLoginService(_loginEmail, mPassword, "email");
                } else {

//                    UtileKit.alertDialog(
//                            getString(R.string.http_login_invalid_pwd),
//                            mContext); // added by

                    UtileKit.intitializeAlertDialog(
                            getString(R.string.http_login_invalid_pwd),
                            mContext); // added by
                }
            } else if (android.util.Patterns.PHONE.matcher(_loginEmail).matches()) {
                if (UtileKit.validateObjectValues(_loginPwd)) {
                    callLoginService(_loginEmail, _loginPwd, "phone");
                } else {

//                    UtileKit.alertDialog(
//                            getString(R.string.http_login_invalid_pwd),
//                            mContext); // added by
                    UtileKit.intitializeAlertDialog(
                            getString(R.string.http_login_invalid_pwd),
                            mContext); // added by
                }
            } else {
//                UtileKit.alertDialog(
//                        getString(R.string.forget_pwd_email_val_msg),
//                        mContext); // added by

                UtileKit.intitializeAlertDialog(
                        getString(R.string.forget_pwd_email_val_msg),
                        mContext); // added by

            }
        } else {
//            UtileKit.alertDialog(getString(R.string.forget_pwd_enter_email),
//                    mContext); // added by murali

            UtileKit.intitializeAlertDialog(getString(R.string.forget_pwd_enter_email),
                    mContext); // added by murali
        }
    }

    protected void validateUserCredentials(String _loginEmail,
                                           String _loginPwd, String _phNum) {
        // TODO Auto-generated method stub
        if (UtileKit.validateObjectValues(_loginEmail)) {
            if (UtileKit.validateEmail(_loginEmail)) {
                if (UtileKit.validateObjectValues(_phNum)) {
                    if (UtileKit.validateObjectValues(_loginPwd)) {
                        //callVerificationCode(_loginEmail, _loginPwd, _phNum);
                    } else {
                        UtileKit.intitializeAlertDialog(
                                getString(R.string.http_login_invalid_pwd),
                                mContext);
//                        UtileKit.alertDialog(
//                                getString(R.string.http_login_invalid_pwd),
//                                mContext);
                    }
                } else {
                    UtileKit.intitializeAlertDialog(
                            getString(R.string.http_login_invalid_phonenumber),
                            mContext);
//                    UtileKit.alertDialog(
//                            getString(R.string.http_login_invalid_phonenumber),
//                            mContext);
                }
            } else {
                UtileKit.intitializeAlertDialog(
                        getString(R.string.forget_pwd_email_val_msg),
                        mContext);
//                UtileKit.alertDialog(
//                        getString(R.string.forget_pwd_email_val_msg),
//                        mContext);
            }
        } else {
            UtileKit.intitializeAlertDialog(getString(R.string.forget_pwd_enter_email),
                    mContext);
//            UtileKit.alertDialog(getString(R.string.forget_pwd_enter_email),
//                    mContext);
        }
    }

    private void callLoginService(String mMailID, String password, String flag) {
        UtileKit.showSpinnerDialog(mContext, false);
        UtileKit.persistingPurplePathPref("UserEmailPref", mMailID);


        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        Call<LoginModel> call = webServiceObj.CallLoginService(mMailID, password, flag);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call1, Response<LoginModel> response) {
                //Log.e("CallBack", " response is " + call1.toString());
                UtileKit.dismisssSpinnerDialog();
                LoginModel loginModel = response.body();
                if (loginModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    setRestricted_menus(loginModel.getData().getRestricted_menus());

                    //oAuth_key = "Bearer "+loginModel.getData().getAccess_token();
                    // UtileKit.createSharedPreference(getActivity(), loginModel.getData().getUser_details().getUser_id());
                    UtileKit.persistingPurplePathPref("user_id", loginModel.getData().getUser_details().getUser_id());

                    UtileKit.persistingPurplePathPref("user_id_everytime", loginModel.getData().getUser_details().getUser_id());

                    //Muruga taking user name
                    UtileKit.persistingPurplePathPref("name_services", loginModel.getData().getUser_details().getName());
                    UtileKit.persistingPurplePathPref("email_service", loginModel.getData().getUser_details().getEmail());

                    //taking paid type
                    UtileKit.persistingPurplePathPref("paid_type", loginModel.getData().getUser_details().getUser_paid_type());

                    UtileKit.persistingPurplePathPref("cust_id", loginModel.getData().getUser_details().getCust_id());

                    UtileKit.persistingPurplePathPref("isSignUp_demoScreen", false);
                    UtileKit.persistingPurplePathPref("survey_flag", loginModel.getData().getUser_details().getIs_survey_completed());
                    surch = loginModel.getData().getUser_details().getIs_survey_completed();

                    Intent activity = new Intent(getActivity(), HomePageActivity.class);
                    activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    activity.putExtra("taxfiling_flags", taxfiling_flags);

                    Log.d("taxfiling_flagshome", "taxfiling_flagshome" + taxfiling_flags);

                    startActivity(activity);

//                    addFragmenttoStack(new InstructionScreenOne());
                    SurveyFragmentView.silakidum = 0;

                } else {
                    UtileKit.intitializeAlertDialog(
                            loginModel.getData().getMessage(),
                            mContext);
//                    UtileKit.alertDialog(
//                            loginModel.getData().getMessage(),
//                            mContext);
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "2");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Email Login");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Sign Up");
        MyApplication.mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }

    /*private void callVerificationCode(final String mMailID, final String mPassword, final String mPhoneNumber) {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        Call<VerificationModel> call = webServiceObj.GetVerificationCode(mMailID);
        call.enqueue(new Callback<VerificationModel>() {
            @Override
            public void onResponse(Call<VerificationModel> call, Response<VerificationModel> response) {
                //Log.e("CallBack", " response is " + call.toString());
                UtileKit.dismisssSpinnerDialog();
                VerificationModel verificationModel = response.body();
                if (verificationModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    CodeVerifyFragment frag = new CodeVerifyFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("VerificationCode", verificationModel.getData().getVerify_code());
                    bundle.putString("Email", mMailID);
                    bundle.putString("Password", mPassword);
                    bundle.putString("PhoneNumber", mPhoneNumber);
                    frag.setArguments(bundle);
                    addFragmenttoStack(frag);

                } else {
                    UtileKit.intitializeAlertDialog(
                            verificationModel.getData().getMessage(),
                            mContext);
                }

            }

            @Override
            public void onFailure(Call<VerificationModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });
    }*/

    // [START onActivityResult]

    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        try {
            Log.d("TAG", "handleSignInResult:" + result.isSuccess());
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();
//            loginForgetPwdView.setText("Singned in as" + acct.getDisplayName());

                String url = "";

//            addFragmenttoStack( GetMobileNumberFragment.newInstance(acct.getDisplayName(), url, acct.getEmail(), UtileKit.encryptPwd("12345"), acct.getId()));
//            if (acct.getPhotoUrl() != null)
//                url = acct.getPhotoUrl().toString();
                getUserSocialMediaAccoutnt(acct.getDisplayName(), url, acct.getEmail(), UtileKit.encryptPwd("12345"), acct.getId());
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Google Login/SignUp");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Login/SignUp");
                MyApplication.mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                updateUI(true);

            } else {
                // Signed out, show unauthenticated UI.
                updateUI(false);
            }
        } catch (Exception e) {
        }
    }
    // [END handleSignInResult]

    // [START signIn]

    // [END signIn]

    // [START signOut]
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]

    // [START revokeAccess]
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    private void getUserSocialMediaAccoutnt(String displayName, String url, String email, String x, String id) {
        UtileKit.persistingPurplePathPref("name_services", displayName);
        UtileKit.persistingPurplePathPref("email_service", email);
        callLoginServiceSocialMedia(id, email, displayName);
    }

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

                    setRestricted_menus(loginModel.getData().getRestricted_menus());
                    UtileKit.createSharedPreference(mContext, loginModel.getData().getUser_details().getUser_id());
                    UtileKit.persistingPurplePathPref("firsttimeLogin", "isSuccess");
                    UtileKit.persistingPurplePathPref("name_services", name);
                    UtileKit.persistingPurplePathPref("email_service", mMailID);

                    //taking paid type
                    UtileKit.persistingPurplePathPref("paid_type", loginModel.getData().getUser_details().getUser_paid_type());
                    UtileKit.persistingPurplePathPref("cust_id", loginModel.getData().getUser_details().getCust_id());

                    UtileKit.persistingPurplePathPref("isSignUp_demoScreen", false);
                    Intent homePage = new Intent(getActivity(), HomePageActivity.class);
                    startActivity(homePage);

//                    getActivity().finish();
                } else {
                    try {
                        String mobilenumberischeck = UtileKit.getPersistedPurplePathPref("mobilenumbergot");
                        String countrycode = UtileKit.getPersistedPurplePathPref("countrycode");
                        Log.i("GuideViewFragment", "mobilenumberischeck " + mobilenumberischeck);
                        if (mobilenumberischeck != null) {
                            if (mobilenumberischeck.equalsIgnoreCase("gotmobilenumber")) {
                                callSignUpSocialMediaService(social_media_id, mMailID, mPassword, name, "1",
                                        mobilenumberischeck, countrycode);
                            } else {
                                addFragmenttoStack(GetMobileNumberFragment.newInstance(social_media_id, mMailID, mPassword, name, "1"));
                            }
                        } else {
                            addFragmenttoStack(GetMobileNumberFragment.newInstance(social_media_id, mMailID, mPassword, name, "1"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });
    }

    /**
     * enable menu in list
     *
     * @param restricted_menus
     */
    private void setRestricted_menus(ArrayList<Restricted_menus> restricted_menus) {
        try {
            for (String menu : menuList) {
                persistingPurplePathPref(menu, true);
            }
            for (String menu : menuList) {
                for (Restricted_menus obj : restricted_menus) {
                    if (obj.getMenu_name().equalsIgnoreCase(menu)) {
                        persistingPurplePathPref(menu, false);
                    }
                }


            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }


    private void callSignUpSocialMediaService(String mMediaId, String mMailID, String mPassword, String mFullName, String mFlag, String mobilenumberischeck, String countrycode) {
        UtileKit.showSpinnerDialog(mContext, false);
        UtileKit.persistingPurplePathPref("name_services", mFullName);
        UtileKit.persistingPurplePathPref("email_service", mMailID);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        Call<SignUpModel> call = webServiceObj.callRegisterSocialMediaService(mMediaId, mFullName, mMailID,
                mPassword, mFlag, mobilenumberischeck, countrycode, "1");
        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                //Log.e("CallBack", " response is " + call.toString());
                UtileKit.dismisssSpinnerDialog();
                SignUpModel signupModel = response.body();
                if (signupModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    UtileKit.createSharedPreference(getActivity(), signupModel.getData().getUser_id());
//                   UtileKit.C signupModel.getData().getUser_id();

                    setRestricted_menus(signupModel.getData().getRestricted_menus());

                    //taking paid type
                    UtileKit.persistingPurplePathPref("paid_type", signupModel.getData().getUser_details().getUser_paid_type());
                    UtileKit.persistingPurplePathPref("cust_id", signupModel.getData().getCust_id());

                    Bundle args = new Bundle();
                    args.putString("userid", signupModel.getData().getUser_id());
                    args.putBoolean("IsSignUp", true);
                    UtileKit.persistingPurplePathPref("isSignUp_demoScreen", true);
                    PersonalDetailsFragment fragment = new PersonalDetailsFragment();
                    fragment.setArguments(args);
                    addFragmenttoStack(fragment);
                }
            }

            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.persistingPurplePathPref("firsttimeLogin", "isFailed");
            }
        });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
//        Log.d("TAG", "onConnectionFailed:" + connectionResult);
    }

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

    @Override
    public void onPause() {
        super.onPause();
        InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getActivity().getCurrentFocus();
        if (view == null) {
            view = new View(getActivity());
        }
        im.hideSoftInputFromWindow(view.getWindowToken(), 0);


        try {
            LoginManager.getInstance().logOut();
        } catch (Exception e) {
            e.printStackTrace();
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

}
