The `builder` module add builder support for deserialization.

The objectMapper will use builder methods automatically during object deserialization, instead of explicitly defining a `JsonPOJOBuilder`.

## Add dependency

Add dependency in your `pom.xml`
```xml
<dependency>
    <groupId>com.ggl.jackson-extensions</groupId>
    <artifactId>builder</artifactId>
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

```java
new ObjectMapper().registerModule(new Module());
```

You can provide custom build and builder methods names
```java
new ObjectMapper().registerModule(
    new ModuleBuilder.builder()
        .setBuilderMethodName("newBuilder")
        .setBuildMethodName("create")
        .setWithPrefix("")
        .create()
);
```
