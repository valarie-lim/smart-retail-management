# Smart Retail Management System

![Language](https://img.shields.io/badge/Language-Java-blue)
![Application](https://img.shields.io/badge/Application-Console%20App-green)
![Paradigm](https://img.shields.io/badge/Paradigm-OOP-orange)

The Smart Retail Management System is a console-based retail operations platform developed in Java. The system provides dedicated modules for both staff and customers to manage products, gift cards, bundle deals, and transaction records through a menu-driven interface.

This project demonstrates core Object-Oriented Programming (OOP) concepts, including inheritance, polymorphism, abstraction, and encapsulation.

---

## Quick Start
1. Download the SmartRetail.zip.
2. Extract the ZIP file.
3. Open the folder.
4. Run SmartRetail.exe.  *(No external Java installation required.)*
5. Log in using the provided credentials as follows.

---

## Default Login Credentials
To test the system with specific role-based permissions, use the following credentials:

| Role | Username | Password |
| :--- | :--- | :--- |
| **Customer** | `cust` | `cust` |
| **Staff** | `staff` | `staff@123` |

---

## Project Overview
The purpose of this project is to simulate a retail management environment, providing a secure and organized way for businesses to manage inventory and for customers to process purchases. The system emphasizes data integrity and security, featuring a three-attempt account lock mechanism for unauthorized access prevention.

---

## System Features
### Authentication & Security
- Role-based access control (Staff vs. Customer)
- Three-attempt account lock mechanism

### Retail Operations
- **Item Management**: CRUD operations for products, gift cards, and bundle deals
- **Transaction Processing**: Automated calculation of discounts, tax/rounding adjustments, and receipt generation
- **Order Tracking**: Customers can access their unique transaction history

### Data Integrity
- Robust input validation to ensure accuracy for registrations, updates, and transaction amounts

---

## System Modules
**Staff Module**
- Manage product inventory
- Oversee gift cards and bundle deals
- Manage customer accounts
- Oversee transaction records

**Customer Module**
- Browse items
- Manage shopping cart
- Process checkouts with payment method selection
- View personal order history

---

## Object-Oriented Design
The system architecture follows these OOP principles:
*   **Inheritance**: `Product`, `GiftCard`, and `BundleDeal` inherit from an `Item` superclass.
*   **Polymorphism**: Implemented via the `Transactable` interface, allowing roles to perform unique transaction logic.
*   **Abstraction**: Utilizes abstract classes (`User`, `Payment`, `Item`) to define essential behaviors while hiding complex implementation details.
*   **Encapsulation**: Protects sensitive data using `private` modifiers and controlled access methods.
*   **Aggregation**: Models "has-a" relationships, such as the `Staff` class managing collections of `Customer`, `Item`, and `Payment` objects.

---

## Technologies Used
- Java (JDK)
- IntelliJ IDEA
- Git & Github
- OOP Principles (Inheritance, Polymorphism, Abstraction, Encapsulation)

---

## Learning Outcomes
Through this project, I gained experience in:
- Applying core **Object-Oriented Programming (OOP)** principles to structure a complex system.
- Designing and implementing a **menu-driven console interface** for diverse user roles.
- Developing robust **input validation** and **security mechanisms** like account lockout logic.
- Implementing **polymorphic behaviors** to handle different transaction types effectively.
- Managing project version control and collaborative development using **GitHub**.
- **Project Packaging**: Exporting Java projects into executable JAR artifacts.
- **Deployment**: Utilizing `jpackage` to bundle the application into a native `.exe` file for distribution.
- Designing an extensible system architecture that supports future feature scaling.

---

## Author
Valarie Lim  
Diploma in Information Technology
