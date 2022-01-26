package ru.netology.web;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class AuthTest {

    @BeforeEach
    void before() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    void validLogPassActive() {
        val user = FakeGenerator.dataName("active");
        FakeGenerator.sendRequest(user);
        $("span[data-test-id='login'] input").setValue(user.getLogin());
        $("span[data-test-id='password'] input").setValue(user.getPassword());
        $("button[data-test-id='action-login']").click();
        $(byText("Личный кабинет")).shouldBe(visible, Duration.ofSeconds(3));
    }

    @Test
    void validLogPassBlocked() {
        val user = FakeGenerator.dataName("blocked");
        FakeGenerator.sendRequest(user);
        $("span[data-test-id='login'] input").setValue(user.getLogin());
        $("span[data-test-id='password'] input").setValue(user.getPassword());
        $("button[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void noValidLogValidPassActive() {
        val user = FakeGenerator.dataName("active");
        FakeGenerator.sendRequest(user);
        $("span[data-test-id='login'] input").setValue(FakeGenerator.noValidLog());
        $("span[data-test-id='password'] input").setValue(user.getPassword());
        $("button[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Неверно указан логин или пароль"));
    }
    @Test
    void validLogNoValidPassActive() {
        val user = FakeGenerator.dataName("active");
        FakeGenerator.sendRequest(user);
        $("span[data-test-id='login'] input").setValue(user.getLogin());
        $("span[data-test-id='password'] input").setValue(FakeGenerator.noValidPass());
        $("button[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Неверно указан логин или пароль"));
    }
}
