package armazem.data;

import armazem.business.catalogo.Localizacao;
import armazem.business.catalogo.Palete;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PaleteDAO  implements Map<String, Palete> {
    private static PaleteDAO singleton = null;

    private PaleteDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS `Armazem`.`Palete` (" +
                    "`codPalete` VARCHAR(45) NOT NULL," +
                    "`estado` VARCHAR(45) NOT NULL," +
                    "`codQRCode` VARCHAR(45) NOT NULL," +
                    "`localizacao` INT NOT NULL," +
                    "PRIMARY KEY (`codPalete`)," +
                    "INDEX `fk_Palete_QRCode_idx` (`codQRCode` ASC) VISIBLE," +
                    "INDEX `fk_Palete_Localizacao1_idx` (`localizacao` ASC) VISIBLE," +
                    "CONSTRAINT `fk_Palete_QRCode`" +
                    "FOREIGN KEY (`codQRCode`)" +
                    "REFERENCES `Armazem`.`QRCode` (`codQRCode`)" +
                    "ON DELETE NO ACTION " +
                    "ON UPDATE NO ACTION," +
                    "CONSTRAINT `fk_Palete_Localizacao1`" +
                    "FOREIGN KEY (`localizacao`)" +
                    "REFERENCES `Armazem`.`Localizacao` (`prateleira`)" +
                    "ON DELETE NO ACTION " +
                    "ON UPDATE NO ACTION);";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static PaleteDAO getInstance() {
        if (PaleteDAO.singleton == null) {
            PaleteDAO.singleton = new PaleteDAO();
        }
        return PaleteDAO.singleton;
    }

    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM palete")) {
            if(rs.next()) {
                i = rs.getInt(1);
            }
        }
        catch (Exception e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }


    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT codPalete FROM palete WHERE codPalete='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    public Palete get(Object key) {
        Palete a = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM palete WHERE codPalete='"+key+"'")) {
            if (rs.next()) {
                Localizacao l = null;
                String sql = "SELECT * FROM localizacao WHERE prateleira ='"+rs.getString("localizacao")+"'";

                try (ResultSet rsa = stm.executeQuery(sql)) {
                    if (rsa.next()) {  // Encontrou a sala
                        l = new Localizacao(rsa.getInt("prateleira"),rsa.getString("zona"));
                    }
                }

                a = new Palete(rs.getString("codPalete"), rs.getString("estado"),rs.getString("codQRCode"),l);
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }

    public Palete put(String key, Palete value) {
        Palete res = null;
        Localizacao l = value.getLocalizacao();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            stm.executeUpdate(
                    "INSERT INTO localizacao " +
                            "VALUES ('"+ l.getPrateleira()+ "', '"+
                            l.getZona()+"') " +
                            "ON DUPLICATE KEY UPDATE zona=Values(zona), " +
                            "prateleira=Values(prateleira)");

            stm.executeUpdate(
                    "INSERT INTO Palete (codPalete,estado,codQRCode,localizacao) VALUES ('"+value.getCodPalete()+"', '"+value.getEstado()+"', '"+value.getQrCode()+"' ,"+l.getPrateleira()+") " +
                            "ON DUPLICATE KEY UPDATE estado = VALUES(estado) , codQRCode = VALUES(codQRCode), localizacao=VALUES(localizacao)" );


        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        return res;
    }

    public Palete remove(Object key) {
        Palete p = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)) {
            Statement stm = conn.createStatement();

            stm.executeUpdate("DELETE FROM palete WHERE codPalete='" + key + "'");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return p;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Palete> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Palete> values() {
        Collection<Palete> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT codPalete FROM palete")) {
            while (rs.next()) {
                String idt = rs.getString("codPalete");
                Palete p = this.get(idt);
                res.add(p);
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }


    public Set<Entry<String, Palete>> entrySet() {
        return null;
    }
}
