package cz.kucharo2.mysql.query;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class AddColumnQuery extends AlterTableQuery {

    private String columnName;

    private Boolean columnNullability;

    private String columnDefinition;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Boolean getColumnNullability() {
        return columnNullability;
    }

    public void setColumnNullability(Boolean columnNullability) {
        this.columnNullability = columnNullability;
    }

    public String getColumnDefinition() {
        return columnDefinition;
    }

    public void setColumnDefinition(String columnDefinition) {
        this.columnDefinition = columnDefinition;
    }

    protected String stringifyColumnDefinition() {
        if (columnNullability == null) {
            return "column " + columnName + " with definition " + columnDefinition;
        } else {
            return "column " + columnName + " with definition " + columnDefinition +
                    " nullable: " + columnNullability.toString();
        }
    }

    @Override
    public String toString() {
        return "Adding " + stringifyColumnDefinition() + " to table " + tableName;
    }
}
