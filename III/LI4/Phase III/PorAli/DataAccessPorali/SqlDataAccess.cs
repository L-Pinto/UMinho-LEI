using Dapper;
using Microsoft.Extensions.Configuration;
using MySql.Data.MySqlClient;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessPorali
{
    public class SqlDataAccess : ISqlDataAccess
    {
        public async Task<List<T>> LoadData<T, U>(string sql, U parameters, string connectionString)
        {

            using (IDbConnection connection = new MySqlConnection(connectionString))
            {
                var rows = await connection.QueryAsync<T>(sql, parameters);

                return rows.ToList();

            }
        }

        public Task SaveData<T>(string sql, T parameters, string connectionString)
        {
            using (IDbConnection connection = new MySqlConnection(connectionString))
            {
                return connection.ExecuteAsync(sql, parameters);

            }
        }
    }
}
