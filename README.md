# Travel Explorer — Android Studio Project

A Kotlin + XML travel discovery app starter with destination cards, categories, search, destination details, favorites, Glide image loading, and a Retrofit-powered Wikipedia summary lookup.

## Requirements
- Android Studio Ladybug or newer
- JDK 17
- Android SDK 35
- Internet connection for Gradle dependencies, remote photos, and Wikipedia summaries

## Run
1. Extract `TravelExplorer.zip`.
2. Open the `TravelExplorer` folder in Android Studio.
3. Allow Gradle sync to finish.
4. Select an emulator or Android device (Android 7.0 / API 24 or newer).
5. Press **Run**.

## Features
- Home screen with destination cards
- Category filtering: Mountains, Beach, City, History
- Search by destination, country, or category
- Destination detail screen
- Add/remove favorites (stored locally with SharedPreferences)
- Remote destination images loaded with Glide
- Live destination summary from the Wikipedia REST API using Retrofit
- Offline fallback descriptions if the API is unavailable

## Project structure
- `MainActivity.kt` — home screen, search, category filters, tabs
- `DetailActivity.kt` — destination details and API request
- `DestinationRepository.kt` — sample destination catalogue
- `DestinationAdapter.kt` — RecyclerView adapter
- `ApiClient.kt`, `TravelApi.kt` — Retrofit API client
- `FavoritesStore.kt` — local favorites persistence
- `res/layout/` — XML screen and card layouts

## API note
The app calls the public Wikipedia REST summary endpoint:
`https://en.wikipedia.org/api/rest_v1/page/summary/{title}`

No API key is required. The included destination catalogue is sample data; verify travel requirements, opening times, safety advice, and local conditions with official sources before travelling.

## Customization
Add destinations to `DestinationRepository.destinations`. Each entry contains an ID, title, country/region, category, fallback description, image URL, Wikipedia title, and travel tip. Use HTTPS image URLs.
