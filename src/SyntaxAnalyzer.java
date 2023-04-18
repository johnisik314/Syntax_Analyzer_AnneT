import java.beans.Expression;
import java.sql.SQLOutput;

public class SyntaxAnalyzer {
    LexicalAnalyzer la;

    public SyntaxAnalyzer(LexicalAnalyzer la){
        this.la = la;
        Accept(TokenCodes.PROGRAM);
        STATEMENTS();
        Accept(TokenCodes.EOF);
    }

    //this is the essential function which accepts token codes;
    //return error message if there is a syntax error
    //continue if there is no errors in the syntax
    public void Accept(TokenCodes token){
        Token currentToken = la.getNextToken();
        while(currentToken.tokenCode == TokenCodes.SPACE){
            currentToken = la.getNextToken();
        }
        if(currentToken.tokenCode == token){
            System.out.println("Good token: "+ currentToken.lexeme);
            if(currentToken.tokenCode == TokenCodes.EOF){
                System.out.println("End of file; file is syntactically correct");
            }
        }else{
            errorMessege();
            System.out.println("Syntax Error; \n   Expected lexeme: "+token+"\n   Current token: "+currentToken.tokenCode);
        }
    }

    //output of error in terminal if there is any found by Accept() method
    private void errorMessege() {
        System.out.println(la.lines.get(la.currentLine));
        for(int i=1; i<la.currentLocation;i++){
            System.out.print(" ");
        }
        System.out.println("^");
    }

    //accept comments and verify the syntax

    public void STATEMENTS(){
        STATEMENT();
        if(la.currentToken == TokenCodes.IDENT){
            STATEMENT();
        }
    }
    public void STATEMENT(){
        while (la.currentToken == TokenCodes.SPACE){
            la.getNextToken();
        }
        if(la.currentToken == TokenCodes.IDENT){
            DECLERATIONS();
        }else if (la.currentToken == TokenCodes.LPAREN){
            COMMENT();
        }else{
            FUNCTIONS();
        }
    }
    public void DECLERATIONS(){

    }
    public void COMMENT(){

    }
    public void FUNCTIONS(){
        if(la.currentToken == TokenCodes.IF){
            IF();
        }else if (la.currentToken == TokenCodes.WHILE){
            WHILE();
        }else if (la. currentToken == TokenCodes.VAR){
            Accept(TokenCodes.VAR);
        }else if (la. currentToken == TokenCodes.READSYM){
            READ();
        }else if (la. currentToken == TokenCodes.WRITESYM){
            WRITE();
        }else if (la. currentToken == TokenCodes.BEGIN){
            Accept(TokenCodes.READSYM);
        }
    }
    public void READ(){
        Accept(TokenCodes.READSYM);
        Accept(TokenCodes.LPAREN);
        STATEMENT();
        Accept(TokenCodes.RPAREN);
    }
    public void WRITE(){
        Accept(TokenCodes.WRITESYM);
        Accept(TokenCodes.LPAREN);
        STATEMENT();
        Accept(TokenCodes.RPAREN);
    }
    public void IF(){
        Accept(TokenCodes.IF);
        Accept(TokenCodes.LPAREN);
        BOOLEAN();
        Accept(TokenCodes.RPAREN);
        Accept(TokenCodes.THEN);
        STATEMENT();
        if (la.currentToken == TokenCodes.ELSE){
            Accept(TokenCodes.ELSE);
            STATEMENT();
        }
        Accept(TokenCodes.SEMICOLON);
    }
    public void WHILE(){
        Accept(TokenCodes.WHILE);
        Accept(TokenCodes.LPAREN);
        BOOLEAN();
        Accept(TokenCodes.RPAREN);
        Accept(TokenCodes.DO);
        STATEMENT();
    }
    public void BOOLEAN(){
        EXPRESSION();
        if(la.currentToken == TokenCodes.EQL){
            Accept(TokenCodes.EQL);
            EXPRESSION();
        }else if(la.currentToken == TokenCodes.DEQL){
            Accept(TokenCodes.DEQL);
            EXPRESSION();
        }else if(la.currentToken == TokenCodes.LSS){
            Accept(TokenCodes.LSS);
            EXPRESSION();
        }else if(la.currentToken == TokenCodes.LEQ){
            Accept(TokenCodes.LEQ);
            EXPRESSION();
        }else if(la.currentToken == TokenCodes.GTR){
            Accept(TokenCodes.GTR);
            EXPRESSION();
        }else if(la.currentToken == TokenCodes.GEQ) {
            Accept(TokenCodes.GEQ);
            EXPRESSION();
        }
    }
    public void EXPRESSION(){
        TERM();
        if (la.currentToken == TokenCodes.PLUS){
            Accept(TokenCodes.PLUS);
            TERM();
        }else if (la.currentToken == TokenCodes.MINUS){
            Accept(TokenCodes.MINUS);
            TERM();
        }else if (la.currentToken == TokenCodes.OR){
            Accept(TokenCodes.OR);
            TERM();
        }
    }
    public void TERM(){
        if(la.currentToken == TokenCodes.NOT ||
            la.currentToken == TokenCodes.IDENT ||
            la.currentToken == TokenCodes.NUMLIT||
            la.currentToken == TokenCodes.TRUE ||
            la.currentToken == TokenCodes.FALSE
        ){
            FACTOR();
        }else if(la.currentToken == TokenCodes.TIMES){
            Accept(TokenCodes.TIMES);
            FACTOR();
        }else if(la.currentToken == TokenCodes.SLASH){
            Accept(TokenCodes.SLASH);
            FACTOR();
        }else if(la.currentToken == TokenCodes.DIV){
            Accept(TokenCodes.DIV);
            FACTOR();
        }else if(la.currentToken == TokenCodes.MOD){
            Accept(TokenCodes.MOD);
            FACTOR();
        }else if(la.currentToken == TokenCodes.AND){
            Accept(TokenCodes.AND);
            FACTOR();
        }
    }
    public void FACTOR(){
        if (la.currentToken == TokenCodes.NOT){
            Accept(TokenCodes.NOT);
        }
        PRIMARY();
    }
    public void PRIMARY(){
        if(la.currentToken == TokenCodes.IDENT){
            Accept(TokenCodes.IDENT);
        }else if (la.currentToken ==TokenCodes.NUMLIT){
            Accept(TokenCodes.NUMLIT);
        }else if (la.currentToken ==TokenCodes.TRUE){
            Accept(TokenCodes.TRUE);
        }else if (la.currentToken ==TokenCodes.FALSE){
            Accept(TokenCodes.FALSE);
        }else if (la.currentToken ==TokenCodes.LPAREN){
            Accept(TokenCodes.LPAREN);
            BOOLEAN();
            Accept(TokenCodes.RPAREN);
        }
    }

}
