package armazem.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UtilizadorDAO {
    private static UtilizadorDAO singleton = null;

    private  UtilizadorDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS `Armazem`.`Utilizador` (" +
                    "`codUtilizador` VARCHAR(45) NOT NULL," +
                    "`palavraPasse` VARCHAR(45) NOT NULL," +
                    "`sessaoIniciada` TINYINT NOT NULL," +
                    "PRIMARY KEY (`codUtilizador`));";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    public static UtilizadorDAO getInstance() {
        if (UtilizadorDAO.singleton == null) {
            UtilizadorDAO.singleton = new UtilizadorDAO();
        }
        return UtilizadorDAO.singleton;
    }
}
