package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.Spend;
import guru.qa.niffler.jupiter.extension.GenerateCategoryExtension;
import guru.qa.niffler.jupiter.extension.SpendExtension;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.web.pages.MainPage;
import guru.qa.niffler.web.pages.PreLoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import guru.qa.niffler.web.pages.LoginPage;

@ExtendWith({GenerateCategoryExtension.class,
        SpendExtension.class,
})
public class SpendingTest {
    PreLoginPage plp = new PreLoginPage();
    LoginPage lp = new LoginPage();
    MainPage mp = new MainPage();

    static {
        Configuration.browserSize = "1920x1080";
    }

    @Test
    void anotherTest() {
        Selenide.open("http://127.0.0.1:3000/");
        $("a[href*='redirect']").should(visible);
    }

    @GenerateCategory(
            username = "spoon",
            category = "Расходы на кота"
    )
    @Spend(
            username = "spoon",
            description = "QA.GURU Advanced 5",
            amount = 65000.00,
            currency = CurrencyValues.RUB,
            category = "Расходы на кота"
    )
    @Test
    void spendingShouldBeDeletedAfterTableAction(SpendJson spendJson) {
        Selenide.open("http://127.0.0.1:3000/");
        plp
                .loginButtonClick();
        lp
                .setUserName("spoon")
                .setPassword("12345")
                .signIn();
        mp
                .scrollToSpending()
                .selectFirstRowWithSpendingByText(spendJson.description())
                .deleteSelectedButtonClick()
                .spendingsShouldHaveSizeNull();
    }

}
