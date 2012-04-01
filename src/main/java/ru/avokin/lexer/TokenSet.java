package ru.avokin.lexer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TokenSet {

    private final Set<TokenType> elements;

    private TokenSet(Set<TokenType> elements) {
        this.elements = elements;
    }

    public static TokenSet create(TokenType... types) {
        List<TokenType> list = Arrays.asList(types);
        Set<TokenType> set = new HashSet<TokenType>(list);
        return new TokenSet(set);
    }


    public boolean contains(TokenType t) {
        //noinspection SimplifiableIfStatement
        if (t == null) return false;
        return elements.contains(t);
    }
}