package com.example.nearplace;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nearplace.apiInterface.ApiInterface;
import com.example.nearplace.model.loginResponse.LoginModel;
import com.example.nearplace.network.NetworkClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    AlertDialog alertDialog;
    ProgressDialog progressDialog;

    boolean isSignedIn = false;

    String name;
    String email;
    String pass;
    @BindView(R.id.et_email_signin)
    EditText et_email_signin;
    @BindView(R.id.et_pass_signin)
    EditText et_pass_signin;
    @BindView(R.id.btn_signin)
    Button btn_signin;
    @BindView(R.id.tv_gotoSignup)
    TextView btn_signUp;
    ToastMgs toastMgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        toastMgs = new ToastMgs(this);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email_signin.getText().toString();
                pass = et_pass_signin.getText().toString();
                if(!email.isEmpty() && !pass.isEmpty()){
                    login(email,pass);
                }else {
                    toastMgs.showToast("Enter email and pass");

                }
            }
        });
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
    }

    void login(String email,String pass){
        progressDialog.show();
        ApiInterface service =  NetworkClient.getRetrofitClient().create(ApiInterface.class);
        Call<LoginModel> call;
        call = service.getUser(email,pass);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                progressDialog.hide();
                Log.e("res",response.body().toString());
                LoginModel model = response.body();
                if(model.get(0).getStatus().equals("success")){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }else {
                    toastMgs.showToast("Email and Password are not right.");
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                progressDialog.hide();

            }
        });

    }

}
