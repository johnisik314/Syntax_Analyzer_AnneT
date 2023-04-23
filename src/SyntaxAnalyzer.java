import javax.lang.model.util.ElementScanner6;
import javax.swing.*;
import java.beans.Expression;
import java.rmi.UnexpectedException;
import java.sql.SQLOutput;
import java.sql.ShardingKeyBuilder;

/*
*The following mmethods were used to implement the syntax analysis:
*
*  -ACCEPT()
*  -errorMessege()
*  -DECLERATION_PART()
*  -DECLERATIONS()
*  -DECLERATION()
*  - IDENT()
*  -STATEMENT_PART()
*  - STATEMENTS()
*  - STATEMENT()
*  -  READ()
*  - WRITE()
*  -  IF()
*  - FOR()
* - WHILE()
* - EXPRESSION()
* -MATH_EXPRE()
* - TERM()
* -FACTOR()
* - PRIMARY()
* */
public class SyntaxAnalyzer {
    LexicalAnalyzer la;

    public SyntaxAnalyzer(LexicalAnalyzer la){
        this.la = la;                   //syntax analyzer constructor which takes in lexical analyzer then begins the syntax analysis, breaking down 
        Accept(TokenCodes.PROGRAM);     // the text file contents 
        Accept(TokenCodes.IDENT);
        Accept(TokenCodes.SEMICOLON);
        DECLERATION_PART();
        STATEMENT_PART();
        while(la.currentToken.tokenCode !=TokenCodes.EOF){
            la.currentToken = la.getNextToken();
        }
        Accept(TokenCodes.EOF);

    }

    //this is the essential function which accepts token codes;
    //return error message if there is a syntax error
    //continue if there is no errors in the syntax
    public void Accept(TokenCodes token){
        if(la.currentToken.tokenCode == token){
            System.out.println("Accepted token : "+ la.currentToken.lexeme + " ("+la.currentToken.tokenCode+")");
            if(la.currentToken.tokenCode == TokenCodes.EOF){
                System.out.println("=================================");
                System.out.println("End of file");
                System.out.println("=================================\n");
            }
        }else{
            System.out.println("\n=================================");
            System.out.println("Syntax Error;");
            errorMessege();
            System.out.println("   Expected lexeme: "+token+"\n   Current token: "+la.currentToken.tokenCode);
            System.out.println("=================================\n");
        }
        la.currentToken = la.getNextToken();
    }
    
    //output of error in terminal if there is any found by Accept() method
    private void errorMessege() {
        if(la.lines.size() > la.currentLine){
            System.out.println(la.lines.get(la.currentLine));
        }
        for(int i=1; i<la.currentLocation;i++){
            System.out.print(" ");
        }
        System.out.println("^");

    }

    /*
     * Each of the following will accept the correct terms that must appear in order
     * and will check its valdidity with the accept function that takes in the token code
     * If they are not in order, error message will appear and point out syntax error
     */



