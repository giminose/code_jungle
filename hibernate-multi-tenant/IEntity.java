package model.data;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author by gimme on 18/3/22.
 * 建物標示部
 */
@Entity
@Table(name = "IENTITY")
public class IEntity {

    private String column;

    @Column(name = "column")
    @Type(type = "tool.AsciiStringType")
    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

}
