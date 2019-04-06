package App;

import OX.Menu;

public class Main {
    public static void main(String[] args){
        Menu.paintMenu();
    }
    public final static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");
            Runtime.getRuntime().exec("cls");

            if (os.contains("Windows"))
            {
                try
                {
                    new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
                }catch(Exception E)
                {
                    System.out.println(E);
                }
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {

        }
    }
}
