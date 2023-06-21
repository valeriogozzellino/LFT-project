class Es16 {
    public static boolean scan(String stringa) {
        int i = 0;
        int state = 0;
        while (i < stringa.length()) {
            char ch = stringa.charAt(i++);

            switch (state) {
                case 0:
                    if (ch == 'b') {
                        state = 0;
                    } else if (ch == 'a') {
                        state = 1;
                    }
                    break;
                case 1:
                    if (ch == 'b') {
                        state = 2;
                    } else if (ch == 'a') {
                        state = 2;
                    }
                    break;
                case 2:
                    if (ch == 'b') {
                        state = 3;
                    } else if (ch == 'a') {
                        state = 3;
                    }
                    break;
                case 3:
                    if (ch == 'b') {
                        state = 0;
                    } else if (ch == 'a') {
                        state = 3;
                    }
                    break;
            }

        }
        return state == 1 || state == 2 || state == 3;
    }

    public static void main(String[] args) {
        String stringa = "aabbbb";
        String stringa1 = "aababab";
        String stringa2 = "aabbab";
        String stringa3 = "ab";
        System.out.println("aabbbb = false: " + scan(stringa));
        System.out.println("aababab = true: " + scan(stringa1));
        System.out.println("aabbab = true: " + scan(stringa2));
        System.out.println("ab = true: " + scan(stringa3));

    }

}