    public void DECLERATION_PART(){                 //starts variable declaration segment of code
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
    public void DECLERATION(){              // variable declarations that begin with VAR reserved word
        IDENT();
        Accept(TokenCodes.COLON);
        if(la.currentToken.tokenCode == TokenCodes.REAL){
            Accept(TokenCodes.REAL);
        }else if(la.currentToken.tokenCode == TokenCodes.INT){
            Accept(TokenCodes.INT);
        }else if(la.currentToken.tokenCode == TokenCodes.EQL){
            Accept(TokenCodes.EQL);
            MATH_EXPRE();
        }else if(la.currentToken.tokenCode == TokenCodes.BOOL){
            Accept(TokenCodes.BOOL);
        }
        Accept(TokenCodes.SEMICOLON);
    }
    public void IDENT(){            //can be anything that is not a reserved word, mostly used for variable names
        Accept(TokenCodes.IDENT);
        if(la.currentToken.tokenCode == TokenCodes.COMMA){
            Accept(TokenCodes.COMMA);
            IDENT();
        }
    }
    //possible statements inside the program; function, identifier, or comment
    public void STATEMENT_PART(){
        Accept(TokenCodes.BEGIN);
        STATEMENTS();
        Accept(TokenCodes.END);
        Accept(TokenCodes.PERIOD);
    }
    public void STATEMENTS(){       //accept a statement, but has to end in semicolon
        STATEMENT();
        if(la.currentToken.tokenCode == TokenCodes.SEMICOLON){
            Accept(TokenCodes.SEMICOLON);
            STATEMENTS();
        }
    }
    public void STATEMENT(){        //determine which function is being called using reserved word 
        if(la.currentToken.tokenCode == TokenCodes.READSYM){
            READ();
        }else if (la.currentToken.tokenCode == TokenCodes.WRITESYM){
            WRITE();
        }else if (la.currentToken.tokenCode == TokenCodes.IDENT){
            Accept(TokenCodes.IDENT);
            Accept(TokenCodes.ASSIGN_OP);
            EXPRESSION();
        }else if (la.currentToken.tokenCode == TokenCodes.IF){
            IF();
        }else if (la.currentToken.tokenCode == TokenCodes.FOR){
            FOR();
        }else if (la.currentToken.tokenCode == TokenCodes.WHILE){
            WHILE();
        } else if (la.currentToken.tokenCode == TokenCodes.ELSE){
            STATEMENT();         //test *****
        }
    }
   
    public void READ(){             //correctly analyze read function
        Accept(TokenCodes.READSYM);
        Accept(TokenCodes.LPAREN);
        Accept(TokenCodes.IDENT);
        Accept(TokenCodes.RPAREN);
    }

    public void WRITE(){            //correctly analyze write function which can be both string or variable parameter
        Accept(TokenCodes.WRITESYM);
        Accept(TokenCodes.LPAREN);
        String parameter = "";
        if(la.currentToken.tokenCode == TokenCodes.SINGQUO){
             Accept(TokenCodes.SINGQUO);
             while(la.currentToken.tokenCode != TokenCodes.SINGQUO){
                 parameter = parameter +" "+ la.currentToken.lexeme;
                 System.out.println("if"+la.currentToken.tokenCode);
                 la.currentToken = la.getNextToken();
             }
            Accept(TokenCodes.SINGQUO);
        }else if(la.currentToken.tokenCode == TokenCodes.IDENT){
            while(la.currentToken.tokenCode != TokenCodes.RPAREN){
                parameter = parameter + la.currentToken.lexeme;
                System.out.println("else"+la.currentToken.tokenCode);
                la.currentToken = la.getNextToken();
            }
        }
        System.out.println("Parameter accepted by write: "+parameter);
        Accept(TokenCodes.RPAREN);
    }

    public void IF(){       //checks if statement validity and takes expression in the center
        Accept(TokenCodes.IF);
        Accept(TokenCodes.LPAREN);
        EXPRESSION();
        Accept(TokenCodes.RPAREN);
        Accept(TokenCodes.THEN);
        System.out.println(la.currentToken.tokenCode);
        STATEMENTS();
        if (la.currentToken.tokenCode == TokenCodes.ELSE){
            Accept(TokenCodes.ELSE);
            STATEMENTS();
        }
    }
    
    public void FOR(){            //checks the validity of the for loop
        Accept(TokenCodes.FOR);
        Accept(TokenCodes.IDENT);
        Accept(TokenCodes.ASSIGN_OP);
        Accept(TokenCodes.NUMLIT);
        Accept(TokenCodes.TO);
        Accept(TokenCodes.NUMLIT);
        Accept(TokenCodes.DO);
        STATEMENT();
    }
    public void WHILE(){            //checks while loop validity
        Accept(TokenCodes.WHILE);
        Accept(TokenCodes.LPAREN);
        EXPRESSION();
        Accept(TokenCodes.RPAREN);
        Accept(TokenCodes.DO);
        STATEMENT();
    }

    public void EXPRESSION(){       //evaluates expressions
        MATH_EXPRE();
        if(la.currentToken.tokenCode == TokenCodes.EQL){
            Accept(TokenCodes.EQL);
            MATH_EXPRE();
        }else if(la.currentToken.tokenCode == TokenCodes.DEQL){
            Accept(TokenCodes.DEQL);
            MATH_EXPRE();
        }else if(la.currentToken.tokenCode == TokenCodes.LSS){
            Accept(TokenCodes.LSS);
            MATH_EXPRE();
        }else if(la.currentToken.tokenCode == TokenCodes.LEQ){
            Accept(TokenCodes.LEQ);
            MATH_EXPRE();
        }else if(la.currentToken.tokenCode == TokenCodes.GTR){
            Accept(TokenCodes.GTR);
            MATH_EXPRE();
        }else if(la.currentToken.tokenCode == TokenCodes.GEQ) {
            Accept(TokenCodes.GEQ);
            MATH_EXPRE();
        }
    }
    //math expressions
    public void MATH_EXPRE(){
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
        }else if (la.currentToken.tokenCode == TokenCodes.DIV){
            Accept(TokenCodes.DIV);
            TERM();
       }  else if (la.currentToken.tokenCode == TokenCodes.MOD){
                Accept(TokenCodes.MOD);
                TERM();
        } 
    
    }
    public void TERM(){         //analyzes simple terms in mathematical expressions
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
        } else if(la.currentToken.tokenCode == TokenCodes.SLASH){
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
    public void FACTOR(){           //if its not, accept else go to primary
        if (la.currentToken.tokenCode == TokenCodes.NOT){
            Accept(TokenCodes.NOT);
        }
        PRIMARY();
    }
    public void PRIMARY(){          //breaks down 
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
            EXPRESSION();
            Accept(TokenCodes.RPAREN);
        }
    }

}
