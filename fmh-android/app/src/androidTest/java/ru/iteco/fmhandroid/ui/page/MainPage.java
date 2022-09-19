package ru.iteco.fmhandroid.ui.page;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

public class MainPage {
    public final String packageName;
    private UiDevice device;

    /*public MainPage(UiDevice device, String packageName) {
        this.device = device;
        this.packageName = packageName;
    }*/

    public MainPage(String packageName) {
        this.packageName = packageName;
    }

    private BySelector mainPageNameSelector() {
        return By.res(packageName, "ru.iteco.fmhandroid:id/main_swipe_refresh");
    }

    private UiSelector newsHeaderSelector() {
        return (new UiSelector().className("android.widget.TextView").text("News"));
    }

    private UiSelector claimsHearSelector() {
        return (new UiSelector().className("android.widget.TextView").text("Claims"));

    }

    private BySelector newsBlockSelector() {
        return By.res(packageName, "ru.iteco.fmhandroid:id/container_list_news_include_on_fragment_main");
    }

    private BySelector claimsBlockSelector() {
        return By.res(packageName, "ru.iteco.fmhandroid:id/container_list_claim_include_on_fragment_main");
    }

    private BySelector allNewsButSelector() {
        return By.res(packageName, "ru.iteco.fmhandroid:id/all_news_text_view");
    }
    /*private UiSelector allNewsButSelector() {
        return (new UiSelector().text("ALL NEWS").fromParent(new UiSelector().resourceId("container_list_news_include_on_fragment_main")));
    }*/

    public UiObject2 mainPageName() {
        return device.findObject(mainPageNameSelector());
    }

    public UiObject newsHeader() {
        return device.findObject(newsHeaderSelector());
    }

    public UiObject claimsHear() {
        return device.findObject(claimsHearSelector());
    }

    public UiObject2 newsBlock() {
        return device.findObject(newsBlockSelector());
    }

    public UiObject2 claimsBlock() {
        return device.findObject(claimsBlockSelector());
    }

    //public UiObject2 allNewsBut () { return getUiObject(allNewsButSelector()); }

    public UiObject2 allNewsBut () { return device.wait(Until.findObject(allNewsButSelector()), 10000); }


    private UiObject2 getUiObject(BySelector selector) {
        return device.wait(Until.findObject(selector), 10000);}

}
