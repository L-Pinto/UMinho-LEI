package armazem.data;

import armazem.business.abastecimento.Robot;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RobotDAO  implements Map<String, Robot> {
    private static RobotDAO singleton = null;

    private  RobotDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS `Armazem`.`Robot` (" +
                    "`codRobot` VARCHAR(45) NOT NULL," +
                    "`ocupado` TINYINT NOT NULL," +
                    "`codQRPalete` VARCHAR(45) NULL," +
                    "`percurso` VARCHAR(45) NULL," +
                    "PRIMARY KEY (`codRobot`));";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static RobotDAO getInstance() {
        if (RobotDAO.singleton == null) {
            RobotDAO.singleton = new RobotDAO();
        }
        return RobotDAO.singleton;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Robot get(Object key) {
        Robot r = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Robot WHERE codRobot='"+key+"'")) {
            if (rs.next()) {

                r = new Robot(rs.getString("codRobot"),
                        rs.getBoolean("ocupado"),
                        rs.getString("codQRPalete"),
                        rs.getString("percurso"));

            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public Robot put(String key, Robot r) {
        Robot res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate(
                    "INSERT INTO Robot VALUES ('"+r.getCodRobot()+"', '"
                            +r.getOcupado()+"', '"
                            +r.getCodPalete()+"', '"
                            +r.fromListToString()+"') " +
                            "ON DUPLICATE KEY UPDATE ocupado=Values(ocupado), " +
                            "codQRPalete=Values(codQRPalete)," +
                            "percurso=Values(percurso)");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Robot remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Robot> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Robot> values() {

        Collection<Robot> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT codRobot FROM Robot")) {
            while (rs.next()) {
                String cod = rs.getString("codRobot");
                Robot r = this.get(cod);
                res.add(r);
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Set<Entry<String, Robot>> entrySet() {
        return null;
    }
}
