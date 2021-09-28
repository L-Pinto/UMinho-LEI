using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace PorAli.Data
{
    public class Percurso
    {
        public string local_inicio { get; set; }
        public string local_destino { get; set; }
        public string hora_inicio { get; }
        public string hora_fim { get; }
        public string local_penultima { get; set; }
        public string hora_penultima { get; set; }
        public int nrautocarro { get; set; }
        public int nrParagens { get; set; }


        public Percurso(string local_inicio, string local_destino, string hora_inicio, string hora_fim, int nrBus, int nrParagens)
        {
            this.local_inicio = local_inicio;
            this.local_destino = local_destino;
            this.hora_inicio = hora_inicio;
            this.hora_fim = hora_fim;
            this.nrautocarro = nrBus;
            this.nrParagens = nrParagens;
        }


        public void atualizaHoraP(string time)
        {
            DateTime hp = DateTime.Parse(hora_fim, null);

            double min = (-1) * Convert.ToDouble(Regex.Replace(time, "[^0-9.]", ""));

            hp = hp.AddMinutes(min);

            hora_penultima = hp.ToString("HH:mm");
        }
    }
}
