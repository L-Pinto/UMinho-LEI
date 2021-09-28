using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace PorAli.Data
{
    public class Notificacao
    {
        public string nome { get; set; }
        public string conteudo { get; set; }

        public Notificacao(string nome, string Conteudo)
        {
            this.nome = nome;
            this.conteudo = Conteudo; //isto é para ser aleatório

        }

    }
}
