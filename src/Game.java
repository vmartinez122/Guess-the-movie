import java.util.ArrayList;

public class Game {
    private String film = "aA-ab a"; //Nombre de la película
    private StringBuilder guess = new StringBuilder();
    private ArrayList<String> errorList = new ArrayList<>();

    public StringBuilder getGuess() {
        return guess;
    }

    public ArrayList<String> getErrorList() {
        return errorList;
    }

    //Genera la palabra escondida
    //TODO leer archivo con nombres
    /**
     * Selecciona un título de forma aleatoria de la lista en un fichero de texto
     * Añade carácteres al StringBuilder guess:
     * - Las letras formando el título se mostrarán como "*"
     * - Si el título contiene otros carácteres, se mostrarán.
     */
    public void create(){
        for (int c = 0; c < film.length(); ++c) {
            if (!String.valueOf(film.charAt(c)).matches("(?i)[a-z]")) { //String.valueOf, permite convertie un char a String,
                // de esta manera, podemos comprobar que el carácter no sea una letra [a-z], regex flags: case insensitive (?i)
                guess.append(film.charAt(c)); //Añade el mismo carácter

            } else {
                guess.append('*'); //Añade un '*' en lugar de la letra
            }
        }
    }

    /**
     *
     * @param answ
     * @return
     */
    public Answer addLetter(String answ){
        int pos = 0; //Posición de la letra
        if(guess.toString().contains(answ)||errorList.contains(answ)) { //Si el jugador repite una letra
            return Answer.REPEAT;
        }else{
                if (film.contains(answ)) { //Si el nombre de la película contiene esta letra
                    do {
                        pos = film.indexOf(answ, pos); //Almacena la posición de la letra indicada empezando por la posición indicada
                        //En la primera iteración de este bucle, la posición es 0
                        //En las siguentes iteraciones, la posición será la siguiente a la de la última letra encontrada.
                        //Si no encuentra la letra, devuelve -1
                        if (pos != -1) {
                            guess.replace(pos,pos+1,answ); //Reemplaza el String entre pos(incluído) y pos+1(excluído)
                            //por el String answ(letra)
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
}
