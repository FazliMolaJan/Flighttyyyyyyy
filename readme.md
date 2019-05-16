# Flightyyyy

### Introduction
Android app that pulls data from https://api.lufthansa.com/v1/ .

### Used libraries
**Dagger2** - Dagger2 was used for dependency injection.</br>
**RxJava2** - RxJava2 was used for thread management and data stream management.</br>
**Retrofit** - Network calls</br>
**Anko** - For cool extensions to Android classes.</br>
**Architecture Components** - For Lifecycle, and Navigation in the presentation layer.</br>
**Mockito** - For mocking test dependencies.</br>
**JUnit** - For Unit test assertions etc.</br>
**Konveyor** - For generating random data for tests.</br>

### Process
I started by carefully reading the requirements file and then went on to examine the Lutfthansa API. I noticed how the data from the api had fields identifying `airports` and `airlines` in the `Schedule` and `Flight` objects. I also noticed that there was no data nesting in any of the fields, instead there was an `ID` referring to the appropriate data e.g `airlines` and `airports`. With this in mind, I was prepared to  do some work on executing network requests in parallel. I however decided to use well formatted list of airports and airlines that I found in JSON files on Github, this way, the airline and airport data was quickly and easily assesible offline, this helped me to easily implement a feature where users could search for cities or airports they wanted to visit instead of using the `IATA` codes provided by the API.

I decided to use clean architecture and also break down the project into modules, I thought of exploring `Kotlin coroutines` extensively but decided otherwise because `RxJava2` offered more in terms of stream and error management, the similar `Flow` API in coroutines are still in development so I decided to avoid them, I also decided to use different models for each layer of the architecture so I could do some data cleaning and conversion to fit the models into each layer and to mimic a situation where many developers are working on the same app. I wrote interfaces to represent repositories in the domain layer and then wrote `UseCase`s for searching characters and fetching details of characters and then wrote tests for the usecases and repositories.

Afterwards I started writing the `data` layer, here I began by writing models for each of the apps entities/data objects and then interfaces that described classes that would interact with the starwars API e.g ICharactersRemote. I then wrote concrete implementations of all the repositories defined in the `domain` layer. I tested the concrete implementations and wrote mapper classes to map from `data entities` to `domain models`. I also wrote some tests for these mappers to make ensure the data was always being mapped correctly.

I continued by writing concrete implementations of the Remote classes. The remote layer had models that fit the response from the Lutfthansa API and support for null values. I wrote mapper classes to map these models into `data entities`.

Finally I started work on the UI layer. I used the MVVM architecture supported by `ViewModel` and `LiveData` from the Android Architecture Components. I tested only the ViewModel classes and used the Navigation library to switch between destinations in the application and used Dagger2 to provide dependencies to the fragments and ViewModels.

On the Flight result list screen of the app. I noticed that the `Schedule` object contains a list of `Flights` when there are multiple connecting flights and a single `Flight` object when there only one. To combat this issue, I wrote a `TypeAdapterClass` to deserialize the data correctly dependending on  the situation. I also noticed another problem with the API tending to return flights that had multiple stops first, so I sorted the flights to show the cheapest and quickest `direct` flights first.


### Possible Improvements

I had a lot of fun building this app and I did some exploratory work with the `Navigation Component` and `Android-ktx`. There are some improvements I could make.

- Add some sweet animations to the polylines been drawn on the map screen</br>
- Write more tests for UI using Espresso </br>
- Use MockWebServer to test `Remote` layer.</br>
- Shared element transitions.</br>
- Write more tests.
- Add Klint checks for cleaner code.</br>
