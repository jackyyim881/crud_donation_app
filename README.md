# crud_donation_app

# My Donation App

This repository contains the code for **My Donation App**, which is a web application built with Java Spring Boot, MySQL, and Tailwind CSS. You can easily set up the development environment with Docker.

## Prerequisites

Make sure you have the following installed on your system:

- **Docker**: [Installation Guide](https://docs.docker.com/get-docker/)
- **Docker Compose**: [Installation Guide](https://docs.docker.com/compose/install/)

## One-Click Deployment

Deploy the entire application stack (including MySQL, Java Spring Boot, and Tailwind CSS) with a single command using Docker Compose.

### Docker Badge/Button for Deployment

[![Deploy with Docker](https://img.shields.io/badge/deploy%20with-docker-2496ED?logo=docker&logoColor=white&style=for-the-badge)](https://github.com/jackyyim881/crud_donation_app#one-click-deployment)

## Steps to Deploy

1. Clone the Repository

   ```bash
   git clone https://github.com/jackyyim881/crud_donation_app.git
   cd crud_donation_app

   ```

2. **API**

   ```curl
   http://localhost:8080/api/v1/feedbacks/donor-type/organization - personal / organization

   http://localhost:8080/api/donations?minAmount=1100
   ```

npx tailwindcss -i ./src/main/resources/static/tailwind.css -o ./src/main/resources/static/css/style.css --watch

http://localhost:8080/dashboard/settings?lang=fr
