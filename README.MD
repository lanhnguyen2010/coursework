1. Checkout code
2. Build: mvn clean install
3. Copy target/coursework-1.0.SNAPSHOT.jar to server
4. Edit resource/server.properties with correct config.
    Copy resource/server.properties to server.
5. Run:
    java -cp coursework-1.0.SNAPSHOT.jar server.cmd.Cmd path/to/server.properties
6. Run client from IDE to test.