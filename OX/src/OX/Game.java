package OX;
import java.util.InputMismatchException;
import java.util.Random;

import java.util.Scanner;

public class Game {
    // POLA
    public static char CPLAYER = 'X';
    public static char CCPU = 'O';

    private static char[] MAP = new char[9];
    public static char[] CH = {' ', CPLAYER, CCPU};
    private static Scanner SCAN  = new Scanner(System.in);
    private static Random RAND = new Random();
    private static int PMS;
    private static int FIRSTPLAYER;
    public static boolean SHOWLABELS = false;
    private static int RESULT;

    // URUCHAMIANIE GRY
    public static void start(){
        initializeMap();
        PMS = 1;
        RESULT = 0;

        FIRSTPLAYER = randomPlayer();
        System.out.println(" [ oxo ] Zawsze możesz się poddać, podając za pole wartość 0 [ xox ]");
        switch (FIRSTPLAYER){
            case 0:
                System.out.println();
                System.out.println(" [ oxo ] Rozpoczyna GRACZ [ xox ]");
                while(RESULT == 0){
                    paintMap();
                    playerMove();
                    if(RESULT == 2 || RESULT == 3) break;
                    RESULT = checkWin();
                    if (RESULT == 1) continue;
                    paintMap();
                    aiMove();
                    RESULT = checkWin();
                }
                break;
            case 1:
                System.out.println();
                System.out.println(" [ oxo ] Rozpoczyna KOMPUTER [ xox ]");
                while(RESULT == 0){
                    paintMap();
                    aiMove();
                    RESULT = checkWin();
                    if (RESULT == 2) continue;
                    paintMap();
                    playerMove();
                    if (RESULT == 2 || RESULT == 3) break;
                    RESULT = checkWin();
                }
                break;
        }


        switch(RESULT){
            case 1:
                System.out.println("\n\n [ OXO ] Wygrywa GRACZ [ XOX ]");
                break;
            case 2:
                System.out.println("\n\n [ OXO ] Wygrywa KOMPUTER [ XOX ]");
                break;
            case 3:
                System.out.println("\n\n [ OXO ] REMIS [ XOX ]");
                break;
        }
    }

