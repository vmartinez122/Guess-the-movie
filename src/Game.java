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

    //Genera la palabra escondida
    //TODO leer archivo con nombres
    /**
     * Selecciona un título de forma aleatoria de la lista en un fichero de texto.
     * Añade carácteres al StringBuilder guess:
     * - Las letras formando el título se mostrarán como "*".
     * - Si el título contiene otros carácteres, se mostrarán.
     */
    public void create(){ //TODO could be in costructor
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
