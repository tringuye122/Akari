package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ControlView implements FXComponent {
  private ClassicMvcController controller;

  public ControlView(ClassicMvcController controller) {
    this.controller = controller;
  }

  public Parent render() {
    HBox layout = new HBox(30);
    layout.setStyle("-fx-background-color: #444444; -fx-alignment: center;");

    Button prevPuzzle = new Button("\u2190");
    prevPuzzle.setStyle(
        "-fx-background-color: #222222; -fx-text-fill: #ffffff; -fx-font-size: 16px;");
    prevPuzzle.setOnAction(event -> controller.clickPrevPuzzle());
    layout.getChildren().add(prevPuzzle);

    Label title = new Label("Akari Game");
    title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #f0f0f0;");
    layout.getChildren().add(title);

    Button nextPuzzle = new Button("\u2192");
    nextPuzzle.setStyle(
        "-fx-background-color: #222222; -fx-text-fill: #ffffff; -fx-font-size: 16px;");
    nextPuzzle.setOnAction(event -> controller.clickNextPuzzle());
    layout.getChildren().add(nextPuzzle);

    VBox header = new VBox(10);
    header.getStyleClass().add("header");
    header.getChildren().add(layout);

    Button clear = new Button("Reset");
    clear.setStyle("-fx-background-color: #ff6600; -fx-text-fill: white;");
    clear.setOnAction(event -> controller.clear());

    Button random = new Button("Random");
    random.setStyle("-fx-background-color: #0066cc; -fx-text-fill: white;");
    random.setOnAction(event -> controller.clickRandPuzzle());

    HBox bottom = new HBox(20);
    bottom.setAlignment(Pos.CENTER);
    bottom.setSpacing(20);
    bottom.getChildren().addAll(clear, random);
    header.getChildren().add(bottom);

    return header;
  }
}
