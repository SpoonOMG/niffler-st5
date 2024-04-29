package guru.qa.niffler.web.components;

import com.codeborne.selenide.SelenideElement;

import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class AddNewSpenfingForm {
    private SelenideElement
            categoryList = $("#react-select-3-input"),
            inputAmount = $("[name=amount]"),
            inputSpendDate= $(".react-datepicker-ignore-onclickoutside"),
            inputSpendingDescription = $("[name=description]"),
            addNewSpendingButton = $("[type=submit]");

    public AddNewSpenfingForm addNewSpending(String SpendingCategory, String amount, String spendDate, String spendingDescription){
        categoryList.selectOptionContainingText(SpendingCategory);
        inputAmount.setValue(amount);
        inputSpendDate.setValue(spendDate);
        inputSpendingDescription.setValue(spendingDescription);
        addNewSpendingButton.click();

        return this;
    }



}
