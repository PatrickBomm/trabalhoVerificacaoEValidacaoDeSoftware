package com.trabalho;

public class CentroDistribuicao {
    public enum SITUACAO {
        NORMAL, SOBRAVISO, EMERGENCIA;
    }

    public enum TIPOPOSTO {
        COMUM, ESTRATEGICO;
    }

    public static final int MAX_ADITIVO = 500;
    public static final int MAX_ALCOOL = 2500;
    public static final int MAX_GASOLINA = 10000;

    private int MAX_ALCOOL1;
    private int MAX_ALCOOL2;
    private int MAX_GASOLINA1;
    private int MAX_ADITIVO1;

    private static SITUACAO situacao; // Situação atual do centro de distribuição

    public CentroDistribuicao(int tAditivo, int tGasolina, int tAlcool1, int tAlcool2) {
        this.MAX_ADITIVO1 = tAditivo;
        this.MAX_GASOLINA1 = tGasolina;
        if ((tAlcool1 + tAlcool2) > MAX_ALCOOL) {
            throw new IllegalNumberException();
        }
        this.MAX_ALCOOL1 = tAlcool1;
        this.MAX_ALCOOL2 = tAlcool2;
        situacao = SITUACAO.NORMAL;
    }

    public void defineSituacao() {
        
        if (MAX_ADITIVO1 < (MAX_ADITIVO * 25)/100 || MAX_GASOLINA1 < (MAX_GASOLINA * 25)/100
                || (MAX_ALCOOL1 + MAX_ALCOOL2) < (MAX_ALCOOL * 25)/100) {
            setSituacao(SITUACAO.EMERGENCIA);
            return;
        }
        if (MAX_ADITIVO1 < (MAX_ADITIVO * 0.50) || MAX_GASOLINA1 < (MAX_GASOLINA * 0.50)
                || (MAX_ALCOOL1 + MAX_ALCOOL2) < (MAX_ALCOOL * 0.50)) {
            setSituacao(SITUACAO.SOBRAVISO);
            return;
        } else {
            setSituacao(SITUACAO.NORMAL);
        }
    }

    public void setSituacao(SITUACAO situacao) {
        CentroDistribuicao.situacao = situacao;
    }

    public SITUACAO getSituacao() {
        return situacao;
    }

    public int gettGasolina() {
        return MAX_GASOLINA1;
    }

    public int gettAditivo() {
        return MAX_ADITIVO1;
    }

    public int gettAlcool1() {
        return MAX_ALCOOL1;
    }

    public int gettAlcool2() {
        return MAX_ALCOOL2;
    }

    public int recebeAditivo(int qtdade) {
        if (MAX_ADITIVO1 + qtdade > MAX_ADITIVO || qtdade < 0) {
            return -1;
        } else {
            MAX_ADITIVO1 += qtdade;
            return MAX_ADITIVO - MAX_ADITIVO1;
        }
    }

    public int recebeGasolina(int qtdade) {
        if (MAX_GASOLINA1 + qtdade > MAX_GASOLINA || qtdade < 0) {
            return -1;
        } else {
            MAX_GASOLINA1 += qtdade;
            return MAX_GASOLINA - MAX_GASOLINA1;
        }
    }

    public int recebeAlcool(int qtdade) {
        if ((MAX_ALCOOL1 + qtdade + MAX_ALCOOL2) > MAX_ALCOOL || qtdade < 0) {
            return -1;
        }
        if (MAX_ALCOOL1 + qtdade <= (MAX_ALCOOL / 2)) {
            MAX_ALCOOL1 += qtdade;
            return MAX_ALCOOL1 - MAX_ALCOOL;
        } else {
            MAX_ALCOOL2 += qtdade;
            return MAX_ALCOOL - MAX_ALCOOL2;
        }
    }

