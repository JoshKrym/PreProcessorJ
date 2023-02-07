import java.util.ArrayList;
import java.util.TreeMap;

public class Interpreter {
    static ArrayList<Token> tokens = Lexer.tokens;
    static boolean debugMode = false;
    static TreeMap<String, String> Macros = new TreeMap<String, String>();
    static String theFile;
    //static String outFile = "";
    static int TokenNum = 0;
    String returnFile;
    static int BraceNum = 0;
    public Interpreter(String theFile){
        this.theFile = theFile;
        returnFile = readTokens();
    }
    static String readTokens(){
        String outFile = "";
        while(TokenNum<tokens.size()){
            switch(tokens.get(TokenNum).token){
                case MODE:
                    defineMode(tokens.get(TokenNum+2).text, tokens.get(TokenNum+4).text);
                    TokenNum+=4;
                    break;
                case MACRO:
                    if(tokens.get(TokenNum+4).token==Tokens.RIGHT_BRACE){
                        TokenNum+=5;
                        System.out.println(tokens.get(TokenNum-3).text);
                        String key = tokens.get(TokenNum-3).text;
                        String value = readTokens().trim();
                        defineMacro(key, value.substring(0, value.length()-1));
                        System.out.println(Macros);
                        break;
                    }
                    defineMacro(tokens.get(TokenNum+2).text, tokens.get(TokenNum+4).text);
                    TokenNum+=4;
                    break;
                case DEBUG:
                    TokenNum++;
                    ignoreWhitespace();
                    if(tokens.get(TokenNum).token==Tokens.RIGHT_BRACE){
                        TokenNum++;
                        String DebugContents = readTokens().trim();
                        if(debugMode) outFile+=DebugContents.substring(0, DebugContents.length()-1);
                    }
                    break;
                case RIGHT_BRACE:
                    TokenNum++;
                    BraceNum++;
                    outFile+='{'+readTokens();
                    break;
                case LEFT_BRACE:
                    BraceNum--;
                    outFile+='}';
                    return outFile;
                case LITERAL:
                    String literal = tokens.get(TokenNum).text;
                    if(Macros.containsKey(literal)){
                        outFile+=Macros.get(literal);
                    }
                    else{
                        outFile+=literal;
                    }
                    break;
                /*case INT:     ; break;
                case CHAR:    ; break;
                case STR:     ; break;
                case SHORT:   ; break;
                case BYTE:    ; break;
                case LONG:    ; break;
                case FLOAT:   ; break;
                case VOID:    ; break;
                case DOUBLE:  ; break;
                case BOOLEAN: ; break;*/
                case STRING:
                    outFile+=tokens.get(TokenNum).text;
                    break;
                default:
                    outFile+=tokens.get(TokenNum).text;
            }
            TokenNum++;
        }
        return outFile;
    }
    /*public static void types(){
        String toCopy = "";
        if(BraceNum==0){
            if(tokens.get(TokenNum+3).token==Tokens.RIGHT_PAREN){
                String scope = tokens.get(TokenNum-2).text;
                if(scope.equals("public")||scope.equals("private")||scope.equals("protected")){
                    tokens.add(TokenNum-1, new Token(Tokens.STATIC, ));
                    int i=-2;
                    while(tokens.get(TokenNum+i).token==Tokens.LEFT_BRACE){
                        toCopy+=tokens.get(TokenNum+i).text;
                        i++;
                    }
                }
            }
            else{

            }
        }
    }*/
    public static void ignoreWhitespace(){
        while(tokens.get(TokenNum).token==Tokens.WSPACE||tokens.get(TokenNum).token==Tokens.NEWLINE){
            TokenNum++;
        }
    }
    public String getFile(){
        return returnFile;
    }
    static void defineMode(String mode, String state){
        switch(mode){
            case "debug": debugMode=(state.equals("true"));
        }
    }
    static void defineMacro(String name, String definition){
        Macros.put(name,definition);
    }
}
