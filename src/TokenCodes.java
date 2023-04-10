
/*
 * TokenCode enum which specifies the various types, reserved words, and characters that can be found
 * are legal in the Pascal coding language.
 */

public enum TokenCodes {

    PLUS, MINUS, TIMES, SLASH, EQL, LPAREN, RPAREN, PERIOD, COMMA, SEMICOLON,
    COLON,NEQ, ASSIGN_OP ,LSS,LEQ,GTR,GEQ,

    AND, ARRAY,	BEGIN, CASE, CONST, DIV, DO, DOWNTO, ELSE, END, FILE,
    FOR, FUNCTION, GOTO, IF, IN, LABEL, MOD, NIL, NOT, OF, OR,PACKED,
    PROCEDURE,PROGRAM, RECORD, REPEAT, SET, THEN, TO, TYPE, UNTIL, VAR,
    WHILE, WITH, NULL, NUMLIT, IDENT, NOTALEX, EOF, DEQL, UNKNOWN, WRITESYM, INTSYM,
    READSYM, QUOTE,SINGQUO,EMPTYLINE,SPACE
}