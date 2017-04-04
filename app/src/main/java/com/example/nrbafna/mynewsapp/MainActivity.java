package com.example.nrbafna.mynewsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    public static final String URL = "https://newsapi.org/v1/";

    // public static final String source_id = "source_id";

    //List view to show data
    private ListView listView;
    private com.example.nrbafna.mynewsapp.Source sources;
    //List of type source this list will store type Source which is our data model
    private List<Source_> sourceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv_sources);

        getSources();

        listView.setOnItemClickListener(this);
    }



    private void getSources(){
        final ProgressDialog loading = ProgressDialog.show(this,"Fetching Data","Please wait...",false,false);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiInterface apiinterface = retrofit.create(ApiInterface.class);

        Call<com.example.nrbafna.mynewsapp.Source> call = apiinterface.getSources();
        call.enqueue(new Callback<com.example.nrbafna.mynewsapp.Source>() {
            @Override
            public void onResponse(Call<com.example.nrbafna.mynewsapp.Source> call, Response<com.example.nrbafna.mynewsapp.Source> response) {

                if(response.isSuccessful()){
                    loading.dismiss();
                    sources = response.body();
                    showSources();
                }
            }

            @Override
            public void onFailure(Call<com.example.nrbafna.mynewsapp.Source> call, Throwable t) {
                Log.d("NAHI AAYA","NAHI AAYA");
                Toast.makeText(getBaseContext(),"Couldn't fetch data",Toast.LENGTH_SHORT).show();
            }
        });



    }


//        sourceinterface.getSources(new Callback<Source>() {
//            @Override
//            public void success(List<Source> sources, Response response) {
//
//
//
//            }

//            @Override
//            public void success(Source source, Response response) {
//                loading.dismiss();
//
//                sources = source;
//
//                showSources();
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.d("Error", String.valueOf(error));
//            }
//        });
//    }

    void showSources(){

        sourceList = sources.getSources();
        String[] sourceNameList = new String[sourceList.size()];

        for(int i=0;i<sourceList.size();i++){
            sourceNameList[i] = sourceList.get(i).getName();

            Log.d("Sources",sourceNameList[i]);
        }

        ArrayAdapter sourceAdapter = new ArrayAdapter<>(this, R.layout.sources_name, sourceNameList);

        listView.setAdapter(sourceAdapter);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this,NewsActivity.class);

        Source_ selectedSource = sourceList.get(position);

        intent.putExtra("source_id",selectedSource.getId());
        intent.putExtra("source_name",selectedSource.getName());
        intent.putExtra("source_url",selectedSource.getUrlsToLogos().getSmall());

        startActivity(intent);
    }
}
