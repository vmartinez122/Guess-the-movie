public class Player {
    private int turns; //Turnos que tiene el jugador para adivinar la palabra
    private int points; //Número de puntos que ha logrado obtener el jugador
    private String[][] leaderboard = new String[5][2]; //Almacena las 5 mayores puntuaciones

    /**
     * Constructor de la clase, permite asignar el número de turnos que tendrá el jugador.
     * El jugador siempre empiza con 0 puntos.
     * @param turns Número de turnos
     */
    public Player(int turns) {
        this.turns = turns;
        this.points = 0;
        //TESTING
        leaderboard[0]= new String[]{"n1","40"};
        leaderboard[1]= new String[]{"n2","30"};
        leaderboard[2]= new String[]{"n3","30"};
        leaderboard[3]= new String[]{"n4","20"};
        leaderboard[4]= new String[]{"n5","10"};
    }
    //Getters variables de la clase:
    public int getTurns() {
        return turns;
    }
    public int getPoints() {
        return points;
    }
    public String[][] getLeaderboard() {
        return leaderboard;
    }

    //Resta un turno al jugador
    public void minusTurns() {
        --this.turns;
    }

    //Añade el número de puntos indicados (pueden ser puntos negativos)
    public void addPoints(int points) {
        this.points += points;
    }

    /**
     * Comprueba que el nickname del jugador, no sea igual a otro dentro de la leaderboard.
     * @param name Nickname a comprobar.
     * @return True si el nombre está disponible, false si el nombre está repetido.
     */
    public boolean validName(String name){
        for (String[] r : leaderboard){
            try { //Inicialmente, la tabla de puntuaciones, puede tener entradas null. Este bloque permite evitar
                // errores al comparar estas con el String name
                if (name.equals(r[0])){
                    return false; //El nombre está repetido
                }
            }catch (NullPointerException e){
                break; //Actualmente, las entradas null, siempre ocupan las últimas posiciones de la lista, así que podemos
                // utilizar break para no tener que leer la lista entera.
            }
        }
        return true; //Este nombre és válido
    }

    /**
     * Intenta añadir una nueva entrada a la tabla de puntuaciones con el nickname del jugador.
     * Si encuentra una entrada en la tabla con menos puntos que esta, la reemplaza y sigue comprobando si hay entradas
     *  menores a esta.
     *  Si no hay ningúna puntuacion menor, no se escribirá en la tabla.
     * @param name Nickname del jugador.
     */
    public void updateLeaderboard(String name){
        String[]  newScore = new String[] {name, String.valueOf(points)}; //Nueva puntuación, inicialmente formada del nombre
        // y la puntuación en formato String
        String[]  OldScore; //Puntuación contra la que se comprobará en la iteración actual
        for (int row = 0; row < leaderboard.length; ++row){
            OldScore = leaderboard[row]; //Actualiza la puntuación antigua a la fila actual
            if(OldScore[0]==null){ //Si esta puntuación es null, significa que no habrá más puntuaciones en el array
                // Por tanto, escribiremos la nueva puntuación y salimos del bucle
                leaderboard[row] = newScore;
                break;
            }else{
                if (Integer.parseInt(OldScore[1]) <= points) { //Puntuación antigua és menor o igual
                    // (si las puntuaciones són iguales, se mostrará la más nueva primero)
                    leaderboard[row] = newScore;
                    newScore = OldScore; //La puntuación antigua, pasará a actuar como la nueva, para organizar las
                    // puntuaciones de mayor a menor
                }
            }
        }
    }
}