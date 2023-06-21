class Es17 {
    public static boolean scan(String nome) {
        int i = 0;
        int state = 0;
        boolean verifica = true;
        while (i < nome.length() && state != 4) {
            char ch = nome.charAt(i++);
            switch (state) {
                case 0:
                    if (ch == 'v') {
                        state = 1;
                    } else {
                        verifica = false;
                        state = 1;
                    }
                    break;
                case 1:
                    if (ch == 'a') {
                        state = 2;
                    } else {
                        if (!verifica) {
                            return false;
                        } else {
                            verifica = false;
                            state = 2;
                        }
                    }
                    break;
                case 2:
                    if (ch == 'l') {
                        state = 3;
                    } else {
                        if (!verifica) {
                            return false;
                        } else {
                            verifica = false;
                            state = 3;
                        }
                    }
                    break;
                case 3:
                    if (ch == 'e') {
                        state = 4;
                    } else {
                        if (!verifica) {
                            return false;
                        } else {
                            verifica = false;
                            state = 4;
                        }
                    }
                    break;
            }
        }
        return state == 4;
    }

    public static void main(String[] args) {
        String nome1 = "vale";
        String nome2 = "gale";
        String nome3 = "vajy";
        String nome4 = "vdle";
        System.out.println("nome:" + nome1 + " true: " + scan(nome1));
        System.out.println("nome:" + nome2 + " true: " + scan(nome2));
        boolean debug = scan(nome3);
        System.out.println("nome:" + nome3 + " falso: " + debug/* scan(nome3) */);
        System.out.println("nome:" + nome4 + " true: " + scan(nome4));
    }

}
