using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Threading.Tasks;

namespace PorAli.Data
{
    public class PorAli
    {
        public PorAli modelUtilizador { get; }

        public PorAli modelViagens { get; }

        public long convertTime(DateTime date)
        {
            date = DateTime.SpecifyKind(date, DateTimeKind.Local);
            DateTimeOffset date_aux = date;

            long epoch = date_aux.ToUnixTimeSeconds();

            return epoch;
        }

        public List<Viagem> iniciaViagem(string origem, string destino, DateTime date)
        {
            long epoch = convertTime(date);

            string request_api = "https://maps.googleapis.com/maps/api/directions/json?";
            string key = "key=AIzaSyBF98jX9qVO-bB8pr-x6GIo9TGTDXqR3Go";
            string settings = "&transit_mode=bus&mode=transit&alternatives=true&";

            //replace blank space

            if (string.IsNullOrEmpty(origem) || string.IsNullOrEmpty(destino))
            {
                return null;
            }

            string origem_bs = origem.Replace(" ", "%20");
            string destino_bs = destino.Replace(" ", "%20");

            string origem_url = "origin=" + origem_bs;
            string destino_url = "&destination=" + destino_bs;

            string dep_time = "&departure_time=" + epoch;

            string request_complete = request_api + origem_url + destino_url + dep_time + settings + key;

            WebRequest requestObjGet = WebRequest.Create(request_complete);
            requestObjGet.Method = "GET";
            HttpWebResponse responseObjGet = null;
            responseObjGet = (HttpWebResponse)requestObjGet.GetResponse();

            string result = null;
            using (Stream stream = responseObjGet.GetResponseStream())
            {
                StreamReader sr = new StreamReader(stream);
                result = sr.ReadToEnd(); //jason em formato de string
                sr.Close(); //fechar a streamreader
            }


            JObject json = JObject.Parse(result);

            string res1 = null;
            //List<Viagem> viagens = new List<Viagem>();

            List<Viagem> viagens = new List<Viagem>();
            int i = 0;

            foreach (var x in json)
            {
                string name = x.Key;
                JToken value = x.Value;


                if (value.Count<object>() != 0 && name == "routes")
                {
                    foreach (var t in value)
                    { //percorrer as routes 
                        Viagem viagem = new Viagem();
                        string preco;


                        JToken fare_token = t["fare"];
                        if (fare_token != null)
                        {
                            JObject fare = t["fare"].Value<JObject>();
                            preco = (string)fare["text"]; //preço da viagem (daquela route)
                            viagem.setPreco(preco);
                        }



                        JToken legs = t["legs"].Value<JToken>();

                        if (legs != null)
                        {
                            foreach (var l in legs)
                            {


                                JToken steps = l["steps"].Value<JToken>();

                                if (steps != null)
                                {
                                    foreach (var s in steps)
                                    {
                                        JToken travel_mode_token = s["travel_mode"];

                                        string travel_mode = null;

                                        if (travel_mode_token != null) travel_mode = (string)s["travel_mode"];

                                        if (travel_mode.CompareTo("TRANSIT") == 0)
                                        {

                                            JToken td = s["transit_details"];

                                            if (td != null)
                                            {
                                                string tempo_chegada = (string)td["arrival_time"]["text"];
                                                string tempo_partida = (string)td["departure_time"]["text"];
                                                string paragem_chegada = (string)td["arrival_stop"]["name"];
                                                string paragem_partida = (string)td["departure_stop"]["name"];
                                                string numero_bus = (string)td["line"]["short_name"];
                                                string num_paragens = (string)td["num_stops"];

                                                Percurso p = null;

                                                string data_aux = date.ToString(" dd-MM-yyyy");
                                                tempo_partida = tempo_partida + data_aux;

                                                string data2_aux = date.ToString(" dd-MM-yyyy");
                                                tempo_chegada = tempo_chegada + data2_aux;


                                                if (numero_bus.All(char.IsDigit)) p = new Percurso(paragem_partida, paragem_chegada, tempo_partida, tempo_chegada, Int32.Parse(numero_bus), Int32.Parse(num_paragens));
                                                else p = new Percurso(paragem_partida, paragem_chegada, tempo_partida, tempo_chegada, -1, Int32.Parse(num_paragens));

                                                //p.nrautocarro = Int32.Parse(numero_bus);


                                                /*
                                                p.local_inicio = paragem_partida;
                                                p.local_destino = paragem_chegada;
                                                p.hora_fim = tempo_chegada;
                                                p.hora_inicio = tempo_partida;
                                                p.nrParagens = Int32.Parse(num_paragens);
                                                */


                                                viagem.addPercurso(p);
                                            }
                                        }
                                    }
                                }
                                if (viagem.percursos.Count != 0) //caso haja percursos
                                {
                                    viagem.num = i;

                                    viagens.Add(viagem);
                                    i++;
                                }

                            }
                        }


                    }
                }
            }


            return viagens;
        }


        public void atualizaPercurso(JObject json, Percurso p)
        {
            foreach (var x in json)
            {
                string name = x.Key;
                JToken value = x.Value;


                if (value.Count<object>() != 0 && name == "rows")
                {
                    foreach (var v in value)
                    {
                        JToken elements_token = v["elements"];

                        if (elements_token != null)
                        {
                            JToken elements = v["elements"].Value<JToken>();

                            if (elements != null)
                            {
                                foreach (var e in elements)
                                {
                                    JToken duration = e["duration"].Value<JToken>();

                                    string time = (string)duration["text"];

                                    p.atualizaHoraP(time);
                                }
                            }

                        }
                    }

                }
            }
        }




        public void calculaHorarioPenultima(Viagem v)
        {
            string request_api = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&";
            string key = "&key=AIzaSyBF98jX9qVO-bB8pr-x6GIo9TGTDXqR3Go";

            List<Percurso> percursos = v.percursos;

            foreach (Percurso p in percursos)
            {
                if (!string.IsNullOrEmpty(p.local_penultima)) //se aquele percurso tiver informação de penultima paragem
                {
                    string origem = "origins=" + p.local_penultima.Replace(" ", "%20");
                    string destino = "&destinations=" + p.local_destino.Replace(" ", "%20");

                    string request_complete = request_api + origem + destino + key;

                    WebRequest requestObjGet = WebRequest.Create(request_complete);
                    requestObjGet.Method = "GET";
                    HttpWebResponse responseObjGet = null;
                    responseObjGet = (HttpWebResponse)requestObjGet.GetResponse();

                    string result = null;
                    using (Stream stream = responseObjGet.GetResponseStream())
                    {
                        StreamReader sr = new StreamReader(stream);
                        result = sr.ReadToEnd(); //jason em formato de string
                        sr.Close(); //fechar a streamreader
                    }


                    JObject json = JObject.Parse(result);

                    atualizaPercurso(json, p);
                }

            }

        }


        protected void Submit(object sender, EventArgs e)
        {

        }
    }
}
