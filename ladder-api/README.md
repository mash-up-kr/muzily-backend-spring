# ladder-api

### Build & Deploy Image

```shell
./gradlew ladder-api:jib -Dimage={{DOCKER_REGISTRY}} -Dtag={{IMAGE_TAG}}

ex) ./gradlew ladder-api:jib -Dimage=mashupladder/ladder-api -Dtag=0.0.1
```
