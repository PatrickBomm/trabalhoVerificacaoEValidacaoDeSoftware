package com.trabalho;

import static org.junit.Assert.*;

import org.junit.*;

import com.trabalho.CentroDistribuicao.TIPOPOSTO;

import junit.framework.AssertionFailedError;

public class CentroDistribuicaoTest {

    // Situacoes
    @Test
    public void testaSituacaoNormal() {
        CentroDistribuicao centro = new CentroDistribuicao(500, 10000, 1250, 1250);
        centro.defineSituacao();
        assertEquals(CentroDistribuicao.SITUACAO.NORMAL, centro.getSituacao());
    }

    @Test
    public void testaSituacaoSobraviso() {
        CentroDistribuicao centro = new CentroDistribuicao(500, 10000, 624, 624);
        centro.defineSituacao();
        assertEquals(CentroDistribuicao.SITUACAO.SOBRAVISO, centro.getSituacao());
    }

    @Test  
    public void testaSituacaoEmergencia(){
        CentroDistribuicao centro = new CentroDistribuicao(100, 1000, 250, 1250);
        centro.defineSituacao();
        assertEquals(CentroDistribuicao.SITUACAO.EMERGENCIA, centro.getSituacao());
    }


    // Adicionando combustível
    @Test
    public void testaAdicionaNormal() {
        CentroDistribuicao centro = new CentroDistribuicao(0, 0, 0, 0);
        centro.recebeAditivo(500);
        centro.recebeGasolina(10000);
        centro.recebeAlcool(2500);
        assertEquals(centro.gettAditivo(), 500);
        assertEquals(centro.gettGasolina(), 10000);
        assertEquals((centro.gettAlcool1() + centro.gettAlcool2()), 2500);
    }

    // Adicionando combustível illegalNumberException
    @Test(expected=IllegalNumberException.class)
    public void testaAdicionaErro(){
        CentroDistribuicao centro = new CentroDistribuicao(0, 0, 0, 0); 
        centro.recebeAditivo(0);
        centro.recebeGasolina(0); 
        centro.recebeAlcool(0);
    }
    
    // Retirando Combustivel
    // NORMAL
    @Test
    public void testRetirarCombustivel(){
        CentroDistribuicao centro = new CentroDistribuicao(500, 10000, 1250, 1250); 
        int[] aux = {450, 9300, 1125, 1125};
        int[] resta = centro.encomendaCombustivel(1000, TIPOPOSTO.COMUM);

        for(int i = 0; i < aux.length; i++){
            assertEquals(aux[i], resta[i]);
        }
    }

    @Test
    public void testRetirarCombustivel2(){
        CentroDistribuicao centro = new CentroDistribuicao(500, 10000, 1250, 1250);
        int[] aux = {-7, 0, 0, 0};
        int[] resta = centro.encomendaCombustivel(-12, TIPOPOSTO.COMUM);
        for(int i = 0; i < aux.length; i++){
            assertEquals(aux[i], resta[i]);
        }
    }

    @Test
    public void testRetirarCombustivel3(){
        CentroDistribuicao centro = new CentroDistribuicao(100, 1000, 250, 250);
        int[] aux = {-14, 0, 0, 0};
        centro.defineSituacao();
        int[] resta = centro.encomendaCombustivel(1000, TIPOPOSTO.COMUM);
        assertEquals(centro.getSituacao(), CentroDistribuicao.SITUACAO.EMERGENCIA);
        for(int i = 0; i < aux.length; i++){
            assertEquals(aux[i], resta[i]);
        }
    }

    @Test
    public void testRetirarCombustivel4(){
        CentroDistribuicao centro = new CentroDistribuicao(235, 4430, 613, 613);
        centro.defineSituacao();
        int[] aux = {185, 3730, 488, 488};
        int[] resta = centro.encomendaCombustivel(2000, TIPOPOSTO.COMUM);
        for(int i = 0; i < aux.length; i++){
            assertEquals(aux[i], resta[i]);
        }
    }

    @Test
    public void testRetirarCombustivel5(){
        CentroDistribuicao centro = new CentroDistribuicao(300, 10000, 1250, 1250);
        centro.defineSituacao();
        int[] aux = {-21, 0, 0, 0};
        int[] resta = centro.encomendaCombustivel(10000, TIPOPOSTO.COMUM);
        for(int i = 0; i < aux.length; i++){
            assertEquals(aux[i], resta[i]);
        }
    }

    // ESTRATEGICO
    @Test
    public void testRetirarEstratico(){
        CentroDistribuicao centro = new CentroDistribuicao(500, 10000, 1250, 1250);
        int[] aux = {450, 9300, 1125, 1125};
        int[] resta = centro.encomendaCombustivel(1000, TIPOPOSTO.ESTRATEGICO);
        for(int i = 0; i < aux.length; i++){
            assertEquals(aux[i], resta[i]);
        }
    }

    @Test
    public void testRetirarEstratico2(){
        CentroDistribuicao centro = new CentroDistribuicao(100, 1000, 250, 250);
        int[] aux = {75, 650, 188, 188};
        centro.defineSituacao();
        assertEquals(centro.getSituacao(), CentroDistribuicao.SITUACAO.EMERGENCIA);
        int[] resta = centro.encomendaCombustivel(1000, TIPOPOSTO.ESTRATEGICO);
        for(int i = 0; i < aux.length; i++){
            assertEquals(aux[i], resta[i]);
        }
    }

}
