package armazem.business.abastecimento;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Robot {
    private String codRobot;
    private boolean ocupado;
    private String codPalete;
    private List<Integer> percurso;

    /**
     * Construtor da classe robot
     * @param codRobot código do robot
     * @param ocupado ocupação do robot
     * @param codPalete código da palete a transportar
     * @param percurso do robot (dividido por ",")
     */
    public Robot(String codRobot, boolean ocupado, String codPalete, String percurso)
    {
        this.codRobot = codRobot;
        this.ocupado = ocupado;
        this.codPalete = codPalete;
        this.percurso = fromStringToList(percurso);
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public void setCodPalete(String codPalete) {
        this.codPalete = codPalete;
    }

    public void setPercurso(List<Integer> percurso) {
        this.percurso = percurso;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public String getCodRobot() {
        return codRobot;
    }

    public String getCodPalete() {
        return codPalete;
    }

    /**
     * Método que transforma o percurso do robot (lista) numa string
     * @return string do percurso
     */
    public String fromListToString() {

        if(this.percurso != null) {
            StringBuilder strbul = new StringBuilder();
            Iterator<Integer> iter = percurso.iterator();
            while (iter.hasNext()) {
                strbul.append(iter.next());
                if (iter.hasNext()) {
                    strbul.append(",");
                }
            }
            return strbul.toString();
        } else return null;
    }

    /**
     * Método que transforma uma string (percurso) numa lista
     * @param s string do percurso
     * @return lista do percurso
     * @throws NumberFormatException
     */
    public List<Integer> fromStringToList(String s) throws NumberFormatException
    {
        ArrayList<Integer> percurso = new ArrayList<>();
        try {
            if (s != null && !s.equals("")) {
                String[] fields = s.split(",");
                for (int i = 0; i < fields.length; i++)
                    percurso.add(Integer.parseInt(fields[i]));
            }
        } catch (NumberFormatException e) {
            percurso = null;
        }
        return percurso;
    }

    /**
     * Método toString da classe Robot
     * @return String da classe Robot
     */
    public String toString() {
        return "\033[34m" + " \n Robot " + codRobot + ": \n" + "\u001B[0m" +
                "  ocupado = " + ocupado + "\n" +
                "  codigo palete = " + codPalete + "\n" +
                "  percurso = " + percurso + "\n";
    }


    /**
     * Método que retorna um inteiro correspondente à ocupação do robot
     * @return 1 caso esteja ocupado, 0 caso contrário
     */
    public int getOcupado() {
        if(ocupado) return 1;
        else return 0;
    }

    /**
     * Método que obtém o destino do robot
     * @return local da palete a armazenar
     */
    public int getDestino() {
        return this.percurso.get(this.percurso.size()-1);
    }
}
