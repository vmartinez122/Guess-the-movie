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
}
