# Capstone-Project

Six Four Fantasy is an extremely addictive cricket fantasy game that allows you to build your own team and earn fantasy points based on how your team players  actually performs  in real world cricket matches. But there is a catch! Unlike all other cricket fantasy games here you only earn points for the boundaries hit by your batsmen while losing points for the runs committed by your bowlers.

## Features

Six Four Fantasy has following features:

- Users can see ongoing and upcoming matches.
- Users can see scores and their fantasy points for the ongoing matches.
- Users can see results and fantasy points of matches concluded in past.
- Users can build their fantasy team for upcoming matches by selecting 11 players of their choice out of all available players.
- Users can see their rank and a leaderboard for all the matches ongoing or concluded in past.
- App sends notification to the user about the upcoming matches of their home team.

Please refer to the document [here](https://github.com/shubham1g5/SixFourFantasy/blob/master/Capstone_Stage1.pdf) for more detailed features and UI mocks.

## Implementation Features

- [Dagger2](https://google.github.io/dagger/) dependency injection.
- MVVM implementation with [Android data binding library](https://developer.android.com/topic/libraries/data-binding/index.html).
- RxJava/RxAndroid streams to interact with async data apis.
- Content Provider to store matches locally.
- App Loaders to provide data from content providers.
- User data gets stored and synced with [Firebase Realtime DB](https://firebase.google.com/docs/database/?gclid=CjwKEAiArIDFBRCe_9DJi6Or0UcSJAAK1nFv97quw8-bzf1h_bWYTY2twN47H7ENfnBJIlqA3C4eeRoCv5zw_wcB).
- [Firebase JobDispatcher](https://github.com/firebase/firebase-jobdispatcher-android) for scheduling regular match data requests.
- [Retrofit2](https://square.github.io/retrofit/) is used for making http requests with Moshi to parse the Json response. 


