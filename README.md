# API-less YouTube Uploader

This project provides a way to upload videos to YouTube without using the YouTube API. It uses the YouTube web interface to perform the upload, so it can be used in cases where the YouTube API is not available or cannot be used.

## Getting Started

To use the API-less YouTube Uploader, you will need to download and install the following software:

- [Java Development Kit (JDK) version 8 or higher](https://www.oracle.com/ca-en/java/technologies/downloads/)
- [Selenium for Java](https://www.selenium.dev/downloads/)

## Usage

Once you have these dependencies installed, you can compile and run the project using the following commands:

```
javac YoutubeUploader
java YoutubeUploader <chromedriver-location> <channel-id> <youtube-email> <youtube-password> <video-title> <description-textfile-location> <video-location> <thumbnail-location>
```

This will upload the specified video file to the YouTube account associated with the given username and password, with the specified title and description.

## Contributing
Contributions to the API-less YouTube Uploader project are always welcome! If you have any ideas, suggestions, or bug reports, please submit them as issues on the project's GitHub page.

## License
This project is licensed under the MIT License
 See the LICENSE file for more information.

