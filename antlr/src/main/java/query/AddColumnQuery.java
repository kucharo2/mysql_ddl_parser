package query;

import antlr.MySqlDdlParser;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class AddColumnQuery extends AlterTableQuery {

    private String columnName;

    private Boolean columnNullability;

    private String columnDefinition;

    @Override
    public void workOnAlterSpecification(MySqlDdlParser.AlterSpecificationContext context) {
        columnName = context.columnName.getText();
        workOnColumnDefinition(context.columnDefinition());
    }

    @Override
    public void workOnAlterTable(MySqlDdlParser.AlterTableContext context) {
        tableName = context.tableName.getText();
    }

    protected void workOnColumnDefinition(MySqlDdlParser.ColumnDefinitionContext context) {
        if (context.dataType != null) {
            columnDefinition = context.dataType.getText();
            if (context.nullability != null){
                columnNullability = context.nullability.getText().equals(context.NULL().getText());
            }
        }
    }

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
