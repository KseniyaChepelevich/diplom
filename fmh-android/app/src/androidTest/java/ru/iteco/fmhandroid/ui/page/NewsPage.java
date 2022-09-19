package ru.iteco.fmhandroid.ui.page;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;

public class NewsPage {
    public final String packageName;
    private UiDevice device;

    public NewsPage(String packageName) {
        this.packageName = packageName;
    }

    private UiSelector newsPageHeaderSelector() {
        return (new UiSelector().className("android.widget.TextView").text("News"));
    }

    public UiObject newsPageHeader() {
        return device.findObject(newsPageHeaderSelector());
    }

}
