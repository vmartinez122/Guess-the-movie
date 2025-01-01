import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Game {
    private String film = "aA-ab a"; //Nombre de la película
    private StringBuilder guess = new StringBuilder();
    private ArrayList<String> errorList = new ArrayList<>();

    //Getter nombre de la película
    public String getFilm() {
        return film;
    }

    //Devuelve guess como String (StringBuilder es impráctico)
    public String getGuess() {
        return guess.toString();
    }

    //Devuelve ErrorList como String
    public String getErrorList() {
        return ""+errorList;
    }

    //Devuelve la longitud del nombre de la película
    public int countFilmChars(){
        return film.length();
    }

    /**
     * Constructor de la clase Game, no tiene parámetros de entrada, pero se encarga de escoger una palabra aleatoria
     * del fichero movies.txt para la instancia de la clase.
     * A partir de esta palabra, genera un StringBuilder guess, ocultando la palabra:
     *      - Las letras formando el título se mostrarán como "*".
     *      - Si el título contiene otros carácteres, se mostrarán.
     */
    public Game() {
        File movies = new File("movies.txt");
        Random rand = new Random();
        int lines = countFileLines(movies); //Llama a ún método para saber cuantas líneas hay en el fichero
        int filmNum = 0; //Variable para iterar sobre las palabras del fichero

        //Escoje una película aleatoria
        try (Scanner writeMovies = new Scanner(movies)){
            int randFilm = rand.nextInt(1,lines+1); // La primera línea es 1, el valor máximo es excluido.
            while (writeMovies.hasNextLine()){
                filmNum++;
                if (filmNum == randFilm){
                    film = writeMovies.nextLine();
                    break;
                }
                writeMovies.nextLine(); //Limpiar bufer
            }
        } catch (Exception e) {
            System.out.println(e); //Excepciones en la lectura de ficheros
        }

        // Genera la palabra escondida
        for (int c = 0; c < film.length(); ++c) {
            if (!String.valueOf(film.charAt(c)).matches("(?i)[a-z]")) { //String.valueOf, permite convertie un char a String,
                // de esta manera, podemos comprobar que el carácter no sea una letra [a-z], regex flags: case insensitive (?i)
                // No funciona para carácteres con accentos
                guess.append(film.charAt(c)); //Añade el mismo carácter

            } else {
                guess.append('*'); //Añade un '*' en lugar de la letra
            }
        }
    }

    /**
     * Cuenta el número de líneas de un fichero File.
     * @param file Fichero sobre el que queremos contar.
     * @return número de líneas del ficehro, formato int.
     */
    private int countFileLines(File file){
        int lines = 0;
        try (Scanner countLines = new Scanner(file)){
            while (countLines.hasNextLine()){
                lines++; //Lines incrementa en cada línea del fichero
                countLines.nextLine(); // Avanza a la siguiente línea, evita un bucle infinito
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lines;
    }

    /**
     * Comprueba si una letra está dentro del nombre de la película, no lo está o ya se ha comprobado anteriormente.
     * @param answ letra que comprobamos.
     * @return Enum Answer con 3 posibles valores, CORRECT, INCORRECT o REPEAT.
     */
    public Answer addLetter(String answ){
        int pos = 0; //Posición de la letra
        if(guess.toString().toLowerCase().contains(answ)||errorList.contains(answ)) { //Si el jugador repite una letra
            return Answer.REPEAT;
        }else{
                if (film.toLowerCase().contains(answ)) { //Si el nombre de la película contiene esta letra
                    do {
                        pos = film.toLowerCase().indexOf(answ, pos); //Almacena la posición de la letra indicada empezando por la posición indicada
                        //En la primera iteración de este bucle, la posición es 0
                        //En las siguentes iteraciones, la posición será la siguiente a la de la última letra encontrada.
                        //Si no encuentra la letra, devuelve -1
                        if (pos != -1) {
                            guess.replace(pos,pos+1,String.valueOf(film.charAt(pos)));
                            //Reemplaza el String entre pos(incluído) y pos+1(excluído)
                            //por el char en film. No utilizamos answ para mantener las lteras mayúsculas.
                            ++pos; //La función indexOf empezará a buscar por la siguiente letra a esta.
                        }
                    } while (pos != -1); //No hay más letras iguales a la introducida
                    return Answer.CORRECT;
                } else {
                    errorList.add(answ); //Añadimos la letra al arrayList de letras incorrectas
                    return Answer.INCORRECT;
                }
        }
    }

    /**
     * Comprueba que un String sea igual al título de la película, ignorando mayúsculas en ambos utilizando la función equalsIgnoreCase()
     * @param answ String a comparar
     * @return Valor booleano resultado de esta operación
     */
    public boolean compareTitle(String answ){
        return answ.equalsIgnoreCase(film);
    }
}
