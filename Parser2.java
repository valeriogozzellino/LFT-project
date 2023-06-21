import java.io.*;

public class Parser2 {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser2(Lexer l, BufferedReader br) {
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

    public void prog() { /* GUIDA(<prog> --> <statlistp>EOF) */
        if (look.tag == Tag.ASSIGN || look.tag == Tag.PRINT || look.tag == Tag.READ || look.tag == Tag.WHILE
                || look.tag == Tag.COND || look.tag == '{') {
            statlist();
            match(Tag.EOF);
        } else {
            error("syntax error prog ");
        }
    }

    private void statlist() { /* GUIDA(<statlist> --> <stat><statlistp>) */
        if (look.tag == Tag.ASSIGN || look.tag == Tag.PRINT || look.tag == Tag.READ || look.tag == Tag.WHILE
                || look.tag == Tag.COND || look.tag == '{') {
            stat();
            statlistp();
        } else {
            error("syntax error statlist");
        }

    }

    private void statlistp() {/*
                               * GUIDA(<statlistp> --> ;<stat><statlistp>) && /*GUIDA(<statlist> --> £)
                               * == {;} && {EOF , }}
                               */
        if (look.tag == ';' || look.tag == Tag.EOF || look.tag == '}') {
            switch (look.tag) {
                case ';':
                    match(';');
                    stat();
                    statlistp();
                    break;
                default:
                    break;
            }

        } else {
            error("syntax error statlistp");
        }
    }

    private void stat() {/* GUIDA(<term> --> <fact><term>) */
        if (look.tag == Tag.ASSIGN || look.tag == Tag.PRINT || look.tag == Tag.READ || look.tag == Tag.WHILE
                || look.tag == Tag.COND || look.tag == '{') {
            switch (look.tag) {
                case Tag.ASSIGN:
                    match(Tag.ASSIGN);
                    expr();
                    match(Tag.TO);
                    idlist();
                    break;

                case Tag.PRINT:
                    match(Tag.PRINT);
                    match('[');
                    exprlist();
                    match(']');
                    break;

                case Tag.READ:
                    match(Tag.READ);
                    match('[');
                    idlist();
                    match(']');
                    break;

                case Tag.WHILE:
                    match(Tag.WHILE);
                    match('(');
                    bexpr();
                    match(')');
                    stat();
                    break;

                case Tag.COND:
                    match(Tag.COND);
                    match('[');
                    optlist();
                    match(']');
                    conditionalprod();
                    break;

                case '{':
                    match('{');
                    statlist();
                    match('}');
                    break;
            }

        } else {
            error("syntax error stat");
        }
    }

    private void conditionalprod() { /*
                                      * GUIDA(<proud>--> end )= {end} && GUIDA(<proud>--> else..)={else}
                                      * produzione: <proud>--> end | else <stat> end
                                      */
        if (look.tag == Tag.END || look.tag == Tag.ELSE) {
            switch (look.tag) {
                case Tag.END:
                    match(Tag.END);
                    break;
                case Tag.ELSE:
                    match(Tag.ELSE);
                    stat();
                    match(Tag.END);
                    break;
            }
        } else {
            error("syntax error in proud");
        }
    }

    private void idlist() {/* {ID} */
        if (look.tag == Tag.ID) {
            match(Tag.ID);
            idlistp();
        } else {
            error("syntax error idlist");
        }
    }

    private void idlistp() {/* {,} , per la stringa vuota{], end, ;, EOF, }} */
        if (look.tag == ',' || look.tag == ']' || look.tag == ';' || look.tag == '}' || look.tag == Tag.END
                || look.tag == Tag.EOF) {
            switch (look.tag) {
                case ',':
                    match(',');
                    match(Tag.ID);
                    idlistp();
                    break;
                default:
                    break;
            }

        } else {
            error("syntax error idlistp");
        }
    }

    private void optlist() {/* {option} */
        if (look.tag == Tag.OPTION) {
            optitem();
            optlistp();

        } else {
            error("syntax error optlist");
        }
    }

    private void optlistp() {/* {option} {]} */
        if (look.tag == Tag.OPTION || look.tag == ']') {
            switch (look.tag) {
                case Tag.OPTION:
                    optitem();
                    optlistp();
                    break;
                default:
                    break;
            }
        } else {
            error("syntax error optlistp");
        }
    }

    private void optitem() {/* GUIDA(<term> --> <fact><term>) */
        if (look.tag == Tag.OPTION) {
            match(Tag.OPTION);
            match('(');
            bexpr();
            match(')');
            match(Tag.DO);
            stat();
        } else {
            error("syntax error optitem");
        }
    }

    private void bexpr() {/* GUIDA(<term> --> <fact><term>) */
        if (look.tag == Tag.RELOP) {
            match(Tag.RELOP);
            expr();
            expr();
        } else {
            error("syntax error bexpr");
        }
    }

    private void expr() {/* GUIDA(<term> --> <fact><term>) */
        if (look.tag == '+' || look.tag == '-' || look.tag == '*' || look.tag == '/'
                || look.tag == Tag.NUM || look.tag == Tag.ID) {
            switch (look.tag) {
                case '+':
                    match('+');
                    match('(');
                    exprlist();
                    match(')');
                    break;

                case '-':
                    match('-');
                    expr();
                    expr();
                    break;

                case '*':
                    match('*');
                    match('(');
                    exprlist();
                    match(')');
                    break;

                case '/':
                    match('/');
                    expr();
                    expr();
                    break;

                case Tag.NUM:
                    match(Tag.NUM);
                    break;

                case Tag.ID:
                    match(Tag.ID);
                    break;
            }

        } else {
            error("syntax error expr");
        }
    }

    private void exprlist() {/* GUIDA(<term> --> <fact><term>) */
        if (look.tag == '+' || look.tag == '-' || look.tag == '*' || look.tag == '/' || look.tag == Tag.NUM
                || look.tag == Tag.ID) {
            expr();
            exprlistp();
        } else {
            error("syntax error exprlist");
        }
    }

    private void exprlistp() {/* GUIDA(<term> --> <fact><term>) */
        if (look.tag == ',' || look.tag == ';' || look.tag == ')' || look.tag == ']'
                || look.tag == '/' || look.tag == Tag.NUM
                || look.tag == Tag.ID) {
            switch (look.tag) {
                case ',':
                    match(',');
                    expr();
                    exprlistp();
                    break;
                default:
                    break;
            }

        } else {
            error("syntax error exprlistp");
        }
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "./analisilessicale.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser2 parser = new Parser2(lex, br);
            parser.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
