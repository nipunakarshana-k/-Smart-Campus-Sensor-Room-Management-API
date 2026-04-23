# Smart Campus Sensor Room Management API

## Tomcat 9 / NetBeans setup

This project is now packaged as a WAR for Apache Tomcat 9.

1. Open the project in NetBeans as a Maven project.
2. Make sure Tomcat 9 is added in the Services tab.
3. Run the project. NetBeans should deploy it as a web application.
4. Open `http://localhost:8080/<context-root>/api/v1/` to test the API root.

The web app entry point is `src/main/webapp/index.html`, and the Jersey servlet is configured in `src/main/webapp/WEB-INF/web.xml`.