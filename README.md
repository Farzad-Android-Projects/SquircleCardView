# SquircleCardView

<p align="center">
  <strong>A lightweight Android ViewGroup for beautiful continuous corners.</strong>
</p>

<p align="center">
  Create smooth, customizable squircle cards with independent corner radii.
</p>

<p align="center">

![Android](https://img.shields.io/badge/Android-API%2021%2B-3DDC84?style=for-the-badge\&logo=android\&logoColor=white)
![Java](https://img.shields.io/badge/Java-8%2B-orange?style=for-the-badge\&logo=openjdk\&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-7%2B-02303A?style=for-the-badge\&logo=gradle\&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

</p>

---

## Overview

**SquircleCardView** is a lightweight Android `ViewGroup` that provides smooth,
continuous-corner shapes instead of traditional circular rounded corners.

It is designed for developers who want more control over the visual shape of
cards, containers, list items, panels, and other UI components.

Unlike a standard rounded rectangle, a squircle provides a smoother transition
between straight edges and corners.

```text
Traditional Rounded Corner

     ┌──────────────┐
   ╭─┘              └─╮
   │                  │
   ╰─┐              ┌─╯
     └──────────────┘


Squircle / Continuous Corner

      ╭──────────────╮
    ╭─╯              ╰─╮
   │                    │
    ╰─╮              ╭─╯
      ╰──────────────╯
```

---

# Features

* Independent radius for every corner
* Smooth continuous-corner geometry
* Adjustable Bézier control factor
* XML configuration
* Runtime configuration
* Custom background color
* Child-view clipping
* Lightweight `FrameLayout`-based implementation
* No `CardView` dependency
* No third-party UI dependencies
* Java API
* Android API 21+
* Suitable for RecyclerView items
* Suitable for reusable UI containers
* Small and focused API
* Native Android Canvas rendering

---

# Installation

## JitPack

Add JitPack to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
```

Then add SquircleCardView to your module:

```kotlin
dependencies {
    implementation("com.github.YOUR_USERNAME:SquircleCardView:1.0.0")
}
```

Replace `YOUR_USERNAME` with your GitHub username.

> **Important:** Use the exact dependency coordinate and version displayed by
> JitPack for your repository.

---

# Quick Start

Add the component to your XML layout:

```xml

<io.github.farzadski.squirclecardview.SquircleCardView
    xmlns:app="http://schemas.android.com/apk/res-auto" android:id="@+id/card"
    android:layout_width="match_parent" android:layout_height="200dp"
    app:squircleTopLeftRadius="16dp" app:squircleTopRightRadius="24dp"
    app:squircleBottomRightRadius="24dp" app:squircleBottomLeftRadius="16dp"
    app:squircleControlFactor="0.90"
    app:squircleBackgroundColor="@android:color/white" />
```

That's it.

---

# XML Configuration

Every corner can be configured independently.

```xml
app:squircleTopLeftRadius="16dp"app:squircleTopRightRadius="24dp"app:squircleBottomRightRadius="32dp"app:squircleBottomLeftRadius="8dp"
```

You can create completely asymmetric shapes:

```xml

<io.github.farzadski.squirclecardview.SquircleCardView
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent" android:layout_height="180dp"

    app:squircleTopLeftRadius="8dp" app:squircleTopRightRadius="40dp"
    app:squircleBottomRightRadius="16dp" app:squircleBottomLeftRadius="32dp"

    app:squircleControlFactor="0.90" app:squircleBackgroundColor="#FFFFFF" />
```

## XML Radius Units

When using XML, corner radius attributes are Android `dimension` values.

You should normally specify them using `dp`:

```xml
app:squircleTopLeftRadius="16dp"app:squircleTopRightRadius="24dp"app:squircleBottomRightRadius="32dp"app:squircleBottomLeftRadius="12dp"
```

Android automatically converts these values to pixels internally.

Therefore:

```text
XML
16dp
 │
 ▼
Android dimension conversion
 │
 ▼
Pixel value used by the view
```

---

# Control Factor

The `squircleControlFactor` controls the Bézier curve used to create the
continuous corner.

```xml
app:squircleControlFactor="0.90"
```

Different values produce different visual characteristics.

|  Value | Character                    |
|-------:|------------------------------|
| `0.55` | More compact                 |
| `0.75` | Soft transition              |
| `0.90` | Smooth continuous transition |
| `1.00` | Maximum curve influence      |

## Visual Comparison

<p align="center">
  <img
    src="docs/images/squircle-factor-comparison.svg"
    alt="Squircle control factor comparison"
    width="800">
</p>

### 0.55 — Compact

```xml
app:squircleControlFactor="0.55"
```

Creates a more compact corner transition.

### 0.75 — Soft

```xml
app:squircleControlFactor="0.75"
```

Creates a softer transition while retaining a defined corner.

### 0.90 — Recommended Starting Point

```xml
app:squircleControlFactor="0.90"
```

Provides a smooth continuous-looking transition and is a good starting point for
most designs.

### 1.00 — Maximum

```xml
app:squircleControlFactor="1.00"
```

Provides the maximum Bézier control influence supported by the component.

> **Tip:** Start with `0.90` and adjust the value according to your design.

---

# Using Child Views

SquircleCardView is a `FrameLayout`, so you can place normal Android views
inside it.

```xml

<io.github.farzadski.squirclecardview.SquircleCardView
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent" android:layout_height="220dp"
    android:padding="24dp" app:squircleTopLeftRadius="32dp"
    app:squircleTopRightRadius="32dp" app:squircleBottomRightRadius="32dp"
    app:squircleBottomLeftRadius="32dp"
    app:squircleBackgroundColor="@android:color/white">

    <TextView android:layout_width="wrap_content"
        android:layout_height="wrap_content" android:text="SquircleCardView"
        android:textSize="20sp" />

</io.github.farzadski.squirclecardview.SquircleCardView>
```

Child content is clipped to the same squircle path.

This means images, text, buttons, icons, and other child views remain inside the
custom shape.

---

# Runtime Configuration

All major properties can also be configured programmatically.

## Important: Java Radius Units

**Java radius setters and getters use pixels (`px`).**

For example:

```java
card.setTopLeftRadius(32f);
```

means:

```text
32 pixels
```

It does **not** mean:

```text
32dp
```

This distinction is important when writing density-independent Android UI.

### Recommended Approach

If your design value is specified in `dp`, convert it to pixels before passing
it to the Java API.

```java
float radiusPx = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        16,
        getResources().getDisplayMetrics()
);

card.

setTopLeftRadius(radiusPx);
```

This makes `16dp` work correctly across different screen densities.

---

# Complete Java Example

```java
import android.graphics.Color;
import android.util.TypedValue;

import io.github.farzadski.squirclecardview.SquircleCardView;

SquircleCardView card = findViewById(R.id.card);

float topLeft = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        16,
        getResources().getDisplayMetrics()
);

