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

/** Screen 4: accepts an email and simulates sending a recovery link. */
public class ForgotPasswordActivity extends AppCompatActivity {

    public static final String EXTRA_EMAIL = "extra_email";

    private TextInputLayout tilEmail;
    private TextInputEditText etEmail;
    private UserStore userStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        userStore = new UserStore(this);

        tilEmail = findViewById(R.id.tilEmail);
        etEmail = findViewById(R.id.etEmail);
        Button btnSend = findViewById(R.id.btnSendRecovery);
        TextView tvBack = findViewById(R.id.tvBackToLogin);

        btnSend.setOnClickListener(v -> attemptRecovery());
        tvBack.setOnClickListener(v -> finish());
    }

    private void attemptRecovery() {
        tilEmail.setError(null);
        String email = etEmail.getText() == null ? "" : etEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            tilEmail.setError(getString(R.string.err_required));
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError(getString(R.string.err_invalid_email));
            return;
        }
        if (!userStore.emailExists(email)) {
            tilEmail.setError(getString(R.string.err_email_not_found));
            return;
        }

        // Simulated: no real email is sent, we just show a message and move on
        Toast.makeText(this, getString(R.string.msg_recovery_sent, email), Toast.LENGTH_LONG).show();

        Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
        intent.putExtra(EXTRA_EMAIL, email);
        startActivity(intent);
    }
}
