package com.nurda.customview.model;

public class CardInfo {
    private String subtopic;
    private String topic;
    private String description;
    private int image;

    public CardInfo( String subtopic,
                        String topic,
                        String description,
                        int image) {

        this.subtopic = subtopic;
        this.topic = topic;
        this.description = description;
        this.image = image;
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

}
