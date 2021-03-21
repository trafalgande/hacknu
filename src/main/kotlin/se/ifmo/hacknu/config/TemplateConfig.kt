package se.ifmo.hacknu.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.SimpleMailMessage

@Configuration
class TemplateConfig {
    @Bean
    fun exampleNewsletterTemplate(): SimpleMailMessage {
        val template = SimpleMailMessage()
        template.setText("""
            Hello there, 
            Here is your secret to prove your authority:
            %s
            Here is your link to access your card:
            http://localhost:8080/api/view/%s 
            by 'FirstTime'
        """.trimIndent()
        )
        return template
    }
}