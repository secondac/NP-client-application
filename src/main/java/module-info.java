module core {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires com.google.gson;

    opens core.$__legacyfiles to javafx.fxml, com.google.gson;

    opens core.view to javafx.fxml;
    opens core.controller to javafx.fxml;
    opens core.service to javafx.fxml;
    opens core.model to javafx.fxml;
    opens core.util to javafx.fxml;


    exports core.$__legacyfiles;
    exports core.controller;
    exports core.model;
    exports core.model.dto;
    exports core.view;
    exports core.service;
    exports core;
    exports core.$__legacyfiles.test;
    opens core.$__legacyfiles.test to com.google.gson, javafx.fxml;
    exports core.$__testserver;
}