    // LOSUJ GRACZA 0 -> PLAYER, 1 -> AI
    private static int randomPlayer(){
        int[] m = {0,1};
        return m[RAND.nextInt(2)];
    }
    // INICJALIZUJ MAPĘ
    private static void initializeMap(){
        for(int i=0; i<9; i++) MAP[i] = ' ';
    }
    // WYPISZ MAPĘ
    public static void paintMap(){
        if(SHOWLABELS){
            System.out.println();
            for(int i=0; i<3; i++) System.out.print("  " + (i+1) + "[  " + MAP[i] + "  ]  ");
            System.out.println();
            System.out.println();
            for(int i=3; i<6; i++) System.out.print("  " + (i+1) + "[  " + MAP[i] + "  ]  ");
            System.out.println();
            System.out.println();
            for(int i=6; i<9; i++) System.out.print("  " + (i+1) + "[  " + MAP[i] + "  ]  ");
            System.out.println();
            System.out.println();
        }
        else{
            System.out.println();
            for(int i=0; i<3; i++) System.out.print("  [  " + MAP[i] + "  ]  ");
            System.out.println();
            System.out.println();
            for(int i=3; i<6; i++) System.out.print("  [  " + MAP[i] + "  ]  ");
            System.out.println();
            System.out.println();
            for(int i=6; i<9; i++) System.out.print("  [  " + MAP[i] + "  ]  ");
            System.out.println();
            System.out.println();
        }
    }
    // RUCH GRACZA
    private static void playerMove(){
        int move = -1;
        do {
            do{
                String data;
                while(true){
                    System.out.print(" [ oxo ] Twój ruch > ");
                    data = SCAN.nextLine();
                    if(data.matches("\\d+")) break;
                    System.out.println(" [ oxo ] Podaj numer pola, nie znak! [ xox ]");
                }
                move = Integer.parseInt(data);
            }while(!checkRange(-1, 10, move));

            if(move == 0){
                System.out.println(" [ OXO ] Poddałeś się [ XOX ]");
                RESULT = 2;
                break;
            }
            if(!isEmpty(move)) System.out.println(" [ oxo ] Pole " + move + " jest już zajęte [ xox ]");
        }while(!isEmpty(move));
        if(move != 0) setOnMap(move, CPLAYER);
    }
    // RUCH AI
    private static void aiMove(){
        int move = 0;

        switch(PMS){
            case 1:
                if (FIRSTPLAYER == 1){
                    setOnMap(7, CCPU);
                    move = 7;
                }
                else PMS--;
                break;
            case 2:
                if (isEmpty(5)){
                    setOnMap(5, CCPU);
                    move = 5;
                }
                else{
                    setOnMap(1, CCPU);
                    move = 1;
                }
                break;

            case 3:
                if(isEmpty(5)){
                    setOnMap(5, CCPU);
                    move = 5;
                }
                else{
                    setOnMap(6, CCPU);
                    move = 6;
                }
                break;

            default:
                // RUCHY PROWADZĄCE DO WYGRANEJ
                if ( ((getChar(2) == CCPU) && (getChar(3) == CCPU)
                  || (getChar(4) == CCPU) && (getChar(7) == CCPU)
                  || (getChar(5) == CCPU) && (getChar(9) == CCPU))
                  && isEmpty(1)){
                    setOnMap(1, CCPU);
                    move = 1;
                }
                else
                if ( ((getChar(1) == CCPU) && (getChar(3) == CCPU)
                  || (getChar(5) == CCPU) && (getChar(8) == CCPU))
                  && isEmpty(2)){
                    setOnMap(2, CCPU);
                    move = 2;
                }
                else
                if ( ((getChar(1) == CCPU) && (getChar(2) == CCPU)
                  || (getChar(6) == CCPU) && (getChar(9) == CCPU)
                  || (getChar(7) == CCPU) && (getChar(5) == CCPU))
                  && isEmpty(3)){
                    setOnMap(3, CCPU);
                    move = 3;
                }



                else
                if ( ((getChar(1) == CCPU) && (getChar(7) == CCPU)
                  || (getChar(5) == CCPU) && (getChar(6) == CCPU)
                  || (getChar(7) == CCPU) && (getChar(5) == CCPU))
                  && isEmpty(4)){
                    setOnMap(4, CCPU);
                    move = 4;
                }
                else
                if ( ((getChar(1) == CCPU) && (getChar(9) == CCPU)
                  || (getChar(2) == CCPU) && (getChar(8) == CCPU)
                  || (getChar(3) == CCPU) && (getChar(7) == CCPU)
                  || (getChar(4) == CCPU) && (getChar(6) == CCPU))
                  && isEmpty(5)){
                    setOnMap(5, CCPU);
                    move = 5;
                }
                else
                if ( ((getChar(4) == CCPU) && (getChar(5) == CCPU)
                  || (getChar(3) == CCPU) && (getChar(9) == CCPU))
                  && isEmpty(6)){
                    setOnMap(6, CCPU);
                    move = 6;
                }



                else
                if ( ((getChar(1) == CCPU) && (getChar(4) == CCPU)
                  || (getChar(3) == CCPU) && (getChar(5) == CCPU)
                  || (getChar(8) == CCPU) && (getChar(9) == CCPU))
                  && isEmpty(7)){
                    setOnMap(7, CCPU);
                    move = 7;
                }
                else
                if ( ((getChar(2) == CCPU) && (getChar(5) == CCPU)
                  || (getChar(7) == CCPU) && (getChar(9) == CCPU))
                  && isEmpty(8)){
                    setOnMap(8, CCPU);
                    move = 8;
                }
                else
                if ( ((getChar(7) == CCPU) && (getChar(8) == CCPU)
                  || (getChar(3) == CCPU) && (getChar(6) == CCPU)
                  || (getChar(1) == CCPU) && (getChar(5) == CCPU))
                  && isEmpty(9)){
                    setOnMap(9, CCPU);
                    move = 9;
                }


                // RUCHY BLOKUJĄCE
                else
                if ( ((getChar(2) == CPLAYER) && (getChar(3) == CPLAYER)
                  || (getChar(4) == CPLAYER) && (getChar(7) == CPLAYER)
                  || (getChar(5) == CPLAYER) && (getChar(9) == CPLAYER))
                  && isEmpty(1)){
                    setOnMap(1, CCPU);
                    move = 1;
                }
                else
                if ( ((getChar(1) == CPLAYER) && (getChar(3) == CPLAYER)
                  || (getChar(5) == CPLAYER) && (getChar(8) == CPLAYER))
                  && isEmpty(2)){
                    setOnMap(2, CCPU);
                    move = 2;
                }
                else
                if ( ((getChar(1) == CPLAYER) && (getChar(2) == CPLAYER)
                  || (getChar(6) == CPLAYER) && (getChar(9) == CPLAYER)
                  || (getChar(7) == CPLAYER) && (getChar(5) == CPLAYER))
                  && isEmpty(3)){
                    setOnMap(3, CCPU);
                    move = 3;
                }


                else
                if ( ((getChar(1) == CPLAYER) && (getChar(7) == CPLAYER)
                  || (getChar(5) == CPLAYER) && (getChar(6) == CPLAYER))
                  && isEmpty(4)){
                    setOnMap(4, CCPU);
                    move = 4;
                }
                else
                if ( ((getChar(1) == CPLAYER) && (getChar(9) == CPLAYER)
                  || (getChar(2) == CPLAYER) && (getChar(8) == CPLAYER)
                  || (getChar(3) == CPLAYER) && (getChar(7) == CPLAYER)
                  || (getChar(4) == CPLAYER) && (getChar(6) == CPLAYER))
                  && isEmpty(5)){
                    setOnMap(5, CCPU);
                    move = 5;
                }
                else
                if ( ((getChar(4) == CPLAYER) && (getChar(5) == CPLAYER)
                  || (getChar(3) == CPLAYER) && (getChar(9) == CPLAYER)
                  || (getChar(3) == CPLAYER) && (getChar(7) == CPLAYER)
                  || (getChar(1) == CPLAYER) && (getChar(9) == CPLAYER))
                  && isEmpty(6)){
                    setOnMap(6, CCPU);
                    move = 6;
                }



                else
                if ( ((getChar(1) == CPLAYER) && (getChar(4) == CPLAYER)
                  || (getChar(3) == CPLAYER) && (getChar(5) == CPLAYER)
                  || (getChar(8) == CPLAYER) && (getChar(9) == CPLAYER))
                  && isEmpty(7)){
                    setOnMap(7, CCPU);
                    move = 7;
                }
                else
                if ( ((getChar(2) == CPLAYER) && (getChar(5) == CPLAYER)
                  || (getChar(7) == CPLAYER) && (getChar(9) == CPLAYER))
                  && isEmpty(8)){
                    setOnMap(8, CCPU);
                    move = 8;
                }
                else
                if ( ((getChar(7) == CPLAYER) && (getChar(8) == CPLAYER)
                  || (getChar(3) == CPLAYER) && (getChar(6) == CPLAYER)
                  || (getChar(1) == CPLAYER) && (getChar(5) == CPLAYER))
                  && isEmpty(9)){
                    setOnMap(9, CCPU);
                    move = 9;
                }

                // POZOSTAŁE
                else
                if (isEmpty(9)){
                    setOnMap(9, CCPU);
                    move = 9;
                }
                else
                if (isEmpty(1)){
                    setOnMap(1, CCPU);
                    move = 1;
                }
                else
                if (isEmpty(3)){
                    setOnMap(3, CCPU);
                    move = 3;
                }

                else
                if (isEmpty(4)){
                    setOnMap(4, CCPU);
                    move = 4;
                }
                else
                if (isEmpty(6)){
                    setOnMap(6, CCPU);
                    move = 6;
                }
                else
                if (isEmpty(7)){
                    setOnMap(7, CCPU);
                    move = 7;
                }

                else
                if (isEmpty(2)){
                    setOnMap(2, CCPU);
                    move = 2;
                }
                else
                if (isEmpty(5)){
                    setOnMap(5, CCPU);
                    move = 5;
                }
                else
                if (isEmpty(8)){
                    setOnMap(8, CCPU);
                    move = 8;
                }
        }

        System.out.print(" [ oxo ] Ruch AI -> " + move);
    }
    // CZY PUSTE POLE
    private static boolean isEmpty(int pos){
        if(MAP[pos-1] == CH[0]) return true;
        else return false;
    }
    // WSTAW DO MAPY
    private static void setOnMap(int pos, char ch){
        MAP[pos-1] = ch;
    }
    // SPRAWDZ ZASIEG
    private static boolean checkRange(int begin, int end, int data){
        if(data > begin && data < end) return true;
        else return false;
    }
    // WEZ ZNAK
    private static char getChar(int pos){
        return MAP[pos-1];
    }
    // SPRAWDŹ WYGRANĄ
    private static int checkWin(){
        // CZY WYGRAŁ GRACZ?
        // [ X ] [ X ] [ X ]
        // [   ] [   ] [   ]
        // [   ] [   ] [   ]
        if( (MAP[0] == CH[1]) && (MAP[1] == CH[1]) && (MAP[2] == CH[1]) ) return 1;

        // CZY WYGRAŁ GRACZ?
        // [   ] [   ] [   ]
        // [ X ] [ X ] [ X ]
        // [   ] [   ] [   ]
        if( (MAP[3] == CH[1]) && (MAP[4] == CH[1]) && (MAP[5] == CH[1]) ) return 1;

        // CZY WYGRAŁ GRACZ?
        // [   ] [   ] [   ]
        // [   ] [   ] [   ]
        // [ X ] [ X ] [ X ]
        if( (MAP[6] == CH[1]) && (MAP[7] == CH[1]) && (MAP[8] == CH[1]) ) return 1;

        // CZY WYGRAŁ GRACZ?
        // [ X ] [   ] [   ]
        // [ X ] [   ] [   ]
        // [ X ] [   ] [   ]
        if( (MAP[0] == CH[1]) && (MAP[3] == CH[1]) && (MAP[6] == CH[1]) ) return 1;

        // CZY WYGRAŁ GRACZ?
        // [   ] [ X ] [   ]
        // [   ] [ X ] [   ]
        // [   ] [ X ] [   ]
        if( (MAP[1] == CH[1]) && (MAP[4] == CH[1]) && (MAP[7] == CH[1]) ) return 1;

        // CZY WYGRAŁ GRACZ?
        // [   ] [   ] [ X ]
        // [   ] [   ] [ X ]
        // [   ] [   ] [ X ]
        if( (MAP[2] == CH[1]) && (MAP[5] == CH[1]) && (MAP[8] == CH[1]) ) return 1;

        // CZY WYGRAŁ GRACZ?
        // [ X ] [   ] [   ]
        // [   ] [ X ] [   ]
        // [   ] [   ] [ X ]
        if( (MAP[0] == CH[1]) && (MAP[4] == CH[1]) && (MAP[8] == CH[1]) ) return 1;

        // CZY WYGRAŁ GRACZ?
        // [   ] [   ] [ X ]
        // [   ] [ X ] [   ]
        // [ X ] [   ] [   ]
        if( (MAP[2] == CH[1]) && (MAP[4] == CH[1]) && (MAP[6] == CH[1]) ) return 1;

        // CZY WYGRAŁ KOMPUTER?
        // [ O ] [ O ] [ O ]
        // [   ] [   ] [   ]
        // [   ] [   ] [   ]
        if( (MAP[0] == CH[2]) && (MAP[1] == CH[2]) && (MAP[2] == CH[2]) ) return 2;

        // CZY WYGRAŁ KOMPUTER?
        // [   ] [   ] [   ]
        // [ O ] [ O ] [ O ]
        // [   ] [   ] [   ]
        if( (MAP[3] == CH[2]) && (MAP[4] == CH[2]) && (MAP[5] == CH[2]) ) return 2;

        // CZY WYGRAŁ KOMPUTER?
        // [   ] [   ] [   ]
        // [   ] [   ] [   ]
        // [ O ] [ O ] [ O ]
        if( (MAP[6] == CH[2]) && (MAP[7] == CH[2]) && (MAP[8] == CH[2]) ) return 2;

        // CZY WYGRAŁ KOMPUTER?
        // [ O ] [   ] [   ]
        // [ O ] [   ] [   ]
        // [ O ] [   ] [   ]
        if( (MAP[0] == CH[2]) && (MAP[3] == CH[2]) && (MAP[6] == CH[2]) ) return 2;

        // CZY WYGRAŁ KOMPUTER?
        // [   ] [ O ] [   ]
        // [   ] [ O ] [   ]
        // [   ] [ O ] [   ]
        if( (MAP[1] == CH[2]) && (MAP[4] == CH[2]) && (MAP[7] == CH[2]) ) return 2;

        // CZY WYGRAŁ KOMPUTER?
        // [   ] [   ] [ O ]
        // [   ] [   ] [ O ]
        // [   ] [   ] [ O ]
        if( (MAP[2] == CH[2]) && (MAP[5] == CH[2]) && (MAP[8] == CH[2]) ) return 2;

        // CZY WYGRAŁ KOMPUTER?
        // [ O ] [   ] [   ]
        // [   ] [ O ] [   ]
        // [   ] [   ] [ O ]
        if( (MAP[0] == CH[2]) && (MAP[4] == CH[2]) && (MAP[8] == CH[2]) ) return 2;

        // CZY WYGRAŁ KOMPUTER?
        // [   ] [   ] [ O ]
        // [   ] [ O ] [   ]
        // [ O ] [   ] [   ]
        if( (MAP[2] == CH[2]) && (MAP[4] == CH[2]) && (MAP[6] == CH[2]) ) return 2;

        // CZY REMIS?
        int count = 0;
        for (int i=0; i<9; i++){
            if(MAP[i] != ' ') count++;
        }
        System.out.println("test -> " + count);
        if( count==9 ) return 3;

        // WOLNE POLA -> GRAJ DALEJ
        PMS++;
        System.out.println("pms -> " + PMS);
        return 0;
    }
}
