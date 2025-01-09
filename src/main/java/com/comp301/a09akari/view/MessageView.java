package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MessageView implements FXComponent {
  private ClassicMvcController controller;

  public MessageView(ClassicMvcController controller) {
    this.controller = controller;
  }

  public Parent render() {
    Label victory;
    if (controller.getModel().isSolved()) {
      victory = new Label("Completed");
      victory.setStyle("-fx-font-size: 24px; -fx-text-fill: #43a047; -fx-font-weight: bold;");
    } else {
      victory = new Label("Incomplete");
      victory.setStyle("-fx-font-size: 24px; -fx-text-fill: #fa0f0f; -fx-font-weight: bold;");
    }

    Label puzzles = new Label(this.makeLabelString());
    puzzles.setStyle("-fx-font-size: 18px; -fx-text-fill: #cccccc;");

    HBox topLayout = new HBox(10);
    topLayout.setStyle("-fx-alignment: center;");
    topLayout.getChildren().add(puzzles);

    HBox layout = new HBox(20);
    layout.setStyle("-fx-alignment: center;");
    layout.getChildren().add(victory);

    VBox elts = new VBox(15);
    elts.getChildren().addAll(topLayout, layout);
    elts.setStyle("-fx-background-color: #2e2e2e; -fx-padding: 20px; -fx-alignment: center;");

    return elts;
  }

  public String makeLabelString() {
    return "Puzzle "
        + (this.controller.getModel().getActivePuzzleIndex() + 1)
        + " of "
        + (this.controller.getModel().getPuzzleLibrarySize());
  }
}
