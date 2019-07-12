package us.intando.uas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ednim;
    private EditText ednama;
    private EditText edalamat;

    private Button btntambah;
    private Button btntampil;
    private Spinner spinjk;

    String[] JK = {"Laki Laki", "Perempuan"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ednim = (EditText)findViewById(R.id.ed_nim);
        ednama = (EditText)findViewById(R.id.ed_nama);
        edalamat = (EditText)findViewById(R.id.ed_alamat);
        spinjk = findViewById(R.id.sp_jk);

        btntambah = (Button)findViewById(R.id.btnTambah);
        btntampil = (Button)findViewById(R.id.btnTampil);


        btntambah.setOnClickListener(this);
        btntampil.setOnClickListener(this);
        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, JK);
        spinjk.setAdapter(adapter);
    }

    private void addEmployee(){
        final String nim = ednim.getText().toString().trim();
        final String nama = ednama.getText().toString().trim();
        final String alamat = edalamat.getText().toString().trim();
        final String spin = spinjk.getSelectedItem().toString().trim();

        class AddEmployee extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Menambahkan...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_EMP_NIM, nim);
                params.put(Konfigurasi.KEY_EMP_NIM, nama);
                params.put(Konfigurasi.KEY_EMP_ALAMAT, alamat);
                params.put(Konfigurasi.KEY_EMP_JENISKELAMIN, spin);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Konfigurasi.URL_ADD, params);
                return res;
            }
        }
        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    private void addEmployee1(){
        final String nim = ednim.getText().toString().trim();
        final String nama = ednama.getText().toString().trim();
        final String alamat = edalamat.getText().toString().trim();
        final String spin = spinjk.getSelectedItem().toString().trim();

        class AddEmployee extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Menambahkan...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_EMP_NIM, nama);
                params.put(Konfigurasi.KEY_EMP_NAMA, nim);
                params.put(Konfigurasi.KEY_EMP_ALAMAT, alamat);
                params.put(Konfigurasi.KEY_EMP_JENISKELAMIN, spin);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Konfigurasi.URL_ADD, params);
                return res;
            }
        }
        AddEmployee ae = new AddEmployee();
        ae.execute();
    }


    public void onClick(View view) {
        if (view == btntambah){
            if (spinjk.getSelectedItem().toString().equals(JK[0])){
                addEmployee();
            }else if (spinjk.getSelectedItem().toString().equals(JK[1])){
                addEmployee1();
            }
        }
        if (view == btntampil){
            Intent a = new Intent(MainActivity.this, TampilData.class);
            startActivity(a);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
