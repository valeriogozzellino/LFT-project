class Es19 {

    public static boolean scan(String stringa) {
        int i = 0;
        int state = 0;
        while (i < stringa.length()) {
            char ch = stringa.charAt(i++);

            switch (state) {
                case 0:
                    if (ch == '/') {
                        state = 1;
                    } else {
                        return false;
                    }
                    break;

                case 1:
                    if (ch == '*') {
                        state = 2;
                    } else {
                        return false;
                    }
                    break;
                case 2:
                    if (ch == 'a') {
                        state = 3;
                    } else if (ch == '*') {
                        state = 4;
                    }
                    break;

                case 3:
                    if (ch == 'a' || ch == '/') {
                        state = 3;
                    } else if (ch == '*') {
                        state = 4;
                    }
                    break;

                case 4:
                    if (ch == 'a') {
                        state = 3;
                    } else if (ch == '*') {
                        state = 4;
                    } else if (ch == '/') {
                        state = 5;
                    }
                    break;
                case 5:
                    return false;

            }
        }
        return state == 5;
    }

    public static void main(String[] args) {
        String stringa = "/*aa*/";
        String stringa1 = "/*a*a*/";
        String stringa2 = "/*a/*/";
        String stringa3 = "/**/*/";
        boolean debug = scan(stringa3);
        System.out.println("debug: " + debug);
        System.out.println("true: " + scan(stringa));
        System.out.println("true: " + scan(stringa1));
        System.out.println("true: " + scan(stringa2));
        System.out.println("false: " + scan(stringa3));

    }

}