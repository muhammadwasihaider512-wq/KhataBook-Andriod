package com.example.wasi.khatabook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/** Screen 7: shows user information and a Logout button. */
public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        UserStore userStore = new UserStore(this);

        ((TextView) findViewById(R.id.tvProfileName)).setText(userStore.getSessionName());
        ((TextView) findViewById(R.id.tvProfileEmail)).setText(userStore.getSessionEmail());
        ((TextView) findViewById(R.id.tvInfoName)).setText(userStore.getSessionName());
        ((TextView) findViewById(R.id.tvInfoEmail)).setText(userStore.getSessionEmail());
        ((TextView) findViewById(R.id.tvInfoSince)).setText(userStore.getSessionSince());

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            userStore.logout();
            Toast.makeText(this, R.string.logged_out, Toast.LENGTH_SHORT).show();

            // Clear the whole back stack so Back cannot return to the Dashboard
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}
