package hello;

import hello.extend.AppPlugin;
import hello.extend.PluginLoader;
import hello.extend.PluginRepository;
import hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
public class Application {
    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public CommandLineRunner commandLineRunner(ApplicationContext context) {
        return args -> {
            System.out.print("Inspecting beans provided by Spring boot");

            String[] beanNames = context.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for(String bean : beanNames)
                System.out.print(String.format("%s bean loaded!", bean));
        };
    }

    @Bean
    public PluginRepository getPluginRepository() {
        PluginLoader loader = new PluginLoader(AppPlugin.class, "");
        loader.provideBean("userService", userService);
        Collection plugins = loader.getPlugins(null);
        return new PluginRepository(plugins);
    }
}
