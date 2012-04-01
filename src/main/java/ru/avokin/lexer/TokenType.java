package ru.avokin.lexer;

public class TokenType {

    private final String name;

    public TokenType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}