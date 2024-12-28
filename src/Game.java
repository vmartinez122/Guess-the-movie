public class Game {
    private String film = "aab a"; //Nombre de la película
    private StringBuilder guess = new StringBuilder();

    public StringBuilder getGuess() {
        return guess;
    }

    //Genera la palabra escondida
    //TODO leer archivo con nombres
    public void create(){
        for (int c = 0; c < film.length(); ++c) {
            if (film.charAt(c) == ' ') {
                guess.append(film.charAt(c));

            } else {
                guess.append('*');
            }
        }
    }

    /**
     *
     * @param answ
     * @return
     */
    public boolean addLetter(String answ){
        int pos = 0; //Posición de la letra
        if(guess.toString().contains(answ)) { //Si el jugador ya ha adivinado esta letra
            return true;
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
                    return true;
                } else {
                    return false;
                }
        }
    }
}
