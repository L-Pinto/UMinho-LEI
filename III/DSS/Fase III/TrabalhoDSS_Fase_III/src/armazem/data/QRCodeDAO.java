package armazem.data;

import armazem.business.catalogo.QRCode;

import java.sql.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class QRCodeDAO  implements Map<String, QRCode> {
    private static QRCodeDAO singleton = null;

    private QRCodeDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS `Armazem`.`QRCode` (" +
                    "`codQRCode` VARCHAR(45) NOT NULL," +
                    "`perecivel` TINYINT NOT NULL," +
                    "`descricao` VARCHAR(45) NOT NULL," +
                    "PRIMARY KEY (`codQRCode`))";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS `Armazem`.`Localizacao` (" +
                    "`prateleira` INT NOT NULL," +
                    "`zona` VARCHAR(45) NOT NULL," +
                    "PRIMARY KEY (`prateleira`))";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS `Armazem`.`Palete` (" +
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

    public static QRCodeDAO getInstance() {
        if (QRCodeDAO.singleton == null) {
            QRCodeDAO.singleton = new QRCodeDAO();
        }
        return QRCodeDAO.singleton;
    }

    private Collection<String> getPaletesQRCode(String tid, Statement stm) throws SQLException {
        Collection<String> paletes = new TreeSet<>();
        try (ResultSet rsa = stm.executeQuery("SELECT codPalete FROM palete WHERE codQRCode='"+tid+"'")) {
            while(rsa.next()) {
                paletes.add(rsa.getString("codPalete"));
            }
        }
        return paletes;
    }

    @Override
    public QRCode get(Object key) {
        QRCode qrCode = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM qrcode WHERE codQRCode='"+key+"'")) {
            if (rs.next()) {
                Collection<String> paletes = getPaletesQRCode(key.toString(), stm);

                qrCode = new QRCode(rs.getString("codQRCode"),rs.getString("descricao"),rs.getBoolean("perecivel"),paletes);
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return qrCode;
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
    //Utilizado
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT codQRCode FROM qrcode WHERE codQRCode='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public QRCode put(String key, QRCode value) {
        return null;
    }

    @Override
    public QRCode remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends QRCode> m) { }

    @Override
    public void clear() { }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<QRCode> values() {
        return null;
    }

    @Override
    public Set<Entry<String, QRCode>> entrySet() {
        return null;
    }
}
