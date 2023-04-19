import javax.swing.*;
import java.beans.Expression;
import java.sql.SQLOutput;

public class SyntaxAnalyzer {
    LexicalAnalyzer la;

    public SyntaxAnalyzer(LexicalAnalyzer la){
        this.la = la;
        Accept(TokenCodes.PROGRAM);
        Accept(TokenCodes.IDENT);
        Accept(TokenCodes.SEMICOLON);
        DECLERATION_PART();
        STATEMENT_PART();
        //Accept(TokenCodes.EOF);
    }

    //this is the essential function which accepts token codes;
    //return error message if there is a syntax error
    //continue if there is no errors in the syntax
    public void Accept(TokenCodes token){
        if(la.currentToken.tokenCode == token){
            System.out.println("Accepted token : "+ la.currentToken.lexeme + " ("+la.currentToken.tokenCode+")");
            if(la.currentToken.tokenCode == TokenCodes.EOF){
                System.out.println("End of file; file is syntactically correct");
            }
        }else{
            errorMessege();
            System.out.println("Syntax Error; \n   Expected lexeme: "+token+"\n   Current token: "+la.currentToken.tokenCode);
        }
        la.currentToken = la.getNextToken();
    }

    //output of error in terminal if there is any found by Accept() method
    private void errorMessege() {
        System.out.println(la.lines.get(la.currentLine));
        for(int i=1; i<la.currentLocation;i++){
            System.out.print(" ");
        }
        System.out.println("^");
    }
    public void DECLERATION_PART(){
        System.out.println(la.currentToken.tokenCode);
        if(la.currentToken.tokenCode == TokenCodes.VAR){

            Accept(TokenCodes.VAR);
            DECLERATIONS();
        }
    }
    public void DECLERATIONS(){
        DECLERATION();
        if(la.currentToken.tokenCode == TokenCodes.IDENT){
            DECLERATION();
            DECLERATIONS();
        }
    }
    public void DECLERATION(){
        IDENT();
        Accept(TokenCodes.COLON);
        if(la.currentToken.tokenCode == TokenCodes.REAL){
            Accept(TokenCodes.REAL);
        }else if(la.currentToken.tokenCode == TokenCodes.INT){
            Accept(TokenCodes.INT);
        }else if(la.currentToken.tokenCode == TokenCodes.BOOL){
            Accept(TokenCodes.BOOL);
        }
        Accept(TokenCodes.SEMICOLON);
    }
    public void IDENT(){
        Accept(TokenCodes.IDENT);
        if(la.currentToken.tokenCode == TokenCodes.COMMA){
            Accept(TokenCodes.COMMA);
            IDENT();
        }
    }
    //possible statements inside the program; function, identifier, or comment
    public void STATEMENT_PART(){
        STATEMENT();
        if(la.currentToken.tokenCode == TokenCodes.IDENT){
            STATEMENT();
        }
    }
    public void STATEMENT(){

        FUNCTIONS();
    }
    //accept comments and verify the syntax
    public void FUNCTIONS(){
        if(la.currentToken.tokenCode == TokenCodes.IF){
            IF();
        }else if (la.currentToken.tokenCode == TokenCodes.WHILE){
            WHILE();
        }else if (la.currentToken.tokenCode == TokenCodes.VAR){
            Accept(TokenCodes.VAR);
        }else if (la.currentToken.tokenCode == TokenCodes.READSYM){
            READ();
        }else if (la.currentToken.tokenCode == TokenCodes.WRITESYM){
            WRITE();
        }else if (la.currentToken.tokenCode == TokenCodes.BEGIN){
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
        if (la.currentToken.tokenCode == TokenCodes.ELSE){
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
        if(la.currentToken.tokenCode == TokenCodes.EQL){
            Accept(TokenCodes.EQL);
            EXPRESSION();
        }else if(la.currentToken.tokenCode == TokenCodes.DEQL){
            Accept(TokenCodes.DEQL);
            EXPRESSION();
        }else if(la.currentToken.tokenCode == TokenCodes.LSS){
            Accept(TokenCodes.LSS);
            EXPRESSION();
        }else if(la.currentToken.tokenCode == TokenCodes.LEQ){
            Accept(TokenCodes.LEQ);
            EXPRESSION();
        }else if(la.currentToken.tokenCode == TokenCodes.GTR){
            Accept(TokenCodes.GTR);
            EXPRESSION();
        }else if(la.currentToken.tokenCode == TokenCodes.GEQ) {
            Accept(TokenCodes.GEQ);
            EXPRESSION();
        }
    }
    public void EXPRESSION(){
        TERM();
        if (la.currentToken.tokenCode == TokenCodes.PLUS){
            Accept(TokenCodes.PLUS);
            TERM();
        }else if (la.currentToken.tokenCode == TokenCodes.MINUS){
            Accept(TokenCodes.MINUS);
            TERM();
        }else if (la.currentToken.tokenCode == TokenCodes.OR){
            Accept(TokenCodes.OR);
            TERM();
        }
    }
    public void TERM(){
        if(la.currentToken.tokenCode == TokenCodes.NOT ||
                la.currentToken.tokenCode == TokenCodes.IDENT ||
                la.currentToken.tokenCode == TokenCodes.NUMLIT||
                la.currentToken.tokenCode == TokenCodes.TRUE ||
                la.currentToken.tokenCode == TokenCodes.FALSE
        ){
            FACTOR();
        }else if(la.currentToken.tokenCode == TokenCodes.TIMES){
            Accept(TokenCodes.TIMES);
            FACTOR();
        }else if(la.currentToken.tokenCode == TokenCodes.SLASH){
            Accept(TokenCodes.SLASH);
            FACTOR();
        }else if(la.currentToken.tokenCode == TokenCodes.DIV){
            Accept(TokenCodes.DIV);
            FACTOR();
        }else if(la.currentToken.tokenCode == TokenCodes.MOD){
            Accept(TokenCodes.MOD);
            FACTOR();
        }else if(la.currentToken.tokenCode == TokenCodes.AND){
            Accept(TokenCodes.AND);
            FACTOR();
        }
    }
    public void FACTOR(){
        if (la.currentToken.tokenCode == TokenCodes.NOT){
            Accept(TokenCodes.NOT);
        }
        PRIMARY();
    }
    public void PRIMARY(){
        if(la.currentToken.tokenCode == TokenCodes.IDENT){
            Accept(TokenCodes.IDENT);
        }else if (la.currentToken.tokenCode ==TokenCodes.NUMLIT){
            Accept(TokenCodes.NUMLIT);
        }else if (la.currentToken.tokenCode ==TokenCodes.TRUE){
            Accept(TokenCodes.TRUE);
        }else if (la.currentToken.tokenCode ==TokenCodes.FALSE){
            Accept(TokenCodes.FALSE);
        }else if (la.currentToken.tokenCode ==TokenCodes.LPAREN){
            Accept(TokenCodes.LPAREN);
            BOOLEAN();
            Accept(TokenCodes.RPAREN);
        }
    }

}
