# SauceDemo Automated Tests

## Table of Contents
- [Overview](#overview)
- [Features / UC](#features--uc)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Installation & Running Tests](#installation--running-tests)
- [Test Structure](#test-structure)
- [Logging](#logging)
- [Notes](#notes)
- [Author](#author)

---

## Overview
This project automates the login scenarios of the [SauceDemo](https://www.saucedemo.com/) application using Selenium WebDriver and JUnit 5.  
The goal is to practice automated testing techniques, including locators, assertions, and basic logging.

---

## Features / UC
The project implements the following **Use Cases (UC)**:

1. **UC-1: Test Login with empty credentials**
    - Click "Login" without entering username or password.
    - Validate error message: `"Username is required"`.

2. **UC-2: Test Login with missing password**
    - Enter a valid username but leave password empty.
    - Validate error message: `"Password is required"`.

3. **UC-3: Test Login with valid credentials**
    - Enter accepted username (`standard_user`) and password (`secret_sauce`).
    - Click "Login" and validate the dashboard title: `"Swag Labs"`.

---

## Technologies
- **Java 17**
- **Maven** (project build tool)
- **Selenium WebDriver 4.20**
- **JUnit 5** (testing framework)
- **SLF4J** (logging)
- **Hamcrest** (assertions)

---

## Prerequisites
- JDK 17+ installed
- Maven 3.8+ installed
- Internet connection (for WebDriverManager to download drivers)
- Chrome installed (current version)

---

## Installation & Running Tests
1. Clone the repository:
    ```bash
    git clone https://github.com/GorKhurshudyan/SauceDemo-Automation.git

2.   Navigate to the project folder
     ```bash
     cd SauceDemo-Automation

3.   Run the tests using Maven
     ```bash
     mvn clean test
