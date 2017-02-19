# SixFourFantasy

A simple cricket app that allows you to see ongoing, past and upcoming matches with their scores.

## Implementation Features

- [Dagger2](https://google.github.io/dagger/) dependency injection.
- MVVM implementation with [Android data binding library](https://developer.android.com/topic/libraries/data-binding/index.html).
- RxJava/RxAndroid streams to interact with async data apis.
- Content Provider to store matches locally.
- App Loaders to provide data from content providers.
- [Firebase JobDispatcher](https://github.com/firebase/firebase-jobdispatcher-android) for scheduling regular match data requests.
- [Retrofit2](https://square.github.io/retrofit/) is used for making http requests with Moshi to parse the Json response. 