float topRight = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        24,
        getResources().getDisplayMetrics()
);

float bottomRight = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        32,
        getResources().getDisplayMetrics()
);

float bottomLeft = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        12,
        getResources().getDisplayMetrics()
);

card.

setTopLeftRadius(topLeft);
card.

setTopRightRadius(topRight);
card.

setBottomRightRadius(bottomRight);
card.

setBottomLeftRadius(bottomLeft);

card.

setControlFactor(0.90f);

card.

setCardBackgroundColor(Color.WHITE);
```

---

# API Reference

## Corner Radius

All corner-radius values exposed through the **Java API are pixels (`px`)**.

### Top Left

Set:

```java
card.setTopLeftRadius(radiusPx);
```

Get:

```java
float radiusPx = card.getTopLeftRadius();
```

Example:

```java
float radiusPx = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        16,
        getResources().getDisplayMetrics()
);

card.

setTopLeftRadius(radiusPx);
```

---

## Top Right

Set:

```java
card.setTopRightRadius(radiusPx);
```

Get:

```java
float radiusPx = card.getTopRightRadius();
```

Example:

```java
float radiusPx = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        24,
        getResources().getDisplayMetrics()
);

card.

setTopRightRadius(radiusPx);
```

---

## Bottom Right

Set:

```java
card.setBottomRightRadius(radiusPx);
```

Get:

```java
float radiusPx = card.getBottomRightRadius();
```

---

## Bottom Left

Set:

```java
card.setBottomLeftRadius(radiusPx);
```

Get:

```java
float radiusPx = card.getBottomLeftRadius();
```

---

# Radius Units at a Glance

| API              | Unit                     | Example       |
|------------------|--------------------------|---------------|
| XML radius       | `dp` / Android dimension | `16dp`        |
| Java setter      | `px`                     | `16f`         |
| Java getter      | `px`                     | `float`       |
| Control factor   | Unitless                 | `0.90f`       |
| Background color | ARGB integer             | `Color.WHITE` |

### XML

```xml
app:squircleTopLeftRadius="16dp"
```

### Java

```java
float radiusPx = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        16,
        getResources().getDisplayMetrics()
);

