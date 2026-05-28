# OOP News Reader

A simple Java Swing desktop application that fetches and displays the latest
news/reports from public web APIs. Built as an Object-Oriented Programming
course project to demonstrate the four OOP pillars - **encapsulation,
inheritance, polymorphism, and abstraction** - in a real, working program.

## Concept

Multiple news sources (Spaceflight News API, Hacker News API) are accessed
through a single common abstraction. Each source fetches and parses data
differently but returns the same `Article` type, so the UI handles every
source identically - polymorphism in action. No database and no API key
required.

## Tech

- Java 11 (uses the built-in `java.net.http.HttpClient`)
- Java Swing for the GUI
- Gson for JSON parsing
- Plain `javac` / `java` build (no build tool required)

## Status

Work in progress - built incrementally, one feature per pull request.

## Build & run

Requires a JDK 11+ (`java`/`javac` on your `PATH`). The Gson jar is bundled in
`lib/`, so no download is needed.

```bash
./build.sh   # compiles src/ -> out/
./run.sh     # builds, then launches the app
```

## Project layout

```
src/
 ├─ Main.java     application entry point
 ├─ model/        domain types and core abstractions
 ├─ source/       concrete news sources
 ├─ service/      aggregation / app services
 └─ ui/           Swing user interface
lib/              bundled dependencies (Gson)
```

