package ru.iteco.fmhandroid.ui.data;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.os.IBinder;
import android.os.SystemClock;
import android.view.WindowManager;

import androidx.test.espresso.Root;
import androidx.test.espresso.ViewInteraction;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;


import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

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
        String login = "invalid";
        String pass = "invalid";
        return new AuthInfo(login, pass);
    }

    public static class ToastMatcher extends TypeSafeMatcher<Root> {

        @Override
        public void describeTo(Description description) {
            description.appendText("is toast");
        }

        @Override
        public boolean matchesSafely(Root root) {
            int type = root.getWindowLayoutParams().get().type;
            if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
                IBinder windowToken = root.getDecorView().getWindowToken();
                IBinder appToken = root.getDecorView().getApplicationWindowToken();
                if (windowToken == appToken) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class EspressoBaseTest {
        public static void clickButton(ViewInteraction nameButton) {
            SystemClock.sleep(3000);
            nameButton.check(matches(isDisplayed()));
            nameButton.perform(click());
        }
    }

    public static String generateTitleId() {
        String titleId = UUID.randomUUID().toString();
        return titleId;
    }

    public static LocalDateTime generateDate(int plusDays) {
        return LocalDateTime.now().plusDays(plusDays);
    }

    public static LocalDateTime getValidDate() {
        int randomNumber = ThreadLocalRandom.current().nextInt(1, 368);
        LocalDateTime date = generateDate(randomNumber);
        return date;
    }

    public static LocalDateTime getDateOneMonthAgo() {
        LocalDateTime date = generateDate(-31);
        return date;
    }

    public static LocalDateTime getDateTenDaysAgo() {
        LocalDateTime date = generateDate(-10);
        return date;
    }

    public static LocalDateTime getDateToday() {
        LocalDateTime date = generateDate(0);
        return date;
    }


}