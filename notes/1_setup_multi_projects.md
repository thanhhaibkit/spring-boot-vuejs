# Setup multiple sub-projects 
- Refer: [Setup multi-projects](https://docs.gradle.org/current/userguide/multi_project_builds.html)

Project layout
```
.
├── app
│   ...
│   └── build.gradle
├── app2
│   ...
│   └── build.gradle
└── settings.gradle
```

settings.gradle
```
rootProject.name = 'multiprojects'
include 'app'
include 'app2'
```

app/build.gradle
```
plugins {
    id 'application'
}

application {
    mainClass = 'com.example.Hello'
}
```

app/src/main/java/com/example/Hello.java
```
package com.example;

public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
    }
}
```