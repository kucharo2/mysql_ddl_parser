package query;

import antlr.MySqlDdlParser;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class ChangeColumnQuery extends AddColumnQuery {

    private String oldColumnName;

    @Override
    public void workOnAlterSpecification(MySqlDdlParser.AlterSpecificationContext context) {
        oldColumnName = context.columnName.getText();
        setColumnName(context.newColumnName.getText());
        workOnColumnDefinition(context.columnDefinition());
    }

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
