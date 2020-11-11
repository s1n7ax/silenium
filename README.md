# silenium

A simple framework where you can start working on your tests right away.
(Just wanted to prove myself to companies that I'm capable of creating a Selenium framework. :D)

## Features
* Parallel execution 
* Supports Linux & Windows (You can override or extend supported platforms easily)
* Supports Chrome & Firefox (You can override or extend supported browsers easily)
* Do you want to run different test cases on different browsers using different driver versions concurrently? You can do that (Guide will be updated soon... :))

## Prerequisite
* Java 10 or higher (some Java 10 features are being used)
* You may have to update web drivers if they are too old or doesn't match your browser version. Current versions are as follows
	* `ChromeDriver 86.0.4240.22`
	* `geckodriver 0.28.0`
* Eclipse with TestNG plugin will make things so much easier.

# Running
* Clone or download the project
* Import the project to Eclipse as a Gradle project
* Right click on the project and click on Gradle -> Refresh Gradle Project
* Open `GoogleSearchTests.java` class
* Right click -> Run As -> TestNG Test

You are welcome! :D
