package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

public class MainPage {
    public final String packageName;
    private UiDevice device;

    /*public MainPage(UiDevice device, String packageName) {
        this.device = device;
        this.packageName = packageName;
    }*/

    public MainPage(String packageName) throws UiObjectNotFoundException {
        this.packageName = packageName;
        this.device = device;
    }

    private UiSelector mainPageNameSelector() {
        return (new UiSelector().resourceId("main_swipe_refresh"));
    }

    private UiSelector newsHeaderSelector() {
        return (new UiSelector().text("News").fromParent(new UiSelector().className("android.view.ViewGroup").index(0).fromParent(new UiSelector().className("android.widget.LinearLayout").fromParent(new UiSelector().className("android.widget.LinearLayout").fromParent(new UiSelector().className("android.widget.ScrollView"))))));
    }

    /*private UiSelector newsHeaderSelector() {
        return (new UiSelector().className("android.widget.ScrollView").childSelector(new UiSelector().className("android.widget.LinearLayout").childSelector(new UiSelector().className("android.widget.LinearLayout").childSelector(new UiSelector().className("android.view.ViewGroup").index(0).childSelector(new UiSelector().text("News"))))));
    }*/

    private UiSelector claimsHearSelector() {
        return (new UiSelector().className("android.widget.TextView").text("Claims"));

    }

    private UiSelector newsBlockSelector() {
        return (new UiSelector().className("android.widget.TextView").resourceId("ru.iteco.fmhandroid:id/container_list_news_include_on_fragment_main"));
    }

    private BySelector claimsBlockSelector() {
        return By.res(packageName, "ru.iteco.fmhandroid:id/container_list_claim_include_on_fragment_main");
    }

    /*private BySelector allNewsButSelector() {
        return By.res(packageName, "ru.iteco.fmhandroid:id/all_news_text_view");
    }*/

    /*UiScrollable scrollView = new UiScrollable(new UiSelector().scrollable(true));
    UiObject newsBlock = scrollView.getChild(new UiSelector().className("android.widget.LinearLayout")).getChild(new UiSelector().resourceId("ru.iteco.fmhandroid:id/container_list_news_include_on_fragment_main"));

    public UiObject allNewsBut() throws UiObjectNotFoundException { return newsBlock.getChild(new UiSelector().resourceId("ALL NEWS")); }*/


    /*private UiSelector allNewsButSelector() {
        return (new UiSelector().className("android.widget.ScrollView").childSelector(new UiSelector().className("android.widget.LinearLayout")).childSelector(new UiSelector().className("android.widget.LinearLayout").index(0)).childSelector(new UiSelector().resourceId("ru.iteco.fmhandroid:id/all_news_text_view").textContains("ALL NEWS")));
    }*/
    /*private UiSelector allNewsButSelector() {

        return (new UiSelector().className("android.widget.RelativeLayout").fromParent(new UiSelector().fromParent(new UiSelector().fromParent(new UiSelector().fromParent(new UiSelector().resourceId("ru.iteco.fmhandroid:id/all_news_text_view"))))));
    }*/
    /*private UiSelector allNewsButSelector() {

        return (new UiSelector().className("android.widget.LinearLayout").index(0).fromParent(new UiSelector().resourceId("ru.iteco.fmhandroid:id/all_news_text_view")));
    }*/

     UiSelector allNewsButSelector() {

        return (new UiSelector().resourceId("all_news_text_view"));
    }

    //public UiObject





    private BySelector allClaimsButSelector() {

        return (By.res(packageName,"ru.iteco.fmhandroid:id/all_claims_text_view")); }

    private BySelector navigationDrawerSelector() {
        return (By.res(packageName, "ru.iteco.fmhandroid:id/main_menu_image_button"));
    }



    public UiObject mainPageName() {
        return device.findObject(mainPageNameSelector());
    }

    public UiObject newsHeader() {
        return device.findObject(newsHeaderSelector());
    }

    public UiObject claimsHear() {
        return device.findObject(claimsHearSelector());
    }

    public UiObject newsBlock() {
        return device.findObject(newsBlockSelector());
    }

    public UiObject2 claimsBlock() {
        return device.findObject(claimsBlockSelector());
    }

    public UiObject allNewsBut () { return device.findObject(allNewsButSelector()); }

        public UiObject2 allClaimsBut () { return device.findObject(allClaimsButSelector()); }

    //public UiObject allNewsBut () { return device.wait(Until.findObject(allNewsButSelector()), 10000); }
    //public UiObject2 allNewsBut () {return device.wait(Until.findObject(allNewsButSelector()), 10000);}

    public UiObject2 navigationDrawer() { return device.findObject(navigationDrawerSelector());}
    private UiObject2 getUiObject(BySelector selector) {
        return device.wait(Until.findObject(selector), 10000);}

}
