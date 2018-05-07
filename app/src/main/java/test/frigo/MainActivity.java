package test.frigo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
//    public class Dog extends RealmObject {
//        public String name;
//        public int age;
//    }
    Button button ;
    Intent intent ;
    private ImageView imageView;
    private final int requestCode = 20;
    private CameraPreview mPreview;
    Fragment f;


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

        Camera cam = Camera.open();
        if(cam != null)
            Toast.makeText(this, "Opened", Toast.LENGTH_SHORT).show();

        mPreview = new CameraPreview(this, cam);

        FrameLayout preview = (FrameLayout) findViewById(R.id.camView);
        preview.addView(mPreview);


//        SurfaceView mview = new SurfaceView(getBaseContext());
//        cam.setPreviewDisplay(mview.getHolder());

//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//        Fragment fragment = new ScannerFragment();
//        fragmentTransaction.add(findViewById(R.id.fl), fraggy);
//        fragmentTransaction.commit();

//        FrameLayout fl = (FrameLayout)findViewById(R.id.fl);
//        f = new ScannerFragment();
//
//        FrameLayout preview = (FrameLayout) findViewById(R.id.camView);
//        preview.addView(mPreview);
//
//        ScannerFragment sf = new ScannerFragment();
//        EditText et = new EditText(this);
//        fl.addView(et,100,100);

//        fl.addView(new IntentIntegrator(fl));


//        new IntentIntegrator(fragment).initiateScan(); // `this` is the current Activity
//        new IntentIntegrator(this).setCaptureActivity(CameraPreview.class).initiateScan();
//        IntentIntegrator integrator = new IntentIntegrator(fraggy);

//        Intent intent = new Intent(fraggy, ContinuousCaptureActivity.class);
//        startActivity(intent);

//        SurfaceView sv = (SurfaceView)findViewById(R.id.camView);
//        sv.draw();
//        imageView.
//        SurfaceView mview = new SurfaceView(getBaseContext());
//        cam.setPreviewDisplay(mview.getHolder());

//        cam.setPreviewCallback(previewCallback);

//        try {
//            cam.setPreviewDisplay(sv.getHolder());
//            Toast.makeText(this, "Cam ok", Toast.LENGTH_LONG).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Cam FAIL", Toast.LENGTH_LONG).show();
//        }
//        cam.startPreview();

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        cam.startPreview();

//        cam.startPreview();

//        SurfaceTexture surfaceTexture = new SurfaceTexture(10);
//        cam.setPreviewTexture(surfaceTexture);


//        button = (Button)findViewById(R.id.button);
//        imageView = (ImageView)this.findViewById(R.id.camView);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//
//                startActivityForResult(intent, requestCode);
//
//            }
//        });

//        Button photoButton = (Button) this.findViewById(R.id.button1);
//        photoButton.setOnClickListener(new View.OnClickListener() {

//            @Override
//            public void onClick(View v) {
//                if (checkSelfPermission(Manifest.permission.CAMERA)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(new String[]{Manifest.permission.CAMERA},
//                            MY_CAMERA_PERMISSION_CODE);
//                } else {
//                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
//                }
//            }
//        });
    }

//    Camera.PreviewCallback previewCallback = new Camera.PreviewCallback(){
//        public void onPreviewFrame(byte[] b, Camera c) {
//            setContentView(R.layout.activity_main);
//            ImageView iv = (ImageView)findViewById(R.id.iv);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
//            iv.setImageBitmap(bitmap);
//            Toast.makeText(this, "callback", Toast.LENGTH_SHORT).show();
//        }
//    };

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
