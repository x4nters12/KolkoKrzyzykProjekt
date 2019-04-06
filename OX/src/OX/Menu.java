package OX;
import App.Main;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
    private static Scanner SCAN = new Scanner(System.in);
    private static int OPTION = -1;
    private static String LABELS;
    public static void paintMenu(){
        while(OPTION != 0){
            Main.clearConsole();
            if(Game.SHOWLABELS) LABELS = "Włączone"; else LABELS = "Wyłączone";
            System.out.println();
            System.out.println("       oO|           xX|                    === Kółko & krzyżyk ===");
            System.out.println("         oO|       xX|           [ 1 ] Rozpocznij nową grę ");
            System.out.println("           oO|   xX|             [ 2 ] Zamień znaki ( gracz->[ " + Game.CPLAYER + " ] ai->[ " + Game.CCPU + " ] )");
            System.out.println("            = OX =               [ 3 ] Pokaż znaczniki pól ( " + LABELS + " )");
            System.out.println("           xX|   oO|             [ 0 ] Zakończ");
            System.out.println("         xX|       oO|                      -----------------------");
            System.out.print(  "       xX|           oO|         MENU> ");
            OPTION = SCAN.nextInt();
            System.out.println();
            menuSwitch();
        }
    }


    private static void menuSwitch(){
        switch (OPTION){
            case 1:
                Game.start();
                System.out.println(" [ oxo ] Końcowa mapa gry [ xox ]");
                Game.paintMap();
                System.out.print(" [ oxo ] Wciśnij ENTER, aby kontynuować [xox]");
                try { System.in.read(); } catch (IOException ex) { }
                break;
            case 2:
                switchOX();
                break;
            case 3:
                switchLABELS();
                break;
            case 0:
                break;
            default:
                System.out.println(" [ ! ] Niepoprawna opcja");
                break;
        }
    }

    private static void switchOX(){
        if(Game.CPLAYER == 'X') Game.CPLAYER = 'O';
        else Game.CPLAYER = 'X';

        if(Game.CCPU == 'O') Game.CCPU = 'X';
        else Game.CCPU = 'O';

        Game.CH[1] = Game.CPLAYER;
        Game.CH[2] = Game.CCPU;
    }

    private static void switchLABELS(){
        if(Game.SHOWLABELS) Game.SHOWLABELS = false;
        else Game.SHOWLABELS = true;
    }
}
