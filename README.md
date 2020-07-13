# C2S PassMan DbLib

Database library for the C2S PassMan backend. This is a Kotlin on JVM project.

C2S PassMan is a password manager developed primarily as a proof-of-concept collection of applications, tied together in
a uniform architecture. The project was inspired by Bitwardent, a free and open-source password manager.

## Use

This is a Kotlin on JVM project using Gradle build tools. Your IDE should be able to resolve dependencies. Provide the
`config.json` configuration file inside the `resources` directory:

```json
{
  "mongo_connection_string": "XXX",
  "mongo_db_name": ""
}
```

Project is licensed under the open-source MIT license that you can check in the project root.

## Documentation 

You can generate the documentation for this library yourself by running the `dokka` Gradle task with `./gradlew dokka`
which generates the documentation in the `build/dokka` directory. The index HTML file is located in the
`build/dokka/c2s-passman-dblib/index.html`.