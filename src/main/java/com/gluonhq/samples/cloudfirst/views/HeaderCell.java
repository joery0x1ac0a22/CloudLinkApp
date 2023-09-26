package com.gluonhq.samples.cloudfirst.views;

import com.gluonhq.charm.glisten.control.CharmListCell;
import com.gluonhq.samples.cloudfirst.model.Note;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.scene.control.Label;

public class HeaderCell extends CharmListCell<Note> {

    private final Label label;
    private Note currentItem;
    private final DateTimeFormatter dateFormat;

    public HeaderCell() {
        label = new Label();
        dateFormat = DateTimeFormatter.ofPattern("EEEE, MMM dd", Locale.ENGLISH);
    }

    @Override
    public void updateItem(Note item, boolean empty) {
        super.updateItem(item, empty);
        currentItem = item;
        if (!empty && item != null) {
            updateWithSettings();
            setGraphic(label);
        } else {
            setGraphic(null);
        }
    }

    private void updateWithSettings() {
        if (currentItem != null) {
            label.setText(dateFormat.format(currentItem.getCreationDate()));
        } else {
            label.setText("");
        }
    }

}