# DB version control
Refer:
 - [Flyway](https://flywaydb.org/)
 - [Flyway Spring boot] (https://flywaydb.org/documentation/plugins/springboot)

File: build.gradle
```
dependencies {
    ...
	compile 'org.flywaydb:flyway-core'
}
```

File: build.database.gradle
```
import groovy.sql.Sql

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath "org.postgresql:postgresql:${postgresVersion}"
    }
}

def env = System.getenv()
def jdbc = [
        driver  : 'org.postgresql.Driver',
        url     : env['SPRING_DATASOURCE_URL'] ?: 'jdbc:postgresql://localhost:5432/postgres',
        user    : env['SPRING_DATASOURCE_USERNAME'] ?: 'user',
        password: env['SPRING_DATASOURCE_PASSWORD'] ?: 'password'
]
def classLoader = Sql.class.getClassLoader()
this.buildscript.configurations.classpath.each { classPath ->
    classLoader.addURL(classPath.toURI().toURL())
}


flyway {
    driver = jdbc.driver
    url = jdbc.url
    user = jdbc.user
    password = jdbc.password
    table = '__flyway_schema_history'
    baselineOnMigrate = true
}

task setupDataPublic {
    group = 'database'
    doLast {
        executeSqlFiles("moduleCore/src/main/resources/db/migration/data", /^data_public_.*\.sql$/, jdbc)
    }
}

static void executeSqlFiles(String directory,
                            String filePattern,
                            Map<String, String> jdbc) {
    def sql = Sql.newInstance(
        jdbc.url,
        jdbc.user,
        jdbc.password,
        jdbc.driver)
    println('[JDBC] ' + jdbc)

    new File(directory).listFiles({ d, f -> f ==~ filePattern } as FilenameFilter).each { sqlFile ->
        println('[SQL] ' + sqlFile)
        sql.execute readQueries(sqlFile)
    }
}

static String readQueries(File file) {
    file.readLines().join(String.valueOf(System.properties['line.separator']))
}
```