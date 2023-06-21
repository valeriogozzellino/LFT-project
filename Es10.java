
class Es10 {
    public static boolean scan(String matricola) {
        int i = 0;
        int state = 0;
        while (i < matricola.length()) {
            char ch = matricola.charAt(i++);

            switch (state) {
                case 0:
                    if (ch == 'a' || ch == '*') {
                        state = 0;
                    } else if (ch == '/') {
                        state = 1;
                    }
                    break;
                case 1:
                    if (ch == 'a') {
                        state = 0;
                    } else if (ch == '*') {
                        state = 2;
                    }
                    break;
                case 2:
                    if (ch == 'a' || ch == '/') {
                        state = 2;
                    } else if (ch == '*') {
                        state = 3;
                    }
                    break;
                case 3:
                    if (ch == '*') {
                        state = 3;
                    } else if (ch == 'a') {
                        state = 2;
                    } else if (ch == '/') {
                        state = 4;
                    }
                    break;
                case 4:
                    if (ch == '*' || ch == 'a') {
                        state = 0;
                    }
                    break;
            }
        }
        return state == 4 || state == 0;
    }

    public static void main(String[] args) {

        String stringa = "aa/*aa";
        String stringa1 = "aa/*a*a*/";
        String stringa2 = "a/**/***/a";
        String stringa3 = "a/**/aa/***/a";
        String stringa4 = "aaa/*/aa";

        System.out.println("false:" + scan(stringa));
        System.out.println("true:" + scan(stringa1));
        System.out.println("true:" + scan(stringa2));
        System.out.println("true:" + scan(stringa3));
        System.out.println("false:" + scan(stringa4));

    }

}
