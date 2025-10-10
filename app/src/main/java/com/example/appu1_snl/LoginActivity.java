package com.example.appu1_snl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appu1_snl.Interface.JsonPlaceHolderApi;
import com.example.appu1_snl.databinding.ActivityLoginBinding;
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
                    binding.passwordTextInput.setError(null); // Clear the error
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
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

    /**private void getPosts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.obtenerPublicaciones();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    binding.jsonText.setText("Codigo: " + response.code());
                }
                List<Post> listaPublicaciones = response.body();
                for(Post posts : listaPublicaciones){
                    String contenido = "";
                    contenido += "UserId: " + posts.getUserId() + "\n";
                    contenido += "Id: " + posts.getId() + "\n";
                    contenido += "Title: " + posts.getTitle() + "\n";
                    contenido += "Body: " + posts.getBody();
                    binding.jsonText.append(contenido);
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                binding.jsonText.setText(t.getMessage());
            }
        });**/



}


