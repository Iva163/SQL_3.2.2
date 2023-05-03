package ru.netology.web.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;

import static ru.netology.web.api.APIHelper.*;
import static ru.netology.web.data.DataHelper.firstCard;
import static ru.netology.web.data.DataHelper.secondCard;
import static ru.netology.web.data.SQLHelper.*;

public class TestTransfer {

    @AfterAll
    static void teardown() {
        cleanDatabase();
    }

    @Test
    void shouldSuccessfulTransfer() {
        var userInfo = DataHelper.getAuthInfo();
        validLogin(userInfo);
        var token = getToken(userInfo, getVerificationCode().getCode());
        var balanceToFirstCard = getCardBalance(firstCard());
        var balanceToSecondCard = getCardBalance(secondCard());
        var amount = 5000;
        transferCard(token, firstCard().getCardNumber(), secondCard().getCardNumber(), amount);
        var balanceAfterFirstCard = getCardBalance(firstCard());
        var balanceAfterSecondCard = getCardBalance(secondCard());
        var amountInKopecks = amount * 100;
        Assertions.assertEquals(balanceAfterFirstCard, balanceToFirstCard - amountInKopecks);
        Assertions.assertEquals(balanceAfterSecondCard, balanceToSecondCard + amountInKopecks);


    }

}
