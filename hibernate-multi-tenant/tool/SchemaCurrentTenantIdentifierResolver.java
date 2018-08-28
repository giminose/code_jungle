package tool;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

/**
 * @author by gimme on 18/3/21.
 */
public class SchemaCurrentTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setTenantIdentifier(String tenantIdentifier) {
        threadLocal.set(tenantIdentifier);
    }

    public SchemaCurrentTenantIdentifierResolver(String defaultTenant) {
        threadLocal.set(defaultTenant);
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        return threadLocal.get();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
