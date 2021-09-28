import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public class Coordenadas {
    private int x;
    private int y;
    private ReentrantLock lock = new ReentrantLock();


    public Coordenadas(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Para um mapa de 5x5 - gerar coordenadas aleatórias
     */
    public Coordenadas() {
        this.x = (int) (Math.random() * (5 - 1 + 1) + 1);
        this.y = (int) (Math.random() * (5 - 1 + 1) + 1);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordenadas that = (Coordenadas) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public String toString() {
        StringBuilder res = new StringBuilder("");
        res.append("Coordenada (").append(x).append(",").append(y).append(")");
        return res.toString();
    }
}
