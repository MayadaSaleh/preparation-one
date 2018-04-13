package gp.web.and.mobile.preparationtask_1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity implements Contract.MyView {


    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.enterButton)
    Button enterButton;
    @BindView(R.id.login_button)
    LoginButton loginButton;

    Contract.MyPresenter myPresenter;

    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPresenter = new PresenterClass(this);

        //Login With Facebook
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                myPresenter.getUserDetailsusingFBPresenter(loginResult);
            }

            @Override
            public void onCancel() {
                Log.i("cancelLogin", "login request has been canceled");
            }

            @Override
            public void onError(FacebookException error) {
                error.fillInStackTrace();
                Log.i("Error Message", error.getMessage());
                Log.i("error request", "login request has an error");
            }
        });

    }

    //Login With Facebook
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //Login With Facebook
    @Override
    public void getUserDetailsusingFBView(JSONObject json_object) {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.putExtra("userProfile", json_object.toString());
        startActivity(intent);
    }


    //Mail and Password Validation
    @OnClick(R.id.enterButton)
    public void checkValidate() {

        Boolean state = checkInternetConnectivity();
        if (state == true) {

            myPresenter.checkValidation();
        } else {
            Toast.makeText(getApplicationContext(), "Please open your internet access", Toast.LENGTH_LONG).show();
        }
    }

        //Check Internet Connectivity
    public boolean checkInternetConnectivity() {
        boolean status;
        final ConnectivityManager connectivityManager = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE));
        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()) {
            status = true;
        } else {
            status = false;
        }
        return status;
    }


    @Override
    public void initView() {
        ButterKnife.bind(this);


//        //To get keyhash
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo("gp.web.and.mobile.preparationtask_1",PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));  //jx+l4rOdSAJTESUYVrKCo6mEEvw=
//            }        } catch (PackageManager.NameNotFoundException e)
//        {       }
//        catch (NoSuchAlgorithmException e) {        }

    }

    @Override
    public String getMail() {
        return userName.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        Log.i("pass", password.getText().toString().trim() + "x");
        return password.getText().toString().trim();
    }

    @Override
    public void getValidationResult(String s) {

        switch (s) {
            case "1":
                Toast.makeText(getApplicationContext(), "valid email address and password", Toast.LENGTH_LONG).show();

                String check = myPresenter.checkjson(userName.getText().toString().trim(), password.getText().toString().trim());
                if (check == "true") {
                    Toast.makeText(getApplicationContext(), "Mail:" + userName.getText().toString().trim() + "Password:" + password.getText().toString().trim(), Toast.LENGTH_LONG).show();

                    Intent myIntent = new Intent(MainActivity.this, HomeActivity.class);
                    myIntent.putExtra("mail", userName.getText().toString().trim());
                    myIntent.putExtra("password", password.getText().toString().trim());
                    MainActivity.this.startActivity(myIntent);
                } else if (check == "loading") {
                    Toast.makeText(getApplicationContext(), "Loading ... please click again", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Error Data", Toast.LENGTH_LONG).show();
                }
                break;

            case "2":
                Toast.makeText(getApplicationContext(), "You have to enter  valid mail and password", Toast.LENGTH_LONG).show();
                break;

            case "3":
                Toast.makeText(getApplicationContext(), "You have to enter the password ", Toast.LENGTH_LONG).show();
                break;

            case "4":
                Toast.makeText(getApplicationContext(), "You have to enter the mail", Toast.LENGTH_LONG).show();
                break;

            case "5":
                Toast.makeText(getApplicationContext(), "You have to enter the mail and the password", Toast.LENGTH_LONG).show();
                break;
        }

    }
}


