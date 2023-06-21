public class NumberTok extends Token {
    public int lexeme = 0;

    public NumberTok(int s) {
        super(Tag.NUM); /*
                         * con super mi sto riferendo ad un oggetto
                         * incluso nella classe che implemento
                         */
        lexeme = s;
    }

    public String toString() {
        return "<" + tag + ", " + lexeme + ">";
    }

}