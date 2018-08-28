package tool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author by gimme on 18/3/19.
 * 處理 ISO-8859-1 跟 MS950 互相轉換，主要處理 Oracle 裡面的中文字
 */
public class AsciiStringType implements UserType {

    private static final Logger logger = LogManager.getLogger(AsciiStringType.class);

    private static final int SQL_TYPE = Types.VARCHAR;
    private static final String OBJECT_TYPE = String.class.getTypeName();

    @Override
    public int[] sqlTypes() {
        return new int[]{SQL_TYPE};
    }

    @Override
    public Class returnedClass() {
        return String.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x == null? y == null: x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {

//        if (rs.wasNull()) {
//            return null;
//        }
        try {
            // ISO-8859-1 to MS950
            return new String(String.valueOf(rs.getObject(names[0])).getBytes("ISO-8859-1"), "MS950");

        } catch (UnsupportedEncodingException e) {

            logger.info(e.getMessage());

        }
        return null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, SQL_TYPE, OBJECT_TYPE);
        }
        else
        {
            try {
                // MS950 to ISO-8859-1
                st.setObject(index, new String(String.valueOf(value).getBytes("MS950"), "ISO-8859-1"), SQL_TYPE);

            } catch (UnsupportedEncodingException e) {

                logger.info(e.getMessage());
                st.setNull(index, SQL_TYPE, OBJECT_TYPE);

            }
        }

    }

    /**
     * Used to create Snapshots of the object
     */
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) {
            return null;
        }
        return String.valueOf(value);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    /**
     * method called when Hibernate puts the data in a second level cache. The
     * data is stored in a serializable form
     */
    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    /**
     * Returns the object from the 2 level cache
     */
    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return this.deepCopy(original);
    }
}
