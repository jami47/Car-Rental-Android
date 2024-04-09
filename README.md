# Car Rental

Car Rental is an Android application developed in Java. It features a user-friendly interface for renting cars and an admin panel for managing the car inventory.

<div align="center">
<img src = "https://media0.giphy.com/media/v1.Y2lkPTc5MGI3NjExcmxqYnhveWd2eWdhenhqa2VkeXcxMm1vemc5N2V3YWczcTdnYWs1byZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/xT4uQzGxVBf16tUpmU/giphy.gif" width = "300">
</div>

## Features

- **User Authentication**: The app provides separate entry points for users and the admin. Users can register and log in to access the car rental services.

- **Admin Panel**: The admin can add, delete, and update the details of the cars. The admin can also change their email and password.

- **User Interface**: Users can view a list of available cars after logging in. The list is displayed using a RecyclerView.

- **User Profile**: Users can edit their profile information.

- **Upcoming Cars**: The app uses a JSON API to display upcoming cars.

- **Firebase Realtime Database**: All user and admin information, as well as car details, are stored in a Firebase Realtime Database. Any addition or update to the car inventory is immediately reflected in the database. 

<div align="center">
<img src = "app/src/main/res/drawable/car_rental3.png" width = "200">
</div>

## Setup

To run this application on your device

```bash
  1. Clone the repository
  2. Open the project in Android Studio
  3. Configure your Firebase account and add the google-services.json file to the project
  4. Build and run the project on an emulator or real device
```

## Contributions

Contributions, issues, and feature requests are welcome!

<!-- <div style="width:100%;height:0;padding-bottom:100%;position:relative;"><iframe src="https://giphy.com/embed/xT4uQzGxVBf16tUpmU" width="100%" height="100%" style="position:absolute" frameBorder="0" class="giphy-embed" allowFullScreen></iframe></div><p><a href="https://giphy.com/gifs/xT4uQzGxVBf16tUpmU">via GIPHY</a></p> -->