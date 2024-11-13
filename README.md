# BootApp

**BootApp** is an Android application developed as a test playground project to handle boot events
and perform specific tasks upon device startup. This project was created within a limited timeframe
of 3 hours for a test task.

## Features

- **Boot Event Handling:** Listens for device boot events and performs scheduled tasks.
- **ViewModel Architecture:** Uses `ViewModel` and `LiveData` for managing UI-related data in a
  lifecycle-conscious way.
- **Dependency Injection:** Utilizes [Dagger Hilt](https://dagger.dev/hilt/) for dependency
  injection.
- **Coroutines and Flow:**
  Implements [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)
  and [Flow](https://developer.android.com/kotlin/flow) for asynchronous programming.
- **WorkManager for Scheduled Tasks:**
  Uses [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)
  and `PeriodicWork` to schedule and manage background tasks related to boot events.

> **Note:** To track boot events, you need to reboot your device or use a cold boot if running on an
> emulator. A notification displays the timestamp of the last boot, with the intended goal of showing
> the difference between the two most recent boots; however, due to the time limitation, the feature
> remains incomplete.

## Project Structure

- **Languages:** Kotlin, Java
- **Build System:** Gradle

### Key Components

- **BootListViewModel:** Manages the UI-related data for the boot list.
- **BootCompletedReceiver:** Receives the boot completed broadcast and triggers necessary actions.
- **WorkManager with PeriodicWork:** Manages periodic tasks, ensuring they are executed even after
  device reboot.

## Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio)
- JDK 8 or higher

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/marin-hen/BootApp.git