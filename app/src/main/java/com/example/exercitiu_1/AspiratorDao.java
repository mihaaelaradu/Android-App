package com.example.exercitiu_1;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AspiratorDao {

    @Insert
     void insertAspirator(Aspirator aspirator);

    @Insert
    void insertAspiratoare(List<Aspirator> aspiratoare);

    @Delete
    void deleteAspirator(Aspirator aspirator);

    @Query("SELECT * FROM aspiratoare")
    List<Aspirator> afiseazaTot();

    //editare?
    @Update
    void updateAspirator(Aspirator aspirator);

    @Query("SELECT * FROM aspiratoare WHERE id = :idd")
    Aspirator selectDupaId (int idd);

}
