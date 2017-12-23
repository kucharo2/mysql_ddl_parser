package cz.kucharo2.mysql.query;

import sjm.utensil.PubliclyCloneable;

/**
 * Model that holds parsed data from query of type "alter table".
 *
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public abstract class AlterTableQuery implements PubliclyCloneable {

    /**
     * Table name which is updated by query.
     */
    protected String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // this should never happen
            throw new InternalError();
        }
    }
}
