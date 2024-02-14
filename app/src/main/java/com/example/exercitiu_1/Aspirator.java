package com.example.exercitiu_1;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "aspiratoare")
public class Aspirator implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String model;
    private float pret;

    public Aspirator(String model, float pret) {
        this.model = model;
        this.pret = pret;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    @Override
    public String toString() {
        return "Aspirator{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", pret=" + pret +
                '}';
    }
}
