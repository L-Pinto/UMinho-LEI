package armazem.data;

import armazem.business.abastecimento.Corredor;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CorredorDAO implements Map<Integer,Corredor> {
    private static CorredorDAO singleton = null;

    private  CorredorDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS `Armazem`.`Corredor` (" +
                    "`codCorredor` INT NOT NULL," +
                    "`zona` VARCHAR(45) NOT NULL," +
                    "`ocupacao` INT NOT NULL," +
                    "`capacidadeTotal` INT NOT NULL," +
                    "PRIMARY KEY (`codCorredor`));";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static CorredorDAO getInstance() {
        if (CorredorDAO.singleton == null) {
            CorredorDAO.singleton = new CorredorDAO();
        }
        return CorredorDAO.singleton;
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
    public Corredor get(Object key) {
        Corredor c = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Corredor WHERE codCorredor='"+key+"'")) {
            if (rs.next()) {  // A key existe na tabela

                c = new Corredor(rs.getInt("codCorredor"),
                        rs.getString("zona"),
                        rs.getInt("ocupacao"),
                        rs.getInt("capacidadeTotal"));

            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return c;
    }

    @Override
    public Corredor put(Integer key, Corredor c) {
        Corredor res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            // Actualizar a Corredor
            stm.executeUpdate(
                    "INSERT INTO Corredor VALUES ('"+c.getCodCorredor()+"', '"
                            +c.getZona()+"', '"
                            +c.getOcupacao()+"', '"
                            +c.getCapacidadeTotal()+"') " +
                            "ON DUPLICATE KEY UPDATE zona=Values(zona), " +
                            "ocupacao=Values(ocupacao)," +
                            "capacidadeTotal=Values(capacidadeTotal)");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Corredor remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Corredor> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<Integer> keySet() {
        return null;
    }

    @Override
    public Collection<Corredor> values() {
        Collection<Corredor> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT codCorredor FROM Corredor")) {
            while (rs.next()) {
                int cod = rs.getInt("codCorredor");
                Corredor c = this.get(cod);
                res.add(c);
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Set<Entry<Integer, Corredor>> entrySet() {
        return null;
    }
}
