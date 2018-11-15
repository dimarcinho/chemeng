/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.testes;

import com.chemeng.gameobjects.chemical.Corrente;
import java.util.Arrays;

/**
 *
 * @author Marcio
 */
public class ConnectionTesterMain {
 
    public Tester[] world;
    
    public ConnectionTesterMain(){
        
        cenarioIV();
        
        /*
        double z[];
        z = new double[3];
        
        z[0] = 0.20;
        z[1] = 0.50;
        z[2] = 0.30;
        
        Corrente c1 = new Corrente(z, 100f, 300.2f, 1.5f);
        Corrente c2 = new Corrente();        
        Corrente c3 = new Corrente();
        
        c2.setData(c1);
        c3.setData(c1);
        
        System.out.println("Corrente c1: "+Arrays.toString(c1.z));
        System.out.println("Corrente c2: "+Arrays.toString(c2.z));
        System.out.println("Corrente c3: "+Arrays.toString(c3.z));
        System.out.println("Alterando c1...");
        c1.z[0] = 0.33;
        c1.z[1] = 0.33;
        c1.z[2] = 0.33;
        System.out.println("Corrente c1 alterada;");
        System.out.println("Corrente c1: "+Arrays.toString(c1.z));
        System.out.println("Corrente c2: "+Arrays.toString(c2.z));
        System.out.println("Corrente c3: "+Arrays.toString(c3.z));
        System.out.println("Transferindo os dados para c2 e c3...");
        c2.setData(c1);
        c3.setData(c1);
        System.out.println("Transferência concluída;");
        System.out.println("Corrente c1: "+Arrays.toString(c1.z));
        System.out.println("Corrente c2: "+Arrays.toString(c2.z));
        System.out.println("Corrente c3: "+Arrays.toString(c3.z));
        */
        
    }
    
    public void cenarioIV(){
        world = new Tester[4];
        
        world[0] = new TesterSource();        
        world[1] = new TesterSplitter(0.600);
        world[2] = new TesterSplitter(0.800);
        world[3] = new TesterMixer();
        
        for(Tester obj : world){
            obj.getPhysics().init();
        }
        
        TestConnectionList.Link(world[0].getPhysics().getSaida(0),
                                    world[1].getPhysics().getEntrada(0));
        
        TestConnectionList.Link(world[1].getPhysics().getSaida(0),
                                    world[2].getPhysics().getEntrada(0));
        TestConnectionList.Link(world[1].getPhysics().getSaida(1),
                                    world[3].getPhysics().getEntrada(1));
        
        TestConnectionList.Link(world[2].getPhysics().getSaida(1),
                                    world[3].getPhysics().getEntrada(0));
        
    }
    
    public void cenarioV(){
        world = new Tester[3];        
        
        world[0] = new TesterSource();
        world[1] = new TesterMixer();
        world[2] = new TesterSplitter(0.600);
        
        for(Tester obj : world){
            obj.getPhysics().init();
        }
        
        TestConnectionList.Link(world[0].getPhysics().getSaida(0),
                                    world[1].getPhysics().getEntrada(0));
        TestConnectionList.Link(world[1].getPhysics().getSaida(0),
                                    world[2].getPhysics().getEntrada(0));
        TestConnectionList.Link(world[2].getPhysics().getSaida(1),
                                    world[1].getPhysics().getEntrada(1));
    }
    
    
    
    public void loop(){
        for(Tester t: world){
            t.update();
            t.getPhysics().getConexoes().print();
        }
    }
    
    public static void main(String[] args){
        ConnectionTesterMain app = new ConnectionTesterMain();
        
        int i = 0;
        while(i < 60){
            app.loop();
            i++;
        }
    }
    
}
