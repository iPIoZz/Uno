package com.uno;

import com.uno.models.GameSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.UUID;

@SpringBootApplication
public class Main {

    private static Map<UUID, GameSession> games;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    public static Map<UUID, GameSession> getGames()
    {
        return games;
    }

}
