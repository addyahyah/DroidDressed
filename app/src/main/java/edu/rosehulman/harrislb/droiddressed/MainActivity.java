package edu.rosehulman.harrislb.droiddressed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        Button registerButton = (Button) findViewById(R.id.register_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);

            }

        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}


/**
 * Eventually this main should work. (From password keeper)
 */
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//
//import com.firebase.client.AuthData;
//import com.firebase.client.Firebase;
//import com.firebase.client.FirebaseError;
//
//
//import java.io.IOException;
//
//
//
//public class MainActivity extends AppCompatActivity {
//
//    private static final int REQUEST_CODE_GOOGLE_LOGIN = 1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        if (savedInstanceState == null) {
//            Firebase.setAndroidContext(this);
//        }
//
//
//        Firebase firebase = new Firebase(Constants.FIREBASE_URL);
//        if (firebase.getAuth() ==null || isExpired(firebase.getAuth())) {
//            //switchToLoginFragment();
//        } else {
//            switchToPasswordFragment(Constants.FIREBASE_URL + "/users/" + firebase.getAuth().getUid());
//
//        }
//    }
//
//    private boolean isExpired(AuthData authData) {
//        return (System.currentTimeMillis() / 1000) >= authData.getExpires();
//    }
//
//    public void onLogin(String email, String password) {
//        //TODO: Log user in with username & password
//        Firebase firebase = new Firebase(Constants.FIREBASE_URL);
//        firebase.authWithPassword(email, password, new MyAuthResultHandler());
//
//    }
//
//
//    class MyAuthResultHandler implements Firebase.AuthResultHandler {
//
//        @Override
//        public void onAuthenticated(AuthData authData) {
//            switchToPasswordFragment(Constants.FIREBASE_URL + "/users/" + authData.getUid());
//        }
//
//        @Override
//        public void onAuthenticationError(FirebaseError firebaseError) {
//            Log.e(Constants.TAG, "onAuthenticationError:" + firebaseError.getMessage());
//
//        }
//    }
//
//
//
//
//
//
//    private void onGoogleLoginWithToken(String oAuthToken) {
//        //DONE: Log user in with Google OAuth Token
//        Firebase firebase = new Firebase(Constants.FIREBASE_URL);
//        firebase.authWithOAuthToken("google", oAuthToken, new MyAuthResultHandler());
//    }
//
//
//
//    public void onLogout() {
//        //DONE: Log the user out.
//        Firebase firebase = new Firebase(Constants.FIREBASE_URL);
//        firebase.unauth();
//        //switchToLoginFragment();
//    }
//
//
//
//    private void switchToPasswordFragment(String repoUrl) {
//        //Switch to Home Activity?
//    }
//
//    private void showLoginError(String message) {
//        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentByTag("Login");
//        loginFragment.onLoginError(message);
//    }
//
//}
//
