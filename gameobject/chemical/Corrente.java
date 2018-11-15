/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.chemical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.chemeng.Calculator;
import com.chemeng.GameTime;
import java.util.Arrays;

/**
 *
 * @author Marcio
 */
public class Corrente {
    
    private static final String TAG = Corrente.class.getName();
    
    public enum Estado {
        
        LIQUIDO_SUBRESFRIADO,
        LIQUIDO_SATURADO,
        ELV,
        VAPOR_SATURADO,
        VAPOR_SUPERAQUECIDO
        
    }
    
    /*
    As moléculas deverão fazer parte do Level, visto que o Array não vai mudar
    
    Por ser static, PRECISA ser resetada a cada level!
    usar uma função reset() para garantir;
    
    */
    
    private static Array<Molecula> moleculas;
    private static int n; //número de componentes
    
    //x, y, z, w em percentual MOLAR
    public double[] x, y, w, z;    
    private double flow; //vazão molar total
    
    private double flashBeta = 0;
    
    public float Temperature;
    public double Pressure;
    
    public boolean free = true;
    
    //Inicializa a classe; deve ser puxada ao início de cada Level;
    public static void init(Array<Molecula> mol){
        moleculas = mol;
        n = moleculas.size;
    }
    
    //Reseta os campos estáticos da classe;
    public static void reset(){
        moleculas = null;
        n = 0;
    }
    
    private void initArrays(){
        this.x = new double[n];
        this.y = new double[n];
        this.w = new double[n];
        this.z = new double[n];
    }
    
    public Corrente(){
        
        /*
        ------------------------------
        
                  EM TESTE 
        
        ------------------------------
        */
        n = 4;
        flow = 0;
        initArrays();
    }
    
    public Corrente(double[] zComposition, float molarFlow, float T, double P){
        
        if(moleculas == null){
            System.out.println("Composição das correntes não iniciada; utilizar"
                    + "método init()");
        } else if(zComposition.length != n){
            System.out.println("Composição de entrada incompatível com composição no objeto"
                    + " " + this.toString());
        }
        
        initArrays();
        
        this.Temperature = T;
        this.Pressure = P;
        this.flow = molarFlow;
        
        for(int i = 0; i < n; i++){
            z[i] = zComposition[i];
        }

        
    }
    
    public void setCorrente(double[] zComposition, float molarFlow, float T, double P){
        if(moleculas == null){
            System.out.println("Composição das correntes não inicializada; utilizar"
                    + "método init()");
        } else if(zComposition.length != n){
            System.out.println("Composição de entrada incompatível com composição no objeto"
                    + " " + this.toString());
        }        
        this.Temperature = T;
        this.Pressure = P;
        this.flow = molarFlow;
        
        //seta/copia a composição
        for(int i = 0; i < n; i++){
            z[i] = zComposition[i];
        }
        /*
        ***********************************************************
        Define o ESTADO via [P,T] e calcula as correntes {x} e {y}
        ***********************************************************
        */
        /*
        ***********************************
        !!!!!!!!! APENAS PARA TESTES!!!!!!!
        ***********************************
        */
        for(int i = 0; i < n; i++){ //loop que considera toda a composição líquida
            x[i] = z[i]; 
            y[i] = 0; 
        } 
        
        /*
        ***********************************
        !!!!!!!!! FIM DOS TESTES!!!!!!!
        ***********************************
        */
        
    }

    public void setData(Corrente c){
        for(int i = 0; i < n; i++){
            x[i] = c.x[i];
            y[i] = c.y[i];
            z[i] = c.z[i];            
        }
        this.Temperature = c.Temperature;
        this.Pressure = c.Pressure;
        this.flow = c.flow;
    }

    public void setTP(float T, double P){
        this.Temperature = T;
        this.Pressure = P;
    }
    
    public void setZcomposition(double[] comp){
        for(int i = 0; i < z.length; i++){
            z[i] = comp[i];
        }
        //System.arraycopy(comp, 0, z, 0, z.length);
    }
    public void setXcomposition(double[] comp){
        for(int i = 0; i < x.length; i++){
            x[i] = comp[i];
        }
        //System.arraycopy(comp, 0, z, 0, z.length);
    }
    public void setYcomposition(double[] comp){
        for(int i = 0; i < y.length; i++){
            y[i] = comp[i];
        }
        //System.arraycopy(comp, 0, z, 0, z.length);
    }
    
    public float getTemperature() {
        return Temperature;
    }

    public void setTemperature(float Temperature) {
        this.Temperature = Temperature;
    }

    public double getPressure() {
        return Pressure;
    }

    public void setPressure(double Pressure) {
        this.Pressure = Pressure;
    }
    
    public double[] getFlowArray(){
        double[] d;
        d = new double[n];
        
        for(int i = 0; i < n; i++){
            d[i] = z[i]*getFlow();
        }        
        return d;
    }
    
