package de.hsbi.lockgame.logic;

import static org.junit.jupiter.api.Assertions.*;

import de.hsbi.lockgame.model.CellType;
import de.hsbi.lockgame.model.Direction;
import de.hsbi.lockgame.model.Level;
import de.hsbi.lockgame.model.Pin;
import de.hsbi.lockgame.model.Position;
import de.hsbi.lockgame.model.Snake;
import java.util.List;
import org.junit.jupiter.api.Test;

class GameStateTest {

  @Test
  void constructorStoresGivenValues() {
    var level = level(4, 4, List.of(), List.of(), new Position(1, 1));
    var snake = new Snake(List.of(new Position(1, 1)));
    var pins = List.of(new Pin(new Position(2, 2), Pin.State.LOW, Direction.LEFT));

    var state = new GameState(level, snake, pins, GameState.Status.RUNNING, Direction.RIGHT);

    assertSame(level, state.level());
    assertSame(snake, state.snake());
    assertSame(pins, state.pins());
    assertEquals(GameState.Status.RUNNING, state.status());
    assertEquals(Direction.RIGHT, state.pendingDirection());
  }

  @Test
  void tickReturnsSameStateWhenGameIsNotRunning() {
    var state =
        new GameState(
            level(4, 4, List.of(), List.of(), new Position(1, 1)),
            new Snake(List.of(new Position(1, 1))),
            List.of(),
            GameState.Status.WON,
            Direction.RIGHT);

    assertSame(state, state.tick());
  }

  @Test
  void tickReturnsSameStateWhenDirectionIsNone() {
    var state =
        new GameState(
            level(4, 4, List.of(), List.of(), new Position(1, 1)),
            new Snake(List.of(new Position(1, 1))),
            List.of(),
            GameState.Status.RUNNING,
            Direction.NONE);

    assertSame(state, state.tick());
  }

  @Test
  void tickMovesSnakeOneStepWhenPathIsClear() {
    var state =
        new GameState(
            level(5, 5, List.of(), List.of(), new Position(1, 1)),
            new Snake(List.of(new Position(1, 1))),
            List.of(),
            GameState.Status.RUNNING,
            Direction.RIGHT);

    var next = state.tick();

    assertEquals(new Position(2, 1), next.snake().head());
    assertEquals(List.of(new Position(2, 1), new Position(1, 1)), next.snake().body());
    assertEquals(Direction.RIGHT, next.pendingDirection());
    assertEquals(GameState.Status.RUNNING, next.status());
  }

  @Test
  void tickLosesWhenNextStepLeavesBoard() {
    var state =
        new GameState(
            level(2, 2, List.of(), List.of(), new Position(0, 0)),
            new Snake(List.of(new Position(0, 0))),
            List.of(),
            GameState.Status.RUNNING,
            Direction.LEFT);

    var next = state.tick();

    assertEquals(GameState.Status.LOST_OUT_OF_BOUNDS, next.status());
  }

  @Test
  void tickBlocksAtWallAndClearsDirection() {
    var wall = new Position(2, 1);
    var state =
        new GameState(
            level(5, 5, List.of(wall), List.of(), new Position(1, 1)),
            new Snake(List.of(new Position(1, 1))),
            List.of(),
            GameState.Status.RUNNING,
            Direction.RIGHT);

    var next = state.tick();

    assertEquals(new Position(1, 1), next.snake().head());
    assertEquals(Direction.NONE, next.pendingDirection());
    assertEquals(GameState.Status.RUNNING, next.status());
  }

  @Test
  void tickLosesOnSelfCollision() {
    var state =
        new GameState(
            level(5, 5, List.of(), List.of(), new Position(1, 1)),
            new Snake(List.of(new Position(1, 1), new Position(2, 1))),
            List.of(),
            GameState.Status.RUNNING,
            Direction.RIGHT);

    var next = state.tick();

    assertEquals(GameState.Status.LOST_SELF_COLLISION, next.status());
  }

  @Test
  void tickBlocksOnAlreadySetPin() {
    var pin = new Pin(new Position(2, 1), Pin.State.HIGH, Direction.RIGHT);
    var state =
        new GameState(
            level(5, 5, List.of(), List.of(pin), new Position(1, 1)),
            new Snake(List.of(new Position(1, 1))),
            List.of(pin),
            GameState.Status.RUNNING,
            Direction.RIGHT);

    var next = state.tick();

    assertEquals(Direction.NONE, next.pendingDirection());
    assertEquals(new Position(1, 1), next.snake().head());
    assertTrue(next.pins().getFirst().state().isSet());
  }

  @Test
  void tickBlocksOnPinFromWrongDirection() {
    var pin = new Pin(new Position(2, 1), Pin.State.LOW, Direction.LEFT);
    var state =
        new GameState(
            level(5, 5, List.of(), List.of(pin), new Position(1, 1)),
            new Snake(List.of(new Position(1, 1))),
            List.of(pin),
            GameState.Status.RUNNING,
            Direction.RIGHT);

    var next = state.tick();

    assertEquals(Direction.NONE, next.pendingDirection());
    assertEquals(Pin.State.LOW, next.pins().getFirst().state());
    assertEquals(new Position(1, 1), next.snake().head());
  }

  @Test
  void tickActivatesLowPinFromCorrectDirectionWithoutMovingSnake() {
    var pin = new Pin(new Position(2, 1), Pin.State.LOW, Direction.RIGHT);
    var state =
        new GameState(
            level(5, 5, List.of(), List.of(pin), new Position(1, 1)),
            new Snake(List.of(new Position(1, 1))),
            List.of(pin),
            GameState.Status.RUNNING,
            Direction.RIGHT);

    var next = state.tick();

    assertEquals(new Position(1, 1), next.snake().head());
    assertEquals(Pin.State.HIGH, next.pins().getFirst().state());
    assertEquals(Direction.NONE, next.pendingDirection());
    assertEquals(GameState.Status.WON, next.status());
  }

  @Test
  void tickWinsWhenLastRemainingPinGetsActivated() {
    var firstPin = new Pin(new Position(3, 3), Pin.State.HIGH, Direction.UP);
    var lastPin = new Pin(new Position(2, 1), Pin.State.LOW, Direction.RIGHT);
    var pins = List.of(firstPin, lastPin);
    var state =
        new GameState(
            level(5, 5, List.of(), pins, new Position(1, 1)),
            new Snake(List.of(new Position(1, 1))),
            pins,
            GameState.Status.RUNNING,
            Direction.RIGHT);

    var next = state.tick();

    assertEquals(GameState.Status.WON, next.status());
    assertTrue(next.pins().stream().allMatch(pin -> pin.state().isSet()));
  }

  private static Level level(
      int width,
      int height,
      List<Position> walls,
      List<Pin> pins,
      Position snakeStart) {
    var cells = new CellType[width][height];
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        cells[x][y] = CellType.EMPTY;
      }
    }
    for (var wall : walls) {
      cells[wall.x()][wall.y()] = CellType.WALL;
    }
    for (var pin : pins) {
      cells[pin.position().x()][pin.position().y()] = CellType.PIN_SLOT;
    }
    return new Level(width, height, cells, pins, snakeStart);
  }
}
