dataTypeResolver.registerDataTypes( MySqlParser.StringDataTypeContext.class.getCanonicalName(), Arrays.asList(
                new DataTypeEntry(Types.CHAR, MySqlParser.CHAR),
                new DataTypeEntry(Types.BINARY, MySqlParser.CHAR, MySqlParser.BINARY)
        ));
dataTypeResolver.registerDataTypes( MySqlParser.DimensionDataTypeContext.class.getCanonicalName(), Arrays.asList(
                new DataTypeEntry(Types.DOUBLE, MySqlParser.DOUBLE)
                        .setSuffixTokens(MySqlParser.PRECISION, MySqlParser.SIGNED, MySqlParser.UNSIGNED, MySqlParser.ZEROFILL),
                new DataTypeEntry(Types.DECIMAL, MySqlParser.DECIMAL)
                        .setSuffixTokens(MySqlParser.SIGNED, MySqlParser.UNSIGNED, MySqlParser.ZEROFILL)
                        .setDefaultLengthScaleDimension(10, 0)
        ));
    