package com.example.AppMovCRUD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText id_cingreso_cedula, id_cingreso_nombre, id_cingreso_carrera, id_cingreso_horas, id_cingreso_correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id_cingreso_cedula = findViewById(R.id.id_ingreso_cedula);
        id_cingreso_nombre = findViewById(R.id.id_ingreso_nombre);
        id_cingreso_carrera = findViewById(R.id.id_ingreso_carrera);
        id_cingreso_horas = findViewById(R.id.id_ingreso_horas);
        id_cingreso_correo = findViewById(R.id.id_ingreso_correo);
    }
    public void guardar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "bd_docentes", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cedula = id_cingreso_cedula.getText().toString();
        String nombre = id_cingreso_nombre.getText().toString();
        String carrera = id_cingreso_carrera.getText().toString();
        String horas = id_cingreso_horas.getText().toString();
        String correo = id_cingreso_correo.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("cedula", cedula);
        registro.put("nombre", nombre);
        registro.put("carrera", carrera);
        registro.put("horas", horas);
        registro.put("correo", correo);
        bd.insert("doc_datos", null, registro);
        bd.close();
        id_cingreso_cedula.setText("");
        id_cingreso_nombre.setText("");
        id_cingreso_carrera.setText("");
        id_cingreso_horas.setText("");
        id_cingreso_correo.setText("");
        Toast.makeText(this, "Se cargaron los datos del docente",Toast.LENGTH_SHORT).show();
    }
    public void buscar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "bd_docentes", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cedula = id_cingreso_cedula.getText().toString();
        Cursor fila = bd.rawQuery(
                "select nombre, carrera, horas, correo from doc_datos where cedula=" + cedula, null);
        if (fila.moveToFirst()) {
            id_cingreso_nombre.setText(fila.getString(0));
            id_cingreso_carrera.setText(fila.getString(1));
            id_cingreso_horas.setText(fila.getString(2));
            id_cingreso_correo.setText(fila.getString(3));
        } else
            Toast.makeText(this, "No existe un docente con dicha cedula",
                    Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void eliminar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "bd_docentes", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cedula= id_cingreso_cedula.getText().toString();
        int cant = bd.delete("doc_datos", "cedula=" + cedula, null);
        bd.close();
        id_cingreso_cedula.setText("");
        id_cingreso_nombre.setText("");
        id_cingreso_carrera.setText("");
        id_cingreso_horas.setText("");
        id_cingreso_correo.setText("");
        if (cant == 1)
            Toast.makeText(this, "Se borró el docente con dicha cedula",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No existe un docente con dicha cedula",
                    Toast.LENGTH_SHORT).show();
    }
    public void modificar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "bd_docentes", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cedula = id_cingreso_cedula.getText().toString();
        String nombre = id_cingreso_nombre.getText().toString();
        String carrera = id_cingreso_carrera.getText().toString();
        String horas = id_cingreso_horas.getText().toString();
        String correo = id_cingreso_correo.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("cedula", cedula);
        registro.put("nombre", nombre);
        registro.put("carrera", carrera);
        registro.put("horas", horas);
        registro.put("correo", correo);

        int cant = bd.update("doc_datos", registro, "cedula=" + cedula, null);
        bd.close();
        if (cant == 1)
            Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                    .show();
        else
            Toast.makeText(this, "no existe un docente con la cerdula ingresada",
                    Toast.LENGTH_SHORT).show();
    }
}


