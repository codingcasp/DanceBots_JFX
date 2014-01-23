package org.dancebots.creator;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.dancebots.creator.model.AnimationPopupMenu;
import org.dancebots.creator.model.MotorPrimitive;
import org.dancebots.creator.model.Song;
import org.dancebots.creator.util.PrimitiveBox;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class ChoreographerController {
    public static Label logArea;

    @FXML
    public ProgressBar songbarProgress;
    @FXML
    public ScrollPane songbar;

    public Label labelSongtitle;
    public Label labelSongartist;
    public Label labelSongalbum;
    public Label labelSongduration;
    public Label labelSongBPM;
    public Label songbarTime;
    public Label songbarTimeMax;
    public Line timeLine;
    public Group songbarGroup;


    private Song currentTrack;

    private Stage stage;

    private ListChangeListener<MotorPrimitive> mpListener;

    public void initialize() {
        currentTrack = new Song();
    }

    public void setStage (Stage stage) {
        System.out.println("Stage set");
        this.stage = stage;

        songbar.addEventFilter(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        // bind to right mouse button
                        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                            drawToSongbar(mouseEvent);

                            mouseEvent.consume();
                        }


                    }
                });

    }
    public void loadChoreoDialog(ActionEvent actionEvent) {
       logArea.setText("Button loadChoreoDialog clicked \n" + logArea.getText());

       currentTrack.playClip();
    }

    public void saveChoreoDialog(ActionEvent actionEvent) {
        logArea.setText("Button saveChoreoDialog clicked \n" +  logArea.getText());
        currentTrack.stopClip();
    }

    public void generateFile(ActionEvent actionEvent) {
        logArea.setText("Button generate clicked \n" +  logArea.getText());
    }

    public void playClip() {
        currentTrack.playClip();
    }

    public void pauseClip() {
        currentTrack.stopClip();
    }

    public void loadFile(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(null);

        try {
            System.out.println("attempting to set file");
            this.currentTrack.setSong(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        fillSongInfo();
        initializeProgressBar();

        setupListener();


        // timeline to keep progress updated
        final Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                updateProgressBar();

            }
        }) , new KeyFrame(Duration.millis(250)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        prototypeBox();



    }

    private void setupListener() {

        mpListener = new ListChangeListener<MotorPrimitive>() {
            @Override
            public void onChanged(Change<? extends MotorPrimitive> change) {

                while(change.next()){

                    if (change.wasPermutated()) {
                        for (int i = change.getFrom(); i < change.getTo(); ++i) {
                            //permutate
                        }
                    } else if (change.wasUpdated()) {
                        //update item
                    } else {
                        for (MotorPrimitive remitem : change.getRemoved()) {

                            System.out.println("Deleted primitive at" + remitem.getPositionInTrack().toString());
                        }
                        for (MotorPrimitive additem : change.getAddedSubList()) {
                            System.out.println("New primitive at" + additem.getPositionInTrack().toString());
                        }
                    }


                }
                System.out.println("LIST HAS CHANGED");
            }
        };

        currentTrack.getMotorPrimitiveList().addListener(mpListener);
    }

    private void fillSongInfo() {
        labelSongartist.setText(currentTrack.getArtist());
        labelSongtitle.setText(currentTrack.getTitle());
        labelSongalbum.setText(currentTrack.getAlbum());
        labelSongduration.setText(currentTrack.getDuration());
        labelSongBPM.setText(currentTrack.getBPM());
    }

    private void initializeProgressBar() {
        songbarProgress.setProgress(0);
        songbarTime.setText("0:00");
        songbarTimeMax.setText(currentTrack.getDuration());
        songbarProgress.addEventFilter(MouseEvent.MOUSE_CLICKED,
                                       new EventHandler<MouseEvent>() {
                                           @Override
                                           public void handle(MouseEvent mouseEvent) {

                                               currentTrack.updateTrackPosition(mouseEvent.getX(), songbarProgress.getWidth());
                                               updateProgressBar();
                                               mouseEvent.consume();
                                           }
                                       });
        songbar.addEventFilter(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {

                        currentTrack.updateTrackPosition(mouseEvent.getX(), songbar.getWidth());
                        updateProgressBar();
                        mouseEvent.consume();
                    }
                });




    }

    private void updateProgressBar() {
       //get current position in track as double value between 0.0 and 1.0
       songbarProgress.setProgress(currentTrack.getClipPosition());
       //get clip position calculated in minutes and seconds
       songbarTime.setText(currentTrack.getClipPositionInMinSec());



       updateTimeLine();


    }

    private void updateTimeLine() {

        Double relativePos = currentTrack.getClipPosition() * (songbar.getWidth() - songbar.getLayoutX());


        timeLine.setLayoutX(songbar.getLayoutX()+relativePos);





    }


    public void drawSongbar (Double X, Double Y, MotorPrimitive mp) {

        Group boxGroup = new Group();

        Stop[] stops = new Stop[] { new Stop(0, Color.rgb(197,234,245)), new Stop(1, Color.WHITE)};

        LinearGradient lg2 = new LinearGradient(1, 0, 50, 0, false, CycleMethod.NO_CYCLE, stops);

        Rectangle box = new Rectangle(50,songbar.getHeight()-1);
        box.setFill(lg2);
        box.setStroke(Color.BLACK);
        //box.setX(songbar.getLayoutX());
        //box.setY(songbar.getLayoutY());
        box.setX(X);
        box.setY(Y);



        PrimitiveBox box2 = new PrimitiveBox(mp);
        box2.setX(X); box2.setY(Y);
        box2.setBoxHeight(songbar.getHeight()-1);
        box2.setBoxWidth(75D);

        boxGroup.getChildren().add(box2.getContainer());


        songbarGroup.getChildren().add(boxGroup);

        //songbar.setContent(motorCanvas);


    }

    private void drawToSongbar(MouseEvent mouseEvent) {
        //System.out.println("drawToSongbar, mouseX/Y :"+mouseX + "/" +mouseY);
        //drawSongbar(mouseX, mouseY);


        final Popup popup = new Popup();


        AnimationPopupMenu myMenu  = new AnimationPopupMenu(currentTrack, this, mouseEvent);


        myMenu.setX(mouseEvent.getScreenX());
        myMenu.setY(mouseEvent.getScreenY());
        myMenu.show(this.stage);




    }

    public Song getSong() {
        return this.currentTrack;
    }

    private void prototypeBox() {

    }



}
