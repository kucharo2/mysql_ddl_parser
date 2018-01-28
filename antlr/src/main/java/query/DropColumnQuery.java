package query;

import antlr.MySqlDdlParser;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class DropColumnQuery extends AlterTableQuery {

    public DropColumnQuery() {
    }

    public DropColumnQuery(String columnName, String tableName) {
        this.columnName = columnName;
        this.tableName = tableName;
    }

    /**
     * Column name which is dropped by the query.
     */
    private String columnName;

    @Override
    public void workOnAlterSpecification(MySqlDdlParser.AlterSpecificationContext context) {
        columnName = context.columnName.getText();
    }

    @Override
    public void workOnAlterTable(MySqlDdlParser.AlterTableContext context) {
        tableName = context.tableName.getText();
    }

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
