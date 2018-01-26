package com.teampietro.hero;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.teampietro.hero.helper.APIClient;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class WelcomeFragment extends Fragment {

    private CallbackManager callbackManager;

    private Profile profile;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_welcome, container, false);

        ProgressBar progressBar = view.findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);

        profile = Profile.getCurrentProfile();
        if (profile != null) {
            Log.d("WELCOME", "User already logged in. Redirecting to main screen.");

            /*Intent intent = new Intent(getContext(), MainBoard.class);
            startActivity(intent);*/
        }

        final LoginButton loginButton = view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String token = loginResult.getAccessToken().getToken();
                Log.d("WELCOME", "Success. AccessToken: " + token);
                com.teampietro.hero.models.Profile.getInstance().token = token;

                APIClient.fetchProfile(token, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        com.teampietro.hero.models.Profile.updateWithJson(response);
                        EventBus.getDefault().post(new com.teampietro.hero.models.Profile.ProfileUpdatedEvent());

                        MatchFragment fragment = new MatchFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, fragment, "MATCH_TAG");
                        transaction.commit();

                    }
                });

                ProgressBar waitBar = view.findViewById(R.id.progressBar2);
                waitBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.MULTIPLY);
                waitBar.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancel() {
                // App code
                Log.i("WELCOME", "User cancelled FB login.");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.e("WELCOME", "An error occurred during FB login: " + exception);
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    protected void to2site(View view){

        MatchFragment fragment = new MatchFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, "MATCH_TAG");
        transaction.commit();

    }


}
