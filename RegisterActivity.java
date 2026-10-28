package com.example.wasi.khatabook;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/** Screen 3: registration with name, email, password and confirm password. */
public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout tilName, tilEmail, tilPassword, tilConfirm;
    private TextInputEditText etName, etEmail, etPassword, etConfirm;
    private UserStore userStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userStore = new UserStore(this);

        tilName = findViewById(R.id.tilName);
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        tilConfirm = findViewById(R.id.tilConfirmPassword);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirmPassword);

        Button btnCreate = findViewById(R.id.btnCreateAccount);
        TextView tvBack = findViewById(R.id.tvBackToLogin);

        btnCreate.setOnClickListener(v -> attemptRegister());
        tvBack.setOnClickListener(v -> goToLogin());
    }

    private void attemptRegister() {
        tilName.setError(null);
        tilEmail.setError(null);
        tilPassword.setError(null);
        tilConfirm.setError(null);

        String name = textOf(etName).trim();
        String email = textOf(etEmail).trim();
        String password = textOf(etPassword);
        String confirm = textOf(etConfirm);

        boolean valid = true;

        if (TextUtils.isEmpty(name)) {
            tilName.setError(getString(R.string.err_required));
            valid = false;
        } else if (name.length() < 3) {
            tilName.setError(getString(R.string.err_name_short));
            valid = false;
        }

        if (TextUtils.isEmpty(email)) {
            tilEmail.setError(getString(R.string.err_required));
            valid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError(getString(R.string.err_invalid_email));
            valid = false;
        }

        if (TextUtils.isEmpty(password)) {
            tilPassword.setError(getString(R.string.err_required));
            valid = false;
        } else if (password.length() < 6) {
            tilPassword.setError(getString(R.string.err_short_password));
            valid = false;
        }

        if (TextUtils.isEmpty(confirm)) {
            tilConfirm.setError(getString(R.string.err_required));
            valid = false;
        } else if (!confirm.equals(password)) {
            tilConfirm.setError(getString(R.string.err_password_mismatch));
            valid = false;
        }

        if (!valid) {
            return;
        }

        if (userStore.register(name, email, password)) {
            Toast.makeText(this, R.string.msg_register_success, Toast.LENGTH_LONG).show();
            goToLogin();
        } else {
            tilEmail.setError(getString(R.string.msg_email_exists));
        }
    }

    private void goToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private String textOf(TextInputEditText editText) {
        return editText.getText() == null ? "" : editText.getText().toString();
    }
}
