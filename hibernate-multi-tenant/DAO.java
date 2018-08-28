
import dao.data.constant.KCDEConst;
import dao.data.constant.SchemaConst;
import model.data.*;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import idv.gizone.utilities.GeoUtil;
import idv.gizone.utilities.StringUtil;
import idv.gizone.utilities.persistence.BaseDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @author by gimme on 18/3/19.
 */
@Repository
public class DAO {

    private SessionFactory sessionFactory;

    @Autowired
    public DAO(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Session getCurrentSession(String tenant) {
        Session currentSession = this.sessionFactory.getCurrentSession();
        if(!StringUtils.equals(currentSession.getTenantIdentifier(), tenant)) {
            currentSession.doWork((connection) -> {
                connection.createStatement().execute(String.format("ALTER SESSION SET CURRENT_SCHEMA = %s", new Object[]{tenant}));
            });
        }

        return this.sessionFactory.getCurrentSession();
    }

    public List<IEntity> getSections(String townCode) {
        Session session = getCurrentSession("schema");
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<IEntity> query = builder.createQuery(IEntity.class);
        Root<IEntity> root = query.from(IEntity.class);

        query.select(root);

        return session.createQuery(query).list();
    }
}
