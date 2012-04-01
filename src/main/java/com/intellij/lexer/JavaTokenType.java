/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.lexer;

import ru.avokin.lexer.TokenSet;
import ru.avokin.lexer.TokenType;


@SuppressWarnings({"JavaDoc"})
public interface JavaTokenType {
   
    /**
   * Token type for a sequence of whitespace characters.
   */
  TokenType WHITE_SPACE = new TokenType("WHITE_SPACE");

  /**
   * Token type for a character which is not valid in the position where it was encountered,
   * according to the language grammar.
   */
  TokenType BAD_CHARACTER = new TokenType("BAD_CHARACTER");

  //IElementType WHITE_SPACE = new IElementType("WHITE_SPACE");
  TokenType IDENTIFIER = new TokenType("IDENTIFIER");
  TokenType C_STYLE_COMMENT = new TokenType("C_STYLE_COMMENT");
  TokenType END_OF_LINE_COMMENT = new TokenType("END_OF_LINE_COMMENT");
  TokenType DOC_COMMENT = new TokenType("DOC_COMMENT");

  TokenType INTEGER_LITERAL = new TokenType("INTEGER_LITERAL");
  TokenType LONG_LITERAL = new TokenType("LONG_LITERAL");
  TokenType FLOAT_LITERAL = new TokenType("FLOAT_LITERAL");
  TokenType DOUBLE_LITERAL = new TokenType("DOUBLE_LITERAL");
  TokenType CHARACTER_LITERAL = new TokenType("CHARACTER_LITERAL");
  TokenType STRING_LITERAL = new TokenType("STRING_LITERAL");


  TokenType TRUE_KEYWORD = new TokenType("TRUE_KEYWORD");
  TokenType NULL_KEYWORD = new TokenType("NULL_KEYWORD");

  TokenType ABSTRACT_KEYWORD = new TokenType("ABSTRACT_KEYWORD");
  TokenType ASSERT_KEYWORD = new TokenType("ASSERT_KEYWORD");
  TokenType BOOLEAN_KEYWORD = new TokenType("BOOLEAN_KEYWORD");
  TokenType BREAK_KEYWORD = new TokenType("BREAK_KEYWORD");
  TokenType BYTE_KEYWORD = new TokenType("BYTE_KEYWORD");
  TokenType CASE_KEYWORD = new TokenType("CASE_KEYWORD");
  TokenType CATCH_KEYWORD = new TokenType("CATCH_KEYWORD");
  TokenType CHAR_KEYWORD = new TokenType("CHAR_KEYWORD");
  TokenType CLASS_KEYWORD = new TokenType("CLASS_KEYWORD");
  TokenType CONST_KEYWORD = new TokenType("CONST_KEYWORD");
  TokenType CONTINUE_KEYWORD = new TokenType("CONTINUE_KEYWORD");
  TokenType DEFAULT_KEYWORD = new TokenType("DEFAULT_KEYWORD");
  TokenType DO_KEYWORD = new TokenType("DO_KEYWORD");
  TokenType DOUBLE_KEYWORD = new TokenType("DOUBLE_KEYWORD");
  TokenType ELSE_KEYWORD = new TokenType("ELSE_KEYWORD");
  TokenType ENUM_KEYWORD = new TokenType("ENUM_KEYWORD");
  TokenType EXTENDS_KEYWORD = new TokenType("EXTENDS_KEYWORD");
  TokenType FINAL_KEYWORD = new TokenType("FINAL_KEYWORD");
  TokenType FINALLY_KEYWORD = new TokenType("FINALLY_KEYWORD");
  TokenType FLOAT_KEYWORD = new TokenType("FLOAT_KEYWORD");
  TokenType FOR_KEYWORD = new TokenType("FOR_KEYWORD");
  TokenType GOTO_KEYWORD = new TokenType("GOTO_KEYWORD");
  TokenType IF_KEYWORD = new TokenType("IF_KEYWORD");
  TokenType IMPLEMENTS_KEYWORD = new TokenType("IMPLEMENTS_KEYWORD");
  TokenType IMPORT_KEYWORD = new TokenType("IMPORT_KEYWORD");
  TokenType INSTANCEOF_KEYWORD = new TokenType("INSTANCEOF_KEYWORD");
  TokenType INT_KEYWORD = new TokenType("INT_KEYWORD");
  TokenType INTERFACE_KEYWORD = new TokenType("INTERFACE_KEYWORD");
  TokenType LONG_KEYWORD = new TokenType("LONG_KEYWORD");
  TokenType NATIVE_KEYWORD = new TokenType("NATIVE_KEYWORD");
  TokenType NEW_KEYWORD = new TokenType("NEW_KEYWORD");
  TokenType PACKAGE_KEYWORD = new TokenType("PACKAGE_KEYWORD");
  TokenType PRIVATE_KEYWORD = new TokenType("PRIVATE_KEYWORD");
  TokenType PUBLIC_KEYWORD = new TokenType("PUBLIC_KEYWORD");
  TokenType SHORT_KEYWORD = new TokenType("SHORT_KEYWORD");
  TokenType SUPER_KEYWORD = new TokenType("SUPER_KEYWORD");
  TokenType SWITCH_KEYWORD = new TokenType("SWITCH_KEYWORD");
  TokenType SYNCHRONIZED_KEYWORD = new TokenType("SYNCHRONIZED_KEYWORD");
  TokenType THIS_KEYWORD = new TokenType("THIS_KEYWORD");
  TokenType THROW_KEYWORD = new TokenType("THROW_KEYWORD");
  TokenType PROTECTED_KEYWORD = new TokenType("PROTECTED_KEYWORD");
  TokenType TRANSIENT_KEYWORD = new TokenType("TRANSIENT_KEYWORD");
  TokenType RETURN_KEYWORD = new TokenType("RETURN_KEYWORD");
  TokenType VOID_KEYWORD = new TokenType("VOID_KEYWORD");
  TokenType STATIC_KEYWORD = new TokenType("STATIC_KEYWORD");
  TokenType STRICTFP_KEYWORD = new TokenType("STRICTFP_KEYWORD");
  TokenType WHILE_KEYWORD = new TokenType("WHILE_KEYWORD");
  TokenType TRY_KEYWORD = new TokenType("TRY_KEYWORD");
  TokenType VOLATILE_KEYWORD = new TokenType("VOLATILE_KEYWORD");
  TokenType THROWS_KEYWORD = new TokenType("THROWS_KEYWORD");

