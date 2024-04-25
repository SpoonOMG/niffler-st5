package guru.qa.niffler.web.pages;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.web.components.AddNewSpenfingForm;
import guru.qa.niffler.web.components.HistoryOfSpendingComponent;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {

    private final String header_title = "Welcome to Niffler. The coin keeper";
    private AddNewSpenfingForm addNewSpenfingForm = new AddNewSpenfingForm();
    private HistoryOfSpendingComponent historyOfSpendingComponent = new HistoryOfSpendingComponent();

    private SelenideElement
            mainPageButton = $x("//*[@class=\"header__people\"][@src=\"/images/home.svg\"]"),
            friendsButton = $x("//*[@class=\"header__people\"][@src=\"/images/friends.svg\"]"),
            allPeopleButton= $x("//*[@class=\"header__people\"][@src=\"/images/globe.svg\"]"),
            profileButton = $(".header__avatar"),
            logoutButton = $("[data-tooltip-id=logout]");

    public MainPage formHeaderCheck() {
        $("[class=header__title]").shouldHave(text(header_title));

        return this;
    }

    public MainPage clickMainPageButton() {
        mainPageButton.click();

        return this;
    }

    public MainPage clickFriendsButton() {
        friendsButton.click();

        return this;
    }

    public MainPage allPeopleButtonClick() {
        allPeopleButton.click();

        return this;
    }

    public MainPage profileButtonClick() {
        profileButton.click();

        return this;
    }

    public MainPage logoutButtonClick() {
        logoutButton.click();

        return this;
    }

    public MainPage addNewSpending(String SpendingCategory, String amount, String spendDate, String spendingDescription){
        addNewSpenfingForm.addNewSpending(SpendingCategory,amount,spendDate, spendingDescription);
        return this;
    }
    public MainPage findRowWithSpendingByText(String text){
        historyOfSpendingComponent.findRowWithSpendingByText(text);

        return this;
    }
    public MainPage selectFirstRowWithSpendingByText(String text){
        historyOfSpendingComponent.selectRowWithSpendingByText(text);

        return this;
    }
    public MainPage scrollToSpending(){
        $("[class=spendings__content]").scrollIntoView(true);
        return this;
    }
    public MainPage deleteSelectedButtonClick(){
        historyOfSpendingComponent.deleteSelectedButtonClick();
        return this;
    }

    public MainPage spendingsShouldBeEmpty(){
        historyOfSpendingComponent.tableBodyShouldHaveEmpty();
        return this;
    }
}
