package com.introtoandroid.passwordmatcher;


import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PasswordMatcherTest extends
        ActivityInstrumentationTestCase2<PasswordMatcherActivity> {
    private static final String EMPTY_STRING = "";
    private static final String GOOD_PASSWORD = "A B C 1 2 3 ENTER";
    private static final String BAD_PASSWORD = "S B C 1 2 3 ENTER";

    TextView title;
    EditText password;
    EditText matchingPassword;
    Button button;
    TextView passwordResult;
    PasswordMatcherActivity passwordMatcherActivity;

    public PasswordMatcherTest() {
        super(PasswordMatcherActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        passwordMatcherActivity = getActivity();
        title = (TextView) passwordMatcherActivity.findViewById(R.id.title);
        password = (EditText) passwordMatcherActivity.findViewById(R.id.password);
        matchingPassword = (EditText) passwordMatcherActivity.findViewById(R.id.matchingPassword);
        button = (Button) passwordMatcherActivity.findViewById(R.id.matchButton);
        passwordResult = (TextView) passwordMatcherActivity.findViewById(R.id.passwordResult);
    }

    public void testPreConditions() {
        String t = title.getText().toString();
        assertEquals(passwordMatcherActivity.getResources().getString(R.string.match_passwords_title), t);

        String p = password.getText().toString();
        String pHint = password.getHint().toString();
        int pInput = password.getInputType();
        assertEquals(EMPTY_STRING, p);
        assertEquals(passwordMatcherActivity.getResources().getString(R.string.password), pHint);
        assertEquals(129, pInput);

        String mp = matchingPassword.getText().toString();
        String mpHint = matchingPassword.getHint().toString();
        int mpInput = matchingPassword.getInputType();
        assertEquals(EMPTY_STRING, mp);
        assertEquals(passwordMatcherActivity.getResources().getString(R.string.matching_password), mpHint);
        assertEquals(129, mpInput);

        String b = button.getText().toString();
        assertEquals(passwordMatcherActivity.getResources().getString(R.string.match_password_button), b);

        int visibility = passwordResult.getVisibility();
        assertEquals(View.GONE, visibility);
    }

    public void testMatchingPasswords() {
        TouchUtils.clickView(this, password);
        sendKeys(GOOD_PASSWORD);

        TouchUtils.clickView(this, matchingPassword);
        sendKeys(GOOD_PASSWORD);

        TouchUtils.clickView(this, button);

        String p = password.getText().toString();
        assertEquals("abc123", p);

        String mp = matchingPassword.getText().toString();
        assertEquals("abc123", mp);

        assertEquals(p, mp);

        int visibility = passwordResult.getVisibility();
        assertEquals(View.VISIBLE, visibility);

        String notice = passwordResult.getText().toString();
        assertEquals(passwordMatcherActivity.getResources().getString(R.string.passwords_match_notice), notice);

        int noticeColor = passwordResult.getCurrentTextColor();
        assertEquals(passwordMatcherActivity.getResources().getColor(R.color.green), noticeColor);
    }

    public void testEmptyPasswords() {
        TouchUtils.clickView(this, password);
        sendKeys(EMPTY_STRING);

        TouchUtils.clickView(this, matchingPassword);
        sendKeys(EMPTY_STRING);

        TouchUtils.clickView(this, button);

        String p = password.getText().toString();
        assertEquals(p, EMPTY_STRING);

        String mp = matchingPassword.getText().toString();
        assertEquals(mp, EMPTY_STRING);

        assertEquals(p, mp);

        int visibility = passwordResult.getVisibility();
        assertEquals(View.VISIBLE, visibility);

        String notice = passwordResult.getText().toString();
        assertEquals(passwordMatcherActivity.getResources().getString(R.string.passwords_do_not_match_notice), notice);

        int noticeColor = passwordResult.getCurrentTextColor();
        assertEquals(passwordMatcherActivity.getResources().getColor(R.color.red), noticeColor);
    }

    public void testNotEqualPasswords() {
        TouchUtils.clickView(this, password);
        sendKeys(GOOD_PASSWORD);

        TouchUtils.clickView(this, matchingPassword);
        sendKeys(BAD_PASSWORD);

        TouchUtils.clickView(this, button);

        String good = password.getText().toString();
        assertEquals("abc123", good);

        String bad = matchingPassword.getText().toString();
        assertEquals("sbc123", bad);

        assertTrue("Passwords should not match", (!good.equals(bad)));

        int visibility = passwordResult.getVisibility();
        assertEquals(View.VISIBLE, visibility);

        String notice = passwordResult.getText().toString();
        assertEquals(passwordMatcherActivity.getResources().getString(R.string.passwords_do_not_match_notice), notice);

        int noticeColor = passwordResult.getCurrentTextColor();
        assertEquals(passwordMatcherActivity.getResources().getColor(R.color.red), noticeColor);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
