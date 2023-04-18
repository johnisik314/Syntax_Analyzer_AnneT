public class SyntaxAnalyzer {
    LexicalAnalyzer la;
//repo test
    public SyntaxAnalyzer(LexicalAnalyzer la){
        this.la = la;
        Accept(TokenCodes.PROGRAM);
        Accept(TokenCodes.IDENT);
        Accept(TokenCodes.SEMICOLON);
        Accept(TokenCodes.PROGRAM);
        Accept(TokenCodes.PROGRAM);
    }
    public void Accept(TokenCodes token){
        Token currentToken = la.getNextToken();
        while(currentToken.tokenCode == TokenCodes.SPACE){
            currentToken = la.getNextToken();
        }
        if(currentToken.tokenCode == token){
            System.out.println("Good token: "+ currentToken.lexeme);
            if(currentToken.tokenCode == TokenCodes.EOF){
                System.out.println("End of file; file is grammatically correct");
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
