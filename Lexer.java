import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;

public class Lexer{
    //Secure coding!!
    static int position=0;
    static int line=0;
    static String currToken =  "";
    static String theFile = "";
    public static ArrayList<Token> tokens = new ArrayList<Token>();
    static String outputString = "";
    public static void main(String[] args) throws IOException{
        //System.out.println(args[0]);
        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);
        while(br.ready()){
            theFile+=br.readLine()+'\n';
        }
        //System.out.println(theFile);
        br.close();
        for(position=0; position<theFile.length(); position++){//make this a function that is called recursively each time there is a change in scope
            if(theFile.charAt(position)=='\n'){
                endToken();
                tokens.add(new Token(Tokens.NEWLINE, position, line, "\n"));
                line++;
            }
            else if(theFile.charAt(position)=='"') {
                endToken();
                position++;
                tokens.add(new Token(Tokens.STRING, position, line, '"'+consume('"')+'"'));//Type String
            }
            else if(theFile.charAt(position)==';'){
                endToken();
                tokens.add(new Token(Tokens.SEMICOLON, position, line, ""+';'));
                //position++;
            }
            else if(theFile.charAt(position)=='{') {
                endToken();
                tokens.add(new Token(Tokens.RIGHT_BRACE, position, line, ""+'{'));
            }
            else if(theFile.charAt(position)=='}') {
                endToken();
                tokens.add(new Token(Tokens.LEFT_BRACE, position, line, ""+'}'));
            }
            else if(theFile.charAt(position)=='('){
                endToken();
                tokens.add(new Token(Tokens.RIGHT_PAREN, position, line, ""+'('));
            }
            else if(theFile.charAt(position)==')'){
                endToken();
                tokens.add(new Token(Tokens.LEFT_PAREN, position, line, ""+')'));
            }
            else if(theFile.charAt(position)=='['){
                endToken();
                tokens.add(new Token(Tokens.RIGHT_BRAC, position, line, ""+'['));
            }
            else if(theFile.charAt(position)==']'){
                endToken();
                tokens.add(new Token(Tokens.LEFT_BRAC, position, line, ""+']'));
            }
            else if(Character.isWhitespace(theFile.charAt(position))){
                //position++;
                endToken();
                tokens.add(new Token(Tokens.WSPACE, position, line, ""+theFile.charAt(position)));
            }
            else{
                currToken += theFile.charAt(position);
            }
        }
        Interpreter interpreter = new Interpreter(theFile);
        System.out.println(interpreter.getFile());
        
        /*if(args[0].equals("Main.java")){
            outputString+="public class Main {\n\tpublic static void main(String args[]) {";
            //put anything that is not a method or class here
            outputString+="\t\n}";
            //put all methods not inside of classes here
            outputString+="\n}";
            //put all classes here
        }
        if(!theFile.contains("public class ")){
            outputString+="public class "+args[0].substring(0, args[0].length()-5)+" {";
            //put static stuff in here
            outputString+="\n}";
        }*/
        Path fileName = Path.of(args[0].substring(0, args[0].length()-1));
        Files.writeString(fileName, interpreter.getFile());
    }

    static void endToken(){
        if(currToken!=""){
            switch(currToken){
                case "#mode":    tokens.add(new Token(Tokens.MODE, position-5, line, "mode")); break;
                case "#define":  tokens.add(new Token(Tokens.MACRO, position-7, line, "define")); break;
                case "#debug":   tokens.add(new Token(Tokens.DEBUG, position-6, line, "debug")); break;
                /*case "int":      tokens.add(new Token(Tokens.INT, position-3, line,     "int"    )); break;
                case "char":     tokens.add(new Token(Tokens.CHAR, position-4, line,    "char"   )); break;
                case "byte":     tokens.add(new Token(Tokens.BYTE, position-4, line,    "byte"   )); break;
                case "void":     tokens.add(new Token(Tokens.VOID, position-4, line,    "void"   )); break;
                case "float":    tokens.add(new Token(Tokens.FLOAT, position-5, line,   "float"  )); break;
                case "long":     tokens.add(new Token(Tokens.LONG, position-4, line,    "long"   )); break;
                case "boolean":  tokens.add(new Token(Tokens.BOOLEAN, position-7, line, "boolean")); break;
                case "short":    tokens.add(new Token(Tokens.SHORT, position-5, line,   "short"  )); break;
                case "double":   tokens.add(new Token(Tokens.DOUBLE, position-6, line,  "double" )); break;
                case "String":   tokens.add(new Token(Tokens.STR, position-6, line,     "String" )); break;*/
                default:         tokens.add(new Token(Tokens.LITERAL, position, line, currToken)); break;
            }
            currToken = "";
        }
    }

    static String consume(char stopChar){
        try{
            String currToken="";
            while(theFile.charAt(position)!=stopChar){
                currToken+=theFile.charAt(position);
                position++;
            }
            return currToken;
        }
        catch(StringIndexOutOfBoundsException e){
            return null;
        }
    }
    static Character peak(char lookChar){
        if(theFile.charAt(position+1)==lookChar){
            return theFile.charAt(position+1);
        }
        return null;
    }
    static String peak(String lookString){
        try{
        if(theFile.substring(position, position+lookString.length()).equals(lookString)){
            position+=lookString.length(); 
            return lookString;
        }
        return null;
        }
        catch(StringIndexOutOfBoundsException e){
            return null;
        }
    }
}