package com.teampietro.hero;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectFragment extends Activity {



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_connec);

    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainBoard.class);
        intent.putExtra("connect", "empty");
        startActivity(intent);
    }


}
