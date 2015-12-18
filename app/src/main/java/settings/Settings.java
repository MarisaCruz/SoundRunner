package settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.goncalomatos.spotifysdktest.*;

/**
 * Created by Hugo on 11-12-2015.
 */
public class Settings extends PreferenceActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        EditText textName = (EditText) findViewById(R.id.textName);
        EditText textsurname = (EditText) findViewById(R.id.textsurname);
        RadioButton radioMale = (RadioButton) findViewById(R.id.radioMale);
        RadioButton radioFemale = (RadioButton) findViewById(R.id.radioFemale);
        EditText birthday = (EditText) findViewById(R.id.birthday);
        EditText textWeight = (EditText) findViewById(R.id.textWeight);
        EditText textHeight = (EditText) findViewById(R.id.textHeight);
        EditText textEmail = (EditText) findViewById(R.id.textEmail);
        EditText textPassword = (EditText) findViewById(R.id.textPassword);

        Intent i=new Intent(Settings.this,MainActivity.class);
        i.putExtra("NAME", textName.toString());
        i.putExtra("SURNAME", textsurname.toString());
        i.putExtra("MALE", radioMale.toString());
        i.putExtra("FEMALE", radioFemale.toString());
        i.putExtra("BIRTHDAY", birthday.toString());
        i.putExtra("WEIGHT", textWeight.toString());
        i.putExtra("HEIGHT", textHeight.toString());

        startActivity(i);

        back();
    }

    private void back() {


        Button actiondistribuir=(Button) findViewById(R.id.buttonSettingsBack);
        actiondistribuir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View View) {
                finish();
                startActivity(new Intent(Settings.this,MainActivity.class));
            }
        });
    }
}