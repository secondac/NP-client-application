module com.examples {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens core.$legacyfiles to javafx.fxml;
    exports core.$legacyfiles;
    exports core.controller;
    exports core.model;
    exports core.view;
    exports core.service;
}