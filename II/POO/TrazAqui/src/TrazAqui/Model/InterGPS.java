package TrazAqui.Model;

public interface InterGPS {

    double getX();

    double getY();

    void setX(double x);

    void setY(double y);

    GPS clone();

    boolean isApto(InterGPS gu,InterGPS gl, double raio);

    double dist2Pontos(InterGPS gl);
}
