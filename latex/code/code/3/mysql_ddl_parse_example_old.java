String ddlStatements = "..."
DdlParser parser = new MySqlDdlParser();
Tables tables = new Tables();
parser.parse(ddl, tables);