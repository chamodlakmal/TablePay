
# Table Pay

A modern Android application developed using Kotlin and Jetpack Compose, designed to manage table billing for restaurants. This app simulates a real-world waiter/table experience and is built using MVVM architecture. It features Room for local data storage, Dagger 2 for dependency injection, and Retrofit for network communication. Key highlights include paginated table listings, offline caching, and the ability to view and mark bills as paid.

## Features

- **Paginated Table List:** Seamlessly browse through a list of restaurant tables with pagination support.
- **Table Bill Details:** View each cart's detailed billing, including products, quantity, and total cost.
- **Offline Caching:** Previously fetched data is cached locally using Room for offline accessibility.
- **Modern Android Practices:** Built with Jetpack Compose, Kotlin Coroutines, and a modular MVVM structure.
- **Clean Architecture:** Follows domain-driven modular design with separation of concerns.


## Screenshots

<table>
  <tr>
    <th align="center"><b>Table List Screen</b></th>
    <th align="center"><b>Bill Details Screen (Non Paid)</b></th>
    <th align="center"><b>Bill Details Screen (Paid)</b></th>
  </tr>
  <tr>
    <td><img src="https://github.com/chamodlakmal/TablePay/blob/main/screenshots/Table%20List.png" width="400"></td>
    <td><img src="https://github.com/chamodlakmal/TablePay/blob/main/screenshots/Bill%20Details%20Non%20Paid.png" width="400"></td>
    <td><img src="https://github.com/chamodlakmal/TablePay/blob/main/screenshots/Bill%20Details%20Paid.png" width="400"></td>
  </tr>
</table>

## Installation

To run this application locally:

1. **Clone the Repository:** ```git clone https://github.com/chamodlakmal/TablePay.git```
2. **Navigate to the Project Directory:**
3. **Open with Android Studio:**
- Launch Android Studio.
- Select 'Open an existing project' and choose the cloned directory.
4. **Build the Project:**
- Allow Android Studio to synchronize and build the project. Ensure all dependencies are resolved.
5. **Run the App:**
- Connect an Android device or start an emulator.
- Click on the 'Run' button.

## Dependencies

- **Kotlin:** Primary language for Android development.
- **Jetpack Compose:** Declarative UI framework.
- **Room:** Local data storage with offline support.
- **Retrofit:** Networking library for API calls.
- **Dagger 2:** Dependency injection.
- **Coroutines + Flow:** For async data streams and background processing.
- **Paging 3:** Efficient paging and caching of large datasets.
- **Coil:** For image loading in Jetpack Compose.

## Folder Structure
```
TablePay/
│-- app/
│   │-- src/
│   │   │-- main/
│   │   │   │-- java/lk/chamiviews/tablepay/
│   │   │   │   │-- data/       # Handles API calls and database interactions
│   │   │   │   │-- di/         # Hilt dependency injection setup
│   │   │   │   │-- domain/     # Business logic and use cases
│   │   │   │   │-- presentation/ # UI-related components, ViewModels, and navigation
│   │   │   │-- res/            # Resources such as layouts, drawables, etc.
│   │   │-- AndroidManifest.xml # Application configuration
│-- build.gradle.kts            # Project-level Gradle configuration
│-- settings.gradle.kts         # Gradle settings
│-- README.md                   # Project documentation
```

## Architecture and Design Decisions

- **MVVM + Clean Architecture:** For better separation of concerns, testability, and maintainability. Divided into three layers: Data, Domain, and Presentation.
- **Dagger 2:** Used for dependency injection to manage app-level and feature-level components.
- **Paging 3 Library:** To handle large datasets efficiently and with lazy loading.
- **Room Database:** For offline support and local caching.
- **Retrofit:** For API calls (simulated with https://dummyjson.com).
- **Coil:** Chosen for fast, lightweight image loading with Compose integration.

## Known Limitations / Areas for Improvement
- **No Real Payment Integration:** Currently, "Mark as Paid" is a simulated action with local DB update.
- **No Testing:** No unit tests or UI tests have been written for this version yet.
- **UI/UX Enhancements:** The app follows a basic layout; animations, themes, and polish can be added.
- **Error Handling:** Missing graceful UI for API and database failures.

## Contact

For any inquiries or feedback, please contact [Chamod Lakmal](https://github.com/chamodlakmal).