    //fluxo molar --- 
    public double getFlow(){ //mol/s
        return flow;
        //return flow*GameTime.getMPF()*(1d/60d);
    }
    
    public void setFlow(double flow){
        this.flow = flow;
    }
    
    //fluxo volumétrico ----- FAZER AS CONTAS !!!!! (precisa densidade)
    public double getVolumetricFlow(){ //m3/s
        double vFlow = 0;
        return vFlow;
    }
    
    //fluxo mássico ---- FAZER AS CONTAS !!!!! (usar a massa molar)    
    public double getMassicFlow(){ //kg/s
        double massFlow = 0;
        return massFlow;
    }
    
    public double getCp(){
        
        double k = 0;        
        for(int i = 0; i < moleculas.size; i++){
            k += z[i]*moleculas.get(i).getCp();
        }
        
        return k;
    }

    public static Array<Molecula> getMoleculas() {
        return moleculas;
    }    

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }

    public double[] getW() {
        return w;
    }    
    
    public double[] getZ() {
        return z;
    }
        
    @Override
    public String toString(){
                 
        System.out.println("{z}: "+Arrays.toString(z));
        System.out.println("{x}: "+Arrays.toString(x));
        System.out.println("{w}: "+Arrays.toString(w));
        System.out.println("{y}: "+Arrays.toString(y));
        
        
        System.out.printf("Flow: %.2f mol/h%n", getFlow());
        System.out.printf("T(K): %.2f %n", Temperature);
        System.out.printf("P(Pa): %.4f %n", Pressure);        
        
        String str = "";
        
        return str;
    }
    
    public Estado getEstado(){
        Estado state = null;
        
        double Pbol = Calculator.BOL_P(moleculas, z, Temperature);
        double Porv = Calculator.ORV_P(moleculas, z, Temperature);
        
//        String str;
//        
//        str = String.format("%.2f", Pbol/1000);        
//        System.out.println("Pressão de Bolha (kPa): "+str);
//        str = String.format("%.2f", Porv/1000);        
//        System.out.println("Pressão de Orvalho (kPa): "+str);
//        str = String.format("%.2f", Pressure/1000);        
//        System.out.println("Pressão do Sistema (kPa): "+str);
//        System.out.println("---------------");
        
        if(Pressure < Porv){
            state = Estado.VAPOR_SUPERAQUECIDO;
            //y = z;
        } else if(Pressure > Pbol){
            state = Estado.LIQUIDO_SUBRESFRIADO;
            //x = z;
        } else {
            state = Estado.ELV;
            //calculaFlash();
        }
        
        return state;
    }
    
    public void calculaFlash(){
        double[] K = new double[moleculas.size];
        
        for(int i = 0; i < moleculas.size; i++){
            //Lei de Raoult
            K[i] = moleculas.get(i).getPsat(Temperature)/Pressure;
        }
        
//        System.out.println("**************************************************");
//        System.out.println("Cálculo do Flash: ");
//        
//        System.out.println("K[]: "+Arrays.toString(K));
//        System.out.println("z[]: "+Arrays.toString(z));
//        System.out.println("x[]: "+Arrays.toString(x));
//        System.out.println("y[]: "+Arrays.toString(y));
        
        //Método de Newton
        double[] V = new double[50];
        V[0] = 0.9999f;
        double tol = 0.000001d;
        int i = 0;
        
        try {
            
            do {
                i++;
                V[i] = V[i-1] - F(z, K, V[i-1])/dF(z, K, V[i-1]);    
                if(i > 50) throw new ArrayIndexOutOfBoundsException();    
            } while(Math.abs(V[i] - V[i-1]) > tol);            
            
        } catch (Exception e){
            Gdx.app.log(TAG, e.toString());
            
        }
        
        //calculando composição do gás
        for(int j = 0; j < moleculas.size; j++){
            y[j] = z[j]*K[j]/(1+V[i]*(K[j]-1));
        }
        
        //calculando composição do líquido
        for(int j = 0; j < moleculas.size; j++){
            x[j] = y[j]/K[j];
        }
        
        flashBeta = V[i];
        
//        System.out.println("Beta: "+flashBeta);
//        
//        System.out.println("Fim do Cálculo do Flash: ");
//        System.out.println("**************************************************");
        
    }
    
    //Equação de Flash
    private double F(double z[], double K[], double V){
        
        double F = 0;
        
        for(int i=0; i <z.length; i++){
            F += z[i]*(K[i]-1)/(1+V*(K[i]-1));
        }
        return F;
    }
    
    //Derivada da Equação de Flash (em relação a V)
    private double dF(double z[], double K[], double V){
        
        double dF = 0;
        
        for(int i=0; i <z.length; i++){
            dF += z[i]*(K[i]-1)*(K[i]-1)/((1+V*(K[i]-1))*(1+V*(K[i]-1)));
        }
        return -dF;
    }

    public double getFlashBeta() {
        return flashBeta;
    }    
    
}
