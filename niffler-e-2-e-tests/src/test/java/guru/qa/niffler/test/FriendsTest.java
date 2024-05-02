package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.extension.UserQueueExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.web.pages.AllPeoplePage;
import guru.qa.niffler.web.pages.LoginPage;
import guru.qa.niffler.web.pages.MainPage;
import guru.qa.niffler.web.pages.WelcomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.annotation.User.Selector.*;

@WebTest
@ExtendWith(UserQueueExtension.class)
public class FriendsTest {
    WelcomePage welcomePage = new WelcomePage();
    LoginPage loginPage = new LoginPage();
    MainPage mainPage = new MainPage();
    AllPeoplePage allPeoplePage = new AllPeoplePage();

    static {
        Configuration.browserSize = "1920x1080";
    }


    @Test
    @DisplayName("User is friend for spoonomg and recieved invite from spoonlol")
    void friendsAndRecievedTest(@User(selector = INVITATION_RECEIVED) UserJson userForTest,
               @User(selector = WITH_FRIENDS) UserJson userFromTest) {
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage.clickLoginButton();
        loginPage.setUserName("user")
                .setPassword("12345")
                .signIn();
        mainPage.clickAllPeopleButton();
        allPeoplePage.isFriends(userFromTest.username());
        allPeoplePage.isInvitationRecieved(userForTest.username());
    }

    @Test
    @DisplayName("User is friend for spoonomg and sent invite to spoonsht")
    void friendsAndSentTest(@User(selector = INVITATION_SENT) UserJson userForTest,
               @User(selector = WITH_FRIENDS) UserJson userFromTest) {
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage.clickLoginButton();
        loginPage.setUserName("user")
                .setPassword("12345")
                .signIn();
        mainPage.clickAllPeopleButton();
        allPeoplePage.isFriends(userFromTest.username());
        allPeoplePage.isPendingInvitation(userForTest.username());
    }

    @Test
    @DisplayName("user is friend with spoonomg")
    void friendsTest(@User(selector = WITH_FRIENDS) UserJson userForTest) {
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage.clickLoginButton();
        loginPage.setUserName("user")
                .setPassword("12345")
                .signIn();
        mainPage.clickAllPeopleButton();
        allPeoplePage.isFriends(userForTest.username());
    }

}
