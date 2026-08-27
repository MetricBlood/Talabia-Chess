# Talabia Chess

A two-player chess variant with a graphical interface, written in Java. Built as a university software design project with the emphasis on architecture rather than on the game itself.

## What it does

- Full two-player game loop with a Swing interface
- Piece types with their own distinct movement rules
- Transformation mechanics, where pieces change type under defined conditions
- Save and load, with game state written out through serialisation so a match can be resumed later

## How it is built

The project follows the **Model-View-Controller** pattern, keeping game rules and board state separate from the interface that draws them. That separation is what makes the piece behaviour testable on its own and the interface replaceable without touching the rules.

The piece hierarchy uses **subclassing** for shared movement behaviour, **delegation** for rule checks that belong elsewhere, and **composition and aggregation** to model the relationship between the board, the pieces on it and the players who own them.

## Running it

Open the project in an IDE with a JDK installed, or compile and run from the command line:

```bash
javac *.java
java Main
```

## Built with

Java, Swing
