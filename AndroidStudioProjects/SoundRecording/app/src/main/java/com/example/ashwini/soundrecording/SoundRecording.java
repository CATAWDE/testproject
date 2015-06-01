package com.example.ashwini.soundrecording;

/*This example will help you to record audio in .wav format The WAV file will be
 * placed in “/SDCard/AudioRecorder” folder with current millisecond as the file name.
 * --------------------------
 *
 * Just remember to use a short[] buffer instead of byte buffer when using 16bit.
 *  Since 16bit is 2 bytes you will have to combine two entries in the buffer at a time:

byte][]{sample_1_upper, sample_1_lower, sample_2_upper, sample_2_lower,...,sample_n_lower}
However if you'll use a short[]buffer:
short[]{sample1, sample2, ..., sample3}
 *
 * */


        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.text.SimpleDateFormat;
        import java.util.Date;

/*****************************************************************/

/*The AudioFormat class :
-is used to access a number of audio format and channel configuration constants.
They are for instance used in AudioTrack and AudioRecord.
 audio format can be :
int 	ENCODING_AC3 	      Audio data format: AC-3 compressed
int 	ENCODING_DEFAULT 	  Default audio data format
int 	ENCODING_E_AC3 	      Audio data format: E-AC-3 compressed
int 	ENCODING_INVALID 	  Invalid audio data format
int 	ENCODING_PCM_16BIT 	  Audio data format: PCM 16 bit per sample.
                                                 Guaranteed to be supported by devices.
                                                 Constant Value: 2 (0x00000002)
int 	ENCODING_PCM_8BIT 	  Audio data format: PCM 8 bit per sample.
                                                 Not guaranteed to be supported by devices.
                                                 Constant Value: 3 (0x00000003)
int 	ENCODING_PCM_FLOAT 	  Audio data format: single-precision floating-point per sample

Channel configuration:
int 	CHANNEL_CONFIGURATION_INVALID   use CHANNEL_INVALID instead
int 	CHANNEL_CONFIGURATION_MONO      use CHANNEL_OUT_MONO or CHANNEL_IN_MONO instead
int 	CHANNEL_CONFIGURATION_STEREO    use CHANNEL_OUT_STEREO or CHANNEL_IN_STEREO instead

*/
        import android.media.AudioFormat;
/* Audiorecord class :
 - manages the audio resources, to record audio from the audio input hardware of the platform.
 -Upon creation, an AudioRecord object initializes its associated audio buffer that it will fill with the new audio data
 -The size of this buffer, specified during the construction, determines how long an AudioRecord can
  record before "over-running" data that has not been read yet.
 -Data should be read from the audio hardware in chunks of sizes inferior to the total recording buffer size.

 int STATE_INITIALIZED: indicates AudioRecord state is ready to be used , Constant Value: 1 (0x00000001)
 int STATE_UNINITIALIZED: indicates AudioRecord state is not successfully initialized,  Constant Value: 0
 int SUCCESS : Denotes a successful operation.  Constant Value: 0 (0x00000000)

 int ERROR : Denotes a generic "operation failure". Constant Value: -1 (0xffffffff)
 int ERROR_BAD_VALUE : Denotes a failure due to the use of an "invalid value". Constant Value: -2
 int ERROR_INVALID_OPERATION: Denotes a failure due to the "improper use of a method".Constant Value: -3 (0xfffffffd)

 public AudioRecord (int audioSource, int sampleRateInHz, int channelConfig, int audioFormat, int bufferSizeInBytes)
                                                          mono /stereo          8/16
 */
        import android.media.AudioRecord;
/*MediaRecorder:Used to record audio and video. The recording control is based on a simple state machine (see below). */
        import android.media.MediaRecorder;
        import android.media.MediaRecorder.AudioSource;//class 	MediaRecorder.AudioSource 	Defines the audio source.
        import android.os.Bundle;
        import android.os.Environment;
/*StatFs :Retrieve overall information about the space on a filesystem.
 * getAvailableBlocks() ,int 	getBlockCount(), int 	getBlockSize() */
        import android.os.StatFs;
/*View :
 * This class represents the basic building block for user interface components.
 * A View occupies a rectangular area on the screen and is responsible for drawing and event handling.
 * View is the base class for widgets,
 * which are used to create interactive UI components (buttons, text fields, etc.). */
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;


public class SoundRecording extends Activity {
    /***************************************/

    // private int audioEncoding = 2;
    //  private int channelConfiguration = 16;

    private TextView tv;
    /********************************/
    private static final int RECORDER_BPP = 16;
    private static final String AUDIO_RECORDER_FILE_EXT_WAV = ".wav";
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
    private static final String AUDIO_RECORDER_TEMP_FILE = "record_temp.raw";
    private static final int RECORDER_SAMPLERATE = 44100;
    //44100, 22050, 11025, 8000, etc..
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_STEREO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;// 16 bit per sample

