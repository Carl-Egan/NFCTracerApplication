package com.example.nfccontacttracing.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nfccontacttracing.R;
import com.example.nfccontacttracing.parser.NdefMessageParser;
import com.example.nfccontacttracing.record.ParsedNdefRecord;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NFCReader extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private TextView text;
    BottomNavigationView navigation;
    FloatingActionButton floatingActionButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_reader);

        navigation = findViewById(R.id.bottomNavigationView);
        floatingActionButton = findViewById(R.id.fab);
        text = findViewById(R.id.text);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(nfcAdapter== null){
            Toast.makeText(this, "No NFC", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        pendingIntent = PendingIntent.getActivity(this, 0 ,
                new Intent(this , this.getClass())
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),0);

        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    Intent intent1 = new Intent(NFCReader.this , HomePageActivity.class);
                    startActivity(intent1);
                    break;


                case R.id.profile:
                    Intent intent2 = new Intent(NFCReader.this , ProfileActivity.class);
                    startActivity(intent2);
                    break;

                case R.id.news:
                    Intent intent3 = new Intent(NFCReader.this, NewsActivity.class);
                    startActivity(intent3);
                    break;
            }
            return false;
        });

        floatingActionButton.setOnClickListener(v -> {
           Toast.makeText(NFCReader.this,"Already Reading NFC",Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(nfcAdapter != null){
            if(!nfcAdapter.isEnabled())
                showWirelessSettings();

            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(nfcAdapter != null){
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        resolveIntent(intent);
    }

    private void resolveIntent(Intent intent){
        String action = intent.getAction();

        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)){
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;

            if(rawMsgs != null){
                msgs = new NdefMessage[rawMsgs.length];

                for(int i = 0; i< rawMsgs.length;i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }else{
                byte[] empty = new byte[0];
                byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
                Tag tag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                byte[] payload = dumpTagData(tag).getBytes();
                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id , payload);
                NdefMessage msg = new NdefMessage(new NdefRecord[] {record});
                msgs = new NdefMessage[] {msg};
            }
            displayMsgs(msgs);
        }
    }

    private void displayMsgs(NdefMessage[] msgs){
        if(msgs == null || msgs.length==0)
            return;

        StringBuilder builder = new StringBuilder();
        List<ParsedNdefRecord> records = NdefMessageParser.parse(msgs[0]);
        final int size = records.size();

        for(int i =0; i < size; i++){
            ParsedNdefRecord record = records.get(i);
            String str = record.str();
            builder.append(str).append("\n");
        }

        text.setText(builder.toString());
    }

    private void showWirelessSettings(){
        Toast.makeText(this, "You need to enable NFC", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        startActivity(intent);
    }

    private String dumpTagData(Tag tag){
        StringBuilder sb = new StringBuilder();
        byte[] id = tag.getId();

      
        sb.delete(sb.length() - 2, sb.length());

        for(String tech: tag.getTechList()){
            if(tech.equals(MifareClassic.class.getName())){
                sb.append('\n');
                String type = "Unknown";

                try{
                    MifareClassic mifareTag = MifareClassic.get(tag);

                    switch (mifareTag.getType()){
                        case MifareClassic.TYPE_CLASSIC:
                            type = "Classic";
                            break;
                        case MifareClassic.TYPE_PLUS:
                            type = "Plus";
                            break;
                        case MifareClassic.TYPE_PRO:
                            type = "Pro";
                            break;
                    }
                    sb.append("Mifare Classic type: ");
                    sb.append(type);
                    sb.append('\n');

                    sb.append("Mifare size: ");
                    sb.append(mifareTag.getSize()+ " bytes");
                    sb.append('\n');

                    sb.append("Mifare blocks: ");
                    sb.append(mifareTag.getSectorCount());
                    sb.append('\n');

                    sb.append("Mifare blocks");
                    sb.append(mifareTag.getBlockCount());
                }catch(Exception e){
                    sb.append("Mifare classic error: "+ e.getMessage());
                }
            }
            if(tech.equals(MifareUltralight.class.getName())){
                sb.append('\n');
                MifareUltralight mifareUlTag = MifareUltralight.get(tag);
                String type = "Unknown";
                switch (mifareUlTag.getType()){
                    case MifareUltralight.TYPE_ULTRALIGHT:
                        type = "Ultralight";
                        break;
                    case MifareUltralight.TYPE_ULTRALIGHT_C:
                        type = type = "Ultralight C";
                        break;
                }
                sb.append("Mifare Ultralight type: ");
                sb.append(type);
            }
        }
        return sb.toString();

    }



}
