using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;


namespace PorAli.Data
{
    public class Utilizador
    {

        public string email { get; set; }

        public string password { get; set; }

        public int avaliacao { get; set; }

        public string codigo { get; set; }

        public string nome { get; set; }

        public Dictionary<int, Viagem> mapviagens = new Dictionary<int, Viagem>();

        public List<Notificacao> listaNotificacoes = new List<Notificacao>();

        public List<ConfigNotificacoes>  lstconfiguracaoes = new List<ConfigNotificacoes>();

        public List<ViagemGratis> lstviagemGratis = new List<ViagemGratis>();

        /*
        public void addNotificacao(string Conteudo)
        {
            Notificacao n = new Notificacao(Conteudo);
            listaNotificacoes.Add(n);
        }
        */

        public void buildUserRegisto(string email, string password, string nome)
        {
            Random _random = new Random();

            int codigo = _random.Next(1000, 2000);

            string cod = codigo.ToString();

            this.email = email;
            this.password = password;
            this.nome = nome;
            this.avaliacao = 0;
            this.codigo = cod;
                
        }

        public Utilizador()
        {
     

        }

        public void buildUser(List<ConfigNotificacoes> config, List<Viagem> viagens, List<ViagemGratis> gratis, Utilizador user)
        {

            foreach(var c in config)
            {
                lstconfiguracaoes.Add(c);

            }

            foreach (var v in viagens)
            {
                mapviagens.Add(v.IDViagem, v);

            }

            foreach (var g in gratis)
            {
                lstviagemGratis.Add(g);

            }

            email = user.email;
            password = user.password;
            avaliacao = user.avaliacao;
            codigo = user.codigo;
            nome = user.nome;

        }

        public void destroyUser()
        {

            listaNotificacoes.Clear();
            lstconfiguracaoes.Clear();

            lstviagemGratis.Clear();
            mapviagens.Clear();

        }

        public void updateViagens(string codigo)
        {
            foreach (var v in lstviagemGratis)
            {
                if (v.codigo == codigo)
                {
                    v.redimida = true;

                }
            }

        }

        public void deleteViagens(string codigo)
        {
            foreach (var v in lstviagemGratis)
            {
                if (v.codigo == codigo)
                {
                    lstviagemGratis.Remove(v);
                    break;
                }
            }

        }

        public List<ConfigNotificacoes> getConfigNotificacoes()
        {
            return lstconfiguracaoes;
        }

        public void updateConfig(bool ativo, string codigo)
        {
            foreach (var v in lstconfiguracaoes)
            {
                if (v.IDNotificacao == codigo)
                {
                    v.ativada = ativo;
                    break;
                }
            }

        }

        public void updateNome(string nome)
        {
            this.nome = nome;

        }

    }
}