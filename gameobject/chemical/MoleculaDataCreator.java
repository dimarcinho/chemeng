/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.chemical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author marci
 */
public class MoleculaDataCreator {
    
    private static final String TAG = MoleculaDataCreator.class.getName();
    
    private JSONArray jarray;
    
    public MoleculaDataCreator(){

        //inicializa ol objeto JSONArray
        jarray = new JSONArray();

        //Cria os dados via JSONObject e armanazena no JSONArray
        createData("Etanol", "C2H5OH",46, 2427, true);
        createData("Agua", "H2O",18, 4184, true);
        createData("Acetona", "CH3(CO)CH3",58, 2152, true);

        FileWriter writeFile;
        try{
            writeFile = new FileWriter("data/saida.json");
            //Escreve no arquivo conteudo do Objeto JSON
            //writeFile.write(jsonObject.toString());
            writeFile.write(jarray.toString());
            writeFile.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        //Imprimne na Tela o Objeto JSONArray para vizualização
        System.out.println(jarray);

    }   

    private void createData(String nome, String formula,
                            float MM, float Cp, boolean polar){
        JSONObject jsonObject = new JSONObject();

        HashMap map = new HashMap<String, Object>();
        //map.put("nome", nome);
        map.put("formula", formula);
        map.put("MM", MM);
        map.put("Cp", Cp);
        map.put("polar", polar);

        jsonObject.put(nome, map);            
        jarray.put(jsonObject);            


    }
        
}
