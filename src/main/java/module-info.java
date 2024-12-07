module core {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires com.google.gson;


    opens core.view to javafx.fxml;
    opens core.controller to javafx.fxml;
    opens core.service to javafx.fxml;
    opens core.model to javafx.fxml;
    opens core.util to javafx.fxml;
    opens core.model.dto to com.google.gson;
    opens core.model.dto.request to com.google.gson;
    opens core.model.dto.response to com.google.gson;



    exports core.controller;
    exports core.model;
    exports core.model.dto;
    exports core.view;
    exports core.service;
    exports core;

}