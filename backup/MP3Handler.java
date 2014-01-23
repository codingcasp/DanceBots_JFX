package sample;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import javazoom.spi.mpeg.sampled.file.tag.MP3Tag;
import org.tritonus.share.sampled.TAudioFormat;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

/**
 * Created by Alessandro on 1/15/14.
 */
public class MP3Handler {

    String filepath;
    Map properties;
    Long duration;

    public MP3Handler(String filepath) {
        this.filepath = filepath;
    }

    public MP3Handler() {

    }

    public void setFilepath (String s) {
        this.filepath = s;
    }

    public Double getDuration() {
       return new Double(0);
    }

    public Map getAudioProperties() throws IOException, UnsupportedAudioFileException {

        File file = new File(this.filepath);
        AudioFileFormat baseFileFormat = null;
        AudioFormat baseFormat = null;
        baseFileFormat = AudioSystem.getAudioFileFormat(file);
        baseFormat = baseFileFormat.getFormat();

        // TAudioFileFormat properties
        if (baseFileFormat instanceof TAudioFileFormat)
        {
            Map properties = ((TAudioFileFormat)baseFileFormat).properties();
            this.properties = ((TAudioFileFormat)baseFileFormat).properties();
            String key = "author";
            String val = (String) properties.get(key);
            key = "mp3.id3tag.v2";
            InputStream tag= (InputStream) properties.get(key);
            return(properties);
        }
    // TAudioFormat properties
        if (baseFormat instanceof TAudioFormat)
        {
            Map properties = ((TAudioFormat)baseFormat).properties();
            this.properties =((TAudioFormat)baseFormat).properties();
            String key = "bitrate";
            Integer val = (Integer) properties.get(key);
            return(properties);
        }

        return(Collections.emptyMap());

    }

}
