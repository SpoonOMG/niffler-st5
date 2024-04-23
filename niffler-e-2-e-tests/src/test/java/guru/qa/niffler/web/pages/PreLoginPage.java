package guru.qa.niffler.web.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class PreLoginPage {
    private final String main_header = "Welcome to magic journey with Niffler. The coin keeper";
    private SelenideElement
            loginButton= $("a[href*='redirect']"),
            registerButton= $("a[href*='http://127.0.0.1:9000/register']");

    public PreLoginPage loginButtonClick(){
        loginButton.click();
        return this;
    }

    public PreLoginPage registerButtonClick(){
        registerButton.click();
        return this;
    }



}
