package TrazAqui.Model;

import java.io.Serializable;

import static java.lang.StrictMath.abs;

public class GPS implements InterGPS , Serializable {

    /*--------------------------  variaveis de instancia  --------------------------*/
    /** Longitude */
    private double x;
    /** Latitude */
    private double y;

    /*--------------------------  construtores  --------------------------*/

    /** Metodo construtor vazio */
    public GPS() {
        this.x = 0;
        this.y = 0;
    }

    /** Metodo construtor parametrizado
     * @param x - longitude
     * @param y - latitude*/
    public GPS(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** Metodo construtor por copia
     * @param gps - TrazAqui.Model.GPS*/
    public GPS(GPS gps) {
        setX(gps.getX());
        setY(gps.getY());
    }

    /*-------------------------- gets, sets  --------------------------*/

    /** Método para retorno da longitude : voluntário, transporatodora ou loja
     * @return double - longitude */
    public double getX() {
        return x;
    }
    /** Método para retorno da latitude : voluntário, transporatodora ou loja
     * @return double - latitude */
    public double getY() {
        return y;
    }
    /** Método para definição da longitude : voluntário, transporatodora ou loja
     * @param x - longitude */
    public void setX(double x) {
        this.x = x;
    }
    /** Método para definição da latitude : voluntário, transporatodora ou loja
     * @param y - latitude */
    public void setY(double y) {
        this.y = y;
    }

    /*-------------------------- clone, equals, toString --------------------------*/

    /** Metodo de copia de TrazAqui.Model.GPS
     * @return TrazAqui.Model.GPS - TrazAqui.Model.GPS clonado*/
    public GPS clone(){
        return new GPS(this);
    }

    public boolean isApto(InterGPS gl,InterGPS gv, double raio)
    {
        double distL = Math.sqrt(Math.pow(abs(gl.getX()-x),2) + Math.pow(abs(gl.getY()-y),2));
        double distV = Math.sqrt(Math.pow(abs(gv.getX()-x),2) + Math.pow(abs(gv.getY()-y),2));

        return distL<=raio && distV<=raio;
    }

    /**
     * Método que calcula a distancia entre 2 pontos
     * @param gl gps com o qual vamos calcular a distancia
     * @return distância
     */
    public double dist2Pontos(InterGPS gl)
    {
        return Math.sqrt(Math.pow(abs(x-gl.getX()),2)+Math.pow(abs(y-gl.getY()),2));
    }

    /** Metodo de comparacao entre dois TrazAqui.Model.GPS
     * @param o - TrazAqui.Model.GPS de comparacao
     * @return boolean - verdade ou falso */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GPS gps = (GPS) o;

        if (Double.compare(gps.getX(), getX()) != 0) return false;
        return Double.compare(gps.getY(), getY()) == 0;
    }

    /** Metodo para retorno de uma mensagem com as coordenadas TrazAqui.Model.GPS.
     * @return String - TrazAqui.Model.GPS + longitude + latitude*/
    public String toString() {
        return "GPS{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
