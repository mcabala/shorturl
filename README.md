# ShortURL Creation in SpringBoot Application

SpringBoot Web Application for Short URL Creation

To run it using maven (http://maven.apache.org):
```
$ mvn spring-boot:run
```

Then navigate to http://localhost:8080/ in your browser.

## Components

### Guava is used for hashing URLs
```
		<dependency>
			<groupId>com.google.guava</groupId>
			<artifactId>guava</artifactId>
			<version>23.5-jre</version>
		</dependency>
```