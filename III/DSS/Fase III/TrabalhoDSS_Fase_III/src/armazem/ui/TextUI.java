package armazem.ui;

import armazem.business.ArmazemFacade;
import armazem.business.IArmazemFacade;
import armazem.business.abastecimento.Robot;
import armazem.business.catalogo.Localizacao;
import armazem.business.catalogo.Palete;

import java.util.List;
import java.util.Scanner;

public class TextUI {
    // O model tem a 'lógica de negócio'.
    private IArmazemFacade model;

    // Scanner para leitura
    private Scanner scin;

    /**
     * Construtor.
     *
     * Cria os menus e a camada de negócio.
     */
    public TextUI() {

        this.model = new ArmazemFacade();
        scin = new Scanner(System.in);
    }

    /**
     * Executa o menu principal e invoca o método correspondente à opção seleccionada.
     */
    public void run() {
        System.out.println("\033[1;94m" + "Bem vindo ao Sistema de Gestão do Armazem!" + "\u001B[0m");
        this.menuPrincipal();
        System.out.println( "\u001B[1;94m" + "\nAté breve... \u272F" + "\u001B[0m");
    }

    // Métodos auxiliares - Estados da UI

    /**
     * Estado - Menu Principal
     */
    private void menuPrincipal() {
        Menu menu = new Menu(new String[]{
                "Consultar listagem",
                "Registar nova palete",
                "Comunicar ordem de transporte",
                "Operações do Robot",
        });

        // Registar pré-condições das transições
        menu.setPreCondition(2, ()->this.model.existeEspacoArmazem());
        menu.setPreCondition(3, ()->this.model.existePaleteRececao() && this.model.existeRobot());

        // Registar os handlers
        menu.setHandler(1, ()->consultarListagem());
        menu.setHandler(2, ()->registarPalete());
        menu.setHandler(3, ()->ordenarTransporte());
        menu.setHandler(4, ()->gestaoDeRobot());

        menu.run();
    }

    /**
     *  Estado - Gestão de Robot
     */
    private void gestaoDeRobot() {

        System.out.println("Insira robot a notificar: ");
        System.out.print("\033[34m" + "\u21DB " + "\u001B[0m");
        String robot = scin.nextLine();

        if (robot.equals("A") || robot.equals("B") || robot.equals("C") || robot.equals("D")) {
            String palete = model.getPaleteRobot(robot);

            Menu menu = new Menu(new String[]{
                    "Notificar Recolha",
                    "Notificar Entrega"
            });

            menu.setPreCondition(1, () -> (palete != null) && this.model.getEstadoPalete(palete).equals("aguardaTransporte"));
            menu.setPreCondition(2, () -> (palete != null) && this.model.getEstadoPalete(palete).equals("robot"));

            // Registar os handlers
            menu.setHandler(1, () -> notificarRecolhaPalete(palete, robot));
            menu.setHandler(2, () -> notificarEntregaPalete(palete, robot));

            menu.run();
        }
        else System.out.println("\u001B[31m" + "ERROR: Robot Inválido! Selecione um dos Robots A,B,C ou D." + "\u001B[0m");
    }

    /**
     * Método que permite consultar a listagem do armazém
     */
    private void consultarListagem() {
        try {
            List<String> listagem = model.getListagem();
            System.out.println();
            for(String s: listagem) {
                System.out.println(s);
            }
        } catch (Exception e) {
            System.out.println("\u001B[31m" + "ERROR: Impossível obter listagem" + "\u001B[0m");
        }
    }

    /**
     * Método que permite registar uma palete
     */
    private void registarPalete() {
        System.out.println("Insira o QRCode da palete a adicionar: ");
        System.out.print("\033[34m" + "\u21DB " + "\u001B[0m");
        String qrCode = scin.nextLine();
        if (model.validarQRCode(qrCode)) {
            String codPalete = model.registarPalete(model.getQRCode(qrCode));
            System.out.println("Código da palete adicionada: " + "\033[34m" + codPalete + "\u001B[0m");
            model.atualizaOcupacao(0);
        } else {
            System.out.println("\u001B[31m" + "ERROR: QRCode inválido" + "\u001B[0m");
        }
    }

    /**
     * Método que permite ordenar transporte de uma palete
     */
    private void ordenarTransporte() {
        try {
            Palete p = model.getPalete("rececao");

            Localizacao l = model.getLocalizacao();

            Robot r = model.getRobot();

            List<Integer> percurso = model.getPercurso(l, p);
            model.atualizarEstadoPalete(p.getCodPalete(), l);
            model.notificarRobot(p, percurso, r);

            System.out.println(r.toString());

        } catch (Exception e) {
            System.out.println("\u001B[31m" + "ERROR: Impossível ordenar transporte" + "\u001B[0m");
        }
    }

    /**
     * Método que permite notificar a recolha de uma palete
     * @param palete a recolher
     * @param robot que vai recolher a palete
     */
    private void notificarRecolhaPalete(String palete, String robot) {
        try {
            model.atualizarEstadoPalete(palete, null);
            System.out.println("Robot " + "\033[34m" + robot + "\u001B[0m" + " notificou a recolha da palete " + "\033[34m" + palete);
        } catch (Exception e) {
            System.out.println("\u001B[31m" + "ERROR: Erro ao notificar recolha" + "\u001B[0m");
        }
    }

    /**
     * Método que permite notificar entrega de uma palete
     * @param palete a entregar
     * @param robot que irá entregar a palete
     */
    private void notificarEntregaPalete(String palete, String robot) {
        try {
            Localizacao l = model.getLocalizacaoPalete(robot);
            model.atualizarEstadoPalete(palete, null);
            model.atualizaRobot(robot);
            System.out.println("Robot " + "\033[34m" + robot + "\u001B[0m" + " notificou da entrega da palete " + "\033[34m" + palete + "\u001B[0m" + " em " + l.toString());
        } catch (Exception e) {
            System.out.println("\u001B[31m" + "ERROR: Erro ao notificar entrega" + "\u001B[0m");
        }
    }
}
