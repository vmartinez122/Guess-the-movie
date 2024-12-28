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
    final String ANSI_GREEN = "\u001B[32m"; //Color verde
    final String ANSI_PURPLE = "\u001B[35m"; //Color morado
    final String ANSI_RESET = "\u001B[0m"; //Devolver color predeterminado

    Scanner input = new Scanner(System.in);
    Game game = new Game();
    Player player = new Player();
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
                    "\nThe movie title has "+game.countFilmChars()+" characters (including spaces and punctuation)"+
                    "\nYou are guessing: "+ANSI_PURPLE+game.getGuess()+ANSI_RESET+
                    "\nRemaining turns: "+ANSI_PURPLE+player.getTurns()+ANSI_RESET+
                    "\nPoints: "+ANSI_PURPLE+player.getPoints()+ANSI_RESET+'\n'+"""
                    [1] Guess a letter
                    [2] Guess the movie's title
                    [3] Exit""");
            switch (intFromConsole(1,3)){
                case 1: //Guess a letter
                    //System.out.println(ANSI_YELLOW+"Guess a letter:"+ANSI_RESET);
                    guessLetter();
                    break;
                case 2: //Guess the movie's title
                    //System.out.println(ANSI_YELLOW+"Guess the movie's title:"+ANSI_RESET);
                    guessTitle();
                    break;
                case 3: //Salir del programa
                    System.out.println("Exiting...");
                    //Show leaderboard
                    exit = true;
                    break;
                default:
                    //Valor erróneo
                    break;
            }
        } while(!exit);
    }

    private void guessLetter(){
        final int LETTER_POINTS = 10;
        do {
            System.out.println(ANSI_YELLOW+"Guess a letter:"+ANSI_RESET);
            //System.out.println("Insert your guess:");
            String letter = letterFromConsole();
            Answer a = game.addLetter(letter);
            if(a!=Answer.REPEAT) {
                if (a==Answer.CORRECT) {
                    //+10 points
                    System.out.println(game.getGuess()+
                            ANSI_GREEN+"\nCorrect. +"+ LETTER_POINTS +" points"+ANSI_RESET);
                    player.addPoints(LETTER_POINTS);
                }else{
                    //-10 points
                    System.out.println("Incorrect list: "+game.getErrorList()+
                            ANSI_RED+"\nIncorrect. -"+ LETTER_POINTS +" points"+ANSI_RESET);
                    player.addPoints(-LETTER_POINTS);
                }
                //-1 turn
                player.minusTurns();
                break;
            }else {
                System.out.println("Letter "+letter+" already guessed. Try again.");
                //Repite el bucle
            }
        }while (true);
    }

    public void guessTitle(){
        final int TITLE_POINTS = 20;
        System.out.println(ANSI_YELLOW+"Guess the movie's title:"+ANSI_RESET);
        //System.out.println("Guess movie's title:");
        if(game.compareTitle(stringFromConsole())){
            player.addPoints(TITLE_POINTS);
            //WIN GAME
            System.out.println("YOU WIN (WIP)");
        }else {
            player.addPoints(-TITLE_POINTS);
            //LOOSE GAME
            System.out.println("YOU LOOSE (WIP)");
        }
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
        System.out.println(ANSI_RED+"Invalid velue. Insert a number between [" + min + " - " + max + "]."+ANSI_RESET);
        return -1; //Si el número es inválido, el método devuelve -1, para que se vuelva a mostrar el menú
    }

    /**
     * Pide un String al usuario y devuelve el primer carácter, siempre que este sea 1 única letra
     * En caso contrario, vuelve a pedir otro String
     * El método devuelve un String en vez de char, debido a que el valor que devuelve,
     * será utilizado por una clase StringBuilder, cuyos métodos, utilizan mayoritiariamente String como parámetros
     * @return letra [a-z] en formato String
     */
    private String letterFromConsole(){
        String x;
        do {
            x = input.nextLine().toLowerCase(); //Queremos el input siempre en minúsculas
            if(x.length()==1&&x.matches("[a-z]")){ //Si el String x tiene una longidud de 1 carácter y contiene un carácter entre a-z
                return x; //Devuelve el primer carácter del string
            }
            System.out.println(ANSI_RED+"Error. Insert a single letter."+ANSI_RESET);
        }while (true);
    }

    /**
     * Comprueba que el usuario no inserte un string vacío, se repite hasta que el string tenga el formato correcto
     * @return String validado
     */
    private String stringFromConsole(){
        String x;
        do {
            x = input.nextLine();
            if(!x.isBlank()){
                return x;
            }
            System.out.println(ANSI_RED+"Error. Este campo no puede estar vacío."+ANSI_RESET);
        }while (true);
    }

}