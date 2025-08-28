package ar.com.edsa.taller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
/*@EntityScan(basePackages = {
        "ar.com.edsa.taller.vehicles.domain",
        "ar.com.edsa.taller.works.domain"
})
@EnableJpaRepositories(basePackages = {
        "ar.com.edsa.taller.vehicles.repository",
        "ar.com.edsa.taller.works.repository"
})*/
public class TallerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TallerApplication.class, args);
    }

}
