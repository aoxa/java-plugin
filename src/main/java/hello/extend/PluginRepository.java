package hello.extend;

import java.util.Collection;

/**
 * Created by Pedro on 02/07/2017.
 */
public class PluginRepository {
    private final Collection plugins;

    public PluginRepository(Collection plugins) {
        this.plugins = plugins;
    }

    public Collection getPlugins() {
        return plugins;
    }
}
