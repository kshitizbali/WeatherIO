---

# WeatherIO

Welcome to WeatherIO, a small and lightweight weather application developed as a simple project. This project leverages the OpenWeatherMap API to deliver up-to-date weather forecasts  for any location in the USA.

## Features

- **Current Weather Data**: Access detailed information on current weather conditions, including temperature, humidity, wind speed, and atmospheric pressure.
- **Forecast**: Get weather forecasts for the upcoming day to plan your next day efficiently.
- **Weather Icons**: Visual representation of weather conditions using icons that adjust based on the weather data.
- **User-Friendly Interface**: A clean and intuitive UI designed with Material Design principles to enhance user experience.

## Technologies Used

- **Kotlin**: The primary programming language used for the application.
- **Jetpack Compose**: For building responsive and modern UI components.
- **Retrofit**: To handle API requests and responses.
- **Moshi**: For JSON parsing.
- **Dagger-Hilt**: For dependency injection to ensure a scalable and maintainable codebase.
- **Coil**: To load images efficiently in the app.

## Setup and Installation

Follow these steps to set up the project on your local machine:

1. **Clone the repository**:
   ```sh
   git clone https://github.com/kshitizbali/WeatherIO.git
   ```

2. **Open in Android Studio**: Open the project in Android Studio to configure it for your development environment.

3. **Add API Key**:
   - You can use mine (already added) or Obtain your API key from [OpenWeatherMap](https://home.openweathermap.org/users/sign_up).
   - Create a `local.properties` file in the root directory of your project.
   - Add your API key:
     ```properties
     API_KEY=your_api_key_here
     ```

4. **Build and Run**: Build and run the project on an emulator or physical device to start using WeatherIO.

## Usage

Once the application is installed, you can:
- **Search for a Location**: Enter a city name to get current weather data and forecasts.
- **View Weather Details**: See detailed weather metrics such as temperature, wind speed and humidity.
- **Visual Weather Representation**: Observe the weather conditions visually through icons that update based on the latest weather data.

## Contribution

As this is a simple & samll project, contributions are not expected. However, if you have suggestions or improvements, feel free to fork the repository and submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For any inquiries or support, please contact:
- Kshitiz Bali - [kshitizbali@gmail.com](mailto:kshitizbali@gmail.com)

Thank you for reviewing WeatherIO. I hope you find my implementation are upto the current standards and showcases my skills effectively.

---
