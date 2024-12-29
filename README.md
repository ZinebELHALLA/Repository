# VoiceLearn: A Language Learning Application


VoiceLearn is an innovative language learning application designed to help users improve their language skills through interactive methods and tailored learning experiences. This platform harnesses cutting-edge technologies to provide a seamless learning experience.

## Table of Contents

- [Docker Image](#docker-image)
- [Frontend](#frontend)
- [Backend](#backend)
- [Getting Started](#getting-started)
- [Video Demonstration](#video-demonstration)
- [Contributing](#contributing)


## Docker Image

```yaml
version: '3.8'

services:
  backend:
    build:
      context: ./
    ports:
      - "5000:5000"
    environment:
      DBHOST: database
      DBUSER: root
      DBDATABASE: voice_learn
    depends_on:
      - database

  frontend:
    build:
      context: ./Web
    ports:
      - "8091:8091"

  database:
    image: mysql:5.7
    environment:
      MYSQL_ROOT_PASSWORD: ""
      MYSQL_DATABASE: voice_learn
    volumes:
      - db_data:/var/lib/mysql

volumes:
  db_data: {}
```

## Frontend

### Technologies Used
- Kotlin
- OkHttp

### Frontend Project Structure
```
com.voicelearn.app
├── adapter: Contains adapters to interact with data layers.
├── api: APIs for network requests.
├── component: UI components of the application.
├── data: Data handling and repositories.
├── helper: Utility functions and helpers.
├── ui: UI logic and design elements.
└── utils: General utility functions.
```

## Backend

### Technologies Used
- Express.js
- Node.js
- MySQL

## Getting Started

Here are step-by-step instructions to set up and run your VoiceLearn project locally:

### Prerequisites
- **Git**: Ensure you have Git installed. If not, download and install it from [git-scm.com](https://git-scm.com).
- **Node.js**: Install Node.js from [nodejs.org](https://nodejs.org).
- **Docker**: Install Docker to handle containerization from [docker.com](https://www.docker.com).

### Setup

1. **Clone the Project**:
   ```bash
   git clone https://github.com/ZinebELHALLA/VoiceLearn
   cd VoiceLearn
   ```

2. **Run Docker**:
   - Navigate to the folder containing the `docker-compose.yml` file.
   - Build and start your services:
     ```bash
     docker-compose up --build
     ```

3. **Access the Applications**:
   - **Frontend**: Open your browser and navigate to `http://localhost:8091`.
   - **Backend**: The backend is accessible at `http://localhost:5000`.



## Video Demonstration

Click the link below to watch a demonstration video:

[Watch Video Demonstration](#)

## Contributing

We welcome contributions from everyone, and we appreciate your help to make this project even better! If you would like to contribute, please follow these guidelines:
