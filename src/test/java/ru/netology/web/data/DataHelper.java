package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Locale;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }


    @Value
    public static class VerificationCode {
        String code;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthCode {
        private String id;
        private String user_id;
        private String code;
        private String created;
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
    }

    public static CardInfo firstCard() {
        return new CardInfo("5559 0000 0000 0001");
    }

    public static CardInfo secondCard() {
        return new CardInfo("5559 0000 0000 0002");
    }

    @Value
    public static class TransferData {
        private String from;
        private String to;
        private int amount;
    }

    public static TransferData transfer(String from, String to, int amount) {
        return new TransferData(from, to, amount);
    }


}
