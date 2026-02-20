# MovieGeek Android TV Starter

This is a starter Android TV app scaffold wired to MovieGeek recommendation APIs.

## What it includes

- Kotlin + Gradle project (`android-tv/`)
- TV launcher activity and TV manifest config
- Compose-based TV home screen with recommendation rails
- Retrofit data layer wired to these endpoints:
  - `/rec/cf/user/{userId}/`
  - `/rec/cb/user/{userId}/`
  - `/rec/pop/user/{userId}/`
  - `/rec/ar/{userId}/`
- Basic ViewModel state handling (loading, error, data)

## Import and run

1. Open Android Studio.
2. `File -> Open...` and select `android-tv/`.
3. Let Gradle sync.
4. Start MovieGeek backend locally (repo root):
   - `./start-local.sh`
5. Run the app on an Android TV emulator.

## Backend URL configuration

Default base URL is configured in `app/build.gradle.kts`:

- `MOVIEGEEK_BASE_URL = "http://10.0.2.2:8000/"`

`10.0.2.2` is correct for Android emulator to reach host `localhost`.

If using a physical TV device, change this to your machine LAN IP, for example:

- `http://192.168.1.10:8000/`

Then rebuild and reinstall.

## User ID configuration

Default user id is in `app/build.gradle.kts`:

- `DEFAULT_USER_ID = "10000000001"`

Change it to any user id in your dataset.

## Notes

- This is a starter scaffold, not a production app.
- Recommendation cards currently show movie IDs and score only.
- Next step is integrating movie metadata/artwork (TMDB) and a playback/detail flow.
