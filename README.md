# StudentIDAssignment2

## Overview
This is an Android application developed for the NIT3213 assignment. The app allows users to log in to the Footscray API, retrieve dashboard entities, and view the details of a selected entity.

## Features
- Login using student credentials.
- Connect to the Footscray API using Retrofit.
- Retrieve and display dashboard entities.
- View details for a selected entity.
- Dependency injection using Hilt.
- Unit tests for login request data.

## Technologies
- Kotlin
- Android Studio
- Android SDK
- Retrofit and Gson
- Hilt dependency injection
- JUnit

## API
Base URL: `https://nit3213apinew.onrender.com/`

Authentication endpoint: `/footscray/auth`

Dashboard endpoint: `/dashboard/{keypass}`

## Requirements
- Android Studio
- A compatible Android SDK
- Internet connection

## Build and Run
1. Clone or download this repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync and download dependencies.
4. Connect an Android device or start an Android emulator.
5. Run the app using the Run button in Android Studio.
6. Log in using the credentials specified by the assignment.

## Testing
The project includes JUnit unit tests for the login request model. In Android Studio, open the `ExampleUnitTest` file and select **Run** to execute the tests.
