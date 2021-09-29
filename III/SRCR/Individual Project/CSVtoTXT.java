import java.io.*;
import java.util.*;

// TIPOS DE LIXO: LIXO | PAPEL E CARTAO| EMBALAGENS| ORGANICOS| VIDRO
public class CSVtoTXT
{
    public static void main(String[] args) throws IOException {
        List<List<String>> records = new ArrayList<>(); //linha atual CSV
        Map<String, List<String>> ruaID = new HashMap<>(); //Alecrim :: 355, 364
        Map<String, String> spotID = new HashMap<>(); // 15805:: 355
        Map<String, List<String>> ruaSPOT = new HashMap<>(); // Alecrim:: [15805,15806]

        try (BufferedReader br = new BufferedReader(new FileReader("dataMini.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                records.add(Arrays.asList(values));
            }
        }
        List<List<String>> aux = new ArrayList<>(); //linha (lat,long, id, rua, vizinho, tipo, qnt)
        List<String> record;
        // 0 é o cabeçalho
        for (int i = 1; i < records.size(); i++) {
            record = records.get(i);

            List<String> newList = new ArrayList<>();
            newList.add(record.get(0)); // latitude
            newList.add(record.get(1)); // longitude
            newList.add(record.get(2)); // firstID


            String adjacentes = "", rua = "";
            String vizinhos = record.get(4);
            String[] v = vizinhos.split(": ");

            String spot = v[0];
            spotID.putIfAbsent(spot, record.get(2));

            if (v.length == 2 ){
                String [] auxRua = v[1].split(",");
                rua = auxRua[0];
                adjacentes = new String("[]");
            } // sem vizinho
            else if (v.length == 3 ){
                String [] auxRua1 = v[1].split("[(]");
                String ruaComEspacoFinal = auxRua1[0];
                rua = ruaComEspacoFinal.substring(0, ruaComEspacoFinal.length() - 1);

                String [] auxVizinho = v[2].split(" -");
                String adjacente = auxVizinho[0];
                adjacentes = "[" + adjacente + "]";
            } // tem vizinho

            newList.add(rua); // nome rua
            newList.add(adjacentes); // nome adjacentes ou vazio
            newList.add(record.get(5)); // tipoLixo
            newList.add(record.get(9)); // quantidadeLixo

            aux.add(newList); //indice i


            String nomeRua = rua;
            List<String> place = new ArrayList<>();
            if(ruaSPOT.get(rua) != null) place.addAll(ruaSPOT.get(rua));
            if(place.contains(spot) == false )place.add(spot);
            ruaSPOT.put(rua,place); //ALECRIM :: 15805, 15806

        }

        //map : rua -> ids rua
        for(Map.Entry<String,List<String>> rua : ruaSPOT.entrySet()) {
            String auxRua = rua.getKey();
            List<String> auxSPOTS = rua.getValue();
            List<String> IDS = new ArrayList<>();

            for (String spot : auxSPOTS) {
                String ID = spotID.get(spot);
                IDS.add(ID);
            }
            ruaID.put(auxRua, IDS);
        }


        System.out.println("-----------DATASET-----------");
        System.out.println("Linhas Lidas : " + records.size());
        System.out.println("Cabeçalho : " + records.get(0));
        System.out.println("1ª Linha : " + records.get(1));
        System.out.println("1ª Linha : " + records.get(2));
        System.out.println("Ultima Linha: "+ records.get(records.size()-1));

        System.out.println("-----------DEBUG-----------");
        System.out.println("-----Nova Formatação-----");

        System.out.println("Numero Linhas Formatadas: " + aux.size());
        System.out.println("1º Linha  : " + aux.get(0));
        System.out.println("2º Linha  : " + aux.get(1));
        System.out.println("Ultima Linha: "+ aux.get(aux.size()-1));

        /*
        System.out.println("-----Map SPOT - ID-----");
        System.out.println("Nr. IDS ruas : " + spotID.size());
        for (Map.Entry<String, String> entry : spotID.entrySet()){
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
        }
         */
        System.out.println("-----Map RUA - SPOT-----");
        System.out.println("Nr. IDS ruas : " + ruaSPOT.size());
        for (Map.Entry<String,List<String>> entry : ruaSPOT.entrySet()){
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
        }
        System.out.println("-----Map RUA - ID-----");
        System.out.println("Nr. IDS ruas : " + ruaID.size());
        for (Map.Entry<String,List<String>> entry : ruaID.entrySet()){
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
        }

        List<List<String>> agrupada = new ArrayList<>(); //linha (lat,long, id, rua, vizinhos, qntLixo,qntPapel,qntEmbalagem,qntVidro,qntOrganicos)
                                                        //[-9.143308809, 38.70807879, 355, 'R do Alecrim', [368], 1860, 1530, 0, 0, 0]
        List<String> lF;
        List<String> nlF;
        boolean flag = true;
        int indiceAtual;

        for(int i =0; i < aux.size(); i++){
            indiceAtual = i;
            String lat = aux.get(i).get(0);
            String lon = aux.get(i).get(1);
            String idRua = aux.get(i).get(2);
            String ruaAtual = aux.get(i).get(3);

            String ruaVizinhaAux = aux.get(i).get(4);
            String ruaVizinha = ruaVizinhaAux.substring( 1, ruaVizinhaAux.length() - 1 ); //erro se for vazio
            List<String> idsRuaVizinhos = ruaID.get(ruaVizinha); // 4
            String tipoLixo = aux.get(i).get(5); //5
            //6 - quantidade somada - TIPO A
            //7 - tipo B
            //8 - TIPO C
            //

            if (i!=(aux.size()-1)) {
                lF = aux.get(i);
                nlF = aux.get(i+1);
            }
            else {
                lF =  aux.get(i);
                nlF = aux.get(i);
                flag = false;
            }
            Integer qntVidro = 0;
            Integer qntEmbalagens = 0;
            Integer qntOrganic = 0;
            Integer qntPapelCartao = 0;
            Integer qntLixo = 0;
            switch (tipoLixo){
                case "Lixos":
                    qntLixo = Integer.parseInt(lF.get(6));qntLixo = Integer.parseInt(lF.get(6));
                    break;
                case "Papel e Cartão":
                    qntPapelCartao = Integer.parseInt(lF.get(6));
                    break;
                case "Embalagens":
                    qntEmbalagens = Integer.parseInt(lF.get(6));
                    break;
                case "Vidro":
                    qntVidro = Integer.parseInt(lF.get(6));
                    break;
                case "Organicos":
                    qntOrganic = Integer.parseInt(lF.get(6));
                    break;
                default:
                    System.out.println("Erro a saber tipo de lixo");
                    break;
            }
            while( flag && lF.get(0).equals(nlF.get(0))
                    && lF.get(1).equals(nlF.get(1))
                    //&& lF.get(5).equals(nlF.get(5)) //tipo lixo igual - REMOVER ISTO
            ){
                switch (nlF.get(5)){
                    case "Lixos":
                        qntLixo += Integer.parseInt(nlF.get(6));
                        break;
                    case "Papel e Cartão":
                        qntPapelCartao += Integer.parseInt(nlF.get(6));
                        break;
                    case "Embalagens":
                        qntEmbalagens += Integer.parseInt(nlF.get(6));
                        break;
                    case "Vidro":
                        qntVidro += Integer.parseInt(nlF.get(6));
                        break;
                    case "Organicos":
                        qntOrganic += Integer.parseInt(nlF.get(6));
                        break;
                    default:
                        System.out.println("Erro a ACUMULAR tipo de lixo");
                        break;
                }

                i++;
                if (i!=(aux.size()-1)) {
                    lF = aux.get(i);
                    nlF = aux.get(i+1);
                }
                else {
                    lF =  aux.get(i);
                    nlF = aux.get(i);
                    flag = false;
                }
            }
            //next Vizinho
            String nextIDVizinho;
            if(i != aux.size()-1){
                nextIDVizinho = aux.get(i+1).get(2);
            }
            else {
                nextIDVizinho = null;
            }


            //put lF
            List<String> listaAgrupada = new ArrayList<>();
            listaAgrupada.add(lat.replace(',','.'));
            listaAgrupada.add(lon.replace(',','.'));
            listaAgrupada.add(idRua);
            String plicasRuaAtual = "'" + ruaAtual +"'";
            listaAgrupada.add(plicasRuaAtual);
            if(idsRuaVizinhos == null){
                if (nextIDVizinho != null) listaAgrupada.add("["+nextIDVizinho+"]");
                else listaAgrupada.add("[]");
            }
            else{
                StringBuilder strbul=new StringBuilder();
                strbul.append("[");
                for(String str : idsRuaVizinhos)
                {
                    strbul.append(str);
                    //for adding comma between elements
                    strbul.append(", ");
                }
                //remove last ", "
                if(!idsRuaVizinhos.contains(nextIDVizinho) && nextIDVizinho != null){
                    strbul.append(nextIDVizinho);
                }
                else strbul.setLength(strbul.length()-2);
                strbul.append("]");
                String str = strbul.toString();

                listaAgrupada.add(str); //vizinhos
            }


            // MUDAR ESTA PARTE TIRAR TIPO DE LIXO E METER VALORES
            // LIXO   PAPEL   EMBALAGENS   VIDRO   ORGANICOS

            //String plicasTipoLixo = "'" + tipoLixo +"'";
            //listaAgrupada.add(plicasTipoLixo.replace('ã','a'));

            listaAgrupada.add(String.valueOf(qntLixo)); //quantidade   5
            listaAgrupada.add(String.valueOf(qntPapelCartao)); //quantidade
            listaAgrupada.add(String.valueOf(qntEmbalagens)); //quantidade
            listaAgrupada.add(String.valueOf(qntVidro)); //quantidade
            listaAgrupada.add(String.valueOf(qntOrganic)); //quantidade 9

            agrupada.add(listaAgrupada);
        }

        // --------------------------------------------------
        System.out.println("-----------AGRUPAMENTOS-----------");
        System.out.println("Linhas Lidas : " + agrupada.size());
        System.out.println("Agrupamento : " + agrupada.get(0));
        System.out.println("1º Agrupamento : " + agrupada.get(1));
        System.out.println("2º Agrupamento : " + agrupada.get(2));
        System.out.println("Ultimo Agrupamento: "+ agrupada.get(agrupada.size()-1));

        //testar agrupamentos
        for(List<String> linhaAgrupada : agrupada){

            //Integer id = ruaID.get(linhaAgrupada.get(3));

            //linhaAgrupada.set(3, String.valueOf(id));

            System.out.println(linhaAgrupada);    //está bom porque vai indicar o primeiro vizinho
                                                   //ID da rua, e depois se precisar avanca pela rua
        }

        // PL file
        File plOutputFile = new File("miniData.pl");
        try (PrintWriter pw = new PrintWriter(plOutputFile)) {

            for (List<String> e : agrupada) {

                StringBuilder strbul=new StringBuilder();
                strbul.append("ponto(");
                for(String str : e)
                {
                    strbul.append(str);
                    //for adding comma between elements
                    strbul.append(", ");
                }
                //just for removing last comma
                strbul.setLength(strbul.length()-2);
                strbul.append(").");
                String str=strbul.toString();
                pw.write(str);
                pw.write("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // --------------------------------------------------
        /*
        List<List<String>> resultado = new ArrayList<>();
        for(int x = 0; x < aux.size(); x++){

            while( (x+1 != aux.size()) ){


            }
            Integer id = ruaID.get(linhaFormatada.get(3));

            linhaFormatada.set(3, String.valueOf(id));
            System.out.println(linhaFormatada);    //está bom porque vai indicar o primeiro vizinho
        }
        */

    }
}






/*
            while(record.get(0).equals(nextRecord.get(0)) && record.get(1).equals(nextRecord.get(1)) && record.get(6).equals(nextRecord.get(6))){


                int resultadoLixo = Integer.parseInt(record.get(7));
                int qntLixo = Integer.parseInt(nextRecord.get(7));
                int totalLixo = qntLixo+resultadoLixo;
                aux.get(indiceAtual).set(7,String.valueOf(totalLixo));//atualizar conta lixo

                //atualizar adjacentes, se não tiver acrescentamos novo vizinho

                // atualizar vizinho para proxima iteracao
                i++;
                nextRecord = records.get(i+1);
            }
            String vizinhosAtual = records.get(i).get(4);
            String nextVizinho = records.get(i+1).get(4);
            String vizinhosTodos = vizinhosAtual+nextVizinho;
            newList.set(4, vizinhosTodos);//adicionar aos vinhos a linha a seguir (caso não acabe ficheiro) (vizinho diferente)
            */