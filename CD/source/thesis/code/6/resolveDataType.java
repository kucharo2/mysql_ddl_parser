public DataType resolveDataType(ParserRuleContext dataTypeContext) {
        DataType dataType = null;
        // use priority according to number of matched tokens
        int selectedTypePriority = -1;
        for (DataTypeEntry dataTypeEntry : contextDataTypesMap.get(dataTypeContext.getClass().getCanonicalName())) {
            int dataTypePriority = dataTypeEntry.getDbmsDataTypeTokenIdentifiers().length;
            if (dataTypePriority > selectedTypePriority) {
                DataTypeBuilder dataTypeBuilder = new DataTypeBuilder();
                boolean correctDataType = true;
                for (Integer mainTokenIdentifier : dataTypeEntry.getDbmsDataTypeTokenIdentifiers()) {
                    TerminalNode token = dataTypeContext.getToken(mainTokenIdentifier, 0);
                    if (correctDataType) {
                        if (token == null) {
                            correctDataType = false;
                        }
                        else {
                            dataTypeBuilder.addToName(token.getText());
                        }
                    }
                }
                if (correctDataType) {
                    dataType = buildDataType(dataTypeContext, dataTypeEntry, dataTypeBuilder);
                    selectedTypePriority = dataTypePriority;
                }
            }
        }
        if (dataType == null) {
            throw new ParsingException(null, "Unrecognized dataType for " + AntlrDdlParser.getText(dataTypeContext));
        }
        return dataType;
    }