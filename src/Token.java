public class Token {

    String lexeme;
    TokenCodes tokenCode;

    Token(String lex, TokenCodes code){		//creation of a Token object
        lexeme = lex;
        tokenCode = code;
    }

    /* no-parameter print function will print the selected lexeme
     *  and its corresponding token code */

    void print (){

        System.out.print("Lexeme: '"+lexeme+"'" );

        for (int i=0 ; i<=12-lexeme.length();i++){ 		//creates a gap between columns
            System.out.print(" ");
        }
        System.out.print(" TokenCode: " + tokenCode);
        System.out.println();
    }
    //test
}