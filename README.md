# prog2-b03

## Blatt 03

Zu Blatt 03 gehoeren bei mir zwei getrennte Java-Projekte:

- [Calculator](https://github.com/AnsArbi006/prog2_ybel_calculator)
- [LockSnake](https://github.com/AnsArbi006/prog2_ybel_locksnake)

Ich habe die Projekte getrennt gelassen, weil es zwei eigene Vorgabe-Repositories waren.

## Inhalt

### Aufgabe 1: Calculator

Im Projekt `Calculator` habe ich die geforderten Stellen in `calculator.Calculator` ergänzt.

- Ich habe `Sub` als eigene Java-Klasse erstellt.
- Ich habe `Mul` als anonyme Klasse umgesetzt.
- Ich habe `Div` als Lambda-Ausdruck umgesetzt.
- Ich habe den `ActionListener` von einer anonymen Klasse in einen Lambda-Ausdruck umgebaut.

### Aufgabe 2.1: Code-Analyse

Für `LockSnake` habe ich ein UML-Klassendiagramm erstellt.

### Aufgabe 2.2: GameEngine und GameState

Im Projekt `LockSnake` habe ich `GameState` und `GameEngine` ergänzt.

- `GameState` speichert den aktuellen Spielzustand.
- In `tick()` habe ich die Spielregeln umgesetzt.
- `GameEngine` verwaltet den aktuellen `GameState`, verarbeitet Richtungsänderungen und führt Spiel-Ticks aus.

### Aufgabe 2.3: Observer-Pattern

Das Observer-Pattern habe ich im Code so umgesetzt:

- `GamePanel` gibt Eingaben als `Direction` an die `GameEngine` weiter.
- `GameEngine` aktualisiert nach Änderungen den `GameState`.
- `GamePanel` wird mit dem neuen `GameState` aktualisiert und zeichnet das Spielfeld neu.

### Aufgabe 2.4: JUnit

Für `GameState` habe ich JUnit-Tests erstellt.

- Ich habe mindestens 10 Tests erstellt.
- Ich teste damit Bewegungen, Wände, Pins, Selbstkollision, Gewinn- und Verlustzustände sowie den Initialzustand.

## Post Mortem

Bei B03 fand ich vor allem den zweiten Teil mit `LockSnake` anspruchsvoller als den `Calculator`. Im `Calculator` konnte ich die Unterschiede zwischen normaler Klasse, anonymer Klasse und Lambda-Ausdruck nach und nach gut nachvollziehen. Besonders bei den Lambda-Ausdrücken habe ich im Verlauf der Aufgabe gemerkt, dass ich besser verstanden habe, wie sie aufgebaut sind und warum sie an manchen Stellen die kürzere Form einer Implementierung sind.

Etwas schwieriger war für mich am Anfang wieder das Thema `this`, vor allem bei Zuweisungen wie `this.level = level;`. Das war für mein Verständnis zuerst nicht ganz klar, weil Feldname und Parameter gleich heißen. Im Verlauf der Aufgabe wurde das aber verständlicher, und ich konnte besser einordnen, was damit gemeint ist.

Im größeren Teil mit `GameState` und `GameEngine` war für mich besonders wichtig, die Logik in `tick()` zu verstehen. Dort musste ich Schritt für Schritt nachvollziehen, welche Fälle geprüft werden: keine Richtung, Wand, Selbstkollision, Pins und Gewinnbedingung. Das war anfangs etwas unübersichtlich, wurde aber verständlicher, sobald ich die einzelnen Fälle getrennt betrachtet habe.

Außerdem habe ich bei diesem Blatt das Observer-Pattern besser verstanden. Vorher war mir das eher theoretisch bekannt, aber durch `GamePanel`, `GameEngine` und `GameState` konnte ich besser sehen, wie Eingaben, Zustandsänderungen und Aktualisierung der Anzeige zusammenhängen. Auch bei den JUnit-Tests für `GameState` habe ich mehr Sicherheit bekommen. Dabei war für mich hilfreich zu sehen, dass man gezielt kleine Spielsituationen aufbauen und dann genau prüfen kann, was im nächsten Schritt passieren soll.

Insgesamt habe ich bei B03 vor allem bei Lambda-Ausdrücken, beim Observer-Pattern und beim Testen von Spiellogik dazugelernt. Besonders hilfreich war für mich, die einzelnen Probleme nicht als einen großen Block zu sehen, sondern sie nacheinander zu lösen.
