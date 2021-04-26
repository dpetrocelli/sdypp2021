package com.backend.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomMessage  {
    String text;
    int priority;
    boolean secret;

    public CustomMessage() {
    }

    public CustomMessage(String text, int priority, boolean secret) {
        this.text = text;
        this.priority = priority;
        this.secret = secret;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isSecret() {
        return secret;
    }

    public void setSecret(boolean secret) {
        this.secret = secret;
    }
}
