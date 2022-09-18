package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiSelector;

import ru.iteco.fmhandroid.ui.data.DataHelper;

public class AuthPage {


    public final String packageName;
    private UiDevice device;

    public AuthPage(UiDevice device, String packageName) {
        this.device = device;
        this.packageName = packageName;
    }

    private UiSelector authHeadSelector() {
        return (new UiSelector().className("android.widget.TextView").text("Authorization"));
    }

    private UiSelector loginFieldSelector() {
        return (new UiSelector().className("android.widget.EditText").instance(0));
    }

    private UiSelector passFieldSelector() {
        return (new UiSelector().className("android.widget.EditText").instance(1));
    }

    private BySelector enterButSelector() {
        return By.res(packageName, "enter_button");
    }

    /*public ViewInteraction emptyToast(String id){
        return onView(withText(id)).inRoot(new DataHelper.ToastMatcher());
    }*/


    public UiObject authHead() {
        return device.findObject(authHeadSelector());
    }

    public UiObject loginField() {
        return device.findObject(loginFieldSelector());
    }

    public UiObject passField() {
        return device.findObject(passFieldSelector());
    }

    public UiObject2 enterBut() {
        return device.findObject(enterButSelector());
    }



   /*private UiObject2 getUiObject2(BySelector selector) {
        return device.wait(Until.findObject(selector), 10000);
    }*/
}
