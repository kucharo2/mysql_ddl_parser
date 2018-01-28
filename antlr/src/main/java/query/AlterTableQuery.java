package query;

import antlr.MySqlDdlParser;

/**
 * Model that holds parsed data from query of type "alter table".
 *
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public abstract class AlterTableQuery {

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

    public abstract void workOnAlterSpecification(MySqlDdlParser.AlterSpecificationContext context);

    public abstract void workOnAlterTable(MySqlDdlParser.AlterTableContext context);
}
