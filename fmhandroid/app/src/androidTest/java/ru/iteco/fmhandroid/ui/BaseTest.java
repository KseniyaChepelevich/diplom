package ru.iteco.fmhandroid.ui;


import androidx.test.rule.ActivityTestRule;


import org.junit.Rule;
import org.junit.rules.RuleChain;

import io.qameta.allure.android.rules.LogcatRule;


public class BaseTest {

    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Rule
    public RuleChain ruleChain = RuleChain.outerRule(new io.qameta.allure.android.rules.ScreenshotRule()).around(new io.qameta.allure.android.rules.ScreenshotRule());

    @Rule
    public io.qameta.allure.android.rules.LogcatRule logcatRule = new LogcatRule();


}
