public void removeTablesForDatabase(String catalogName, String schemaName) {
        lock.write(() -> {
            tablesByTableId.entrySet().removeIf(tableIdTableEntry -> {
                TableId tableId = tableIdTableEntry.getKey();
                boolean equalSchema = schemaName == null && tableId.schema() == null
                        || schemaName != null && schemaName.equals(tableId.schema());
                boolean equalCatalog = catalogName == null && tableId.catalog() == null
                        || catalogName != null && catalogName.equals(tableId.schema());
                return equalSchema && equalCatalog;
            });
        });
    }