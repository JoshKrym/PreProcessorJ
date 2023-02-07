#mode debug true
#define print System.out.println
public class main {
    public static void main(String[] args){
        print("Hello World");
        #debug{
            print("Testing");
        }
    }
}
