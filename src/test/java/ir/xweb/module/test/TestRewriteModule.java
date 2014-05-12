package ir.xweb.module.test;

import ir.xweb.module.RewriteModule;
import ir.xweb.test.module.TestModule;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestRewriteModule extends TestModule {


    public TestRewriteModule() throws IOException {
    }

    @Test
    public void test() throws IOException, ServletException {
        final RewriteModule m = getManager().getModuleOrThrow(RewriteModule.class);

        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getServerName()).thenReturn("hamed");
        when(request.getContextPath()).thenReturn("/");

        final HttpServletResponse response = mock(HttpServletResponse.class);
        final TestChain chain = new TestChain();

        m.doFilter(getServletContext(), request, response, chain);
    }

    private class TestChain implements FilterChain {

        ServletRequest request;

        ServletResponse response;

        @Override
        public void doFilter(
                final ServletRequest servletRequest,
                final ServletResponse servletResponse) throws IOException, ServletException
        {
            this.request = servletRequest;
            this.response = servletResponse;
        }

        boolean done() {
            return this.request != null || this.response != null;
        }

    }

}