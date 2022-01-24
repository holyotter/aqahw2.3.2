package ru.netology.web;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
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
        FakeGenerator.setUpAll(user);
        $("span[data-test-id='login'] input").setValue(user.getLogin());
        $("span[data-test-id='password'] input").setValue(user.getPassword());
        $("button[data-test-id='action-login']").click();
        $("h2.heading.heading_size_l.heading_theme_alfa-on-white").shouldHave(text("Личный кабинет"));
    }

    @Test
    void validLogPassBlocked() {
        val user = FakeGenerator.dataName("blocked");
        FakeGenerator.setUpAll(user);
        $("span[data-test-id='login'] input").setValue(user.getLogin());
        $("span[data-test-id='password'] input").setValue(user.getPassword());
        $("button[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void noValidLogValidPassActive() {
        val user = FakeGenerator.dataName("active");
        FakeGenerator.setUpAll(user);
        $("span[data-test-id='login'] input").setValue(FakeGenerator.noValidLog());
        $("span[data-test-id='password'] input").setValue(user.getPassword());
        $("button[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Неверно указан логин или пароль"));
    }
    @Test
    void validLogNoValidPassActive() {
        val user = FakeGenerator.dataName("active");
        FakeGenerator.setUpAll(user);
        $("span[data-test-id='login'] input").setValue(user.getLogin());
        $("span[data-test-id='password'] input").setValue(FakeGenerator.noValidPass());
        $("button[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Неверно указан логин или пароль"));
    }
}
