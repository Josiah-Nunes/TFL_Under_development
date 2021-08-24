package org.tensorflow.lite.examples.detection;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Locale;

public class Needs extends AppCompatActivity
{
    //From Main for the voice assistant...
    EditText editText;
    ImageView imageView;

    public static final Integer RecordAudioRequestCode = 1;
    private TextToSpeech textToSpeech;

    private SpeechRecognizer speechRecognizer;
    AlertDialog.Builder alertSpeechDialog;
    AlertDialog alertDialog;

    final Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

    private int nam=0,nam2=0,phno=0,phno2=0,eme1=0,eme12=0,eme2=0,eme22=0;

    //From Needs
    Button submit;
    EditText etName, etContact, etEmer1,etEmer2;
    String name, phone, emer1,emer2;
    public final static String NAME = "name";
    public final static String PHno = "phno";
    public static final String Emer1 = "birth";
    public static final String Emer2 = "emer";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_needs);

        //From Main
        editText = findViewById(R.id.editText_from_needs);
        imageView = findViewById(R.id.imageView_from_needs);

        //Grabbing References
        submit = (Button) findViewById(R.id.buttonSubmit);
        etName = (EditText) findViewById(R.id.editTextName);
        etContact = (EditText) findViewById(R.id.editTextContact);
        etEmer1 = (EditText) findViewById(R.id.editTextEmer1);
        etEmer2 = (EditText) findViewById(R.id.editTextEmer2);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Initializing variables
                name = etName.getText().toString();
                phone = etContact.getText().toString();
                emer1 = etEmer1.getText().toString();
                emer2 = etEmer2.getText().toString();
                //Saves to database
                try {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString(NAME,name);
                    editor.putString(PHno,phone);
                    editor.putString(Emer1,emer1);
                    editor.putString(Emer2,emer2);
                    editor.commit();
                    /*
                    AayaDatabase enterDatabase = new AayaDatabase(Needs.this);
                    enterDatabase.open();
                    enterDatabase.createEntry(name, address, phone, birth, vImp);
                    data = enterDatabase.getData();
                    enterDatabase.close();
                    */
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    //Toast.makeText(Needs.this, data, Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(Needs.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



        if (ContextCompat.checkSelfPermission(Needs.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                //textToSpeech.speak("please tell me your name", TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });


        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
            }

            @Override
            public void onBeginningOfSpeech() {


                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(Needs.this).inflate(R.layout.alertcustom, viewGroup, false);

                alertSpeechDialog = new AlertDialog.Builder(Needs.this);
                alertSpeechDialog.setMessage("Listening.....");
                alertSpeechDialog.setView(dialogView);
                alertDialog = alertSpeechDialog.create();
                alertDialog.show();
            }

            @Override
            public void onRmsChanged(float v) {
            }

            @Override
            public void onBufferReceived(byte[] bytes) {
            }

            @Override
            public void onEndOfSpeech() {
            }

            @Override
            public void onError(int i) {
            }

            @Override
            public void onResults(Bundle bundle) {
                imageView.setImageResource(R.drawable.ic_baseline_mic_24);
                ArrayList<String> arrayList = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                editText.setText(arrayList.get(0));
                alertDialog.dismiss();

                String string = "";
                editText.setText("");
                if (arrayList != null) {
                    string = arrayList.get(0);
                    editText.setText(string);

                    if(string.equals("yes"))
                    {
                        if(nam2==0) {
                            nam2=1;
                            phone_number();
                        }
                        else if(phno2==0) {
                            phno2=1;
                            emergency_number1();
                        }
                        else if(eme12==0) {
                            eme12=1;
                            emergency_number2();
                        }else if(eme22==0) {
                            eme22=1;
                            done();
                        }
                    }
                    else if(string.equals("no"))
                    {
                        if(nam2==0)
                        {
                            nam=0;
                            name();
                        }
                        else if(phno2==0) {
                            phno=0;
                            phone_number();
                        }
                        else if(eme12==0) {
                            eme1=0;
                            emergency_number1();
                        }else if(eme22==0) {
                            eme2=0;
                            emergency_number2();
                        }
                    }


                    else if (nam == 0)
                    {
                        etName.setText(string);
                        String ai=etName.getText().toString();
                        //dont change
                        nam=1;

                        new Thread(new Runnable() {
                            public void run() {
                                // a potentially time consuming task
                                editText.post(new Runnable() {
                                    public void run() {
                                        textToSpeech.speak("is your name "+ai, TextToSpeech.QUEUE_FLUSH, null, null);
                                        try {
                                            Thread.sleep(1200);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        speechRecognizer.startListening(speechIntent);
                                    }
                                });
                            }
                        }).start();




                    }

                    else if(phno==0)
                    {
                        etContact.setText(string);
                        String bi=etContact.getText().toString();

                        phno=1;
                        textToSpeech.speak("is your contact number ", TextToSpeech.QUEUE_FLUSH, null, null);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        new Thread(new Runnable() {
                            public void run() {
                                // a potentially time consuming task
                                editText.post(new Runnable() {
                                    public void run() {
                                        for(int i = 0 ; i < bi.length(); i++)
                                        {
                            /* refer Speech API , Don't use QUEUE_FLUSH as it results in flushing
                            some characters in this case */

                                            textToSpeech.speak(Character.toString(bi.charAt(i)), TextToSpeech.QUEUE_FLUSH, null, null);
                                            try {
                                                Thread.sleep(700);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                        speechRecognizer.startListening(speechIntent);
                                    }
                                });
                            }
                        }).start();
                    }

                    else if(eme1==0)
                    {
                        etEmer1.setText(string);
                        String ci=etEmer1.getText().toString();
                        eme1=1;
                        textToSpeech.speak("is your emergency number ", TextToSpeech.QUEUE_FLUSH, null, null);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        new Thread(new Runnable() {
                            public void run() {
                                // a potentially time consuming task
                                editText.post(new Runnable() {
                                    public void run() {
                                        for(int i = 0 ; i < ci.length(); i++)
                                        {
                            /* refer Speech API , Don't use QUEUE_FLUSH as it results in flushing
                            some characters in this case */

                                            textToSpeech.speak(Character.toString(ci.charAt(i)), TextToSpeech.QUEUE_FLUSH, null, null);
                                            try {
                                                Thread.sleep(700);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                        speechRecognizer.startListening(speechIntent);
                                    }
                                });
                            }
                        }).start();
                    }

                    else if(eme2==0)
                    {
                        etEmer2.setText(string);
                        String di=etEmer2.getText().toString();
                        eme1=1;
                        textToSpeech.speak("is your second emergency number ", TextToSpeech.QUEUE_FLUSH, null, null);
                        try {
                            Thread.sleep(2500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        new Thread(new Runnable() {
                            public void run() {
                                // a potentially time consuming task
                                editText.post(new Runnable() {
                                    public void run() {
                                        for(int i = 0 ; i < di.length(); i++)
                                        {
                            /* refer Speech API , Don't use QUEUE_FLUSH as it results in flushing
                            some characters in this case */

                                            textToSpeech.speak(Character.toString(di.charAt(i)), TextToSpeech.QUEUE_FLUSH, null, null);
                                            try {
                                                Thread.sleep(700);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                        speechRecognizer.startListening(speechIntent);
                                    }
                                });
                            }
                        }).start();

                    }
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {
            }

            @Override
            public void onEvent(int i, Bundle bundle) {
            }
        });

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        speechRecognizer.startListening(speechIntent);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(nam2==0)
                {
                    nam=0;
                    name();
                }
                else if(phno2==0) {
                    phno=0;
                    phone_number();
                }
                else if(eme12==0) {
                    eme1=0;
                    emergency_number1();
                }else if(eme22==0) {
                    eme2=0;
                    emergency_number2();
                }

                if (motionEvent.getAction() == motionEvent.ACTION_UP) {
                    speechRecognizer.stopListening();
                }
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    imageView.setImageResource(R.drawable.ic_baseline_mic_24);
                    speechRecognizer.startListening(speechIntent);
                }
                return false;
            }
        });
    }



    public void name()
    {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textToSpeech.speak("Tell me your name", TextToSpeech.QUEUE_FLUSH, null, null);
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        speechRecognizer.startListening(speechIntent);

    }
    public void phone_number()
    {

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textToSpeech.speak("Tell me your phone number", TextToSpeech.QUEUE_FLUSH, null, null);
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        speechRecognizer.startListening(speechIntent);

    }

    public void emergency_number1()
    {

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textToSpeech.speak("Tell me your emergency contact", TextToSpeech.QUEUE_FLUSH, null, null);
        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        speechRecognizer.startListening(speechIntent);

    }

    public void emergency_number2()
    {

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textToSpeech.speak("Tell me another emergency contact", TextToSpeech.QUEUE_FLUSH, null, null);
        try {
            Thread.sleep(2250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        speechRecognizer.startListening(speechIntent);

    }

    private void done()
    {
        //Initializing variables
        name = etName.getText().toString();
        phone = etContact.getText().toString();
        emer1 = etEmer1.getText().toString();
        emer2 = etEmer2.getText().toString();
        //Saves to database
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putString(NAME,name);
            editor.putString(PHno,phone);
            editor.putString(Emer1,emer1);
            editor.putString(Emer2,emer2);
            editor.commit();
                    /*
                    AayaDatabase enterDatabase = new AayaDatabase(Needs.this);
                    enterDatabase.open();
                    enterDatabase.createEntry(name, address, phone, birth, vImp);
                    data = enterDatabase.getData();
                    enterDatabase.close();
                    */
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //Toast.makeText(Needs.this, data, Toast.LENGTH_LONG).show();
        }



        textToSpeech.speak("The registration form is now complete. Since it is your first time, say instruction for getting the complete preview of the app. Say instruction after the beep. ", TextToSpeech.QUEUE_FLUSH, null, null);
        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        Intent intent = new Intent(Needs.this, MainActivity.class);
        startActivity(intent);
        finish();
    }











    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(Needs.this, new String[]{
                    Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }


    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == RecordAudioRequestCode && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

    }


}