# Rick And Morty API Demo App

The purpose of this project is to demonstrate an efficient usage of Rick and Morty REST API
in the Android application.
<br><br>
Please refer to the official API documentation here:
https://rickandmortyapi.com/
<br><br>
For app building please use the latest stable version of Android Studio.
<br>
As of Jan 14, 2023 it's Electric Eel 2022.1.1
<br>
<br>

## Used technologies

- Kotlin, Jetpack Compose
- Hilt
- ViewModels, Coroutines, Flows, Paging
- Ktor (http networking, caching)
- Coil (image loading)
- Accompanist (animated navigation, etc)

<br>
<br>

## Application features

- displaying list of characters with paging support and filtering by name 
- individual character screen with details and a list of episodes where that character appeared 
- displaying locations with paging support and filtering by name
- displaying location with all the residents
- displaying episodes with paging
- displaying individual episode with a list of characters from that episode  
- home button clears navigation stack and redirects to the home screen
- supports devise orientation changes
- supports dark and light themes

<br>
<br>


## App Architecture

The application is designed using Jetpack Compose and custom version of MVVM & Clean Architecture.
<br>
<br>
The basic data flow can be represented by this idea:
<br>

```
Composable UI <-> ViewModel <-> UseCase <-> Repository <-> NetworkClient  
```

The contains 3 main layers (packages):

<br>

- app or presentation layer
  - dependency injection setup
  - composable ui
  - view models
  - utils
- domain layer
  - models
  - use cases
  - repository interfaces
- data layer
  - data transfer objects, DTOs
  - repository implementations
  - network client
  - analytics

<br>
<br>

## Architecture decisions

- each composable screen has a corresponding ViewModel and Navigation component
- MainViewModel is responsible for loading and caching paged data for Characters, Locations and
  Episodes
- for minimizing the number of individual API requests we use Ktor's Http cache, requests are cached
  in-memory based on 'Cache-Control' header provided by the API
- for deserializing API responses we use Kotlin Serialization
- using raw JSON strings for producing PreviewParameterProvider's sequences
- the navigation between characters, locations and episodes is done by clicking on corresponding
  cards
- added simple error handling and retry functionality

<br>
<br>

## Testing

- configured UI and Unit Test harnesses
- added sample UI tests for HomeScreen composable
- added sample tests for HomeViewModel view model
