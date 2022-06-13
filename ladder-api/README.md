## Helm Chart (API)

### Build & Deploy Image

```shell
./gradlew ladder-api:deploy -Dimage={{DOCKER_REGISTRY}} -Dtag={{IMAGE_TAG}}

ex) ./gradlew ladder-api:deploy -Dimage=ghcr.io/seungh0/ladder-api-dev -Dtag=0.0.1
```

### Setup Helm

```shell
helm install --values values-{{NAMESPACE}}.yaml ladder . -n {{NAMESPACE}}

ex) helm install --values values-dev.yaml ladder . -n dev
```

### Deploy new version

```shell
helm upgrade --values values-{{NAMESPACE}}.yaml --set image.tag={{IMAGE_TAG}} ladder . -n {{NAMESPACE}}

ex) helm upgrade --values values-dev.yaml --set image.tag=0.0.1 ladder . -n dev
```

### Update ReplicaCount

````shell
helm upgrade --values values-dev.yaml --set replicaCount={{REPLICA_COUNT}} ladder . -n {{NAMESPACE}}

helm upgrade --values values-dev.yaml --set replicaCount=3 ladder . -n dev
````

### Show Helm List

```shell
helm list -n {{NAMESPACE}}

ex) helm list -n dev
```

### Show Status

```shell
helm status ladder -n {{NAMESPACE}}

ex) helm status ladder -n dev
```

### Show Revision Histories

```shell
helm history ladder -n {{NAMESPACE}}

ex) helm history ladder -n dev
```

### Rollback

```shell
helm rollback ladder {{REVISITON}} -n {{NAMESPACE}}

ex) helm rollback ladder {{REVISITON}} -n dev
```
