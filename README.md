# 🏨 Hotel Management System

A **Java-based desktop application** that automates and manages hotel operations such as customer check-in/check-out, room allocation, and employee management using a **MySQL database**.

---

## 📌 Features

* Customer Check-In & Check-Out
* Room Booking & Availability Tracking
* Search Rooms (Bed Type & Availability)
* Employee & Manager Information
* Department Details
* Update Room Status (Clean / Dirty)
* Pickup Service Management
* Login Authentication System

---

## 🛠️ Technologies Used

* **Java (Swing & AWT)** – GUI
* **MySQL** – Database
* **JDBC** – Connectivity
* **NetBeans / Eclipse / IntelliJ** – IDE

---

## 🗂️ Project Structure

```
hotel.management.system
│
├── Login.java
├── Dashboard.java
├── Reception.java
├── NewCustomer.java
├── CheckOut.java
├── UpdateRoom.java
├── UpdateCheck.java
├── SearchRoom.java
├── PickUp.java
├── CustomerInfo.java
├── Employee.java
├── ManagerInfo.java
├── Department.java
├── Room.java
├── conn.java
└── icons/
```

---

## ⚙️ Setup Instructions

### 1. Clone the Repository

```
git clone https://github.com/your-username/hotel-management-system.git
```

---

### 2. Setup Database

Open MySQL and run:

```sql
CREATE DATABASE hms;
USE hms;
```

---

### 3. Create Tables

```sql
CREATE TABLE login (
    username VARCHAR(20),
    password VARCHAR(20)
);

INSERT INTO login VALUES ('admin','admin');

CREATE TABLE room (
    room_number INT,
    availability VARCHAR(20),
    clean_status VARCHAR(20),
    price INT,
    bed_type VARCHAR(20)
);

CREATE TABLE customer (
    id VARCHAR(20),
    number VARCHAR(20),
    name VARCHAR(50),
    gender VARCHAR(10),
    country VARCHAR(30),
    room_number INT,
    status VARCHAR(20),
    deposit VARCHAR(20)
);

CREATE TABLE employee (
    name VARCHAR(50),
    age VARCHAR(10),
    gender VARCHAR(10),
    job VARCHAR(30),
    salary VARCHAR(20),
    phone VARCHAR(20),
    aadhar VARCHAR(20),
    email VARCHAR(50)
);

CREATE TABLE department (
    department VARCHAR(50),
    budget VARCHAR(20)
);

CREATE TABLE driver (
    name VARCHAR(50),
    age VARCHAR(10),
    gender VARCHAR(10),
    company VARCHAR(50),
    brand VARCHAR(50),
    available VARCHAR(10),
    location VARCHAR(50)
);
```

---

### 4. Configure Database Connection

Edit `conn.java`:

```java
Class.forName("com.mysql.cj.jdbc.Driver");

Connection c = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/hms?useSSL=false&serverTimezone=UTC",
    "root",
    ""
);
```

---

### 5. Add MySQL Connector

* Download MySQL Connector/J (.jar file)
* Add it to your project libraries

---

### 6. Run the Project

Run:

```
HotelManagementSystem.java
```

---

## 🔐 Default Login

```
Username: admin
Password: admin
```

---

## 🎯 Objective

To develop a system that reduces manual work and efficiently manages hotel operations using a centralized database.

---

## 🚀 Future Improvements

* Use PreparedStatement for security
* Add password encryption
* Improve UI design
* Add billing system
* Online booking feature

---

## 👨‍💻 Author

H. Swathi
B.Tech Student

---

## 📄 License

This project is for educational purposes only.

---

## 💡 Notes

* Ensure MySQL is running
* Database name should be `hms`
* Tables must be created before running
