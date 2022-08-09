package net.chrisrichardson.liveprojects.servicetemplate.security

import net.chrisrichardson.liveprojects.servicetemplate.domain.AuthenticatedUser
import net.chrisrichardson.liveprojects.servicetemplate.domain.AuthenticatedUserSupplier
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.stereotype.Component


@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val jwtConverter = JwtAuthenticationConverter()
        jwtConverter.setJwtGrantedAuthoritiesConverter { jwt ->
            val realmAccess = jwt.claims["realm_access"] as Map<String, List<String>>?
            val roles = realmAccess?.get("roles")
            (roles ?: listOf()).map { SimpleGrantedAuthority("ROLE_$it") }
        }
        return jwtConverter
    }

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers("/actuator/**").permitAll()
            .antMatchers("/swagger**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .antMatchers("/**")
             .hasRole("service-template-user")
            .and()
             .oauth2ResourceServer().jwt { jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter()) }
    }


}

