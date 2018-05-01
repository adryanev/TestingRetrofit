package com.circlenode.testingretrofit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.circlenode.testingretrofit.R;
import com.circlenode.testingretrofit.model.User;
import com.circlenode.testingretrofit.response.ResponseLogin;
import com.circlenode.testingretrofit.rest.ApiClient;
import com.circlenode.testingretrofit.rest.ApiInterface;
import com.circlenode.testingretrofit.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.etUsername)
    TextInputEditText etUsername;

    @BindView(R.id.etPassword)
    TextInputEditText etPassword;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    SessionManager sessionManager;
    ApiInterface apiService;

    String username, password;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        apiService.login(username,password).enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if(response.isSuccessful()){
                    User loginUser = response.body().getUser();
                    sessionManager.createLoginSession(loginUser);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Toast.makeText(LoginActivity.this, "Berhasil Login", Toast.LENGTH_SHORT).show();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal konek Ke server", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: "+t.getLocalizedMessage() );
            }
        });
    }


}
