# Hospital Management System (HMS)

HMS is an application aimed at automating the management of hospital operations, including patient management, appointment scheduling, staff management, and billing. The system is expected to facilitate efficient management of hospital resources, enhance patient care, and streamline administrative processes


## Contributors

| Name                            | Email                 |    
| ------------------------------- | --------------------- |
| Amos Ng Zheng Jie | C230139@e.ntu.edu.sg |
| Kuo Eugene | KUOE0001@e.ntu.edu.sg |
| Gilbert Adriel Tantoso | GILB0007@e.ntu.edu.sg |
| Reswara Anargya Dzakirullah | RESWARAA001@e.ntu.edu.sg |
| Theodore Amadeo Argasetya Atmadja | THEO0013@e.ntu.edu.sg |

# Prerequisites

Ensure you have the following installed on your system:

- **JDK 21** or higher: [Download here](https://www.oracle.com/sg/java/technologies/downloads/)

Make sure your environment is set up correctly by verifying the installation of Java and Maven. You can check this by running the following commands:

```
vscode ➜ /workspaces/sc2002-oop-project (master) $ java --version
openjdk 21.0.4 2024-07-16 LTS
OpenJDK Runtime Environment Microsoft-9889606 (build 21.0.4+7-LTS)
OpenJDK 64-Bit Server VM Microsoft-9889606 (build 21.0.4+7-LTS, mixed mode, sharing)
```

## Usage Options:

### Pre-compiled JAR

Clone the repository and run the pre-compiled JAR file located in the `bin/` directory:

```
git clone https://github.com/YakunToast/sc2002-oop-project
cd sc2002-oop-project
java -jar ./app/build/libs/app.jar
```

### Build with Gradle

**1. Clone the Repository**

Navigate to your desired directory and clone this repository with `git clone`. Then navigate into the project directory.

```
git clone https://github.com/YakunToast/sc2002-oop-project
cd sc2002-oop-project/app
```

**2. Build the Project**

Clean the previous build artifacts (if any) and package the project into a JAR file using Maven. The generated build files will be located in the `target/` directory.

```
./gradlew build
```

**3. Run the Application**

After successfully building the project, execute the JAR file to run the Hospital Management System.

```
java -jar ./app/build/libs/app.jar
```

## Development Options:

This project has a [Development Container](https://containers.dev/) configured with the necessary prerequisites.

## Project Structure

The project follows a standard Maven structure:

- `app/src/main/java/`: Contains the main source code for the application.
- `app/src/test/java/`: Holds unit tests for the project.
- `app/build/`: This directory is generated during the build process and houses all output of the build.

Our project uses the Model-View-Controller (MVC) design pattern for maintainability and extensibility.

- **Model** objects, such as patient, hold the core business logic and data of our application.
- **View** objects act as the interface between the system and the users, handling the display of the user interface and user interactions.
- **Controller** objects coordinate the flow of data between the Model and View, processing user input and updating the Model and View accordingly.

This separation allows us to achieve strong decoupling, which improves modularity, testability and scalability of the application.

Other design patterns our project uses include the Repository pattern, which enforces a clean, uniform interface for performing CRUD operations on stored objects. We also made use of generics for better code reusability and type safety.

## Features

- Data Persistence
- New Patient Registration
- Unit Testing
- Input Validation
- Password Hashing

## JavaDoc

The JavaDoc is deployed on [github pages](https://yakuntoast.github.io/sc2002-oop-project/).

