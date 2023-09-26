package com.gluonhq.samples.cloudfirst.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.samples.cloudfirst.model.Model;
import com.gluonhq.samples.cloudfirst.model.Note;
import com.gluonhq.samples.cloudfirst.service.Service;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.util.ResourceBundle;

public class EditionPresenter {

    @Inject private Service service;

    @Inject private Model model;

    @FXML private View edition;

    @FXML private Button submit;
    @FXML private Button cancel;
    @FXML private TextField title;
    @FXML private TextArea comment;

    @FXML private ResourceBundle resources;

    private boolean editMode;

    public void initialize() {
        edition.setShowTransitionFactory(BounceInRightTransition::new);

        edition.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                submit.disableProperty().unbind();

                Note activeNote = model.activeNote().get();
                if (activeNote != null) {
                    submit.setText(resources.getString("button.apply"));
                    title.setText(activeNote.getTitle());
                    comment.setText(activeNote.getText());
                    submit.disableProperty().bind(Bindings.createBooleanBinding(()->{
                        if (title == null || comment == null) {
                            return true;
                        }
                        return title.textProperty()
                                .isEqualTo(activeNote.getTitle())
                                .and(comment.textProperty()
                                        .isEqualTo(activeNote.getText())).get();
                    }, title.textProperty(),comment.textProperty()));
                    editMode = true;
                } else {
                    submit.setText(resources.getString("button.submit"));
                    submit.disableProperty().bind(Bindings.createBooleanBinding(() -> {
                        return title.textProperty()
                                .isEmpty()
                                .or(comment.textProperty()
                                        .isEmpty()).get();
                    }, title.textProperty(), comment.textProperty()));
                    editMode = false;
                }

                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText(
                        editMode ?
                                resources.getString("appbar.edit.note") :
                                resources.getString("appbar.add.note"));
            } else {
                title.clear();
                comment.clear();
            }
        });

        submit.setOnAction(e -> {
            Note note = editMode ? model.activeNote().get() : new Note();
            note.setTitle(title.getText());
            note.setText(comment.getText());

            if (!editMode) {
                service.addNote(note);
            }
            close();
        });
        cancel.setOnAction(e -> close());
    }

    private void close() {
        title.clear();
        comment.clear();
        model.activeNote().set(null);
        AppManager.getInstance().goHome();
    }
}