card.

setTopLeftRadius(radiusPx);
```

> **Rule of thumb:** Use `dp` in XML. Convert `dp` to `px` when using the Java
> radius API.

---

# Control Factor API

Set:

```java
card.setControlFactor(0.90f);
```

Get:

```java
float controlFactor = card.getControlFactor();
```

The value is constrained to the supported range:

```text
0.0 <= controlFactor <= 1.0
```

---

# Background Color API

Set:

```java
card.setCardBackgroundColor(Color.WHITE);
```

Get:

```java
int color = card.getCardBackgroundColor();
```

You can also use any Android color resource:

```java
card.setCardBackgroundColor(
        getResources().

getColor(R.color.my_color)
);
```

---

# XML Attributes

| Attribute                   | Type        | Description                |
|-----------------------------|-------------|----------------------------|
| `squircleTopLeftRadius`     | `dimension` | Top-left corner radius     |
| `squircleTopRightRadius`    | `dimension` | Top-right corner radius    |
| `squircleBottomRightRadius` | `dimension` | Bottom-right corner radius |
| `squircleBottomLeftRadius`  | `dimension` | Bottom-left corner radius  |
| `squircleControlFactor`     | `float`     | Bézier control factor      |
| `squircleBackgroundColor`   | `color`     | Background color           |

---

# RecyclerView

SquircleCardView can be used as the root container of a RecyclerView item.

## XML

```xml

<io.github.farzadski.squirclecardview.SquircleCardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"

    android:id="@+id/root" android:layout_width="match_parent"
    android:layout_height="wrap_content"

    app:squircleTopLeftRadius="20dp" app:squircleTopRightRadius="20dp"
    app:squircleBottomRightRadius="20dp" app:squircleBottomLeftRadius="20dp"
    app:squircleControlFactor="0.90"
    app:squircleBackgroundColor="@android:color/white">

    <!-- RecyclerView item content -->

</io.github.farzadski.squirclecardview.SquircleCardView>
```

## ViewHolder

```java
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import io.github.farzadski.squirclecardview.SquircleCardView;

public static class ViewHolder extends RecyclerView.ViewHolder {

    final SquircleCardView root;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        root = itemView.findViewById(R.id.root);
    }
}
```

---

# Why SquircleCardView?

Traditional rounded containers generally rely on circular corner geometry.

SquircleCardView instead builds a custom path using cubic Bézier curves.

This provides:

```text
Independent Radii
        +
Custom Curve Control
        +
Child Clipping
        +
Native Canvas Rendering
        =
Flexible Modern Container
```

The component is useful for:

* Cards
* Product items
* Profile containers
* Dashboard widgets
* Media cards
* RecyclerView items
* Settings containers
* Feature panels
* Custom UI components

---

# Architecture

```text
                    SquircleCardView
                            │
                            ▼
                     Corner Radii
                            │
                            ▼
                     Radius Clamping
                            │
                            ▼
                      Custom Path
                            │
               ┌────────────┴────────────┐
               ▼                         ▼
        Background Paint            Child Views
               │                         │
               │                         │
               └────────────┬────────────┘
                            ▼
                          Canvas
                            │
                            ▼
                     Clipped Shape
```

The view generates and maintains a custom `Path` based on its dimensions, corner
radii, and control factor.

Child views are clipped against that same path during drawing.

---

# Rendering Model

The component uses Android's native drawing APIs:

```text
FrameLayout
    │
    ├── Path
    │
    ├── Paint
    │
    └── Canvas
```

The general rendering process is:

```text
View Size
   │
   ▼
Clamp Corner Radii
   │
   ▼
Build Cubic Bézier Path
   │
   ├───────────────┐
   ▼               ▼
Draw Background   Clip Children
   │               │
   └───────┬───────┘
           ▼
       Final Shape
