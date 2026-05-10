package com.devblocks42.vaultchat.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.devblocks42.vaultchat.R;
import com.devblocks42.vaultchat.Utils;
import com.devblocks42.vaultchat.crypto.KeyManager;
import com.devblocks42.vaultchat.dto.RegisterRequest;
import com.devblocks42.vaultchat.dto.RegisterResponse;
import com.devblocks42.vaultchat.repositories.AuthRepository;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private final AuthRepository repository = new AuthRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onRegisterButtonClicked(View view) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        EditText username = findViewById(R.id.inputUsername);
        EditText password1 = findViewById(R.id.inputPassword);
        EditText password2 = findViewById(R.id.inputPassword2);
        EditText email = findViewById(R.id.inputEmail);
        RegisterRequest registerRequest = new RegisterRequest();
        KeyManager keyManager = new KeyManager();
        keyManager.generateECDSAKeyPair();
        keyManager.generateECDHKeyPair();
        registerRequest.username = username.getText().toString().strip();
        registerRequest.password = password1.getText().toString().strip();
        registerRequest.password2 = password2.getText().toString().strip();
        registerRequest.email = email.getText().toString().strip();
        registerRequest.signing_public_key = keyManager.getECDSAPublicKey().strip();
        registerRequest.key_agreement_public_key = keyManager.getECDHPublicKey().strip();
        repository.register(registerRequest, new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Compte créé", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String errors = Utils.parseError(response.errorBody().string());
                        Toast.makeText(RegisterActivity.this, "Erreur : " + errors, Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}