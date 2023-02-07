public class Token {
    Tokens token;
    String text;
    int line;
    int position;
    public Token(Tokens token, int position, int line, String text){
        this.token = token;
        this.text = text;
        this.line = line;
        this.position = position;
    }
}
