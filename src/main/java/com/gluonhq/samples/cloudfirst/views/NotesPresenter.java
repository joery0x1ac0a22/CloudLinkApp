package com.gluonhq.samples.cloudfirst.views;

import com.gluonhq.charm.glisten.animation.BounceInLeftTransition;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.samples.cloudfirst.model.Model;
import com.gluonhq.samples.cloudfirst.model.Note;
import com.gluonhq.samples.cloudfirst.service.Service;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.ResourceBundle;

public class NotesPresenter {

    @Inject private Service service;

    @FXML private View notes;

    @Inject private Model model;

    @FXML private CharmListView<Note, LocalDate> lstNotes;

    @FXML private ResourceBundle resources;

    public void initialize() {
        notes.setShowTransitionFactory(BounceInLeftTransition::new);
        notes.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText(resources.getString("appbar.notes"));
            }
        });

        lstNotes.setCellFactory(p -> new NoteCell(service, this::edit, this::remove));
        lstNotes.setHeadersFunction(t -> t.getCreationDate().toLocalDate());
        lstNotes.setHeaderCellFactory(p -> new HeaderCell());
        lstNotes.setComparator(Comparator.comparing(Note::getCreationDate));
        lstNotes.setHeaderComparator(LocalDate::compareTo);
        lstNotes.setPlaceholder(new Label(resources.getString("label.no.notes")));

        final FloatingActionButton floatingActionButton = new FloatingActionButton();
        floatingActionButton.setOnAction(e -> edit(null));
        floatingActionButton.showOn(notes);

        lstNotes.setItems(service.getNotes());
    }

    private void edit(Note note) {
        model.activeNote().set(note);
        AppViewManager.EDITION_VIEW.switchView();
    }

    private void remove(Note note) {
        service.removeNote(note);
    }

}