import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import to handle errors


public class Main {
    public static void main(String[] args) {

        try {
            //read the file and create a LexicalAnlyzer object with the provided file
            File file = new File("src/test1.pas");
            LexicalAnalyzer la = new LexicalAnalyzer(file);
            la.output();

        }

        //enabled exception handling if for various reasons, the file cannot be opened.
        catch (FileNotFoundException e) {
            System.out.println("Error: Cant read the input file!");
            e.printStackTrace();
        }


    }
}