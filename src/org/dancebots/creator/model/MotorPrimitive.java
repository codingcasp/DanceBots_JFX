package org.dancebots.creator.model;

/**
 * Created by codingcaspar on 1/21/14.
 */
public class MotorPrimitive extends Object{

    private Short   ID;
    private String  type;
    private Double  frequency;
    private Short   velocity;
    private Short   velocityRight;
    private Short   velocityLeft;

    private Double  positionInTrack;
    private Short   duration;



    private MotorPrimitive(Builder builder)  {
        this.ID                 = builder.ID;
        this.type               = builder.type;
        this.frequency          = builder.frequency;
        this.velocity           = builder.velocity;
        this.velocityLeft       = builder.velocityLeft;
        this.velocityRight      = builder.velocityRight;
        this.positionInTrack    = builder.positionInTrack;
        this.duration           = builder.duration;
    }

        // Builder pattern
        public static class Builder {

        // mandatory parameter
        private Short       ID;
        private String      type;
        private Double      positionInTrack;
        private Short       duration;
        private Double      frequency;

        // optional
        private Short       velocity = 0;
        private Short       velocityLeft = 0;
        private Short       velocityRight = 0;


        public Builder(Short ID, String type, Double positionInTrack, Short duration, Double frequency) {
            this.ID =ID;
            this.type = type;
            this.positionInTrack = positionInTrack;
            this.duration = duration;
            this.frequency = frequency;
        }
        public Builder velocity(Short vel) {
            this.velocity = vel;
            return this;
        }
        public Builder velocityLeft(Short vel) {
            this.velocityLeft = vel;
            return this;
        }
        public Builder velocityRight(Short vel) {
            this.velocityRight = vel;
            return this;
        }

        public MotorPrimitive build() {
            return new MotorPrimitive(this);
        }
    }


    //end of builder pattern

    //****************************
    // METHODS of MotorPrimitive
    //****************************

    public String getType() {return this.type;}

    public Short getID() {
        return this.ID;
    }
    public Short getDuration() {return this.duration;}

    public Double getPositionInTrack(){return this.positionInTrack;}
    public Double getFrequency(){return this.frequency;}

    public Short getVelocity() {return this.velocity;}
    public Short getVelocityRight() {return this.velocityRight;}
    public Short getVelocityLeft() {return this.velocityLeft;}






}




