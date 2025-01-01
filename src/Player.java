import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Player {
    private int turns; //Turnos que tiene el jugador para adivinar la palabra
    private int points; //Número de puntos que ha logrado obtener el jugador
    private String[][] leaderboard = new String[5][2]; //Almacena las 5 mayores puntuaciones

    /**
     * Constructor de la clase, permite asignar el número de turnos que tendrá el jugador.
     * El jugador siempre empieza con 0 puntos.
     * Carga la tabla de puntuaciones de un fichero binario.
     * @param turns Número de turnos
     */
    public Player(int turns) {
        this.turns = turns;
        this.points = 0;
        //Carga la tabla de puntuaciones
        try (FileInputStream scores = new FileInputStream("scores.data");
             ObjectInputStream input = new ObjectInputStream(scores)){
            leaderboard = (String[][]) input.readObject(); //Asigna el array guardado a la variable almacenada en el programa
        } catch (Exception e) {
            System.out.println(e); //Mostrará un error si no hay fichero de guardado (entre otros)
        }
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
     *  Almacena los cambios en un fichero binario.
     * @param name Nickname del jugador.
     */
    public void updateLeaderboard(String name){
        String[]  newScore = new String[] {name, String.valueOf(points)}; //Nueva puntuación, inicialmente formada del nombre
        // y la puntuación en formato String
        String[]  OldScore; //Puntuación contra la que se comprobará en la iteración actual
        //Actualiza la tabla de puntuaciones
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

        //Guarda la tabla de puntuaciones en un fichero binario
        try (FileOutputStream scores = new FileOutputStream ("scores.data");
             ObjectOutputStream output = new ObjectOutputStream(scores)){
             output.writeObject(leaderboard); //Guarda el array completo
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}