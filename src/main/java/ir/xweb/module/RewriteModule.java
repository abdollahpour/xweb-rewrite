
/**
 * XWeb project. 2012~2014.
 * Hamed Abdollahpour
 * https://github.com/abdollahpour/xweb
 */

package ir.xweb.module;

import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

/**
 * XWeb mail module.
 *
 * <p>Send email over SMTP with java mail</p>
 *
 * @author Hamed Abdollahpour
 */
public class RewriteModule extends Module {


    private static final String PARAM_CONFIG_TEST = "config.text";

    private final UrlRewriteFilter u;

    private final String text;

    public RewriteModule(
            final Manager manager,
            final ModuleInfo info,
            final ModuleParam properties) throws ModuleException
    {
        super(manager, info, properties);

        u = new UrlRewriteFilter();
        text = properties.getString(PARAM_CONFIG_TEST, null);
    }

    @Override
    public void init(final ServletContext context) {
        try {
            u.init(new FilterConfig() {
                @Override
                public String getFilterName() {
                    return "rewrite";
                }

                @Override
                public ServletContext getServletContext() {
                    return context;
                }

                @Override
                public String getInitParameter(String s) {
                    if(s.equals("modRewriteConfText")) {
                        return text;
                    }
                    return null;
                }

                @Override
                public Enumeration getInitParameterNames() {
                    if(text != null) {
                        return Collections.enumeration(Arrays.asList("modRewriteConfText"));
                    }
                    return null;
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void doFilter(
            final ServletContext context,
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain) throws IOException, ServletException
    {
        u.doFilter(request, response, filterChain);
    }
}
