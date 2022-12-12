package com.example.exfinal01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Admin extends AppCompatActivity {
    public static final String Error_Detectado = "Tag NFC no dectectado";
    public static final String Escritura_exitosa = "Texto escrito exitosamente!";
    public static final String Error_Escritura = "Error durante la escritura, intente nuevamente.";
Button btnstopreg, btnregistro;
EditText editxtuid, editxtnombre, editxtapellido, editxtcedula;
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writingTagFilters [];
    boolean writeMode;
    Tag tags;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btnregistro = findViewById(R.id.btnregistro);
        btnstopreg = findViewById(R.id.btnstopreg);
        editxtuid = findViewById(R.id.edittxtid);
        editxtnombre = findViewById(R.id.edittxtnombre);
        editxtapellido = findViewById(R.id.edittxtapellido);
        editxtcedula = findViewById(R.id.edittxtcedula);

        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editxtuid.getText().toString()) || TextUtils.isEmpty(editxtnombre.getText().toString()) || TextUtils.isEmpty(editxtapellido.getText().toString()) || TextUtils.isEmpty(editxtcedula.getText().toString())){
                    Toast.makeText(Admin.this, "dejo un espacio en blanco", Toast.LENGTH_LONG).show();
                }
                else{
                    try{
                        if(tags ==null){ // para detectar si lee
                            Toast.makeText(context,Error_Detectado, Toast.LENGTH_LONG ).show();
                        } else{
                            write("{id : "+editxtuid.getText().toString()+", nombre : "+editxtnombre.getText().toString()+", apellido : "+editxtapellido.getText().toString()+", cedula : "+editxtcedula.getText().toString()+",}",tags);
                            Toast.makeText(context, Escritura_exitosa, Toast.LENGTH_LONG ).show();
                        }
                    } catch (IOException e){
                        Toast.makeText(context, Error_Escritura, Toast.LENGTH_SHORT ).show();
                        e.printStackTrace();
                    } catch (FormatException e){
                        Toast.makeText(context, Error_Escritura, Toast.LENGTH_LONG ).show();
                        e.printStackTrace();
                    }

                }
            }
        });
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter ==null){
            Toast.makeText(this, "Este dispositivo no posee de lector NFC", Toast.LENGTH_SHORT).show();
            finish();
        }
        readFromIntent(getIntent());
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent( this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0   );
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writingTagFilters = new IntentFilter[] { tagDetected };
    }
    private void readFromIntent (Intent intent){
        context = this;
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)){
            Parcelable[] rawTxt = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] txt = null;
            if (rawTxt !=null){
                txt = new NdefMessage[rawTxt.length];
                for (int i=0; i< rawTxt.length; i++){
                    txt[1]= (NdefMessage) rawTxt[i];
                }
            }
            buildTagViews(txt);
        }
    }


    private void buildTagViews (NdefMessage[] txt){
        if (txt == null || txt.length == 0) return;

        String text ="";
        //     String tagID = new String(txt[0].getRecord()[0].getType());
        byte[] payload = txt[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16"; //Obtiene el enconding de texto
        int languageCodeLength = payload [0] & 0063;

        try{
            text = new String (payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding );
        } catch (UnsupportedEncodingException e){
            Log.e(  "No soporta Enconding", e.toString());
        }

    }


    private void write (String text, Tag tag) throws IOException, FormatException{
        NdefRecord[] records = { createRecord(text) };
        NdefMessage message = new NdefMessage(records);

        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        ndef.writeNdefMessage(message);
        ndef.close();
    }

    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang      = "esp";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int    langLength= langBytes.length;
        int    textLength= textBytes.length;
        byte[] payload   = new byte[1 + langLength + textLength];

        payload [0] = (byte) langLength;

        System.arraycopy(langBytes,0, payload,  1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);


        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);

        return recordNFC;
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        setIntent(intent);
        readFromIntent(intent);
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            tags = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
    }

    @Override
    public void onPause (){
        super.onPause();
        WriteModeOff();
    }

    @Override
    public void onResume(){
        super.onResume();
        WriteModeOn();
    }

    private void WriteModeOn(){
        writeMode = true;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writingTagFilters, null );
    }

    private void WriteModeOff(){
        writeMode = false;
        nfcAdapter.disableForegroundDispatch(this);

}