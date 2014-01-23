package org.dancebots.creator.model;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.control.SeparatorMenuItemBuilder;

/**
 * Created by Alessandro on 1/21/14.
 */
public class AnimationPopupMenu extends ContextMenu {

    Song songRef;

    public AnimationPopupMenu(Song contextSong)
    {
    this.songRef = contextSong;
    songRef.stopClip();

    String MOVE_TWIST       = "Add Twist";
    String MOVE_BACKFORTH   = "Add Back and Forth";
    String MOVE_STRAIGTH    = "Add Drive Straight";
    String MOVE_SPIN        = "Add Spin";
    String MOVE_CUSTOM      = "Add Drive Custom";



    String LED_KITT         = "LED Knight Rider";
    String LED_ALT          = "LED Alternate";
    String LED_BLINK        = "LED Blink";
    String LED_CONSTANT     = "LED Constant";
    String LED_RANDOM       = "LED Random";



     this.getItems().addAll(
             MenuItemBuilder.create()
                     .text(MOVE_TWIST)
             .onAction(
                new EventHandler<ActionEvent>()
                {
                 @Override
                 public void handle(ActionEvent e) {

                     System.out.println("MOVE TWIST EVENT TRIGGERED");
                     Short newID = 0;
                     MotorPrimitive.Builder builder =
                             new MotorPrimitive.Builder(newID,"TWIST",1000L,new Short("2"),1D);

                     MotorPrimitive mp = builder.build();
                     songRef.addPrimitive(mp);


                     songRef.printPrimitives();

                 }
             })
             .build(),
             MenuItemBuilder.create()
             .text(MOVE_BACKFORTH)
             .onAction(
                new EventHandler<ActionEvent>()
                {
                    @Override public void handle(ActionEvent e)
                    {
                        System.out.println("You clicked on About!");
                    }
                })
             .build(),
             MenuItemBuilder.create()
             .text(MOVE_STRAIGTH)
             .onAction(new EventHandler<ActionEvent>() {
                 @Override
                 public void handle(ActionEvent e) {

                 }
             })
             .build(),
             MenuItemBuilder.create()
             .text(MOVE_SPIN)
             .onAction(new EventHandler<ActionEvent>() {
                 @Override
                 public void handle(ActionEvent event) {

                 }
             })
             .build(),
             MenuItemBuilder.create()
             .text(MOVE_CUSTOM)
             .onAction(new EventHandler<ActionEvent>() {
                 @Override
                 public void handle(ActionEvent event) {

                 }
             })
             .build(),
             SeparatorMenuItemBuilder.create().build(),
             MenuItemBuilder.create()
                     .text(LED_KITT)
                     .onAction(new EventHandler<ActionEvent>() {
                         @Override
                         public void handle(ActionEvent event) {

                         }
                     })
                     .build(),
             SeparatorMenuItemBuilder.create().build(),
             MenuItemBuilder.create()
                     .text(LED_ALT)
                     .onAction(new EventHandler<ActionEvent>() {
                         @Override
                         public void handle(ActionEvent event) {

                         }
                     })
                     .build(),
             SeparatorMenuItemBuilder.create().build(),
             MenuItemBuilder.create()
                     .text(LED_BLINK)
                     .onAction(new EventHandler<ActionEvent>() {
                         @Override
                         public void handle(ActionEvent event) {

                         }
                     })
                     .build(),
             SeparatorMenuItemBuilder.create().build(),
             MenuItemBuilder.create()
                     .text(LED_CONSTANT)
                     .onAction(new EventHandler<ActionEvent>() {
                         @Override
                         public void handle(ActionEvent event) {

                         }
                     })
                     .build(),
             SeparatorMenuItemBuilder.create().build(),
             MenuItemBuilder.create()
                     .text(LED_RANDOM)
                     .onAction(new EventHandler<ActionEvent>() {
                         @Override
                         public void handle(ActionEvent event) {

                         }
                     })
                     .build()
     );


    }
}
