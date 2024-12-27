import java.util.Scanner;
//    Desarrolla un juego en Java donde se le propondrá al usuario adivinar el titulo de una película dando las
//    letras que lo forman. Es como un ahorcado
//    Tu aplicación seleccionará un título de forma aleatoria de la lista que haya en un fichero de texto (lo
//    tienes que crear tú y guardarlo en el workspace del proyecto), y mostrará por consola de cuantas letras
//    está formado el título usando *. Si el título contiene otros caracteres que no sean letras (espacios en
//    blanco, guiones, etc) se mostrarán, solamente usaremos * para las letras.
//    El fichero de texto debe contener el título de al menos 10 películas distintas.

public class Main {
    final String ANSI_RED = "\u001B[31m"; //Color rojo
    final String ANSI_YELLOW = "\u001B[33m"; //Color amarillo
    final String ANSI_RESET = "\u001B[0m"; //Devolver color predeterminado
    Scanner input = new Scanner(System.in);
    Game game = new Game();
    public static void main(String[] args)
    {
        Main programa = new Main();
        programa.start();
    }
    private void start(){
        game.create();
        menu();
    }

    private void menu(){
        boolean exit = false; //Variable que cierra el menú
        do {
            System.out.println(ANSI_YELLOW+"🎯🎯🎯 Guess the Movie 🎯🎯🎯"+ANSI_RESET+
                    "\nThe movie title has "+"TODO"+" characters (including spaces and punctuation)"+
                    "\nYou are guessing: "+game.getGuess()+
                    "\nRemaining turns: "+"TODO"+
                    "\nPoints: "+"TODO"+'\n'+"""
                    [1] Guess a letter
                    [2] Guess the movie's title
                    [3] Exit""");
            switch (intFromConsole(1,3)){
                case 1: //Guess a letter
                    System.out.println(ANSI_YELLOW+"Guess a letter:"+ANSI_RESET);

                    break;
                case 2: //Guess the movie's title
                    System.out.println(ANSI_YELLOW+"Guess the movie's title:"+ANSI_RESET);

                    break;
                case 3: //Salir del programa
                    System.out.println("Exiting...");
                    exit = true;
                    break;
                default:
                    //Valor erróneo
                    break;
            }
        } while(!exit);
    }

    /**
     * Verifica el input del usuario, para verificar que és una integer entre un rango de valores
     * @param min Valor mínimo de la integer
     * @param max Valor máximo de la integer
     * @return Integer validada, devuelve -1 si el input és inválido
     */
    private int intFromConsole(int min, int max) {
        int x;
        if (input.hasNextInt()) {
            x = input.nextInt();
            if (x >= min && x <= max) {
                input.nextLine(); //Limpiar búfer
                return x;
            }
        }
        input.nextLine(); //Limpiar búfer
        System.out.println(ANSI_RED+"Valor inválido. Introduce un número [" + min + " - " + max + "]"+ANSI_RESET);
        return -1; //Si el número es inválido, el método devuelve -1, para que se vuelva a mostrar el menú
    }
}