    public int[] encomendaCombustivel(int qtdade, TIPOPOSTO tipoPosto) {
        int[] qtdCombustivel = new int[4];

        int auxAlcool = ((qtdade * 25) / 100);
        int auxGasolina = ((qtdade * 70) / 100);
        int auxAdtivo = 0;
        if(qtdade < 40 && qtdade > 10){
            auxAdtivo = 1;
        }else{
            auxAdtivo = ((qtdade * 5) / 100);
        }
        

        if (qtdade <= 0) {
            qtdCombustivel[0] = -7;
            return qtdCombustivel;
        }

        if (auxAlcool > (gettAlcool1() + gettAlcool2()) || auxGasolina > gettGasolina() || auxAdtivo > gettAditivo()) {
            qtdCombustivel[0] = -21;
            return qtdCombustivel;
        }

        if (tipoPosto == TIPOPOSTO.COMUM && situacao == SITUACAO.EMERGENCIA) {
            qtdCombustivel[0] = -14;
            return qtdCombustivel;
        }

        if (tipoPosto == TIPOPOSTO.COMUM) {
            if (situacao == SITUACAO.NORMAL) {

                MAX_GASOLINA1 = MAX_GASOLINA1 - auxGasolina;
                MAX_ADITIVO1 = MAX_ADITIVO1 - auxAdtivo;
                qtdCombustivel[0] = (MAX_ADITIVO1);
                qtdCombustivel[1] = (MAX_GASOLINA1);

                if (MAX_ALCOOL1 >= auxAlcool) {
                    MAX_ALCOOL1 -= auxAlcool;
                    qtdCombustivel[2] = (MAX_ALCOOL1);
                    qtdCombustivel[3] = (MAX_ALCOOL2);
                    defineSituacao();
                    return qtdCombustivel;
                }
                if (MAX_ALCOOL2 >= auxAlcool) {
                    MAX_ALCOOL2 -= auxAlcool;
                    qtdCombustivel[2] = (MAX_ALCOOL1);
                    qtdCombustivel[3] = (MAX_ALCOOL2);
                    defineSituacao();
                    return qtdCombustivel;
                } else if ((MAX_ALCOOL1 + MAX_ALCOOL2) >= auxAlcool) {
                    for (int j = auxAlcool; j > 0; j--) {
                        if (MAX_ALCOOL1 > 0) {
                            MAX_ALCOOL1--;
                        } else {
                            MAX_ALCOOL2--;
                        }
                    }
                    qtdCombustivel[2] = MAX_ALCOOL1;
                    qtdCombustivel[3] = MAX_ALCOOL2;
                    defineSituacao();
                    return qtdCombustivel;
                }
            }
            if (situacao == SITUACAO.SOBRAVISO) {
                auxAlcool = ((qtdade * 25) / 100) / 2;
                auxGasolina = ((qtdade * 70) / 100) / 2;
                auxAdtivo = ((qtdade * 5) / 100) / 2;

                MAX_GASOLINA1 = MAX_GASOLINA1 - auxGasolina;
                MAX_ADITIVO1 = MAX_ADITIVO1 - auxAdtivo;
                qtdCombustivel[0] = (MAX_ADITIVO1);
                qtdCombustivel[1] = (MAX_GASOLINA1);
                if (MAX_ALCOOL1 >= auxAlcool) {
                    MAX_ALCOOL1 -= auxAlcool;
                    qtdCombustivel[2] = MAX_ALCOOL1;
                    qtdCombustivel[3] = (MAX_ALCOOL2);
                    defineSituacao();
                    return qtdCombustivel;
                }
                if (MAX_ALCOOL2 >= auxAlcool) {
                    MAX_ALCOOL2 -= auxAlcool;
                    qtdCombustivel[2] = (MAX_ALCOOL1);
                    qtdCombustivel[3] = MAX_ALCOOL2;
                    defineSituacao();
                    return qtdCombustivel;
                } else if ((MAX_ALCOOL1 + MAX_ALCOOL2) >= auxAlcool) {
                    for (int j = auxAlcool; j > 0; j--) {
                        if (MAX_ALCOOL1 > 0) {
                            MAX_ALCOOL1--;
                        } else {
                            MAX_ALCOOL2--;
                        }
                    }
                    qtdCombustivel[2] = MAX_ALCOOL1;
                    qtdCombustivel[3] = MAX_ALCOOL2;
                    defineSituacao();
                    return qtdCombustivel;
                }

            }
        }
        if (tipoPosto == TIPOPOSTO.ESTRATEGICO) {
            if (situacao == SITUACAO.NORMAL || situacao == SITUACAO.SOBRAVISO) {

                MAX_GASOLINA1 = MAX_GASOLINA1 - auxGasolina;
                MAX_ADITIVO1 = MAX_ADITIVO1 - auxAdtivo;
                qtdCombustivel[0] = (MAX_ADITIVO1);
                qtdCombustivel[1] = (MAX_GASOLINA1);
                if (MAX_ALCOOL1 >= auxAlcool) {
                    MAX_ALCOOL1 -= auxAlcool;
                    qtdCombustivel[2] = (MAX_ALCOOL1);
                    qtdCombustivel[3] = (MAX_ALCOOL2);
                    defineSituacao();
                    return qtdCombustivel;
                }
                if (MAX_ALCOOL2 >= auxAlcool) {
                    MAX_ALCOOL2 -= auxAlcool;
                    qtdCombustivel[2] = (MAX_ALCOOL1);
                    qtdCombustivel[3] = (MAX_ALCOOL2);
                    defineSituacao();
                    return qtdCombustivel;
                } else if ((MAX_ALCOOL1 + MAX_ALCOOL2) >= auxAlcool) {
                    for (int j = auxAlcool; j > 0; j--) {
                        if (MAX_ALCOOL1 > 0) {
                            MAX_ALCOOL1--;
                        } else {
                            MAX_ALCOOL2--;
                        }
                    }
                    qtdCombustivel[2] = MAX_ALCOOL1;
                    qtdCombustivel[3] = MAX_ALCOOL2;
                    defineSituacao();
                    return qtdCombustivel;
                }

            }
            if (situacao == SITUACAO.EMERGENCIA) {
                auxAlcool = ((qtdade * 25) / 100) / 2;
                auxGasolina = ((qtdade * 70) / 100) / 2;
                auxAdtivo = ((qtdade * 5) / 100) / 2;

                MAX_GASOLINA1 = MAX_GASOLINA1 - auxGasolina;
                MAX_ADITIVO1 = MAX_ADITIVO1 - auxAdtivo;
                qtdCombustivel[0] = (MAX_ADITIVO1);
                qtdCombustivel[1] = (MAX_GASOLINA1);
                if (MAX_ALCOOL1 >= auxAlcool) {
                    MAX_ALCOOL1 -= auxAlcool;
                    qtdCombustivel[2] = (MAX_ALCOOL1);
                    qtdCombustivel[3] = (MAX_ALCOOL2);
                    defineSituacao();
                    return qtdCombustivel;
                }
                if (MAX_ALCOOL2 >= auxAlcool) {
                    MAX_ALCOOL2 -= auxAlcool;
                    qtdCombustivel[2] = (MAX_ALCOOL1);
                    qtdCombustivel[3] = (MAX_ALCOOL2);
                    defineSituacao();
                    return qtdCombustivel;
                } else if ((MAX_ALCOOL1 + MAX_ALCOOL2) >= auxAlcool) {
                    for (int j = auxAlcool; j > 0; j--) {
                        if (MAX_ALCOOL1 > 0) {
                            MAX_ALCOOL1--;
                        } else {
                            MAX_ALCOOL2--;
                        }
                    }
                    qtdCombustivel[2] = MAX_ALCOOL1;
                    qtdCombustivel[3] = MAX_ALCOOL2;
                    defineSituacao();
                    return qtdCombustivel;
                }

            }
        }

        return qtdCombustivel;
    }
}