    private AudioRecord recorder = null;
    private int bufferSize = 0;
    private Thread recordingThread = null;
    private boolean isRecording = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_recording);
        tv = (TextView) findViewById(R.id.textView1);
        setButtonHandlers();     // sets buttons id and  a listener to notify clients when the button is clicked.
        enableButtons(false);    // set enabled state of button. true =enable, false =disable

        checkTotalSpace();      // get info of SD card


        // 44100, stereo ,PCM_16
        bufferSize = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE,
                AudioFormat.CHANNEL_IN_STEREO,
                AudioFormat.ENCODING_PCM_16BIT);
    }

    private void checkTotalSpace() {
        // TODO Auto-generated method stub

        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());

        @SuppressWarnings("deprecation")
        float blockSize = stat.getBlockSize() ;
        @SuppressWarnings("deprecation")
        float totalBlocks = stat.getBlockCount();
        @SuppressWarnings("deprecation")
        float availableBlocks = stat.getAvailableBlocks();
        float bytesAvailable=blockSize * totalBlocks;
        float megTotal= bytesAvailable / 1073741824;
        float megAvailable=blockSize * availableBlocks;
        float megAva= megAvailable/1048576;

        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd   HH.mm.ss:SSS") ;
        //tv.setText("Current date is:"+dateFormat.format(date)+ "\n"+dataFromAsyncTask );
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // Can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Can't read or write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }


        // External SD card
        tv.setText("DATE  : "+dateFormat.format(date)+"\n\nEXTERNAL MEDIA: \n readable="
                +mExternalStorageAvailable+"  & "+" writable="+mExternalStorageWriteable+
                "\n\n AVAILABLE SPACE : \n"+(megAva)+"MB / " + (megTotal)+"GB" + "\n\n PATH: \n "+ path );
        return;
    }




    private void setButtonHandlers() {
        ((Button)findViewById(R.id.btnStart)).setOnClickListener(btnClick);
        ((Button)findViewById(R.id.btnStop)).setOnClickListener(btnClick);
    }

    private void enableButton(int id,boolean isEnable){
        	/* setEnabled :Set the enabled state of this view
        	 * enabled 	=True if this view is enabled, false otherwise. .*/
        ((Button)findViewById(id)).setEnabled(isEnable);
    }

    private void enableButtons(boolean isRecording) {
        System.out.println("in enableButtons");
        enableButton(R.id.btnStart,!isRecording); // !(false) = true
        enableButton(R.id.btnStop,isRecording);// false
    }

    @SuppressLint("SimpleDateFormat")
    private String getFilename() // get .wav file name
    {
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;

        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath,AUDIO_RECORDER_FOLDER); //"AudioRecorder";

        if(!file.exists()){
            file.mkdirs();
        }
        //".wav";
        return (file.getAbsolutePath() + "/" + (dateFormat.format(date)+" "+ System.currentTimeMillis()) + AUDIO_RECORDER_FILE_EXT_WAV);
    }

    private String getTempFilename() // get record_temp.raw file name
    {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath,AUDIO_RECORDER_FOLDER); //"AudioRecorder";

        if(!file.exists()){
            file.mkdirs();
        }

        File tempFile = new File(filepath,AUDIO_RECORDER_TEMP_FILE);//"record_temp.raw"

        if(tempFile.exists())
            tempFile.delete();

        return (file.getAbsolutePath() + "/" + AUDIO_RECORDER_TEMP_FILE);//"record_temp.raw"
    }

    private void startRecording(){

        System.out.println("in startrecording instance innitialization");
    /*recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                                     RECORDER_SAMPLERATE, //44100
                                     RECORDER_CHANNELS, //AudioFormat.CHANNEL_IN_STEREO;
                                     RECORDER_AUDIO_ENCODING,  //AudioFormat.ENCODING_PCM_16BIT;
                                     bufferSize);*/
        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                RECORDER_SAMPLERATE,
                AudioFormat.CHANNEL_IN_STEREO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize);

        int i = recorder.getState();
        if(i==1) //indicates AudioRecord state is ready to be used
            // 0 : not successfully initialized
            recorder.startRecording(); //Starts recording from the AudioRecord instance.

        isRecording = true;

        recordingThread = new Thread(new Runnable() {

            @Override
            public void run() {
                writeAudioDataToFile();
            }
        },"AudioRecorder Thread");

        recordingThread.start();
    }

    private void writeAudioDataToFile(){
        //byte data[] = new byte[bufferSize];
        byte[] audioBuffer;
        audioBuffer= new byte[bufferSize];
        String filename = getTempFilename(); //"record_temp.raw"

        FileOutputStream os = null;

        try {
            os = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int read = 0;

        if(null != os){
            while(isRecording)
            {
                //public int read (byte[] audioData(TARGET), int offsetInBytes, int sizeInBytes(source))
                read = recorder.read( audioBuffer, 0, bufferSize);

                if(AudioRecord.ERROR_INVALID_OPERATION != read){
                    try {
                        os.write(audioBuffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //  when the recording is stopped we read this data and write to WAV file.
    private void stopRecording(){
        if(null != recorder){
            isRecording = false;

            int i = recorder.getState();
            if(i==1)
                recorder.stop();
            //Recording can be stopped using stop() method. Remember to call release() method to release the AudioRecord object.
            recorder.release();

            recorder = null;
            recordingThread = null;
        }
        // raw , wave file
        copyWaveFile(getTempFilename(),getFilename());
        deleteTempFile();
    }

    private void deleteTempFile() {
        File file = new File(getTempFilename());

        file.delete();
    }

    // raw , wav file
    private void copyWaveFile(String inFilename,String outFilename){
        FileInputStream in = null;
        FileOutputStream out = null;
        long totalAudioLen = 0;
        long totalDataLen = totalAudioLen + 36;
        long longSampleRate = RECORDER_SAMPLERATE; //44100
        int channels = 2;
        //SampleRate * NumChannels * BitsPerSample/8
        long byteRate = RECORDER_BPP * RECORDER_SAMPLERATE * channels/8;
        //( 16* 44100 * 2/8)
        byte[] data = new byte[bufferSize];

        try {
            in = new FileInputStream(inFilename); // raw
            out = new FileOutputStream(outFilename); // wav
            totalAudioLen = in.getChannel().size();
            totalDataLen = totalAudioLen + 36;

            System.out.println("File size : " + totalDataLen);
            AppLog.logString("File size: " + totalDataLen);

            // wav file
            WriteWaveFileHeader(out, totalAudioLen, totalDataLen,
                    longSampleRate, channels, byteRate);

            while(in.read(data) != -1){
                out.write(data); //wav
            }

            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void WriteWaveFileHeader(
            FileOutputStream out, long totalAudioLen,
            long totalDataLen, long longSampleRate, int channels,
            long byteRate) throws IOException {

        byte[] header = new byte[44];

        header[0] = 'R';  // RIFF/WAVE header
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f';  // 'fmt ' chunk : describes the sound data's format: 4 bytes
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16;  // Subchunk1Size    16 for PCM.  This is the size of the // 4 bytes
        //rest of the Subchunk which follows this number.
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1;  // format = 1   PCM = 1 (i.e. Linear quantization)
        //Values other than 1 indicate some  form of compression.  : 2 bytes
        header[21] = 0;
        header[22] = (byte) channels; // Mono = 1, Stereo = 2, etc. 2 bytes  here int channels = 2;
        header[23] = 0;
        header[24] = (byte) (longSampleRate & 0xff); //  Sample rate :8000, 44100, etc. : 4 bytes
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff); //byterate   == (SampleRate * NumChannels * BitsPerSample/8):4bytes
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        header[32] = (byte) (2 * 16 / 8);  //  BlockAlign       == NumChannels * BitsPerSample/8
        //The number of bytes for one sample including
        //all channels. I wonder what happens when this number isn't an integer? = 2 bytes
        header[33] = 0;
        header[34] = RECORDER_BPP;  // bits per sample  8 or 16 here 16.. 2bytes
        header[35] = 0;
                /*The "data" subchunk contains the size of the data and the actual sound:*/
        header[36] = 'd';// Contains the letters "data"  4 bytes
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';

/*40  4   Subchunk2Size= NumSamples * NumChannels * BitsPerSample/8
                               This is the number of bytes in the data.
                               You can also think of this as the size
                               of the read of the subchunk following this
                               number.*/
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
        // 44        *   Data             The actual sound data.
        out.write(header, 0, 44);//(byte[] buffer, int byteOffset, int byteCount)
                /*Writes count bytes from header  starting at 0 offset to this stream.
                 * 0 the start position in buffer from where to get bytes.
 byteCount the number of bytes from header  to write to this stream.
*/
    }

    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnStart:{
                    AppLog.logString("Start Recording");
                    System.out.println("Start Recording");

                    enableButtons(true);
                    startRecording();//to start recording audio.

                    break;
                }
                case R.id.btnStop:{
                    AppLog.logString("Stop Recording");
                    System.out.println("Stop Recording");

                    enableButtons(false);
                    stopRecording();

                    break;
                }
            }
        }
    };
}