  TokenType LPARENTH = new TokenType("LPARENTH");
  TokenType RPARENTH = new TokenType("RPARENTH");
  TokenType LBRACE = new TokenType("LBRACE");
  TokenType RBRACE = new TokenType("RBRACE");
  TokenType LBRACKET = new TokenType("LBRACKET");
  TokenType RBRACKET = new TokenType("RBRACKET");
  TokenType SEMICOLON = new TokenType("SEMICOLON");
  TokenType COMMA = new TokenType("COMMA");
  TokenType DOT = new TokenType("DOT");
  TokenType ELLIPSIS = new TokenType("ELLIPSIS");
  TokenType AT = new TokenType("AT");

  TokenType EQ = new TokenType("EQ");
  TokenType GT = new TokenType("GT");
  TokenType LT = new TokenType("LT");
  TokenType EXCL = new TokenType("EXCL");
  TokenType TILDE = new TokenType("TILDE");
  TokenType QUEST = new TokenType("QUEST");
  TokenType COLON = new TokenType("COLON");
  TokenType PLUS = new TokenType("PLUS");
  TokenType MINUS = new TokenType("MINUS");
  TokenType ASTERISK = new TokenType("ASTERISK");
  TokenType DIV = new TokenType("DIV");
  TokenType AND = new TokenType("AND");
  TokenType OR = new TokenType("OR");
  TokenType XOR = new TokenType("XOR");
  TokenType PERC = new TokenType("PERC");

  TokenType EQEQ = new TokenType("EQEQ");
  TokenType LE = new TokenType("LE");
  TokenType GE = new TokenType("GE");
  TokenType NE = new TokenType("NE");
  TokenType ANDAND = new TokenType("ANDAND");
  TokenType OROR = new TokenType("OROR");
  TokenType PLUSPLUS = new TokenType("PLUSPLUS");
  TokenType MINUSMINUS = new TokenType("MINUSMINUS");
  TokenType LTLT = new TokenType("LTLT");
  TokenType GTGT = new TokenType("GTGT");
  TokenType GTGTGT = new TokenType("GTGTGT");
  TokenType PLUSEQ = new TokenType("PLUSEQ");
  TokenType MINUSEQ = new TokenType("MINUSEQ");
  TokenType ASTERISKEQ = new TokenType("ASTERISKEQ");
  TokenType DIVEQ = new TokenType("DIVEQ");
  TokenType ANDEQ = new TokenType("ANDEQ");
  TokenType OREQ = new TokenType("OREQ");
  TokenType XOREQ = new TokenType("XOREQ");
  TokenType PERCEQ = new TokenType("PERCEQ");
  TokenType LTLTEQ = new TokenType("LTLTEQ");
  TokenType GTGTEQ = new TokenType("GTGTEQ");
  TokenType GTGTGTEQ = new TokenType("GTGTGTEQ");

