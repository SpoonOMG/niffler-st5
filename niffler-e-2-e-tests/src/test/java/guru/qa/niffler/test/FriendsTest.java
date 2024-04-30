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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.annotation.User.Selector.INVITATION_SENT;
import static guru.qa.niffler.jupiter.annotation.User.Selector.WITH_FRIENDS;
import static guru.qa.niffler.model.UserJson.simpleUser;

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

//    @Test
//    void invitationSendTest(){
//        Selenide.open("http://127.0.0.1:3000/");
//        welcomePage.clickLoginButton();
//        loginPage.setUserName("spoon")
//                .setPassword("12345")
//                .signIn();
//        mainPage.clickAllPeopleButton();
//        allPeoplePage.isFriends("spoonomg");
//    }
//
//    @Test
//    void invitationRecievedTest(){
//        Selenide.open("http://127.0.0.1:3000/");
//        welcomePage.clickLoginButton();
//        loginPage.setUserName("spoon")
//                .setPassword("12345")
//                .signIn();
//        mainPage.clickAllPeopleButton();
//        allPeoplePage.isFriends("spoonomg");
//    }
//
//    @Test
//    void friendsTest(){
//        Selenide.open("http://127.0.0.1:3000/");
//        welcomePage.clickLoginButton();
//        loginPage.setUserName("spoon")
//                .setPassword("12345")
//                .signIn();
//        mainPage.clickAllPeopleButton();
//        allPeoplePage.isFriends("spoonomg");
//    }

    @Test
    void test1(@User (selector = WITH_FRIENDS ) UserJson userForTest,
              @User (selector = WITH_FRIENDS) UserJson userFromTest){
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage.clickLoginButton();
        loginPage.setUserName(userForTest.username())
                .setPassword("12345")
                .signIn();
        mainPage.clickAllPeopleButton();
        allPeoplePage.isFriends(userFromTest.username());
    }

    @Test
    void test2(@User (selector = WITH_FRIENDS ) UserJson userForTest,
              @User (selector = WITH_FRIENDS) UserJson userFromTest){
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage.clickLoginButton();
        loginPage.setUserName(userForTest.username())
                .setPassword("12345")
                .signIn();
        mainPage.clickAllPeopleButton();
        allPeoplePage.isFriends(userFromTest.username());
    }
}
