package guru.qa.niffler.web.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class AllPeoplePage {


    private final ElementsCollection
            tableRows = $(".abstract-table tbody").$$("tr");

    private SelenideElement findUserByRow (String username) {
        return tableRows.find((text(username)));
    }

    public void pendingIvitationCheck(String username){
        findUserByRow(username).lastChild().$(".abstract-table__buttons").shouldHave(text("Pending invitation"));
    }

    public void invitationRecievedCheck(String username){
        findUserByRow(username).lastChild().$(".abstract-table__buttons div").shouldHave(attribute("data-tooltip-id","submit-invitation"));
    }

    public void friendsCheck(String username){
        findUserByRow(username).lastChild().$(".abstract-table__buttons").shouldHave(text("You are friends"));
    }
}
