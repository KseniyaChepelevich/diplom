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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.UUID;



//import io.bloco.faker.Faker;

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

    public static String generateDate(int plusMonth, int plusDays, String formatPattern) {
        return LocalDate.now().plusMonths(plusMonth).plusDays(plusDays).format(DateTimeFormatter.ofPattern(formatPattern));
    }

    public static String getValidDate(int plusMonth, int plusDays) {
        //int randomNumber = ThreadLocalRandom.current().nextInt(1,60);
        String date = generateDate(plusMonth, plusDays, "dd.MM.yyyy");
        return date;
    }

    public static String generateTime(int plusHours, int plusMinutes, String formatPattern) {
        return LocalTime.now().plusHours(plusHours).plusMinutes(plusMinutes).format(DateTimeFormatter.ofPattern(formatPattern));
    }

    public static String getValidTime(int plusHour, int plusMinuts) {
        //int randomNumber = ThreadLocalRandom.current().nextInt(1,60);
        String time = generateTime(plusHour, plusMinuts, "HH:mm");
        return time;
    }

    public static Cloneable getDateForPicker() {
        Calendar date = Calendar.getInstance();
        return date;
    }





    public static String generateTitleId() {
        //Faker faker = new Faker();
        String titleId = UUID.randomUUID().toString();
        return titleId;

    }

    public String generateTitle(String item) {
        return (item + generateTitleId());

    }



}