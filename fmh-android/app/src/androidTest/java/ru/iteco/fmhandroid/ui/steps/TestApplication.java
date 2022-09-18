package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.notNullValue;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import ru.iteco.fmhandroid.ui.page.AuthPage;

public class TestApplication {

    private UiDevice device;
    public static final String packageName = "ru.iteco.fmhandroid";
    private static final int TIMEOUT = 5000;
    private AuthPage authPage;



    public TestApplication() {

        //Connect to device
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        // Press home
        device.pressHome();

        // Wait for launcher
        final String launcherPackage = device.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                TIMEOUT);

        // Launch the
        Context context = ApplicationProvider.getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(packageName);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)),
                TIMEOUT);
        //waitForPackage(packageName);

        authPage = new AuthPage(device, packageName);
    }

    /*public void waitForPackage(String packageName) {
        Context context = ApplicationProvider.getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(packageName);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)),
                TIMEOUT);

    }*/



    public void authentication(String login, String password) throws UiObjectNotFoundException {

        authPage.loginField().setText(login);
        authPage.passField().setText(password);
        authPage.enterBut().clickAndWait(Until.newWindow(), 5000);
    }

    public void closeApp() throws UiObjectNotFoundException {
        UiObject authImButton = device.findObject(new UiSelector().resourceId("ru.iteco.fmhandroid:id/authorization_image_button"));
        authImButton.click();

        device.findObject(new UiSelector().resourceId("android:id/title")).click();
    }
}
