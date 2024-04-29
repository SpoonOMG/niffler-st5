package guru.qa.niffler.web.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final String form_header = "Welcome to Niffler. The coin keeper";

    private SelenideElement
            userNameInput = $("[name=username]"),
            passwordInput = $("[name=password]"),
            signInButton = $("[type=submit]"),
            signUpButton = $(byText("Sign up!"));

    public LoginPage formHeaderCheck(){
        $("[class=form__header]").shouldHave(text(form_header));

        return this;
    }

    public LoginPage setUserName(String value) {
        userNameInput.setValue(value);

        return this;
    }

    public LoginPage setPassword(String value) {
        passwordInput.setValue(value);

        return this;
    }

    public LoginPage signIn() {
        signInButton.click();

        return this;
    }

    public LoginPage signUp() {
        signUpButton.click();

        return this;
    }
}




