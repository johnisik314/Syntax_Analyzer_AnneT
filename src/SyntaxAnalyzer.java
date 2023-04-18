public class SyntaxAnalyzer {
    LexicalAnalyzer la;

    public SyntaxAnalyzer(LexicalAnalyzer la){
        this.la = la;
        Accept(TokenCodes.PROGRAM);
        STATEMENT();
        Accept(TokenCodes.EOF);
    }

    public void COMMENT(){
        Token currentTC = la.getNextToken();
        Token nextTC= la.getNextToken();
        while(currentTC.tokenCode != TokenCodes.TIMES && nextTC.tokenCode != TokenCodes.RPAREN){
            currentTC = nextTC;
            nextTC = la.getNextToken();
        }
        System.out.println("Hi");
    }
    public void IF(){
        Accept(TokenCodes.IF);
        Accept(TokenCodes.LPAREN);
        STATEMENT();

    }

    
    public void STATEMENT(){
        Token currentTC = la.getNextToken();
       if(currentTC.tokenCode ==  TokenCodes.LPAREN ){
            currentTC = la.getNextToken();
            if(currentTC.tokenCode == TokenCodes.TIMES){
                COMMENT();
            }

       }else{

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
