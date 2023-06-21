class Es15 {

    public static boolean scan(String matricola) {
        int i = 0;
        int state = 0;
        while (i < matricola.length()) {
            char ch = matricola.charAt(i++);
            int n = (int) ch; // per fare controlli sui numeri
            // state==1 per il numero se è pari
            // state==2 per il numero se è dispari

            switch (state) {
                case 0: // una lettera due casi
                    if (65 <= ch && ch <= 75) {
                        state = 1;
                    } else if (76 <= ch && ch <= 90) {
                        state = 2;
                    }
                    break;

                case 1:// ho tovato una lettera A-K
                       // se trovo altre lettere ciclo
                    if (48 <= ch && ch <= 57) {
                        if (n % 2 == 0) {
                            state = 1;
                        } else {
                            state = 3;
                        }
                    }

                    break;

                case 2:
                    if (48 <= ch && ch <= 57) {
                        if (n % 2 == 0) {
                            state = 2;
                        } else {
                            state = 4;
                        }
                    }

                    break;
                case 3: // significa che il numero è dispari
                        // e si trova tra A-K
                    if (n % 2 == 0) {
                        state = 1;
                    } else {
                        state = 3;
                    }
                    break;
                case 4:
                    if (n % 2 == 0) {
                        state = 2;
                    } else {
                        state = 4;
                    }
                    break;

            }

        }

        return state == 1 || state == 4;
    }

    public static void main(String[] args) {
        // Scanner s = new Scanner(Sistem.in);
        String matricola = "Bianchi1234";// s.nextLine();
        String matricola2 = "Gianchi1233";

        System.out.println("true:" + scan(matricola));
        System.out.println("false:" + scan(matricola2));

    }

}