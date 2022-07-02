# ladder-search

### Build & Deploy Image

```shell
./gradlew ladder-search:jib -Dimage={{DOCKER_REGISTRY}} -Dtag={{IMAGE_TAG}}

ex) ./gradlew ladder-search:jib -Dimage=mashupladder/ladder-search -Dtag=0.0.1
```
