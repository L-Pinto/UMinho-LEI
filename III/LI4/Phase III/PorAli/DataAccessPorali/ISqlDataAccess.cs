using System.Collections.Generic;
using System.Threading.Tasks;

namespace DataAccessPorali
{
    public interface ISqlDataAccess
    { 
        Task<List<T>> LoadData<T, U>(string sql, U parameters, string connectionString);
        Task SaveData<T>(string sql, T parameters, string connectionString);
    }
}