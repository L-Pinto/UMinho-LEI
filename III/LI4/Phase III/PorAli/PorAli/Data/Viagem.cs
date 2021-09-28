using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace PorAli.Data
{
    public class Viagem
    {
        public int IDViagem { get; }
        public DateTime hora_inicio { get; set; }

        public DateTime hora_Fim { get; set; }
        public string local_chegada { get; set; }

        public string local_partida { get; set; }

        public string preco { get; set; }

        public List<Percurso> percursos { get; set; }

        public int num { get; set; }


        public Viagem()
        {
            Random _random = new Random();

            int viagem = _random.Next(1, 1000000); //não estamos verificar se o código já existe na BD

            IDViagem = viagem;

            percursos = new List<Percurso>();
            preco = "ND"; //inicializar a -1, significa que nao se sabe o preço da viagem
        }

        public void calculaPenultima()
        {
            Rota rota = new Rota();
            Dictionary<Tuple<int, int>, List<Rota>> rotas = rota.getRotas();


            foreach (Tuple<int, int> t in rotas.Keys) //percorrer o dicionario
            {
                if (t.Item2 == percursos[0].nrautocarro)
                {
                    List<Rota> rota_d = rotas[t]; //ir buscar o value daquele tuplo

                    foreach (Rota r in rota_d)
                    {
                        foreach (Percurso p in percursos)
                        {
                            if (p.local_inicio == r.paragens[0] && p.local_destino == r.paragens[r.paragens.Count - 1])
                            {
                                p.local_penultima = r.paragens[r.paragens.Count - 2]; //colocar penultima paragem a cada um dos percursos
                            }

                        }
                    }

                }

            }

        }

        public void setPreco(string preco)
        {
            this.preco = preco;
        }

        public void addPercurso(Percurso p)
        {
            if (percursos.Count == 0)
            {
                this.hora_inicio = DateTime.Parse(p.hora_inicio, null); // pode estar a dar mal
                this.local_partida = p.local_inicio;
            }

            this.local_chegada = p.local_destino; //adiciona sempre até ficar com o valor do ultimo
            this.hora_Fim = DateTime.Parse(p.hora_fim, null);

            percursos.Add(p);
        }

        public string showViagem()
        {
            string res = "";

            res = res + " " + this.hora_inicio + " " + "Autocarro(s)";

            foreach (Percurso p in percursos)
            {
                res = res + " " + p.nrautocarro;
            }
            return res;
        }




    }
}

