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
    Player player = new Player(10);
    boolean win = false; //Indica si el jugador ha ganado

    public static void main(String[] args)
    {
        Main programa = new Main();
        programa.start();
    }

    private void start(){
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
                    guessLetter();
                    break;
                case 2: //Guess the movie's title
                    guessTitle();
                    exit = true;
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
            //Condiciones de final de partida (el jugador ha ganado o se ha quedado sin turnos):
            if(win||player.getTurns()<=0){
                exit = true;
            }
        } while(!exit);
        gameEnd();
    }

    /**
     * Permite al usuario insertar una letra a adivinar:
     * Si la letra existe en el título, se muestra la letra añadida y se le suman 10 puntos.
     * Si la letra no existe en el título, se muestra la lista de letras incorrectas y se le restan 10 puntos.
     * Si la letra se ha intentado adivinar previamente, se le vuelve a preguntar otra.
     */
    private void guessLetter(){
        final int LETTER_POINTS = 10;
        do {
            System.out.println(ANSI_YELLOW+"Guess a letter:"+ANSI_RESET);
            //System.out.println("Insert your guess:");
            String letter = letterFromConsole();
            Answer a = game.addLetter(letter); //El método addLetter, devuelve un enum con los 3 posibles resultados
            if(a!=Answer.REPEAT) {
                if (a==Answer.CORRECT) {
                    //+10 points
                    System.out.println(colorChanges(game.getGuess(), ANSI_GREEN, letter.charAt(0))+
                            ANSI_GREEN+"\nCorrect. +"+ LETTER_POINTS +" points"+ANSI_RESET);
                    player.addPoints(LETTER_POINTS);
                    //Comprueba si el jugadoir ha ganado, es decir, no quedan letras a adivinar ("*")
                    if(!game.getGuess().contains("*")){
                        win = true; //Condición fin de juego
                    }
                }else{
                    //-10 points
                    System.out.println("Incorrect letters: "+colorChanges(game.getErrorList(), ANSI_RED, letter.charAt(0))+
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

    /**
     * Cambia los carácteres letter de un string al color designado
     * Changes every letter on a string to the desired color
     * @param text String a formatear
     * @param color Color de la letra
     * @param letter Letra a colorear
     * @return texto con letras a color
     */
    private String colorChanges(String text,String color, char letter){
        String coloredText = "";
        for (int i = 0; i < text.length(); i++) { //Recorre el String text
            char c = text.charAt(i);
            if (Character.toLowerCase(c)==letter){ //Si es la letra que queremos
                coloredText=coloredText.concat(color);//Añade color antes de concatenar el carácter
            }
            coloredText=coloredText.concat(c+ANSI_RESET);
        }
        return coloredText;
    }

    /**
     * Comprueba que el título sea igual al introducido por el usuario, restando o sumando 20 puntos.
     */
    public void guessTitle(){
        final int TITLE_POINTS = 20;
        System.out.println(ANSI_YELLOW+"Guess the movie's title:"+ANSI_RESET);
        //System.out.println("Guess movie's title:");
        if(game.compareTitle(stringFromConsole())){
            player.addPoints(TITLE_POINTS);
            //WIN GAME
            win = true;
        }else {
            player.addPoints(-TITLE_POINTS);
            //LOOSE GAME
        }
    }

    /**
     * Muestra un mensaje indicando si el jugador ha ganado o perdido, junto con el nombre de la película y número total de puntos.
     * Después, pide un nombre para intentar introducir la puntuación a la leaderboard (tabla de puntuaciones) y la muestra por pantalla.
     */
    public void gameEnd(){
        if(win) {
            System.out.println(ANSI_GREEN + "YOU WON!" + ANSI_RESET);
        }else {
            System.out.println(ANSI_RED + "YOU LOST!" + ANSI_RESET);
        }
        System.out.println("Name of the film: "+ANSI_PURPLE+game.getFilm()+ANSI_RESET+
                "\nTotal points: "+ANSI_PURPLE+player.getPoints()+ANSI_RESET);

        //Añadir puntuación a leaderboard
        do {
            System.out.println("Nickname: ");
            String name = stringFromConsole();
            if (!player.validName(name)) {
                System.out.println(ANSI_RED+"Name unavailable. Try again"+ANSI_RESET);
            }else {
                player.updateLeaderboard(name);
                break;
            }
        }while(true);

        //Mostrar leaderboard
        System.out.println(ANSI_YELLOW+"Leaderboard:"+ANSI_RESET);
        printLeaderboard();
    }

    /**
     * Recoje el array con las puntuaciones, y itera sobre este para mostrar las puntuaciones no null.
     */
    private void printLeaderboard() {
        int pos =1; //Posición de la puntuación en la lista
        for (String[] row : player.getLeaderboard()){
            if (row[1]==null){ //Las puntuacines null, no se muestran
               break;
            }
            System.out.println(pos +"# "+row[0]+": "+ANSI_PURPLE+row[1]+ANSI_RESET);
            ++pos;
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
        System.out.println(ANSI_RED+"Invalid value. Insert a number between [" + min + " - " + max + "]."+ANSI_RESET);
        return -1; //Si el número es inválido, el método devuelve -1, para que se vuelva a mostrar el menú
    }

    /**
     * Pide un String al usuario y devuelve el primer carácter, siempre que este sea 1 única letra.
     * En caso contrario, vuelve a pedir otro String.
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
            System.out.println(ANSI_RED+"Error. Field cannot be empty."+ANSI_RESET);
        }while (true);
    }

}