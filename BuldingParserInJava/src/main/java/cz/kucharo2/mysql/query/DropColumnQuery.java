package cz.kucharo2.mysql.query;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class DropColumnQuery extends AlterTableQuery {

    public DropColumnQuery(String columnName, String tableName) {
        this.columnName = columnName;
        this.tableName = tableName;
    }

    /**
     * Column name which is dropped by the query.
     */
    private String columnName;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        return "Dropping column " + columnName + " from table " + tableName;
    }
}
