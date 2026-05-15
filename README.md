# B03 Übersicht

## Inhalt

Dieses Blatt besteht für mich aus zwei Teilen:

1. `Calculator`
2. `LockSnake`

## Aufgabe 1: Calculator

Im Projekt `Calculator` habe ich die geforderten Stellen in `calculator.Calculator` ergänzt.

- Ich habe `Sub` als eigene Java-Klasse erstellt. Diese Klasse implementiert das Interface `Operation`.
- Ich habe `Mul` als anonyme Klasse umgesetzt.
- Ich habe `Div` als Lambda-Ausdruck umgesetzt.
- Ich habe den `ActionListener` der `JComboBox` von einer anonymen Klasse in einen Lambda-Ausdruck umgebaut.

Damit habe ich in diesem Teil mit anonymen Klassen, Lambda-Ausdrücken und Methodenreferenzen gearbeitet.

## Aufgabe 2.1: Code-Analyse

Für `LockSnake` habe ich ein UML-Klassendiagramm erstellt, das die wichtigsten Klassen und ihre Beziehungen zeigt.

## Aufgabe 2.2: GameEngine und GameState

Im Projekt `LockSnake` habe ich `GameState` und `GameEngine` ergänzt.

- `GameState` speichert den aktuellen Spielzustand mit `Level`, `Snake`, `Pin`-Liste, `Status` und `pendingDirection`.
- In `tick()` habe ich die Spielregeln umgesetzt:
  - keine Bewegung ohne gesetzte Richtung
  - Verlust bei Verlassen des Spielfelds
  - Blockade bei Wänden
  - Verlust bei Selbstkollision
  - Pin-Blockade bei falscher Richtung oder bereits gesetztem Pin
  - Aktivierung eines Pins bei korrekter Richtung
  - Gewinn, wenn alle Pins gesetzt sind
- `GameEngine` verwaltet den aktuellen `GameState`, verarbeitet Richtungsänderungen und führt die Spiel-Ticks aus.

## Aufgabe 2.3: Observer-Pattern

Das Observer-Pattern habe ich im Code so umgesetzt:

- `GamePanel` gibt Eingaben als `Direction` an die `GameEngine` weiter.
- `GameEngine` aktualisiert nach Änderungen den `GameState`.
- `GamePanel` wird mit dem neuen `GameState` aktualisiert und zeichnet das Spielfeld neu.

## Aufgabe 2.4: JUnit

Für `GameState` habe ich JUnit-Tests erstellt.

- Ich habe mindestens 10 Tests erstellt.
- Ich teste damit Bewegungen, Wände, Pins, Selbstkollision, Gewinn- und Verlustzustände sowie den Initialzustand.
- In den Tests baue ich die Ausgangssituationen für `Level`, `Pin` und `Snake` gezielt selbst auf.
