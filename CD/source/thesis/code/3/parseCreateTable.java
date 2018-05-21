protected void parseCreateTable(Marker start) {
        tokens.canConsume("TEMPORARY");
        tokens.consume("TABLE");
        boolean onlyIfNotExists = tokens.canConsume("IF", "NOT", "EXISTS");
        TableId tableId = parseQualifiedTableName(start);
        if (tokens.canConsume("LIKE")) {
            TableId originalId = parseQualifiedTableName(start);
            Table original = databaseTables.forTable(originalId);
            if (original != null) {
                databaseTables.overwriteTable(tableId, original.columns(), original.primaryKeyColumnNames(), original.defaultCharsetName());
            }
            consumeRemainingStatement(start);
            signalCreateTable(tableId, start);
            debugParsed(start);
            return;
        }
        if (onlyIfNotExists && databaseTables.forTable(tableId) != null) {
            // The table does exist, so we should do nothing ...
            consumeRemainingStatement(start);
            signalCreateTable(tableId, start);
            debugParsed(start);
            return;
        }
        TableEditor table = databaseTables.editOrCreateTable(tableId);
        // create_definition ...
        if (tokens.matches('(')) parseCreateDefinitionList(start, table);
        // table_options ...
        parseTableOptions(start, table);
        // partition_options ...
        if (tokens.matches("PARTITION")) {
            parsePartitionOptions(start, table);
        }
        // select_statement
        if (tokens.canConsume("AS") || tokens.canConsume("IGNORE", "AS") || tokens.canConsume("REPLACE", "AS")) {
            parseAsSelectStatement(start, table);
        }
        // Make sure that the table's character set has been set ...
        if (!table.hasDefaultCharsetName()) {
            table.setDefaultCharsetName(currentDatabaseCharset());
        }
        // Update the table definition ...
        databaseTables.overwriteTable(table.create());
        signalCreateTable(tableId, start);
        debugParsed(start);
    }