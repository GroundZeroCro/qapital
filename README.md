## Table of contents
* [General info](#general-info)
* [Showcase](#showcase)
* [Technologies](#technologies)
* [Libraries](#libraries)

## General info
Initial screen shows goals fetched from server. Upon successful data fetch, data is cached into local database. Next time user opens goal screen, if remote data fetch has an error, cached data from previous load will be shown. If there is no cached data and user is unable to fetch data from server, refresh button will be visible where on click new request for data to the server will be sent.

## Showcase	
<img src="https://i.imgur.com/jhYH5k0.png" width="280">   <img src="https://i.imgur.com/76xSyCk.png" width="280">
<img src="https://i.imgur.com/pjbloo5.png" height="280">

## Technologies
* Android
* Kotlin

## Libraries
* Dagger 2
* Gson
* RxJava2
* Retrofit
* LiveData
* DataBinding
* Calligraphy
* Picasso
* Mockito
