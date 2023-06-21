class TurniMatricole {

    public static boolean scan(String matricola) {
        int i = 0;
        int state = 0;
        while ((state != 3 || state != 4) && i < matricola.length()) {
            char ch = matricola.charAt(i++);
            int n = (int) ch; // per fare controlli sui numeri
            // state==1 per il numero se è pari
            // state==2 per il numero se è dispari

            switch (state) {
                case 0: // gestione del caso in cui sia un numero
                    if (48 <= ch && ch <= 57) {
                        if (n % 2 == 0) {
                            state = 1;
                        } else {
                            state = 2;
                        }
                    }
                    break;

                case 1: // significa se che il numero precedente è pari allora due casi
                    if (48 <= ch && ch <= 57) { // ancora un numero
                        if ((n % 2 == 0)) {
                            state = 1;
                        } else {
                            state = 2;
                        }

                    } else if (65 <= ch && ch <= 75) {
                        state = 3;
                    }
                    break;

                case 2:
                    if (48 <= ch && ch <= 57) { // se è ancora un numero
                        if ((n % 2 == 0)) {
                            state = 1;
                        } else {
                            state = 2;
                        }
                    } else if (76 <= ch && ch <= 90) {
                        state = 4;
                    }
                    break;
            }

        }

        return state == 3 || state == 4;
    }

    public static void main(String[] args) {
        // Scanner s = new Scanner(Sistem.in);
        String matricola = "1234Bianchi";// s.nextLine();
        String matricola2 = "1233Gianchi";

        System.out.println("true:" + scan(matricola));
        System.out.println("false:" + scan(matricola2));

    }

}