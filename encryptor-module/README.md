The `encryptor` module provide encrypt support to Jackson.
It let you define sensitive data using `@Encrypt` annotation.

## Add dependency

Add dependency in your `pom.xml`
```xml
<dependency>
    <groupId>com.ggl.jackson-extensions</groupId>
    <artifactId>encryptor</artifactId>
    <version>1.0.0</version>
</dependency>
```

Add repository
```xml
<repository>
  <id>github</id>
  <url>https://maven.pkg.github.com/galeries-lafayette/jackson-extensions</url>
</repository>
```

In your `settings.xml` add github server (a personal access token can be added [here](https://github.com/settings/tokens/new), it needs at minimal `read:packages` rights)
```xml
<server>
  <id>github</id>
  <username>USER</username>
  <password>USER_ACCESS_TOKEN</password>
</server>
```
## Usage

Register the encryptor module

```java
new ObjectMapper().registerModule(
    new EncryptorModule(new AesBytesEncryptor("password", "salt"))
)
```

Annotate sensitive attributes
```java
class Pojo {
    @Encrypt
    String sensitiveAttribute;

    String nonSensitiveAttribute;
}
```
