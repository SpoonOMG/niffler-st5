package guru.qa.niffler.web.components;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByText;
import guru.qa.niffler.web.pages.MainPage;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class HistoryOfSpendingComponent {

    private SelenideElement
            todayFilterButton = $(byText("Today")),
            lastWeekFilterButton = $(byText("Last week")),
            lastMonthFilterButton= $(byText("Last month")),
            allTimeFilterButton = $(byText("All time")),
            currencySelector = $("#react-select-15-live-region"),
            deleteSelectedButton = $(byText("Delete selected")),
            tableHeaderCheckbox = $("[type=checkbox][checked]"),
            tableBody=$(".spendings-table tbody"),
            firstRow =tableBody.$$("tr").first();


    public HistoryOfSpendingComponent todayFilterButtonClick() {
        todayFilterButton.click();

        return this;
    }
    public HistoryOfSpendingComponent lastWeekFilterButtonClick() {
        lastWeekFilterButton.click();

        return this;
    }

    public HistoryOfSpendingComponent lastMonthFilterButtonClick() {
        lastMonthFilterButton.click();

        return this;
    }
    public HistoryOfSpendingComponent allTimeFilterButtonClick() {
        allTimeFilterButton.click();

        return this;
    }
    public HistoryOfSpendingComponent deleteSelectedButtonClick() {
        deleteSelectedButton.click();

        return this;
    }
    public HistoryOfSpendingComponent tableHeaderCheckboxCLick() {
        tableHeaderCheckbox.click();

        return this;
    }
    public HistoryOfSpendingComponent currencySelectorSelect(String currency) {
        currencySelector.selectOptionContainingText(currency);

        return this;
    }
    public HistoryOfSpendingComponent findRowWithSpendingByText(String text){
        tableBody.$$("tr").find(text(text));
        return this;
    }

    public HistoryOfSpendingComponent selectRowWithSpendingByText(String text){
        tableBody.$$("tr").find(text(text)).$$("td").first().click();
        return this;
    }
    public HistoryOfSpendingComponent selectFirstRowInTable(){
        firstRow.click();
        return this;
    }
    public HistoryOfSpendingComponent scrollIntoViewTableBody(){
        tableBody.scrollIntoView(false);
        return this;
    }

    public HistoryOfSpendingComponent tableBodyShouldHaveSizeNull(){
        tableBody.$$("tr").shouldHave(size(0));
        return this;
    }
}
