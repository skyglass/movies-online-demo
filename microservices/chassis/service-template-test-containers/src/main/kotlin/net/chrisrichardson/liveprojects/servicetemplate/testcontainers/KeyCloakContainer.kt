package net.chrisrichardson.liveprojects.servicetemplate.testcontainers

import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.images.builder.ImageFromDockerfile
import java.nio.file.FileSystems

object KeyCloakContainer : DefaultPropertyProvidingContainer() {

    override val container = GenericContainer<Nothing>(ImageFromDockerfile()
            .withDockerfile(FileSystems.getDefault().getPath("../keycloak/Dockerfile"))).apply {
        withReuse(true)
        withEnv("KEYCLOAK_USER", "admin")
        withEnv("KEYCLOAK_PASSWORD", "admin")
        withEnv("KEYCLOAK_IMPORT", "/realm.json")
        withEnv("DB_VENDOR", "h2")

        withExposedPorts(8080)
        ContainerNetwork.withNetwork(this)
        withNetworkAliases(getContainerAlias())
        waitingFor(Wait.forHttp("/auth/"))

    }

    private fun getContainerAlias() = "keycloak"

    override fun consumeProperties(registry: PropertyConsumer) {
        val port = getPort()
        val issuerUrl = "http://localhost:$port/auth/realms/${getRealm()}"


        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", issuerUrl)
        registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri", "http://localhost:$port/auth/realms/${getRealm()}/protocol/openid-connect/certs")
        registry.add("keycloak.auth-server-url", "http://localhost:$port/auth")

        registry.forNameAndPorts(getContainerAlias(), hostPort= getPort(), servicePort=8080)
    }

    fun getKeyCloakUrl(): String {
        val port = container.getMappedPort(8080)
        return "http://localhost:$port/auth"
    }

    fun getPort() = container.getMappedPort(8080)

    fun getRealm(): String {
        return "service-template"
    }


}