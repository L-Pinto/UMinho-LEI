using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace PorAli.Data
{
    public class Rota
    {
        public int IDRota { get; }
        public List<string> paragens { get; set; }

        public Rota(List<string> paragens)
        {
            this.paragens = paragens;
        }

        public Rota(Rota p)
        {
            this.paragens = p.getParagens();
        }

        public Rota()
        {
            this.paragens = new List<string>();
        }

        public List<string> getParagens()
        {
            return paragens;
        }

        public void setParagens(List<string> p)
        {
            this.paragens = p;
        }



        public Dictionary<Tuple<int, int>, List<Rota>> getRotas()
        {
            //Numero da rota para l 
            Dictionary<Tuple<int, int>, List<Rota>> Rotas = new Dictionary<Tuple<int, int>, List<Rota>>();

            List<string> list1 = new List<string>() {
                "Albano Belino","Quinta Armada (F O Guimarães)","Quinta Armada (B Alegria)","Quinta Armada (Escola)","Verdosas (B Alegria)"
            };
            Rota p1 = new Rota(list1);
            List<Rota> viagem1 = new List<Rota>();
            viagem1.Add(p1);
            // BP -> Uni (41)
            Tuple<int, int> t1 = new Tuple<int, int>(1, 41);
            Rotas.Add(t1, viagem1);

            List<string> list2 = new List<string>() {
                "Júlio Fragata II","Fernando O Guimarães I","Fernando O Guimarães II","Quinta Armada (B Alegria)","Quinta Armada (Escola)","Verdosas (B Alegria)"
            };
            Rota p2 = new Rota(list2);
            List<Rota> viagem2 = new List<Rota>();
            viagem2.Add(p2);
            // BP -> Uni (74)
            Tuple<int, int> t2 = new Tuple<int, int>(2, 74);
            Rotas.Add(t2, viagem2);

            List<string> list3 = new List<string>() {
                "Universidade do Minho II", "João Paulo II (Inl)", "João Paulo II (Parque Rodovia)", "Júlio Fragata I", "Júlio Fragata II"
            };
            Rota p3 = new Rota(list3);
            List<Rota> viagem3 = new List<Rota>();
            viagem3.Add(p3);
            // Uni -> BP (7)
            Tuple<int, int> t3 = new Tuple<int, int>(3, 7);
            Rotas.Add(t3, viagem3);

            List<string> list4 = new List<string>() {
                "Universidade do Minho II", "João Paulo II (Inl)", "João Paulo II (Parque Rodovia)", "Júlio Fragata I", "Júlio Fragata II"
            };
            Rota p4 = new Rota(list4);
            List<Rota> viagem4 = new List<Rota>();
            viagem4.Add(p4);
            // Uni -> BP (24)
            Tuple<int, int> t4 = new Tuple<int, int>(4, 24);
            Rotas.Add(t4, viagem4);

            List<string> list5 = new List<string>() {
               "Universidade do Minho II", "João Paulo II (Inl)", "João Paulo II (Parque Rodovia)", "Nova Santa Cruz (Qta Armada)",
               "Quinta Armada (Rosalvo Almeida)", "Albano Belino"
            };
            Rota p5 = new Rota(list5);
            List<Rota> viagem5 = new List<Rota>();
            viagem5.Add(p5);
            // Uni -> BP (41)
            Tuple<int, int> t5 = new Tuple<int, int>(5, 41);
            Rotas.Add(t5, viagem5);

            List<string> list6 = new List<string>() {
               "Universidade do Minho II", "João Paulo II (Inl)", "João Paulo II (Parque Rodovia)", "Júlio Fragata I", "Júlio Fragata II",
               "Júlio Fragata III", "Júlio Fragata IV", "D Pedro V - II", "D Pedro V - I", "S Victor", "Central I"
            };
            Rota p6 = new Rota(list6);
            List<Rota> viagem6 = new List<Rota>();
            viagem6.Add(p6);
            // Uni -> Avenida (7)
            Tuple<int, int> t6 = new Tuple<int, int>(6, 7);
            Rotas.Add(t6, viagem6);

            List<string> list7 = new List<string>() {
               "Universidade do Minho II", "João Paulo II (Inl)", "João Paulo II (Parque Rodovia)", "Júlio Fragata I", "Júlio Fragata II",
               "Júlio Fragata III", "Júlio Fragata IV", "D Pedro V - II", "D Pedro V - I", "S Victor", "Central II"
            };
            Rota p7 = new Rota(list7);
            List<Rota> viagem7 = new List<Rota>();
            viagem7.Add(p7);
            // Uni -> Avenida (24)
            Tuple<int, int> t7 = new Tuple<int, int>(7, 24);
            Rotas.Add(t7, viagem7);

            List<string> list8 = new List<string>() {
               "Universidade do Minho I", "João Paulo II (Inl)", "João XXI (C Amarante)"
            };
            Rota p8 = new Rota(list8);
            List<Rota> viagem8 = new List<Rota>();
            viagem8.Add(p8);
            // Uni -> Avenida (43)
            Tuple<int, int> t8 = new Tuple<int, int>(8, 43);
            Rotas.Add(t8, viagem8);

            List<string> list9 = new List<string>() {
               "Verdosas (B Alegria)", "Quinta Armada (Escola)", "Quinta Armada (B Alegria)", "Fernando O Guimarães II", "Fernando O Guimarães I",
               "Júlio Fragata III", "Júlio Fragata IV", "D Pedro V - II", "D Pedro V - I", "S Victor", "Central I"
            };
            Rota p9 = new Rota(list9);
            List<Rota> viagem9 = new List<Rota>();
            viagem9.Add(p9);
            // Uni -> Avenida (74)
            Tuple<int, int> t9 = new Tuple<int, int>(9, 74);
            Rotas.Add(t9, viagem9);

            List<string> list10 = new List<string>() {
               "Senhora-a-Branca","S Victor","D Pedro V - I","D Pedro V - II","Nova Santa Cruz (Qta Armada)","Nova Santa Cruz","Nova Santa Cruz (Lusíadas)",
               "Nova Santa Cruz (Um)"
            };
            Rota p10 = new Rota(list10);
            List<Rota> viagem10 = new List<Rota>();
            viagem10.Add(p10);
            // Avenida -> Uni (24) 
            Tuple<int, int> t10 = new Tuple<int, int>(10, 24);
            Rotas.Add(t10, viagem10);

            List<string> list11 = new List<string>() {
               "Senhora-a-Branca","S Victor","D Pedro V - I","D Pedro V - II","Nova Santa Cruz (Qta Armada)","Nova Santa Cruz","Nova Santa Cruz (Lusíadas)",
               "Nova Santa Cruz (Um)"
            };
            Rota p11 = new Rota(list11);
            List<Rota> viagem11 = new List<Rota>();
            viagem11.Add(p11);
            // Avenida -> Uni (7)
            Tuple<int, int> t11 = new Tuple<int, int>(11, 7);
            Rotas.Add(t11, viagem11);

            List<string> list12 = new List<string>() {
                "João XXI (31 de Janeiro I)", "João XXI (C Amarante)", "João Paulo II (Inl)", "Universidade do Minho I"
            };
            Rota p12 = new Rota(list12);
            List<Rota> viagem12 = new List<Rota>();
            viagem12.Add(p12);
            // Avenida -> Uni (43)
            Tuple<int, int> t12 = new Tuple<int, int>(12, 43);
            Rotas.Add(t12, viagem12);

            List<string> list13 = new List<string>() {
                "Senhora-a-Branca", "S Victor", "D Pedro V - I", "D Pedro V - II", "Júlio Fragata I", "Júlio Fragata II", "Fernando O Guimarães I",
                "Fernando O Guimarães II", "Quinta Armada (B Alegria)", "Quinta Armada (Escola)", "Verdosas (B Alegria)"
            };
            Rota p13 = new Rota(list13);
            List<Rota> viagem13 = new List<Rota>();
            viagem13.Add(p13);
            // Avenida -> Uni (74)
            Tuple<int, int> t13 = new Tuple<int, int>(13, 74);
            Rotas.Add(t13, viagem13);

            List<string> list14 = new List<string>() {
                "Conde Agrolongo III", "25 de Abril (D Maria II - II)", "25 de Abril (D Maria II - III)", "Senhora-a-Branca", "S Victor", "D Pedro V - I",
                "D Pedro V - II", "Nova Santa Cruz (Qta Armada)", "Nova Santa Cruz", "Nova Santa Cruz (Lusíadas)", "Nova Santa Cruz (Um)"
            };
            Rota p14 = new Rota(list14);
            List<Rota> viagem14 = new List<Rota>();
            viagem14.Add(p14);
            // Cetral->Uni(24)
            Tuple<int, int> t14 = new Tuple<int, int>(14, 24);
            Rotas.Add(t14, viagem14);

            List<string> list15 = new List<string>() {
                "Senhora-a-Branca", "S Victor", "D Pedro V - I", "D Pedro V - II", "Nova Santa Cruz (Qta Armada)", "Nova Santa Cruz", "Nova Santa Cruz (Lusíadas)",
                "Nova Santa Cruz (Um)"
            };
            Rota p15 = new Rota(list15);
            List<Rota> viagem15 = new List<Rota>();
            viagem15.Add(p15);
            // Cetral->Uni(7)
            Tuple<int, int> t15 = new Tuple<int, int>(15, 7);
            Rotas.Add(t15, viagem15);

            List<string> list16 = new List<string>() {
                "Norton Matos (C Camionagem)", "Cons Januário (Igreja)", "Cons Januário (Sá de Miranda)", "Cons Bento Miguel", "Monte D' Arcos",
                "António B M Junior (Bairro Social)", "Bairro Soc Enguardas", "S José", "Hospital"
            };
            Rota p16 = new Rota(list16);
            List<Rota> viagem16 = new List<Rota>();
            viagem16.Add(p16);
            // Cetral->Uni(87)
            Tuple<int, int> t16 = new Tuple<int, int>(16, 87);
            Rotas.Add(t16, viagem16);

            List<string> list17 = new List<string>() {
                "Conde Agrolongo III", "Liberdade (25 de Abril)", "João XXI (31 de Janeiro I)", "João XXI (C Amarante)", "João Paulo II (Piscinas)",
                "João Paulo II (Parque Rodovia)", "João Paulo II (Inl)", "Lusíadas I"
            };
            Rota p17 = new Rota(list17);
            List<Rota> viagem17 = new List<Rota>();
            viagem17.Add(p17);
            // Cetral -> Uni (2)
            Tuple<int, int> t17 = new Tuple<int, int>(17, 2);
            Rotas.Add(t17, viagem17);

            List<string> list18 = new List<string>() {
               "Universidade do Minho II", "Lusíadas I", "D João II", "Luís A Correia", "Luís A Correia (Residência Universitária)", "António Mariz",
               "Pascoal Fernandes", "Egídio Guimarães I", "Egídio Guimarães II", "Robert Smith II", "Machado Owen (Simões Almeida)", "Machado Owen (B D Pacheco)",
               "Machado Owen (Residência Um II)", "Nascente", "Cantinho", "Bernardo Sequeira", "Justiça", "31 de Janeiro (Seg Social)", "31 de Janeiro (Senhora-a-Branca)",
               "Central III", "A Herculano (Penedos)", "Gabriel P Castro II"
            };
            Rota p18 = new Rota(list18);
            List<Rota> viagem18 = new List<Rota>();
            viagem18.Add(p18);
            // Uni -> Cetral (40)
            Tuple<int, int> t18 = new Tuple<int, int>(18, 40);
            Rotas.Add(t18, viagem18);

            List<string> list19 = new List<string>() {
               "Universidade do Minho II", "João Paulo II (Inl)", "João Paulo II (Parque Rodovia)", "Júlio Fragata I", "Júlio Fragata II", "Júlio Fragata III",
               "Júlio Fragata IV", "D Pedro V - II", "D Pedro V - I", "S Victor", "Central II", "A Herculano (Penedos)", "Carmo (Mercado)"
            };
            Rota p19 = new Rota(list19);
            List<Rota> viagem19 = new List<Rota>();
            viagem19.Add(p19);
            // Uni -> Cetral (24)
            Tuple<int, int> t19 = new Tuple<int, int>(19, 24);
            Rotas.Add(t19, viagem19);

            List<string> list20 = new List<string>() {
               "Universidade do Minho II", "João Paulo II (Inl)", "João Paulo II (Parque Rodovia)", "Júlio Fragata I", "Júlio Fragata II", "Júlio Fragata III",
               "Júlio Fragata IV", "D Pedro V - II", "D Pedro V - I", "S Victor", "Central I"
            };
            Rota p20 = new Rota(list20);
            List<Rota> viagem20 = new List<Rota>();
            viagem20.Add(p20);
            // Uni -> Cetral (7)
            Tuple<int, int> t20 = new Tuple<int, int>(20, 7);
            Rotas.Add(t20, viagem20);

            List<string> list21_1 = new List<string>() {
              "Universidade do Minho I", "João Paulo II (Inl)", "João XXI (C Amarante)", "João XXI (D Maria II)", "Imac Conceição (Fujacal)", "Caires"
            };
            List<string> list21_2 = new List<string>() {
              "Caires", "Rotunda Estação III", "Biscainhos", "Cons Torres Almeida II", "Conde Agrolongo I", "Carmo", "Norton Matos I"
            };
            Rota p21_1 = new Rota(list21_1);
            Rota p21_2 = new Rota(list21_2);
            List<Rota> viagem21 = new List<Rota>();
            viagem21.Add(p21_1);
            viagem21.Add(p21_2);
            // Uni -> Cetral (43/53)
            Tuple<int, int> t21 = new Tuple<int, int>(21, 43);
            Rotas.Add(t21, viagem21);

            return Rotas;
        }




    }

}
