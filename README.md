# VoiceLearn: A Language Learning Application
![voicelearn](https://github.com/user-attachments/assets/2fe100d2-a61f-41f2-93f2-cbb0713c4432)


VoiceLearn is an innovative language learning application designed to help users improve their language skills through interactive methods and tailored learning experiences. This platform harnesses cutting-edge technologies to provide a seamless learning experience.

## Table of Contents
- [Project architecture](#architecture)
- [Docker Image](#docker-image)
- [Frontend](#frontend)
- [Backend](#backend)
- [Getting Started](#getting-started)
- [Video Demonstration](#video-demonstration)
- [Contributors](#Contributors)

## Project architecture

![architecture](https://github.com/user-attachments/assets/13c45c72-9641-410f-b9ba-43d293948ebb)


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
- Python

## Getting Started

Here are step-by-step instructions to set up and run  VoiceLearn project locally:

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


https://github.com/user-attachments/assets/e7b09836-7dfc-451d-8ada-8be4694a3ee3



## Contributors
- [**ELHALLA ZINEB**](https://github.com/ZinebELHALLA)
- [**EL MAHJOUBI SOUKAINA**](https://github.com/elmahjoubisouka)


