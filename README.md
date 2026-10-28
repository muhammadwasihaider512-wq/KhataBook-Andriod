# KhataBook - Assignment 2 (Android, Java + XML)

**Student:** Muhammad Wasi Haider (24108127)
**Course:** Mobile Application Development, BS (AI)-5A, SZABIST-ISB
**Submitted to:** Muhammad Azhar

KhataBook is a digital credit-ledger app (continuing from Assignment 1). This assignment builds the common screens every app needs: splash, login, registration, password recovery, a dashboard and a profile. Authentication is **simulated locally** - no backend is used.

## Demo account

| Username / Email | Password |
|---|---|
| `admin` or `admin@khatabook.com` | `admin123` |

You can also create your own account on the Register screen.

## Screens

| # | Screen | Activity | Main layout |
|---|---|---|---|
| 1 | Splash (logo + name) | `SplashActivity` | ConstraintLayout |
| 2 | Login | `LoginActivity` | LinearLayout |
| 3 | Register | `RegisterActivity` | LinearLayout |
| 4 | Forgot Password | `ForgotPasswordActivity` | LinearLayout |
| 5 | Reset Password | `ResetPasswordActivity` | LinearLayout |
| 6 | Dashboard (module cards) | `DashboardActivity` | LinearLayout + GridLayout |
| 7 | Profile + Logout | `ProfileActivity` | ConstraintLayout |

## Navigation flow

```
Splash --(2 s)--> Login --Register--> Register --(success)--> Login
                    |
                    +--Forgot Password--> Forgot Password --> Reset Password --> Login
                    |
                    +--Login (valid)--> Dashboard --Profile card--> Profile --Logout--> Login
```

All navigation uses **explicit Intents** (`new Intent(this, TargetActivity.class)`). The back stack is cleared after logout and after a password reset so the Back button cannot return to a protected screen.

## Features

- **Input controls:** `textEmailAddress` for email, `textPersonName` for name, `textPassword` with a show/hide toggle for passwords (Material `TextInputLayout`).
- **Validation:** required fields, email format, password minimum 6 characters, name minimum 3 characters, password and confirm-password must match. Errors appear under the field.
- **Login:** accepts email or username; shows an error for wrong credentials.
- **Register:** rejects an email that is already registered.
- **Forgot password:** checks the email exists, shows a simulated "recovery link sent" message, then opens Reset Password.
- **Dashboard:** six cards (Customers, Transactions, Reports, Reminders, Backup, Profile) in a 2-column `GridLayout`, each with an icon and text. Profile opens the Profile screen; the others show a demo message.
- **Profile:** shows name, email and member-since date, with a Logout button.

## Android resources

- `res/values/strings.xml` - all UI text (no hard-coded strings in layouts).
- `res/values/colors.xml` - default (light) palette.
- `res/values-night/colors.xml` and `values-night/themes.xml` - alternative night-mode palette and theme. Switch the phone to dark mode to see it.
- `res/values/themes.xml` - shared styles for titles, buttons, input fields and dashboard cards, so every screen looks consistent.
- `res/drawable/` - vector logo and module icons.

## Project structure

```
app/src/main/
  java/com/example/wasi/khatabook/   Activities + UserStore (simulated local user data)
  res/layout/                        activity_*.xml
  res/values/, res/values-night/     strings, colors, themes (default + night)
  res/drawable/                      logo and icons
  AndroidManifest.xml
```

## How to run

1. Open the project folder in Android Studio.
2. Let Gradle sync, then run on an emulator or phone (minSdk 24 / Android 7.0).
