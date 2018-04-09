package test.frigo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import android.content.ContentValues;
import android.widget.EditText;
import android.net.Uri;

import android.database.Cursor;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toggle(View view) {
        Toast.makeText(this, "Okker", Toast.LENGTH_LONG).show();
   }

    public void onClickAddName(View view) {
        // Add a new student record
        ContentValues values = new ContentValues();
        values.put(DBProvider.NAME,
                ((EditText)findViewById(R.id.name)).getText().toString());

        values.put(DBProvider.GRADE,
                ((EditText)findViewById(R.id.grade)).getText().toString());

        Uri uri = getContentResolver().insert(
                DBProvider.CONTENT_URI, values);

        Toast.makeText(getBaseContext(),
                uri.toString(), Toast.LENGTH_LONG).show();
    }
    public void onClickRetrieveStudents(View view) {
        // Retrieve student records
        String URL = "content://test.frigo.DBProvider";

        Uri students = Uri.parse(URL);
        Cursor c = managedQuery(students, null, null, null, "name");

        if (c.moveToFirst()) {
            do{
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(DBProvider._ID)) +
                                ", " +  c.getString(c.getColumnIndex( DBProvider.NAME)) +
                                ", " + c.getString(c.getColumnIndex( DBProvider.GRADE)),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }
}
