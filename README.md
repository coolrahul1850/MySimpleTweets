# Project 3 - Simple Tweets

**Name of your app** is an android app that allows a user to view his Twitter timeline and post a new tweet. The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

Time spent: **20** hours spent in total

## User Stories

The following **required** functionality is completed:

* [done]	User can **sign in to Twitter** using OAuth login
* [done]	User can **view tweets from their home timeline**
  * [done] User is displayed the username, name, and body for each tweet
  * [done] User is displayed the [relative timestamp](https://gist.github.com/nesquena/f786232f5ef72f6e10a7) for each tweet "8m", "7h"
  * [done] User can view more tweets as they scroll with [infinite pagination](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView). Number of tweets is unlimited.
    However there are [Twitter Api Rate Limits](https://dev.twitter.com/rest/public/rate-limiting) in place.
* [done] User can **compose and post a new tweet**
  * [done] User can click a “Compose” icon in the Action Bar on the top right
  * [done] User can then enter a new tweet and post this to twitter
  * [done] User is taken back to home timeline with **new tweet visible** in timeline

The following **optional** features are implemented:

* [done] User can **see a counter with total number of characters left for tweet** on compose tweet page
* [done] User can **pull down to refresh tweets timeline**


The following **bonus** features are implemented:

* [done] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce view boilerplate.

The following **additional** features are implemented:

* [done] Added roboto font 
* [done] make the tweet button in compose activity rounded

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<a href="http://imgur.com/8HJmSxy"><img src="http://i.imgur.com/8HJmSxy.gif" title="source: imgur.com" /></a>

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.