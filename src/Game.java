public class Game {
    private String film = "aab a"; //Nombre de la película
    private StringBuilder guess = new StringBuilder();

    public StringBuilder getGuess() {
        return guess;
    }

    public void create(){
        //Generar palabra escondida
        for (int c = 0; c < film.length(); ++c) {
            if (film.charAt(c) == ' ') {
                guess.append(film.charAt(c));

            } else {
                guess.append('*');
            }
        }
    }

    public boolean addLetter(char answ){
        int pos;
        int oldpos = 0;
        if(guess.toString().contains(""+answ)) {
            return true;
        }else{
                if (film.contains(""+answ)) {
                    do {
                        pos = film.indexOf(answ, oldpos);
                        if (pos != -1) {
                            guess.delete(pos, pos + 1).insert(pos, answ);
                            oldpos = pos + 1;
                        }
                    } while (pos != -1);
                    return true;
                } else {
                    return false;
                }
        }
    }
}
