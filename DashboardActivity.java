package com.example.wasi.khatabook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/** Screen 6: main modules shown as cards in a GridLayout. */
public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        UserStore userStore = new UserStore(this);
        TextView tvGreeting = findViewById(R.id.tvGreeting);
        tvGreeting.setText(getString(R.string.dashboard_greeting, userStore.getSessionName()));

        // Customers, Transactions, Reports, Reminders and Backup show a demo message
        setupModule(R.id.cardCustomers, R.string.module_customers);
        setupModule(R.id.cardTransactions, R.string.module_transactions);
        setupModule(R.id.cardReports, R.string.module_reports);
        setupModule(R.id.cardReminders, R.string.module_reminders);
        setupModule(R.id.cardBackup, R.string.module_backup);

        // Profile opens a real screen using an explicit Intent
        findViewById(R.id.cardProfile).setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, ProfileActivity.class)));
    }

    private void setupModule(int cardId, int nameRes) {
        View card = findViewById(cardId);
        card.setOnClickListener(v -> Toast.makeText(
                this, getString(R.string.module_opened, getString(nameRes)),
                Toast.LENGTH_SHORT).show());
    }
}
