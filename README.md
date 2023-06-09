# LinkShrink

A simple SpringBoot project that provides a link-shortener service. Includes a Dockerfile and a compose file for easy setup.

### How To Get Running

1. Clone the repo
2. Open up a terminal in the root project folder
3. Make sure Docker is running
4. Make sure both the project SDK and Gradle JVM are set to Java 17
5. `./gradlew build`
6. `docker compose up`

### Saving Links

When saved, links are given a hash generated using Java's default `UUID.randomUUID()` and taking the first 8 digits.
A quick check is made to make sure no collisions happened, and then the map between link and hash is stored in the
database. The system isn't designed for large-scale use, so this method works in this example.

To save, POST the raw link as an object in the request body as JSON:

```shell
curl --location 'http://localhost:8080/' \
--header 'Content-Type: application/json' \
--data '{
    "decoded": "http://www.reddit.com/r/programming"
}'
```

This will return an 8-digit string representing the hashed link. This is the value you will use to access your desired
link, but from a shortened format. The code responsible for generating the hash is below:

```java
private String encode() {
    String uuid;
    while (true) {
        uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
        LinkEntity exists = linkRepository.findByEncoded(uuid);
        if (exists == null) {
            break;
        }
    }
    return uuid;
}
```

### Accessing Links

Once you've saved a link in the database, you can reference it by using the 8-digit hash you received when saving.
If the hash returned was `59fab853`, then your request would be:

```shell
curl --location 'http://localhost:8080/59fab853'
```

This performs a HTTP-302 redirect to your desired link.