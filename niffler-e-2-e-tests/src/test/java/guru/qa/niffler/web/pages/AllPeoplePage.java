package guru.qa.niffler.web.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class AllPeoplePage {


    final ElementsCollection
            tableRows = $(".abstract-table tbody").$$("tr");

    public SelenideElement findUserByRow (String username) {
        return tableRows.find((text(username)));
    }

    public boolean isPendingInvitation(String username){
        findUserByRow(username).lastChild().$(".abstract-table__buttons").shouldHave(text("Pending invitation"));
        return true;
    }

    public boolean isInvitationRecieved(String username){
        findUserByRow(username).lastChild().$(".abstract-table__buttons div").shouldHave(attribute("data-tooltip-id","submit-invitation"));
        return true;
    }

    public boolean isFriends(String username){
        findUserByRow(username).lastChild().$(".abstract-table__buttons").shouldHave(text("You are friends"));
        return true;
    }
}
