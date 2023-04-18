import java.sql.SQLOutput;

public class SyntaxAnalyzer {
    LexicalAnalyzer la;

    public SyntaxAnalyzer(LexicalAnalyzer la){
        this.la = la;
        Accept(TokenCodes.PROGRAM);
        STATEMENTS();
        Accept(TokenCodes.EOF);
    }

    public void COMMENT(){
        String comment = "";
        while(la.currentToken != TokenCodes.TIMES){
            comment = comment + la.currentLexeme;
        }
    }
    public void IF(){
        Accept(TokenCodes.IF);
        Accept(TokenCodes.LPAREN);
        STATEMENT();
    }

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

        }
    }
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

    private void errorMessege() {
        System.out.println(la.lines.get(la.currentLine));
        for(int i=1; i<la.currentLocation;i++){
            System.out.print(" ");
        }
        System.out.println("^");
    }
}
