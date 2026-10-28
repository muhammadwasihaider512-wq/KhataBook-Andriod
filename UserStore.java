package com.example.wasi.khatabook;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Simulated local "backend".
 * Users are kept in SharedPreferences, plus one hardcoded demo account
 * (admin / admin123) so the app can be tested immediately.
 * No network or real security here - this is for the assignment only.
 */
public class UserStore {

    public static final String DEMO_USERNAME = "admin";
    public static final String DEMO_EMAIL = "admin@khatabook.com";
    private static final String DEMO_NAME = "Admin User";
    private static final String DEMO_PASSWORD = "admin123";

    private static final String PREFS = "khatabook_prefs";
    private static final String KEY_SESSION_EMAIL = "session_email";

    private final SharedPreferences prefs;

    public UserStore(Context context) {
        prefs = context.getApplicationContext()
                .getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        seedDemoUser();
    }

    private void seedDemoUser() {
        if (!prefs.contains("pw_" + DEMO_EMAIL)) {
            prefs.edit()
                    .putString("name_" + DEMO_EMAIL, DEMO_NAME)
                    .putString("pw_" + DEMO_EMAIL, DEMO_PASSWORD)
                    .putString("since_" + DEMO_EMAIL, "October 2026")
                    .apply();
        }
    }

    /** Turns "admin" into the demo email; any other input is treated as an email. */
    private String resolveEmail(String identifier) {
        String id = identifier.trim().toLowerCase();
        return id.equals(DEMO_USERNAME) ? DEMO_EMAIL : id;
    }

    public boolean emailExists(String email) {
        return prefs.contains("pw_" + email.trim().toLowerCase());
    }

    /** @return true if the account was created, false if the email is taken. */
    public boolean register(String name, String email, String password) {
        String key = email.trim().toLowerCase();
        if (emailExists(key)) {
            return false;
        }
        prefs.edit()
                .putString("name_" + key, name.trim())
                .putString("pw_" + key, password)
                .putString("since_" + key, "October 2026")
                .apply();
        return true;
    }

    /** @return true and starts a session if the credentials match. */
    public boolean login(String identifier, String password) {
        String email = resolveEmail(identifier);
        String saved = prefs.getString("pw_" + email, null);
        if (saved != null && saved.equals(password)) {
            prefs.edit().putString(KEY_SESSION_EMAIL, email).apply();
            return true;
        }
        return false;
    }

    public void updatePassword(String email, String newPassword) {
        prefs.edit().putString("pw_" + email.trim().toLowerCase(), newPassword).apply();
    }

    public void logout() {
        prefs.edit().remove(KEY_SESSION_EMAIL).apply();
    }

    public String getSessionEmail() {
        return prefs.getString(KEY_SESSION_EMAIL, "");
    }

    public String getSessionName() {
        return prefs.getString("name_" + getSessionEmail(), "");
    }

    public String getSessionSince() {
        return prefs.getString("since_" + getSessionEmail(), "");
    }
}
