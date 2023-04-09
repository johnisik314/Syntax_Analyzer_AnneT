public class SyntaxAnalyzer {
    LexicalAnalyzer la;

    public SyntaxAnalyzer(LexicalAnalyzer la){
        this.la = la;
        Accept(TokenCodes.PROGRAM);
        Accept(TokenCodes.PROGRAM);
    }
    public void Accept(TokenCodes token){
        Token currentToken = la.getNextToken();
        if(currentToken.tokenCode == token){
            System.out.println("Good token: "+ currentToken.lexeme);
            if(currentToken.tokenCode == TokenCodes.EOF){
                System.out.println("End of file; file is grammatically correct");
            }

        }else{
            System.out.println("Syntax Error; \n   Expected lexeme: "+token+"\n   Current token: "+currentToken.tokenCode);
        }
    }
}
