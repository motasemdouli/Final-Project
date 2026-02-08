# Final-Project — Software Testing Project (HTU)

Overview
--------
This repository contains the final project for the Software Testing course at HTU. The goal is to apply multiple software testing methodologies to validate functionality, performance, and reliability of the "Loyalty+ Store" e-commerce platform (uses DummyJSON as backend).

Test types included
-------------------
- Manual Testing — core flows and edge cases.
- API Testing — Postman collections, executed locally or via Newman.
- Performance Testing — k6 scripts for smoke/load/stress tests.
- UI Automation — Selenium WebDriver tests managed by TestNG.

Testing objectives
------------------
- Ensure functional integrity for core features (authentication, product browsing, cart, checkout).
- Validate backend API behavior under varied conditions.
- Assess performance under load and find bottlenecks.
- Automate critical UI flows for regression safety.

Repository layout (recommended)
------------------------------
- /postman
  - FinalProjectApiTest.postman_collection.json
  - FinalProjectEnv.postman_environment.json
- /k6
  - k6test.js
- /ui-tests
  - (Selenium/TestNG Java code, pom.xml or build.gradle)
- /reports
  - newman-report.html
  - k6-report-*.json
  - selenium-*.html
- README.md

Prerequisites
-------------
- Git
- Node.js + npm (optional, for Newman if installed via npm)
- Newman (for running Postman collections from CLI) OR Postman desktop app
  - Install via npm: `npm install -g newman`
- Java 8+ and Maven (or Gradle) for UI tests
- Edge browser and compatible EdgeDriver (or use WebDriverManager in code)
- k6 (for performance tests)
  - Install: https://k6.io/docs/getting-started/installation

Clone the repository
--------------------
```bash
git clone https://github.com/motasemdouli/Final-Project.git
cd Final-Project
```

Postman / API tests
-------------------
- Import the collection `postman/FinalProjectApiTest.postman_collection.json` into Postman (or run with Newman).
- Example Newman command (generates an HTML report):
```bash
newman run postman/FinalProjectApiTest.postman_collection.json \
  -e postman/FinalProjectEnv.postman_environment.json \
  -r cli,html \
  --reporter-html-export reports/newman-report.html
```
Notes:
- Make sure the environment file contains any required base URLs or tokens.
- If you don't include Postman files in the repo, add them under `/postman`.

UI Automation (Selenium + TestNG)
---------------------------------
Requirements:
- Java (8+), Maven (or Gradle), Edge browser and EdgeDriver.
- Alternatively use WebDriverManager to avoid manual EdgeDriver management.

Run (Maven example):
```bash
cd ui-tests
mvn test
```
Or run specific TestNG suite:
```bash
mvn -Dtestng.suiteXmlFile=testng.xml test
```
Tips:
- Ensure the EdgeDriver executable is on PATH or configured in your tests.
- Prefer using WebDriverManager (io.github.bonigarcia) to manage driver binary automatically.

Performance tests (k6)
----------------------
Run the k6 test:
```bash
cd k6
k6 run k6test.js
```
Example to output JSON summary:
```bash
k6 run --out json=../reports/k6-report.json k6test.js
```

Test reporting
--------------
- Newman produces CLI and HTML reports (see `reports/newman-report.html`).
- k6 can export JSON results; use external tools to visualize.
- UI tests can produce TestNG/Allure/JUnit reports depending on configured reporters — place them under `/reports`.

Recommended improvements
------------------------
- Add CI pipeline (GitHub Actions) to run smoke tests (API + UI) on PRs.
- Include Postman collection + environment files in `/postman`.
- Add a `run-tests.sh` (or npm scripts) to unify commands for new contributors.
- Add badges (build / tests / k6 summary) to README.
- Document test data and any required accounts or credentials (use `.env` and ignore secrets).


Contact
-------
For questions about running tests or the project structure, open an issue or contact the author: motasemdouli
