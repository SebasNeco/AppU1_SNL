package com.example.appu1_snl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appu1_snl.Interface.DAMusatAPI;
import com.example.appu1_snl.Interface.JsonPlaceHolderApi;
import com.example.appu1_snl.databinding.ActivityLoginBinding;
import com.example.appu1_snl.model.AuthRequest;
import com.example.appu1_snl.model.AuthResponse;
import com.example.appu1_snl.model.Post;
import com.example.appu1_snl.model.generandoJson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.nextButton.setOnClickListener(v -> {

            if (!isPasswordValid(binding.passwordEditText.getText())) {
                binding.passwordTextInput.setError(getString(R.string.shr_error_password));
            } else {
                if (!isUsernameValid(binding.txtUsername.getText())) {
                    binding.txtILUsername.setError(getString(R.string.shr_error_username));

                } else{
                    binding.txtILUsername.setError(null);
                    binding.passwordTextInput.setError(null);
                    getToken();
                }
            }

            SharedPreferences sharedPreferences = getSharedPreferences("SP_USAT", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", binding.txtUsername.getText().toString());
            editor.apply();

        });

        binding.passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isPasswordValid(binding.passwordEditText.getText())) {
                    binding.passwordTextInput.setError(null); //Clear the error
                }
                return false;

            }
        });


        binding.txtUsername.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isUsernameValid(binding.txtUsername.getText())) {
                    binding.txtILUsername.setError(null); //Clear the error
                }
                return false;
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



    }

    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }
    private boolean isUsernameValid(@Nullable Editable text) {
        return text != null && text.length() >= 3;
    }

    private void getToken(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sebasneco.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DAMusatAPI damusatAPI = retrofit.create(DAMusatAPI.class);
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername(binding.txtUsername.getText().toString());
        authRequest.setPassword(binding.passwordEditText.getText().toString());
        SharedPreferences sharedPreferences = getSharedPreferences("SP_USAT", MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.remove("tokenJWT");
        sharedPrefEditor.apply();
        Call<AuthResponse> call = damusatAPI.obtenerToken(authRequest);
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse authResponse = response.body();
                    SharedPreferences.Editor editor = getSharedPreferences("SP_USAT", MODE_PRIVATE).edit();
                    editor.putString("username", binding.txtUsername.getText().toString());
                    editor.putString("tokenJWT", authResponse.getAccess_token());
                    editor.apply();

                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    String message = !response.isSuccessful()
                            ? "Code: " + response.code()
                            : "Respuesta inválida del servidor.";
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}


