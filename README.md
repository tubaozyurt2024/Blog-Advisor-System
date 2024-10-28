## BlogHub Service
 
BlogHub Service is an AI-powered blog recommendation system that uses OpenAI to suggest relevant blog content based on users' reading history. It integrates RabbitMQ for message handling, supports personalized blog recommendations through OpenAI, and leverages PostgreSQL for database management.

## Features
- **AI-Powered Blog Recommendations:** Personalized blog suggestions based on the user's reading history, using OpenAI for intelligent content recommendations.
- **RabbitMQ Integration:** Handles message queues for different operations like email notifications.
- **Database Integration:** Utilizes PostgreSQL for efficient storage and management of user, blog, and recommendation data.

## Technologies
This project leverages the following technologies:

- **Java Spring Boot** - Main application framework
- **OpenAI API** - AI-based blog recommendation engine
- **RabbitMQ** - Message queuing and event handling
- **PostgreSQL** - Database management
- **Spring Security & JWT** - User authentication and security

## Installation
### Prerequisites

- **Java** 17 or higher
- **PostgreSQL
- **RabbitMQ
- **Docker (optional, for containerization)

## Explanation of Configuration Parameters

- **OpenAI API Key:** Required to connect and authenticate with the OpenAI API for generating recommendations.
- **RabbitMQ:** Used for handling asynchronous messaging. Configured to connect with local RabbitMQ setup.
- **Override Bean Definitions:** Set to true to allow redefinition of beans in the Spring context if needed.
