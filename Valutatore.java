import java.io.*;

public class Valutatore {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Valutatore(Lexer l, BufferedReader br) {
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
        int expr_val;
        switch (look.tag) {
            case '(':
            case Tag.NUM:
                expr_val = expr();
                match(Tag.EOF);
                System.out.println(expr_val);
                break;
            default:
                error("syntax error start ");
                break;
        }

    }

    private int expr() { /* GUIDA(<term> --> <fact><term>) */
        int term_val, exprp_val;
        switch (look.tag) {
            case '(':
            case Tag.NUM:
                term_val = term();
                exprp_val = exprp(term_val);
                return exprp_val;
            default:
                error("syntax error expr");
                return -1;
        }

    }

    private int exprp(int exprp_i) {/* GUIDA(<term> --> <fact><term>) */
        int term_val, exprp_val;

        switch (look.tag) {
            case '+':
                match('+');
                term_val = term();
                exprp_val = exprp(exprp_i + term_val);
                return exprp_val;
            case '-':
                match('-');
                term_val = term();
                exprp_val = exprp(exprp_i - term_val);
                return exprp_val;
            case ')':
            case Tag.EOF:
                return exprp_i;
            default:
                error("syntax error exprp");
                return -1;
        }

    }

    private int term() { // GUIDA(<term> --> <fact><term>)
        int fact_val;
        int termp_val;
        switch (look.tag) {
            case '(':
            case Tag.NUM:
                fact_val = fact();
                termp_val = termp(fact_val);
                return termp_val;
            default:
                error("syntax error term");
                return -1;

        }

    }

    private int termp(int termp_i) { /* GUIDA(<term> --> <fact><term>) */
        int fact_val, termp_val;

        switch (look.tag) {
            case '*':
                match('*');
                fact_val = fact();
                termp_val = termp(termp_i * fact_val);
                return termp_val;
            case '/':
                match('/');
                fact_val = fact();
                termp_val = termp(termp_i / fact_val);
                return termp_val;
            case '+':
            case '-':
            case ')':
            case Tag.EOF:
                return termp_i;

            default:
                error("syntax error expr");
                return -1;

        }

    }

    private int fact() {/* GUIDA(<term> --> <fact><term>) */
        int fact_val = 0;
        int expr_val;

        switch (look.tag) {
            case '(':
                match('(');
                expr_val = expr();
                fact_val = expr_val;
                match(')');
                break;
            case Tag.NUM:
                /* cast per looktag a Tag.NUM per avere il valore effettivo del numero */
                fact_val = ((NumberTok) look).lexeme;
                match(Tag.NUM);
                break;

            default:
                fact_val = -1;
                error("Fact error" + look.tag);

        }
        return fact_val;
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "./analisilessicale.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Valutatore valutatore = new Valutatore(lex, br);
            valutatore.start();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
