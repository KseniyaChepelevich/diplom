package ru.iteco.fmhandroid.ui;

import static androidx.test.InstrumentationRegistry.getContext;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import androidx.test.espresso.PerformException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;


import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import ru.iteco.fmhandroid.api.NewsApi;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.FilterNewsPageSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;
import ru.iteco.fmhandroid.ui.steps.NewsPageSteps;

@RunWith(AllureAndroidJUnit4.class)
public class NewsFilterMockTest {

    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();
    private static NewsPageSteps newsPageSteps = new NewsPageSteps();
    private static FilterNewsPageSteps filterNewsPageSteps = new FilterNewsPageSteps();
    private static ControlPanelSteps controlPanelSteps = new ControlPanelSteps();

    @Before
    public void logoutCheck() {
        try {
            authSteps.isAuthScreen();
        } catch (PerformException e) {
            mainPageSteps.clickLogOutBut();
        }
        authSteps.authWithValidData(authInfo());
        mainPageSteps.isMainPage();
        mainPageSteps.openNewsPageThroughTheMainMenu();
    }

    @Test
    @DisplayName("Фильтрация новостей по Категории Обьявление")
    public void shouldFilterTheNewsWithCategoryAnnouncement() throws InterruptedException, IOException {
        // mock_response.json is placed on 'androidTest/res/'
        final InputStream stream = getContext().getAssets().open("mock_response.json");

        OkHttpClient httpClient = new OkHttpClient();
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return new Response.Builder()
                        .protocol(Protocol.HTTP_2)
                        // This is essential as it makes response.isSuccessful() returning true.
                        .code(200)
                        .request(chain.request())
                        .body(new ResponseBody() {
                            @Override
                            public MediaType contentType() {
                                return null;
                            }

                            @Override
                            public long contentLength() {
                                // Means we don't know the length beforehand.
                                return -1;
                            }

                            @Override
                            public BufferedSource source() {
                                try {
                                    return new Buffer().readFrom(stream);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    return null;
                                }
                            }
                        })
                        .build();
            }
        });

        /*NewsApi api = new NewsApi(httpClient);
        api.getAllNews(List<News>);*/

        // TODO: Let's assert the data here.

        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(controlPanelSteps.categoryAnnouncement, 0, -1, 0, 0, 0, 0);
        filterNewsPageSteps.filterNewsButtonClick();

        //Проверка, что отображаются новости с категорией Объявление
        newsPageSteps.isNewsPage();
        controlPanelSteps.scrollToElementInRecyclerList("Объявление 8572faec-a230-4fe2-a4cf-90531bf613b5").check(matches(isDisplayed()));
    }
}
