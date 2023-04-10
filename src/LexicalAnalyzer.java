import java.io.File;  // Import the neccesary libraries
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {

    List<String> lines = new ArrayList<String>();   //needed ArrayList and variables
    int currentLine;
    int currentLocation;

    // LexicalAnalyzer constructor to read file and add lines to 
    public LexicalAnalyzer(File file) throws FileNotFoundException {
        currentLine = 0;
        currentLocation = 0;

        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }
        scanner.close();
    }

    //return the next token 
    public Token getNextToken(){
        String line = lines.get(currentLine);
        String lexeme = findNextToken(line);

        Token token = new Token(lexeme,getTokenCode(lexeme));
        return token;
    }

    //return next lexeme according to the different string types
    public String findNextToken(String line){
        String token = "";

        while(true) {

            //assign letter and next_letter
            String letter, next_letter = "null";
            if(line.equals("")){
                currentLine++;
                return "EmptyLine";
            }
            letter = line.substring(currentLocation, currentLocation + 1);
            if (currentLocation < line.length() - 1) {
                next_letter = line.substring(currentLocation + 1, currentLocation + 2);
            }
            //go to next character  
            if(next_letter.equals("null")){
                currentLocation = 0;
                currentLine++;
                return token + letter;
            }

            if(     letter.equals(" ")||
                    letter.equals("+")||
                    letter.equals("-")||
                    letter.equals("*")||
                    letter.equals(".")||
                    letter.equals("'")||
                    letter.equals("\"")||
                    letter.equals("%")||
                    letter.equals("(")||
                    letter.equals("”")||
                    letter.equals("“")||
                    letter.equals(")")||
                    letter.equals(",")||
                    letter.equals("^")||
                    letter.equals(";")
            ){
                currentLocation++;
                return letter;
            }else if (letter.equals("&")){
                currentLocation++;
                if(next_letter.equals("&")){
                    currentLocation++;
                    return letter+next_letter;
                }
                return letter;
            }
            else if (letter.equals("|")){
                currentLocation++;
                if(next_letter.equals("|")){
                    currentLocation++;
                    return letter+next_letter;
                }
                return letter;
            }else if (letter.equals("/")){
                currentLocation++;
                if(next_letter.equals("=")){
                    currentLocation++;
                    return letter+next_letter;
                }
                return letter;
            }else if (letter.equals(":")){
                currentLocation++;
                if(next_letter.equals("=")){
                    currentLocation++;
                    return letter+next_letter;
                }
                return letter;
            }else if (letter.equals("=")){
                currentLocation++;
                if(next_letter.equals("=")){
                    currentLocation++;
                    return letter+next_letter;
                }
                return letter;
            }else if (letter.equals("<")){
                currentLocation++;
                if(next_letter.equals("=")){
                    currentLocation++;
                    return letter+next_letter;
                }
                return letter;
            }else if (letter.equals(">")){
                currentLocation++;
                if(next_letter.equals("=")){
                    currentLocation++;
                    return letter+next_letter;
                }
                return letter;
            }else if (letter.equals("&")){
                currentLocation++;
                if(next_letter.equals("&")){
                    currentLocation++;
                    return letter+next_letter;
                }
                return letter;
            }else if (letter.equals("!")){
                currentLocation++;
                if(next_letter.equals("=")){
                    currentLocation++;
                    return letter+next_letter;
                }
                return letter;
            }

            if(     next_letter.equals(" ")||
                    next_letter.equals("+")||
                    next_letter.equals("-")||
                    next_letter.equals("=")||
                    next_letter.equals("*")||
                    next_letter.equals(".")||
                    next_letter.equals(",")||
                    next_letter.equals("%")||
                    next_letter.equals("(")||
                    next_letter.equals(")")||
                    next_letter.equals(";")||
                    next_letter.equals("/")||
                    next_letter.equals(":")||
                    next_letter.equals("<")||
                    next_letter.equals("“")||
                    next_letter.equals("”")||
                    next_letter.equals(">")||
                    next_letter.equals("&")||
                    next_letter.equals("!")

            ){
                currentLocation++;
                return token+letter;
            }

            currentLocation++;
            token = token + letter;

        }



    }

    //find the TokenCode for the lexeme string
    private TokenCodes getTokenCode(String lexeme){

        TokenCodes token = TokenCodes.NULL;

        if(lexeme.equals(" ")){
            token = TokenCodes.SPACE;
        }else if(lexeme.equals("&")){
            token = TokenCodes.NOTALEX;
        }else if(lexeme.equals("|")){
            token = TokenCodes.NOTALEX;
        }else if(lexeme.equals("+")){
            token = TokenCodes.PLUS;
        }else if(lexeme.equals("-")){
            token = TokenCodes.MINUS;
        }else if(lexeme.equals("'")){
            token = TokenCodes.SINGQUO;
        }else if(lexeme.equals("*")){
            token = TokenCodes.TIMES;
        }else if(lexeme.equals("/")){
            token = TokenCodes.SLASH;
        }else if(lexeme.equals("=")){
            token = TokenCodes.EQL;
        }else if(lexeme.equals("==")){
            token = TokenCodes.DEQL;
        }else if(lexeme.equals("(")){
            token = TokenCodes.LPAREN;
        }else if(lexeme.equals(")")){
            token = TokenCodes.RPAREN;
        }else if(lexeme.equals(".")){
            token = TokenCodes.PERIOD;
        }else if(lexeme.equals(",")){
            token = TokenCodes.COMMA;
        }else if(lexeme.equals(";")){
            token = TokenCodes.SEMICOLON;
        }else if(lexeme.equals(":")){
            token = TokenCodes.COLON;
        }else if(lexeme.equals("!=")){
            token = TokenCodes.NEQ;
        }else if(lexeme.equals(":=")){
            token = TokenCodes.ASSIGN_OP;
        }else if(lexeme.equals("<")){
            token = TokenCodes.LSS;
        }else if(lexeme.equals("<=")){
            token = TokenCodes.LEQ;
        }else if(lexeme.equals(">")){
            token = TokenCodes.GTR;
        }else if(lexeme.equals(">=")){
            token = TokenCodes.GEQ;
        }else if(lexeme.equals("&&")){
            token = TokenCodes.AND;
        }else if(lexeme.equalsIgnoreCase("array")){
            token = TokenCodes.ARRAY;
        }else if(lexeme.equalsIgnoreCase("begin")){
            token = TokenCodes.BEGIN;
        }else if(lexeme.equalsIgnoreCase("case")){
            token = TokenCodes.CASE;
        }else if(lexeme.equalsIgnoreCase("const")){
            token = TokenCodes.CONST;
        }else if(lexeme.equals("%") || lexeme.equalsIgnoreCase("div")){
            token = TokenCodes.DIV;
        }else if(lexeme.equalsIgnoreCase("do")){
            token = TokenCodes.DO;
        }else if(lexeme.equalsIgnoreCase("downto")){
            token = TokenCodes.DOWNTO;
        }else if(lexeme.equalsIgnoreCase("else")){
            token = TokenCodes.ELSE;
        }else if(lexeme.equalsIgnoreCase("end")){
            token = TokenCodes.END;
        }else if(lexeme.equalsIgnoreCase("file")){
            token = TokenCodes.FILE;
        }else if(lexeme.equalsIgnoreCase("for")){
            token = TokenCodes.FOR;
        }else if(lexeme.equalsIgnoreCase("function")){
            token = TokenCodes.FUNCTION;
        }else if(lexeme.equalsIgnoreCase("goto")){
            token = TokenCodes.GOTO;
        }else if(lexeme.equalsIgnoreCase("if")){
            token = TokenCodes.IF;
        }else if(lexeme.equalsIgnoreCase("in")){
            token = TokenCodes.IN;
        }else if(lexeme.equalsIgnoreCase("label")){
            token = TokenCodes.LABEL;
        }else if(lexeme.equalsIgnoreCase("mod")){
            token = TokenCodes.MOD;
        }else if(lexeme.equalsIgnoreCase("nil")){
            token = TokenCodes.NIL;
        }else if(lexeme.equalsIgnoreCase("not")){
            token = TokenCodes.NOT;
        }else if(lexeme.equalsIgnoreCase("of")){
            token = TokenCodes.OF;
        }else if(lexeme.equalsIgnoreCase("||")){
            token = TokenCodes.OR;
        }else if(lexeme.equalsIgnoreCase("packed")){
            token = TokenCodes.PACKED;
        }else if(lexeme.equalsIgnoreCase("procedure")){
            token = TokenCodes.PROCEDURE;
        }else if(lexeme.equalsIgnoreCase("program")){
            token = TokenCodes.PROGRAM;
        }else if(lexeme.equalsIgnoreCase("record")){
            token = TokenCodes.RECORD;
        }else if(lexeme.equalsIgnoreCase("repeat")){
            token = TokenCodes.REPEAT;
        }else if(lexeme.equalsIgnoreCase("set")){
            token = TokenCodes.SET;
        }else if(lexeme.equalsIgnoreCase("then")){
            token = TokenCodes.THEN;
        }else if(lexeme.equalsIgnoreCase("to")){
            token = TokenCodes.TO;
        }else if(lexeme.equalsIgnoreCase("type")){
            token = TokenCodes.TYPE;
        }else if(lexeme.equalsIgnoreCase("until")){
            token = TokenCodes.UNTIL;
        }else if(lexeme.equalsIgnoreCase("var")){
            token = TokenCodes.VAR;
        }else if(lexeme.equalsIgnoreCase("while")){
            token = TokenCodes.WHILE;
        }else if(lexeme.equalsIgnoreCase("with")){
            token = TokenCodes.WITH;
        }else if(lexeme.equalsIgnoreCase("write")) {
            token = TokenCodes.WRITESYM;
        }else if(lexeme.equalsIgnoreCase("integer")){
            token = TokenCodes.INTSYM;
        }else if(lexeme.equalsIgnoreCase("EmptyLine")){
            token = TokenCodes.EMPTYLINE;
        }else if(lexeme.equalsIgnoreCase("read")){
            token = TokenCodes.READSYM;
        }else if((lexeme.equals("“"))||(lexeme.equals("”"))) {
            token = TokenCodes.QUOTE;
        } else if(isInteger(lexeme)){
            token = TokenCodes.NUMLIT;
        } else if(!isUnknown(lexeme)==false) {
            token = TokenCodes.UNKNOWN;
        }else {
            token = TokenCodes.IDENT;
        }
        return token;
    }


    //return true if any character is unknown
    private boolean isUnknown(String lexeme) {
        String[] substrings = lexeme.split("");
        boolean isUnknown = false;
        //list of acceptable characters turned into  various regex
        Pattern letter = Pattern.compile("[A-za-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile ("[-+*/:=,;.()[]={}`]]");

        //the characterized string is analyzed to see if it matches any of the regex values
        for(String ch: substrings){
            Matcher hasLetter = letter.matcher(ch);
            Matcher hasDigit = digit.matcher(ch);
            Matcher hasSpecial = special.matcher(ch);
            if(hasLetter.find() || hasDigit.find() || hasSpecial.find()){
                // if any is true, that means its a valid, iff all false that means its unknown
                isUnknown = false;
            } //if it is valid, it is not an unknown
            else  isUnknown = true;
        }
        return isUnknown;
    }
    //checks to see if the string is an integer 
    public static boolean isInteger(String i) {
        try {
            Integer.parseInt(i);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }

    /* Utilizes the print function of the Token class while traversing the ArrayList
    and prints the results  */
    public void output (){

        while (lines.size() != currentLine){
            Token token = getNextToken();
            token.print();
        }
        Token token = new Token("End of File",TokenCodes.EOF);
        token.print();
    }


}