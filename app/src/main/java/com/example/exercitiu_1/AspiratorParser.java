package com.example.exercitiu_1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AspiratorParser {

    public static List<Aspirator> fromJson(String rezultat){
        try {
            JSONArray array = new JSONArray(rezultat);
            return citimAspiratoare(array);
        } catch (JSONException e) {
            throw new RuntimeException();
            //e.printStackTrace();
        }
        //return new ArrayList<>();
    }
    private static List<Aspirator> citimAspiratoare(JSONArray array) throws JSONException {
        List<Aspirator> lista = new ArrayList<>();
        for (int i=0; i< array.length(); i++)
            lista.add(citimAspirator(array.getJSONObject(i)));
        return lista;
    }
    private static Aspirator citimAspirator(JSONObject object) throws JSONException {
        String model = object.getString("model");
        Float pret = (float) object.getDouble("pret");

        return new Aspirator(model, pret);
    }
}
