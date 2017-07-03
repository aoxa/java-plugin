package hello.extend;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * Created by Pedro on 02/07/2017.
 */
public class PluginLoader {
    private final Class<? extends Annotation> pluginAnnotationType;
    private final String pluginBasePackage;
    private ListableBeanFactory context;

    private final DefaultListableBeanFactory providedBeans;

    public PluginLoader(Class<? extends Annotation> pluginAnnotationType,
                        String pluginBasePackage) {
        this.pluginAnnotationType = pluginAnnotationType;
        this.pluginBasePackage = pluginBasePackage;
        providedBeans = new DefaultListableBeanFactory();
    }

    private void load() {
        if( null != context ) return;

        GenericApplicationContext parentContext = new GenericApplicationContext(providedBeans);
        parentContext.refresh();

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.setParent(parentContext);

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(ctx, false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(pluginAnnotationType));

        scanner.scan(pluginBasePackage);
        ctx.refresh();

        context = ctx;
    }

    private static Collection getPluginDescriptors(ListableBeanFactory context) {
        return context.getBeansWithAnnotation(AppPlugin.class).values();
    }

    public <T> Collection<T> getPlugins(Class<T> extensionPointType) {
        load();
        return getPluginDescriptors(context);
    }

    public void provideBean(String beanName, Object bean) {
        if( null != context )
            throw new IllegalStateException("provideBean must be called before intantiating conterxt");

        providedBeans.registerSingleton(beanName, bean);
    }
}