```

---

# Performance

SquircleCardView is designed to remain lightweight.

The component:

* Uses Android's native drawing APIs
* Avoids external rendering libraries
* Reuses the generated `Path`
* Recalculates geometry when required
* Clips child content to the generated shape
* Does not introduce image-processing or layout-heavy dependencies

For typical cards, containers, and RecyclerView items, it can be used directly
in normal Android layouts.

As with any custom ViewGroup, extremely large numbers of complex views should
still be profiled according to the application's actual rendering workload.

---

# Dependencies

SquircleCardView intentionally keeps dependencies minimal.

The library does **not** require:

* `CardView`
* Material Components
* Third-party UI libraries
* Image loading libraries
* Networking libraries
* Database libraries

The core component is built using Android's native:

```text
FrameLayout
Path
Paint
Canvas
```

---

# Requirements

| Requirement | Version  |
|-------------|----------|
| Android     | API 21+  |
| Java        | Java 8+  |
| AndroidX    | Required |
| Gradle      | 7+       |

Kotlin is **not required**.

---

# Sample Project

The repository contains a sample Android application demonstrating:

* Basic SquircleCardView usage
* Independent corner radii
* Different corner combinations
* Control factor configuration
* Nested child views
* Runtime configuration
* RecyclerView integration

Build the sample application with:

```bash
./gradlew :app:assembleDebug
```

Run tests with:

```bash
./gradlew test
```

---

# Project Structure

```text
SquircleCardView/
│
├── app/
│   └── Sample Android application
│
├── squirclecardview/
│   └── Android library
│       │
│       ├── src/main/java/
│       │   └── io/github/farzadski/
│       │       └── squirclecardview/
│       │           └── SquircleCardView.java
│       │
│       └── src/main/res/
│           └── values/
│               └── attrs.xml
│
├── docs/
│   └── images/
│       └── squircle-factor-comparison.svg
│
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── LICENSE
└── README.md
```

---

# GitHub Publishing

The recommended release workflow is:

```bash
git init
git add .
git commit -m "Initial release"
```

Add your GitHub repository:

```bash
git remote add origin https://github.com/YOUR_USERNAME/SquircleCardView.git
```

Use `main` as the default branch:

```bash
git branch -M main
```

Push:

```bash
git push -u origin main
```

Create the first release tag:

```bash
git tag v1.0.0
```

Push the tag:

```bash
git push origin v1.0.0
```

JitPack can then build the tagged release.

---

# Versioning

SquircleCardView follows semantic versioning where practical:

```text
MAJOR.MINOR.PATCH
```

For example:

```text
1.0.0
```

Where:

```text
1 = major version
0 = minor version
0 = patch version
```

A typical release workflow is:

```bash
git tag v1.0.0
git push origin v1.0.0
```

---

# Roadmap

Possible future improvements include:

* [ ] Stroke support
* [ ] Stroke width
* [ ] Stroke color
* [ ] Gradient backgrounds
* [ ] Per-corner control factors
* [ ] Shadow/elevation support
* [ ] Additional shape presets
* [ ] More extensive instrumentation tests
* [ ] Jetpack Compose interoperability

The roadmap is subject to change.

---

# Contributing

Contributions are welcome.

## 1. Fork the repository

Create your own fork of the project.

## 2. Create a feature branch

```bash
git checkout -b feature/my-feature
```

## 3. Make your changes

Keep changes focused and maintain the existing API style.

## 4. Run tests

```bash
./gradlew test
```

## 5. Build the project

```bash
./gradlew build
```

## 6. Commit

```bash
git commit -m "Add my feature"
```

## 7. Push

```bash
git push origin feature/my-feature
```

## 8. Open a Pull Request

Please provide a clear description of:

* What changed
* Why it changed
* How it was tested
* Any API changes

---

# Bug Reports

When reporting a bug, please include:

* Android version
* Device or emulator
* Library version
* Reproduction steps
* Expected behavior
* Actual behavior
* Minimal reproduction code if possible

For rendering issues, screenshots are especially helpful.

---

# License

Copyright © 2026 Farzad Sobhani Kazemi

Licensed under the MIT License.

See [LICENSE](LICENSE) for details.

---

# Author

**Farzad Sobhani Kazemi**

Backend Engineering · Distributed Systems · Data Science · Machine Learning ·
Android

---

<p align="center">

### SquircleCardView

**Beautiful corners. Simple API. Native Android.**

<br>

If you find this library useful, consider giving it a ⭐ on GitHub.

</p>
