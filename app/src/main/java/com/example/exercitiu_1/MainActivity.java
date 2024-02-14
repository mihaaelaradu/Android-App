package com.example.exercitiu_1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final  List<Aspirator> listaMea = new ArrayList<>();
    private AspiratorDB aspiratorDB; // = AspiratorDB.getInstance(getApplicationContext());
    private ListView listView; // = new ListView(this);
    private ArrayAdapter<Aspirator> adapter;

    private final static String adresaJson ="https://jsonkeeper.com/b/ALUX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

           aspiratorDB = AspiratorDB.getInstance(getApplicationContext());
           listView = new ListView(this);

        //punem in lista ce e in baza de date
        listaMea.addAll(aspiratorDB.getAutobuzDao().afiseazaTot());
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listaMea);
        listView.setAdapter(adapter);



        //adaugam un aspirator nou
        Button button = new Button(this);
        button.setText(R.string.deschide);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SecondActivity.class);
                i.putExtra("aspiratorNou", 1);
                startActivity(i);
            }
        });


        //STERGEM DIN LISTA la short click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aspirator aspirator = listaMea.get(position);
                listaMea.remove(aspirator);
                aspiratorDB.getAutobuzDao().deleteAspirator(aspirator);
                adapter.notifyDataSetChanged();
            }
        });

        //EDITAM UN OBIECT DIN LISTA la long click
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Aspirator aspirator = listaMea.get(position);
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("aspiratorEditare", aspirator);
                
                startActivity(intent);
                return true;
            }
        });


        //obtin obiectul creat
        Intent intent = getIntent();
        if (intent.hasExtra("obiectNou")) {
            Aspirator aspirator = (Aspirator) intent.getSerializableExtra("obiectNou");
            if (aspirator != null) {
                aspiratorDB.getAutobuzDao().insertAspirator(aspirator);
                listaMea.add(aspirator);
                adapter.notifyDataSetChanged();
            }
        }
        /////aici l-am adaugat direct din secondActivity, nu ii mai fac nimic aici
        else if (intent.hasExtra("obiectEditat")) {
            ///nu e nevoie sa ii fac update ptc i am facut update in baza de date
            Toast.makeText(getApplicationContext(), "Aspirator editat", Toast.LENGTH_LONG).show();
        }

       //adaug obiecte din din json
        incarcareProduseHttps();

        //o a treia activitate cu afisare dupa id
        EditText editText = new EditText(this);
        editText.setHint("Cauta dupa id");
        Button button3 = new Button(this);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ThirdActivity.class);
                int id = Integer.parseInt(editText.getText().toString());
                Aspirator aspirator = aspiratorDB.getAutobuzDao().selectDupaId(id);
                i.putExtra("id", aspirator);
                startActivity(i);
            }
        });



        linearLayout.addView(editText);
        linearLayout.addView(button3);
//        linearLayout.addView(listView);
        linearLayout.addView(button);
        linearLayout.addView(listView);
        horizontalScrollView.addView(linearLayout);
        setContentView(horizontalScrollView);
    }


    private void incarcareProduseHttps() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                HttpsManager httpsManager= new HttpsManager(adresaJson);   //super.run();
                String rezultat = httpsManager.process();

                new Handler(getMainLooper()).post(() -> {
                    if (!isFinishing()) {
                        getProduseFromJson(rezultat);
                    }
                });
            }
        };
        thread.start();
    }

    private void getProduseFromJson(String rezultat) {
        List<Aspirator> listaJson = new ArrayList<>();
        listaJson = AspiratorParser.fromJson(rezultat);
        //listaMea.addAll(listaJson);
        //adapter = (ArrayAdapter<Aspirator>) listView.getAdapter();
        //adapter.notifyDataSetChanged();
        aspiratorDB.getAutobuzDao().insertAspiratoare(listaJson);


    }

}