  TokenType FALSE_KEYWORD = new TokenType("FALSE_KEYWORD");

  TokenSet OPERATION_BIT_SET = TokenSet.create(
    EQ, GT, LT, EXCL, TILDE, QUEST, COLON, PLUS, MINUS, ASTERISK, DIV, AND, OR, XOR,
    PERC, EQEQ, LE, GE, NE, ANDAND, OROR, PLUSPLUS, MINUSMINUS, LTLT, GTGT, GTGTGT,
    PLUSEQ, MINUSEQ, ASTERISKEQ, DIVEQ, ANDEQ, OREQ, XOREQ, PERCEQ, LTLTEQ, GTGTEQ, GTGTGTEQ
  );

  TokenSet KEYWORD_BIT_SET = TokenSet.create(
    ABSTRACT_KEYWORD, ASSERT_KEYWORD, BOOLEAN_KEYWORD, BREAK_KEYWORD, BYTE_KEYWORD, CASE_KEYWORD,
    CATCH_KEYWORD, CHAR_KEYWORD, CLASS_KEYWORD, CONST_KEYWORD, CONTINUE_KEYWORD,
    DEFAULT_KEYWORD, DO_KEYWORD, DOUBLE_KEYWORD, ELSE_KEYWORD, ENUM_KEYWORD, EXTENDS_KEYWORD,
    FINAL_KEYWORD, FINALLY_KEYWORD, FLOAT_KEYWORD, FOR_KEYWORD, GOTO_KEYWORD,
    IF_KEYWORD, IMPLEMENTS_KEYWORD, IMPORT_KEYWORD, INSTANCEOF_KEYWORD, INT_KEYWORD,
    INTERFACE_KEYWORD, LONG_KEYWORD, NATIVE_KEYWORD, NEW_KEYWORD, PACKAGE_KEYWORD,
    PRIVATE_KEYWORD, PROTECTED_KEYWORD, PUBLIC_KEYWORD, RETURN_KEYWORD, SHORT_KEYWORD,
    SUPER_KEYWORD, STATIC_KEYWORD, STRICTFP_KEYWORD, SWITCH_KEYWORD, SYNCHRONIZED_KEYWORD,
    THIS_KEYWORD, THROW_KEYWORD, THROWS_KEYWORD, TRANSIENT_KEYWORD, TRY_KEYWORD,
    VOID_KEYWORD, VOLATILE_KEYWORD, WHILE_KEYWORD, TRUE_KEYWORD, FALSE_KEYWORD, NULL_KEYWORD
  );

  TokenSet MODIFIER_BIT_SET = TokenSet.create(
    PUBLIC_KEYWORD, PROTECTED_KEYWORD, PRIVATE_KEYWORD, STATIC_KEYWORD,
    ABSTRACT_KEYWORD, FINAL_KEYWORD, NATIVE_KEYWORD, STRICTFP_KEYWORD,
    SYNCHRONIZED_KEYWORD, TRANSIENT_KEYWORD, VOLATILE_KEYWORD
  );

  TokenSet WHITE_SPACE_OR_COMMENT_BIT_SET = TokenSet.create(
    WHITE_SPACE, END_OF_LINE_COMMENT, C_STYLE_COMMENT, DOC_COMMENT
  );

  TokenSet WHITESPACE_BIT_SET = TokenSet.create(
    WHITE_SPACE
  );

  TokenSet COMMENT_BIT_SET = TokenSet.create(
    END_OF_LINE_COMMENT, C_STYLE_COMMENT, DOC_COMMENT/*, JavaDocTokenType.DOC_COMMENT_DATA*/
  );

  TokenSet STRING_LITERAL_SET = TokenSet.create(STRING_LITERAL, CHARACTER_LITERAL);

    TokenSet NUMBER_LITERAL_SET = TokenSet.create(INTEGER_LITERAL, LONG_LITERAL, FLOAT_LITERAL, DOUBLE_LITERAL);
}
