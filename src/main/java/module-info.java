module com.gluonhq.samples.cloudfirststorage {

    requires javafx.controls;
    requires javafx.fxml;

    requires com.gluonhq.charm.glisten;
    requires com.gluonhq.cloudlink.client;
    requires com.gluonhq.connect;
    requires com.gluonhq.glisten.afterburner;

    requires com.gluonhq.attach.display;
    requires com.gluonhq.attach.lifecycle;
    requires com.gluonhq.attach.statusbar;
    requires com.gluonhq.attach.storage;
    requires com.gluonhq.attach.util;

    requires java.json;
    requires java.annotation;
    requires afterburner.mfx;

    exports com.gluonhq.samples.cloudfirst;
    exports com.gluonhq.samples.cloudfirst.model to afterburner.mfx;
    exports com.gluonhq.samples.cloudfirst.service to afterburner.mfx;
    exports com.gluonhq.samples.cloudfirst.views to afterburner.mfx;

    opens com.gluonhq.samples.cloudfirst.model to com.gluonhq.cloudlink.client;
    opens com.gluonhq.samples.cloudfirst.views to javafx.fxml, afterburner.mfx, com.gluonhq.glisten.afterburner;
}