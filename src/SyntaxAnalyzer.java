import java.sql.SQLOutput;

public class SyntaxAnalyzer {
    LexicalAnalyzer la;

    public SyntaxAnalyzer(LexicalAnalyzer la){
        this.la = la;
        program();
    }
    public void program(){
        Token currentTC = la.getNextToken();
            Accept(TokenCodes.PROGRAM);
            Accept(TokenCodes.IDENT);
            Accept(TokenCodes.SEMICOLON);
          
    }
    public void COMMENT(){
        Token currentTC = la.getNextToken();
        Token nextTC= la.getNextToken();
        while(currentTC.tokenCode != TokenCodes.TIMES){
            currentTC = nextTC;
            nextTC = la.getNextToken();
        }
        System.out.println("Comment");
    }
    //e
    public void IF(){
        Accept(TokenCodes.IF);
        Accept(TokenCodes.LPAREN);
        

    }

    public void Accept(TokenCodes token){
        Token currentToken = la.getNextToken();     //declare currentToken object
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
