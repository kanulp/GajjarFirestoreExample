package kanu_lp.com.gajjarfirestoreexample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button submit,fetch;
    EditText name,fav;
    RecyclerView recyclerView;
    ArrayList<FavModel> favModelArrayList;
    private RecyclerView.LayoutManager layoutManager;
    public static String  TAG = "MainActivity";
    private FirebaseFirestore mFirestore;

    @Override
    protected void onStart() {
        super.onStart();
        mFirestore.collection("sampleUsers").addSnapshotListener(this,new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(documentSnapshots.isEmpty()){
                    Log.w(TAG,"Exec "+e);
                }else{
                    List<FavModel> favModel  = documentSnapshots.toObjects(FavModel.class);
                    CustomAdapter customAdapter = new CustomAdapter(favModel);
                    recyclerView.setAdapter(customAdapter);

                    Log.w(TAG,"Size onstart "+favModel.size());
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirestore = FirebaseFirestore.getInstance();

        initviews();
        //fetchdata();
        //final CollectionReference samplesref = mFirestore.collection("sampleData");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertdata();
            }
        });
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchdata();
            }
        });
    }

    private void fetchdata() {
        //DocumentReference userRef = mFirestore.collection("cities").document("SF");

        mFirestore.collection("sampleUsers")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {

                        List<FavModel> favModel  = documentSnapshots.toObjects(FavModel.class);
                        Log.w(TAG,"fetch successful : "+favModel.size());
                        for(int i=0;i<favModel.size();i++){
                            Log.w(TAG,"data"+favModel.get(i).getUser()+" "+favModel.get(i).getFavsinger());
                        }
                        CustomAdapter customAdapter = new CustomAdapter(favModel);
                        recyclerView.setAdapter(customAdapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                    Log.w(TAG,"failed to fetch");
            }
        });

    }


    private void insertdata() {

        String sname = name.getText().toString();
        String sfav = fav.getText().toString();
        if(sname.isEmpty()|| sfav.isEmpty()){
            Toast.makeText(MainActivity.this, "Both fields Required", Toast.LENGTH_SHORT).show();
        }else {
            FavModel favModel = new FavModel(sname,sfav);
            mFirestore.collection("sampleUsers").document(sname)
                    .set(favModel, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.w(TAG,"submitted");
                           // fetchdata();
                            name.setText("");
                            fav.setText("");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG,"not submitted");

                }
            });

        }
    }


    private void initviews() {

        fav = (EditText) findViewById(R.id.fav);
        name = (EditText)findViewById(R.id.name);
        submit = (Button)findViewById(R.id.submit);
        fetch = (Button)findViewById(R.id.fetch);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
