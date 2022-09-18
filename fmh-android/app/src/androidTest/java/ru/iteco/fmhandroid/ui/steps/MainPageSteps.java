package ru.iteco.fmhandroid.ui.steps;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;

import ru.iteco.fmhandroid.ui.page.MainPage;

public class MainPageSteps {

    private UiDevice device;
    public static final String packageName = "ru.iteco.fmhandroid";
    private static final int TIMEOUT = 5000;
    private MainPage mainPage;

    public void isMainPage() throws UiObjectNotFoundException {
        mainPage.newsHeader().getText();
        mainPage.claimsHear().isEnabled();
        mainPage.newsBlock().isEnabled();
        mainPage.claimsBlock().isEnabled();
    }
}
