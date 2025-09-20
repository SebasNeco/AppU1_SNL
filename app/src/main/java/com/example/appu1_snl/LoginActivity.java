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
import com.example.appu1_snl.databinding.ActivityLoginBinding;

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

}


