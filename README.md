# Trivia Bot Backend [![Build Status](https://travis-ci.org/gardncl/trivia-bot-backend.svg?branch=master)](https://travis-ci.org/gardncl/trivia-bot-backend)

### Deploying to Heroku

```shell
sbt stage deployHeroku
```

## Slack

### Create a [slash command](https://api.slack.com/slash-commands) inside Slack
It will send a payload of data in `application/x-www-form-urlencoded` form. The object of interest is `text` which contains the text the user added to the slash command.

```java
Map(
   channel_name -> ListBuffer(directmessage),
   team_id -> ListBuffer(*******),
   response_url -> ListBuffer(*******),
   trigger_id -> ListBuffer(*******),
   text -> ListBuffer(Just testing!),
   command -> ListBuffer(/example),
   user_id -> ListBuffer(*******),
   channel_id -> ListBuffer(*******),
   token -> ListBuffer(*******),
   team_domain -> ListBuffer(teika),
   user_name -> ListBuffer(clay)
)
```



# Getting Started With Docker

## Dockerfile

To run the application, we require the computer to have installed both `Java` and `sbt`. And that's what we are going to specified in the Dockerfile.

We choose [`openjdk:8`](https://hub.docker.com/_/openjdk/) as our base image to start with.

    FROM openjdk:8
    
And we install `sbt` on top of that: 

    RUN \
      curl -L -o sbt-$SBT_VERSION.deb http://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
      dpkg -i sbt-$SBT_VERSION.deb && \
      rm sbt-$SBT_VERSION.deb && \
      apt-get update && \
      apt-get install sbt && \
      sbt sbtVersion

Set the working directory to `HelloWorld`:

    WORKDIR /HelloWorld
    
Copy the current directory contents into the container at `HelloWorld`:

    ADD . /HelloWorld 

Run `stb run` when the container launches:

    CMD sbt run

## Docker image and container

An **image** is a lightweight, stand-alone, executable package that includes everything needed to run a piece of software, including the code, a runtime, libraries, environment variables, and config files.

A **container** is a runtime instance of an image – what the image becomes in memory when actually executed. It runs completely isolated from the host environment by default, only accessing host files and ports if configured to do so.

Now run the build command to create a Docker image. We tag it using `-t` so it has a friendly name and version.

    $ docker build -t hello_world:v1 .
    ... 
    Successfully built 8508c6ecdf78
    Successfully tagged hello_world:v1

The built image is in the machine’s local Docker image registry. We can list them via the docker images commend:

    $ docker images
    REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
    hello_world         v1                  2c43052fd1c5        8 minutes ago       774 MB
    openjdk             8                   ab0ecda9094c        11 days ago         610 MB
    ➜  ~ 

Finally, run the app in the container:

    $ docker run hello_world:v1
    [info] Loading project definition from /HelloWorld/project
    ...
    [info]   Compilation completed in 9.433 s
    [info] Running HelloWorld 
    Hello, world!

Reference: https://docs.docker.com/get-started
