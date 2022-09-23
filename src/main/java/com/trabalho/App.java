package com.trabalho;

import java.io.IOException;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App {
    static CentroDistribuicao cd = new CentroDistribuicao(0, 0, 0, 0);
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException, IOException {
        viewMenu();
        if (System.getProperty("os.name").contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }

    }

    public static void viewMenu() throws InterruptedException, IOException {
        boolean exit = false;
        boolean start = false;

        while (start == false) {
            System.out.println(
                    "\nDigite a quantidade de combustivel que deseja iniciar em seu posto: \n1- Se desejar adicionar o máximo de combustivel.\n2- Se desejar adicionar um valor específico.\n0- Se deseja sair.\n");
            String max = sc.next();
            if (max.equals("1")) {
                cd.recebeGasolina(10000);
                cd.recebeAditivo(500);
                cd.recebeAlcool(2500);
                clearConsole();
                System.out.println("Combustivel inicializado com: " + cd.gettGasolina() + " litros de gasolina, "
                        + cd.gettAditivo() + " litros de aditivo e " + (cd.gettAlcool1() + cd.gettAlcool2())
                        + " litros de alcool.");
                start = true;
                break;
            }
            if (max.equals("2")) {
                System.out.println("GASOLINA: ");
                int gasolina = sc.nextInt();
                if (gasolina < 0 || gasolina > 10000) {
                    throw new IllegalNumberException();
                }
                System.out.println("ALCOOL: ");
                int alcool = sc.nextInt();
                if (alcool < 0 || alcool > 2500) {
                    throw new IllegalNumberException();
                }
                System.out.println("DIESEL: ");
                int diesel = sc.nextInt();
                if (diesel < 0 || diesel > 500) {
                    throw new IllegalNumberException();
                }
                cd.recebeGasolina(gasolina);
                cd.recebeAlcool(alcool);
                cd.recebeAditivo(diesel);
                clearConsole();
                System.out.println("Combustivel inicializado com: " + cd.gettGasolina() + " litros de gasolina, "
                        + cd.gettAditivo() + " litros de aditivo e " + (cd.gettAlcool1() + cd.gettAlcool2())
                        + " litros de alcool.");
                start = true;
                break;
            }
            if (max.equals("0")) {
                System.out.println("Saindo");
                System.exit(0);
            } else {
                System.out.println("Valor inválido");
            }
        }
        while (exit == false) {
            System.out.println("\nMENU PRINCIPAL\n");
            System.out.println("1 - Adicionar combustível");
            System.out.println("2 - Retirar combustível");
            System.out.println("3 - Verificar estoque");
            System.out.println("4 - Verificar situação");
            System.out.println("0 - Sair\n");
            int opc = sc.nextInt();

            switch (opc) {
                case 1:
                    clearConsole();
                    System.out.println("\nDigite o tipo de combustível que deseja adicionar: ");
                    System.out.println("1 - Aditivo");
                    System.out.println("2 - Gasolina");
                    System.out.println("3 - Álcool");
                    System.out.println("4- Se deseja reabastercer o posto até o máximo.");
                    System.out.println("0 - Voltar ao menu principal\n");
                    int opc1 = sc.nextInt();
                    switch (opc1) {
                        case 1:
                            System.out.println("\nDigite a quantidade de aditivo que deseja adicionar: ");
                            int qtdAditivo = sc.nextInt();
                            int a = cd.recebeAditivo(qtdAditivo);
                            if (a < 0) {
                                clearConsole();
                                System.out.println("\nQuantidade inválida");
                            } else {
                                clearConsole();
                                System.out.println("\nAditivo adicionado com sucesso!");
                            }
                            break;
                        case 2:

                            System.out.println("\nDigite a quantidade de gasolina que deseja adicionar: ");
                            int qtdGasolina = sc.nextInt();
                            a = cd.recebeGasolina(qtdGasolina);
                            if (a < 0) {
                                clearConsole();
                                System.out.println("\nQuantidade inválida");
                            } else {
                                clearConsole();
                                System.out.println("\nGasolina adicionada com sucesso!");
                            }
                            break;
                        case 3:

                            System.out.println("\nDigite a quantidade de álcool que deseja adicionar: ");
                            int qtdAlcool = sc.nextInt();
                            a = cd.recebeAlcool(qtdAlcool);
                            if (a < 0) {
                                clearConsole();
                                System.out.println("\nQuantidade inválida");
                            } else {
                                clearConsole();
                                System.out.println("\nÁlcool adicionado com sucesso!");
                            }
                            break;
                        case 4:

                            int auxGasolina = 10000 - cd.gettGasolina();
                            int auxAlcool1 = 1250 - cd.gettAlcool1();
                            int auxAlcool2 = 1250 - cd.gettAlcool2();
                            int auxAditivo = 500 - cd.gettAditivo();

                            cd.recebeGasolina(auxGasolina);
                            cd.recebeAlcool(auxAlcool1);
                            cd.recebeAlcool(auxAlcool2);
                            cd.recebeAditivo(auxAditivo);
                            clearConsole();
                            System.out.println("\nPosto reabastecido com sucesso!");
                            break;
                        case 0:
                            clearConsole();
                            break;

                        default:
                            clearConsole();
                            System.out.println("\nOpção inválida");
                    }
                    break;
                case 2:
                    clearConsole();
                    System.out.println("\n\n\n\n\nDigite a quantidade de combustível que deseja retirar: ");
                    int qtdCombustivel = sc.nextInt();
                    if (qtdCombustivel < 10) {
                        System.out.println("Quantidade mínima de combustível a ser retirada é 10");
                        break;
                    }
                    System.out.println("\nDigite o tipo de posto que deseja retirar o combustível: ");
                    System.out.println("1 - COMUM");
                    System.out.println("2 - ESTRATEGICO");
                    System.out.println("3- Voltar\n");
                    int opc2 = sc.nextInt();
                    switch (opc2) {
                        case 1:
                            int[] a = cd.encomendaCombustivel(qtdCombustivel, CentroDistribuicao.TIPOPOSTO.COMUM);
                            if (a[0] == -7) {
                                clearConsole();
                                System.out.println("\nValor inválido");
                                break;
                            }
                            if (a[0] == -14) {
                                clearConsole();
                                System.out.println(
                                        "\nA situação do centro de distribuição é crítica portanto só abastece postos estratégicos");
                                break;
                            }
                            if (a[0] == -21) {
                                clearConsole();
                                System.out.println("\nCombustível insuficiente");
                                break;
                            }
                            if (cd.getSituacao() == CentroDistribuicao.SITUACAO.SOBRAVISO) {
                                clearConsole();
                                System.out.println(
                                        "\nComo a situação é de SOBRAVISO, só é possível abastecer 50% do pedido!\nSendo ele: "
                                                + (qtdCombustivel / 2));
                                System.out.println("\nCombustível ainda disponível:\nGasolina: " + a[1] + " Álcool: "
                                        + (a[2] + a[3]) + " Aditivo: " + a[0] + "\nSituação atual: "
                                        + cd.getSituacao());
                            } else {
                                clearConsole();
                                System.out.println(
                                        "\nCombustível ainda disponível:\nGasolina: " + a[1] + " Álcool: "
                                                + (a[2] + a[3]) + " Aditivo: " + a[0] + "\nSituação atual: "
                                                + cd.getSituacao());
                            }
                            break;
                        case 2:
                            a = cd.encomendaCombustivel(qtdCombustivel, CentroDistribuicao.TIPOPOSTO.ESTRATEGICO);
                            if (a[0] == -7) {
                                clearConsole();
                                System.out.println("\nValor inválido");
                            }
                            if (a[0] == -21) {
                                clearConsole();
                                System.out.println("\nCombustível insuficiente");
                            }
                            if (cd.getSituacao() == CentroDistribuicao.SITUACAO.EMERGENCIA) {
                                clearConsole();
                                System.out.println(
                                        "\nComo a situação é de EMERGÊNCIA, só é possível abastecer 50% do pedido!\nSendo ele: "
                                                + (qtdCombustivel / 2));
                                System.out.println("\nCombustível ainda disponível:\nGasolina: " + a[1] + " Álcool: "
                                        + (a[2] + a[3]) + " Aditivo: " + a[0] + "\nSituação atual: "
                                        + cd.getSituacao());
                            } else {
                                clearConsole();
                                System.out.println(
                                        "\nCombustível ainda disponível:\nGasolina: " + a[1] + " Álcool: "
                                                + (a[2] + a[3]) + " Aditivo: " + a[0] + "\nSituação atual: "
                                                + cd.getSituacao());
                            }
                            break;
                        case 3:
                            break;
                        default:
                            clearConsole();
                            System.out.println("\nOpção inválida");
                    }

                    break;
                case 3:
                    clearConsole();
                    System.out.println("\nEstoque Disponível:\n");
                    System.out.println("Gasolina: " + cd.gettGasolina());
                    System.out.println("Álcool: " + (cd.gettAlcool1() + cd.gettAlcool2()));
                    System.out.println("Aditivo: " + cd.gettAditivo());
                    break;
                case 4:
                    clearConsole();
                    
                    if(cd.getSituacao() == CentroDistribuicao.SITUACAO.NORMAL){
                        System.out.println("\nSituação atual do posto é: " + cd.getSituacao());
                        break;
                    }
                    if(cd.getSituacao() == CentroDistribuicao.SITUACAO.SOBRAVISO || cd.getSituacao() == CentroDistribuicao.SITUACAO.EMERGENCIA){
                        System.out.println("\nSituação atual do posto é de : " + cd.getSituacao() + "!");
                        break;
                    }
                   break;
                case 0:
                    clearConsole();
                    exit = true;
                    System.out.println("\nSaindo...");
                    System.exit(0);
                    break;
                default:
                    clearConsole();
                    System.out.println("\nOpção inválida");
                    break;
            }

        }
    }

    public static void clearConsole() throws InterruptedException, IOException {
        if (System.getProperty("os.name").contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
    }
}
