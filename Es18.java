class Es18 {
    public static boolean scan(String numero) {
        int i = 0;
        int state = 0;
        boolean presenzaE = true;
        while (i < numero.length()) {
            char ch = numero.charAt(i++);

            switch (state) {
                case 0:
                    if (ch == '+' || ch == '-') {
                        state = 1;
                    } else {
                        return false;
                    }
                    break;
                case 1:
                    if (ch == '.') {
                        state = 2;
                    } else if (48 <= ch || ch <= 57) {
                        state = 4;
                    } else {
                        return false;
                    }
                    break;
                case 2:
                    if (48 <= ch || ch <= 57) {
                        state = 3;
                    } else {
                        return false;
                    }
                    break;
                case 3:
                    if ((!presenzaE) || ch != 'e' || (48 > ch || ch > 57)) {
                        return false;
                    } else if (presenzaE && ch == 'e') {
                        state = 3;
                        presenzaE = false;
                    } else {
                        state = 3;
                    }
                    break;
                case 4:
                    if (ch == '.') {
                        state = 2;
                    } else if (48 <= ch || ch <= 57) {
                        state = 4;
                    } else {
                        return false;
                    }
                    break;

            }

        }
        return state == 3 || state == 4;
    }

    public static void main(String[] args) {
        String numero = "+12.3";
        String numero1 = "-7.e3";
        String numero2 = "-2";
        String numero3 = "+.43";
        String numero4 = "+12e";
        String numero5 = "+1.2e3e";
        System.out.println("numero: " + numero5 + " false: " + scan(numero5));
        System.out.println("numero: " + numero + " true: " + scan(numero));
        System.out.println("numero: " + numero1 + " false: " + scan(numero1));
        System.out.println("numero: " + numero2 + " true: " + scan(numero2));
        System.out.println("numero: " + numero3 + " true: " + scan(numero3));
        System.out.println("numero: " + numero4 + " false: " + scan(numero4));
    }
}