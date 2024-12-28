public class Player {
    private int turns;
    private int points;

    public Player() {
        this.turns = 10;
        this.points = 0;
    }

    public int getTurns() {
        return turns;
    }

    public int getPoints() {
        return points;
    }

    public void minusTurns() {
        --this.turns;
    }

    public void addPoints(int points) {
        this.points += points;
    }
}
