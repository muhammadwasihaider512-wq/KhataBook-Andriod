package com.example.wasi.khatabook;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/** Screen 5: new password + confirm password with validation. */
public class ResetPasswordActivity extends AppCompatActivity {

    private TextInputLayout tilNew, tilConfirm;
    private TextInputEditText etNew, etConfirm;
    private UserStore userStore;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        userStore = new UserStore(this);
        email = getIntent().getStringExtra(ForgotPasswordActivity.EXTRA_EMAIL);

        tilNew = findViewById(R.id.tilNewPassword);
        tilConfirm = findViewById(R.id.tilConfirmPassword);
        etNew = findViewById(R.id.etNewPassword);
        etConfirm = findViewById(R.id.etConfirmPassword);
        Button btnReset = findViewById(R.id.btnResetPassword);

        btnReset.setOnClickListener(v -> attemptReset());
    }

    private void attemptReset() {
        tilNew.setError(null);
        tilConfirm.setError(null);

        String newPass = etNew.getText() == null ? "" : etNew.getText().toString();
        String confirm = etConfirm.getText() == null ? "" : etConfirm.getText().toString();

        boolean valid = true;
        if (TextUtils.isEmpty(newPass)) {
            tilNew.setError(getString(R.string.err_required));
            valid = false;
        } else if (newPass.length() < 6) {
            tilNew.setError(getString(R.string.err_short_password));
            valid = false;
        }

        if (TextUtils.isEmpty(confirm)) {
            tilConfirm.setError(getString(R.string.err_required));
            valid = false;
        } else if (!confirm.equals(newPass)) {
            tilConfirm.setError(getString(R.string.err_password_mismatch));
            valid = false;
        }

        if (!valid || email == null) {
            return;
        }

        userStore.updatePassword(email, newPass);
        Toast.makeText(this, R.string.msg_password_reset, Toast.LENGTH_LONG).show();

        // Back to Login; CLEAR_TOP removes Forgot/Reset screens from the back stack
        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
