package ru.iteco.fmhandroid.ui.data;

import android.app.Notification;
import android.app.UiAutomation;
import android.nfc.Tag;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

import androidx.test.espresso.Root;
import androidx.test.espresso.core.internal.deps.guava.collect.ImmutableMap;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.concurrent.TimeoutException;
import java.util.function.Function;

public class DataHelper {
    private DataHelper() {

    }

    public static class AuthInfo {
        private final String login;
        private final String pass;

        public AuthInfo(String login, String pass) {
            this.login = login;
            this.pass = pass;
        }


        public String getLogin() {
            return login;
        }

        public String getPass() {
            return pass;
        }

    }

    public static AuthInfo authInfo() {
        String login = "login2";
        String pass = "password2";
        return new AuthInfo(login, pass);
    }

    public static AuthInfo invalidAuthInfo() {
        String login = "invalid2";
        String pass = "invalid2";
        return new AuthInfo(login, pass);
    }

    private static final String TAG = "IsToast";

    public static boolean waitForToast(String toast, long timeout) {
        return waitForToast(toast, Function::identity, timeout);
    }

    private static boolean waitForToast(String toast, Runnable r, long timeout) {
        UiAutomation automation = InstrumentationRegistry.getInstrumentation().getUiAutomation();
        try {
            AccessibilityEvent event = automation.executeAndWaitForEvent(
                    r,
                    e -> isToastShow(e, toast),
                    timeout);
            event.recycle();
            return true;
        } catch (TimeoutException e) {
            return  false;
        }
    }

    private static boolean isToastShow(AccessibilityEvent event, String toast) {
        if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            Parcelable parcelable = event.getParcelableData();
            if (!(parcelable instanceof Notification)) { // without Notification is Toast
                String toastMessage = "" + event.getText().get(0);
                Log.d(TAG, toastMessage);
                return  toastMessage.contains(toast);
            }
        }
        return false;
    }


}
