package cz.kucharo2.mysql.query;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class ChangeColumnQuery extends AddColumnQuery {

    private String oldColumnName;

    public String getOldColumnName() {
        return oldColumnName;
    }

    public void setOldColumnName(String oldColumnName) {
        this.oldColumnName = oldColumnName;
    }

    @Override
    public String toString() {
        return "Change column " + oldColumnName + " to new " + stringifyColumnDefinition() + " on table " + tableName;
    }
}
