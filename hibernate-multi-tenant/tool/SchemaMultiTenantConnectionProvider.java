package tool;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author by gimme on 18/3/21.
 */
public class SchemaMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

    private DatasourceConnectionProviderImpl connectionProvider;

    public SchemaMultiTenantConnectionProvider(BasicDataSource dataSource) throws IOException {
        connectionProvider = new DatasourceConnectionProviderImpl();
        connectionProvider.setDataSource(dataSource);
        connectionProvider.configure(new HashMap<>());
    }

    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        return connectionProvider;
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
        return connectionProvider;
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        Connection connection = super.getConnection(tenantIdentifier);
        connection.createStatement().execute(String.format("ALTER SESSION SET CURRENT_SCHEMA = %s", tenantIdentifier));
        return connection;
    }
}
