package test.frigo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import android.content.ContentValues;
import android.widget.EditText;
import android.net.Uri;

import android.database.Cursor;

import io.realm.Realm;
import io.realm.*;

import android.widget.ImageView;
import android.widget.Button;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.graphics.BitmapFactory;
import android.widget.FrameLayout;

import java.io.IOException;

//import java.util.

import test.frigo.CameraPreview;
import android.app.Fragment;


import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

//import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
//import com.journeyapps.barcodescanner.CameraPreview;


public class MainActivity extends AppCompatActivity {
    private static String LOG_TAG = "MainA";
    //    public class Dog extends RealmObject {
//        public String name;
//        public int age;
//    }
    Button button ;
    Intent intent ;
//    private ImageView imageView;
    private final int requestCode = 20;
//    private CameraPreview mPreview;
//    Fragment f;
//    Camera cam = null;

    private Camera mCamera;
    private CameraPreview mPreview;

//    private byte[] previewBuffer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);

        Realm realm = Realm.getDefaultInstance();

//        Dog dog = new Dog();
//        dog.name = "asd";
//        dog.age = 11;
//
//        RealmResults<Dog> puppies = realm.where(Dog.class).lessThan("age", 11).findAll();
//        puppies.size(); // => 0 because no dogs have been added to the Realm yet
//
//        realm.beginTransaction();
//        final Dog managedDog = realm.copyToRealm(dog); // Persist unmanaged objects
//        realm.commitTransaction();
//
//        puppies = realm.where(Dog.class).lessThan("age", 11).findAll();
//        puppies.size(); // => 0 because no dogs have been added to the Realm yet

//        SurfaceView sv = new SurfaceView(this);

        // Create an instance of Camera
        mCamera = CameraPreview.getCameraInstance();





        // print saved parameters
//        int prevWidth = mCamera.getParameters().getPreviewSize().width;
//        int prevHeight = mCamera.getParameters().getPreviewSize().height;
//        int picWidth = mCamera.getParameters().getPictureSize().width;
//        int picHeight = mCamera.getParameters().getPictureSize().height;
//
//        Log.d(MainActivity.LOG_TAG, "setupCamera(): settings applied:\n\t"
//                + "preview size: " + prevWidth + "x" + prevHeight + "\n\t"
//                + "picture size: " + picWidth + "x" + picHeight
//        );


        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camView);
        preview.addView(mPreview);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            String _code = data.getStringExtra("SCAN_RESULT");

            // do whatever you want
            Toast.makeText(this, "REQUEST_CODE = " + _code, Toast.LENGTH_LONG).show();

//            IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);


        }

//        if(this.requestCode == requestCode && resultCode == RESULT_OK){
//            Uri selectedImage = data.getData();
//            imageView.setImageURI(selectedImage);
//            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
//            this.imageView.setImageBitmap(bitmap);
//        }
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
