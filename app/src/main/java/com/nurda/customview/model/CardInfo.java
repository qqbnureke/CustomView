package com.nurda.customview.model;

public class CardInfo {
    private String subtopic;
    private String topic;
    private String description;
    private int image;
    private String action1;
    private String action2;

    public CardInfo( String subtopic,
                        String topic,
                        String description,
                        int image,
                        String action1,
                        String action2) {

        this.subtopic = subtopic;
        this.topic = topic;
        this.description = description;
        this.image = image;
        this.action1 = action1;
        this.action2 = action2;
    }

    public String getSubtopic() {
        return subtopic;
    }

    public String getTopic() {
        return topic;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }

    public String getAction1() {
        return action1;
    }

    public String getAction2() {
        return action2;
    }
}
