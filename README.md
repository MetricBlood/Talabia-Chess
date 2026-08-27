# Talabia Chess

A two-player chess variant with a Swing graphical interface, written in Java. Built as a university software design project, with the emphasis on architecture rather than on the game itself.

## What it does

- Full two-player game loop with a clickable board
- Piece types with their own distinct movement rules
- Transformation mechanics, where pieces change type under defined conditions
- Save and load, so a match can be stopped and resumed later

## How it is built

The project follows the **Model-View-Controller** pattern, and the class names reflect it directly:

| Class | Responsibility |
|---|---|
| `GameData` | Game state — the model |
| `GameView` | The Swing interface, a `JFrame` with a grid of buttons and image icons for pieces |
| `GameController` | Mediates between the two, handling input and driving state changes |
| `Board` | Board representation and coordinate handling |
| `Piece` | Base piece behaviour, extended per piece type |
| `Hourglass` | The transformation mechanic |
| `GameSaveManager` | Writing and restoring game state |

That separation is what makes the piece rules testable on their own and the interface replaceable without touching them. The piece hierarchy uses **subclassing** for shared movement behaviour, with **composition and aggregation** modelling the relationship between the board, the pieces on it and the players who own them.

## Running it

The entry point is in `GameView`:

```bash
javac *.java
java GameView
```

The `Images/` directory must be alongside the compiled classes, as piece icons are loaded from there at runtime.

## Built with

Java, Swing, Java serialisation
