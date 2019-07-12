package us.intando.uas;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TampilData extends AppCompatActivity {

    private ListView listView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_data);

        listView = (ListView)findViewById(R.id.listview);

        //listView.setOnItemClickListener(this);
        getJSON();
    }

    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Konfigurasi.TAG_ID);
                String nim = jo.getString(Konfigurasi.TAG_NIM);
                String nama = jo.getString(Konfigurasi.TAG_NAMA);
                String alamat = jo.getString(Konfigurasi.TAG_ALAMAT);
                String jenis_kelamin = jo.getString(Konfigurasi.TAG_JENISKELAMIN);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Konfigurasi.TAG_ID, id);
                employees.put(Konfigurasi.TAG_NAMA, nim);
                employees.put(Konfigurasi.TAG_NIM, nama);
                employees.put(Konfigurasi.TAG_ALAMAT, alamat);
                employees.put(Konfigurasi.TAG_JENISKELAMIN, jenis_kelamin);
                list.add(employees);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                TampilData.this, list , R.layout.list_item,
                new String[]{Konfigurasi.TAG_ID,Konfigurasi.TAG_NIM,Konfigurasi.TAG_NAMA,Konfigurasi.TAG_ALAMAT, Konfigurasi.TAG_JENISKELAMIN},
                new int[]{R.id.tvid, R.id.nim, R.id.nama, R.id.alamat, R.id.jk});
        listView.setAdapter(adapter);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(TampilData.this, "Mengambil Data","Mohon Tunggu...",false,false);
            }
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Konfigurasi.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
