package org.dancebots.creator.model;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.media.AudioClip;
import org.tritonus.share.sampled.TAudioFormat;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Model class for the used Song in the application. Performs all
 * necessary transformations on the data and provides methods to
 * retrieve information and data as well as playback controls.
 * @author      Caspar Reist
 * @version     %I%, %G%
 * @since       1.0
 */
public class Song {

    String      songFilepath;
    File        infile;
    Clip        songclip;
    Map         songProperties;
    Map         songParams;
    Long        songDurationMS;
    Short       songBPM = 99;

    Short  plIndex = 0;



    private List<MotorPrimitive> list = new ArrayList<MotorPrimitive>();
    private ObservableList<MotorPrimitive> primitiveList = FXCollections.observableArrayList(list);




    public Song() {
        //required initiations

        songProperties = Collections.emptyMap();
        songParams = Collections.emptyMap();




    }

    public ObservableList<MotorPrimitive> getMotorPrimitiveList() {
        return this.primitiveList;
    }
    /**
     * Provides the audio clip as an object [Clip]
     * @see       Clip
     * @since     1.0
     */
    public Clip getSongclip() {
        return this.songclip;
    }

    /**
     * Starts/resumes the playback of the audio clip (unconditionally)
     *
     * @since     1.0
     */
    public void playClip() {
        songclip.start();
    }

    /**
     * Stops the playback of the audio clip (unconditionally)
     *
     * @since     1.0
     */

    public void stopClip() {
        if (songclip.isOpen() & songclip.isRunning()) {
          songclip.stop();}

    }

    /**
     * Returns a boolean regarding the current play/pause state of
     * the audio clip
     *
     * @return    <code>true</code> if clip is currently playing
     *            <code>false</code> is paused or not yet started
     * @since     1.0
     */
    public boolean isRunning() {
        return songclip.isRunning();
    }

    /**
     * Returns the current position in the audio clip as double value
     * for use in further calculations
     *
     * @return    Current position in clip as double
     * @since     1.0
     */
    public Double getClipPosition() {

        Float retVal = 1 / songDurationMS.floatValue() * songclip.getMicrosecondPosition();
        return(retVal.doubleValue());
    }


    /**
     * Returns the current position in the audio clip in a printable
     * string for use in the UI.
     *
     * @return    Current position in clip in the format mM:SS
     * @since     1.0
     */
    public String getClipPositionInMinSec() {

        Long dur = (Long) songclip.getMicrosecondPosition();
        dur = dur/1000000;


        Long min = dur / 60;
        Long sec = dur % 60;

        return(min.toString() + ":" + sec.toString());
    }

    /**
     * Handles all needed steps to load the provided File as a
     * Clip into songclip. Uses acquireSong() and retrieveAudioProperties()
     *
     * @param   file  File object e.g. from FileChooser
     * @see     org.dancebots.creator.model.Song#acquireSong()
     * @see     org.dancebots.creator.model.Song#retrieveAudioProperties()
     * @since   1.0
     */
    public void setSong(File file) throws IOException {
        System.out.println("Entering setSong");

        this.songFilepath = file.getName();
        this.infile = file;
        System.out.println("Filepath set to" +     this.songFilepath);

        acquireSong();

        retrieveAudioProperties();

        this.songDurationMS = (Long) this.songProperties.get("duration");

    }

    /**
     * Returns the title of the currently loaded song
     *
     * @return  Title of the loaded track as string for UI display
     * @since   1.0
     */
    public String getTitle() {
        return this.songProperties.get("title").toString();
    }

    /**
     * Returns the artist of the currently loaded song
     *
     * @return  Artist/author of the loaded track as string for UI display
     * @since   1.0
     */
    public String getArtist() {
        return this.songProperties.get("author").toString();
    }


    /**
     * Returns the album of the currently loaded song
     *
     * @return  Album of the loaded track as string for UI display
     * @since   1.0
     */
    public String getAlbum() {
        return this.songProperties.get("album").toString();
    }

    /**
     * Returns the total duration of the currently loaded song
     * as a displayable string
     *
     * @return  Total duration of track as MM:SS
     * @since   1.0
     */
    public String getDuration() {
        //duration contains tracklength in microseconds
        Long dur = (Long) this.songProperties.get("duration");
        dur = dur/1000000;
        System.out.println("Duration in sec" +  dur.toString());


        Long min = dur / 60;
        Long sec = dur % 60;

        return(min.toString() + ":" + sec.toString());

    }

    public Double getDurationInSeconds() {
        Long dur = (Long) this.songProperties.get("duration");
        dur = dur / 1000000;

        return dur.doubleValue();
    }

    /**
     * Sets the desired position in the clip after calculating the
     * clicked relative position in the audio clip.
     *
     * @param   mouse Mouse position
     * @param   track Track duration
     * @see     Clip#setMicrosecondPosition(long)
     * @since   1.0
     */
    public void updateTrackPosition(Double mouse, Double track) {
        Double relativePos = (100 / track * mouse);
        Double newPos = (songDurationMS.doubleValue() / 100 * relativePos);
        songclip.setMicrosecondPosition(newPos.longValue());
    }


    /**
     * Returns the BPM as detected of the current audio clip
     * <not>implemented></not>
     *
     * @since   1.0
     */
    public String getBPM() {
        return this.songBPM.toString();
    }


    /**
     * Reads tag information from loaded file and sets Song#songProperties
     * and Song#songParams
     *
     * @see Song#songProperties
     * @see Song#songParams
     * @since   1.0
     */
    public void retrieveAudioProperties(){
        System.out.println("Entering retrieveAudioProperties");

        File file = this.infile;
        AudioFileFormat baseFileFormat = null;
        AudioFormat baseFormat = null;
        try {
            baseFileFormat = AudioSystem.getAudioFileFormat(file);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        baseFormat = baseFileFormat.getFormat();

        // TAudioFileFormat properties
        if (baseFileFormat instanceof TAudioFileFormat)
        {
            System.out.println("Setting props from TAudioFileFormat");
            this.songProperties = ((TAudioFileFormat)baseFileFormat).properties();


        }
        // TAudioFormat properties
        if (baseFormat instanceof TAudioFormat)
        {
            System.out.println("Setting props from TAudioFormat");
            this.songParams =((TAudioFormat)baseFormat).properties();


        }



        System.out.println("Reached End retrieveAudioProperties");
    }

    /**
     * Converts Song#infile to Clip. Called by Song#setSong
     *
     * @see Clip
     * @see Song#setSong(java.io.File)
     * @since   1.0
     */
    public void acquireSong() {
        System.out.println("Entering acquireSong");
        //gotta check for existense of mp3 handler and reuse if user drops new file
        try {
            //File file = new File(this.songFilepath);
            File file = this.infile;
            AudioInputStream in= AudioSystem.getAudioInputStream(file);


            AudioInputStream din = null;
            AudioFormat baseFormat = in.getFormat();
            AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false);
            din = AudioSystem.getAudioInputStream(decodedFormat, in);
            songclip = AudioSystem.getClip();
            songclip.open(din);

            in.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    System.out.println("Leaving acquireSong");
    }

    /**
     * Converts Song#songclip into two separate channels
     *
     * @since   1.0
     */
    public void splitSong() {

    }


    public void addPrimitive(MotorPrimitive mp) {
        primitiveList.add(mp.getID(),mp);
        plIndex++;


    }

    public void printPrimitives() {
        Iterator<MotorPrimitive> it = primitiveList.iterator();
        while(it.hasNext())
        {
            Object obj = it.next();
            System.out.println(obj.toString());
        }
    }
}
