import java.io.*;

public class Lexer {

    public static int line = 1;
    private char peek = ' ';

    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n' || peek == '\r') {
            if (peek == '\n')
                line++;
            readch(br);
        }

        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;
            case '(':
                peek = ' ';
                return Token.lpt;
            case ')':
                peek = ' ';
                return Token.rpt;
            case '[':
                peek = ' ';
                return Token.lpq;
            case ']':
                peek = ' ';
                return Token.rpq;
            case '{':
                peek = ' ';
                return Token.lpg;
            case '}':
                peek = ' ';
                return Token.rpg;
            case '+':
                peek = ' ';
                return Token.plus;
            case '-':
                peek = ' ';
                return Token.minus;
            case '*':
                peek = ' ';
                return Token.mult;
            case '/':

                boolean closeC = false;
                readch(br);

                switch (peek) {
                    case '/':
                        while (peek != '\n' && peek != (char) -1) {
                            readch(br);
                        }
                        return lexical_scan(br);

                    case '*':
                        while (!closeC) {
                            if (peek == '*') {
                                readch(br);
                                closeC = peek == '/';
                            } else {
                                readch(br);
                            }

                        }
                        peek = ' ';
                        return lexical_scan(br);
                }
                return Token.div;

            case ';':
                peek = ' ';
                return Token.semicolon;
            case ',':
                peek = ' ';
                return Token.comma;

            case '&':
                readch(br); // cosi mi leggo il carattere successivo che è stato salvato nel buffer della
                            // memoria
                if (peek == '&') { // allora se quello dopo è un altro & allora si tratta di una world and
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("Erroneous character"
                            + " after & : " + peek);
                    return null;
                }
            case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("Erroneous character"
                            + "after | : " + peek);
                    return null;
                }
            case '<':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.le;

                } else if (peek == '>') {
                    peek = ' ';
                    return Word.ne;

                } else {

                    return Word.lt;
                }
            case '>':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.ge;
                } else {
                    return Word.gt;
                }
            case '=':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.eq;
                } else {
                    System.err.println("Erroneous character"
                            + "after = : " + peek);
                    return null;
                }

                // ... gestire i casi di || < > <= >= == <> ... //

            case (char) -1:
                return new Token(Tag.EOF);

            default:
                if (Character.isLetter(peek)) { // mi restituisce true o false a seconda del valore di peek
                    String str = "";

                    // ... gestire il caso degli identificatori e delle parole chiave //
                    while (Character.isLetter(peek)) {
                        str += peek; // per concatenare la stringa
                        readch(br); // me lo legge fino a terminazione, svuota il buffer
                    }
                    return stringa(str);

                }

                if (Character.isDigit(peek)) {
                    // ... gestire il caso dei numeri ... //
                    return numero(br);

                }

                System.err.println("Erroneous character" + peek);
                return null;

        }

    }

    public Word stringa(String str) {
        switch (str) { // lo switch verifica che parola str è tra quelle presenti nei case
            case "assign": // il case mi verifica già che la parola è assign
                return Word.assign;
            case "to":
                return Word.to; // word.__ per riferimi ad una variabile presente in un file nella
                                // stessa
                                // cartella
            case "conditional":
                return Word.conditional;
            case "option":
                return Word.option;
            case "do":
                return Word.dotok;
            case "else":
                return Word.elsetok;
            case "while":
                return Word.whiletok;
            case "begin":
                return Word.begin;
            case "end":
                return Word.end;
            case "print":
                return Word.print;
            case "read":
                return Word.read;
            default:
                return new Word(Tag.ID, str);
        }

    }

    public NumberTok numero(BufferedReader br) {

        int numero = 0;
        if (Character.isDigit(peek)) {
            while (Character.isDigit(peek)) {
                numero = numero * 10 + Character.getNumericValue(peek);
                readch(br);
                if (Character.isLetter(peek)) {
                    return null;
                }
            }
            return new NumberTok(numero);
        } else {

            System.err.println("Erroneous character" + peek);
            return null;
        }
    }
}
