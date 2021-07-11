package org.tensorflow.lite.examples.detection;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    String welcome;

    EditText editText;
    ImageView imageView;

    public static final Integer RecordAudioRequestCode = 1;
    private TextToSpeech textToSpeech;


    private SpeechRecognizer speechRecognizer;
    AlertDialog.Builder alertSpeechDialog;
    AlertDialog alertDialog;

    final Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); //Made this global


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        imageView = findViewById(R.id.imageView);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
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
                View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.alertcustom, viewGroup, false);
                alertSpeechDialog = new AlertDialog.Builder(MainActivity.this);
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

                    if (string.equals("open navigation") || string.equals("navigation"))
                    {
                        Toast.makeText(getBaseContext(), "Navigation Module", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,DetectorActivity.class);
                        startActivity(intent);
                    }
                    else if(string.equals("what is the date") || string.equals("what is the date today") || string.equals("tell me the date") || string.equals("date"))
                    {
                        Calendar calendar = Calendar.getInstance();
                        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                        editText.setText(currentDate);
                        textToSpeech.speak(currentDate, TextToSpeech.QUEUE_FLUSH, null, null);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        take_command();
                    }
                    else if(string.equals("what is the time") || string.equals("tell me the time") || string.equals("time") || string.equals("tell me the time now"))
                    {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm a");
                        String currentTime = simpleDateFormat.format(calendar.getTime());
                        editText.setText(currentTime);
                        textToSpeech.speak(currentTime, TextToSpeech.QUEUE_FLUSH, null, null);
                        take_command();
                    }
                    else if (string.equals("help") || string.equals("emergency") || string.equals("help me") )
                    {
                        Toast.makeText(getBaseContext(), "Emergency Module", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, sms.class);
                        startActivity(intent);
                    }
                    else if (string.equals("denomination") || string.equals("how much am i holding") || string.equals("identify") || string.equals("currency") )
                    {
                        Toast.makeText(getBaseContext(), "Denomination Module", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, org.tensorflow.lite.examples.classification.CameraActivityDenom.class);
                        startActivity(intent);
                        //take_command();
                    }
                    else if (string.equals("stop"))
                    {
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory( Intent.CATEGORY_HOME );
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                    }
                    else if (string.equals("instructions") || string.equals("instruction"))
                    {
                        new Thread(new Runnable() {
                            public void run() {
                                // a potentially time consuming task
                                editText.post(new Runnable() {
                                    public void run() {
                                        welcome = "The app has a microphone button in the middle of your phone screen. " +
                                                "On pressing say the following commands based on your needs. " +
                                                "Say Open Navigation for obstacle detection. " +
                                                "Say identify to validate the cash note in your hand. " +
                                                "Say help in case of emergency.";

                                        textToSpeech.speak(welcome, TextToSpeech.QUEUE_FLUSH, null, null);
                                        try {
                                            Thread.sleep(20000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        }).start();
                    }
                    else
                    {
                        textToSpeech.speak("Invalid Command", TextToSpeech.QUEUE_FLUSH, null, null);
                        take_command();
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

        speechRecognizer.startListening(speechIntent);  //To start listening at the start of the app

        //For the button in the nlp main sceen

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                textToSpeech.speak("Please tell me, how can I help you?", TextToSpeech.QUEUE_FLUSH, null, null);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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

    private void After_sms_command() {
        textToSpeech.speak("Your current location has been sent to your emergency contacts. Help is on it's way. ", TextToSpeech.QUEUE_FLUSH, null, null);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        take_command();
    }


    //Function to take command again
    public void take_command()
    {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textToSpeech.speak("Please tell me, how can I help you?", TextToSpeech.QUEUE_FLUSH, null, null);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        speechRecognizer.startListening(speechIntent);
    }

    //Instructions button
    public void Instructions()
    {


    }

    public void Instructions(View view) {
        Instructions();
    }
//till here


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
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