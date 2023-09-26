package com.gluonhq.samples.cloudfirst.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Note {

    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty text = new SimpleStringProperty();
    private long creationDate;

    public Note() {
        this.creationDate = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    }

    public Note(String title, String text) {
        this.title.set(title);
        this.text.set(text);
    }

    public final String getTitle() { return title.get(); }
    public final void setTitle(String title) { this.title.set(title); }
    public final StringProperty titleProperty() { return this.title; }

    public final String getText() { return text.get(); }
    public final void setText(String text) { this.text.set(text); }
    public final StringProperty textProperty() { return this.text; }

    public final LocalDateTime getCreationDate() {
        return LocalDateTime.ofEpochSecond(creationDate, 0, ZoneOffset.UTC);
    }

    @Override
    public String toString() {
        return super.toString() + " with title " + title.get();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.title);
        hash = 59 * hash + Objects.hashCode(this.text);
        hash = 59 * hash + (int) (this.creationDate ^ (this.creationDate >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Note other = (Note) obj;
        if (this.creationDate != other.creationDate) {
            return false;
        }
        if (!Objects.equals(this.getTitle(), other.getTitle())) {
            return false;
        }
        return Objects.equals(this.getText(), other.getText());
    }
}