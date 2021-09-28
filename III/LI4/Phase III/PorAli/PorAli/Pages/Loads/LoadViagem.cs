using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace PorAli.Pages.Loads
{
    public class LoadViagem
    {
        public string local_inicio { get; set; }
        public string local_destino { get; set; }
        public string data_partida { get; set; }

        public string hora_partida { get; set; }



        public LoadViagem()
        {
            local_inicio = "";
            local_destino = "";
        }
    }
}
