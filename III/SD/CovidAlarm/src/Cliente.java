import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Cliente {

    public static void menuBasico(Demultiplexer m, String nomeUtilizador) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String opcao;

        do {
            Thread.sleep(50);

            System.out.println("\033[1;32m" + "*** MENU PRINCIPAL ***" + "\033[0m");
            System.out.println("\033[1;32m" + "1" + "\033[0m" + " Alterar Localizacao de Utilizador");
            System.out.println("\033[1;32m" + "2" + "\033[0m" + " Saber Número de Utilizadores numa Localizacao");
            System.out.println("\033[1;32m" + "3" + "\033[0m" + " Mover-se para Localizacao quando estiver livre");
            System.out.println("\033[1;32m" + "4" + "\033[0m" + " Notificar infeção de Utilizador com COVID");
            System.out.println("\033[1;32m" + "5" + "\033[0m" + " Terminar sessão");
            System.out.print("\u001B[1;32m" + "=> " + "\u001B[0m");
            opcao = in.readLine();

            switch (opcao) {
                case "1": {
                    System.out.println("Localizacao Manual [S/N]");
                    System.out.print("\u001B[1;32m" + "=> " + "\u001B[0m");
                    String res = in.readLine();

                    if (res.equals("S") || res.equals("s")) {
                        System.out.println("Localizacao x: ");
                        System.out.print("\u001B[1;32m" + "=> " + "\u001B[0m");
                        int x = Input.lerInt();
                        System.out.println("Localizacao y: ");
                        System.out.print("\u001B[1;32m" + "=> " + "\u001B[0m");
                        int y = Input.lerInt();
                        if (x > 5 || x < 1 || y > 5 || y < 1) {
                            System.out.println("\033[1;31m" + "Valores inválidos \n" + "\033[0m");
                            break;
                        }

                        new Thread(() -> {
                            try {
                                m.sendCoordenadas(2, nomeUtilizador, x, y);
                                byte[] data = m.receive(2);
                                if (data != null) {
                                    String msg = new String(data);
                                    System.out.println("\033[1;33m" + "\nResposta a 1: " + "\033[0m");
                                    System.out.println(msg + "\n");
                                }

                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).start();

                    } else if (res.equals("N") || res.equals("n")) {
                        Coordenadas c = new Coordenadas();

                        new Thread(() -> {
                            try {
                                m.sendCoordenadas(2, nomeUtilizador, c.getX(), c.getY());
                                byte[] data = m.receive(2);
                                if (data != null) {
                                    String msg = new String(data);
                                    System.out.println("\033[1;33m" + "\nResposta a 1: " + "\033[0m");
                                    System.out.println(msg + "\n");
                                }

                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).start();

                    } else {
                        System.out.println("\033[1;31m" + "Error\n" + "\033[0m");
                    }

                    break;
                }
                case "2": {
                    System.out.println("Localizacao x: ");
                    System.out.print("\u001B[1;32m" + "=> " + "\u001B[0m");
                    int x = Input.lerInt();
                    System.out.println("Localizacao y: ");
                    System.out.print("\u001B[1;32m" + "=> " + "\u001B[0m");
                    int y = Input.lerInt();
                    if (x > 5 || x < 1 || y > 5 || y < 1) {
                        System.out.println("Valores inválidos \n");
                        break;
                    }
                    new Thread(() -> {
                        try {
                            m.sendCoordenadas(3, nomeUtilizador, x, y);
                            byte[] data = m.receive(3);
                            if (data != null) {
                                System.out.println("\033[1;33m" + "\nResposta a 2: " + "\033[0m");
                                String msg = new String(data);
                                System.out.println(msg + "\n");
                            }
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                    break;
                }
                case "3": {
                    System.out.println("Localizacao x: ");
                    System.out.print("\u001B[1;32m" + "=> " + "\u001B[0m");
                    int x = Input.lerInt();
                    System.out.println("Localizacao y: ");
                    System.out.print("\u001B[1;32m" + "=> " + "\u001B[0m");
                    int y = Input.lerInt();
                    if (x > 5 || x < 1 || y > 5 || y < 1) {
                        System.out.println("Valores inválidos \n");
                        break;
                    }
                    new Thread(() -> {
                        try {
                            m.sendCoordenadas(4, nomeUtilizador, x, y);

                            byte[] data = m.receive(4);
                            if (data != null) {
                                System.out.println("\033[1;33m" + "\nResposta a 3: " + "\033[0m");
                                String msg = new String(data);
                                System.out.println(msg + "\n");
                            }
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                    break;
                }
                case "4": {
                    new Thread(() -> {
                        try {
                            m.sendNotificacao(5, nomeUtilizador);

                            byte[] data = m.receive(5);
                            if (data != null) {
                                String msg = new String(data);
                                System.out.println("\033[1;33m" + "\nResposta a 4: " + "\033[0m");
                                System.out.println(msg + "\n");
                                m.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                    break;
                }
                case "5": {
                    new Thread(() -> {
                        try {
                            m.sendNotificacao(6, nomeUtilizador);

                            byte[] data = m.receive(6);
                            if (data != null) {
                                String msg = new String(data);
                                System.out.println("\033[1;33m" + "\nResposta a 5: " + "\033[0m");
                                System.out.println(msg + "\n");
                                m.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                    break;
                }
                default: {
                    System.out.println("\033[1;31m" + "Error\n" + "\033[0m");
                    break;
                }
            }
        } while (!(opcao.equals("4") || opcao.equals("5")));
    }

    public static void menuCliente() throws Exception {

        Socket s = new Socket("localhost", 12345);
        Demultiplexer m = new Demultiplexer(new TaggedConnection(s));
        m.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        boolean autenticado = false;
        String nomeUtilizador;

        String opcao;
        System.out.println("\033[1;92m" + "*** ALARME COVID ***" + "\u001B[0m" + "\n");

        do {
            System.out.println("\u001B[1;32m" + "*** LOGIN ***" );
            System.out.println("1 " + "\u001B[0m" + "Registar Cliente");
            System.out.println("\u001B[1;32m" + "2 " + "\u001B[0m" + "Autenticação");
            System.out.print("\u001B[1;32m" + "=> " + "\u001B[0m");
            opcao = in.readLine();

            switch (opcao) {
                case "1": {
                    System.out.println("\u001B[1;32m" + "\n** Registo **" + "\u001B[0m");
                    System.out.println("Nome Utilizador: ");
                    System.out.print("\u001B[1;32m" + "=> " + "\u001B[0m");
                    String nome = in.readLine();
                    System.out.println("Palavra Passe: ");
                    System.out.print("\u001B[1;32m" + "=> " + "\u001B[0m");
                    String passe = in.readLine();

                    m.sendAutenticacao(1, nome, passe);

                    // get reply
                    byte[] data = m.receive(1);
                    String msg = new String(data);
                    System.out.println(msg + "\n");

                    if (!msg.equals("Nome de utilizador já existe")) {
                        autenticado = true;
                        nomeUtilizador = nome;

                        new Thread(() -> {
                            try {
                                boolean res = true;
                                while (res) {
                                    byte[] notificacao = m.receive(7);
                                    if (notificacao != null) {
                                        String info = new String(notificacao);
                                        System.out.println("\033[1;31m" + info + "\033[0m\n");
                                        System.out.print("\u001B[1;32m" + "=> " + "\u001B[0m");
                                    } else res = false;
                                } } catch(Exception e){
                                e.printStackTrace();
                            }
                        }).start();
                        menuBasico(m, nomeUtilizador);
                        System.out.println("\u001B[31m" + "Cliente Desconectado do servidor\n" + "\u001B[0m");
                    }
                    break;
                }
                case "2": {
                    System.out.println("\u001B[1;32m" + "\n** Autenticação **" + "\u001B[0m");
                    System.out.println("Nome Utilizador: ");
                    System.out.print("\u001B[1;32m" + "=> " + "\u001B[0m");
                    String nome = in.readLine();
                    System.out.println("Palavra Passe: ");
                    System.out.print("\u001B[1;32m" + "=> " + "\u001B[0m");
                    String passe = in.readLine();

                    m.sendAutenticacao(0, nome, passe);

                    // get reply
                    byte[] data = m.receive(0);
                    String autenticacao = new String(data);
                    System.out.println(autenticacao + "\n");

                    if (autenticacao.equals("Autenticação com sucesso")) {
                        autenticado = true;
                        nomeUtilizador = nome;

                        new Thread(() -> {
                            try {
                                boolean res = true;
                                while (res) {
                                    byte[] notificacao = m.receive(7);
                                    if (notificacao != null) {
                                        String info = new String(notificacao);
                                        System.out.println("\033[1;31m" + info + "\n" + "\033[0m");
                                        System.out.print("\u001B[1;32m" + "=> " + "\u001B[0m");
                                    } else res = false;
                                } } catch(Exception e){
                                    e.printStackTrace();
                                }
                        }).start();

                        menuBasico(m, nomeUtilizador);
                        System.out.println("\u001B[31m" + "Cliente Desconectado do servidor\n" + "\u001B[0m");
                    }
                    break;
                }
                default: {
                    System.out.println("\033[1;31m" + "Error\n" + "\033[0m");
                    break;
                }
            }
        } while (!autenticado);
    }

    public static void main(String[] args) {

        Runnable worker = () -> {
            try {
                menuCliente();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(worker).start();
    }
}