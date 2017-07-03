package hello.plugin;

import hello.extend.AppPlugin;
import hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@AppPlugin
public class PluginTest {
    @Autowired
    private UserService userService;
}
