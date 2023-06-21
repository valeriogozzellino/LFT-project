import java.io.*;

public class Parser {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
        throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
        if (look.tag == t) {
            if (look.tag != Tag.EOF)
                move();
        } else
            error("syntax error match");
    }

    public void start() {
        if (look.tag == '(' || look.tag == Tag.NUM) {
            expr();
            match(Tag.EOF);
        } else {
            error("syntax error start ");
        }
    }

    private void expr() {
        /* GUIDA(<term> --> <fact><term>) */
        if (look.tag == '(' || look.tag == Tag.NUM) {
            term();
            exprp();
        } else {
            error("syntax error expr");
        }

    }

    private void exprp() {/* GUIDA(<term> --> <fact><term>) */
        if (look.tag == '+' || look.tag == '-' || look.tag == ')' || look.tag == Tag.EOF) {
            switch (look.tag) {
                case '+':
                    match('+');
                    term();
                    exprp();
                    break;
                case '-':
                    match('-');
                    term();
                    exprp();
                    break;
                default:
                    break;

            }
        } else {
            error("syntax error exprp");
        }
    }

    private void term() { // GUIDA(<term> --> <fact><term>)
        if (look.tag == '(' || look.tag == Tag.NUM) {
            fact();
            termp();
        } else {
            error("syntax error term");
        }

    }

    private void termp() { /* GUIDA(<term> --> <fact><term>) */
        if (look.tag == '+' || look.tag == '-' || look.tag == '*' || look.tag == '/' || look.tag == Tag.EOF
                || look.tag == ')') {
            switch (look.tag) {
                case '*':
                    match('*');
                    fact();
                    termp();
                    break;
                case '/':
                    match('/');
                    fact();
                    termp();
                    break;
                default:
                    break;

            }
        } else {
            error("syntax error termp");
        }

    }

    private void fact() {/* GUIDA(<term> --> <fact><term>) */
        if (look.tag == '(' || look.tag == Tag.NUM) {
            switch (look.tag) {
                case '(': // se mi trova ( mi fa il match
                    match('(');
                    expr();
                    match(')');
                    break;
                case Tag.NUM: // altrimenti match del numero
                    match(look.tag);
                    break;

            }
        } else {
            error("syntax error fact");
        }

    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "./analisilessicale.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.start();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
