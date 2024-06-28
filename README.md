# Otwarte Migawki

## Overview

App for for photographer with adding online galleries for clients

## Table of Contents

- [Features](#features)
- [Demo](#demo)
- [Technologies Used](#technologies-used)
- [Database ERD](#erd)
- [Installation](#installation)
- [License](#license)

## Features


- Google Cloud storage integration
- JWT authorization
- SSL/TLS (self signed certificate)
- Session management (add, disable, enable sesssion types, adding accessibility etc.)
- Online Galeries
- Client management
- Reservations

## Demo

### Login screen
![alt text](https://github.com/Kuboss949/WdPAI/blob/Database/demo/Zrzut%20ekranu%202024-01-19%20204141.png?raw=true)
### Reservation
![alt text](https://github.com/Kuboss949/WdPAI/blob/Database/demo/Zrzut%20ekranu%202024-01-19%20204420.png?raw=true)
### Online Gallery
![alt text](https://github.com/Kuboss949/WdPAI/blob/Database/demo/Zrzut%20ekranu%202024-01-19%20204704.png?raw=true)
### Statistics and Awards
![alt text](https://github.com/Kuboss949/WdPAI/blob/Database/demo/Zrzut%20ekranu%202024-01-19%20205032.png?raw=true)
### User Profile
![alt text](https://github.com/Kuboss949/WdPAI/blob/Database/demo/Zrzut%20ekranu%202024-01-19%20205113.png?raw=true)



## Technologies Used

- Java SpringBoot
- React.js
- RabbitMQ
- MariaDB


## ERD


![alt text](https://github.com/Kuboss949/WdPAI/blob/Database/demo/erd.png?raw=true)

## Installation

1. To install this app you need to have the following software installed:
   - Oracle OpenJDK 19
   - Node.js
   - MariaDB
   - RabbitMQ Server
   - configured Google Cloud Storage container
2. Pull the repository
3. Edit application properties 
    - provide your user, password, ports etc.
    - change spring.jpa.hibernate.ddl-auto=validate to spring.jpa.hibernate.ddl-auto=update
4. Add your google-cloud-storage.json key to keystore
5. Run backend for example in IntelliJ
6. Run frontend using npm install then npm start in frontend folder


## License

MIT License

Copyright (c) 2024 Jakub Łopata

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.