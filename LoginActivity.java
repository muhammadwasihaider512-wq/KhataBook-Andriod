package com.example.wasi.khatabook;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/** Screen 2: email/username + password with Login, Register and Forgot Password. */
public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilIdentifier, tilPassword;
    private TextInputEditText etIdentifier, etPassword;
    private UserStore userStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userStore = new UserStore(this);

        tilIdentifier = findViewById(R.id.tilIdentifier);
        tilPassword = findViewById(R.id.tilPassword);
        etIdentifier = findViewById(R.id.etIdentifier);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView tvForgot = findViewById(R.id.tvForgotPassword);

        btnLogin.setOnClickListener(v -> attemptLogin());

        btnRegister.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        tvForgot.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class)));
    }

    private void attemptLogin() {
        tilIdentifier.setError(null);
        tilPassword.setError(null);

        String identifier = etIdentifier.getText() == null ? "" : etIdentifier.getText().toString().trim();
        String password = etPassword.getText() == null ? "" : etPassword.getText().toString();

        boolean valid = true;
        if (TextUtils.isEmpty(identifier)) {
            tilIdentifier.setError(getString(R.string.err_required));
            valid = false;
        }
        if (TextUtils.isEmpty(password)) {
            tilPassword.setError(getString(R.string.err_required));
            valid = false;
        } else if (password.length() < 6) {
            tilPassword.setError(getString(R.string.err_short_password));
            valid = false;
        }
        if (!valid) {
            return;
        }

        if (userStore.login(identifier, password)) {
            Toast.makeText(this, R.string.msg_login_success, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            tilPassword.setError(getString(R.string.err_wrong_credentials));
        }
    }
}
