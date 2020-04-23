package com.example.nearplace;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nearplace.apiInterface.ApiInterface;
import com.example.nearplace.network.NetworkClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    @BindView(R.id.et_name_signup)
    EditText et_name_signup;
    @BindView(R.id.et_email_signup)
    EditText et_email_signup;
    @BindView(R.id.et_pass_signup)
    EditText et_pass_signup;
    @BindView(R.id.btn_signup)
    Button btn_signup;
    @BindView(R.id.tv_gotolohin)
    TextView tv_gotoSignin;

    String name;
    String email;
    String pass;
    ProgressDialog progressDialog;
    ToastMgs toastMgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        toastMgs = new ToastMgs(this);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email_signup.getText().toString();
                pass = et_pass_signup.getText().toString();
                name = et_name_signup.getText().toString();
                if(!email.isEmpty() && !pass.isEmpty() && !name.isEmpty()){
                    addUser(email,pass,name);
                }else {
                    toastMgs.showToast("Fill all the information");
                }
            }
        });
        

    }
    void addUser(String email,String pass,String name){
        progressDialog.show();
        ApiInterface service =  NetworkClient.getRetrofitClient().create(ApiInterface.class);
        Call<String> call;
        call = service.addUser(email,pass,name);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.hide();
                Log.e("res",response.body().toString());
                String model = response.body();
                if(!model.equals("same")){
                    startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                    finish();
                }else {
                    toastMgs.showToast("Email already used");
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.hide();

            }
        });
    }
}
