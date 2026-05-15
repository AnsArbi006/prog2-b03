package de.hsbi.lockgame.logic;

import de.hsbi.lockgame.model.*;
import java.util.List;

public final class GameState {
// Diese Felder speichern den aktuellen Spielzustand dauerhaft im GameState-Objekt.
// private = nur die Klasse GameState greift direkt darauf zu
// final = der Verweis wird nach dem Erzeugen nicht mehr neu gesetzt
// Level, Snake, Pins, Status und pendingDirection sind die zentralen Teile des Spielzustands
  private final Level level;
  private final Snake snake;
  private final List<Pin> pins;
  private final Status status;
  private final Direction pendingDirection;

  public GameState(
      Level level, Snake snake, List<Pin> pins, Status status, Direction pendingDirection) {
    // TODO: lege einen neuen GameState mit den übergebenen Informationen an
    // Speichert die übergebenen Werte als aktuellen Spielzustand im GameState
    this.level = level;
    this.snake = snake;
    this.pins = pins;
    this.status = status;
    this.pendingDirection = pendingDirection;
  }

  public Level level() {
    // TODO: Getter
    return level;
  }

  public Snake snake() {
    // TODO: Getter
    return snake;
  }

  public List<Pin> pins() {
    // TODO: Getter
    return pins;
  }

  public Status status() {
    // TODO: Getter
    return status;
  }

  public Direction pendingDirection() {
    // TODO: Getter
    return pendingDirection;
  }

  public GameState tick() {
    // TODO: diese Methode lässt das Spiel einen Schritt laufen (berechnet den Spielzustand im
    // nächsten Schritt)

    // TODO: early exit: wenn das Spiel nicht läuft oder keine Blickrichtung gesetzt ist: keine
    // Änderung
    if (!status.isRunning() || pendingDirection == Direction.NONE) {
      return this;
    }
    var nextHead = snake.nextHead(pendingDirection);
    // TODO: prüfe die folgenden Bedingungen:
    // (a) Schlange würde das Spielfeld verlassen: Spiel verloren
    if (!level.isInside(nextHead)) {
      return new GameState(level, snake, pins, Status.LOST_OUT_OF_BOUNDS, pendingDirection);
    }
    // (b) Schlange würde in ein Wandelement gehen: Blockiert (keine Bewegung, Blickrichtung "none")
    if (level.cellAt(nextHead) == CellType.WALL) {
      return new GameState(level, snake, pins, status, Direction.NONE);
    }
    // (c) Schlange beisst sich: Spiel verloren
    if (snake.occupies(nextHead)) {
      return new GameState(level, snake, pins, Status.LOST_SELF_COLLISION, pendingDirection);
    }
    // (d) Schlange würde auf einen Pin gehen (Pin bereits gesetzt oder Schlange kommt nicht in der
    // Aktivierungsrichtung): Blockiert (keine Bewegung, Blickrichtung "none")
    Pin pinAtNextHead = null;
    for (Pin pin : pins) {
      if (pin.position().equals(nextHead)) {
        pinAtNextHead = pin;
        break;
      }
    }

    if (pinAtNextHead != null) {
      if (pinAtNextHead.state().isSet()
          || pinAtNextHead.activationDirection() != pendingDirection) {
        return new GameState(level, snake, pins, status, Direction.NONE);
      }

      // Feste Hilfsvariable, damit der gefundene Pin im Lambda benutzt werden darf.
      final Pin matchedPin = pinAtNextHead;

      // Baut eine neue Pin-Liste, in der genau der getroffene Pin auf HIGH gesetzt wird.
      var updatedPins =
          pins.stream()
              .map(pin -> pin == matchedPin ? pin.withState(Pin.State.HIGH) : pin)
              .toList();

      // Prüft, ob jetzt alle Pins aktiviert sind.
      boolean allPinsSet = updatedPins.stream().allMatch(pin -> pin.state().isSet());

      return new GameState(
          level,
          snake,
          updatedPins,
          allPinsSet ? Status.WON : status,
          Direction.NONE);
    }

    // Wenn kein Sonderfall greift, bewegt sich die Schlange normal einen Schritt weiter.
    return new GameState(level, snake.grow(pendingDirection), pins, status, pendingDirection);
  }

  public enum Status {
    RUNNING,
    WON,
    LOST_SELF_COLLISION,
    LOST_OUT_OF_BOUNDS;

    public boolean isRunning() {
      return this == RUNNING;
    }
  }
}
